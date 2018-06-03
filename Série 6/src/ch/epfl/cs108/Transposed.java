package ch.epfl.cs108;

import java.util.ArrayList;
import java.util.List;

public class Transposed implements ASCIImage
{
    private ASCIImage baseImage;
    public Transposed(ASCIImage img)
    {
        baseImage = img;
    }
    @Override
    public int width()
    {
        return baseImage.height();
    }

    @Override
    public int height()
    {
        return baseImage.width();
    }

    @Override
    public List<String> drawing()
    {
        List<String> finalList = new ArrayList<>();
        for (int i = 0; i < height(); ++i)
        {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < width(); ++j)
            {
                builder.append(baseImage.drawing().get(j).charAt(i));
            }
            finalList.add(builder.toString());
        }
        return finalList;
    }

}
