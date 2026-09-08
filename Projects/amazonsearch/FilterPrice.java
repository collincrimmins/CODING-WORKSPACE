package Projects.amazonsearch;

import java.util.ArrayList;
import java.util.List;

public class FilterPrice implements Filter {
    private final Double minPrice;
    private final Double maxPrice;

    public FilterPrice(Double minPrice, Double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        List<Product> result = new ArrayList<>();

        boolean validMin;
        boolean validMax;
        for (Product product : products) {
            validMin = (minPrice == null) || product.getPrice() >= minPrice;
            validMax = (maxPrice == null) || product.getPrice() <= maxPrice;
            if (validMin && validMax) {
                result.add(product);
            }
        }

        return result;
    }
}
