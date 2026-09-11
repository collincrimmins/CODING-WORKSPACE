# Threads
thread.start()
thread.sleep(1000);
thread.join()           // Halts current thread until completed

thread.currentThread()
thread.getName()
thread.setName()

thread.setDaemon(true)  // JVM exits automatically when only daeman threads remain

thread.interrupt()
thread.isInterrupted()

thread.wait()           // current thread will wait until another thread invokes notify()
    private Object lock = new Object();
    lock.wait() // wait for other to release
    lock.notify() // wake other thread
thread.notify()
thread.notifyAll()

# Runnable
Class Example
    class MyRunnable implements Runnable {
        @Override
        public void run() {}
    }
    MyRunnable runnable = new MyRunnable();
    Thread thread1 = new Thread(runnable, "Worker-Thread-1");
    thread1.start();

Runnable task = () -> System.out.println("Running on: " + Thread.currentThread().getName());
Thread thread = new Thread(task, "Worker-1");
thread.start(); // Always call start(), NOT run()

# Thread Lifecycle
States: NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED

# Executors & Thread Pools
// Core Pool Types
ExecutorService fixedPool   = Executors.newFixedThreadPool(NUM_CORES);  // CPU-bound
ExecutorService cachedPool  = Executors.newCachedThreadPool();         // Short/bursty tasks
    Threads are dynamically created
    Threads terminated after 60 seconds of inactivity
ExecutorService singleThread = Executors.newSingleThreadExecutor();     // Sequential task execution
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

// Submit & Execute
executorService.submit(new WorkerThread());
execute() = accepts Runnable() doesnt return anything
submit() = accepts Runnable() and Callable() and returns Future<?>

Rejection Policies
- AbortPolicy() - default, throws RejectedExecutionException
- CallerRunsPolicy: Executes task in the submission thread, slowing down caller input.  
- DiscardPolicy: Silently drops the task.
- DiscardOldestPolicy: Drops the oldest unhandled task in queue and tries again.

Thread Starvation = threads are unable to gain regular access to shared resources and make progress (fix with threadpools)

// ThreadPoolExecutor Direct Setup
ThreadPoolExecutor customExecutor = new ThreadPoolExecutor(
    2, 4,                       // corePoolSize, maxPoolSize
    10, TimeUnit.SECONDS,        // keepAliveTime for idle non-core threads
    new ArrayBlockingQueue<>(2), // Work Queue
    new ThreadPoolExecutor.CallerRunsPolicy() // Rejection Policy
);

// Scheduled Pool: Delayed or periodic task execution
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

// delay, period, unit
scheduler.scheduleAtFixedRate(
    () -> System.out.println("Heartbeat, running every 1 second"), 
    0, 1, TimeUnit.SECONDS
);

// delay
scheduler.schedule(() -> {
    System.out.println("Executed after 3 seconds!");}, 
    3, TimeUnit.SECONDS
);

// Monitoring
ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
System.out.println("Active Threads: " + executor.getActiveCount());
System.out.println("Queued Tasks: " + executor.getQueue().size());

# synchronization & volatile
synchronized method
synchronied block (do unimportant work outside of the block)

volatile = a variable's value will be modified by multiple threads
    1) Visibility
        Its value is always read/written from main memory (not threads local cache)
    2) Ordering
        Operations on a volatile variable cannot be re-ordered relative to each other.
volatile use cases
- Flags & Status variables
- Singleton pattern
- Lightwight synchroniation 

# Thread Communication
lock.wait()
lock.notify() - a resource has become free
lock.notifyAll() - producer adds item to queue that multiple consumers must respond to

# Locks (Mutexes), Semaphores, AtomicInteger, Synchronizers
// Lock
private final Object lock = new Object();
synchronized(lock) {}

// ReentrantLock
ReentrantLock lock = new ReentrantLock();
ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(); 
if (tryLock(2, TimeUnit.SECONDS)) {} - tries lock for X seconds

// ReadWriteLock (Multiple readers OR one writer)
ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
rwLock.readLock().lock();
rwLock.writeLock().lock();

// Lock Types
- coarse grained (one lock for everything)
- fine grained (per resource lock)
- read write (multiple readers OR one writer)

// AtomicInteger
AtomicInteger counter = new AtomicInteger(0); 
counter.get()
counter.incrementAndGet()

// Semaphore
Binary Semaphore = new Semaphore(1)
Counting Sempahor = (resourcePool = new Semaphore(3))
    Sempahor.acquire();  // Block if no permits available
    try {
        doWork();
    } finally {
        Sempahor.release();  // Always release, even on exception
    }

// Latches & Barriers
CountDownLatch latch = new CountDownLatch(3); // latch.countDown(); latch.await();
CyclicBarrier barrier = new CyclicBarrier(3);  // barrier.await();

# Condition variables: Condition variables let threads wait efficiently for a condition to become true
synchronized (lock) {
    while (!condition) {
        lock.wait();  // Release lock and sleep
    }
    // Condition is now true
}

# Concurrent Collections
// HashMap
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
    map.putIfAbsent(key, value)
    map.computeIfAbsent(key, k -> new createObject(k))

// List
CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

// HashSet
CopyOnWriteArraySet<Integer> list = new CopyOnWriteArraySet<>();

// Queue
// ArrayBlockingQueue (Fixed)
// LinkedBlockingQueue (Optional Fixed/Infinite)
// PriorityBlockingQueue
// DelayQueue
BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
    queue.put("item");          // Blocks if queue is full
    String item = queue.take(); // Blocks if queue is empty

    // BlockingQueue Producer: API method
    public void handleSignup(User user) {
        saveUser(user);
        queue.put(new EmailTask(user)); // blocks if queue is full
    }

    // BlockingQueueConsumer: Worker Thread
    while (true) {
        Task task = queue.take(); // blocks if queue is empty, zero CPU
        // do stuff
    }

# Future<>
// Future
Future<Integer> future = executor.submit(() -> 10 + 20);
    Integer result = future.get();  // blocks until result is ready
    System.out.println("Result: " + result);  // Result: 30

future.get()        // get value (blocks until value is ready)
future.isDone()
future.cancel(true) // interrupt the thread

// CompletableFuture (Async / Non-blocking chaining)
CompletableFuture.supplyAsync(() -> "Hello")
    .thenApply(s -> s + " World")
    .thenAccept(System.out::println);

future.get()        // non blocking
.thenApply()        // chaining completablefuture
.thenApplyAsync()

# Shutdown Pattern
executor.shutdown(); // Stop accepting new tasks
try {
    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        executor.shutdownNow(); // Cancel currently executing tasks
    }
} catch (InterruptedException ie) {
    executor.shutdownNow();
}

# Key Concurrency Pitfalls
- Check-then-Act: Testing a condition and acting without synchronization (race condition).
    if (!map.containsKey(key)) {
        map.put(key, new Value()); // Race condition: another thread may put a value first
    }
- Read-Modify-Write: Non-atomic composite updates (`count++`).
    count = count + 1 => read count, add +1, write to count
- Coordination: Threads must handoff/wait for work
    // PITFALL: Busy-spinning consumes 100% CPU
    while (!ready) { 
        // waiting...
    }

    // FIX: Using a CountdownLatch or BlockingQueue for handoff
    CountDownLatch latch = new CountDownLatch(1);
    // Consumer waits efficiently without pinning the CPU
    latch.await();
- Thread Starvation: Low-priority threads blocked indefinitely from resources.
- Deadlock: Two threads waiting for locks held by each other.

# Links
HelloInterview
https://codewitharyan.com/system-design/low-level-design 