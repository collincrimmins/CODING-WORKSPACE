package Projects.taskscheduler;

import java.util.concurrent.TimeUnit;

public class _Main {
    public static void main(String[] args) throws InterruptedException {
        /*
        DelayQueue
            Thread-safe
            Blocks until job is ready
            Perfect for time-based scheduling
        ExecutorService
            Controls concurrency
            Prevents thread explosion
        Runnable jobs
            Simple
            Flexible
        */
        JobScheduler scheduler = new JobScheduler(2, 4);

        scheduler.schedule(() -> 
            System.out.println("Job 1 executed at " + System.currentTimeMillis()),
            2, TimeUnit.SECONDS
        );

        scheduler.schedule(() -> 
            System.out.println("Job 2 executed at " + System.currentTimeMillis()),
            4, TimeUnit.SECONDS
        );

        scheduler.schedule(() -> 
            System.out.println("Job 3 executed at " + System.currentTimeMillis()),
            1, TimeUnit.SECONDS
        );

        Thread.sleep(6000);
        scheduler.shutdown();
    }

    /*
        Prompt Design a Task Scheduler
        Design a scheduler system that allows users to create, manage, and execute tasks 
        or jobs at specified times or intervals, including support for recurring schedules and task prioritization.
        https://www.hellointerview.com/community/questions/scheduler-system-design/cmtguy6001byt08adk8i49brj 
        https://programmingappliedai.substack.com/p/lld-design-job-scheduler 
    
        Requirements
        - Create Tasks (immediate or delayed)
        - Execute using multiple threads
    */
}
