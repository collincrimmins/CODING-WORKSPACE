package Projects.parkinggarage.notifications.Concrete;

import Projects.parkinggarage.notifications.NotificationObservor;

public class EmailNotification implements NotificationObservor {

    @Override
    public void update(String text) {
        //System.out.println("[Email Notification] " + text);
    }
   
}
