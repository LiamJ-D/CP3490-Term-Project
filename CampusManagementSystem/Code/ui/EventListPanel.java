package ui;
import model.Event;
import service.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventListPanel extends JPanel {

    private EventManager eventManager;
    private RoomManager roomManager;
    private FeedbackManager feedbackManager;
    private NotificationService notificationService;
    private MainFrame mainFrame;

    private JTable table;
    private DefaultTableModel tableModel;

    public EventListPanel(EventManager eventManager, RoomManager roomManager,
                          FeedbackManager feedbackManager, NotificationService notificationService,
                          MainFrame mainFrame) {
        this.eventManager = eventManager;
        this.roomManager = roomManager;
        this.feedbackManager = feedbackManager;
        this.notificationService = notificationService;
        this.mainFrame = mainFrame;

        initUI();
        loadTable();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("Events", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID","Title","Start","End","Room"},0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Create Event");
        JButton btnEdit = new JButton("View/Edit Event");
        JButton btnBack = new JButton("Back");

        btnAdd.addActionListener(e -> new CreateEventDialog(eventManager, roomManager, notificationService, this).setVisible(true));
        btnEdit.addActionListener(e -> viewSelectedEvent());
        btnBack.addActionListener(e -> mainFrame.switchPanel(new DashboardPanel(mainFrame.userManager.getAdmin(), eventManager, roomManager, feedbackManager, notificationService, mainFrame)));

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnBack);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public void loadTable() {
        tableModel.setRowCount(0);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<Event> events = eventManager.getEvents();
        for (Event e : events) {
            tableModel.addRow(new Object[]{
                    e.getId(),
                    e.getTitle(),
                    e.getStart().format(f),
                    e.getEnd().format(f),
                    e.getRoomId()
            });
        }
    }

    private void viewSelectedEvent() {
        int idx = table.getSelectedRow();
        if (idx >= 0) {
            String id = (String) table.getValueAt(idx,0);
            Event e = eventManager.getEvents().stream().filter(ev -> ev.getId().equals(id)).findFirst().orElse(null);
            if (e != null) {
                new EventDetailsDialog(e, eventManager, roomManager, feedbackManager, notificationService, this).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this,"Please select an event","Warning",JOptionPane.WARNING_MESSAGE);
        }
    }
}
