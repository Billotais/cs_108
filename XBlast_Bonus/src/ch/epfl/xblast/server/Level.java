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
    private static int playerNumber = 4;
    
    public static final Level DEFAULT_LEVEL = simpleLevel();
    public static final Level LEVEL1 = level1();
    public static final Level LEVEL2 = level2();
    public static final Level LEVEL3 = level3();
    public static final Level ARENA = arena();
    public static final Level ONLY_BONUS = onlyBonuses();
    
    public static List<Level> levelList= new ArrayList<>(Arrays.asList(simpleLevel(),level1(),level2(),level3(),arena(),onlyBonuses()));
    private final GameState gameState;
    private final BoardPainter boardPainter;
    private final String name;
    
    
    public Level(GameState gameState, BoardPainter boardPainter, String name)
    {
        this.boardPainter = boardPainter;
        this.gameState = gameState;
        this.name = name;
        
        
    }
    public static void setPlayers(int number)
    {
        playerNumber = number;
        levelList= new ArrayList<>(Arrays.asList(simpleLevel(),level1(),level2(),level3(),arena(),onlyBonuses()));
        
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
        map.put(Block.BONUS_LIFE, BlockImage.BONUS_LIFE);
        map.put(Block.BONUS_TP, BlockImage.BONUS_TP);
        map.put(Block.BONUS_BLIND, BlockImage.BONUS_BLIND);
        
        return new Level(new GameState(board, trueList(arrayList)), new BoardPainter(map),"Niveau par défaut");
        
    }
    private static Level level1()
    {
        Block __ = Block.FREE;
        Block XX = Block.INDESTRUCTIBLE_WALL;
        Block xx = Block.DESTRUCTIBLE_WALL;
        
        Board board = Board.ofRows(
                Arrays.asList(
                        Arrays.asList(XX, XX, XX, xx, XX, XX, XX, __, XX, XX, XX, xx, XX, XX, XX),
                        Arrays.asList(XX, __, xx, __, __, __, xx, __, xx, __, __, __, xx, __, XX),
                        Arrays.asList(XX, __, __, xx, XX, xx, __, XX, __, xx, XX, xx, __, __, XX),
                        Arrays.asList(XX, xx, __, __, xx, __, __, xx, __, __, xx, __, __, xx, XX),
                        Arrays.asList(XX, __, XX, __, XX, __, XX, XX, XX, __, XX, __, XX, __, XX),
                        Arrays.asList(XX, __, XX, xx, __, xx, __, XX, __, xx, __, xx, XX, __, XX),
                        Arrays.asList(__, __, XX, __, xx, XX, __, __, __, XX, xx, __, XX, __, __),
                        Arrays.asList(XX, __, XX, xx, __, xx, __, XX, __, xx, __, xx, XX, __, XX),
                        Arrays.asList(XX, __, XX, __, XX, __, XX, XX, XX, __, XX, __, XX, __, XX),
                        Arrays.asList(XX, xx, __, __, xx, __, __, xx, __, __, xx, __, __, xx, XX),
                        Arrays.asList(XX, __, __, xx, XX, xx, __, XX, __, xx, XX, xx, __, __, XX),
                        Arrays.asList(XX, __, xx, __, __, __, xx, __, xx, __, __, __, xx, __, XX),
                        Arrays.asList(XX, XX, XX, xx, XX, XX, XX, __, XX, XX, XX, xx, XX, XX, XX)));
        
        Player player1 = new Player(PlayerID.PLAYER_1, 3, new Cell(5, 4), 2, 2);
        Player player2 = new Player(PlayerID.PLAYER_2, 3, new Cell(9, 4), 2, 2);
        Player player3 = new Player(PlayerID.PLAYER_3, 3, new Cell(9, 8), 2, 2);
        Player player4 = new Player(PlayerID.PLAYER_4, 3, new Cell(5, 8), 2, 2);
        
        List<Player> arrayList = Arrays.asList(player1,player2,player3,player4);

        
        Map<Block,BlockImage> map = new HashMap<>();
        
        map.put(Block.FREE, BlockImage.IRON_FLOOR);
        map.put(Block.INDESTRUCTIBLE_WALL, BlockImage.DARK_BLOCK);
        map.put(Block.DESTRUCTIBLE_WALL, BlockImage.EXTRA);
        map.put(Block.CRUMBLING_WALL,BlockImage.EXTRA_O);
        map.put(Block.BONUS_BOMB, BlockImage.BONUS_BOMB);
        map.put(Block.BONUS_RANGE, BlockImage.BONUS_RANGE);
        map.put(Block.BONUS_LIFE, BlockImage.BONUS_LIFE);
        map.put(Block.BONUS_TP, BlockImage.BONUS_TP);
        map.put(Block.BONUS_BLIND, BlockImage.BONUS_BLIND);
        
        return new Level(new GameState(board, trueList(arrayList)), new BoardPainter(map),"Niveau 1");
        
    }
    private static Level level2()
    {
        Block __ = Block.FREE;
        Block XX = Block.INDESTRUCTIBLE_WALL;
        Block xx = Block.DESTRUCTIBLE_WALL;
        
        Board board = Board.ofRows(
                Arrays.asList(
                        Arrays.asList(__, __, __, __, __, __, __, __, __, __, __, __, __, __, __),
                        Arrays.asList(__, XX, XX, __, XX, XX, XX, xx, XX, XX, XX, __, XX, XX, __),
                        Arrays.asList(__, XX, __, xx, __, xx, __, __, __, xx, __, xx, __, XX, __),
                        Arrays.asList(__, xx, __, __, xx, __, __, xx, __, __, xx, __, __, xx, __),
                        Arrays.asList(__, XX, __, __, __, __, __, __, __, __, __, __, __, XX, __),
                        Arrays.asList(__, XX, __, xx, __, xx, __, __, __, xx, __, xx, __, XX, __),
                        Arrays.asList(__, xx, __, __, xx, __, xx, xx, xx, __, xx, __, __, xx, __),
                        Arrays.asList(__, XX, __, xx, __, xx, __, __, __, xx, __, xx, __, XX, __),
                        Arrays.asList(__, XX, __, __, __, __, __, __, __, __, __, __, __, XX, __),
                        Arrays.asList(__, xx, __, __, xx, __, __, xx, __, __, xx, __, __, xx, __),
                        Arrays.asList(__, XX, __, xx, __, xx, __, __, __, xx, __, xx, __, XX, __),
                        Arrays.asList(__, XX, XX, __, XX, XX, XX, xx, XX, XX, XX, __, XX, XX, __),
                        Arrays.asList(__, __, __, __, __, __, __, __, __, __, __, __, __, __, __)));
        
        Player player1 = new Player(PlayerID.PLAYER_1, 3, new Cell(4, 4), 4, 5);
        Player player2 = new Player(PlayerID.PLAYER_2, 3, new Cell(10, 4), 4, 5);
        Player player3 = new Player(PlayerID.PLAYER_3, 3, new Cell(10, 8), 4, 5);
        Player player4 = new Player(PlayerID.PLAYER_4, 3, new Cell(4, 8), 4, 5);
        
        List<Player> arrayList = Arrays.asList(player1,player2,player3,player4);

        
        Map<Block,BlockImage> map = new HashMap<>();
        
        map.put(Block.FREE, BlockImage.IRON_FLOOR);
        map.put(Block.INDESTRUCTIBLE_WALL, BlockImage.DARK_BLOCK);
        map.put(Block.DESTRUCTIBLE_WALL, BlockImage.EXTRA);
        map.put(Block.CRUMBLING_WALL,BlockImage.EXTRA_O);
        map.put(Block.BONUS_BOMB, BlockImage.BONUS_BOMB);
        map.put(Block.BONUS_RANGE, BlockImage.BONUS_RANGE);
        map.put(Block.BONUS_LIFE, BlockImage.BONUS_LIFE);
        map.put(Block.BONUS_TP, BlockImage.BONUS_TP);
        map.put(Block.BONUS_BLIND, BlockImage.BONUS_BLIND);
        
        return new Level(new GameState(board, trueList(arrayList)), new BoardPainter(map),"Niveau 2");
        
    }
    private static Level level3()
    {
        Block __ = Block.FREE;
        Block XX = Block.INDESTRUCTIBLE_WALL;
        Block xx = Block.DESTRUCTIBLE_WALL;
        
        Board board = Board.ofRows(
                Arrays.asList(
                        Arrays.asList(__, __, __, __, xx, __, __, xx, __, __, xx, __, __, __, __),
                        Arrays.asList(__, __, xx, __, __, __, __, __, __, __, __, __, xx, __, __),
                        Arrays.asList(__, XX, __, XX, __, xx, __, XX, __, xx, __, XX, __, XX, __),
                        Arrays.asList(__, __, __, xx, __, __, xx, __, xx, __, __, xx, __, __, __),
                        Arrays.asList(__, XX, __, XX, XX, XX, __, XX, __, XX, XX, XX, __, XX, __),
                        Arrays.asList(__, XX, xx, __, xx, __, __, xx, __, __, xx, __, xx, XX, __),
                        Arrays.asList(xx, __, __, XX, __, __, xx, XX, xx, __, __, XX, __, __, xx),
                        Arrays.asList(__, XX, xx, __, xx, __, __, xx, __, __, xx, __, xx, XX, __),
                        Arrays.asList(__, XX, __, XX, XX, XX, __, XX, __, XX, XX, XX, __, XX, __),
                        Arrays.asList(__, __, __, xx, __, __, xx, __, xx, __, __, xx, __, __, __),
                        Arrays.asList(__, XX, __, XX, __, xx, __, XX, __, xx, __, XX, __, XX, __),
                        Arrays.asList(__, __, xx, __, __, __, __, __, __, __, __, __, xx, __, __),
                        Arrays.asList(__, __, __, __, xx, __, __, xx, __, __, xx, __, __, __, __)));
        
        Player player1 = new Player(PlayerID.PLAYER_1, 3, new Cell(2, 2), 4, 5);
        Player player2 = new Player(PlayerID.PLAYER_2, 3, new Cell(12, 2), 4, 5);
        Player player3 = new Player(PlayerID.PLAYER_3, 3, new Cell(12, 10), 4, 5);
        Player player4 = new Player(PlayerID.PLAYER_4, 3, new Cell(2, 10), 4, 5);
        
        List<Player> arrayList = Arrays.asList(player1,player2,player3,player4);

        
        Map<Block,BlockImage> map = new HashMap<>();
        
        map.put(Block.FREE, BlockImage.IRON_FLOOR);
        map.put(Block.INDESTRUCTIBLE_WALL, BlockImage.DARK_BLOCK);
        map.put(Block.DESTRUCTIBLE_WALL, BlockImage.EXTRA);
        map.put(Block.CRUMBLING_WALL,BlockImage.EXTRA_O);
        map.put(Block.BONUS_BOMB, BlockImage.BONUS_BOMB);
        map.put(Block.BONUS_RANGE, BlockImage.BONUS_RANGE);
        map.put(Block.BONUS_LIFE, BlockImage.BONUS_LIFE);
        map.put(Block.BONUS_TP, BlockImage.BONUS_TP);
        map.put(Block.BONUS_BLIND, BlockImage.BONUS_BLIND);
        
        return new Level(new GameState(board, trueList(arrayList)), new BoardPainter(map),"Niveau 3");
        
    }
    
    private static Level arena()
    {
        Block __ = Block.FREE;
        Block XX = Block.INDESTRUCTIBLE_WALL;
        Block xx = Block.DESTRUCTIBLE_WALL;
        
        Board board = Board.ofRows(
                Arrays.asList(
                        Arrays.asList(xx, __, __, __, __, __, xx, __, __, __, __, __, xx, __, __),
                        Arrays.asList(__, __, __, __, xx, __, __, __, __, __, xx, __, __, __, __),
                        Arrays.asList(__, __, xx, __, __, __, __, __, xx, __, __, __, __, __, xx),
                        Arrays.asList(xx, __, __, __, __, __, xx, __, __, __, __, __, xx, __, __),
                        Arrays.asList(__, __, __, __, xx, __, __, __, __, __, xx, __, __, __, __),
                        Arrays.asList(__, __, xx, __, __, __, __, __, xx, __, __, __, __, __, xx),
                        Arrays.asList(xx, __, __, __, __, __, xx, __, __, __, __, __, xx, __, __),
                        Arrays.asList(__, __, __, __, xx, __, __, __, __, __, xx, __, __, __, __),
                        Arrays.asList(__, __, xx, __, __, __, __, __, xx, __, __, __, __, __, xx),
                        Arrays.asList(xx, __, __, __, __, __, xx, __, __, __, __, __, xx, __, __),
                        Arrays.asList(__, __, __, __, xx, __, __, __, __, __, xx, __, __, __, __),
                        Arrays.asList(__, __, xx, __, __, __, __, __, xx, __, __, __, __, __, xx),
                        Arrays.asList(xx, __, __, __, xx, __, xx, __, __, __, __, __, xx, __, __)));
                        
        
        Player player1 = new Player(PlayerID.PLAYER_1, 3, new Cell(4, 3), 5, 10);
        Player player2 = new Player(PlayerID.PLAYER_2, 3, new Cell(10, 3), 5, 10);
        Player player3 = new Player(PlayerID.PLAYER_3, 3, new Cell(10, 8), 5, 10);
        Player player4 = new Player(PlayerID.PLAYER_4, 3, new Cell(4, 8), 5, 10);
        
        List<Player> arrayList = Arrays.asList(player1,player2,player3,player4);

        
        Map<Block,BlockImage> map = new HashMap<>();
        
        map.put(Block.FREE, BlockImage.IRON_FLOOR);
        map.put(Block.INDESTRUCTIBLE_WALL, BlockImage.DARK_BLOCK);
        map.put(Block.DESTRUCTIBLE_WALL, BlockImage.EXTRA);
        map.put(Block.CRUMBLING_WALL,BlockImage.EXTRA_O);
        map.put(Block.BONUS_BOMB, BlockImage.BONUS_BOMB);
        map.put(Block.BONUS_RANGE, BlockImage.BONUS_RANGE);
        map.put(Block.BONUS_LIFE, BlockImage.BONUS_LIFE);
        map.put(Block.BONUS_TP, BlockImage.BONUS_TP);
        map.put(Block.BONUS_BLIND, BlockImage.BONUS_BLIND);
        
        return new Level(new GameState(board, trueList(arrayList)), new BoardPainter(map),"Niveau Arène");
        
    }
    private static Level onlyBonuses()
    {
        Block __ = Block.FREE;
        Block XX = Block.INDESTRUCTIBLE_WALL;
        Block xx = Block.DESTRUCTIBLE_WALL;
        
        Board board = Board.ofRows(
                Arrays.asList(
                        Arrays.asList(xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx),
                        Arrays.asList(xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx),
                        Arrays.asList(xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx),
                        Arrays.asList(xx, xx, xx, xx, __, xx, xx, xx, xx, xx, __, xx, xx, xx, xx),
                        Arrays.asList(xx, xx, xx, xx, __, __, xx, xx, xx, __, __, xx, xx, xx, xx),
                        Arrays.asList(xx, xx, xx, xx, __, xx, xx, xx, xx, xx, __, xx, xx, xx, xx),
                        Arrays.asList(xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx),
                        Arrays.asList(xx, xx, xx, xx, __, xx, xx, xx, xx, xx, __, xx, xx, xx, xx),
                        Arrays.asList(xx, xx, xx, xx, __, __, xx, xx, xx, __, __, xx, xx, xx, xx),
                        Arrays.asList(xx, xx, xx, xx, __, xx, xx, xx, xx, xx, __, xx, xx, xx, xx),
                        Arrays.asList(xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx),
                        Arrays.asList(xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx),
                        Arrays.asList(xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx, xx)));
                        
        
        Player player1 = new Player(PlayerID.PLAYER_1, 1, new Cell(4, 3), 5, 2);
        Player player2 = new Player(PlayerID.PLAYER_2, 1, new Cell(10, 3), 5, 2);
        Player player3 = new Player(PlayerID.PLAYER_3, 1, new Cell(10, 8), 5, 2);
        Player player4 = new Player(PlayerID.PLAYER_4, 1, new Cell(4, 8), 5, 2);
        
        List<Player> arrayList = Arrays.asList(player1,player2,player3,player4);

        
        Map<Block,BlockImage> map = new HashMap<>();
        
        map.put(Block.FREE, BlockImage.IRON_FLOOR);
        map.put(Block.INDESTRUCTIBLE_WALL, BlockImage.DARK_BLOCK);
        map.put(Block.DESTRUCTIBLE_WALL, BlockImage.EXTRA);
        map.put(Block.CRUMBLING_WALL,BlockImage.EXTRA_O);
        map.put(Block.BONUS_BOMB, BlockImage.BONUS_BOMB);
        map.put(Block.BONUS_RANGE, BlockImage.BONUS_RANGE);
        map.put(Block.BONUS_LIFE, BlockImage.BONUS_LIFE);
        map.put(Block.BONUS_TP, BlockImage.BONUS_TP);
        map.put(Block.BONUS_BLIND, BlockImage.BONUS_BLIND);
        
        return new Level(new GameState(board, trueList(arrayList)), new BoardPainter(map),"Niveau Bonus uniquement");
        
    }
    
    public GameState gameState()
    {
        return gameState;
    }
    public BoardPainter boardPainter()
    {
        return boardPainter;
    }
    public String toString()
    {
        return this.name;
    }
    public static List<Player> trueList(List<Player> list)
    {
        List<Player> finalList = new ArrayList<>();
        for (int i = 0; i < PlayerID.values().length; ++i)
        {
            
            if (i < playerNumber)
            {
                finalList.add(list.get(i));
            }
            else
            {
                Player player = list.get(i);
                finalList.add(new Player(player.id(), 0, player.position().containingCell(), 0, 0));
            }
        }
        return finalList;
        
    }
}


