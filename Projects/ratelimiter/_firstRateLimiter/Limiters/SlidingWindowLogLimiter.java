package Projects.ratelimiter._firstRateLimiter.Limiters;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import Projects.ratelimiter._firstRateLimiter.RateLimitResponse;

public class SlidingWindowLogLimiter implements Limiter {
    private final int maxRequests;
    private final Long windowMs;
    private final Map<String, RequestLog> logs = new HashMap<>();

    public SlidingWindowLogLimiter(int maxRequests, long windowMs) {
        this.maxRequests = maxRequests;
        this.windowMs = windowMs;
        System.out.println(maxRequests);
        System.out.println(windowMs);
    }

    @Override
    public RateLimitResponse allow(String key) {
        RequestLog log = logs.computeIfAbsent(key, k -> new RequestLog());

        synchronized(log) {
            long now = System.currentTimeMillis();
            long cutoff = now - windowMs;

            while (!log.timestamps.isEmpty() && log.timestamps.peekFirst() < cutoff) {
                log.timestamps.pollFirst();
            }

            if (log.timestamps.size() < maxRequests) {
                log.timestamps.addLast(now);
                int remaining = maxRequests - log.timestamps.size();
                return new RateLimitResponse(true, remaining, 0);
            }

            long oldestTimestamp = log.timestamps.peekFirst();
            long retryAfterMs = (oldestTimestamp + windowMs) - now;
            return new RateLimitResponse(false, 0, retryAfterMs);
        }
    }

    private static class RequestLog {
        private final Deque<Long> timestamps = new ArrayDeque<>();
    }
}
