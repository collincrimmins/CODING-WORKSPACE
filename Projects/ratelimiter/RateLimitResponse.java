package Projects.ratelimiter;

public class RateLimitResponse {
    private final boolean allowed;
    private final int remaining;
    private final long retryAfterMs;

    public RateLimitResponse(boolean allowed, int remaining, long retryAfterMs) {
        this.allowed = allowed;
        this.remaining = remaining;
        this.retryAfterMs = retryAfterMs;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public int getRemaining() {
        return remaining;
    }

    public long getRetryAfterMs() {
        return retryAfterMs;
    }

    @Override
    public String toString() {
        return "[->] allowed: " + allowed + " - tokens left: " + remaining + " - wait (ms): " + retryAfterMs;
    }

}
