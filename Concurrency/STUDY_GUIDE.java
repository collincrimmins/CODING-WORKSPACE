import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class STUDY_GUIDE {
    public static void main(String[] args) {
        
        Runnable task = () -> System.out.println("Running on: " + Thread.currentThread().getName());
        Thread thread = new Thread(task, "Worker-1");
        thread.start(); // Always call start(), NOT run()




        // Fixed Pool: Constant number of threads
        ExecutorService fixedPool = Executors.newFixedThreadPool(4);

        // Cached Pool: Dynamic thread allocation (ideal for short-lived, bursty tasks)
        ExecutorService cachedPool = Executors.newCachedThreadPool();

        // Scheduled Pool: Delayed or periodic task execution
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        scheduler.scheduleAtFixedRate(
            () -> System.out.println("Heartbeat"), 
            0, 1, TimeUnit.SECONDS
        );



    }
}
