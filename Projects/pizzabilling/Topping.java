package Projects.pizzabilling;

public abstract class Topping implements Pizza {
    protected final Pizza pizza;

    public Topping(Pizza pizza) {
        this.pizza = pizza;
    }
    
}
