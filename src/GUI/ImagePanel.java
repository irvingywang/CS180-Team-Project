package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private BufferedImage image;

    public ImagePanel(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setBackground(GUIConstants.PRIMARY_BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            int width = this.getWidth();
            int height = this.getHeight();
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            // Keep aspect ratio
            float aspectRatio = (float) imgWidth / imgHeight;
            int newWidth = width;
            int newHeight = (int) (width / aspectRatio);
            if (newHeight > height) {
                newHeight = height;
                newWidth = (int) (height * aspectRatio);
            }

            int x = (width - newWidth) / 2;
            int y = (height - newHeight) / 2;
            g2d.drawImage(image, x, y, newWidth, newHeight, this);

            g2d.dispose();
        }
    }
}
