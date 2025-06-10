package repx.view;

import repx.util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MemberWorkoutPanel extends JPanel {
    private DefaultTableModel tableModel;

    public MemberWorkoutPanel(String username) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 250, 255));

        JLabel heading = new JLabel("My Workout Plan", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        heading.setOpaque(true);
        heading.setBackground(new Color(0, 191, 255));
        heading.setForeground(Color.WHITE);
        add(heading, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Day", "Exercise", "Sets", "Reps", "Completed"}, 0) {
            @Override public boolean isCellEditable(int row, int col) {
                return col == 4; // Only 'Completed' column is editable
            }

            @Override public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 4) ? Boolean.class : String.class;
            }
        };

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Save Progress");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.setBackground(new Color(0, 191, 255));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(saveBtn, BorderLayout.SOUTH);

        loadWorkoutData(username);

        saveBtn.addActionListener(e -> saveWorkoutProgress(username));
    }

    private void loadWorkoutData(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT day, exercise, sets, reps, completed FROM workouts WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("day"),
                        rs.getString("exercise"),
                        rs.getInt("sets"),
                        rs.getInt("reps"),
                        rs.getBoolean("completed")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveWorkoutProgress(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE workouts SET completed = ? WHERE username = ? AND day = ? AND exercise = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                boolean completed = (boolean) tableModel.getValueAt(i, 4);
                String day = (String) tableModel.getValueAt(i, 0);
                String exercise = (String) tableModel.getValueAt(i, 1);

                stmt.setBoolean(1, completed);
                stmt.setString(2, username);
                stmt.setString(3, day);
                stmt.setString(4, exercise);
                stmt.addBatch();
            }

            stmt.executeBatch();
            JOptionPane.showMessageDialog(this, "Progress saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
