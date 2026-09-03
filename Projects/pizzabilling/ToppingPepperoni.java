package Projects.pizzabilling;

public class ToppingPepperoni extends Topping {
    public ToppingPepperoni(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + " + pepperoni";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 0.50;
    }
}
