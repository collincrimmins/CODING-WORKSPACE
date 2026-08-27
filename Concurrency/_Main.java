import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class _Main {
    private final static Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        /*
            Atomics

            Use atomics for counters, flags, and simple statistics. 
            They're fast but limited to single variables, 
            so the moment you need to update two things together, atomics no longer help.
        */

        AtomicInteger counter = new AtomicInteger(0);
        counter.incrementAndGet();  // Thread-safe increment

        /*
            Locks (Mutual Exclusion / Mutexes)
        */

       synchronized (lock) {
            // Work here
       }

       /*
            Semaphores
       */

        Semaphore permits = new Semaphore(5);  // Allow 5 concurrent operations
        permits.acquire();  // Block if no permits available
        try {
            System.out.println("do work");
        } finally {
            permits.release();  // Always release, even on exception
        }

        /*
            Condition Varialbes
        */

        boolean condition = true;
        synchronized (lock) {
            while (!condition) {
                lock.wait();  // Release lock and sleep
            }
            // Condition is now true
        }

        /*
            BlockingQueue
        */

        
        BlockingQueue<Task> queue = new LinkedBlockingQueue<>(100);
        //queue.put(task);   // Blocks if queue is full
        Task t = queue.take();  // Blocks if queue is empty
    }

    private class Task{
        public Task(){}
    }
}
