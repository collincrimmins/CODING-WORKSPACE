package Projects.logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Destination {
    private final Formatter formatter;
    private final LogLevel logLevel;
    private final Sink sink;
    //private final ReentrantLock lock;

    private final BlockingQueue<String> logQueue;
    private final Thread workerThread;
    private volatile boolean running = true;

    public Destination(Formatter formatter, LogLevel logLevel, Sink sink) {
        this.formatter = formatter;
        this.logLevel = logLevel;
        this.sink = sink;
        this.logQueue = new ArrayBlockingQueue<>(100);
        //this.lock = new ReentrantLock();

        // Workers
        this.workerThread = new Thread(this::processQueue, "LogWorker-" + sink.getClass().getSimpleName());
        this.workerThread.setDaemon(true); // Wont block JVM from shutting down when application finishes
        this.workerThread.start();

        //System.out.println(this.workerThread.getName());
    }

    public void write(Log log) {
        if (!log.getLogLevel().isAtleastSeverity(logLevel)) {
            return;
        }

        String formattedText = formatter.formatLogText(log);

        if (!logQueue.offer(formattedText)) {
            System.err.println("Logging queue full. Dropped: " + formattedText);
        }
        
        // lock.lock();
        // try {
        //     sink.write(formattedText);
        // } catch (Exception e) {
        //     System.err.println("error in logging: " + e.getMessage());
        // } finally {
        //     lock.unlock();
        // }
    }

    private void processQueue() {
        while (running || !logQueue.isEmpty()) {
            try {
                String string = logQueue.take();
                sink.write(string);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("Error writing log to destination: " + e.getMessage());
            }
        }
    }

    public void stop() {
        this.running = false;
        this.workerThread.interrupt(); // Interrupt take() if blocked
        try {
            this.workerThread.join(); // Wait for the worker to finish processing remaining items
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}