package repx.view;

import repx.util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberProfileFrame extends JFrame {

    public MemberProfileFrame(String username) {
        setTitle("Your Profile");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Member Profile", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel nameLbl = new JLabel("Full Name:");
        JLabel contactLbl = new JLabel("Contact:");
        JLabel ageLbl = new JLabel("Age:");
        JLabel userLbl = new JLabel("Username:");
        JLabel typeLbl = new JLabel("Membership Type:");
        JLabel joinLbl = new JLabel("Join Date:");

        JLabel nameVal = new JLabel();
        JLabel contactVal = new JLabel();
        JLabel ageVal = new JLabel();
        JLabel userVal = new JLabel();
        JLabel typeVal = new JLabel();
        JLabel joinVal = new JLabel();

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        for (JLabel lbl : new JLabel[]{nameLbl, contactLbl, ageLbl, userLbl, typeLbl, joinLbl,
                nameVal, contactVal, ageVal, userVal, typeVal, joinVal}) {
            lbl.setFont(labelFont);
        }

        contentPanel.add(nameLbl);    contentPanel.add(nameVal);
        contentPanel.add(contactLbl); contentPanel.add(contactVal);
        contentPanel.add(ageLbl);     contentPanel.add(ageVal);
        contentPanel.add(userLbl);    contentPanel.add(userVal);
        contentPanel.add(typeLbl);    contentPanel.add(typeVal);
        contentPanel.add(joinLbl);    contentPanel.add(joinVal);

        add(contentPanel, BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(closeBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Fetch profile details
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND role = 'member'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nameVal.setText(rs.getString("name"));
                contactVal.setText(rs.getString("contact"));
                ageVal.setText(String.valueOf(rs.getInt("age")));
                userVal.setText(rs.getString("username"));
                typeVal.setText(rs.getString("membership_type"));
                joinVal.setText(rs.getString("join_date"));
            } else {
                JOptionPane.showMessageDialog(this, "No profile found for: " + username);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading profile: " + ex.getMessage());
        }

        setVisible(true);
    }
}
