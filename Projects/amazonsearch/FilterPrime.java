package Projects.amazonsearch;

import java.util.ArrayList;
import java.util.List;

public class FilterPrime implements Filter {
    private final boolean isPrime;

    public FilterPrime(boolean isPrime) {
        this.isPrime = isPrime;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        List<Product> result = new ArrayList<>();

        for (Product product : products) {
            if (product.isPrime() == isPrime) {
                result.add(product);
            }
        }

        return result;
    }


}
