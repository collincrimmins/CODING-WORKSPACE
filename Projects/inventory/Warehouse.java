package Projects.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {
    private final String id;
    private final Map<String, Integer> inventory;
    private final Map<String, List<AlertConfig>> alertConfigs;
    
    public Warehouse(String id) {
        this.id = id;
        this.inventory = new HashMap<>();
        this.alertConfigs = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    // Inventory

    public void addStock(String productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity < 0");
        }

        synchronized(this) {
            int currentQuantity = inventory.getOrDefault(productId, 0);
            inventory.put(productId, currentQuantity + quantity);
        }
    }

    public boolean removeStock(String productId, int quantity) {
        List<AlertToFire> alertsToFire = null;
        
        synchronized(this) {
            if (quantity <= 0) {
                return false;
            }

            int currentQuantity = inventory.getOrDefault(productId, 0);
            if (currentQuantity < quantity) {
                return false;
            }

            int newQuantity = currentQuantity - quantity;
            inventory.put(productId, newQuantity);

            alertsToFire = getAlertsToFire(productId, currentQuantity, newQuantity);
        }

        // Run Async Alerts outside of Lock
        if (alertsToFire != null) {
            fireAlerts(alertsToFire);
        }

        return true;
    }

    public int getStock(String productId) {
        synchronized(this) {
            return inventory.getOrDefault(productId, 0);
        }
    }

    public boolean checkAvailability(String productId, int quantity) {
        synchronized(this) {
            if (quantity <= 0) {
                return false;
            }

            int currentQuantity = inventory.getOrDefault(productId, 0);
            return currentQuantity >= quantity;
        }
    }

    public void printInventory() {
        synchronized(this) {
            System.out.println("=== Warehouse #" + id);
            System.out.println(inventory.toString());
        }
    }

    // Alerts
    
    public void setLowStockAlert(String productId, int threshold, AlertListener listener) {
        // Valid Quantity
        if (threshold <= 0) {
            throw new IllegalArgumentException("Threshold must be positive");
        }

        // Valid Listener
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        // Add New Alert to List by ProductID
        alertConfigs.computeIfAbsent(productId, k -> new ArrayList<>());
        alertConfigs.get(productId).add(new AlertConfig(threshold, listener));
    }

    private List<AlertToFire> getAlertsToFire(String productId, int previousQty, int newQty) {
        List<AlertToFire> result = new ArrayList<>();

        List<AlertConfig> listConfigs = alertConfigs.getOrDefault(productId, new ArrayList<>());
        for (AlertConfig alert : listConfigs) {
            // First Drop below Threshold Quantity
            if (alert.getThreshold() > newQty && alert.getThreshold() <= previousQty) {
                result.add(new AlertToFire(alert.getListener(), productId, newQty));
            }
        }

        if (result.size() > 0) {
            return result;
        }
        return null;
    }

    private void fireAlerts(List<AlertToFire> alerts) {
        for (AlertToFire alert : alerts) {
            alert.listener.onLowStock(id, alert.productId, alert.quantity);
        }
    }

    private static class AlertToFire {
        final AlertListener listener;
        final String productId;
        final int quantity;

        AlertToFire(AlertListener listener, String productId, int quantity) {
            this.listener = listener;
            this.productId = productId;
            this.quantity = quantity;
        }
    }
}
