package Projects.ratelimiter;

public class LimiterResponse {
    private final boolean allowed;

    public boolean isAllowed() {
        return allowed;
    }

    public LimiterResponse(boolean allowed) {
        this.allowed = allowed;
    }
}
