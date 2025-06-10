package repx.view;

import repx.util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class TrackProgressPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTextField weightField, heightField;

    public TrackProgressPanel(String username) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 250, 255));

        JLabel heading = new JLabel("Track My Progress", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setOpaque(true);
        heading.setBackground(new Color(0, 191, 255));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(heading, BorderLayout.NORTH);

        // Form Panel
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        form.setBackground(new Color(240, 250, 255));

        form.add(new JLabel("Weight (kg):"));
        weightField = new JTextField();
        form.add(weightField);

        form.add(new JLabel("Height (m):"));
        heightField = new JTextField();
        form.add(heightField);

        JButton saveBtn = new JButton("Add Record");
        saveBtn.setBackground(new Color(0, 191, 255));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        form.add(new JLabel());
        form.add(saveBtn);

        add(form, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Date", "Weight", "Height", "BMI"}, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadProgress(username);

        saveBtn.addActionListener(e -> {
            try {
                float weight = Float.parseFloat(weightField.getText());
                float height = Float.parseFloat(heightField.getText());
                float bmi = weight / (height * height);
                saveProgress(username, weight, height, bmi);
                tableModel.addRow(new Object[]{LocalDate.now(), weight, height, String.format("%.2f", bmi)});
                weightField.setText("");
                heightField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        });
    }

    private void loadProgress(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT date, weight, height, bmi FROM progress WHERE username = ? ORDER BY date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getDate("date"),
                        rs.getFloat("weight"),
                        rs.getFloat("height"),
                        String.format("%.2f", rs.getFloat("bmi"))
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveProgress(String username, float weight, float height, float bmi) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO progress (username, date, weight, height, bmi) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setFloat(3, weight);
            stmt.setFloat(4, height);
            stmt.setFloat(5, bmi);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
