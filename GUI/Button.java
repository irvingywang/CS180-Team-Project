package GUI;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    public Button(String text, Runnable action) {
        super(text);
        this.addActionListener(e -> action.run());
        initUI();
    }

    private void initUI() {
        this.setVisible(true);
        //TODO styling
        this.setBackground(Color.BLUE);

        this.setBorderPainted(false);

        // Change color when mouse hovers over button
        //FIXME: This is not working
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(Color.CYAN);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(Color.BLUE);
            }
        });
    }
}
