package ch.epfl.cs108;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public final class Main {
    public static void main(String[] args) throws IOException {
        URL imageURL = new URL("http://cs108.epfl.ch/e/images/pskov.png");
        BufferedImage image = ImageIO.read(imageURL);
        String textToAdd = "Salut";
        String text = Steganographer.extract(Steganographer.insert(image,textToAdd));
        System.out.println("Texte extrait de l'image :");
        System.out.println(text);
    }
}
