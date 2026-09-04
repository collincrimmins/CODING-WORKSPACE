package Projects.pizzabilling.pizzabillingadvanced;

import java.util.List;

public class PizzaFactory {
    public static Pizza create(PizzaSize size, List<ToppingType> toppings) {
        Pizza pizza = new BasePizza(size);

        // Apply Toppings
        if (toppings != null) {
            for (ToppingType topping : toppings) {
                pizza = topping.applyTo(pizza);
            }
        }

        return pizza;
    }
}
