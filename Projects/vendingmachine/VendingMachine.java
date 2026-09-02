package Projects.vendingmachine;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private State state;
    private double money;
    private Map<String, Product> products;
    private Product selectedProduct;

    public VendingMachine() {
        this.state = new StateIdle(this);
        this.money = 0;
        this.products = new HashMap<>();
    }

    // Admin

    public void addProduct(String productName, int amount, double price) {
        Product product = new Product(productName, amount, price);
        products.put(productName, product);
    }

    public void reset() {
        selectedProduct = null;
        money = 0;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void addBalance(double amount) {
        money = money + amount;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public void setSelectedProduct(Product product) {
        this.selectedProduct = product;
    }

    // State

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void insertMoney(double amount) {
        state.insertMoney(amount);
    }

    public void selectItem(String name) {
        state.makeSelection(name);
    }

    public void refundMoney() {
        state.returnChange();
    }
}
