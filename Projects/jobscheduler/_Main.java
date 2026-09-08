package Projects.jobscheduler;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class _Main {
    public static void main(String[] args) throws InterruptedException {
        TaskSchedulerService scheduler = new TaskSchedulerService();

        // Example Runnable
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from a task!");
            }
        };

        // 1. Immediate Job
        Job job1 = new Job("1", "Immediate Job", JobType.RUN_IMMEDIATELY, Instant.now(), () -> {
            System.out.println("-> Immediate Job executed instantly!");
        });

        // 2. Future One-Time Job (5 seconds from now)
        Job job2 = new Job("2", "Future Job (5s)", JobType.RUN_FUTURE_ONE_TIME, Instant.now().plusSeconds(5), () -> {
            System.out.println("-> Future Job executed after 5 seconds delay!");
        });

        // 3. Recurring Daily Job (Starts in 2 seconds)
        Job job3 = new Job("3", "Recurring Job", JobType.RECURRING_DAILY, Instant.now().plusSeconds(2), () -> {
            System.out.println("-> Recurring Job executed!");
        });

         Job job4 = new Job("4", "Run now", JobType.RUN_IMMEDIATELY, Instant.now(), () -> {
            System.out.println("-> ran immmediately");
        });

        System.out.println("Adding jobs to scheduler...");
        scheduler.addJob(job1);
        scheduler.addJob(job2);
        scheduler.addJob(job3);
        scheduler.addJob(job4);

        // Let the scheduler run for 7 seconds to observe execution
        TimeUnit.SECONDS.sleep(7);

        // Shutdown cleanly
        scheduler.shutdown();
        System.out.println("Scheduler shut down.");
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
    */
}
