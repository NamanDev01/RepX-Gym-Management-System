package repx.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FadeInFrame extends JFrame {
    private float opacity = 0f;

    public FadeInFrame() {
        setUndecorated(true);  // remove title bar
        setOpacity(0f);

        Timer timer = new Timer(30, (ActionEvent e) -> {
            opacity += 0.05f;
            if (opacity >= 1f) {
                opacity = 1f;
                ((Timer) e.getSource()).stop();
            }
            setOpacity(opacity);
        });
        timer.start();
    }
}
