package Projects.jobscheduler;

import java.time.Instant;

public class _Main {
    public static void main(String[] args) throws InterruptedException {
        JobSchedulerService system = new JobSchedulerService();

        // Run Immediately
        system.addJob(new Job("1", "Job 1", JobType.RUN_IMMEDIATELY, Instant.now(), 
            () -> {
                System.out.println("RUN_IMMEDIATELY");
            }
        ));

        // Run 3 seconds from now (not recurring)
        system.addJob(new Job("1", "Job 1", JobType.RUN_FUTURE_ONE_TIME, Instant.now().plusSeconds(3), 
            () -> {
                System.out.println("RUN_FUTURE_ONE_TIME - 3 seconds");
            }
        ));

        // Run 5 seconds from now (daily recurring)
        system.addJob(new Job("1", "Job 1", JobType.RUN_RECURRING_DAILY, Instant.now().plusSeconds(5), 
            () -> {
                System.out.println("RUN_RECURRING_DAILY - 5 seconds & run every 5 seconds (simulate daily run)");
            }
        ));

        // shutdown
        //system.shutdown();
    }

    /*
        Prompt Design a Task Scheduler
        Design a scheduler system that allows users to create, manage, and execute tasks 
        immediately or at a future time.
        https://www.hellointerview.com/community/questions/scheduler-system-design/cmtguy6001byt08adk8i49brj 
        https://programmingappliedai.substack.com/p/lld-design-job-scheduler 
    
        Requirements
        - Create Tasks (run immediately / future schedule time, but run just one time / recurring daily at X time)
        - Execute using multiple threads

        Class Design

            JobSchedulerService
            - delayQueue<Job> queue
            - ExecutorService scheduler = Executors.newSingleThreadExecutor()
            - ExecutorService executor = Executors.newThreadPool(3)
            - volatile boolean isRunning
            + addJob()
            + executeJob()
            + schedulerLoop()
            + shutdown()

            Job (runnable)
            - String name
            - JobType type
            - Instant executionTime
            - Runnable job
            + rescheduleNextDay()

            class ScheduledJob implements Delayed
            - Job job
            + compareTo()
            + getDelay()

            enum JobType
            RUN_IMMEDIATELY,
            RUN_FUTURE_ONE_TIME
            RECURRING_DAILY



    */
}
