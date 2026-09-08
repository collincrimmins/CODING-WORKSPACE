package Projects.amazonsearch;

import java.util.ArrayList;
import java.util.List;

public class Query {
    private final List<Product> products;
    private final List<Filter> filters;

    private Query(List<Product> products) {
        this.products = List.copyOf(products);
        this.filters = new ArrayList<>();
    }

    // Run Filter on List
    public List<Product> queryExecute() {
        List<Product> result = this.products;

        for (Filter filter : filters) {
            result = filter.filter(result);
        }

        return result;
    }

    public static class Builder {
        private final Query query;

        public Builder(List<Product> products) {
            this.query = new Query(products);
        }

        public Builder name(String name) {
            query.filters.add(new FilterName(name));
            return this;
        }

        public Builder prime(boolean isPrime) {
            query.filters.add(new FilterPrime(isPrime));
            return this;
        }

        public Builder category(Category category) {
            query.filters.add(new FilterCategory(category));
            return this;
        }

         public Builder price(Double min, Double max) {
            query.filters.add(new FilterPrice(min, max));
            return this;
        }

        public Query build() {
            return query;
        }
    }
}
