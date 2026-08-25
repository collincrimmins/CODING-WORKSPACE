package LLD.Projects.parkinggarage.notifications;

import java.util.ArrayList;
import java.util.List;

public class NotificationService implements Notifications {
    private List<NotificationObservor> observors = new ArrayList<>();

    @Override
    public void attach(NotificationObservor observor) {
        observors.add(observor);
    }

    @Override
    public void detach(NotificationObservor observor) {
        observors.remove(observor);
    }

    @Override
    public void notifyObservors(String message) {
        for (NotificationObservor observor : observors) {
            observor.update(message);
        }
    }
    
}
