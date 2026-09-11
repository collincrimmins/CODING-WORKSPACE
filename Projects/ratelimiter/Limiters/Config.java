package Projects.ratelimiter.Limiters;

public abstract class Config {
    private final String endpoint;
    private final String algorithm;

    public Config(String endpoint, String algorithm) {
        this.endpoint = endpoint;
        this.algorithm = algorithm;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
