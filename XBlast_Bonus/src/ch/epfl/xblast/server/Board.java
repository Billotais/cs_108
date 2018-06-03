package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Lists;

/**
 * Class Board
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class Board
{
    private final List<Sq<Block>> listSequence;

    /**
     * Create a new board with a given List of sequences of Blocks
     * 
     * @param blocks
     *          a List of sequences of Blocks
     * 
     * @throws IllegalArgumentException
     *          if the size of the given list isn't equal to
     *          the number of cells
     *
     */

    public Board(List<Sq<Block>> blocks)
    {
        if (blocks.size() != Cell.COUNT)
        {
            throw new IllegalArgumentException();
        }
        listSequence = Collections.unmodifiableList(new ArrayList<>(blocks));
    }

    /**
     * Create a board with given cells
     * 
     * @param rows
     *          a List of Lists containing some blocks
     * 
     * @return  a Board created with these blocks
     * 
     * @throws IllegalArgumentExeption
     *          if the size of the two-dimensions table
     *          created by the given list isn't equal
     *          to the numbers of Rows and Columns of
     *          the board
     * 
     */

    public static Board ofRows(List<List<Block>> rows)
    {
        checkBlockMatrix(rows, Cell.ROWS, Cell.COLUMNS);

        List<Sq<Block>> sequenceOfRows = new ArrayList<Sq<Block>>();
        for (List<Block> oneRow : rows)
        {
            for (Block b : oneRow)
            {
                sequenceOfRows.add(Sq.constant(b));
            }
        }
        return new Board(sequenceOfRows);
    }

    /**
     * Create a board with the inside cells given
     * 
     * @param rows
     *          a List of Lists containing some blocks
     * 
     * @return
     *          a Board created with these blocks
     * 
     * @throws IllegalArgumentExeption
     *          if the size of the two-dimensions table
     *          created by the given list isn't equal
     *          to the numbers of Rows and Columns of
     *          the inside of the board
     * 
     */
    public static Board ofInnerBlocksWalled(List<List<Block>> innerBlocks)
    {
        checkBlockMatrix(innerBlocks, Cell.ROWS - 2, Cell.COLUMNS - 2);

        List<Sq<Block>> sequenceOfBocksWithWall = new ArrayList<Sq<Block>>();

        Block b = Block.INDESTRUCTIBLE_WALL;

        for (int i = 0; i < Cell.COLUMNS; ++i)
        {
            sequenceOfBocksWithWall.add(Sq.constant(b));
        }
        for (List<Block> oneRow : innerBlocks)
        {
            sequenceOfBocksWithWall.add(Sq.constant(b));
            for (Block block : oneRow)
            {
                sequenceOfBocksWithWall.add(Sq.constant(block));
            }
            sequenceOfBocksWithWall.add(Sq.constant(b));
        }
        for (int i = 0; i < Cell.COLUMNS; ++i)
        {
            sequenceOfBocksWithWall.add(Sq.constant(b));
        }
        return new Board(sequenceOfBocksWithWall);
    }

    /**
     * Create a board with the North West quadrant cells given
     * 
     * @param rows
     *          a List of Lists containing some blocks
     * 
     * @return
     *          a Board created with these blocks
     * 
     * @throws IllegalArgumentExeption
     *          if the size of the two-dimensions table
     *          created by the given list isn't equal
     *          to the numbers of Rows and Columns of
     *          the the North west quadrant
     * 
     */
    
    public static Board ofQuadrantNWBlocksWalled(List<List<Block>> quadrantNWBlocks)
    {
        checkBlockMatrix(quadrantNWBlocks, (Cell.ROWS - 1) / 2, (Cell.COLUMNS - 1) / 2);

        List<List<Block>> listOfQuadrantBlocks = new ArrayList<List<Block>>();

        for (List<Block> oneLineOfQuadrant : quadrantNWBlocks)
        {
            listOfQuadrantBlocks.add(Lists.mirrored(oneLineOfQuadrant));
        }

        return (ofInnerBlocksWalled(Lists.mirrored(listOfQuadrantBlocks)));

    }

    /**
     * Return the constant sequence corresponding to the given cell
     * 
     * @param c
     *          a cell
     * 
     * @return
     *          the sequence corresponding to the given cell
     */

    public Sq<Block> blocksAt(Cell c)
    {
        return listSequence.get(c.rowMajorIndex());
    }

    /**
     * Return the block corresponding to the given cell
     * 
     * @param c
     *          a cell
     * 
     * @return
     *          the block corresponding to the given cell
     */

    public Block blockAt(Cell c)
    {
        return blocksAt(c).head();
    }

    /**
     * Throws an exeption if the size of a given matrix doesn't correspond to
     * the given size
     * 
     * @param matrix
     *          the matrix we want to check the dimension of
     * 
     * @param rows
     *          the number of rows the matrix is supposed to have
     * 
     * @param columns
     *          the number of columns the matrix is supposed to have
     * 
     * @throws IllegalArgumentExeption
     *          if the size of the matrix do not correspond to
     *          the given dimnesions
     */

    private static void checkBlockMatrix(List<List<Block>> matrix, int rows, int columns)
    {
        if (matrix.size() != rows || matrix.get(0).size() != columns)
        {
            throw new IllegalArgumentException();
        }
    }
}
