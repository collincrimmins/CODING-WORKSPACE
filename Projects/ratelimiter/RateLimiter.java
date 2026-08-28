package Projects.ratelimiter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Projects.ratelimiter.Limiters.Limiter;
import Projects.ratelimiter.Limiters.LimiterFactory;

public class RateLimiter {
    Map<String, Limiter> limiters;

    public RateLimiter(List<Map<String, Object>> configs) {
        // Create List of Limiters
        this.limiters = new HashMap<>();
        LimiterFactory factory = new LimiterFactory();

        // Add Configurations to List
        for (Map<String, Object> config : configs) {
            System.out.println(config.toString());
            String algo = (String) config.get("algorithm");
            String endpoint = (String) config.get("endpoint");
            Limiter limiter = factory.create(config);
            limiters.put(endpoint, limiter);
        }
    }

    public RateLimitResponse allow(String clientId, String endpoint) {
        RateLimitResponse response = limiters.get(endpoint).allow(clientId);
        return response;
    }
}
