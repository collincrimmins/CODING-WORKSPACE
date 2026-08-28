package Projects.logger;

public class PlainTextFormatter implements Formatter {
    @Override
    public String formatLogText(Log log) {
        return log.getTimestamp() + " [" + log.getLogLevel()
         + "] [" +  log.getThreadName() + "] " + log.getText();
    }
    
}
