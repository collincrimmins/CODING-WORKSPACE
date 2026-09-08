package Projects.urlshortener;

import java.time.Instant;

public class URL {
    private final String shortKey;
    private final String originalKey;
    private final Instant createdAt;
    private final Instant expiresAt;
    private long clickCount;

    public URL(String shortKey, String originalKey, int daysExpiration) {
        this.shortKey = "urlshortener.com/" + shortKey;
        this.originalKey = originalKey;
        this.createdAt = Instant.now();
        //this.expiresAt = Instant.now().plusSeconds(60 * 60 * 24 * daysExpiration);
        this.expiresAt = Instant.now().plusSeconds(1);
        this.clickCount = 0;
    }

    public synchronized void incrementClicks() {
        this.clickCount = this.clickCount + 1;
    }

    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }

    // Getters

    public String getShortKey() {
        return shortKey;
    }

    public String getOriginalKey() {
        return originalKey;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public long getClickCount() {
        return clickCount;
    }

}
