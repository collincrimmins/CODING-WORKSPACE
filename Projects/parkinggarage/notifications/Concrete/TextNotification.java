package Projects.parkinggarage.notifications.Concrete;

import java.util.ArrayList;
import java.util.List;

import Projects.parkinggarage.notifications.NotificationObservor;
import Projects.parkinggarage.notifications.NotificationService;

public class TextNotification implements NotificationObservor {

    @Override
    public void update(String text) {
        //System.out.println("[Text Notification] " + text);
    }
   
}
