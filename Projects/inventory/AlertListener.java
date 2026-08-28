package Projects.inventory;

public interface AlertListener {
    public void onLowStock(String warehouseId, String productId, int currentQuantity);
}
