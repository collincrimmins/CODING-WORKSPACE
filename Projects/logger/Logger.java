package Projects.logger;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    List<Destination> destinations;

    public Logger(List<Destination> list) {
        destinations = List.copyOf(list);
    }

    private void write(LogLevel logLevel, String text) {
        Log log = new Log(logLevel, text);

        for (Destination dest : destinations) {
            dest.write(log);
        }
    }

    public void stop() {
        for (Destination dest : destinations) {
            dest.stop();
        }
    }

    public void debug(String text) {
        write(LogLevel.DEBUG, text);
    }

    public void info(String text) {
        write(LogLevel.INFO, text);
    }

    public void warn(String text) {
        write(LogLevel.WARN, text);
    }

    public void error(String text) {
        write(LogLevel.ERROR, text);
    }
}
