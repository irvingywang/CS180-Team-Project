package GUI;

import javax.swing.JTextField;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextField extends JTextField {

    private String placeholder;
    private int radius = 10;
    private int thickness = 5;

    public TextField(String placeholder) {
        super(placeholder);
        this.placeholder = placeholder;
        initStyle();
    }

    private void initStyle() {
        setOpaque(false);
        setText(placeholder);
        setEditable(true);
        setVisible(true);

        setForeground(GUIConstants.PRIMARY_WHITE); //text color
        setBackground(GUIConstants.TERTIARY_BLACK);
        setCaretColor(GUIConstants.PRIMARY_WHITE);

        setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setStroke(new BasicStroke(thickness)); // Use thickness for the border
                g2.setColor(GUIConstants.PRIMARY_STROKE);
                g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius); // Use radius for rounded corners
            }

            @Override
            public Insets getBorderInsets(Component c) {
                // Adjust insets based on thickness to avoid text overlapping the border
                int inset = thickness + 2; // Added +2 for a little padding beyond the thickness
                return new Insets(inset, inset, inset, inset);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                }
                //thickness += 4;
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                }
                //thickness -= 4;
            }
        });
    }

}
