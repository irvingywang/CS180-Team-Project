package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {
    Color idleColor = GUIConstants.PRIMARY_BLUE;
    Color hoverColor = GUIConstants.DARKER_BLUE;

    public Button(String text, Runnable action) {
        super(text);
        this.addActionListener(e -> action.run());
        setVisible(true);
        initStyle();
    }

    private void initStyle() {
        this.setOpaque(true);
        this.setContentAreaFilled(true);

        //TODO styling
        this.setBackground(idleColor);

        this.setBorderPainted(false);

        // Change color when mouse hovers over button
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent event) {
                setBackground(hoverColor);
                repaint();
            }

            public void mouseExited(MouseEvent event) {
                setBackground(idleColor);
                repaint();
            }
        });
    }
}
