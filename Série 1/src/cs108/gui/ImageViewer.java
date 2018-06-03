package cs108.gui;

import static javax.swing.SwingUtilities.invokeLater;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import cs108.ColorRGB;
import cs108.ImageRGB;

public final class ImageViewer {
    public static void main(String[] args) {
        invokeLater(() -> {
            try {
                // L'image à afficher, à modifier au fur et à mesure de
                // votre avancement.
                URL u = new URL("http://cs108.epfl.ch/e/images/dub.jpg");
                BufferedImage discreteImage = ImageIO.read(u);
                ImageRGB image = ImageRGB.ofDiscreteImage(ColorRGB.BLACK, discreteImage);
                JFrame mainWindow = new JFrame("Image viewer");
                mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                mainWindow.getContentPane().setLayout(new BorderLayout());
                ImageComponent imageComponent = new ImageComponent(image, 0, 0, 4);
                mainWindow.getContentPane().add(imageComponent, BorderLayout.CENTER);

                mainWindow.pack();
                mainWindow.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
