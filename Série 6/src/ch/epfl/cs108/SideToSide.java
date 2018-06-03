package ch.epfl.cs108;

import java.util.ArrayList;
import java.util.List;

public class SideToSide implements ASCIImage
{
    private ASCIImage left;
    private ASCIImage right;
    public SideToSide(ASCIImage left, ASCIImage right)
    {
        this.left = left;
        this.right = right;
    }
    @Override
    public int width()
    {
        return left.width() + right.width();
    }

    @Override
    public int height()
    {
        return Math.max(left.height(), right.height());
    }

    @Override
    public List<String> drawing()
    {
        List<String> finalList = new ArrayList<>();
        if (left.height() > right.height())
        {
            for (int i = 0; i < left.height(); ++i)
            {
                if (i < right.height())
                {
                    finalList.add(left.drawing().get(i) + right.drawing().get(i));
                }
                else
                {
                    finalList.add(left.drawing().get(i) + finalList.add(Strings.nCopies(right.width(), ' ')));
                }
            }
        }
        
        if (left.height() <= right.height())
        {
            for (int i = 0; i < right.height(); ++i)
            {
                if (i < left.height())
                {
                    finalList.add(left.drawing().get(i) + right.drawing().get(i));
                }
                else
                {
                    finalList.add(Strings.nCopies(left.width(), ' ') + right.drawing().get(i));
                }
            }
        }
        return finalList;
    }

}
