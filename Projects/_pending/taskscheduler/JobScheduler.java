package Projects._pending.taskscheduler;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.platform.engine.ExecutionRequest;

public class JobScheduler {
    private final DelayQueue<ScheduledJob> queue = new DelayQueue<>();
    private final ExecutorService jobExecutor;
    private final ExecutorService workerExecutor;

    public JobScheduler(int workerThreads, int jobThreads) {
        this.jobExecutor = Executors.newFixedThreadPool(jobThreads);
        this.workerExecutor = Executors.newFixedThreadPool(workerThreads);

        for (int i = 0; i < workerThreads; i++) {
            workerExecutor.submit(new Worker(queue, jobExecutor));
        }
    }

    public void schedule(Runnable task, long delay, TimeUnit unit) {
        queue.offer(new ScheduledJob(task, delay, unit));
    }

    public void shutdown() {
        workerExecutor.shutdownNow();
        jobExecutor.shutdown();
    }
}
