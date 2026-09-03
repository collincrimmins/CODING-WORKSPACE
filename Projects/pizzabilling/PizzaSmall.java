package Projects.pizzabilling;

public class PizzaSmall implements Pizza {
    @Override
    public String getDescription() {
        return "Small Pizza";
    }

    @Override
    public double getCost() {
        return 10.00;
    }
    
}
