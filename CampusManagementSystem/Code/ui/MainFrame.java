package ui;
import model.User;
import service.*;
import javax.swing.*;


public class MainFrame extends JFrame {

    public User user;
    public UserManager userManager;
    public EventManager eventManager;
    public RoomManager roomManager;
    public FeedbackManager feedbackManager;
    public NotificationService notificationService;

    private JPanel currentPanel;

    public MainFrame(UserManager userManager, EventManager eventManager, RoomManager roomManager,
                     FeedbackManager feedbackManager, NotificationService notificationService) {
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.roomManager = roomManager;
        this.feedbackManager = feedbackManager;
        this.notificationService = notificationService;

        setTitle("Campus Event Management");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        showLogin();
    }

    private void showLogin() {
        LoginPanel loginPanel = new LoginPanel(userManager);
        setContentPane(loginPanel);
        loginPanel.setLoginListener(u -> {
            user = u;
            showDashboard();
        });
        revalidate();
        repaint();
    }

    public void showDashboard() {
        DashboardPanel dashboardPanel = new DashboardPanel(user, eventManager, roomManager,
                feedbackManager, notificationService, this);
        setContentPane(dashboardPanel);
        revalidate();
        repaint();
    }

    public void switchPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
    }
}
