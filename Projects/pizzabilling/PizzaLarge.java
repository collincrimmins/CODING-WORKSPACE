package Projects.pizzabilling;

public class PizzaLarge implements Pizza {
    @Override
    public String getDescription() {
        return "Large Pizza";
    }

    @Override
    public double getCost() {
        return 20.00;
    }
    
}
