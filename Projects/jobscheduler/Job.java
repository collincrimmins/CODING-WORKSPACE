package Projects.jobscheduler;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Job {
    private final String id;
    private final String name;
    private final JobType jobType;
    private Instant executionTime;
    private final Runnable task;

    public Job(String id, String name, JobType jobType, Instant executionTime, Runnable task) {
        this.id = id;
        this.name = name;
        this.jobType = jobType;
        this.executionTime = executionTime;
        this.task = task;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public JobType getJobType() { return jobType; }
    public Instant getExecutionTime() { return executionTime; }
    public Runnable getTask() { return task; }
    
    // Used for Recurring Jobs
    public void rescheduleNextDay() {
        this.executionTime = this.executionTime.plus(1, ChronoUnit.DAYS);
    }
}
