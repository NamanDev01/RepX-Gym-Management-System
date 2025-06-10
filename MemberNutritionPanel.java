package repx.view;

import repx.util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MemberNutritionPanel extends JPanel {
    private DefaultTableModel tableModel;

    public MemberNutritionPanel(String username) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 250, 255));

        JLabel heading = new JLabel("My Nutrition Plan", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setOpaque(true);
        heading.setBackground(new Color(0, 191, 255));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(heading, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Day", "Meal", "Time", "Calories", "Completed"}, 0) {
            @Override public boolean isCellEditable(int row, int col) {
                return col == 4;
            }
            @Override public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 4) ? Boolean.class : String.class;
            }
        };

        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton saveBtn = new JButton("Save Progress");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.setBackground(new Color(0, 191, 255));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(saveBtn, BorderLayout.SOUTH);

        loadNutritionData(username);

        saveBtn.addActionListener(e -> saveNutritionProgress(username));
    }

    private void loadNutritionData(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT day, meal, time, calories, completed FROM nutrition WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("day"),
                        rs.getString("meal"),
                        rs.getString("time"),
                        rs.getInt("calories"),
                        rs.getBoolean("completed")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveNutritionProgress(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE nutrition SET completed = ? WHERE username = ? AND day = ? AND meal = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                boolean completed = (boolean) tableModel.getValueAt(i, 4);
                String day = (String) tableModel.getValueAt(i, 0);
                String meal = (String) tableModel.getValueAt(i, 1);

                stmt.setBoolean(1, completed);
                stmt.setString(2, username);
                stmt.setString(3, day);
                stmt.setString(4, meal);
                stmt.addBatch();
            }

            stmt.executeBatch();
            JOptionPane.showMessageDialog(this, "Nutrition progress saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
