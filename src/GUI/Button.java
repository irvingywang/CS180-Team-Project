package GUI;

import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton implements Element {
    private Color idleColor = GUIConstants.PRIMARY_BLUE;
    private Color hoverColor = GUIConstants.DARKER_BLUE;
    private int radius = 10;

    public Button(String text, Runnable action) {
        super(text);
        addActionListener(e -> action.run());
        setVisible(true);
        initStyle();
    }

    private void initStyle() {
        setOpaque(false);
        setContentAreaFilled(true);
        setFocusPainted(false);

        setBackground(idleColor);
        setForeground(GUIConstants.PRIMARY_WHITE);

        setBorderPainted(false);

        // Change color when mouse hovers over button
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
        graphics.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        super.paintComponent(graphics);
    }
}
