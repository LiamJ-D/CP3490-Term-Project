package ui;
import model.Role;
import model.User;
import service.UserManager;
import javax.swing.*;
import java.awt.*;

public class EditUserDialog extends JDialog {

    public EditUserDialog(UserManager userManager, ManageUsersPanel parent, User user) {
        setTitle("Edit User");
        setSize(350, 350);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new GridLayout(7, 2));

        JLabel lblId = new JLabel(user.getId());

        JTextField txtName = new JTextField(user.getName());
        JTextField txtPassword = new JTextField(user.getPassword());
        JComboBox<Role> cbRole = new JComboBox<>(Role.values());
        cbRole.setSelectedItem(user.getRole());

        add(new JLabel("User ID:"));
        add(lblId);

        add(new JLabel("Name:"));
        add(txtName);

        add(new JLabel("Password:"));
        add(txtPassword);

        add(new JLabel("Role:"));
        add(cbRole);

        JButton btnSave = new JButton("Save Changes");
        JButton btnCancel = new JButton("Cancel");

        add(btnSave);
        add(btnCancel);

        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            user.setName(txtName.getText());
            user.setPassword(txtPassword.getText());
            user.setRole((Role) cbRole.getSelectedItem());

            userManager.updateUser(user);
            parent.loadUsers();
            dispose();
        });
    }
}

