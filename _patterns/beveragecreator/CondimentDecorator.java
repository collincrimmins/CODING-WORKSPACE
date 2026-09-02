package _patterns.beveragecreator;

public abstract class CondimentDecorator implements Beverage {
    protected final Beverage beverage;

    public CondimentDecorator(Beverage beverage) {
        this.beverage = beverage;
    }
}
