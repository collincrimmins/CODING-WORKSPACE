package Projects.twitterfeed;

import java.time.Instant;

public class PageRequest {
    private final Instant timestamp;
    private final int limit;

    public PageRequest(Instant timestamp, int limit) {
        this.timestamp = timestamp;
        this.limit = limit;
    }
    
     public Instant getTimestamp() {
        return timestamp;
    }
    public int getLimit() {
        return limit;
    }
}
