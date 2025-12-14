package ui;
import model.Role;
import model.User;
import service.UserManager;
import javax.swing.*;
import java.awt.*;

public class AddUserDialog extends JDialog {

    private JTextField txtid;
    private JTextField txtName;
    private JPasswordField txtPassword;
    private JComboBox<Role> cbRole;

    private UserManager userManager;
    private ManageUsersPanel parent;

    public AddUserDialog(UserManager userManager, ManageUsersPanel parent) {
        super((Frame) null, "Add User", true);
        this.userManager = userManager;
        this.parent = parent;

        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("id:"));
        txtid = new JTextField();
        add(txtid);

        add(new JLabel("Name:"));
        txtName = new JTextField();
        add(txtName);


        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        add(new JLabel("Role:"));
        cbRole = new JComboBox<>(Role.values());
        add(cbRole);

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> addUser());
        add(btnAdd);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());
        add(btnCancel);
    }

    private void addUser() {

        String id = txtid.getText().trim();
        String Name = txtName.getText().trim();
        String pass = new String(txtPassword.getPassword()).trim();
        Role role = (Role) cbRole.getSelectedItem();

        if (id.isEmpty() || Name.isEmpty() || role.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        User u = userManager.createUser( id, Name, pass, role);

        userManager.addUser(u);

        parent.loadUsers();
        dispose();
    }
}
