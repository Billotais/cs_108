package ch.epfl.xblast.server;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;


import java.util.Map;

import ch.epfl.cs108.Sq;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.Lists;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.SubCell;

import ch.epfl.xblast.server.Player.DirectedPosition;
import ch.epfl.xblast.server.Player.LifeState;
import ch.epfl.xblast.server.Player.LifeState.State;


/**
 * Class GameState
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */
public final class GameState
{
    private final int ticks;
    private final Board board;
    private final List<Player> players;
    private final List<Bomb> bombs;
    private final List<Sq<Sq<Cell>>> explosions;
    private final List<Sq<Cell>> blasts;
   
    

    private static List<List<PlayerID>> listOfPermutatedPlayers = new ArrayList<>();

    private static Random RANDOM = new Random(2016);

    /**
     * Create a new GameState 
     * @param ticks
     *          the tick of the Gamesate
     * @param board
     *          the board of the GameState
     * @param players
     *          the list of players in the GameState
     * @param bombs
     *          the list of all the bombs on the board
     * @param explosions
     *          the explosions generated
     * @param blasts
     *          the actual blasts
     */
    
    public GameState(int ticks, Board board, List<Player> players, List<Bomb> bombs, List<Sq<Sq<Cell>>> explosions, List<Sq<Cell>> blasts)
    {
        if (ticks < 0 || players.size() != 4)
        {
            throw new IllegalArgumentException();
        }
        this.ticks = ticks;
        this.board = Objects.requireNonNull(board);
        this.players = Objects.requireNonNull(players);
        this.bombs = Objects.requireNonNull(new ArrayList<>(bombs));
        this.explosions = Objects.requireNonNull(explosions);
        this.blasts = Objects.requireNonNull(new ArrayList<>(blasts));
        
        if (listOfPermutatedPlayers.size() == 0)
        {
            listOfPermutatedPlayers = Lists.permutations(Arrays.asList(PlayerID.values()));
        }
    }

    /**
     * Create a new GameState
     * 
     * @param board
     *          the board of the GameState
     * @param players
     *          the players of thie GameState
     */
    public GameState(Board board, List<Player> players)
    {
        this(0, board, players, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
    
    /**
     * Give the current tick of the Gamestate
     * 
     * @return the actual tick of the GameState
     * 
     */
    public int ticks()
    {
        return ticks;
    }

    /**
     * Tells if the game is over
     * 
     * @return true if the game is over
     *         false else
     */
    public boolean isGameOver()
    {
        int alivePlayers = 0;
        for (Player player : players)
        {
            if (player.isAlive())
            {
                alivePlayers++;
            }
        }
        
        return (ticks >= Ticks.TOTAL_TICKS || alivePlayers <= 1);
    }

    /**
     * Gives the remaining time of the game
     * 
     * @return the remaining time
     */
    public double remainingTime()
    {
       
        double remainingTicks = Ticks.TOTAL_TICKS - ticks;
        
        return remainingTicks / Ticks.TICKS_PER_SECOND;
    }
    /**
     * Retrun the board of the GameState
     * 
     * @return the board
     */
    public Board board()
    {
        return board;
    }
    /**
     * Return a list with all the players of the game
     * 
     * @return the list of players
     */
    public List<Player> players()
    {
        return players;
    }

    /**
     * Return a list with all the alive players of the game
     * 
     * @return the list of alive players
     */
    public List<Player> alivePlayers()
    {
        List<Player> alivePlayers = new ArrayList<>();
        for (Player player : players)
        {
            if (player.isAlive())
            {
                alivePlayers.add(player);
            }
        }
        
        return alivePlayers;
    }
    /**
     * Return the winner of the game if there is one
     * 
     * @return the winner if there is one 
     *         Optional.empty() if no one has won yet
     */
    public Optional<PlayerID> winner()
    {  
        return (alivePlayers().size() == 1 ? Optional.of(alivePlayers().get(0).id()) : Optional.empty());
    }
    /**
     * Return the next GameState depending on the given events
     * 
     * @param speedChangeEvents
     *          a map with players and the direction they want to go
     * @param bombDropEvents
     *          a set of players who want to put a bomb
     * @return the next GameSate
     */
    public GameState next(Map<PlayerID, Optional<Direction>> speedChangeEvents, Set<PlayerID> bombDropEvents)
    {
        List<PlayerID> oneTimePermutatedPlayerIds = listOfPermutatedPlayers.get((ticks % listOfPermutatedPlayers.size()));
        List<Player> oneTimePermutatedPlayers = new ArrayList<>();
        for (PlayerID pId : oneTimePermutatedPlayerIds)
        {
            for (Player p : players)
            {
                if (p.id() == pId)
                {
                    oneTimePermutatedPlayers.add(p);
                }
            }
        }
         
        List<Sq<Cell>> blasts1 = nextBlasts(blasts, board, explosions);
        
        Set<Cell> consumedBonuses = new HashSet<>();
        
        for (Player playerOnTheBoard : oneTimePermutatedPlayers)
        {
            if (board.blockAt(playerOnTheBoard.position().containingCell()).isBonus() && playerOnTheBoard.position().isCentral() 
                    && !consumedBonuses.contains(playerOnTheBoard.position().containingCell())) 
            {    
                consumedBonuses.add(playerOnTheBoard.position().containingCell());
            }
        }
        
        Map<PlayerID, Bonus> mapPlayerAndBonuses = new HashMap<>();
        for (Cell cellWithABonus : consumedBonuses)
        {
            for (Player playerMayBeOnABonus : oneTimePermutatedPlayers)
            {
                if(playerMayBeOnABonus.position().containingCell().equals(cellWithABonus) && playerMayBeOnABonus.position().isCentral())
                {   
                    mapPlayerAndBonuses.put(playerMayBeOnABonus.id(), board.blockAt(cellWithABonus).associatedBonus());
                    break;
                }                  
            }
        }
             
        Board board1 = nextBoard(board, consumedBonuses, blastedCells(blasts1));
        
        List<Sq<Sq<Cell>>> explosions1 = nextExplosions(explosions);
         
        List<Bomb> bombs1 =  new ArrayList<>();
        for (Bomb alreadyExistingBomb : bombs)
        {
            if (alreadyExistingBomb.fuseLength() == 1 || blastedCells(blasts1).contains(alreadyExistingBomb.position()))
            {
                explosions1.addAll(alreadyExistingBomb.explosion());
     
            }
            else
            {
                bombs1.add(new Bomb(alreadyExistingBomb.ownerId(), alreadyExistingBomb.position(), alreadyExistingBomb.fuseLength()-1, alreadyExistingBomb.range()));
            }
            
        }
        
        for (Bomb newBomb : newlyDroppedBombs(oneTimePermutatedPlayers
             , bombDropEvents, bombs1))
        {
            
            bombs1.add(new Bomb(newBomb.ownerId(), newBomb.position(), newBomb.fuseLength()-1, newBomb.range()));
        }
        
        List<Player> players1 = nextPlayers(players, mapPlayerAndBonuses, bombedCells(bombs1).keySet() , board1, blastedCells(blasts1), speedChangeEvents);
        
        return new GameState(ticks+1, board1, players1, bombs1, explosions1, blasts1);
    }

    /**
     * Return a set of al blasted cells
     * 
     * @return the set of blasted cells
     */
    
    public Set<Cell> blastedCells()
    {
        return blastedCells(blasts);
    }
    
    /**
     * Return a map associating every existing bomb to its position
     * 
     * @return the map of bombs and positions
     */
    public Map<Cell, Bomb> bombedCells()
    {
        return bombedCells(bombs);
    
    }
    
    private static Map<Cell, Bomb> bombedCells(List<Bomb> bombsGiven)
    {
        Map<Cell, Bomb> mapCellBombs = new HashMap<>();
        for (Bomb bomb : bombsGiven)
        {
            mapCellBombs.put(bomb.position(), bomb);
        }
        return mapCellBombs;
    }
    private static Set<Cell> blastedCells(List<Sq<Cell>> listOfBlasts)
    {
        Set<Cell> setOfCellsWithBlasts = new HashSet<>();
        for (Sq<Cell> sqOfBlasts : listOfBlasts)
        {   if (!sqOfBlasts.isEmpty())
            {   
                setOfCellsWithBlasts.add(sqOfBlasts.head());
            }   
        }
        return setOfCellsWithBlasts;
    }
    
    private static List<Sq<Cell>> nextBlasts(List<Sq<Cell>> blasts0, Board board0, List<Sq<Sq<Cell>>> explosions0)
    {
        List<Sq<Cell>> blasts1 = new ArrayList<>();
        for (Sq<Cell> sqOfParticules : blasts0)
        {
            if (!sqOfParticules.isEmpty() && board0.blockAt(sqOfParticules.head()).isFree())
            {
                blasts1.add(sqOfParticules.tail());
            }
        }
        for (Sq<Sq<Cell>> sqOfOneArm : explosions0)
        {
            if (!sqOfOneArm.isEmpty())
            {
                blasts1.add(sqOfOneArm.head()); 
            }    
        }
        return blasts1;
    }

    private static Board nextBoard(Board board0, Set<Cell> consumedBonuses, Set<Cell> blastedCells1)
    {
        
        List<Sq<Block>> listForTheNewBoard = new ArrayList<>();
        for (Cell cell : Cell.ROW_MAJOR_ORDER)
        {
            
            if (blastedCells1.contains(cell))
            {   
                Block elementInDisappearingTicks = board0.blockAt(cell);
                
                Sq<Block> futureBlocks = board0.blocksAt(cell);
                
                for (int i = 0; i < Ticks.BONUS_DISAPPEARING_TICKS; ++i)
                {
                    futureBlocks = futureBlocks.tail();
                }
                boolean bonusAlreadyHit = (futureBlocks.head() == Block.FREE);
                
                
                
                if (board0.blockAt(cell) == Block.DESTRUCTIBLE_WALL)
                {              
                    Block blockSpawned = Block.FREE;
                    int nextInt = RANDOM.nextInt(3);
           
                    switch (nextInt)
                    {                 
                        case 0:
                            blockSpawned = Block.BONUS_BOMB;
                            break;
                        case 1:
                            blockSpawned = Block.BONUS_RANGE;
                            break;
                        case 2:    
                            blockSpawned = Block.FREE;
                            break;
                    }
                    
                    listForTheNewBoard.add(Sq.constant(Block.CRUMBLING_WALL).limit(Ticks.WALL_CRUMBLING_TICKS).concat(Sq.constant(blockSpawned)));
                }
                
                
                
                else if (elementInDisappearingTicks.isBonus() && !bonusAlreadyHit)
                {      
                    listForTheNewBoard.add(Sq.constant(elementInDisappearingTicks).limit(Ticks.BONUS_DISAPPEARING_TICKS).concat(Sq.constant(Block.FREE)));
                }

                else
                {
                    listForTheNewBoard.add(board0.blocksAt(cell).tail());
                }

            }
            else if (consumedBonuses.contains(cell))
            {   
                listForTheNewBoard.add(Sq.constant(Block.FREE));
            }
            else
            {
                listForTheNewBoard.add(board0.blocksAt(cell).tail());
            }

        }
        return new Board(listForTheNewBoard);
    }


    private static List<Player> nextPlayers(List<Player> players0, Map<PlayerID, Bonus> playerBonuses, Set<Cell> bombedCells1, Board board1, Set<Cell> blastedCells1,
            Map<PlayerID, Optional<Direction>> speedChangeEvents)
    {
        List<Player> players1 = new ArrayList<>();
        List<SubCell> posOfConsummedBonuses = new ArrayList<>();   
        
        for (Player onePlayer : players0)
        {
            PlayerID onePlayerID = onePlayer.id();
            Sq<DirectedPosition> maybeChangedDirectedPositions; 
            if (speedChangeEvents.containsKey(onePlayerID))
            {
                Direction wantedDirection = speedChangeEvents.get(onePlayerID).orElse(null);
                   
                if (wantedDirection == null)
                {
                    Sq<DirectedPosition> sqToCentralSubCell = onePlayer.directedPositions().takeWhile(u -> !u.position().isCentral());
                    Sq<DirectedPosition> sqOfStoppedMoves = DirectedPosition.stopped(onePlayer.directedPositions().findFirst(p -> p.position().isCentral()));
                    maybeChangedDirectedPositions = sqToCentralSubCell.concat(sqOfStoppedMoves);
                }
                else if (wantedDirection.isParallelTo(onePlayer.direction()))
                {
                    maybeChangedDirectedPositions = DirectedPosition.moving(new DirectedPosition(onePlayer.position(), wantedDirection));
                }
                else
                {
                    Sq<DirectedPosition> sqToCentralSubCell = onePlayer.directedPositions().takeWhile(u -> !u.position().isCentral());
                    DirectedPosition nextCentralSubCell = onePlayer.directedPositions().findFirst(p -> p.position().isCentral());
                    Sq<DirectedPosition> sqFromCentralSubCell = DirectedPosition.moving(new DirectedPosition(nextCentralSubCell.position(), wantedDirection));
                    maybeChangedDirectedPositions = sqToCentralSubCell.concat(sqFromCentralSubCell);
                }      
            }
            else
            {
                maybeChangedDirectedPositions = onePlayer.directedPositions();
            }
            
            SubCell actualPosition = onePlayer.position();
            if (onePlayer.lifeState().canMove())
            {
                SubCell nextCentralSubCell = maybeChangedDirectedPositions.tail().findFirst(p -> p.position().isCentral()).position();
                if (board1.blockAt(nextCentralSubCell.containingCell()).canHostPlayer())    
                {
                    int distanceToNextCentralSubCell = Math.abs(actualPosition.x() - nextCentralSubCell.x()+ actualPosition.y() - nextCentralSubCell.y());
                    
                    if(!(bombedCells1.contains(nextCentralSubCell.containingCell()) && distanceToNextCentralSubCell == 6))
                    {
                        maybeChangedDirectedPositions = maybeChangedDirectedPositions.tail();
                    }
                }
            }
            
            Sq<LifeState> maybeNewSqOfLifeStates = onePlayer.lifeStates().tail();
            if (blastedCells1.contains(maybeChangedDirectedPositions.head().position().containingCell()) && onePlayer.lifeState().state() == State.VULNERABLE)
            {               
                maybeNewSqOfLifeStates = onePlayer.statesForNextLife();
            }
            
            Player newlyCreatedPlayer = new Player(onePlayerID, maybeNewSqOfLifeStates, maybeChangedDirectedPositions, onePlayer.maxBombs(), onePlayer.bombRange());
            
            if (playerBonuses.containsKey(onePlayerID) && !posOfConsummedBonuses.contains(onePlayer.position())) 
            {
                players1.add(playerBonuses.get(onePlayerID).applyTo(newlyCreatedPlayer));
                posOfConsummedBonuses.add(onePlayer.position());
            }
            
            else
            {
                players1.add(newlyCreatedPlayer);
            }
   
        }
        return players1;
    }

    private static List<Sq<Sq<Cell>>> nextExplosions(List<Sq<Sq<Cell>>> explosions0)
    {
        List<Sq<Sq<Cell>>> listOfNextExplosions = new ArrayList<>();
        for (Sq<Sq<Cell>> armOfExplosion : explosions0)
        {
            if (!armOfExplosion.isEmpty())
            {
                listOfNextExplosions.add(armOfExplosion.tail());
            }

        }
        return listOfNextExplosions;
    }

    private static List<Bomb> newlyDroppedBombs(List<Player> players0, Set<PlayerID> bombDropEvents, List<Bomb> bombs0)
    {
        List<Bomb> newBombs = new ArrayList<>();

        for(Player p : players0)
        {
            if (bombDropEvents.contains(p.id()))
            {
                Bomb bomb = p.newBomb();
                Cell bombPosition = bomb.position();
               
                boolean canPut = true;
                for (Bomb alreadyExistingBomb : bombs0)
                {
                    if (alreadyExistingBomb.position().equals(bombPosition))
                    {
                        canPut = false;
                    }
                }
                
                for (Bomb newBombAlreadyAdded : newBombs)
                {
                    if (newBombAlreadyAdded.position().equals(bombPosition))
                    {
                        canPut = false;
                    }
                }
                
                int bombsBelongingToPlayer = 0;
                for (Bomb oneBomb : bombs0)
                {
                    if (oneBomb.ownerId() == p.id())
                    {
                        bombsBelongingToPlayer++;
                    }
                }
                
                if (canPut && p.isAlive() && bombsBelongingToPlayer < p.maxBombs())
                {
                    newBombs.add(bomb);
                }
            }          
        }       
        return newBombs;
    }
}
