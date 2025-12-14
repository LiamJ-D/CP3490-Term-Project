package ui;
import model.Room;
import service.RoomManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RoomListPanel extends JPanel {

    private RoomManager roomManager;
    private JTable table;
    private DefaultTableModel tableModel;
    private MainFrame mainFrame;

    public RoomListPanel(RoomManager roomManager, MainFrame mainFrame) {
        this.roomManager = roomManager;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Rooms", JLabel.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        add(lbl, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID","Capacity","Location","Projector","Whiteboard"},0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Add Room");
        JButton btnEdit = new JButton("Edit Room");
        JButton btnDelete = new JButton("Delete Room");
        JButton btnBack = new JButton("Back");

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnBack);
        add(btnPanel, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> mainFrame.switchPanel(new DashboardPanel(
                mainFrame.userManager.getAdmin(),
                mainFrame.eventManager,
                roomManager,
                mainFrame.feedbackManager,
                mainFrame.notificationService,
                mainFrame
        )));
        btnAdd.addActionListener(e -> addRoom());
        btnEdit.addActionListener(e -> editRoom());
        btnDelete.addActionListener(e -> deleteRoom());

        loadTable();
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        List<Room> rooms = roomManager.getRooms();
        for (Room r : rooms) {
            tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getCapacity(),
                    r.getLocation(),
                    r.isProjector() ? "Yes" : "No",
                    r.isWhiteboard() ? "Yes" : "No"
            });
        }
    }

    private void addRoom() {
        JTextField idField = new JTextField();
        JTextField capField = new JTextField();
        JTextField locField = new JTextField();
        JCheckBox projBox = new JCheckBox();
        JCheckBox whiteBox = new JCheckBox();

        Object[] message = {
                "Room ID:", idField,
                "Capacity:", capField,
                "Location:", locField,
                "Projector:", projBox,
                "Whiteboard:", whiteBox
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Room r = new Room(
                        idField.getText().trim(),
                        Integer.parseInt(capField.getText().trim()),
                        locField.getText().trim(),
                        projBox.isSelected(),
                        whiteBox.isSelected()
                );
                roomManager.addRoom(r);
                loadTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Capacity must be a number.");
            }
        }
    }

    private void editRoom() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Select a room to edit.");
            return;
        }

        String roomId = (String) tableModel.getValueAt(selected, 0);
        Room room = null;
        for (Room r : roomManager.getRooms()) {
            if (r.getId().equals(roomId)) {
                room = r;
                break;
            }
        }
        if (room == null) return;

        JTextField capField = new JTextField(String.valueOf(room.getCapacity()));
        JTextField locField = new JTextField(room.getLocation());
        JCheckBox projBox = new JCheckBox("", room.isProjector());
        JCheckBox whiteBox = new JCheckBox("", room.isWhiteboard());

        Object[] message = {
                "Capacity:", capField,
                "Location:", locField,
                "Projector:", projBox,
                "Whiteboard:", whiteBox
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                room.setCapacity(Integer.parseInt(capField.getText().trim()));
                room.setLocation(locField.getText().trim());
                room.setProjector(projBox.isSelected());
                room.setWhiteboard(whiteBox.isSelected());
                roomManager.editRoom(room);
                loadTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Capacity must be a number.");
            }
        }
    }

    private void deleteRoom() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Select a room to delete.");
            return;
        }

        String roomId = (String) tableModel.getValueAt(selected, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete room " + roomId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            roomManager.deleteRoom(roomId);
            loadTable();
        }
    }
}

