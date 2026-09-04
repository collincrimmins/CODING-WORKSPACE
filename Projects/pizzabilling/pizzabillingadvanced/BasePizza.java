package Projects.pizzabilling.pizzabillingadvanced;

public class BasePizza implements Pizza {
    private final PizzaSize size;

    public BasePizza(PizzaSize size) {
        this.size = size;
    }

    @Override
    public String getDescription() {
        return size.getDisplayName();
    }

    @Override
    public double getCost() {
        return size.getPrice();
    }
    
}
