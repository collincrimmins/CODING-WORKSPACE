package Projects.jobscheduler._firstcode;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskSchedulerService {
    private final DelayQueue<ScheduledJob> delayQueue;
    private final ExecutorService workerThreadPool;
    private final Thread schedulerThread;
    private volatile boolean isRunning = true;

    public TaskSchedulerService() {
        this.delayQueue = new DelayQueue<>();
        this.workerThreadPool = Executors.newFixedThreadPool(3);
        
        // Single thread continuously listening for ready jobs in the DelayQueue
        this.schedulerThread = new Thread(() -> this.processQueue());
        this.schedulerThread.start();
    }

    public void addJob(Job job) {
        if (job.getJobType() == JobType.RUN_IMMEDIATELY) {
            submitToPool(job);
        } else {
            delayQueue.offer(new ScheduledJob(job));
        }
    }

    private void processQueue() {
        while (isRunning) {
            try {
                // Block until a jobs execution time has arrived
                ScheduledJob scheduledJob = delayQueue.take();
                Job job = scheduledJob.getJob();

                submitToPool(job);

                if (job.getJobType() == JobType.RECURRING_DAILY) {
                    job.rescheduleNextDay();
                    delayQueue.offer(new ScheduledJob(job));
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void submitToPool(Job job) {
        workerThreadPool.submit(() -> {
            try {
                System.out.println("[" + Thread.currentThread().getName() + "] Executing Job: " + job.getName());
                job.getTask().run();
            } catch (Exception e) {
                System.err.println("Error executing job " + job.getId() + ": " + e.getMessage());
            }
        });
    }

    public void shutdown() {
        this.isRunning = false;
        this.schedulerThread.interrupt();
        this.workerThreadPool.shutdown();
    }
}
