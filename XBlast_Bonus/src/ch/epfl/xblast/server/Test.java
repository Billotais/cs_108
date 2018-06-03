package ch.epfl.xblast.server;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.swing.JFrame;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.client.GameStateDeserializer;
import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.client.XBlastComponent;
import ch.epfl.xblast.server.debug.GameStatePrinter;
import ch.epfl.xblast.server.debug.RandomEventGenerator;
import ch.epfl.xblast.client.KeyboardEventHandler;;


public class Test
{

    public static void main(String[] args) throws  InterruptedException, IOException, URISyntaxException
    { 
        
        
        
        while(true)
        {
            System.out.println(System.nanoTime());
        }
        
        /*
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
        
        ArrayList<Player> arrayList = new ArrayList<>();
        
        arrayList.add(player1);
        arrayList.add(player2);
        arrayList.add(player3);
        arrayList.add(player4);
        
        GameState gameState = new GameState(board, arrayList);
        RandomEventGenerator reg = new RandomEventGenerator(2016, 30, 100);
        
        XBlastComponent xbc  = new XBlastComponent();
        Map<Integer, PlayerAction> kb = new HashMap<>();
        kb.put(KeyEvent.VK_UP, PlayerAction.MOVE_N);
        kb.put(KeyEvent.VK_DOWN,PlayerAction.MOVE_S);
        kb.put(KeyEvent.VK_LEFT, PlayerAction.MOVE_W);
        kb.put(KeyEvent.VK_RIGHT, PlayerAction.MOVE_E);
        kb.put(KeyEvent.VK_SPACE, PlayerAction.DROP_BOMB);
        kb.put(KeyEvent.VK_S,PlayerAction.STOP);
        kb.put(KeyEvent.VK_J,PlayerAction.JOIN_GAME);
        
        
        Consumer<PlayerAction> c = System.out::println;
        xbc.addKeyListener(new KeyboardEventHandler(kb, c));
        xbc.requestFocusInWindow();
        xbc.setFocusable(true);
        
        Map<Block, BlockImage> map = new HashMap<>();
        map.put(Block.FREE, BlockImage.IRON_FLOOR);
        map.put(Block.INDESTRUCTIBLE_WALL, BlockImage.DARK_BLOCK);
        map.put(Block.DESTRUCTIBLE_WALL, BlockImage.EXTRA);
        map.put(Block.CRUMBLING_WALL, BlockImage.EXTRA_O);
        map.put(Block.BONUS_BOMB, BlockImage.BONUS_BOMB);
        map.put(Block.BONUS_RANGE, BlockImage.BONUS_RANGE);
        
        
        
        BoardPainter bPainter = new BoardPainter(map);
        
        
        ch.epfl.xblast.client.GameState gsC = GameStateDeserializer.deserializeGameState(GameStateSerializer.serialize(bPainter, gameState));
        
        xbc.setGameState(gsC, PlayerID.PLAYER_1);
        
        JFrame jFrame = new JFrame("Xblast");
        jFrame.setSize(960, 800);
        jFrame.setVisible(true);
        jFrame.setContentPane(xbc);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        for (int i = 0; i < 3000; ++i)
        {   
            
            Thread.sleep(5);


            gameState = gameState.next(reg.randomSpeedChangeEvents(),reg.randomBombDropEvents());
            gsC = GameStateDeserializer.deserializeGameState(GameStateSerializer.serialize(bPainter, gameState));
            
            xbc.setGameState(gsC, PlayerID.PLAYER_1);
            
        }*/

        
       
    }

}
