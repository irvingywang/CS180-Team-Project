package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextField extends JTextField {
    private String placeholder;

    public TextField(String placeholder, Dimension size) {
        super(placeholder);
        this.placeholder = placeholder;
        setMaximumSize(size);
        setOpaque(false);
        setText(placeholder);
        setEditable(true);
        setVisible(true);

        setFont(GUIConstants.TEXT_FONT);

        setForeground(GUIConstants.TERTIARY_WHITE); //initial text color
        setBackground(GUIConstants.SECONDARY_BLACK);
        setCaretColor(GUIConstants.PRIMARY_WHITE);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        Border padding = BorderFactory.createEmptyBorder(3, GUIConstants.LEFT_PADDING, 0, 0);
        setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(), padding));

        addFocusEffect();
    }

    public void addFocusEffect() {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(GUIConstants.PRIMARY_WHITE);
                    setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(GUIConstants.TERTIARY_WHITE);
                    setText(placeholder);
                }
            }
        });
    }

    @Override
    public String getText() {
        return super.getText().equals(placeholder) ? "" : super.getText();
    }

    public void setPlaceholder(String text) {
        placeholder = text;
        setText(text);
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
