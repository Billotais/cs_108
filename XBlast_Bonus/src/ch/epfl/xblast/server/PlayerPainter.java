package ch.epfl.xblast.server;

import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.server.Player.LifeState;
import ch.epfl.xblast.server.Player.LifeState.State;

/**
 * Class PlayerPainter
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class PlayerPainter
{
    private PlayerPainter(){}
    
    /**
     * Return a byte correponding to the image representing the given Player at a given tick
     * 
     * @param tick
     *          the moment we want to represent the player
     * @param player
     *          the player we want the image of
     * @return the byte correponding to the player's image
     */
    
    public static byte byteForPlayer(int tick, Player player)
    {
        PlayerID id = player.id();
        Direction dir = player.direction();
        LifeState state = player.lifeState();  
        SubCell pos = player.position();
        
        byte returnByte = 0;
        
        switch(id)
        {
            case PLAYER_1:
                returnByte = 0;
                break;
            case PLAYER_2:
                returnByte = 20;
                break;
            case PLAYER_3:
                returnByte = 40;
                break;
            case PLAYER_4:
                returnByte = 60;
                break;
        }
        
        if (state.state() == State.INVULNERABLE && tick % 2 == 1)
        {
            returnByte = 80;
        }
        
        else if (state.state() == State.DYING)
        {
            return (byte)((state.lives() == 1) ? (returnByte + 13) : (returnByte + 12));
        }
        else if (state.state() == State.DEAD)
        {
            return (byte)(returnByte + 15);
        }
        
    
        returnByte += (byte) (dir.ordinal()*3);
        
        if (dir.isHorizontal())
        {
            switch (pos.x() % 4)
            {
                case 1: returnByte += 1;
                        break;
                case 3: returnByte += 2;
                        break;
            }
        }
        else
        {
            switch (pos.y() % 4)
            {
                case 1: returnByte += 1;
                        break;
                case 3: returnByte += 2;
                        break;
            } 
        }
        
        return returnByte;  
    }
}
