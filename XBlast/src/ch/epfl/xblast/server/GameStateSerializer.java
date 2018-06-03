package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.RunLengthEncoder;
import javafx.print.PageLayout;

/**
 * Class GameStateSerializer
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class GameStateSerializer
{

    private GameStateSerializer(){}
    
    /**
     * Serializes the state of the game
     * 
     * @param painter
     *            the board painter
     *            
     * @param gameState
     *            the gameState
     *
     */
    
    public static List<Byte> serialize(BoardPainter painter, GameState gameState)
    {
        List<Byte> finalList = new ArrayList<>();
        List<Byte> boardList = new ArrayList<>();
        List<Byte> blastsList = new ArrayList<>();
        
        for (Cell cell : Cell.spiralOrder())
        {
            boardList.add(painter.byteForCell(gameState.board(), cell));
        }
        boardList = RunLengthEncoder.encode(boardList);
        finalList.add((byte)boardList.size());
        finalList.addAll(boardList);
         
        for (Cell cell : Cell.ROW_MAJOR_ORDER)
        {
            if (! gameState.board().blockAt(cell).isFree())
            {
                blastsList.add(ExplosionPainter.BYTE_FOR_EMPTY);
            }
            else if(gameState.blastedCells().contains(cell))
            {
                blastsList.add(ExplosionPainter.byteForBlast(
                        gameState.blastedCells().contains(cell.neighbor(Direction.N)),
                        gameState.blastedCells().contains(cell.neighbor(Direction.E)),
                        gameState.blastedCells().contains(cell.neighbor(Direction.S)), 
                        gameState.blastedCells().contains(cell.neighbor(Direction.W))));
            }
            else if (gameState.bombedCells().containsKey(cell))
            {
                blastsList.add(ExplosionPainter.byteForBomb(gameState.bombedCells().get(cell)));
            }
            else 
            {
                blastsList.add(ExplosionPainter.BYTE_FOR_EMPTY);
            }
   
        }
        
        blastsList = RunLengthEncoder.encode(blastsList);
        finalList.add((byte) blastsList.size());
        finalList.addAll(blastsList);
        
        
        for (Player player : gameState.players())
        {
            
            finalList.add((byte)player.lives());
            finalList.add((byte)player.position().x());
            finalList.add((byte)player.position().y());
            finalList.add(PlayerPainter.byteForPlayer(gameState.ticks(), player));
        }
       
        finalList.add((byte)((gameState.remainingTime() + 1)/ 2));
        
        return Collections.unmodifiableList(finalList);
    }
    
    
    
    
}
