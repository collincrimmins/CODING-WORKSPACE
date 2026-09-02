package Projects.pubsub_drivers;

public abstract class Subscriber {
    User user;

    public Subscriber(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public abstract void publish(Topic topic, Message message);
}
