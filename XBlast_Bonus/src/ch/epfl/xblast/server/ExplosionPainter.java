package ch.epfl.xblast.server;



/**
 * Class ExplosionPainter
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class ExplosionPainter
{
    
    public static final byte BYTE_FOR_EMPTY = 16;
    public static final byte BYTE_FOR_BOMB = 20;
    public static final byte BYTE_FOR_WHITE_BOMB = 21;
    
    private ExplosionPainter(){}
    
    /**
     * Gives the byte represnting the image of a given bomb
     * 
     * @param bomb
     *          the bomb to represent
     * @return the byte representing the bomb
     */
    
    public static byte byteForBomb(Bomb bomb)
    {
        return (Integer.bitCount(bomb.fuseLength()) == 1 ?  BYTE_FOR_WHITE_BOMB : BYTE_FOR_BOMB);
    }
    
    /**
     * Gives the byte represnting the image of a given blast
     * 
     * @param n 
     *          tells if there is another blast on the north of the one we want to represent
     * @param e 
     *          tells if there is another blast on the east of the one we want to represent
     * @param s 
     *          tells if there is another blast on the south of the one we want to represent
     * @param w 
     *          tells if there is another blast on the west of the one we want to represent
     */
    public static byte byteForBlast(boolean n, boolean e, boolean s, boolean w)
    {
        String byteValue = "";
        
        byteValue += ((n) ? "1" : "0");
        byteValue += ((e) ? "1" : "0");
        byteValue += ((s) ? "1" : "0");
        byteValue += ((w) ? "1" : "0");
        
        return byteValue == "" ? 0 :(byte) Byte.parseByte(byteValue, 2);
    }
    
    
}
