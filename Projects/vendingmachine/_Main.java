package Projects.vendingmachine;

public class _Main {
    public static void main(String[] args) {
        VendingMachine system = new VendingMachine();

        system.addProduct("Product A", 10, 1.00);
        system.addProduct("Product B", 10, 5.00);

        // Buy ProductA
        system.insertMoney(10.00);
        system.insertMoney(15.00);
        system.selectItem("Product A");
        System.out.println("---");

        // Refund Inserted Money
        system.insertMoney(9.99);
        system.refundMoney();
        System.out.println("---");

        // Not enough money
        system.insertMoney(4.50);
        system.selectItem("Product B");
        System.out.println("---");

        // Cannot do XYZ action during XYZ state
        system.setState(new StateReturnChange(system));
        system.insertMoney(0);
        system.selectItem("hello");
        system.setState(new StateSelectedItem(system));
        system.insertMoney(0);
        system.selectItem("hello");
        system.refundMoney();
        system.setState(new StateDispense(system));
        system.insertMoney(0);
        system.selectItem("hello");
        system.refundMoney();
        System.out.println("---");
        system.setState(new StateIdle(system));
    }

    /*
        Prompt: Design and implement a Vending Machine system that allows users to select products, insert coins/notes, 
        dispense products, and return change. The system should manage inventory, handle payments, and use the State design pattern for its operations.
        https://github.com/ashishps1/awesome-low-level-design/blob/main/solutions/java/src/vendingmachine/VendingMachine.java 
    
        Requirements
        - Select Products
        - Insert Coins & return Change
        - Dispense Product
        - Add/Remove from Inventory
        - Use State Pattern

        Entities
        - State
            StateIdle
            StateSelection
            StateDispense
            StateReturnChange
        - VendingMachine
        - Product

        Class Design

        VendingMachine
        - Map<String, Product> products
        - int money
        - State state
        + insertMoney(money)
        + selectProduct(product)
        + addProduct(product, amount, cost)

        Product
        - String name
        - int amount
        - int price

        State
        + insertMoney(amount)
        + makeSelection(product)
        + returnChange()


    */
}
