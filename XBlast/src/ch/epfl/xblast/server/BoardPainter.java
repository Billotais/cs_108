package ch.epfl.xblast.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;

/**
 * Class BoardPainter
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class BoardPainter
{
    
    private final Map<Block, BlockImage> pallet;
    
    /**
     * Create a BoardPainter, given a map that assocites every block to an image
     * 
     * @param pallet 
     *          the map 
     */
    public BoardPainter(Map<Block, BlockImage> pallet)
    {
        this.pallet = new HashMap<>(Objects.requireNonNull(pallet));
    }
    /**
     * Return a byte correponding to the image representing the given cell
     * 
     * @param board
     *          the board we want to represent
     * @param cell
     *          the cell we want the image of
     * @return the byte correponding to the cell's image
     */
    public byte byteForCell(Board board, Cell cell)
    {
        Block block = board.blockAt(cell);
        
        BlockImage associatedImage = pallet.get(block);
        if (board.blockAt(cell.neighbor(Direction.W)).castsShadow() && block == Block.FREE)
        {
            associatedImage = BlockImage.IRON_FLOOR_S;
        }
        
        /*BlockImage[] taBlockImages = BlockImage.values();
        
        for (int i = 0; i<taBlockImages.length; ++i)
        {
            if (taBlockImages[i] == associatedImage)
            {
                return (byte)i;
            }
        }*/
        return (byte) associatedImage.ordinal();
        
    }
}
