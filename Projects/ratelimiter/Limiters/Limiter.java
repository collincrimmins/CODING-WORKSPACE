package Projects.ratelimiter.Limiters;

import Projects.ratelimiter.LimiterResponse;

public interface Limiter {
    public LimiterResponse allow(String clientId);
}
