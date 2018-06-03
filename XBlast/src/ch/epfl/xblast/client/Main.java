package ch.epfl.xblast.client;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;

public class Main
{   
    /**
     * Class Main
     * 
     * @author Loïs Bilat (258560)
     * @author Nicolas Jeitziner (258692)
     *
     */
    
    private static XBlastComponent xbc;
    private static Consumer<PlayerAction> c;
    /**
     * Runs the game, given a optional given ip address
     * @param args
     *          an array that can contain an String representing the ip address of the server
     * @throws IOException
     *          if there is a probleme while exchanging informations with the server
     * @throws InvocationTargetException
     *          if there is a probleme while using the window of the game interface
     *          
     * @throws InterruptedException
     *          if there is a probleme while using the window of the game interface
     */
    public static void main(String[] args) throws IOException, InvocationTargetException, InterruptedException
    {
        SocketAddress serverAddress = (args.length == 0 ? new InetSocketAddress("localhost", 2016) : new InetSocketAddress(args[0], 2016));
        DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
        ByteBuffer joinBuffer = ByteBuffer.allocate(1);
        ByteBuffer receiveBuffer = ByteBuffer.allocate(1000);
        
        
        joinBuffer.put((byte) PlayerAction.JOIN_GAME.ordinal());
        channel.configureBlocking(false);
        SocketAddress testAddress;
        do
        {   
            channel.send(joinBuffer, serverAddress);
            testAddress = channel.receive(receiveBuffer);
            
        } while (testAddress == null);
        
        List<Byte> receivedList = new ArrayList<>();
        receiveBuffer.rewind();
        
        while(receiveBuffer.hasRemaining())
        {
            receivedList.add(receiveBuffer.get());
        }
        
        
        PlayerID idOfClient = PlayerID.values()[receivedList.get(0)];
        
        GameState gsC = GameStateDeserializer.deserializeGameState(receivedList.subList(1, receivedList.size()-1));
        xbc  = new XBlastComponent();
        xbc.setGameState(gsC, idOfClient);
        c = (p)-> 
        {
            ByteBuffer actionBuffer = ByteBuffer.allocate(1);
            for (int i = 0; i < PlayerAction.values().length; ++i)
            {
                if (PlayerAction.values()[i] == p)
                {
                    
                    actionBuffer.clear();
                    actionBuffer.put((byte)i);
                    try
                    {
                        actionBuffer.rewind();
                        channel.send(actionBuffer, serverAddress);
                    }
                    catch(Exception e ){}
                }
            }     
        };
        SwingUtilities.invokeAndWait(() -> createUI());

        while(true)
        {
            receiveBuffer.clear();
            receivedList.clear();
            channel.receive(receiveBuffer);
            receiveBuffer.rewind();
            
            while(receiveBuffer.hasRemaining())
            {
                receivedList.add(receiveBuffer.get());
            }
            gsC = GameStateDeserializer.deserializeGameState(receivedList.subList(1, receivedList.size()-1));
            
            xbc.setGameState(gsC, idOfClient);
        }    
    }
    
    private static void createUI()
    {
        
        Map<Integer, PlayerAction> kb = new HashMap<>();
        kb.put(KeyEvent.VK_UP, PlayerAction.MOVE_N);
        kb.put(KeyEvent.VK_DOWN,PlayerAction.MOVE_S);
        kb.put(KeyEvent.VK_LEFT, PlayerAction.MOVE_W);
        kb.put(KeyEvent.VK_RIGHT, PlayerAction.MOVE_E);
        kb.put(KeyEvent.VK_SPACE, PlayerAction.DROP_BOMB);
        kb.put(KeyEvent.VK_SHIFT,PlayerAction.STOP);

        xbc.addKeyListener(new KeyboardEventHandler(kb, c));
        xbc.requestFocusInWindow();
        xbc.setFocusable(true);
        

        JFrame jFrame = new JFrame("Xblast");
        jFrame.setSize(983, 752);
        
        jFrame.setVisible(true);
        jFrame.setContentPane(xbc);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
