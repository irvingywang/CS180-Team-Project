package GUI;

import javax.swing.*;

public class Spacer extends JPanel {
    public Spacer(int height) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(height));
        setOpaque(false);
    }
}
