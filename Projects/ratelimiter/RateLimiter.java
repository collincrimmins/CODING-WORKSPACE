package Projects.ratelimiter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Projects.ratelimiter.Limiters.Config;
import Projects.ratelimiter.Limiters.Limiter;
import Projects.ratelimiter.Limiters.LimiterFactory;
import Projects.ratelimiter.Limiters.LimiterTokenBucket;

public class RateLimiter {
    private final Map<String, Limiter> limiters;
    private final Limiter defaultLimiter;

    public RateLimiter(List<Config> list) {
        this.limiters = new ConcurrentHashMap<>();

        // Add configs
        for (Config config : list) {
            Limiter limiter = LimiterFactory.create(config);
            limiters.put(config.getEndpoint(), limiter);
        }

        // Default
        this.defaultLimiter = new LimiterTokenBucket(10, 10);
    }

    public LimiterResponse allow(String clientId, String endpoint) {
        Limiter limiter = limiters.getOrDefault(endpoint, defaultLimiter);
        LimiterResponse response = limiter.allow(clientId);
        return response;
    }




}
