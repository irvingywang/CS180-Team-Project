package GUI;

import javax.swing.JPanel;
import java.awt.LayoutManager;

public class Panel extends JPanel {

    public Panel() {
        initStyle();
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