package Projects.ratelimiter.Limiters;

import java.util.Map;

public class LimiterFactory {
    @SuppressWarnings("unchecked")
    public Limiter create(Map<String, Object> config) {
        // Create Config
        String type = (String) config.get("algorithm");
        Map<String, Object> settings = (Map<String, Object>) config.get("settings");
        
        // Create Limiter
        if (type.equals("TokenBucket")) {
            int maxTokens = (int) settings.getOrDefault("maxTokens", 0);
            int refillTokensPerSecond = (int) settings.getOrDefault("refillTokensPerSecond", 0);
            return new TokenBucketLimiter(maxTokens, refillTokensPerSecond);
        }

        if ("SlidingWindowLog".equals(type)) {
            int maxRequests = ((Number) settings.getOrDefault("maxRequests", 0)).intValue();
            long windowMs = ((Number) settings.getOrDefault("windowMs", 0)).longValue();
            return new SlidingWindowLogLimiter(maxRequests, windowMs);
        }

        throw new IllegalArgumentException("Invalid Limiter Type");
    }
}
