package Projects.ratelimiter._firstRateLimiter.Limiters;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Projects.ratelimiter._firstRateLimiter.RateLimitResponse;

/*
    Concurrency
    1) synchronized(myBucket) locks my bucket during runtime
    2) ConcurrentHashMap<> because 2 threads could call .allow() simultaneously on the same Key "Client1"
        & computeIfAbsent() is atomic
*/

public class TokenBucketLimiter implements Limiter {
    private final int maxTokens;
    private final int refillTokensPerSecond;
    private final ConcurrentHashMap<String, TokenBucket> buckets;

    public TokenBucketLimiter(int maxTokens, int refillTokensPerSecond) {
        this.maxTokens = maxTokens;
        this.refillTokensPerSecond = refillTokensPerSecond;
        this.buckets = new ConcurrentHashMap<>();
    }

    @Override
    public RateLimitResponse allow(String key) {
        // Get Client Bucket
        // computeIfAbsent() is Atomic
        TokenBucket myBucket = buckets.computeIfAbsent(key, 
            k -> new TokenBucket(maxTokens, System.currentTimeMillis()));

        // Lock on myBucket (Accessed using my ClientID "Client1")
        synchronized (myBucket) {
            // Add to Tokens (using Last Time and refill per second)
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - myBucket.lastTime;
            double newTokensToAdd = (elapsedTime * refillTokensPerSecond) / 1000.0;
            myBucket.tokens = Math.min(maxTokens, myBucket.tokens + newTokensToAdd);
            myBucket.lastTime = currentTime;
            // System.out.println("New tokens since last: " + newTokensToAdd);

            // Subtract from Tokens
            if (myBucket.tokens > 0) {
                myBucket.tokens = myBucket.tokens - 1;
                myBucket.lastTime = System.currentTimeMillis();
                int remainingTokens = (int) myBucket.tokens;
                long retryTime = 0;
                return new RateLimitResponse(true, remainingTokens, retryTime);
            }

            // Invalid Tokens
            double tokensNeeded = 1 - myBucket.tokens;
            long retryAfterMs = (long) Math.ceil((tokensNeeded * 1000) / refillTokensPerSecond);
            return new RateLimitResponse(false, 0, retryAfterMs);
        }
    }
    
    private class TokenBucket {
        double tokens;
        long lastTime;

        public TokenBucket(double tokens, long lastTime) {
            this.tokens = tokens;
            this.lastTime = lastTime;
        }
    }
}
