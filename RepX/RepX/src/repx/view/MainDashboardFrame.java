package repx.view;

import javax.swing.*;
import java.awt.*;

public class MainDashboardFrame extends BaseFrame {
    public MainDashboardFrame(String role, String username) {
        super("RepX Main Dashboard", 800, 600);

        JLabel welcomeLabel = new JLabel("Welcome, " + username + " (" + role + ")");
        welcomeLabel.setFont(StyleConstants.HEADING_FONT);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(welcomeLabel, BorderLayout.CENTER);

        setVisible(true);
    }
}
