package Projects.logger;

import java.util.concurrent.locks.ReentrantLock;

public class Destination {
    private final Formatter formatter;
    private final LogLevel logLevel;
    private final Sink sink;
    private final ReentrantLock lock;

    public Destination(Formatter formatter, LogLevel logLevel, Sink sink) {
        this.formatter = formatter;
        this.logLevel = logLevel;
        this.sink = sink;
        this.lock = new ReentrantLock();
    }

    public void write(LogRecord logRecord) {
        if (!logRecord.getLogLevel().isAtleastSeverity(logLevel)) {
            return;
        }
        
        lock.lock();
        try {
            String formattedText = formatter.formatLogText(logRecord);
            sink.write(formattedText);
        } catch (Exception e) {
            System.err.println("error in logging: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}