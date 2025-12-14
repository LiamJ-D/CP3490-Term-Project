package ui;
import model.Event;
import service.*;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class EventDetailsDialog extends JDialog {

    private Event event;
    private EventManager eventManager;
    private RoomManager roomManager;
    private FeedbackManager feedbackManager;
    private NotificationService notificationService;
    private EventListPanel parent;

    private JTextArea txtInfo;

    public EventDetailsDialog(Event event, EventManager eventManager, RoomManager roomManager,
                              FeedbackManager feedbackManager, NotificationService notificationService,
                              EventListPanel parent) {
        this.event = event;
        this.eventManager = eventManager;
        this.roomManager = roomManager;
        this.feedbackManager = feedbackManager;
        this.notificationService = notificationService;
        this.parent = parent;

        setTitle("Event Details");
        setModal(true);
        setSize(400,400);
        setLocationRelativeTo(parent);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        add(new JScrollPane(txtInfo), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnDelete = new JButton("Delete");
        JButton btnClose = new JButton("Close");

        btnDelete.addActionListener(e -> {
            eventManager.deleteEvent(event.getId());
            notificationService.eventCancelled(event.getTitle());
            parent.loadTable();
            dispose();
        });

        btnClose.addActionListener(e -> dispose());

        btnPanel.add(btnDelete);
        btnPanel.add(btnClose);

        add(btnPanel, BorderLayout.SOUTH);

        loadEventInfo();
    }

    private void loadEventInfo() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(event.getTitle()).append("\n");
        sb.append("Description: ").append(event.getDescription()).append("\n");
        sb.append("Start: ").append(event.getStart().format(f)).append("\n");
        sb.append("End: ").append(event.getEnd().format(f)).append("\n");
        sb.append("Room: ").append(event.getRoomId()).append("\n");
        sb.append("Participants: ").append(event.getParticipants().size()).append("/").append(event.getCapacity()).append("\n");

        txtInfo.setText(sb.toString());
    }
}
