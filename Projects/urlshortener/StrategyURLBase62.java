package Projects.urlshortener;

import java.util.concurrent.atomic.AtomicLong;

public class StrategyURLBase62 implements  StrategyURL {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final AtomicLong counter = new AtomicLong(1000000000L); // Start from offset for uniform length

    @Override
    public String generateKey(String originalURL) {
        long id = counter.getAndIncrement();
        return encodeBase62(id);
    }

    private String encodeBase62(long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(BASE62.charAt((int) (id % 62)));
            id /= 62;
        }
        return sb.reverse().toString();
    }

}
