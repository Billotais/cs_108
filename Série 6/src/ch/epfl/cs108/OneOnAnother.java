
package ch.epfl.cs108;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

public class OneOnAnother implements ASCIImage
{
    private ASCIImage top;
    private ASCIImage bottom;
    
    public OneOnAnother(ASCIImage top, ASCIImage bottom)
    {
        this.top = top;
        this.bottom = bottom;
    }
    @Override
    public int width()
    {
        return Math.max(top.width(), bottom.width());
    }

    
    @Override
    public int height()
    {
        return top.height() + bottom.height();
    }

    
    @Override
    public List<String> drawing()
    {
        List<String> finalList = new ArrayList<>();
        for (String string : top.drawing())
        {
            if (top.width() == width())
            {
                finalList.add(string);
            }
            else
            {
                finalList.add(string + Strings.nCopies(width() - top.width(), ' '));
            }
        }
        for (String string : bottom.drawing())
        {
            if (bottom.width() == width())
            {
                finalList.add(string);
            }
            else
            {
                finalList.add(string + Strings.nCopies(width() - bottom.width(), ' '));
            }
        }
        return finalList;
    }

}
