package Projects.taskscheduler;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;

public class Worker implements Runnable {
    private final DelayQueue<ScheduledJob> queue;
    private final ExecutorService executor;

    public Worker(DelayQueue<ScheduledJob> queue, ExecutorService executor) {
        this.queue = queue;
        this.executor = executor;
    }

    @Override 
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ScheduledJob job = queue.take(); // blocks until ready
                executor.submit(job.getTask());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    
}
