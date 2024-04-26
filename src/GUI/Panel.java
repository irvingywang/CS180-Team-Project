package GUI;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.LayoutManager;

/**
 * Project05 -- Panel
 * <p>
 * Creates a framework for a GUI panel.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
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