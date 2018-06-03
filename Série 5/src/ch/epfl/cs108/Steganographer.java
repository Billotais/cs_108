package ch.epfl.cs108;

import java.awt.image.BufferedImage;

public final class Steganographer {
    private Steganographer() {}

    // The number of bits in a char (16)
    private static int CHAR_BITS = Character.BYTES * Byte.SIZE;

    public static String extract(BufferedImage image) {
        // TODO à compléter dans le cadre de l'exercice 1
        
        String finalString = "";

        int oneChar = 0;
        int c = 0;
        for (int y = 0; y < image.getHeight(); ++y)
        {
            for (int x = 0; x < image.getWidth(); ++x)
            {
                int rgbColor = image.getRGB(x, y); // We extract the complete value
                int newBit = rgbColor & 1; // We extract the first bit
                oneChar = (oneChar << 1) | (newBit & 1); // We add it to the oneChar bit
                ++c;
                if (c == 16) //if 16 bits in the char -> we code it
                {
                    finalString += (char) oneChar;
                    oneChar = 0;
                    c = 0;
                }
            }
        }
        return finalString;
    }

    public static BufferedImage insert(BufferedImage image, String string) {
        // TODO à compléter dans le cadre de l'exercice 2
        BufferedImage outImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        int charCounter = 0;
        int bitCounter = 0;
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                
                if (bitCounter == 16)
                {
                    bitCounter = 0;
                    ++charCounter;
                }
                
                int bitToAdd;
                if (charCounter < string.length())
                {
                    char charToAdd = string.charAt(charCounter);
                    
                    
                    int bitsOfCharToAdd = (int) charToAdd;
                    
                    bitToAdd = (bitsOfCharToAdd >>> (CHAR_BITS - bitCounter - 1)) & 1;
                    
                }
                else
                {
                    bitToAdd = 0;
                }
               
                bitCounter++;
                int rgb = image.getRGB(x, y);
                
                int newRGB = rgb & (-0 << 1) | (bitToAdd & 1);
                
                
                outImage.setRGB(x, y, newRGB);
            }
        }
        return outImage;
    }
}
