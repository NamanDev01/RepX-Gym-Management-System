package repx.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MemberDashboardPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel rightPanel;
    private String username;

    public MemberDashboardPanel(String username) {
        this.username = username;
        setLayout(new BorderLayout());

        // === TOP HEADER ===
        JLabel title = new JLabel("👋 Welcome to RepX, " + username, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setOpaque(true);
        title.setBackground(new Color(220, 240, 255));
        title.setForeground(new Color(0, 120, 215));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        // === LEFT SIDEBAR ===
        JPanel leftMenu = new JPanel(new GridLayout(6, 1, 10, 10));
        leftMenu.setBackground(new Color(230, 245, 255));
        leftMenu.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        leftMenu.setPreferredSize(new Dimension(200, 0));

        // Buttons
        String[] labels = {"Workout Plan", "Track Progress", "Health Tracker", "Nutrition", "Profile", "Logout"};
        JButton[] buttons = new JButton[labels.length];

        for (int i = 0; i < labels.length; i++) {
            buttons[i] = new JButton(labels[i]);
            buttons[i].setFont(new Font("Segoe UI", Font.PLAIN, 15));
            buttons[i].setBackground(new Color(0, 191, 255));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            buttons[i].setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, true)); // rounded effect
            addHoverEffect(buttons[i]);
            leftMenu.add(buttons[i]);
        }

        add(leftMenu, BorderLayout.WEST);

        // === RIGHT MAIN PANEL ===
        cardLayout = new CardLayout();
        rightPanel = new JPanel(cardLayout);
        rightPanel.add(new MemberWorkoutPanel(username), "Workout");
        rightPanel.add(new TrackProgressPanel(username), "Progress");
        rightPanel.add(new MemberHealthPanel(username), "Health");
        rightPanel.add(new MemberNutritionPanel(username), "Nutrition");
        rightPanel.add(new MemberProfilePanel(username), "Profile");

        add(rightPanel, BorderLayout.CENTER);

        // === ACTIONS ===
        buttons[0].addActionListener(e -> cardLayout.show(rightPanel, "Workout"));
        buttons[1].addActionListener(e -> cardLayout.show(rightPanel, "Progress"));
        buttons[2].addActionListener(e -> cardLayout.show(rightPanel, "Health"));
        buttons[3].addActionListener(e -> cardLayout.show(rightPanel, "Nutrition"));
        buttons[4].addActionListener(e -> cardLayout.show(rightPanel, "Profile"));
        buttons[5].addActionListener(e -> {
            new UserTypeSelectionFrame();
            SwingUtilities.getWindowAncestor(this).dispose();
        });
    }

    private void addHoverEffect(JButton btn) {
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0, 140, 200));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(0, 191, 255));
            }
        });
    }
}
