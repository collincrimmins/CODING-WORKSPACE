package Projects.urlshortener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UrlShortenerService {
    private final Map<String, URL> database;
    private final StrategyURL strategy;

    public UrlShortenerService(StrategyURL strategy) {
        this.database = new ConcurrentHashMap<>();
        this.strategy = strategy;
    }

    public String encodeURL(String originalURL, String customAlias, int expirationDays) {
        if (originalURL == null || originalURL.isBlank()) {
            throw new RuntimeException("invalid url");
        }

        String key;
        if (customAlias != null) {
            // custom alias
            if (database.containsKey(customAlias)) {
                throw new RuntimeException("custom alias already exists");
            }

            key = customAlias;
        } else {
            // generate shortened url
            key = strategy.generateKey(originalURL);
        }

        URL url = new URL(key, originalURL, expirationDays);
        database.put(url.getShortKey(), url);

        return url.getShortKey();
    }

    public String decodeURL(String shortURL) {
        URL url = database.get(shortURL);
        
        if (url == null) {
            throw new IllegalArgumentException("Short URL does not exist: " + shortURL);
        }

        if (url.isExpired()) {
            database.remove(shortURL); // Lazy eviction on access
            throw new IllegalArgumentException("URL is expired: " + shortURL);
        }

        url.incrementClicks();

        return url.getOriginalKey();
    }

    public URL getURLObject(String shortURL) {
        return database.get(shortURL);
    }

    public void printAnalytics(String shortURL) {
        if (database.containsKey(shortURL)) {
            System.out.println("clicks: " + database.get(shortURL).getClickCount());
        }
    }
}
