package Projects.pizzabilling.pizzabillingadvanced;

import java.util.List;

public class _Main {
    public static void main(String[] args) {
        Order order = new Order();

        // Pizza #1
        List<ToppingType> toppings1 = List.of(ToppingType.PEPPERONI, ToppingType.PEPPERONI, ToppingType.EXTRA_CHEESE);
        order.addPizza(PizzaSize.LARGE, toppings1);

        // Pizza #2
        List<ToppingType> toppings2 = List.of(ToppingType.EXTRA_CHEESE);
        order.addPizza(PizzaSize.SMALL, toppings2);

        order.printBill();
    }

    /*
        Prompt: Design a pizza billing system where users can add toppings and cost would be calculated accordingly
        https://www.hellointerview.com/community/questions/pizza-billing-system/cm6jwvaj70047ui4bowex2ej7

        Requirements
        - Create a base Pizza (Small, Medium, Large)
        - Add Topping (Cheese, Pepperoni, Sausage)
        - Print out the bill and description

        Entities
        - Pizza
        - Topping
        - PizzaEnums
        - ToppingsEnum
        - Order

        Class Design

        Order
        - String id
        - List<Pizza> pizzas
        + addPizza(pizza)

        Pizza (interface)
        + getDescription()
        + getCost()

        Topping (abstract class implements Pizza)
        - Pizza pizza

        PizzaFactory
        + createPizza(pizza, List<toppings)

        PizzaEnums
        - SMALL
        - MEDIUM
        - LARGE

        ToppingsEnums
        - PEPPERONI
        - EXTRA_CHEESE

    */
}
