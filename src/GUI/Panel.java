package GUI;

import javax.swing.JPanel;
import java.awt.LayoutManager;

public class Panel extends JPanel {

    public Panel() {
        initStyle();
        setVisible(true);
    }

    public Panel(LayoutManager layout) {
        super(layout);
        initStyle();
        setVisible(true);
    }

    private void initStyle() {
        setOpaque(true);
        setBackground(GUIConstants.PRIMARY_BLACK);
    }
}
