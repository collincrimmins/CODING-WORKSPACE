package Projects.jobscheduler;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JobSchedulerService {
    private final ExecutorService schedulerThread;
    private final ExecutorService executorThreads;
    private final DelayQueue<JobScheduled> delayQueue;
    private volatile boolean isRunning;

    public JobSchedulerService() {
        this.schedulerThread = Executors.newSingleThreadExecutor();
        this.executorThreads = Executors.newFixedThreadPool(3);
        this.delayQueue = new DelayQueue<>();

        this.isRunning = true;
        schedulerThread.submit(() -> this.schedulerLoop());
    }

    public void addJob(Job job) {
        if (job.getJobType() == JobType.RUN_IMMEDIATELY) {
            executeTask(job);
        } else {
            // Future or Recurring
            delayQueue.offer(new JobScheduled(job));
        }
    }

    private void schedulerLoop() {
        while (isRunning && !Thread.currentThread().isInterrupted()) {
            try {
                JobScheduled scheduledJob = delayQueue.take(); // blocks until valid execution time
                Job job = scheduledJob.getJob();

                // Submit to Worker Threads
                executeTask(job);

                // Reschedule for Next Day
                if (job.getJobType() == JobType.RUN_RECURRING_DAILY) {
                    job.rescheduleNextDay();
                    delayQueue.offer(new JobScheduled(job));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("Error : " + e.getMessage());
            }
        }
    }

    private void executeTask(Job job) {
        executorThreads.submit(() -> {
            try {
                // Run Runnable
                System.out.println("Running job " + job.getName() + " - " + Thread.currentThread().getName());
                job.getTask().run();
            } catch (Exception e) {
                System.err.println("Error executing job " + job.getId() + ": " + e.getMessage());
            }
        });
    }

    public void shutdown() {
        isRunning = false;
        schedulerThread.shutdownNow(); // interruts delayQueue.take() waiting state
        executorThreads.shutdown();

        try {
            if (!executorThreads.awaitTermination(5, TimeUnit.SECONDS)) {
                executorThreads.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorThreads.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
