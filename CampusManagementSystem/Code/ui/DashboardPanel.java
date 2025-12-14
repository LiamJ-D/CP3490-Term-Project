package ui;
import model.User;
import model.Role;
import service.*;
import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private User user;
    private EventManager eventManager;
    private RoomManager roomManager;
    private FeedbackManager feedbackManager;
    private NotificationService notificationService;
    private MainFrame mainFrame;

    public DashboardPanel(User user, EventManager eventManager, RoomManager roomManager,
                          FeedbackManager feedbackManager, NotificationService notificationService,
                          MainFrame mainFrame) {

        this.user = user;
        this.eventManager = eventManager;
        this.roomManager = roomManager;
        this.feedbackManager = feedbackManager;
        this.notificationService = notificationService;
        this.mainFrame = mainFrame;

        initUI();
    }

    private void initUI() {

        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Welcome, " + user.getName(), JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitle, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 3, 10, 10));

        JButton btnEvents = new JButton("Manage Events");
        JButton btnRooms = new JButton("Rooms");
        JButton btnFeedback = new JButton("Feedback");
        JButton btnExit = new JButton("Exit");
        JButton btnUsers = new JButton("Manage Users");

        btnEvents.addActionListener(e -> mainFrame.switchPanel(
                new EventListPanel(eventManager, roomManager, feedbackManager, notificationService, mainFrame)));

        btnRooms.addActionListener(e -> mainFrame.switchPanel(
                new RoomListPanel(roomManager, mainFrame)));

        btnFeedback.addActionListener(e -> mainFrame.switchPanel(
                new FeedbackDialog(eventManager, feedbackManager, mainFrame)));

        btnUsers.addActionListener(e -> mainFrame.switchPanel(
                new ManageUsersPanel(mainFrame.userManager, mainFrame)));
        buttonsPanel.add(btnUsers);

        btnExit.addActionListener(e -> System.exit(0));

        buttonsPanel.add(btnEvents);
        buttonsPanel.add(btnRooms);
        buttonsPanel.add(btnFeedback);
        buttonsPanel.add(btnExit);

        add(buttonsPanel, BorderLayout.CENTER);
    }
}



