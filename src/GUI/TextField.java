package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextField extends JTextField {
    private final int radius = 10;
    private final int thickness = 3;
    private final int textPadding = 10;

    public TextField(String placeholder) {
        super(placeholder);
        setOpaque(false);
        setText(placeholder);
        setEditable(true);
        setVisible(true);

        setForeground(GUIConstants.PRIMARY_WHITE); //text color
        setBackground(GUIConstants.SECONDARY_BLACK);
        setCaretColor(GUIConstants.PRIMARY_WHITE);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        Border roundedBorder = new Border() {
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setStroke(new BasicStroke(thickness));
                graphics.setColor(GUIConstants.PRIMARY_STROKE);
                int adjustedRadius = Math.max(radius - thickness / 2, 0);
                graphics.drawRoundRect(x + thickness / 2, y + thickness / 2, width - thickness, height - thickness, adjustedRadius, adjustedRadius);
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
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(graphics);
    }
}
