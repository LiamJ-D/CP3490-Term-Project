package ui;
import model.User;
import model.Role;
import service.UserManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageUsersPanel extends JPanel {

    private UserManager userManager;
    private MainFrame mainFrame;

    private JTable table;
    private DefaultTableModel tableModel;

    public ManageUsersPanel(UserManager userManager, MainFrame mainFrame) {
        this.userManager = userManager;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Users", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Role"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadUsers();

        JPanel buttonPanel = new JPanel();

        JButton btnAdd = new JButton("Add User");
        btnAdd.addActionListener(e -> {
            AddUserDialog d = new AddUserDialog(userManager, this);
            d.setVisible(true);
        });

        JButton btnEdit = new JButton("Edit User");
        btnEdit.addActionListener(e -> editSelectedUser());

        JButton btnDelete = new JButton("Delete User");
        btnDelete.addActionListener(e -> deleteSelectedUser());

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> mainFrame.showDashboard());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void loadUsers() {
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
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a user to edit.");
            return;
        }

        String id = tableModel.getValueAt(row, 0).toString();
        User selected = userManager.getById(id);

        EditUserDialog dialog = new EditUserDialog(userManager, this, selected);
        dialog.setVisible(true);
    }

    private void deleteSelectedUser() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a user to delete.");
            return;
        }

        String id = tableModel.getValueAt(row, 0).toString();

        if (id.equals("u001")) {
            JOptionPane.showMessageDialog(this, "You cannot delete the main admin.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this user?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userManager.deleteUser(id);
            loadUsers();
        }
    }
}

