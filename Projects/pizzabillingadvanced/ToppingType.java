package Projects.pizzabillingadvanced;

public enum ToppingType {
    PEPPERONI("pepperoni", 0.50),
    EXTRA_CHEESE("extra cheese", 0.50),
    SAUSAGE("sausage", 0.75);

    private final String displayName;
    private final double price;

    ToppingType(String displayName, double price) {
        this.displayName = displayName;
        this.price = price;
    }

    // Decorator Factory
    public Pizza applyTo(Pizza targetPizza) {
        return new ToppingDecorator(targetPizza, this.displayName, this.price);
    }
}
