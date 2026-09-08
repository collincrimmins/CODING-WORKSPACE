package Projects.amazonsearch;

import java.util.ArrayList;
import java.util.List;

public class _Main {
    public static void main(String[] args) {
        SearchService system = new SearchService();

        // Catalog
        system.addProduct(new Product("iPhone 10", 500, false, Category.TECHNOLOGY));
        system.addProduct(new Product("iPhone 17", 800, false, Category.TECHNOLOGY));
        system.addProduct(new Product("Shovel", 15, false, Category.HOME));
        system.addProduct(new Product("Shovel w/ Prime", 15, true, Category.HOME));
        system.addProduct(new Product("Super Random Item", 5, false, Category.NO_CATEGORY_SELECTED));
        system.addProduct(new Product("Football", 5, false, Category.SPORTS));
        system.addProduct(new Product("Prime Product", 10, true, Category.NO_CATEGORY_SELECTED));

        // Variables
        List<Product> catalog = system.getProducts();
        Query query = null;
        List<Product> result = null;

        // Query
        System.out.println("=== Query Entire Catalog:");
        query = new Query.Builder(catalog)
                        .build();
        result = query.queryExecute();
        system.printListProducts(result);
        System.out.println(" ");

        // Query
        System.out.println("=== Query Prime only:");
        query = new Query.Builder(catalog)
                        .prime(true)
                        .build();
        result = query.queryExecute();
        system.printListProducts(result);
        System.out.println(" ");

        // Query
        System.out.println("=== Query Prime false only:");
        query = new Query.Builder(catalog)
                        .prime(false)
                        .build();
        result = query.queryExecute();
        system.printListProducts(result);
        System.out.println(" ");

        // Query
        System.out.println("=== Query by search 'iPhone':");
        query = new Query.Builder(catalog)
                        .name("iPhone 17")
                        .build();
        result = query.queryExecute();
        system.printListProducts(result);
        System.out.println(" ");

        // Query
        System.out.println("=== Query by price > 10 and < 100:");
        query = new Query.Builder(catalog)
                        .price(10.00, 100.00)
                        .build();
        result = query.queryExecute();
        system.printListProducts(result);
        System.out.println(" ");

        // Query
        System.out.println("=== Query by Category HOME:");
        query = new Query.Builder(catalog)
                        .category(Category.HOME)
                        .build();
        result = query.queryExecute();
        system.printListProducts(result);
        System.out.println(" ");

        // Query
        System.out.println("=== Query by multiple parameters:");
        query = new Query.Builder(catalog)
                        .price(10.00, 20.00)
                        .prime(true)
                        .category(Category.HOME)
                        .name("Shovel w/ Prime")
                        .build();
        result = query.queryExecute();
        system.printListProducts(result);
        System.out.println(" ");

        System.out.println("Note: This design would be 10x better filtering directly on each list, not List.copy() and adding");
        
    }

    /*
        Prompt: Design an Amazon Product Search Tool
        Design a product searching tool similar to Amazon's search functionality 
        that allows users to filter and find products based on criteria such as 
        Prime eligibility, price range, and category.
        https://www.hellointerview.com/community/questions/product-search-tool/cmooif6v019dg0ead2y5xdn0e 

        Requirements:
        - Search Querying (optional filters & stacking)
        - Product Catalog

        Class Design

            Category
            Filter (FilterName, FilterPrime)
            Product
            Query
            SearchService

    */
}
