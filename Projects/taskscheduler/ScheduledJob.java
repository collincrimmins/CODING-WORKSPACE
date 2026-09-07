package Projects.taskscheduler;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ScheduledJob implements Delayed {
    private final Runnable task;
    private final long executeAt;

    public ScheduledJob(Runnable task, long delay, TimeUnit unit) {
        this.task = task;
        this.executeAt = System.currentTimeMillis() + unit.toMillis(delay);
    }

    public Runnable getTask() {
        return task;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long remaining = executeAt - System.currentTimeMillis();
        return unit.convert(remaining, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        return Long.compare(this.executeAt, ((ScheduledJob) other).executeAt);
    }
}
