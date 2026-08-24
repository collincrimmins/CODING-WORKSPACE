package LLD.Projects.parkinggarage.notifications;

public interface Notifications {
    void attach(NotificationObservor observor);
    void detach(NotificationObservor observor);
    void notifyObservors(String message);
}