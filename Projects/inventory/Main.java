package Projects.inventory;

import java.util.List;

public class Main {
    /*
    
        Prompt: Design an inventory management system that tracks products 
        across multiple warehouses. The system needs to handle adding 
        and removing inventory, transferring stock between locations, 
        and alerting when inventory runs low

        Prompt:  Design a Product Stock Availability Service
        Design a service that determines if a product is in stock across multiple fulfillment centers, each with its own database.
        https://www.hellointerview.com/community/questions/product-stock-availability/cm6ia7l9h00s617nkgn5fzeud

        Requirements
        - Items have a id & name & amount
        - InventorySystem must add/remove from Inventory (composed of Items)
        - InventorySystem must move items between "locations" (warehouses)
        - InventorySystem must "alert" when inventory is low
        - Create X number of Warehouses at initialization
        - "Low Amount" number is unique per warehouse
        - Alerts are warehouse specific
        - Check Availability: given a "product" and "quantitity" return which warehouse can fulfill
        - + Currency of Inventory Access

        Entities
        - InventorySystem (orchestrator)
        - Warehouse (Location w/ List of Items and Amounts)
        - AlertListener
        - AlertConfig (each warehouse has a unique config # that it deems as "too low")
        - X - Product (Not Entity because its just a String)

        Question: "How do you prevent overselling when orders are in progress?"
            When an item is in a User Cart ("reserved"), we add it to => Map<String, Reservation> reservations
            Then .checkAvailability() treats it as unuseable for other users

        Question: "How would you handle inventory that's being shipped between warehouses? (5 day shipping time)"
            You create a "Transfer" object which stores Product & Quantity (and WarehouseFrom and WarehouseTo)
    */


    public static void main(String[] args) {
        InventoryManager system = new InventoryManager(List.of("1", "2", "3"));

        system.addStock("1", "ProductA", 5);
        system.addStock("2", "ProductA", 10);
        system.addStock("3", "ProductABC", 25);

        system.setLowStockAlert("2", "ProductA", 5, new AlertListenerEmail());
        system.setLowStockAlert("2", "ProductA", 4, new AlertListenerEmail());

        // for (int i = 0; i < Integer.MAX_VALUE - 5; i++) {
        //     system.addStock("2", "ProductA", 10);
        //     system.removeStock("2", "ProductA", 8);
        // }

        // Remove
        //system.removeStock("2", "ProductA", 11); // Invalid
        //system.removeStock("2", "ProductA", 5);

        // Transfer
        // Trigger Alert (< 5)
        system.transfer("ProductA", "2", "1", 10);


        system.printAllWarehouseInventories();
    }
}
