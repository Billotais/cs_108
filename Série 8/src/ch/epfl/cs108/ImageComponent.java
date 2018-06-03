package ch.epfl.cs108;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class ImageComponent extends JComponent {
    private static final double ZOOM_FACTOR = 1.5;
    
    private final Image image;
    private Rectangle imageBounds;
    
    public ImageComponent(Image image, Rectangle initialBounds) {
        this.image = image;
        this.imageBounds = initialBounds;
        
        this.addMouseListener(new ImageMouseListener(this));
    }
    
    public Image image() {
        return image;
    }
    
    public Rectangle imageBounds() {
        return imageBounds;
    }
    
    public void setImageBounds(Rectangle newImageBounds) {
        imageBounds = newImageBounds;
        repaint();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 300);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage discreteI = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        double sX = imageBounds.width() / getWidth();
        double sY = imageBounds.height() / getHeight();
        
        for (int discreteY = 0; discreteY < getHeight(); ++discreteY) {
            double y = imageBounds.maxY() - discreteY * sY;
            for (int discreteX = 0; discreteX < getWidth(); ++discreteX) {
                double x = imageBounds.minX() + discreteX * sX;
                discreteI.setRGB(discreteX, discreteY, image.apply(x, y));
            }
        }
        
        g.drawImage(discreteI, 0, 0, null);
    }
    
    private static final class ImageMouseListener extends MouseAdapter {
        private final ImageComponent imageC;
        
        public ImageMouseListener(ImageComponent imageC) {
            this.imageC = imageC;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() != 2)
                return;
            
            Rectangle imageBounds = imageC.imageBounds();
            double sX = imageBounds.width() / imageC.getWidth();
            double sY = imageBounds.height() / imageC.getHeight();
            int x = e.getX(), y = imageC.getHeight() - e.getY();
            Rectangle newImageBounds = imageBounds
                    .translatedBy(x * sX, y * sY)
                    .scaledBy(1d / ZOOM_FACTOR, 1 / ZOOM_FACTOR)
                    .translatedBy(-x * sX / ZOOM_FACTOR, -y * sY / ZOOM_FACTOR);
            imageC.setImageBounds(newImageBounds);
        }
    }
}
