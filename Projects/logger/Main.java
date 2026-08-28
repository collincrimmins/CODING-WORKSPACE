package Projects.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
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

        - Question: "How would you make log() non-blocking?"
            BlockingQueue in Destination, so that a worker is asynchronously working through the queue.
            Adding to the Queue is instant.

        - Question: "How would you support hierarchical named loggers?"
        Answer: We add a "name" field to our LoggerService, and then use a Factory.
            LoggerFactory.getLogger("com.app.service.payments")

    */

    public static void main(String[] args) throws IOException {
        String LogAll = "C:\\Users\\colli\\Documents\\Software Projects\\CODING-WORKSPACE\\Projects\\logger\\logsExample\\LogsAll.log";
        String LogError = "C:\\Users\\colli\\Documents\\Software Projects\\CODING-WORKSPACE\\Projects\\logger\\logsExample\\LogsError.log";

        List<Destination> dests = new ArrayList<>();

        // All & File
        Destination dest1 = new Destination(new PlainTextFormatter(), LogLevel.DEBUG, new SinkFile(LogAll));
        dests.add(dest1); 

        // All & Console
        Destination dest3 = new Destination(new PlainTextFormatter(), LogLevel.DEBUG, new SinkConsole());
        dests.add(dest3); 

        // Error & File
        Destination dest2 = new Destination(new PlainTextFormatter(), LogLevel.ERROR, new SinkFile(LogError));
        dests.add(dest2);

        // Logger
        Logger logger = new Logger(dests);
        
        // Severity ERROR(4) will show up in both LogAll and LogError
        logger.error("hello this is info");

        // Threads
        Thread thread1 = new Thread(() -> {
            logger.info("ThreadTest Thread 1");
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            logger.info("ThreadTest Thread 2");
        });
        thread2.start();
    }
}
