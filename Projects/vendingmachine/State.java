package Projects.vendingmachine;

public abstract class State {
    VendingMachine machine;

    public State(VendingMachine vendingMachine) {
        this.machine = vendingMachine;
    }

    public abstract String getStateDescription();

    public void insertMoney(double amount) {
        System.out.println("[X] currently " + getStateDescription() + "...");
    }

    public void makeSelection(String productName) {
        System.out.println("[X] currently " + getStateDescription() + "...");
    }

    public void dispense() {
        System.out.println("[X] currently " + getStateDescription() + "...");
    }

    public void returnChange() {
        System.out.println("[X] currently " + getStateDescription() + "...");
    }
}
