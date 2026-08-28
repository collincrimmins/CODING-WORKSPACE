package Projects.logger;

import java.time.Instant;

public class Log {
    private final Instant timestamp;
    private final String text;
    private final String threadName;
    private final LogLevel logLevel;
    
    public Log(LogLevel logLevel, String text) {
        // Inputs
        this.logLevel = logLevel;
        this.text = text;

        // Set Automatically
        this.timestamp = Instant.now();
        this.threadName = Thread.currentThread().getName();
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public String getThreadName() {
        return threadName;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }
}
