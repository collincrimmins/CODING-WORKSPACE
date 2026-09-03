package Projects.pizzabilling;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private final List<Pizza> pizzas;

    public Order() {
        this.id = "ORDER-" + UUID.randomUUID().toString().substring(0, 10);
        this.pizzas = new ArrayList<>();
    }

    public void addPizza(PizzaEnums type, List<ToppingEnums> toppings) {
        Pizza pizza = PizzaFactory.create(type, toppings);
        pizzas.add(pizza);
    }

    private double calculateTotal() {
        double total = 0.00;
        for (Pizza pizza : pizzas) {
            total = total + pizza.getCost();
        }
        return total;
    }

    public void printBill() {
        System.out.println("====== BILL (" + id + ") ======");
        for (int i = 0; i < pizzas.size(); i++) {
            Pizza pizza = pizzas.get(i);
            System.out.println((i + 1) + ". " + pizza.getDescription() + " -> $" + pizza.getCost());
        }
        System.out.println("----------------------------------------");
        System.out.println("Total Amount: $" + calculateTotal());
        System.out.println("========================================");
    }
}
