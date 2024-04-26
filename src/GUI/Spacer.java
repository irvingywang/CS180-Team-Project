package GUI;

import javax.swing.*;

/**
 * Project05 -- Spacer
 * <p>
 * Creates a framework for a GUI spacer.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class Spacer extends JPanel {
    public Spacer(int height) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(height));
        setOpaque(false);
    }
}
