package ch.epfl.cs108;

import java.util.Collections;
import java.util.List;

public class FromString implements ASCIImage
{
    String s;
    public FromString(String s)
    {
        this.s = s;
    }
    
    @Override
    public int width()
    {
       
        return s.length();
    }

    @Override
    public int height()
    {
        return 1;
    }

    @Override
    public List<String> drawing()
    {
        return Collections.singletonList(s);
    }
    
}

