package Projects.ratelimiter._firstRateLimiter.Limiters;

import Projects.ratelimiter._firstRateLimiter.RateLimitResponse;

public interface Limiter {
    public RateLimitResponse allow(String key);
}
