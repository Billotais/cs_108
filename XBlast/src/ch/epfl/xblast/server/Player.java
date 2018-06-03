package ch.epfl.xblast.server;

import java.util.Objects;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.ArgumentChecker;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.server.Player.LifeState.State;

/**
 * Class Player
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class Player
{
    private final int maxBombs;
    private final int bombRange;
    private final Sq<LifeState> lifeStates;
    private final Sq<DirectedPosition> directedPos;
    private final PlayerID id;
    private final int lives;
    
    /**
     * Create a new Player with an ID, a sequence of lifestates, a sequence of directed positions, 
     *              the maximum number of bombs, and the range of the bombs.
     * 
     * @param id
     *              a player ID.
     * 
     * @param lifeStates
     *              a sequence of lifestates.
     *              
     * @param directedPos
     *              a sequence of directed positions.
     * 
     * @param maxBombs
     *              the maximum number of bombs.
     * 
     * @param bombRange
     *              the range of the bombs.
     * 
     * @throws NullPointerException
     *              if the id is empty.
     *              if the sequence of lifestates is empty.
     *              if the sequence of directed positions is empty.
     *              
     * @param IllegalArgumentException
     *              if the maximum number of bombs is negative.
     *              if the range of the bombs is negative.
     *
     */
    
    public Player(PlayerID id, Sq<LifeState> lifeStates,
            Sq<DirectedPosition> directedPos, int maxBombs, int bombRange)
    {
        this.id = Objects.requireNonNull(id);
        this.lifeStates = Objects.requireNonNull(lifeStates);
        this.directedPos = Objects.requireNonNull(directedPos);
        this.maxBombs = ArgumentChecker.requireNonNegative(maxBombs);
        this.bombRange = ArgumentChecker.requireNonNegative(bombRange);
        this.lives = lifeStates.head().lives();
    }
    /**
     * Create a new Player with an ID, the number of lives, the position of the player, 
     *              the maximum number of bombs, and the range of the bombs, using the previous constructor.
     * 
     * @param id
     *              a player ID.
     * 
     * @param lives
     *              the number of lives.
     *              
     * @param position
     *              the cell giving the position of the player.
     * 
     * @param maxBombs
     *              the maximum number of bombs.
     * 
     * @param bombRange
     *              the range of the bombs.
     *      
     * @param IllegalArgumentException
     *              if the number of lives is negative.
     *
     */

    public Player(PlayerID id, int lives, Cell position, int maxBombs,
            int bombRange)
    {
        this(id, Sq.constant(new LifeState(lives, State.INVULNERABLE))
                .limit(Ticks.PLAYER_INVULNERABLE_TICKS)
                .concat(Sq.constant(new LifeState(lives, State.VULNERABLE))),
                Sq.constant(new DirectedPosition(SubCell.centralSubCellOf(position), Direction.S)),
                maxBombs, bombRange);
        
    }
    
    /**
     * Return the maximum number of bombs
     * 
     * @return the maximum number of bombs 
     */

    public int maxBombs()
    {
        return maxBombs;
    }
    
    /**
     * Return the range of the bombs
     * 
     * @return the range of the bombs 
     */

    public int bombRange()
    {
        return bombRange;
    }
    
    /**
     * Return the sequence of lifestates
     * 
     * @return the sequence of lifestates
     */

    public Sq<LifeState> lifeStates()
    {
        return lifeStates;
    }
    
    /**
     * Return the sequence of directed positions
     * 
     * @return the sequence of directed positions
     */

    public Sq<DirectedPosition> directedPositions()
    {
        return directedPos;
    }
    /**
     * Return the player's id
     * 
     * @return the player's id
     */

    public PlayerID id()
    {
        return id;
    }
    
    /**
     * Return the number of lives
     * 
     * @return the number of lives
     */
    
    public int lives()
    {
        return lives;
    }
    
    /**
     * Return the actual lifestate
     * 
     * @return the actual lifestate
     */
    
    public LifeState lifeState()
    {
        return lifeStates.head();
    }
    
    /**
     * Return the sequence of lifestates for the player's next live
     * 
     * @return the sequence of lifestates for the player's next live
     */
    
    public Sq<LifeState> statesForNextLife()
    {
        if (lives == 1)
        {
            Sq<LifeState> sq = Sq.constant(new LifeState(lives, State.DYING)).limit(Ticks.PLAYER_DYING_TICKS);
            return sq.concat(Sq.constant(new LifeState(lives-1, State.DEAD)));
        }
        else if (lives > 1)
        {
            Sq<LifeState> sq = Sq.constant(new LifeState(lives, State.DYING)).limit(Ticks.PLAYER_DYING_TICKS);
            sq = sq.concat(Sq.constant(new LifeState(lives-1, State.INVULNERABLE)).limit(Ticks.PLAYER_INVULNERABLE_TICKS));
            return sq.concat(Sq.constant(new LifeState(lives-1,State.VULNERABLE)));
        }
        else
        {
            return Sq.constant(new LifeState(0, State.DEAD));
        }    
    }
    
    /**
     * Tells if the player is alive. 
     * 
     * @return true if the player has more than 0 lives, false otherwise.
     */
    
    public boolean isAlive()
    {
        return (lives > 0);
    }
    
    /**
     * Return the exact position of the player
     * 
     * @return the subcell of the postion of the player.
     */
    
    public SubCell position()
    {
        return directedPositions().head().position();
    }
    
    /**
     * Return the direction in which the player is looking at the moment.
     * 
     * @return the direction in which the player is looking at the moment.
     */
    
    public Direction direction()
    {
        return directedPositions().head().direction();
    }
    
    /**
     * Return a player with a changed maximum number of bombs.
     * 
     * @param newMaxBombs
     *              the new maximum number of bombs
     * 
     * @return a player with a changed maximum number of bombs.
     */
    
    public Player withMaxBombs(int newMaxBombs)
    {
        return new Player(id, lifeStates, directedPos, newMaxBombs, bombRange);
    }
    
    /**
     * Return a player with a changed bomb range.
     * 
     * @param newBombRange
     *              the new bomb range
     * 
     * @return a player with a changed bomb range.
     */
    
    public Player withBombRange(int newBombRange)
    {
        return new Player(id, lifeStates, directedPos, maxBombs, newBombRange);
    }
    
    /**
     * Return a bomb with the player's id, position and bomb range.
     * 
     * @return a bomb with the player's id, position and bomb range.
     */
    
    public Bomb newBomb()
    {
        return new Bomb(id, position().containingCell(), Ticks.BOMB_FUSE_TICKS, bombRange());
    }
    
    /**
     * Class LifeState
     * 
     */

    public static final class LifeState
    {
        private int lives;
        private State state;
        
        /**
         * Create a new LifeState with a number of lives and a state.
         * 
         * @param lives
         *              the number of lives
         * 
         * @param state
         *              the State 
         *              
         * 
         * @throws NullPointerException
         *              if the state is empty.
         *              
         * @param IllegalArgumentException
         *              if the number of lives is negative.
         *
         */

        public LifeState(int lives, State state)
        {
            this.state = (lives == 0 ? State.DEAD : Objects.requireNonNull(state));
            
            
            this.lives = ArgumentChecker.requireNonNegative(lives);
        }
        
        /**
         * Return the number of lives
         * 
         * @return the number of lives
         */

        public int lives()
        {
            return lives;
        }
        
        /**
         * Return the state
         * 
         * @return the state
         */

        public State state()
        {
            return state;
        }
        
        /**
         * Tells if we are able to move
         * 
         * @return true if we are in an invulnerable or vulnerable state, false otherwise.
         */
        public boolean canMove()
        {
            return (this.state == State.INVULNERABLE
                    || this.state == State.VULNERABLE);
        }
        
        /**
         * Enum State
         * 
         */

        public enum State
        {
            INVULNERABLE, VULNERABLE, DYING, DEAD;
        }

    }
    
    /**
     * Class DirectedPosition
     * 
     */

    public static final class DirectedPosition
    {
        private SubCell position;
        private Direction direction;
        
        /**
         * Create a new DirectedPosition with a position and a direction.
         * 
         * @param position
         *              the subcell of the position.
         * 
         * @param direction
         *              the direction.
         *              
         * 
         * @throws NullPointerException
         *              if the position is empty.
         *              if the direction is empty.
         *              
         *
         */

        public DirectedPosition(SubCell position, Direction direction)
        {
            this.position = Objects.requireNonNull(position);
            this.direction = Objects.requireNonNull(direction);
        }
        
        /**
         * Returns a sequence of the directed position given.
         * 
         * @param p
         *          the directed position.
         * 
         * @return a sequence of the directed position given.
         */
        
        public static Sq<DirectedPosition> stopped(DirectedPosition p)
        {
            return Sq.constant(p);
        }
        
        /**
         * Returns a sequence of directed positions 
         *          which corresponds to the movement in the direction of the directed position given.
         * 
         * @param p
         *          the directed position.
         * 
         * @return a sequence of directed positions.
         */

        public static Sq<DirectedPosition> moving(DirectedPosition p)
        {
            return Sq.iterate(p,  c -> new DirectedPosition(
                 c.position().neighbor(c.direction()), c.direction())); 
        }
        
        /**
         * Returns the position of the directed position.
         *  
         * @return the subcell of the position.
         */

        public SubCell position()
        {
            return position;
        }
        
        /**
         * Returns a directed position with a new position.
         * 
         * @param newPosition
         *          the new position.
         * 
         * @return a directed position with a new position.
         */

        public DirectedPosition withPosition(SubCell newPosition)
        {
            return new DirectedPosition(newPosition, direction());
        }
        
        /**
         * Returns the direction of the directed position.
         * 
         * @return the direction.
         */

        public Direction direction()
        {
            return direction;
        }
        /**
         * Returns a directed position with a new direction.
         * 
         * @param newDirection
         *          the new direction.
         * 
         * @return a directed position with a new direction.
         */

        public DirectedPosition withDirection(Direction newDirection)
        {
            return new DirectedPosition(position(), newDirection);
        }
    }
}
