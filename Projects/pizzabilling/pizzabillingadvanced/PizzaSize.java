package Projects.pizzabilling.pizzabillingadvanced;

public enum PizzaSize {
    SMALL("Small Pizza", 10.00),
    MEDIUM("Medium Pizza", 15.00),
    LARGE("Large Pizza", 20.00);

    private final String displayName;
    private final double price;

    PizzaSize(String displayName, double price) {
        this.displayName = displayName;
        this.price = price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getPrice() {
        return price;
    }
}
