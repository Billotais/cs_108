package ch.epfl.xblast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.server.*;

/**
 * Class Main
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public class Main
{
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
        int requiredPlayers = (args.length != 0 ? Integer.parseInt(args[0]) : 0);
        int compteur = 0;
        
        
        Level level = Level.DEFAULT_LEVEL;
        GameState gameState = level.gameState();
        BoardPainter bPainter = level.boardPainter();
        
        Map<SocketAddress, PlayerID> mapSocketId = new HashMap<>();
        DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
            
        channel.bind(new InetSocketAddress(2016));
        
        while(mapSocketId.size() != requiredPlayers)
        {          
            ByteBuffer buffer = ByteBuffer.allocate(1); 
            SocketAddress senderAddress = channel.receive(buffer);
            
            if (! mapSocketId.containsKey(senderAddress))
            {
                mapSocketId.put(senderAddress, PlayerID.values()[compteur]);
                ++compteur;
            }   
        }

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
