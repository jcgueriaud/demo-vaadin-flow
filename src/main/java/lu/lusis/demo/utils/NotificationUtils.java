package lu.lusis.demo.utils;

import com.vaadin.flow.component.notification.Notification;

public class NotificationUtils {

    public static void showError(String message){
        Notification notification = new Notification(message);
        notification.getElement().setAttribute("theme","error");

        notification.open();
    }
}
