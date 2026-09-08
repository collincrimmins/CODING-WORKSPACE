package Projects.amazonsearch;

import java.util.ArrayList;
import java.util.List;

public class FilterCategory implements Filter {
    private final Category category;

    public FilterCategory(Category category) {
        this.category = category;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        List<Product> result = new ArrayList<>();

        for (Product product : products) {
            if (product.getCategory() == category) {
                result.add(product);
            }
        }

        return result;
    }

}
