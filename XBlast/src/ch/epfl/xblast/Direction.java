package ch.epfl.xblast;

/**
 * Enumeration Direction
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public enum Direction
{
    N, E, S, W;

    /**
     * Return the opposite direction
     * 
     * @return the opposite direction
     */

    public Direction opposite()
    {
        switch (this)
        {
            case N:
                return S;
            case E:
                return W;
            case S:
                return N;
            case W:
                return E;
            default:
                return null;
        }
    }

    /**
     * Check if the direction is horizontal
     * 
     * @return true if horizontal, false if not
     */

    public boolean isHorizontal()
    {
        switch (this)
        {
            case E:
                return true;
            case W:
                return true;
            default:
                return false;
        }
    }

    /**
     * Check if the direction is parallel to the given direction
     * 
     * @param that
     *          the given direction
     * 
     * @return true if parallel, false if not
     */

    public boolean isParallelTo(Direction that)
    {
        return (this == that || this == that.opposite());
    }

}
