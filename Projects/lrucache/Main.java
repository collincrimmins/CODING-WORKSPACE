package Projects.lrucache;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    /*
        Prompt: Design and implement an LRU (Least Recently Used) Cache with a fixed capacity.
         The cache should support fast retrieval and insertion, and automatically evict the 
         least recently used item when the capacity is exceeded.
         https://github.com/ashishps1/awesome-low-level-design/blob/main/solutions/java/src/lrucache/README.md 

        Requirements
        - Put, Get, Delete by Key & Value (String : String)
        - Remove Elements using a Strategy (in this case, 100 size limit in an LRU)

        Entities
        - Cache
        - LRU Cache (w/ Node private class)
        - (Optional) DoublyLinkedList - or just build directly into LRU cache
    */
    public static void main(String[] args) throws InterruptedException {
        // LRUCache
        LRUCache<Integer, Integer> cache;

        // Test Put/Get/Delete
        cache = new LRUCache<>(100);
        System.out.println("=== Testing Put, Get, Delete");
        System.out.print("Get (null): ");
        System.out.println(cache.get(100));
        cache.put(100, 99999);
        System.out.print("Get: ");
        System.out.println(cache.get(100));
        System.out.print("Deleted: ");
        cache.delete(100);
        System.out.println(cache.get(100));
        System.out.println("");

        // Test Capacity
        cache = new LRUCache<Integer, Integer>(100);
        for (int i = 1; i <= 101; i++) {
            cache.put(i, i);
        }
        System.out.println("=== Testing Capacity of 100");
        System.out.print("101st Entry (null): ");
        System.out.println(cache.get(1)); // null
        System.out.print("Newest Entry (valid): ");
        System.out.println(cache.get(101)); // 101

        // Test Get - List should be 4 -> 1 -> 3 (evicted 2)
        cache = new LRUCache<Integer, Integer>(3);
        cache.put(1, 1);
        cache.put(2,2);
        cache.put(3, 3);
        cache.get(1);
        cache.put(4, 4);
        cache.printLinkedList();

        // Test Concurrency
        concurrencyTest();
    } 

    static void concurrencyTest() throws InterruptedException {
        int capacity = 5;
        int numberOfThreads = 10;
        
        LRUCache<String, Integer> cache = new LRUCache<>(capacity);
        
        // Executor service to manage worker threads
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        
        // CountDownLatch to make all threads start putting into the cache simultaneously
        CountDownLatch startLatch = new CountDownLatch(1);
        
        // CountDownLatch to wait for all threads to finish their execution
        CountDownLatch finishLatch = new CountDownLatch(numberOfThreads);

        // Submit tasks to the executor
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    // Wait for the signal so all threads attempt to call put() at the same time
                    startLatch.await();
                    
                    // Concurrent action
                    cache.put("Key-" + threadId, threadId);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        // Release all threads at once
        System.out.println("Starting concurrent put operations...");
        startLatch.countDown();

        // Wait for all threads to finish
        finishLatch.await();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Verify outcomes
        System.out.println("\n--- Results ---");
        System.out.println("Cache Size (Expected max " + capacity + "): " + cache.size());
        
        // Print the contents of the internal linked list
        cache.printLinkedList();
    }
}
