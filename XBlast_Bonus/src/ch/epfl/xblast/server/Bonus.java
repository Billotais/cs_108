package ch.epfl.xblast.server;

/**
 * Enumeration Bonus
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */
public enum Bonus
{
    INC_BOMB
    {
        @Override
        public Player applyTo(Player player,Player otherPlayer)
        {
            return (player.maxBombs() < 9 ? player.withMaxBombs(player.maxBombs()+1) : player);
        }
    },
    
    INC_RANGE
    {
        @Override
        public Player applyTo(Player player,Player otherPlayer)
        {
            return (player.bombRange() < 9 ? player.withBombRange(player.bombRange()+1) : player);
        }
    },
    INC_LIFE
    {
        @Override
        public Player applyTo(Player player,Player otherPlayer)
        {
            return (player.withLife(player.lives()+1));
        }
    },
    TP
    {
        @Override
        public Player applyTo(Player player,Player otherPlayer)
        {
            return player.withPosOf(otherPlayer);
        }
    },
    BLIND
    {
        @Override
        public Player applyTo(Player player,Player otherPlayer)
        {
            return player;
        }
    }
    ;
    
    
    abstract public Player applyTo(Player player, Player otherPlayer);
    
   
}
