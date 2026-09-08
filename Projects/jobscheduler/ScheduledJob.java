package Projects.jobscheduler;

import java.time.Instant;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ScheduledJob implements Delayed {
    private final Job job;

    public ScheduledJob(Job job) {
        this.job = job;
    }

    public Job getJob() {
        return job;
    }

    @Override
    public int compareTo(Delayed o) {
        if (this == o) {
            return 0;
        }

        long diff = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
        return Long.compare(diff, 0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diffMs = job.getExecutionTime().toEpochMilli() - Instant.now().toEpochMilli();
        return unit.convert(diffMs, TimeUnit.MILLISECONDS);
    }
    
}
