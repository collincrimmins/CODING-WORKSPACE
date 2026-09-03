package Projects.lrucache;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Projects.lrucache.old.LRUCache3;

public class _Main {
    /*
        Prompt: Design and implement an LRU (Least Recently Used) Cache with a fixed capacity.
         The cache should support fast retrieval and insertion, and automatically evict the 
         least recently used item when the capacity is exceeded.
         https://github.com/ashishps1/awesome-low-level-design/blob/main/solutions/java/src/lrucache/README.md 

        Prompt #2: Design an in-memory key-value store that supports multiple data structures (sorted sets, lists, etc.) 
        and can be configured with different eviction strategies like LRU, LFU, or TTL-based expiration.
        https://www.hellointerview.com/community/questions/kv-store-eviction/cmj4k51r300kw08ado0weux4y 

        Requirements
        - Put, Get, Delete by Key & Value (String : String)
        - Eviction Strategies (LRU, etc)

        Entities
        - Cache
        - LRU Cache (w/ Node private class)
        - (Optional) DoublyLinkedList - or just build directly into LRU cache

        Class Design

            Cache
            - Map<K, V> map
            - int capacity
            + put(key, value)
            + get(key)
            + removed(key)

            EvictionStrategy interface (LRU, FIFO)
            + keyAccessed(key)
            + keyAdded(key)
            + keyRemoved(key)
            + evict(key)


    */
    public static void main(String[] args) throws InterruptedException {
        Cache<Integer, Integer> cache;

        /*
            LRUCache
        */

         // Test Put/Get/Delete
        cache = new Cache<>(100, new EvictionStrategyLRU<>());
        System.out.println("=== Testing Put, Get, Delete");
        System.out.print("Get (null): ");
        System.out.println(cache.get(100));
        cache.put(100, 99999);
        System.out.print("Get: ");
        System.out.println(cache.get(100));
        System.out.print("Deleted: ");
        cache.delete(100);
        System.out.println(cache.get(100));
        System.out.println(" ");

        // Test Capacity
        cache = new Cache<>(100, new EvictionStrategyLRU<>());
        for (int i = 1; i <= 101; i++) {
            cache.put(i, i);
        }
        System.out.println("=== Testing Capacity of 100");
        System.out.print("101st Entry (null): ");
        System.out.println(cache.get(1)); // null
        System.out.print("100st Entry (valid): ");
        System.out.println(cache.get(2)); // 2
        System.out.print("Newest Entry (valid): ");
        System.out.println(cache.get(101)); // 101
        System.out.println(" ");

        // Test Get - List should be 4 -> 1 -> 3 (evicted 2)
        cache = new Cache<>(3, new EvictionStrategyLRU<>());
        cache.put(1, 1);
        cache.put(2,2);
        cache.put(3, 3);
        cache.get(1);
        cache.put(4, 4);
        System.out.println("=== Testing eviction of Key 2");
        cache.printCacheDatastructure();
        
        /*
            FIFOCache
        */


        // LRUCache
        // LRUCache3<Integer, Integer> cache;

        // // Test Put/Get/Delete
        // cache = new LRUCache3<>(100);
        // System.out.println("=== Testing Put, Get, Delete");
        // System.out.print("Get (null): ");
        // System.out.println(cache.get(100));
        // cache.put(100, 99999);
        // System.out.print("Get: ");
        // System.out.println(cache.get(100));
        // System.out.print("Deleted: ");
        // cache.delete(100);
        // System.out.println(cache.get(100));
        // System.out.println("");

        // // Test Capacity
        // cache = new LRUCache3<Integer, Integer>(100);
        // for (int i = 1; i <= 101; i++) {
        //     cache.put(i, i);
        // }
        // System.out.println("=== Testing Capacity of 100");
        // System.out.print("101st Entry (null): ");
        // System.out.println(cache.get(1)); // null
        // System.out.print("Newest Entry (valid): ");
        // System.out.println(cache.get(101)); // 101

        // // Test Get - List should be 4 -> 1 -> 3 (evicted 2)
        // cache = new LRUCache3<Integer, Integer>(3);
        // cache.put(1, 1);
        // cache.put(2,2);
        // cache.put(3, 3);
        // cache.get(1);
        // cache.put(4, 4);
        // cache.printLinkedList();

        // Test Concurrency
        //concurrencyTest();
    } 

    static void concurrencyTest() throws InterruptedException {
        int capacity = 5;
        int numberOfThreads = 10;
        
        LRUCache3<String, Integer> cache = new LRUCache3<>(capacity);
        
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
