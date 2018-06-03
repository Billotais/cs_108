package ch.epfl.cs108;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public final class IFSComponent extends JComponent {
    private IFS ifs = null;
    private int pointsCount = 100_000;
    
    public void setIFS(IFS newIFS) {
        this.ifs = newIFS;
        repaint();
    }

    public void setPointsCount(int newPointsCount) {
        if (! (pointsCount > 0))
            throw new IllegalArgumentException("non-positive points count");
        this.pointsCount = newPointsCount;
        repaint();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (ifs == null)
            return;
        
        double scale = (double)getWidth() / ifs.width();
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        
        Graphics2D imageGraphics = image.createGraphics();
        imageGraphics.setColor(Color.WHITE);
        imageGraphics.fillRect(0, 0, getWidth(), getHeight());
        
        for (Point p: ifs.points(pointsCount)) {
            int x = (int)((p.x() - ifs.minX()) * scale);
            int y = getHeight() - (int)((p.y() - ifs.minY()) * scale);
            if (0 <= x && x < getWidth() && 0 <= y && y < getHeight())
                image.setRGB(x, y, 0x70_70_70);
        }
        g.drawImage(image, 0, 0, null);
    }
}
