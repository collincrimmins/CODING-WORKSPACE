package Projects.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    /*
        Prompt: Design a logging service. Or call it a logger, whichever you prefer.

        Requirements:
        - Users can add to the log
        - Severity Levels: DEBUG, INFO, WARN, ERROR, FATAL
        - Logger writes each record to one or more destinations (fan-out) to types (file/console), set at startup.
        - Each destination has its own min-level threshold and its own format.
            Format and destination type vary independently.
        - Concurrent calls are safe. A record's bytes never interleave with
            another record's bytes on the same destination.

        Entities:
        - logger (service)
        - log (timestamp, level, message)
        - sink (file or console)
        - loggerlevel (debug, info, ...)
        - format (plaintext)

    */

    public static void main(String[] args) throws IOException {
        String LogAll = "C:\\Users\\colli\\Documents\\Software Projects\\LEETCODE JAVA\\Projects\\logger\\logsExample\\LogsAll.log";
        String LogError = "C:\\Users\\colli\\Documents\\Software Projects\\LEETCODE JAVA\\Projects\\logger\\logsExample\\LogsError.log";

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
        //logger.info("hello this is info");

        // Threads
        Thread thread1 = new Thread(() -> {
            logger.info("ThreadTest Thread 1");
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            logger.info("ThreadTest Thread 2");
            logger.error("Error!!!");
        });
        thread2.start();
    }
}
