package repx.view;

import repx.util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberRegistrationFrame extends JFrame {

    private JTextField nameField, contactField, ageField, usernameField, passwordField;
    private JComboBox<String> membershipTypeBox;
    private JButton registerButton, cancelButton;

    public MemberRegistrationFrame() {
        setTitle("Member Registration - RepX");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel headingLabel = new JLabel("Register New Member", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(headingLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Full Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Contact:"));
        contactField = new JTextField();
        formPanel.add(contactField);

        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Membership Type:"));
        membershipTypeBox = new JComboBox<>(new String[]{"Basic", "Premium", "VIP"});
        formPanel.add(membershipTypeBox);

        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
        formPanel.add(registerButton);
        formPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerMember();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void registerMember() {
        String name = nameField.getText();
        String contact = contactField.getText();
        String ageText = ageField.getText();
        String username = usernameField.getText();
        String password = new String(((JPasswordField) passwordField).getPassword());
        String membership = (String) membershipTypeBox.getSelectedItem();

        if (name.isEmpty() || contact.isEmpty() || ageText.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO users (name, contact, age, username, password, membership_type, join_date) VALUES (?, ?, ?, ?, ?, ?, NOW())";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, contact);
            ps.setInt(3, age);
            ps.setString(4, username);
            ps.setString(5, password);
            ps.setString(6, membership);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful.");
                dispose(); // close the frame
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Age must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
