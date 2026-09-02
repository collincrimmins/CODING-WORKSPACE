package _patterns.beveragecreator;

public class Main {
    public static void main(String[] args) {
        /*
            This is a Demo of the "Decorator" pattern
            - interface Beverage
                String getDescription();
                double getCost();
            - abstract class CondimentDecorator
        */

        Beverage drink1 = new Espresso();
        printOrder(drink1);

        Beverage drink2 = new Espresso();
        drink2 = new Mocha(drink2);
        drink2 = new Milk(drink2);
        printOrder(drink2);

        Beverage drink3 = new HouseBlend();
        drink3 = new Mocha(drink3); // 1st Mocha
        drink3 = new Mocha(drink3); // 2nd Mocha
        drink3 = new Milk(drink3);  // Milk
        printOrder(drink3);
    }

    private static void printOrder(Beverage beverage) {
       System.out.printf("Order: %-35s | Price: $%.2f%n", 
            beverage.getDescription(), beverage.getCost());
    }
}
