package repx.view;

import javax.swing.*;

public class DashboardFrame extends JFrame {
    public DashboardFrame() {
        setTitle("RepX - Dashboard");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton registerMemberBtn = new JButton("Register Member");
        registerMemberBtn.addActionListener(e -> new MemberRegistrationFrame());


        add(registerMemberBtn);
        setVisible(true);
    }
}
