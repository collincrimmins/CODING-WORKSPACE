package Projects.amazonsearch;

import java.util.ArrayList;
import java.util.List;

public class SearchService {
    private final List<Product> products;

    public SearchService() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return List.copyOf(products);
    }

    public void printListProducts(List<Product> list) {
        for (Product product : list) {
            System.out.println("- " + product.toString());
        }
    }
    
}
