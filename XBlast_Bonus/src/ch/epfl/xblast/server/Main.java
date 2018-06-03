package ch.epfl.xblast.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JTextArea;



import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;

/**
 * Class Main
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public class Main
{
    private static boolean waitingForPlayers = true;
    private static DatagramChannel channel;
    private static Map<SocketAddress, PlayerID> mapSocketId;
    private static Level selectedLevel = Level.DEFAULT_LEVEL;
    public static boolean SUBIT_DEATH = false;
    /**
     * Runs the server
     * 
     * @param args
     *            the array that will define how many players play the game
     *            
     * @throws IOException
     *            when there is a problem with the channel
     *            
     * @throws InterruptedException
     *            when there is a problem with the sleep method of Thread.
     *            
     * @param range
     *            the range of the explosion
     */
    public static void main(String[] args) throws IOException, InterruptedException
    {
        mapSocketId = new HashMap<>();
        
        channel = DatagramChannel.open(StandardProtocolFamily.INET);
        channel.bind(new InetSocketAddress(2016));
        channel.configureBlocking(false);
        
        String[] levelList = {Level.DEFAULT_LEVEL.toString(),
                             Level.LEVEL1.toString(),
                             Level.LEVEL2.toString(),
                             Level.LEVEL3.toString(),
                             Level.ARENA.toString(),
                             Level.ONLY_BONUS.toString()};
        
        
        JFrame jFrame = new JFrame("Serveur XBlast");
        jFrame.setSize(700, 752);
        JPanel panel = new JPanel(new BorderLayout()); 
        JPanel topPanel = new JPanel(new BorderLayout()); 
        
        panel.add(topPanel,BorderLayout.PAGE_START);
        
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(levelList);      
        JComboBox<String> comboBox = new JComboBox<>(model);       
        comboBox.setPreferredSize(new Dimension(200, 100));
        comboBox.setFont(new Font(Font.SANS_SERIF, 1, 40));
        topPanel.add(comboBox,BorderLayout.PAGE_START);
        
        JCheckBox chooseSubitDeathBox = new JCheckBox("Activer la mort subite");
        
        chooseSubitDeathBox.setPreferredSize(new Dimension(200, 100));
        chooseSubitDeathBox.setFont(new Font(Font.SANS_SERIF, 1, 40));
        topPanel.add(chooseSubitDeathBox,BorderLayout.PAGE_END);
        
        JButton launchButton = new JButton("Lancer la partie");
        launchButton.setPreferredSize(new Dimension(200, 100));
        
        launchButton.setBackground(Color.LIGHT_GRAY);
        launchButton.setFont(new Font(Font.SANS_SERIF, 1, 40));
        
        launchButton.addActionListener(
                new ActionListener(){
                    public void actionPerformed(
                            ActionEvent e){
                                            waitingForPlayers  = false;
                                            SUBIT_DEATH = chooseSubitDeathBox.isSelected();
                                            
                                            Level.setPlayers(mapSocketId.size());
                                            for (Level level : Level.levelList)
                                            {
                                                if (level.toString().equals(comboBox.getSelectedItem()))
                                                {
                                                    selectedLevel = level;
                                                }
                                            }
                                            
                                            jFrame.dispose();
                                            
                                            try
                                            {
                                                launchGame();
                                            } catch(Exception ex){}
                                            
                                            
                                          }
                                    }
                            );
        panel.add(launchButton, BorderLayout.PAGE_END);
        panel.setPreferredSize(new Dimension(1000, 700));
        jFrame.setContentPane(panel);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setBackground(Color.WHITE);
        JTextArea playerLabel = new JTextArea("Liste des joueurs connectés : \n");
        
        
        playerLabel.setFont(new Font(Font.SANS_SERIF, 1, 40));
        panel.add(playerLabel,BorderLayout.CENTER);
        panel.repaint();
        jFrame.setContentPane(panel);
        int compteur = 0;
        
        while (waitingForPlayers)
        {
            ByteBuffer buffer = ByteBuffer.allocate(1);
            
            SocketAddress senderAddress = channel.receive(buffer);
            
            if ( ! mapSocketId.containsKey(senderAddress) && senderAddress != null)
            {
                mapSocketId.put(senderAddress, PlayerID.values()[compteur]);
                playerLabel.setText(String.format(playerLabel.getText() + senderAddress +" est connecté en tant que "+ PlayerID.values()[compteur]+ "\n"));
                
                panel.repaint();
                
                ++compteur;
            }
            
        }
    }
    
    
    public static void launchGame() throws IOException, InterruptedException
    {
        
        
        GameState gameState = selectedLevel.gameState();
        BoardPainter bPainter = selectedLevel.boardPainter();
        
        
        

        long initialTime = System.nanoTime();
        
        for (int i = 0; i < Ticks.TOTAL_TICKS; ++i)
        {
            
            List<Byte> toSend = GameStateSerializer.serialize(bPainter, gameState);
            ByteBuffer sendBuffer = ByteBuffer.allocate(toSend.size());
              
            
            for(byte b : toSend)
            {
                sendBuffer.put(b);
            }
            
            
            
            for (SocketAddress s : mapSocketId.keySet())
            {
                sendBuffer.rewind();
                
                ByteBuffer bForPlayer = ByteBuffer.allocate(toSend.size() + 1);
                
                bForPlayer.put((byte) (mapSocketId.get(s)).ordinal());
                bForPlayer.put(sendBuffer);
                bForPlayer.rewind();
                
                channel.send(bForPlayer, s);
                
            }
            long actualTime = System.nanoTime();
            long timeOfNextTick = initialTime + (long)(i + 1)*(long) Ticks.TICK_NANOSECOND_DURATION;
            long timeUntilNextTick = timeOfNextTick - actualTime;

            if (gameState.isGameOver())
            {
                break;
            }
            if (timeUntilNextTick > 0)
            {
                Thread.sleep((long)(timeUntilNextTick / 1000000), (int)(timeUntilNextTick % 1000000));         
            }
            channel.configureBlocking(false);
            
            ByteBuffer buffer = ByteBuffer.allocate(1);
            
            SocketAddress senderAddress = channel.receive(buffer);
            
            Set<PlayerID> dropBombAsked = new HashSet<>();
            Map<PlayerID, Optional<Direction>> mapDirection = new HashMap<>();

            while(senderAddress != null)
            {
                buffer.rewind();
                
                PlayerAction action = PlayerAction.values()[buffer.get()];

                switch (action)
                {
                    case DROP_BOMB:
                        dropBombAsked.add(mapSocketId.get(senderAddress));
                        break;
                    case STOP:
                        mapDirection.put(mapSocketId.get(senderAddress), Optional.empty());
                        break;
                    case MOVE_E:
                        mapDirection.put(mapSocketId.get(senderAddress), Optional.of(Direction.E));
                        break;
                    case MOVE_N:
                        mapDirection.put(mapSocketId.get(senderAddress), Optional.of(Direction.N));
                        break;
                    case MOVE_W:
                        mapDirection.put(mapSocketId.get(senderAddress), Optional.of(Direction.W));
                        break;
                    case MOVE_S:
                        mapDirection.put(mapSocketId.get(senderAddress), Optional.of(Direction.S));
                        break;
                    default:
                        break;  
                        
                }
                
                
                buffer.clear();
                senderAddress = channel.receive(buffer);   
            }

            gameState = gameState.next(mapDirection,dropBombAsked);
            

        }
        System.out.println("Le vainqueur est : " + gameState.winner());
    }
}
