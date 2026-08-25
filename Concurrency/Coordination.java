import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*

Strategies
1) Shared State Coordination
- BlockingQueues
- Producers & Consumers
2) Message Passing Coordination
- Actors own their data and communicate through messages

*/


// Wait/Notify (Condition Variables)
class Coordination {
    /* 
    synchronized (lock) {
        while (!conditionIsMet()) {
            lock.wait();  // Releases lock, sleeps until notified
        }
        doWork();
        lock.notifyAll();  // Wakes all waiting threads
    }
    */
}

// Blocking Queues
class TaskScheduler {
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1000);

    public void submitTask(Runnable task) throws InterruptedException {
        queue.put(task);
    }

    public void workerLoop() throws InterruptedException {
        while (true) {
            Runnable task = queue.take();
            task.run();
        }
    }
}

// Actor Model: object w/ Mailbox & Message Handler
// if your problem is "process these tasks in the background," use a blocking queue
// If your problem is "coordinate many independent entities with their own state," consider actors.
abstract class Actor<T> {
    private final BlockingQueue<T> mailbox = new LinkedBlockingQueue<>();
    private volatile boolean running = true;

    public Actor() {
        Thread thread = new Thread(() -> {
            while (running) {
                try {
                    T message = mailbox.take();
                    onReceive(message);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        thread.start();
    }

    public void send(T message) {
        mailbox.offer(message);
    }

    protected abstract void onReceive(T message);

    public void stop() {
        running = false;
    }
}
/*
public class EmailActor extends Actor<EmailRequest> {
    private final EmailClient emailClient = new EmailClient();

    @Override
    protected void onReceive(EmailRequest request) {
        emailClient.send(request.to(), request.subject(), request.body());
    }
}

// Usage: no shared state, no locks needed
public class SignupHandler {
    private final EmailActor emailActor = new EmailActor();
    private final UserRepository userRepository;

    public void handleSignup(SignupRequest request) {
        User user = userRepository.save(new User(request.email()));

        // Send message to actor - returns immediately
        emailActor.send(new EmailRequest(
            user.email(),
            "Welcome!",
            "Thanks for signing up..."
        ));
    }
}
*/

// Common Pattern: Process Request Asynchronously
/*
class EmailService {
    private final BlockingQueue<EmailTask> emailQueue =
        new LinkedBlockingQueue<>(10000);

    // API handler (producer)
    public void signup(String email, String name) throws InterruptedException {
        // Fast: Save user to database
        userRepository.save(email, name);

        // Fast: Enqueue background work
        emailQueue.put(new EmailTask(email, "welcome", name));

        // Return immediately - user sees instant response
    }

    // Worker thread (consumer)
    public void emailWorker() throws InterruptedException {
        while (true) {
            EmailTask task = emailQueue.take();
            // Slow: Connect to email server and send
            emailClient.send(task.recipient, task.template, task.data);
        }
    }
}
*/
/*
Common Examples
- S3 Image Upload Processing
- Payment Processing on checkout
- PDF Report Generation
*/

// Common Pattern: Handle Bursty Traffic
/*
public class TicketService {
    // Sized for 10-second burst at 10,000 req/s
    private final BlockingQueue<PurchaseRequest> purchaseQueue =
        new LinkedBlockingQueue<>(100000);

    // API handler (producer) - handles bursts
    public void purchaseTicket(String userId, String eventId, int quantity) throws InterruptedException {
        PurchaseRequest request = new PurchaseRequest(userId, eventId, quantity);

        // Enqueue request - returns immediately even during spike
        if (!purchaseQueue.offer(request, 100, TimeUnit.MILLISECONDS)) {
            throw new ServiceUnavailableException("Too many requests, try again");
        }
    }

    // Worker pool sized for normal load (100 workers)
    public void purchaseWorker() throws InterruptedException {
        while (true) {
            PurchaseRequest request = purchaseQueue.take();
            // Process at normal rate - database, payment, inventory
            processPurchase(request);
        }
    }
}
*/