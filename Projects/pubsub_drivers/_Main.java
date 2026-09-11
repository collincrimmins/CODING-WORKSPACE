package Projects.pubsub_drivers;

public class _Main {
    public static void main(String[] args) {
        NotificationService system = new NotificationService();

        // Bob
        User userBob = new User("Bob Smith", "123-123-1234", "bob.smith@gmail.com");
        system.addNewUser(userBob);
        Subscriber subscriberTextBob = new SubscriberText(userBob);

        // Sally
        User userSally = new User("Sally Clinton", "754-254-4234", "sally.clinton@gmail.com");
        system.addNewUser(userSally);
        Subscriber subscriberEmailSally = new SubscriberEmail(userSally);

        // Create Topic & Subscribers
        System.out.println("=== Topic Doesnt Exist:");
        system.addSubscriber("This-Topic-Doesnt-Exist", subscriberTextBob); // Error: Topic doesnt exist
        system.createTopic("Topic #1");
        System.out.println("=== Topic already Exist:");
        system.createTopic("Topic #1"); // Error: Topic already exists
        system.addSubscriber("Topic #1", subscriberTextBob);
        system.addSubscriber("Topic #1", subscriberEmailSally);

        // Publish Message
        System.out.println("=== Message too long:");
        Message messageTooLong = new Message("This message is toooooooooooooooooooooooooooooooooooooooong long!!!");
        system.publishMessageToTopic("Topic #1", messageTooLong); // Error too long (ValidatorText)
        System.out.println("=== Message to BOB and SALLY:");
        Message message1 = new Message("This is a test message!");
        system.publishMessageToTopic("Topic #1", message1); // Valid: Sends to multiple subscribers Bob & Sally

        // Remove Bob
        System.out.println("=== Message to BOB:");
        system.removeSubscriber("Topic #1", subscriberEmailSally);
        system.publishMessageToTopic("Topic #1", message1); // Valid: Only sends to Bob (sally was removed)

        // Multiple Topics
        System.out.println("=== Multiple Topics");
        system.createTopic("Delivery_Drivers");
        system.addSubscriber("Delivery_Drivers", subscriberTextBob);
        system.addSubscriber("Delivery_Drivers", subscriberEmailSally);
        Message message2 = new Message("Dear Delivery Drivers, you're all fired!");
        system.publishMessageToTopic("Delivery_Drivers", message2);

        // End Async Executor
        system.shutdown();
        System.out.println("delivered all async - you can use a delay also (ScheduledExecutorSerice)");
    }

    /*
        Prompt: Design a system for delivery drivers where drivers can subscribe to real time events. 
        There could be multiple topics and system should be able to deliver events to all drivers 
        whom have subscribed to specific topic / topic(s).
        There can be multiple custom validators with scope to add more.
        https://github.com/ashishps1/awesome-low-level-design/blob/main/solutions/java/src/pubsubsystem/README.md

        Design and implement a notification center system that can handle multiple types
         of notifications, manage delivery preferences, and provide real-time updates to users.
        https://www.hellointerview.com/community/questions/notification-center/cmgzv4hdy023z07adyhgoaozu 

        Requirements
        - Create multiple topics
        - Topics have subscribers (delivery drivers)
            Observor Pattern
        - Subscribers (delivery drivers) subscribe to Topics
        - Multiple Message Delivery Types (Console, Email, Text)
            Strategy Pattern
        - Custom Validators (middleware to verify message is valid, such as max string message length of 50)
        - Concurrency w/ 100k users
        - Asynchronous delivery

        Entities
        - NotificationService
        - User
        - Topic
        - Subscriber
            SubscriberText
            SubscriberEmail
        - Message
        - Validator
            ValidatorText

        Class Design

            NotificationService
            - Set<Users> users
            - Map<String, Topic> topics
            + createTopic(name, validatorType)
            + addSubscriber(user, topic, notifType)
            + removeSubscriber(user, topic)
            + publishMessageToTopic(topic, message)

            User
            - id (string)

            Topic
            - String name
            - Set<Subscribers> subscribers
            + publish(message)

            Subscriber Interface (SubscriberText / SubscriberEmail)
            + send()

            Message
            - String text

            Validator Interface (ValidatorText)
            + validate(message)
    
    */
}
