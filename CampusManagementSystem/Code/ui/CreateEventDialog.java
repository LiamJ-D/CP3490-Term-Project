package ui;
import model.Event;
import service.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class CreateEventDialog extends JDialog {

    private JTextField txtTitle, txtStart, txtEnd, txtRoom, txtDesc, txtCapacity;
    private JButton btnSave, btnCancel;

    private EventManager eventManager;
    private RoomManager roomManager;
    private NotificationService notificationService;
    private EventListPanel parent;

    public CreateEventDialog(EventManager eventManager, RoomManager roomManager, NotificationService notificationService, EventListPanel parent) {
        this.eventManager = eventManager;
        this.roomManager = roomManager;
        this.notificationService = notificationService;
        this.parent = parent;

        setTitle("Create Event");
        setModal(true);
        setSize(400,400);
        setLocationRelativeTo(parent);

        initUI();
    }

    private void initUI() {
        setLayout(new GridLayout(7,2,5,5));

        txtTitle = new JTextField();
        txtStart = new JTextField("yyyy-MM-dd HH:mm");
        txtEnd = new JTextField("yyyy-MM-dd HH:mm");
        txtRoom = new JTextField();
        txtDesc = new JTextField();
        txtCapacity = new JTextField();

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");

        btnSave.addActionListener(e -> saveEvent());
        btnCancel.addActionListener(e -> dispose());

        add(new JLabel("Title:")); add(txtTitle);
        add(new JLabel("Start (yyyy-MM-dd HH:mm):")); add(txtStart);
        add(new JLabel("End (yyyy-MM-dd HH:mm):")); add(txtEnd);
        add(new JLabel("Room ID:")); add(txtRoom);
        add(new JLabel("Capacity:")); add(txtCapacity);
        add(new JLabel("Description:")); add(txtDesc);
        add(btnSave); add(btnCancel);
    }

    private void saveEvent() {
        try {
            String id = UUID.randomUUID().toString();
            String title = txtTitle.getText();
            String desc = txtDesc.getText();
            String roomId = txtRoom.getText();
            int capacity = Integer.parseInt(txtCapacity.getText());

            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime start = LocalDateTime.parse(txtStart.getText(), f);
            LocalDateTime end = LocalDateTime.parse(txtEnd.getText(), f);

            if (roomManager.findRoom(roomId) == null) {
                JOptionPane.showMessageDialog(this,"Room does not exist","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

            Event e = new Event();
            e.setId(id);
            e.setTitle(title);
            e.setDescription(desc);
            e.setStart(start);
            e.setEnd(end);
            e.setRoomId(roomId);
            e.setCapacity(capacity);
            e.setOrganizerId("admin");

            if (eventManager.hasConflict(e)) {
                JOptionPane.showMessageDialog(this,"Room is already booked for this time","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

            eventManager.addEvent(e);
            notificationService.notify("Event created: " + title);
            parent.loadTable();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,"Invalid input","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
