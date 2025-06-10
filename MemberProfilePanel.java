package repx.view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import repx.util.DBConnection;

public class MemberProfilePanel extends JPanel {
    public MemberProfilePanel(String username) {
        setLayout(new GridBagLayout());
        setBackground(new Color(225, 245, 254));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT name, contact, age, membership_type, join_date FROM users WHERE username = ? AND role = 'member'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JLabel[] labels = {
                        new JLabel("Name: " + rs.getString("name")),
                        new JLabel("Contact: " + rs.getString("contact")),
                        new JLabel("Age: " + rs.getInt("age")),
                        new JLabel("Membership: " + rs.getString("membership_type")),
                        new JLabel("Join Date: " + rs.getDate("join_date"))
                };

                for (JLabel lbl : labels) {
                    lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                    lbl.setForeground(Color.DARK_GRAY);
                    add(lbl, gbc);
                    gbc.gridy++;
                }
            } else {
                add(new JLabel("Profile not found."), gbc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            add(new JLabel("Error loading profile."), gbc);
        }
    }
}
