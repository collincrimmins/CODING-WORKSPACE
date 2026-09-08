package Projects.meetingrooms;

import java.time.Instant;

public class Interval {
    private final Instant start;
    private final Instant end;

    public Interval(Instant start, Instant end) {
        this.start = start;
        this.end = end;
    }

    public boolean isIntervalOverlapping(Interval interval) {
        Instant start2 = interval.getStart();
        Instant end2 = interval.getEnd();

        // System.out.println(start);
        // System.out.println(end);
        // System.out.println(start2);
        // System.out.println(end2);

        return this.start.isBefore(end2) && this.end.isAfter(start2);
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    
}
