package Projects.amazonsearch;

public class Product {
    private String name;
    private double price;
    private boolean prime;
    private Category category;

    public Product(String name, double price, boolean prime, Category category) {
        this.name = name;
        this.price = price;
        this.prime = prime;
        this.category = category;
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public boolean isPrime() {
        return prime;
    }
    public Category getCategory() {
        return category;
    }

    @Override 
    public String toString() {
        return name + "... $" + price + " ... Prime: " + prime + " ... Category: " + category;
    }

}
