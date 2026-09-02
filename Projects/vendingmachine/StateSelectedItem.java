package Projects.vendingmachine;

public class StateSelectedItem extends State {

    public StateSelectedItem(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void dispense() {
        machine.setState(new StateDispense(machine));
        machine.getState().dispense();
    }

    @Override
    public String getStateDescription() {
        return "selecting item";
    }
    
}
