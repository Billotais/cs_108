package ch.epfl.xblast.client;


import java.awt.Image;

import java.io.File;


import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

/**
 * Class ImageCollection
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class ImageCollection
{
    
    private Map<Byte, Image> imageMap;
    
    /**
     * Create a new ImageCollection given the name of the folder containing the images
     * @param name
     *          the name of the folder
     */
    
    public ImageCollection(String name)
    {   
        imageMap = new HashMap<>();
        try
        {
            File dir = new File(ImageCollection.class.getClassLoader().getResource(name).toURI());
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; ++i)
            {
                imageMap.put(Byte.parseByte(files[i].getName().substring(0,3)), ImageIO.read(files[i]));
            }
            
        } catch (Exception e){}
    
    }
    /**
     * Gives the image corresponding to the given index in the ImageCollection
     * @param index
     *          the index of the image
     * @return  the image corresponding to the index
     * @throws NoSuchElementException
     *          if no image corresponf to the given index
     */
    public Image image(byte index)
    {
        Image rtnByte = imageOrNull(index);
        if (rtnByte == null)
        {
            throw new NoSuchElementException();
        }
        return rtnByte;

    }/**
     * Gives the image corresponding to the given index in the ImageCollection
     * @param index
     *          the index of the image
     * @return  the image corresponding to the index if there is one, or null if no image is found with the given index
     */
    
    public Image imageOrNull(byte index)
    {
        for (Map.Entry<Byte, Image> entrySet: imageMap.entrySet())
        {
            if (entrySet.getKey() == index)
            {
                return entrySet.getValue();
            }
        } 
        return null;
    }
}
