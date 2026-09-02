package Projects.twitterfeed;

import java.util.HashSet;
import java.util.Set;

public class User {
    private final String id;
    private final Set<User> following;

    public User(String id) {
        this.id = id;
        this.following = new HashSet<>();
    }

    public void addToFollowing(User user) {
        following.add(user);
    }

    public void removeFromFollowing(User user) {
        following.remove(user);
    }

    public Set<User> getFollowing() {
        return following;
    }

    public String getId() {
        return id;
    }
}
