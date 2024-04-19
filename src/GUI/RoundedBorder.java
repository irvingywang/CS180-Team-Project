package GUI;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class RoundedBorder extends AbstractBorder {
    private final int radius = GUIConstants.EDGE_RADIUS;
    private final int thickness = GUIConstants.STROKE_THICKNESS;
    private final Color color = GUIConstants.PRIMARY_STROKE;

    public RoundedBorder() {}

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(thickness));
        graphics.setColor(color);
        int adjustedRadius = Math.max(radius - thickness / 2, 0);
        graphics.drawRoundRect(x + thickness / 2, y + thickness / 2, width - thickness, height - thickness, adjustedRadius, adjustedRadius);
        graphics.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = thickness;
        return insets;
    }

}
