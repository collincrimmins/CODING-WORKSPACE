package LLD.Projects.parkinggarage.notifications.Concrete;

import java.util.ArrayList;
import java.util.List;

import LLD.Projects.parkinggarage.notifications.NotificationObservor;
import LLD.Projects.parkinggarage.notifications.NotificationService;

public class TextNotification implements NotificationObservor {

    @Override
    public void update(String text) {
        //System.out.println("[Text Notification] " + text);
    }
   
}
