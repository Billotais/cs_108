package ch.epfl.xblast;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class Cell
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class Cell
{
    public static final int COLUMNS = 15;
    public static final int ROWS = 13;
    public static final int COUNT = 195;

    public static final List<Cell> ROW_MAJOR_ORDER = Collections
            .unmodifiableList(rowMajorOrder());
    public static final List<Cell> SPIRAL_ORDER = Collections
            .unmodifiableList(spiralOrder());

    private final int x;
    private final int y;

    /**
     * Builds a cell with two coordinates
     * 
     * @param x
     *          The x coordinate
     * @param y
     *          The y coordinate
     */

    public Cell(int x, int y)
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
     * Return the index in the row major order
     * 
     * @return the index of the cell
     */

    public int rowMajorIndex()
    {
        return y * COLUMNS + x;
    }

    /**
     * Return the neighbor cell in the direction given
     * 
     * @param dir
     *          the direction of the neighbor
     * 
     * @return the neighbor cell
     */

    public Cell neighbor(Direction dir)
    {
        int newX = x;
        int newY = y;
        switch (dir)
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
        return new Cell(newX, newY);
    }

    /**
     * Redefine the equals method
     * 
     * @param that
     *          the cell we are compared to
     * 
     * @return true if both cells are the same, false if they are different or
     *          if that is not a cell
     */
    @Override
    public boolean equals(Object that)
    {
        return (this.getClass() == that.getClass() && that.hashCode() == hashCode());
    }

    /**
     * Return a String sentence describing the cell
     * 
     * @return the coordinates in a String
     */
    @Override
    public String toString()
    {
        return ("(" + x + ", " + y + ")");
    }

    /**
     * Return an ArrayList with the cells in row major order in order to
     * initialize the List
     * 
     * @return the ArrayList with the cells
     */

    private static ArrayList<Cell> rowMajorOrder()
    {
        ArrayList<Cell> rowMajorOrder = new ArrayList<>();
        for (int i = 0; i < ROWS; ++i)
        {
            for (int j = 0; j < COLUMNS; ++j)
            {
                rowMajorOrder.add(new Cell(j, i));
            }
        }
        return rowMajorOrder;
    }

    /**
     * Return an ArrayList with the cells in spiral order in order to initialize
     * the List
     * 
     * @return the ArrayList with the cells
     */

    public static ArrayList<Cell> spiralOrder()
    {
        ArrayList<Cell> spiralOrder = new ArrayList<>();

        ArrayList<Integer> ix = new ArrayList<>();
        ArrayList<Integer> iy = new ArrayList<>();
        ArrayList<Integer> i1 = new ArrayList<>();
        ArrayList<Integer> i2 = new ArrayList<>();

        boolean horizontal = true;

        for (int i = 0; i < COLUMNS; ++i)
        {
            ix.add(i);
        }
        for (int j = 0; j < ROWS; ++j)
        {
            iy.add(j);
        }

        while (ix.size() != 0 && iy.size() != 0)
        {
            if (horizontal)
            {
                i1 = ix;
                i2 = iy;
            }
            else
            {
                i1 = iy;
                i2 = ix;
            }
            int c2 = i2.get(0);

            i2.remove(0);

            for (int c1 : i1)
            {
                Cell c = null;
                if (horizontal)
                {
                    c = new Cell(c1, c2);
                }
                else
                {
                    c = new Cell(c2, c1);
                }
                spiralOrder.add(c);
            }
            Collections.reverse(i1);

            horizontal = !horizontal;
        }
        return spiralOrder;

    }
    
    /**
    * Return the hashCode of the Cell
    * 
    * @return the hasCode of the Cell
    */
    
    @Override
    public int hashCode()
    {
       return rowMajorIndex(); 
    }

}
