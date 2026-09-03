package Projects.pizzabilling;

public class PizzaMedium implements Pizza {
    @Override
    public String getDescription() {
        return "Medium Pizza";
    }

    @Override
    public double getCost() {
        return 15.00;
    }
    
}
