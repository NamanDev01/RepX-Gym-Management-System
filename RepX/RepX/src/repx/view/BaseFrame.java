package repx.view;

import javax.swing.*;
import java.awt.*;

public class BaseFrame extends JFrame {

    public BaseFrame(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(StyleConstants.BACKGROUND_WHITE);
        setResizable(true); // enables maximize/minimize buttons
    }

    protected JPanel createHeadingPanel(String headingText) {
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(StyleConstants.PRIMARY_BLUE);
        headingPanel.setPreferredSize(new Dimension(getWidth(), 80));
        headingPanel.setLayout(new GridBagLayout());

        JLabel headingLabel = new JLabel(headingText);
        headingLabel.setFont(StyleConstants.HEADING_FONT);
        headingLabel.setForeground(Color.WHITE);

        headingPanel.add(headingLabel);
        return headingPanel;
    }

    protected JTextField createTextField(int width, int height) {
        JTextField tf = new JTextField();
        tf.setFont(StyleConstants.INPUT_FONT);
        tf.setPreferredSize(new Dimension(width, height));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return tf;
    }

    protected JPasswordField createPasswordField(int width, int height) {
        JPasswordField pf = new JPasswordField();
        pf.setFont(StyleConstants.INPUT_FONT);
        pf.setPreferredSize(new Dimension(width, height));
        pf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return pf;
    }

    protected JButton createPrimaryButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setFont(StyleConstants.LABEL_FONT.deriveFont(Font.BOLD, 20f));
        btn.setBackground(StyleConstants.PRIMARY_BLUE);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(width, height));
        return btn;
    }
}
