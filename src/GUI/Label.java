package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Project05 -- Label
 * <p>
 * Creates a framework for a GUI label.
 *
 * @author Amir Elnashar, L08
 * @author Irving Wang, L08
 * @author Jack Kim, L08
 * @author John Guan, L08
 * @author Karan Vankwani, L08
 * @version April 14, 2024
 */
public class Label extends JLabel {
    public Label (String text, int fontSize) {
        super(text);
        setForeground(GUIConstants.PRIMARY_WHITE);
        setVisible(true);
        setOpaque(false);
        setFont(new Font(GUIConstants.FONT_NAME, Font.BOLD, fontSize)); //uses system default
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
