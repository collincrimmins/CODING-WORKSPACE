package Projects.pubsub_drivers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class NotificationService {
    Map<String, User> users;
    Map<String, Topic> topics;
    final ExecutorService deliveryExecutor;

    public NotificationService() {
        this.users = new ConcurrentHashMap<>();
        this.topics = new ConcurrentHashMap<>();
        this.deliveryExecutor = Executors.newCachedThreadPool(); // A cached thread pool is suitable for handling many short-lived, bursty tasks (message deliveries).
        //this.deliveryExecutor = Executors.newScheduledThreadPool(3);
    }

    // --- Topics ---

    public void createTopic(String name) {
        Topic newTopic = new Topic(name, new ValidatorText(50), deliveryExecutor);
        Topic ExistingTopic = topics.putIfAbsent(name, newTopic); // Thread safe: check-then-act
        if (ExistingTopic != null) {
            System.out.println("[Error] Topic already exists: " + name);
        }

        // Check Topic Exists
        // if (topics.containsKey(name)) {
        //     System.out.println("[Error] Topic already exists: " + name);
        //     return;
        // }

        // // Create Topic
        // Topic topic = new Topic(name, new ValidatorText(50), deliveryExecutor);

        // // Add Topic to Map
        // topics.put(name, topic);
    }

    public void publishMessageToTopic(String name, Message message) {
        // Check Topic Exists
        if (!topics.containsKey(name)) {
            System.out.println("[Error] Topic does not exist: " + name);
            return;
        }

        // Publish to Topic
        Topic topic = topics.get(name);
        topic.publish(message);
    }

    // Subscribers
    public void addSubscriber(String name, Subscriber subscriber) {
        // Check Topic Exists
        if (!topics.containsKey(name)) {
            System.out.println("[Error] Topic does not exist: " + name);
            return;
        }

        // Add to Topic
        topics.get(name).addSubscriber(subscriber);
    }

    public void removeSubscriber(String name, Subscriber subscriber) {
        // Check Topic Exists
        if (!topics.containsKey(name)) {
            System.out.println("[Error] Topic does not exist: " + name);
            return;
        }

        topics.get(name).removeSubscriber(subscriber);
    }

    // --- Users ---
    
    public User addNewUser(User user) {
        if (users.containsKey(user.getId())) {
            System.out.println("[Error] User already exists: " + user.getId());
            return null;
        }

        // Add to Users List
        users.put(user.getId(), user);

        return user;
    }

    // Shutdown
    public void shutdown() {
        deliveryExecutor.shutdown();
    }
   
}
