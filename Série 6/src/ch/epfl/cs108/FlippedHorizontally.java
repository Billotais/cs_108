package ch.epfl.cs108;

import java.util.ArrayList;
import java.util.List;

public class FlippedHorizontally implements ASCIImage
{
    private ASCIImage baseImage;
    public FlippedHorizontally(ASCIImage img)
    {
        baseImage = img;
    }
    @Override
    public int width()
    {
        return baseImage.width();
        
    }

    @Override
    public int height()
    {
        return baseImage.height();
    }

    @Override
    public List<String> drawing()
    {
        List<String> finalList = new ArrayList<>();
        for (String string : baseImage.drawing())
        {
            finalList.add(Strings.reverse(string));
        }
        return finalList;
    }

}
