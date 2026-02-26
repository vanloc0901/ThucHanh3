package quanlinhanvien;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton cancelBtn = new JButton("Cancel");

        add(userLabel);
        add(usernameField);
        add(passLabel);
        add(passwordField);
        add(loginBtn);
        add(cancelBtn);

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("admin") && password.equals("123")) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new EmployeeDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        });

        cancelBtn.addActionListener(e -> {
            System.exit(0);
        });

        setVisible(true);
    }
}
