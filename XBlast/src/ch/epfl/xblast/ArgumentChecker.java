package ch.epfl.xblast;

/**
 * Class ArgumentChecker
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class ArgumentChecker
{
    private ArgumentChecker()
    {};

    /**
     * Cheks if the given number is strictly greater than 0
     * 
     * @param value
     *            the number to check
     *            
     * @return true if the number is grater than 0
     *            false if not
     *
     */

    public static int requireNonNegative(int value)
    {
        
        if (value < 0)
        {
            throw new IllegalArgumentException();
        }
        return value;
    }

}
