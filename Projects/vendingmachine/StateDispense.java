package Projects.vendingmachine;

public class StateDispense extends State {

    public StateDispense(VendingMachine vendingMachine) {
        super(vendingMachine);
       
    }

    @Override
    public void dispense() {
        Product product = machine.getSelectedProduct();
        product.setAmount(product.getAmount() - 1);
        double cost = product.getPrice();
        machine.setMoney(machine.getMoney() - cost);

        System.out.println("dispensing product: " + product.getName());

        machine.setState(new StateReturnChange(machine));
        machine.getState().returnChange();
    }


    @Override
    public String getStateDescription() {
        return "dispensing";
    }
    
}
