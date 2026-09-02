package Projects.pubsub_drivers;

public class SubscriberEmail extends Subscriber {
    public SubscriberEmail(User user) { 
        super(user);
    }

    @Override
    public void publish(Topic topic, Message message) {
        System.out.println(topic.getName() + " - [Email to " + user.getId() + ": " + this.getUser().getEmail() + "] " + message.getText());
    }
    
}
