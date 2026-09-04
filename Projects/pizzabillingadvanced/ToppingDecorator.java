package Projects.pizzabillingadvanced;

public class ToppingDecorator implements Pizza {
    private final Pizza pizza;
    private final String toppingName;
    private final double toppingCost;

    public ToppingDecorator(Pizza pizza, String toppingName, double toppingCost) {
        this.pizza = pizza;
        this.toppingName = toppingName;
        this.toppingCost = toppingCost;
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + " + " + toppingName;
    }

    @Override
    public double getCost() {
        return pizza.getCost() + toppingCost;
    }
    
}
