package Projects.uber;

import java.util.UUID;

public class User {
    private String userId;
    private String name;

    public User(String name) {
        this.userId = "USER-" + UUID.randomUUID().toString().substring(0, 8);
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
    
}
