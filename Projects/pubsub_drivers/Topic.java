package Projects.pubsub_drivers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Topic {
    String name;
    Set<Subscriber> subscribers;
    Validator validator;
    //final ExecutorService executor;

    public Topic(String name, Validator validator) {
        this.name = name;
        this.subscribers = ConcurrentHashMap.newKeySet(); // concurrency safe
        this.validator = validator;
        //this.executor = executor;
    }

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber) {
       subscribers.remove(subscriber);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void publish(Message message) {
        // Check Message Exists
        if (message == null) {
            return;
        }

        // Check Message is Valid
        if (!validator.isValid(message)) {
            return;
        }

        // Publish to Subscribers (Async Executor)
        for (Subscriber sub : subscribers) {
            sub.publish(this, message);
            // executor.submit(() -> {
            //     try {
            //         sub.publish(this, message);
            //     } catch (Exception e) {
            //         System.err.println("Failed to deliver message to subscriber: " + e.getMessage());
            //     }
            // });
        }
    }
}
