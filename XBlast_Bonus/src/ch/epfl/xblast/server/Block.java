package ch.epfl.xblast.server;

import java.util.NoSuchElementException;

/**
 * Enumeration Block
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public enum Block
{
    FREE, INDESTRUCTIBLE_WALL, DESTRUCTIBLE_WALL, CRUMBLING_WALL,  BONUS_BOMB(Bonus.INC_BOMB), BONUS_RANGE(Bonus.INC_RANGE), BONUS_LIFE(Bonus.INC_LIFE), BONUS_TP(Bonus.TP), BONUS_BLIND(Bonus.BLIND);
    
    private Bonus mayBeAssociatedBonus;
    
    /**
     * Builds a new Block with an associated bonus
     * 
     * @param mayBeAssociatedBonus
     *          an associated Bonus
     */
    
    private Block(Bonus mayBeAssociatedBonus)
    {
        this.mayBeAssociatedBonus = mayBeAssociatedBonus;
    }
    
    /**
     * Builds a new Block with no associated Bonus
     */
    
    private Block()
    {
        this.mayBeAssociatedBonus = null;
    }
    /**
     * Checks if the Block is free
     * 
     * @return true if the block is free
     *          false if it is not free
     */
    public boolean isFree()
    {
        return (this == FREE);
    }

    /**
     * Checks if the Block can host a player
     * 
     * @return true if the block can host a player
     *          false if it can't
     */
    
    public boolean canHostPlayer()
    {
        return isFree() || isBonus();
    }

    /**
     * Checks if the Block cast a shadow
     * 
     * @return true if the block cast a shadow
     *          false otherwise
     */
    
    public boolean castsShadow()
    {
        return (this == INDESTRUCTIBLE_WALL || this == DESTRUCTIBLE_WALL || this == CRUMBLING_WALL);
    }
    /**
     * Checks if the Block is associated to a bonus
     * 
     * @return true if the block cast a shadow
     *          false otherwise
     */
    public boolean isBonus()
    {
        return (mayBeAssociatedBonus != null);
    }
    
    /**
     * Returns the associated Bonus
     * 
     * @return maybeAssociatedBonus
     * 
     * @throws NoSuchElementException 
     *            if the block is not a bonus
     */
    
    public Bonus associatedBonus()
    {
        
        if (!isBonus())
        {
            throw new NoSuchElementException();    
        }
        return mayBeAssociatedBonus;
    }
}
