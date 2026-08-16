package LLD.Projects.parkinggarage.notifications.Concrete;

import LLD.Projects.parkinggarage.notifications.NotificationObservor;

public class EmailNotification implements NotificationObservor {

    @Override
    public void update(String text) {
        System.out.println("[Email Notification] " + text);
    }
   
}
