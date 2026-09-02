package Projects.vendingmachine;

public class StateReturnChange extends State {

    public StateReturnChange(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void returnChange() {
        double change = machine.getMoney();
        System.out.println("Returning money: $" + change);

        machine.reset();
        machine.setState(new StateIdle(machine));
    }

    @Override
    public String getStateDescription() {
        return "retuning money";
    }
    
}
