package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerID;

/**
 * Class BoardPainter
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class Level
{

    public static final Level DEFAULT_LEVEL = simpleLevel();
    
    
    private final GameState gameState;
    private final BoardPainter boardPainter;
    
    /**
     * Creates the level with a gamestate and a boardpainter
     * 
     * @param gameState
     *            the given gamestate
     * @param boardPainter
     *            the given boardpainter      
     */
     
    public Level(GameState gameState, BoardPainter boardPainter)
    {
        this.boardPainter = boardPainter;
        this.gameState = gameState;
    }
    
    private static Level simpleLevel()
    {
        Block __ = Block.FREE;
        Block XX = Block.INDESTRUCTIBLE_WALL;
        Block xx = Block.DESTRUCTIBLE_WALL;
        Board board = Board.ofQuadrantNWBlocksWalled(
          Arrays.asList(                    
            Arrays.asList(__, __, __, __, __, xx, __),
            Arrays.asList(__, XX, xx, XX, xx, XX, xx),
            Arrays.asList(__, xx, __, __, __, xx, __),
            Arrays.asList(xx, XX, __, XX, XX, XX, XX),
            Arrays.asList(__, xx, __, xx, __, __, __),
            Arrays.asList(xx, XX, xx, XX, xx, XX, __)));
        Player player1 = new Player(PlayerID.PLAYER_1, 3, new Cell(1, 1), 2, 3);
        Player player2 = new Player(PlayerID.PLAYER_2, 3, new Cell(13, 1), 2, 3);
        Player player3 = new Player(PlayerID.PLAYER_3, 3, new Cell(13, 11), 2, 3);
        Player player4 = new Player(PlayerID.PLAYER_4, 3, new Cell(1, 11), 2, 3);
        
        List<Player> arrayList = Arrays.asList(player1,player2,player3,player4);

        
        Map<Block,BlockImage> map = new HashMap<>();
        
        map.put(Block.FREE, BlockImage.IRON_FLOOR);
        map.put(Block.INDESTRUCTIBLE_WALL, BlockImage.DARK_BLOCK);
        map.put(Block.DESTRUCTIBLE_WALL, BlockImage.EXTRA);
        map.put(Block.CRUMBLING_WALL,BlockImage.EXTRA_O);
        map.put(Block.BONUS_BOMB, BlockImage.BONUS_BOMB);
        map.put(Block.BONUS_RANGE, BlockImage.BONUS_RANGE);
        
        return new Level(new GameState(board, arrayList), new BoardPainter(map));
        
    }
    
    /**
     * Returns the gamestate
     * 
     * @return the gamestate
     */
    
    public GameState gameState()
    {
        return gameState;
    }
    
    /**
     * Return the boardpainter
     * 
     * @return the boardpainter
     */
    
    public BoardPainter boardPainter()
    {
        return boardPainter;
    }
}


