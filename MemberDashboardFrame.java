package repx.view;

import javax.swing.*;
import java.awt.*;

public class MemberDashboardFrame extends JFrame {
    private String username;

    public MemberDashboardFrame(String username) {
        this.username = username;

        setTitle("Member Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add full dashboard panel
        add(new MemberDashboardPanel(username), BorderLayout.CENTER);

        setVisible(true);
    }
}
