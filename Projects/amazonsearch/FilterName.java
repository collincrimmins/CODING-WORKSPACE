package Projects.amazonsearch;

import java.util.ArrayList;
import java.util.List;

public class FilterName implements Filter {
    private final String name;

    public FilterName(String name) {
        this.name = name;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        List<Product> result = new ArrayList<>();

        for (Product product : products) {
            if (product.getName().contains(name)) {
                result.add(product);
            }
        }

        return result;
    }
    
}
