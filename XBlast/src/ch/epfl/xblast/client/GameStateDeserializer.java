package ch.epfl.xblast.client;

import java.awt.Image;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.RunLengthEncoder;
import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.client.GameState.Player;


/**
 * Class GameStateDeserializer
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class GameStateDeserializer
{
    private GameStateDeserializer(){}
    
    public static final byte BYTE_FOR_LED_ON = 21;
    public static final byte BYTE_FOR_LED_OFF = 20;
    public static final byte BYTE_FOR_TEXT_MIDDLE = 10;
    public static final byte BYTE_FOR_TEXT_RIGHT = 11;
    public static final byte BYTE_FOR_TILE_VOID = 12;
    
    private static final ImageCollection blockCollection = new ImageCollection("block");
    private static final ImageCollection explosionCollection = new ImageCollection("explosion");
    private static final ImageCollection playerCollection = new ImageCollection("player");
    private static final ImageCollection scoreCollection = new ImageCollection("score");
    /**
     * Create a GameState froma given list of byte representing this gameState
     * @param listOfBytes 
     *          the list used to recreate the gamestate
     * @return a GameState
     */
    public static GameState deserializeGameState(List<Byte> listOfBytes)
    {
        
        List<Byte> provListOfBytes = new ArrayList<>(listOfBytes);
        
        int lengthOfBoardBytes = Byte.toUnsignedInt(provListOfBytes.get(0));
        List<Image> boardImages = boardDeserializer(provListOfBytes.subList(1, lengthOfBoardBytes+1)); 
        
        provListOfBytes = listOfBytes.subList(lengthOfBoardBytes+1, listOfBytes.size());
        
        int lengthOfExplosionBytes = Byte.toUnsignedInt(provListOfBytes.get(0));
        List<Image> explosionsImages = explosionDeserializer(provListOfBytes.subList(1, lengthOfExplosionBytes+1));
        
        provListOfBytes = provListOfBytes.subList(lengthOfExplosionBytes+1, provListOfBytes.size());
        
        List<Player> playersList = playerDeserializer(provListOfBytes.subList(0, 16));
        
        return new GameState(playersList, boardImages, explosionsImages, scoreDeserializer(playersList), timeDeserializer(provListOfBytes.get(16)));
     
        
    }
    
    
    private static List<Image> boardDeserializer(List<Byte> listOfBytes)
    {
        List<Byte> decodedList = RunLengthEncoder.decode(listOfBytes);

        List<Image> list = new ArrayList<>(Collections.nCopies(decodedList.size(), null));
        
    
        for (int i = 0; i < decodedList.size(); ++i)
        {
            byte oneByte = decodedList.get(i);
            int rowMajorIndex = Cell.SPIRAL_ORDER.get(i).hashCode();
            list.set(rowMajorIndex, blockCollection.image(oneByte));
        }
        return list;
    }
    private static List<Image> explosionDeserializer(List<Byte> listOfBytes)
    {  
        List<Image> list = new ArrayList<>();
        
        List<Byte> decodedList = RunLengthEncoder.decode(listOfBytes);
        for (Byte oneByte : decodedList)
        {
            list.add(explosionCollection.imageOrNull(oneByte));
        }
        return list;
        
    }
    private static List<Player> playerDeserializer(List<Byte> listOfByte)
    { 
        List<Player> list = new ArrayList<>();
        for (int i = 0; i < 4; ++i)
        {
            int lives = Byte.toUnsignedInt(listOfByte.get(i*4));
            SubCell pos = new SubCell(Byte.toUnsignedInt(listOfByte.get(i*4 + 1)), Byte.toUnsignedInt(listOfByte.get(i*4 + 2)));
            list.add(new Player(PlayerID.values()[i], lives, pos, playerCollection.imageOrNull(listOfByte.get(i*4 + 3)))); 
        }
        
        return list;
    }
    private static List<Image> timeDeserializer(byte time)
    {
        List<Image> list = new ArrayList<>();
        
        for (int i = 0; i < 60; ++i)
        {
            list.add(i < time ? scoreCollection.image(BYTE_FOR_LED_ON) : scoreCollection.image(BYTE_FOR_LED_OFF));
        }
        return list;
        
    }
    private static List<Image> scoreDeserializer(List<Player> players)
    {     
        List<Image> list = new ArrayList<>();
        
        list.addAll(playerScore(players.get(0)));
        list.addAll(playerScore(players.get(1)));
        list.addAll(Collections.nCopies(8, scoreCollection.image(BYTE_FOR_TILE_VOID)));
        list.addAll(playerScore(players.get(2)));
        list.addAll(playerScore(players.get(3)));
        return list;   
    }
    private static List<Image> playerScore(Player p)
    {
        
        int index = p.getId().ordinal();
        List<Image> list = new ArrayList<>();
        
        list.add(p.getLives() == 0 ? scoreCollection.image((byte)(index*2 + 1)) : scoreCollection.image((byte) (index*2)));
        list.add(scoreCollection.image(BYTE_FOR_TEXT_MIDDLE));
        list.add(scoreCollection.image(BYTE_FOR_TEXT_RIGHT));
        
        return list;
    }
}
