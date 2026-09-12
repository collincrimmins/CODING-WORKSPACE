package Projects.ratelimiter._firstRateLimiter.Limiters;

public class ConfigTokenBucket extends Config {
    private final int maxTokens;
    private final int refillTokensPerSecond;

    public ConfigTokenBucket(String endpoint, int maxTokens, int refillTokensPerSecond) {
        super(endpoint, "TokenBucket");

        this.maxTokens = maxTokens;
        this.refillTokensPerSecond = refillTokensPerSecond;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public int getRefillTokensPerSecond() {
        return refillTokensPerSecond;
    }
    
}
