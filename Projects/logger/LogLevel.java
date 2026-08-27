package Projects.logger;

public enum LogLevel {
    DEBUG(1),
    INFO(2),
    WARN(3),
    ERROR(4);
    
    private final int serverity;

    LogLevel(int serverity) {
        this.serverity = serverity;
    }

    public boolean isAtleastSeverity(LogLevel minimum) {
        return serverity >= minimum.serverity;
    }
}
