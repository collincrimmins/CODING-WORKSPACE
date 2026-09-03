package Projects.pizzabilling;

public class ToppingCheese extends Topping {
    public ToppingCheese(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + " + extra cheese";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 0.50;
    }
    
}
