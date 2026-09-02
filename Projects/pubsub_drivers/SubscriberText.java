package Projects.pubsub_drivers;

public class SubscriberText extends Subscriber {
    public SubscriberText(User user) { 
        super(user);
    }

    @Override
    public void publish(Topic topic, Message message) {
        System.out.println(topic.getName() + " - [Text to " + user.getId() + ": " + this.getUser().getPhoneNumber() + "] " + message.getText());
    }
    
}
