package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextField extends JTextField {
    private final int leftPadding = 10;
    private String placeholder;

    public TextField(String placeholder, Dimension size) {
        super(placeholder);
        this.placeholder = placeholder;
        setMaximumSize(size);
        setOpaque(false);
        setText(placeholder);
        setEditable(true);
        setVisible(true);

        setForeground(GUIConstants.PRIMARY_WHITE); //text color
        setBackground(GUIConstants.SECONDARY_BLACK);
        setCaretColor(GUIConstants.PRIMARY_WHITE);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        Border padding = BorderFactory.createEmptyBorder(0, leftPadding, 0, 0);
        setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(), padding));

        addFocusEffect();
    }

    public void addFocusEffect() {
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

    public String getPlaceholder() {
        return placeholder;
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
