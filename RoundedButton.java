package repx.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setBackground(new Color(0, 191, 255));
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setContentAreaFilled(false);

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(30, 200, 255));
            }

            public void mouseExited(MouseEvent e) {
                setBackground(new Color(0, 191, 255));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g);
    }
}
