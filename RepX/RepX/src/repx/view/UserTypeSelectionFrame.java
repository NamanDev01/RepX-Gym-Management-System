package repx.view;

import javax.swing.*;
import java.awt.*;

public class UserTypeSelectionFrame extends BaseFrame {

    public UserTypeSelectionFrame() {
        super("RepX Gym Management - Choose Login Type", 400, 250);

        JPanel panel = new JPanel();
        panel.setBackground(StyleConstants.BACKGROUND_WHITE);
        panel.setLayout(new GridBagLayout());

        JButton adminBtn = new JButton("Admin Login");
        adminBtn.setPreferredSize(new Dimension(180, 50));
        adminBtn.setFont(StyleConstants.HEADING_FONT);

        JButton memberBtn = new JButton("Member Login");
        memberBtn.setPreferredSize(new Dimension(180, 50));
        memberBtn.setFont(StyleConstants.HEADING_FONT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;

        gbc.gridy = 0;
        panel.add(new JLabel("Select User Type"), gbc);

        gbc.gridy = 1;
        panel.add(adminBtn, gbc);

        gbc.gridy = 2;
        panel.add(memberBtn, gbc);

        add(panel);

        adminBtn.addActionListener(e -> {
            dispose();
            new AdminLoginFrame();
        });

        memberBtn.addActionListener(e -> {
            dispose();
            new MemberLoginFrame();
        });

        setVisible(true);
    }
}
