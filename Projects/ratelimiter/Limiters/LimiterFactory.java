package Projects.ratelimiter.Limiters;

import java.util.Map;

public class LimiterFactory {
    @SuppressWarnings("unchecked")
    public Limiter create(Config config) {
        // Create Config
        String type = config.getAlgorithm();
        
        // Create Limiter
        if (type.equals("TokenBucket")) {
            ConfigTokenBucket settings = (ConfigTokenBucket) config;
            int maxTokens = settings.getMaxTokens();
            int refillTokensPerSecond = settings.getRefillTokensPerSecond();
            return new TokenBucketLimiter(maxTokens, refillTokensPerSecond);
        }

        // if ("SlidingWindowLog".equals(type)) {
        //     int maxRequests = ((Number) settings.getOrDefault("maxRequests", 0)).intValue();
        //     long windowMs = ((Number) settings.getOrDefault("windowMs", 0)).longValue();
        //     return new SlidingWindowLogLimiter(maxRequests, windowMs);
        // }

        throw new IllegalArgumentException("Invalid Limiter Type");
    }
}
