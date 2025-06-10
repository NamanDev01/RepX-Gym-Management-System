package repx.view;
import repx.util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MemberLoginFrame extends BaseFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel errorLabel;

    public MemberLoginFrame() {
        super("RepX - Member Login", 450, 380);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StyleConstants.BACKGROUND_WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("Member Login");
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

        JButton registerBtn = new JButton("New Member? Register Here");
        registerBtn.setFont(StyleConstants.INPUT_FONT);
        registerBtn.setBorder(BorderFactory.createEmptyBorder());
        registerBtn.setContentAreaFilled(false);
        registerBtn.setForeground(StyleConstants.PRIMARY_BLUE);
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridy = 7;
        panel.add(registerBtn, gbc);

        add(panel);

        loginBtn.addActionListener(e -> performLogin());
        registerBtn.addActionListener(e -> {
            dispose();
            new MemberRegistrationFrame();
        });

        setVisible(true);
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill all fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password); // For real app, use hashing
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                if (!"member".equalsIgnoreCase(role)) {
                    errorLabel.setText("Invalid member credentials.");
                } else {
                    errorLabel.setText(" ");
                    dispose();
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    new MemberDashboardFrame(username);
                }
            } else {
                errorLabel.setText("Incorrect username or password.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            errorLabel.setText("Database error.");
        }
    }
}
