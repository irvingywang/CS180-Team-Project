package GUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class Dropdown extends JComboBox<String> {

    public Dropdown(String[] items, Dimension maxSize) {
        super(items);
        setUI(new DropdownElement());
        setMaximumSize(maxSize);
        setForeground(GUIConstants.PRIMARY_WHITE);
        setBackground(GUIConstants.SECONDARY_BLACK);
        setFont(GUIConstants.TEXT_FONT);
        setFocusable(false);
        setRenderer(new DropdownRender());
        setBorder(new RoundedBorder());
    }

    private static class DropdownRender extends BasicComboBoxRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setBackground(isSelected ? GUIConstants.PRIMARY_BLACK : GUIConstants.SECONDARY_BLACK);
            setForeground(GUIConstants.PRIMARY_WHITE);
            setBorder(BorderFactory.createEmptyBorder(10, GUIConstants.LEFT_PADDING, 10, 10));
            return this;
        }
    }

    private static class DropdownElement extends BasicComboBoxUI {

        @Override
        protected JButton createArrowButton() {
            JButton arrowButton = new JButton("<html><b>&and;</b><br><b>&or;</b></html>") {
                @Override
                protected void paintComponent(Graphics g) {
                    g.setColor(GUIConstants.SECONDARY_BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    super.paintComponent(g);
                }
            };
            arrowButton.setFocusPainted(false);
            arrowButton.setBorderPainted(false);
            arrowButton.setContentAreaFilled(false);
            arrowButton.setForeground(GUIConstants.PRIMARY_WHITE);

            arrowButton.setPreferredSize(new Dimension(20, 20));
            return arrowButton;
        }

        @Override
        public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
            Insets insets = comboBox.getInsets();
            int width = comboBox.getWidth();
            int height = bounds.height + insets.top + insets.bottom;

            g.setColor(GUIConstants.SECONDARY_BLACK);
            g.fillRect(0, 0, width, height);

            super.paintCurrentValue(g, bounds, hasFocus);
        }
    }
}
