package Projects.inventory;

public class AlertConfig {
    int threshold;
    AlertListener listener;

    public AlertConfig(int threshold, AlertListener listener) {
        this.listener = listener;
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }

    public AlertListener getListener() {
        return listener;
    }
}
