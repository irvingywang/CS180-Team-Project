package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {
    private Color idleColor = GUIConstants.PRIMARY_BLUE;
    private Color hoverColor = GUIConstants.DARKER_BLUE;

    public Button(String text, Runnable action, Dimension size) {
        super(text);
        addActionListener(e -> action.run());
        setMaximumSize(size);
        setVisible(true);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);

        setBackground(idleColor);
        setForeground(GUIConstants.PRIMARY_WHITE);
        setFont(GUIConstants.TEXT_FONT);
        Border padding = BorderFactory.createEmptyBorder(3, 0, 0, 0);
        setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(), padding));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        setBorderPainted(false);

        addHoverEffect();
    }

    public Button(String text, Runnable action, Dimension size, Boolean altStyle) {
        this(text, action, size);
        if (altStyle) {
            idleColor = GUIConstants.SECONDARY_BLACK;
            hoverColor = GUIConstants.PRIMARY_BLACK;
            setBackground(idleColor);

            setBorder(new RoundedBorder());
            setBorderPainted(true);

            addHoverEffect();
        }
    }

    public void addHoverEffect() {
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent event) {
                setBackground(hoverColor);
                repaint();
            }

            public void mouseExited(MouseEvent event) {
                setBackground(idleColor);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), GUIConstants.EDGE_RADIUS, GUIConstants.EDGE_RADIUS);
        super.paintComponent(graphics);
    }
}
