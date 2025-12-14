import service.*;
import ui.MainFrame;

import javax.swing.*;

public class CampusEventApp {

    public static void main(String[] args) {

        UserManager userManager = new UserManager();
        EventManager eventManager = new EventManager();
        RoomManager roomManager = new RoomManager();
        FeedbackManager feedbackManager = new FeedbackManager();
        NotificationService notificationService = new NotificationService();

        if (roomManager.getRooms().isEmpty()) {
            roomManager.saveRooms(service.XMLDefaults.defaultRooms());
        }

        if (eventManager.getEvents().isEmpty()) {
            eventManager.saveEvents(XMLDefaults.defaultEvents());
        }

        if (feedbackManager.getFeedback().isEmpty()) {
            feedbackManager.saveFeedback(XMLDefaults.defaultFeedback());
        }

        if (userManager.getAllUsers().isEmpty()) {
            userManager.saveUsers(XMLDefaults.defaultUsers());
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(userManager, eventManager, roomManager, feedbackManager, notificationService);
            frame.setVisible(true);
        });
    }
}
