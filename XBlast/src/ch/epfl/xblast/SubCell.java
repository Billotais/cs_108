package ch.epfl.xblast;

/**
 * Class SubCell
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class SubCell
{

    public static int COLUMNS = 240;
    public static int ROWS = 208;

    private final int x;
    private final int y;

    /**
     * Builds a subcell with two coordinates
     * 
     * @param x
     *          The x coordinate
     * @param y
     *          The y coordinate
     */

    public SubCell(int x, int y)
    {
        this.x = Math.floorMod(x, COLUMNS);
        this.y = Math.floorMod(y, ROWS);
    }

    /**
     * Return the x coordinate
     * 
     * @return the x coordinate
     */

    public int x()
    {
        return x;
    }

    /**
     * Return the y coordinate
     * 
     * @return the y coordinate
     */

    public int y()
    {
        return y;
    }

    /**
     * Returns the central subcell of the given cell
     * 
     * @param cell
     *          the given cell
     * 
     * @return the central subcell
     */

    public static SubCell centralSubCellOf(Cell cell)
    {
        return new SubCell(cell.x() * 16 + 8, cell.y() * 16 + 8);
    }

    /**
     * Returns the distance from the subcell to the nearest central subcell
     * 
     * @return the distance to the central subcell
     */

    public int distanceToCentral()
    {
        int cellPositionX = x / 16;
        int cellPositionY = y / 16;
        SubCell c = centralSubCellOf(new Cell(cellPositionX, cellPositionY));
        return Math.abs(x - c.x()) + Math.abs(y - c.y());
    }

    /**
     * Tells if the subcell is central
     * 
     * @return true if the subcell is central, false elsewise
     */

    public boolean isCentral()
    {
        return (distanceToCentral() == 0);
    }

    /**
     * Return the neighbor subcell in the direction given
     * 
     * @param dir
     *          the direction of the neighbor
     * 
     * @return the neighbor subcell
     */

    public SubCell neighbor(Direction d)
    {
        int newX = x;
        int newY = y;
        switch (d)
        {
            case N:
                newY -= 1;
                break;

            case E:
                newX += 1;
                break;

            case S:
                newY += 1;
                break;

            case W:
                newX -= 1;
                break;
        }

        return new SubCell(newX, newY);

    }

    /**
     * Returns the cell in which the subcell can be found
     * 
     * @return the cell containing the subcell
     */

    public Cell containingCell()
    {
        return new Cell(x / 16, y / 16);
    }

    /**
     * Redefine the equals method
     * 
     * @param that
     *            the subcell we are compared to
     * 
     * @return true if both subcells are the same, false if they are different
     *         or if that is not a subcell
     */
     
    @Override
    public boolean equals(Object that)
    {
        return (this.getClass() == that.getClass() && that.hashCode() == hashCode());
    }

    /**
     * Return a String sentence describing the subcell
     * 
     * @return the coordinates in a String
     */

     @Override
    public String toString()
    {
        return ("(" + x + ", " + y + ")");
    }
    
    /**
    * Return the hashCode of the subcell
    * 
    * @return the hasCode of the subcell
    */
    
    @Override
    public int hashCode()
    {
        return y * COLUMNS + x;
    }
}
