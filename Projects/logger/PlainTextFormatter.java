package Projects.logger;

public class PlainTextFormatter implements Formatter {
    @Override
    public String formatLogText(LogRecord logRecord) {
        return logRecord.getTimestamp() + " [" + logRecord.getLogLevel()
         + "] [" +  logRecord.getThreadName() + "] " + logRecord.getText();
    }
    
}
