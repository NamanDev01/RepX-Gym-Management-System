package repx.view;

import javax.swing.*;
import java.awt.*;

public class AdminLoginFrame extends BaseFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel errorLabel;

    public AdminLoginFrame() {
        super("RepX - Admin Login", 450, 350);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StyleConstants.BACKGROUND_WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("Admin Login");
        title.setFont(StyleConstants.HEADING_FONT);
        gbc.gridy = 0;
        panel.add(title, gbc);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(StyleConstants.LABEL_FONT);
        gbc.gridy = 1;
        panel.add(userLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridy = 2;
        panel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(StyleConstants.LABEL_FONT);
        gbc.gridy = 3;
        panel.add(passLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridy = 4;
        panel.add(passwordField, gbc);

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        gbc.gridy = 5;
        panel.add(errorLabel, gbc);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(StyleConstants.INPUT_FONT);
        gbc.gridy = 6;
        panel.add(loginBtn, gbc);

        add(panel);

        loginBtn.addActionListener(e -> performLogin());

        setVisible(true);
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // For now, hardcoded check
        if (username.equals("admin") && password.equals("admin123")) {
            dispose();
            JOptionPane.showMessageDialog(null, "Admin Login successful!");
            new MainDashboardFrame("admin", username);
        } else {
            errorLabel.setText("Incorrect username or password.");
        }
    }
}
