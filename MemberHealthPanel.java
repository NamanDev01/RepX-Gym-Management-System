package repx.view;

import repx.util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class MemberHealthPanel extends JPanel {
    private DefaultTableModel tableModel;
    private String username;

    public MemberHealthPanel(String username) {
        this.username = username;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 250, 255));

        JLabel heading = new JLabel("Health Tracker", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        heading.setOpaque(true);
        heading.setBackground(new Color(0, 191, 255));
        heading.setForeground(Color.WHITE);
        add(heading, BorderLayout.NORTH);

        String[] columns = {"Date", "Weight (kg)", "Calories Burned", "Water (L)", "Steps Walked"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Update Today's Stats");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.setBackground(new Color(0, 191, 255));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(saveBtn, BorderLayout.SOUTH);

        loadHealthData();

        saveBtn.addActionListener(e -> saveTodayStats());
    }

    private void loadHealthData() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT date, weight, calories_burned, water_intake_ltr, steps_walked FROM health_stats WHERE username = ? ORDER BY date DESC LIMIT 10";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getDate("date"),
                        rs.getFloat("weight"),
                        rs.getInt("calories_burned"),
                        rs.getFloat("water_intake_ltr"),
                        rs.getInt("steps_walked")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveTodayStats() {
        LocalDate today = LocalDate.now();

        JTextField weightField = new JTextField();
        JTextField calField = new JTextField();
        JTextField waterField = new JTextField();
        JTextField stepsField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Weight (kg):"));
        panel.add(weightField);
        panel.add(new JLabel("Calories Burned:"));
        panel.add(calField);
        panel.add(new JLabel("Water Intake (L):"));
        panel.add(waterField);
        panel.add(new JLabel("Steps Walked:"));
        panel.add(stepsField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Today's Stats", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = """
                        INSERT INTO health_stats (username, date, weight, calories_burned, water_intake_ltr, steps_walked)
                        VALUES (?, ?, ?, ?, ?, ?)
                        ON DUPLICATE KEY UPDATE weight=?, calories_burned=?, water_intake_ltr=?, steps_walked=?""";

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setDate(2, Date.valueOf(today));
                stmt.setFloat(3, Float.parseFloat(weightField.getText()));
                stmt.setInt(4, Integer.parseInt(calField.getText()));
                stmt.setFloat(5, Float.parseFloat(waterField.getText()));
                stmt.setInt(6, Integer.parseInt(stepsField.getText()));
                stmt.setFloat(7, Float.parseFloat(weightField.getText()));
                stmt.setInt(8, Integer.parseInt(calField.getText()));
                stmt.setFloat(9, Float.parseFloat(waterField.getText()));
                stmt.setInt(10, Integer.parseInt(stepsField.getText()));
                stmt.executeUpdate();

                tableModel.setRowCount(0);
                loadHealthData();
                JOptionPane.showMessageDialog(this, "Health stats updated!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to save stats.");
            }
        }
    }
}
