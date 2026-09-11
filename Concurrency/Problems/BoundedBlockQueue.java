package Problems;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BoundedBlockQueue {
    // Semaphore to track the number of items in the queue (full spots)
    private Semaphore full; 

    // Semaphore to track the number of available empty spots in the queue
    private Semaphore empty; 

    // A concurrent deque to store the elements of the queue
    private ConcurrentLinkedDeque<Integer> deque; 

    // Constructor to initialize the semaphores and the deque
    public BoundedBlockQueue(int capacity) { 
        // 'full' starts with 0 since the queue is empty initially
        full = new Semaphore(0); 
        // 'empty' starts with the capacity of the queue
        empty = new Semaphore(capacity); 
        // Initialize the concurrent deque to hold queue elements
        deque = new ConcurrentLinkedDeque<>(); 
    } 

    // Method to add an element to the queue
    public void enqueue(int element) throws InterruptedException { 
        // Acquire an empty spot before adding an element
        empty.acquire(); 
        // Add the element to the front of the deque
        deque.addFirst(element); 
        // Release a full spot after adding the element
        full.release(); 
    } 

    // Method to remove and retrieve an element from the queue
    public int dequeue() throws InterruptedException { 
        int result = -1; 
        // Acquire a full spot before removing an element
        full.acquire(); 
        // Remove the element from the end of the deque
        result = deque.pollLast(); 
        // Release an empty spot after removing the element
        empty.release(); 
        return result; 
    } 

    // Method to get the current size of the queue
    public int size() throws InterruptedException { 
        int result = 0; 
        // Retrieve and return the size of the deque
        result = deque.size();
        return result; 
    } 

    public static void main(String[] args) throws InterruptedException {
        int capacity = 3;
        BoundedBlockQueue queue = new BoundedBlockQueue(capacity);

        System.out.println("--- Test 1: Capacity & Blocking Verification ---");
        
        // Consumer thread: Tries to dequeue immediately (will block until items exist)
        Thread consumerThread = new Thread(() -> {
            try {
                System.out.println("[Consumer] Attempting to dequeue (should wait)...");
                int val = queue.dequeue();
                System.out.println("[Consumer] Successfully dequeued: " + val);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumerThread.start();
        Thread.sleep(500); // Give consumer time to block

        // Producer thread: Fills capacity + 1 (4th element should block until consumer reads)
        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 1; i <= 4; i++) {
                    System.out.println("[Producer] Enqueuing: " + i);
                    queue.enqueue(i);
                    System.out.println("[Producer] Enqueued: " + i + " | Queue size: " + queue.size());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producerThread.start();

        producerThread.join();
        consumerThread.join();

        System.out.println("\n--- Test 2: Stress Testing Concurrent Producers and Consumers ---");

        BoundedBlockQueue stressQueue = new BoundedBlockQueue(5);
        int totalItems = 20;
        ExecutorService executor = Executors.newFixedThreadPool(6);

        // Submit 3 producer tasks (producing 20 total items)
        for (int i = 0; i < totalItems; i++) {
            final int item = i;
            executor.submit(() -> {
                try {
                    stressQueue.enqueue(item);
                    System.out.println("Produced: " + item);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Submit 3 consumer tasks (consuming 20 total items)
        for (int i = 0; i < totalItems; i++) {
            executor.submit(() -> {
                try {
                    int val = stressQueue.dequeue();
                    System.out.println("Consumed: " + val);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("\nAll tasks finished. Final queue size: " + stressQueue.size());
    }

    /*
    
        Implement a thread-safe bounded blocking queue that has the following methods:
        • BoundedBlockingQueue(int capacity) The constructor initializes the queue with a maximum capacity.
        • void enqueue(int element) Adds an element to the front of the queue. If the queue is full, the calling thread is blocked until the queue is no longer full.
        • int dequeue() Returns the element at the rear of the queue and removes it. If the queue is empty, the calling thread is blocked until the queue is no longer empty.
        • int size() Returns the number of elements currently in the queue.

        Your implementation will be tested using multiple threads at the same time. 
        Each thread will either be a producer thread that only makes calls to the enqueue 
        method or a consumer thread that only makes calls to the dequeue method.
        The size method will be called after every test case.

        A bounded blocking queue is a queue that allows concurrent access but 
        limits the number of elements that can be stored at a given time.
         The challenge lies in ensuring that:
            • Producers cannot insert elements when the queue is full and must wait.
            • Consumers cannot remove elements when the queue is empty and must wait.
            • The operations must be thread-safe to handle multiple producer and consumer 
            threads simultaneously.
        To achieve this, we use semaphores to control access to the queue, preventing race 
        conditions and ensuring proper synchronization.
    */
}
