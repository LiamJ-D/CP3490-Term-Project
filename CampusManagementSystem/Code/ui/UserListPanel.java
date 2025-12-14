package ui;

import model.Role;
import model.User;
import service.UserManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserListPanel extends JPanel {

    private UserManager userManager;
    private MainFrame mainFrame;

    private JTable table;
    private DefaultTableModel tableModel;

    public UserListPanel(UserManager userManager, MainFrame mainFrame) {
        this.userManager = userManager;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("User Management", JLabel.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 22));
        add(lbl, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{"ID","Name", "Role"}, 0
        );
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();

        JButton btnEdit = new JButton("Edit User");
        JButton btnDelete = new JButton("Delete User");
        JButton btnBack = new JButton("Back");

        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnBack);

        add(btnPanel, BorderLayout.SOUTH);

        loadUsers();


        btnEdit.addActionListener(e -> editSelectedUser());
        btnDelete.addActionListener(e -> deleteSelectedUser());
        btnBack.addActionListener(e ->
                mainFrame.switchPanel(new DashboardPanel(
                        mainFrame.userManager.getAdmin(),
                        mainFrame.eventManager,
                        mainFrame.roomManager,
                        mainFrame.feedbackManager,
                        mainFrame.notificationService,
                        mainFrame
                ))
        );
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        List<User> users = userManager.getAllUsers();

        for (User u : users) {
            tableModel.addRow(new Object[]{
                    u.getId(), u.getName(), u.getRole()
            });
        }
    }

    private void editSelectedUser() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a user to edit.");
            return;
        }

        String userId = tableModel.getValueAt(row, 0).toString();
        User user = userManager.getById(userId);

        JTextField tfName = new JTextField(user.getName());

        JComboBox<Role> cbRole = new JComboBox<>(Role.values());
        cbRole.setSelectedItem(user.getRole());

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Name:"));
        panel.add(tfName);
        panel.add(new JLabel("Role:"));
        panel.add(cbRole);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Edit User", JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            user.setName(tfName.getText().trim());
            user.setRole((Role) cbRole.getSelectedItem());

            userManager.saveUsers(userManager.getAllUsers());
            loadUsers();
        }
    }

    private void deleteSelectedUser() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a user to delete.");
            return;
        }

        String userId = tableModel.getValueAt(row, 0).toString();

        if (userId.equals("a001")) {
            JOptionPane.showMessageDialog(this, "The admin account cannot be deleted.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this user?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            userManager.deleteUser(userId);
            userManager.saveUsers(userManager.getAllUsers());
            loadUsers();
        }
    }
}
