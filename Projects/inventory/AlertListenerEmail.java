package Projects.inventory;

public class AlertListenerEmail implements AlertListener {

    @Override
    public void onLowStock(String warehouseId, String productId, int currentQuantity) {
        System.out.println("Email: Low stock of " + productId + " remaining x" + currentQuantity + "  [warehouse " + warehouseId + "]");
    }

}
