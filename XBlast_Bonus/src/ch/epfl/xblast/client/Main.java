package ch.epfl.xblast.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
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


import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
    private static boolean b;
    private static SocketAddress serverAddress;
    private static PlayerID idOfClient;
    private static DatagramChannel channel;
    private static List<Byte> receivedList ;
    private static ByteBuffer joinBuffer = ByteBuffer.allocate(1);
    private static ByteBuffer receiveBuffer = ByteBuffer.allocate(1000);
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
        JFrame jFrame = new JFrame("Client XBlast");
        jFrame.setSize(700, 752);
        JPanel panel = new JPanel(new BorderLayout());
        
        JTextField ipToSend = new JTextField();
        ipToSend.setPreferredSize(new Dimension(200,100));
        ipToSend.setFont(new Font(Font.SANS_SERIF, 1, 40));
        ipToSend.setBackground(Color.LIGHT_GRAY);
        ipToSend.setText("");
        
        String[] ipList = fileReader("ip.txt");
       
        String[] shortIpList; 
        if (ipList.length >= 4)
        {
            shortIpList = new String[]{ipList[ipList.length - 1],ipList[ipList.length - 2],
                    ipList[ipList.length - 3],ipList[ipList.length - 4]};
        }
        else 
        {
            shortIpList = new String[ipList.length];
            
            for (int i = 0; i < ipList.length; ++i)
            {
                shortIpList[i] = ipList[i];
            }
        }
        
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(shortIpList);      
        JComboBox<String> comboBox = new JComboBox<>(model);       
        
        comboBox.setPreferredSize(new Dimension(200, 100));
        comboBox.setFont(new Font(Font.SANS_SERIF, 1, 40));
        
        panel.add(comboBox,BorderLayout.CENTER);
        JButton launchButton = new JButton("Join Game");
        launchButton.setPreferredSize(new Dimension(200, 100));     
        launchButton.setBackground(Color.LIGHT_GRAY);
        launchButton.setFont(new Font(Font.SANS_SERIF, 1, 40));
        
        launchButton.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                    try
                    {
                        
                    
                        if (ipToSend.getText().equals(""))
                        {
                            serverAddress = new InetSocketAddress((String)comboBox.getSelectedItem(), 2016);
                        }
                        else
                        {
                            writeInFile("ip.txt", ipToSend.getText());
                            serverAddress = new InetSocketAddress(ipToSend.getText(), 2016);
                        }
                        b = true;
                    }
                    catch (Exception ex){} 
                
            }
        });
        
        panel.add(ipToSend,BorderLayout.PAGE_START);
        
        panel.add(launchButton, BorderLayout.PAGE_END);
        panel.setPreferredSize(new Dimension(1000, 700));
        jFrame.setContentPane(panel);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setBackground(Color.WHITE);
        
        while(!b)
        {
            Thread.sleep(10);
        }
        
        
        channel = DatagramChannel.open(StandardProtocolFamily.INET);
        joinBuffer = ByteBuffer.allocate(1);
        receiveBuffer = ByteBuffer.allocate(1000);
        
        
        joinBuffer.put((byte) PlayerAction.JOIN_GAME.ordinal());
        channel.configureBlocking(false);
        SocketAddress testAddress;
        do
        {   
            channel.send(joinBuffer, serverAddress);
            testAddress = channel.receive(receiveBuffer);
            
        } while (testAddress == null);
        
        jFrame.dispose();
        receivedList = new ArrayList<>();
        receiveBuffer.rewind();
        
        while(receiveBuffer.hasRemaining())
        {
            receivedList.add(receiveBuffer.get());
        }
        
        
        idOfClient = PlayerID.values()[receivedList.get(0)];
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
    private static void writeInFile(String fileName, String text)
    {
        String fileAddress = System.getProperty("user.dir") + "/"+ fileName;
        try
        {

            FileWriter fw = new FileWriter(fileAddress, true);
            
            
            BufferedWriter output = new BufferedWriter(fw);
            
            
            output.write(text);
            output.newLine();
            
            output.flush();
            
            
            
            output.close();
            
            
        }
        catch(IOException ioe){}
    }
    private static String[] fileReader(String fileName) throws IOException
    {
        String fileAddress = System.getProperty("user.dir") + "/" + fileName;
        
        FileReader fReader = new FileReader(fileAddress);
        
        BufferedReader input = new BufferedReader(fReader);
        
        Object[] fileCnt = input.lines().toArray();
        String[] linesStrings = new String[fileCnt.length];
        for (int i = 0; i < fileCnt.length; ++i)
        {
            linesStrings[i] = (String)fileCnt[i];
        }
        input.close();
        
        return linesStrings;
    }

}
