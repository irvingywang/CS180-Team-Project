package GUI;

import javax.swing.*;
import java.awt.LayoutManager;

public class Panel extends JPanel {

    public Panel() {
        initStyle();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public Panel(LayoutManager layout) {
        super(layout);
        initStyle();
    }

    public void initStyle() {
        setVisible(true);
        setOpaque(true);
        setBackground(GUIConstants.PRIMARY_BLACK);
    }

}