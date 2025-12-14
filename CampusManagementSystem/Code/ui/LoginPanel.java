package ui;
import model.User;
import service.UserManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private UserManager userManager;
    private LoginListener listener;

    public LoginPanel(UserManager userManager) {
        this.userManager = userManager;
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel lblUser = new JLabel("UserID:");
        JLabel lblPass = new JLabel("Password:");
        txtUser = new JTextField(15);
        txtPass = new JPasswordField(15);
        btnLogin = new JButton("Login");

        btnLogin.addActionListener(this::loginAction);

        c.insets = new Insets(5,5,5,5);

        c.gridx = 0; c.gridy = 0; add(lblUser, c);
        c.gridx = 1; add(txtUser, c);
        c.gridx = 0; c.gridy = 1; add(lblPass, c);
        c.gridx = 1; add(txtPass, c);
        c.gridx = 1; c.gridy = 2; add(btnLogin, c);
    }

    private void loginAction(ActionEvent e) {
        String username = txtUser.getText();
        String password = new String(txtPass.getPassword());
        User user = userManager.login(username, password);
        if (user != null) {
            if (listener != null) listener.onLoginSuccess(user);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setLoginListener(LoginListener listener) {
        this.listener = listener;
    }

    public interface LoginListener {
        void onLoginSuccess(User user);
    }
}
