package Projects.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class _Main {
    public static void main(String[] args) throws IOException {
        String LogAll = "C:\\Users\\colli\\Documents\\Software Projects\\CODING-WORKSPACE\\Projects\\logger\\logsExample\\LogsAll.log";
        String LogError = "C:\\Users\\colli\\Documents\\Software Projects\\CODING-WORKSPACE\\Projects\\logger\\logsExample\\LogsError.log";

        List<Destination> dests = new ArrayList<>();

        // All
        Destination dest1 = new Destination(new PlainTextFormatter(), LogLevel.DEBUG, new SinkFile(LogAll));
        dests.add(dest1); 
        Destination dest3 = new Destination(new PlainTextFormatter(), LogLevel.DEBUG, new SinkConsole());
        dests.add(dest3); 

        // Error
        Destination dest2 = new Destination(new PlainTextFormatter(), LogLevel.ERROR, new SinkFile(LogError));
        dests.add(dest2);

        // Logger
        Logger logger = new Logger(dests);
        
        // Severity ERROR(4) will show up in both LogAll and LogError
        for (int i = 1; i <= 1; i++) {
            logger.info("this is info");
            logger.debug("debug");
            logger.error("this is an error");
        }

        // Threads
        // Thread thread1 = new Thread(() -> {
        //     logger.error("ThreadTest Thread 1");
        //     logger.error("ThreadTest Thread 1");
        //     logger.error("ThreadTest Thread 1");
        // });
        // thread1.start();
        // Thread thread2 = new Thread(() -> {
        //     logger.error("ThreadTest Thread 2");
        //     logger.error("ThreadTest Thread 2");
        //     logger.error("ThreadTest Thread 2");
        // });
        // thread2.start();

        // Graceful shutdown
        logger.stop();
    }

    /*
        Prompt: Design a logging service. Or call it a logger, whichever you prefer.

        Requirements:
        - Log Types (INFO, WARN, ERROR) w/ increasing severity number
            Print to all Log Destination with atleast the same severity number
        - Output to different types (Console or FileWrite)
        - LogSystem has a List of all output destination created at instantation
        - Strategy Pattern: Multiple methods of formatting Log Text (Plaintext, json)

        Entities:
        - logger (service)
        - log (timestamp, level, message)
        - sink (file or console)
        - loggerlevel (debug, info, ...)
        - format (plaintext)

        Class Design

            interface Sink - SinkConsole, SinkFile
            + write()

            interface Formatter - FormatterPlainText, FormatterJSON
            + format(log) -> string

            enum LogLevel
            - INFO, WARN, ERROR

            class Log
            - String text
            - Instant timestamp
            - LogLevel severity

            class Destination
            - Sink sink
            - Formatter formatter
            - LogLevel minSeverity
            + write(log)

            class LoggerSystem
            - List<Destination> destinations
            + write(string, severity) // create new Log(), write to all destinations, the destination itself will check if this log severity belongs

        - Question: "How would you make log() non-blocking?"
            BlockingQueue in Destination, so that a worker is asynchronously working through the queue.
            Adding to the Queue is instant.

        - Question: "How would you support hierarchical named loggers?"
            Answer: We add a "name" field to our LoggerService, and then use a Factory.
            LoggerFactory.getLogger("com.app.service.payments")

    */
}
