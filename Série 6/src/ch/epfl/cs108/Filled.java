package ch.epfl.cs108;

import java.util.Collections;
import java.util.List;

public class Filled implements ASCIImage
{

    private int width;
    private int height;
    private char c;
    public Filled(int width, int height, char c)
    {
        this.c = c;
        this.width = width;
        this.height = height;
    }
    @Override
    public int width()
    {
        return width;
    }

    @Override
    public int height()
    {
        return height;
    }

    @Override
    public List<String> drawing()
    {
        return Collections.nCopies(height, Strings.nCopies(width, c));
    }

}
