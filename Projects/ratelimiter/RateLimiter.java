package Projects.ratelimiter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Projects.ratelimiter.Limiters.Config;
import Projects.ratelimiter.Limiters.Limiter;
import Projects.ratelimiter.Limiters.LimiterFactory;
import Projects.ratelimiter.Limiters.TokenBucketLimiter;

public class RateLimiter {
    Map<String, Limiter> limiters;

    public RateLimiter(List<Config> configs) {
        // Create List of Limiters
        this.limiters = new HashMap<>();
        LimiterFactory factory = new LimiterFactory();

        // Add Configs
        for (Config config : configs) {
            Limiter limiter = factory.create(config);
            limiters.put(config.getEndpoint(), limiter);
        }
    }

    public RateLimitResponse allow(String clientId, String endpoint) {
        // Default Limiter
        if (!limiters.containsKey(endpoint)) {
            throw new RuntimeException("no defined rate limiter for " + endpoint);
            //limiters.computeIfAbsent(endpoint, k -> new TokenBucketLimiter(10, 10));
        }

        RateLimitResponse response = limiters.get(endpoint).allow(clientId);
        return response;
    }
}
