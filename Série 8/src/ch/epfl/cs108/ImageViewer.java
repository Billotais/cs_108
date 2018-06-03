package ch.epfl.cs108;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public final class ImageViewer {
    private static void createUI() {
        JFrame window = new JFrame("Image viewer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageComponent imageComponent = new ImageComponent(Image.mandelbrot(500), new Rectangle(-2.5, 1.5, -1.5, 1.5));
        window.getContentPane().add(imageComponent);
        
        window.pack();
        window.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createUI());
    }
}
