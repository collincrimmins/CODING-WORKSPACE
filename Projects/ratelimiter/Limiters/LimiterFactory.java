package Projects.ratelimiter.Limiters;

public class LimiterFactory {
    public static Limiter create(Config config) {
        String algorithm = config.getAlgorithm();

        if (algorithm.equals("TokenBucket")) {
            ConfigTokenBucket settings = (ConfigTokenBucket) config;
            int maxTokens = settings.getMaxTokens();
            int tokensRefillPerSecond = settings.getRefillTokensPerSecond();
            return new LimiterTokenBucket(maxTokens, tokensRefillPerSecond);
        }

        throw new RuntimeException("Unknown algorithm");
        
    }
}
