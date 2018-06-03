package cs108.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import cs108.ImageRGB;

@SuppressWarnings("serial")
public final class ImageComponent extends JComponent {
    private ImageRGB image;
    private final double centerX, centerY;
    private final double width;

    public ImageComponent(ImageRGB image, double centerX, double centerY, double width) {
        this.image = image;
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
    }

    public ImageRGB image() {
        return image;
    }

    public void setImage(ImageRGB newImage) {
        image = newImage;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 280);
    }

    @Override
    protected void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D)g0;

        BufferedImage jImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        double inc = width / getWidth();
        double xMin = centerX - inc * (getWidth() / 2.0);
        double yMax = centerY + inc * (getHeight() / 2.0);
        for (int imageX = 0; imageX < getWidth(); ++imageX) {
            double x = xMin + inc * imageX;
            for (int imageY = 0; imageY < getHeight(); ++imageY) {
                double y = yMax - inc * imageY;
                jImage.setRGB(imageX, imageY, image.apply(x, y).toAWTColor().getRGB());
            }
        }

        g.drawImage(jImage, 0, 0, null);
    }
}
