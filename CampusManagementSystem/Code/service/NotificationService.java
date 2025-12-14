package service;
import javax.swing.*;

public class NotificationService {

    public void notify(String message) {
        JOptionPane.showMessageDialog(null, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    public void eventReminder(String eventTitle) {
        notify("Reminder: " + eventTitle + " is starting soon.");
    }

    public void eventUpdated(String eventTitle) {
        notify("Event updated: " + eventTitle);
    }

    public void eventCancelled(String eventTitle) {
        notify("Event cancelled: " + eventTitle);
    }
}
