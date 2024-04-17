package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextField extends JTextField {

    private String placeholder;
    private int radius = 10;
    private int thickness = 3;
    private int textPadding = 10;

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
        setBackground(GUIConstants.SECONDARY_BLACK);
        setCaretColor(GUIConstants.PRIMARY_WHITE);

        Border roundedBorder = new Border() {
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setStroke(new BasicStroke(thickness));
                g2.setColor(GUIConstants.PRIMARY_STROKE);
                int adjustedRadius = Math.max(radius - thickness / 2, 0);
                g2.drawRoundRect(x + thickness / 2, y + thickness / 2, width - thickness, height - thickness, adjustedRadius, adjustedRadius);
            }

            public Insets getBorderInsets(Component c) {
                int inset = thickness + 2;
                return new Insets(inset, inset, inset, inset);
            }

            public boolean isBorderOpaque() {
                return false;
            }
        };
        Border padding = BorderFactory.createEmptyBorder(0, textPadding, 0, 0);
        setBorder(BorderFactory.createCompoundBorder(roundedBorder, padding));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g2);
    }
}
