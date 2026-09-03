package Projects.pizzabilling;

import java.util.List;

public class PizzaFactory {
    public static Pizza create(PizzaEnums type, List<ToppingEnums> toppings) {
        Pizza pizza;

        // Create Pizza Base Object
        if (type == PizzaEnums.SMALL) {
            pizza = new PizzaSmall();
        } else if (type == PizzaEnums.MEDIUM) {
            pizza = new PizzaMedium();
        } else if (type == PizzaEnums.LARGE) {
            pizza = new PizzaLarge();
        } else {
            throw new RuntimeException("Invalid Pizza Type Enum");
        }

        // Apply Topping Decorators
        for (ToppingEnums topping : toppings) {
            if (topping == ToppingEnums.PEPPERONI) {
                pizza = new ToppingPepperoni(pizza);
            } else if (topping == ToppingEnums.EXTRA_CHEESE) {
                pizza = new ToppingCheese(pizza);
            } else {
                throw new RuntimeException("Unknown Topping enum");
            }
        }

        return pizza;
    }
}
