package GUI;

import javax.swing.JPanel;
import java.awt.*;

public class Panel extends JPanel {
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
