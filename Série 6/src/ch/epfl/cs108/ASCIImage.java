package ch.epfl.cs108;

import java.io.PrintStream;
import java.util.List;

public interface ASCIImage {
    public int width();
    public int height();
    public List<String> drawing();

    default public void printOn(PrintStream s) {
    	for (String t: drawing())
    		s.println(t);
    }
    public static ASCIImage fromString(String s)
    {
        return new FromString(s);
    }
    public static ASCIImage filled(int width, int height, char c)
    {
        return new Filled(width, height, c);
    }
    default public ASCIImage flippedHorizontally()
    {   
        return new FlippedHorizontally(this);
    }
    default public ASCIImage transposed()
    {
        return new Transposed(this);
    }
    default public ASCIImage leftOf(ASCIImage right)
    {
        return new SideToSide(this, right);
    }
    default public ASCIImage above(ASCIImage bottom)
    {
        return new OneOnAnother(this, bottom);
    }
    
    public static void main(String[] args)
    {
        
        ASCIImage.fromString("La malade pédala mal").flippedHorizontally().printOn(System.out);;
        ASCIImage.filled(5, 2, '+').transposed().printOn(System.out);
        ASCIImage.fromString("La malade pédala mal").transposed().printOn(System.out);
        ASCIImage.fromString("Un rectangle : ").leftOf(ASCIImage.filled(3, 2, '#')).printOn(System.out);
        ASCIImage.filled(3, 1, 'X').above(ASCIImage.filled(4, 2, 'O')).printOn(System.out);
    }
}
