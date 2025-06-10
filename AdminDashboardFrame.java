package repx.view;

import javax.swing.*;

public class AdminDashboardFrame extends JFrame {
    public AdminDashboardFrame() {
        setTitle("Admin Dashboard - RepX");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(new AdminDashboardPanel());

        setVisible(true);
    }
}
