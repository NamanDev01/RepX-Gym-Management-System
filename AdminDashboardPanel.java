package repx.view;

import repx.util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminDashboardPanel extends JPanel {

    private JTable memberTable;
    private DefaultTableModel tableModel;

    public AdminDashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Search UI ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Member Health Details", JLabel.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(200, 30));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Table setup
        tableModel = new DefaultTableModel();
        memberTable = new JTable(tableModel);
        memberTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        memberTable.setRowHeight(24);

        // Column headers
        tableModel.setColumnIdentifiers(new Object[]{
                "ID", "Name", "Age", "Gender", "Email", "BMI", "Weight", "Height", "Goal"
        });

        JScrollPane scrollPane = new JScrollPane(memberTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load all member data initially
        loadMemberData();

        // Search button action
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                searchMembers(keyword);
            } else {
                loadMemberData();
            }
        });
    }

    private void loadMemberData() {
        tableModel.setRowCount(0);
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT m.id, m.name, m.age, m.gender, m.email, h.bmi, h.weight, h.height, h.goal " +
                    "FROM members m JOIN health h ON m.id = h.member_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getDouble("bmi"),
                        rs.getDouble("weight"),
                        rs.getDouble("height"),
                        rs.getString("goal")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading member data: " + e.getMessage());
        }
    }

    private void searchMembers(String keyword) {
        tableModel.setRowCount(0);
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT m.id, m.name, m.age, m.gender, m.email, h.bmi, h.weight, h.height, h.goal " +
                    "FROM members m JOIN health h ON m.id = h.member_id " +
                    "WHERE m.name LIKE ? OR m.id LIKE ? OR h.goal LIKE ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getDouble("bmi"),
                        rs.getDouble("weight"),
                        rs.getDouble("height"),
                        rs.getString("goal")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Search error: " + e.getMessage());
        }
    }
}
