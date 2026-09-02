package Projects.vendingmachine;

public class StateIdle extends State {

    public StateIdle(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Inserting money: " + amount);
        machine.addBalance(amount);
    }

    @Override
    public void makeSelection(String productName) {
        if (!machine.getProducts().containsKey(productName)) {
            System.out.println("Product does not exist");
            return;
        }

        Product product = machine.getProducts().get(productName);

        if (product.getAmount() == 0) {
            System.out.println("Product empty");
            return;
        }

        if (machine.getMoney() < product.getPrice()) {
            System.out.println("Not enough money");
            return;
        }

        // Dispense
        machine.setSelectedProduct(product);
        machine.setState(new StateSelectedItem(machine));
        machine.getState().dispense();
    }

    @Override
    public void returnChange() {
        if (machine.getMoney() > 0) {
            machine.setState(new StateReturnChange(machine));
            machine.getState().returnChange();
        }
    }

    @Override
    public String getStateDescription() {
        return "idle";
    }
    
}
