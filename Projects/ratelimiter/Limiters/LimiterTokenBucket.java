package Projects.ratelimiter.Limiters;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Projects.ratelimiter.LimiterResponse;

public class LimiterTokenBucket implements Limiter {
    private int maxTokens;
    private int tokensRefillPerSecond;

    private final Map<String, TokenBucket> map;

    public LimiterTokenBucket(int maxTokens, int tokensRefillPerSecond) {
        this.maxTokens = maxTokens;
        this.tokensRefillPerSecond = tokensRefillPerSecond;
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public LimiterResponse allow(String clientId) {
        TokenBucket myBucket = map.computeIfAbsent(clientId, k -> new TokenBucket(maxTokens, System.currentTimeMillis()));

        synchronized(myBucket) {
            // Update Tokens
            Long currentTime = System.currentTimeMillis();
            Long timeElapsed = currentTime - myBucket.lastTime;
            double newTokens = (timeElapsed / 1000.00) * tokensRefillPerSecond;
            myBucket.tokens = Math.min(maxTokens, myBucket.tokens + newTokens);

            // Set Time
            myBucket.lastTime = System.currentTimeMillis();

            // Check if Valid Tokens
            if (myBucket.tokens > 0) {
                myBucket.tokens = myBucket.tokens - 1;
                return new LimiterResponse(true);
            }

            return new LimiterResponse(false);
        }
    }
    
    private static class TokenBucket {
        double tokens;
        Long lastTime;

        public TokenBucket(double tokens, Long lastTime) {
            this.tokens = tokens;
            this.lastTime = lastTime;
        }
    }
}
