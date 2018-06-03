package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.ArgumentChecker;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerID;

/**
 * Class Bomb
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class Bomb
{
    private final PlayerID playerID;
    private final Cell position;
    private final Sq<Integer> fuseLenghts;
    private final int range;

    /**
     * Builds a bomb with given informations
     * 
     * @param ownerID
     *            the id of the player who put the bomb
     *            
     * @param position
     *            the position of the bomb
     *            
     * @param fuseLenghts
     *            a sequence of integer containing the lengths of the explosion
     *            
     * @param range
     *            the range of the explosion
     */

    public Bomb(PlayerID ownerID, Cell position, Sq<Integer> fuseLengths, int range)
    {
        Sq<Integer> provFuseLenghts = Objects.requireNonNull(fuseLengths);
        if (provFuseLenghts.isEmpty())
        {
            throw new IllegalArgumentException();
        }
        this.playerID = Objects.requireNonNull(ownerID);
        this.position = Objects.requireNonNull(position);     
        this.range = ArgumentChecker.requireNonNegative(range);
        this.fuseLenghts = fuseLengths;
    }

    /**
     * Builds a bomb with given informations
     * 
     * @param ownerID
     *            the id of the player who put the bomb
     *            
     * @param position
     *            the position of the bomb
     *            
     * @param fuseLenght
     *            the fuse length
     *            
     * @param range
     *            the range of the explosion
     *            
     */

    public Bomb(PlayerID ownerID, Cell position, int fuseLength, int range)
    {
        this(ownerID, position, Sq.iterate(ArgumentChecker.requireNonNegative(fuseLength), s -> s - 1).limit(ArgumentChecker.requireNonNegative(fuseLength)), range);
    }

    /**
     * Return the id of the bomb's owner
     * 
     * @return the id of the player
     */
    
    public PlayerID ownerId()
    {
        return playerID;
    }

    /**
     * Return the position of the bomb (a Cell)
     * 
     * @return the the cell containing the bomb
     */

    public Cell position()
    {
        return position;
    }

    /**
     * Return the sequence of Integer representing the fuse lengths
     * 
     * @return the sequence of lengths
     */

    public Sq<Integer> fuseLengths()
    {
        return fuseLenghts;
    }

    /**
     * Return the fuse length of the bomb
     * 
     * @return the fuse length of the bomb
     */
    public int fuseLength()
    {
        return fuseLengths().head();
    }
    
    /**
     * Return range of the bomb
     * 
     * @return the range of the explosion
     */
    
    public int range()
    {
        return range;
    }

    /**
     * Return a List of sequences of sequences of cells, representing the
     * explosion
     * 
     * @return the List representing the explosion
     */

    public List<Sq<Sq<Cell>>> explosion()
    {
        List<Sq<Sq<Cell>>> list = new ArrayList<Sq<Sq<Cell>>>();
        for (Direction direction : Direction.values())
        {
            list.add(explosionArmTowards(direction));
        }
        return list;

    }

    /**
     * Return the sequence representing an arm of the expolsion
     * 
     * @return the sequence of sequences of cells
     */
    
    public Sq<Sq<Cell>> explosionArmTowards(Direction dir)
    {
        Sq<Cell> sqCell = Sq.iterate(position, c -> c.neighbor(dir)).limit(range);
        Sq<Sq<Cell>> sqSqCell = Sq.constant(sqCell).limit(Ticks.EXPLOSION_TICKS);
        return sqSqCell;
    }

}
