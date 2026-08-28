package Projects.ratelimiter.Limiters;

import Projects.ratelimiter.RateLimitResponse;

public interface Limiter {
    public RateLimitResponse allow(String key);
}
