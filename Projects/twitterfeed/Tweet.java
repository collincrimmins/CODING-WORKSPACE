package Projects.twitterfeed;

import java.time.Instant;
import java.util.UUID;

public class Tweet {
    private final String id;
    private final User user;
    private final String text;
    private final Instant createdAt;

    public Tweet(User user, String text) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.text = text;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void printString() {
        System.out.println("Tweet [" + user.getId() + ", text=" + text + ", createdAt=" + createdAt + "]");
    }
}
