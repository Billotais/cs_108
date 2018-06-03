package ch.epfl.xblast.client;

import java.awt.Image;


import java.util.List;
import java.util.Objects;

import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.SubCell;


/**
 * Class GameState
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class GameState
{   
    private final List<Player> players;
    private final List<Image> boardImages;
    private final List<Image> explosionsImages;
    private final List<Image> scoreImages;
    private final List<Image> timeImages;
    

    /**
     * Create a new client gamestate
     * @param players
     *          the list of all players of the gamestate
     * @param boardImages
     *          a list containing all the images representing the board
     * @param explosionsImages
     *          a list containing all the images representing the explosions
     * @param scoreImages
     *          a list containing all the images representing the score
     * @param timeImages
     *          a list containing all the images representing the time
     */
    
    public GameState(List<Player> players, List<Image> boardImages,List<Image> explosionsImages,List<Image> scoreImages,List<Image> timeImages) 
    {
        this.players = Objects.requireNonNull(players);
        this.boardImages = Objects.requireNonNull(boardImages);
        this.explosionsImages = Objects.requireNonNull(explosionsImages);
        this.scoreImages = Objects.requireNonNull(scoreImages);
        this.timeImages = Objects.requireNonNull(timeImages);
    }
    /**
     * Give the list of players of the gamestate
     * @return  the list of players
     */
    
    public List<Player> getPlayers()
    {
        return players;
    }


    /**
     * Give the list of board images of the gamestate
     * @return  the list of images of the board
     */

    public List<Image> getBoardImages()
    {  
        return boardImages;
    }

    /**
     * Give the list of explosions images of the gamestate
     * @return  the list of images of the explosions
     */


    public List<Image> getExplosionsImages()
    {
        return explosionsImages;
    }


    /**
     * Give the list of score images of the gamestate
     * @return  the list of images of the score
     */

    public List<Image> getScoreImages()
    {
        return scoreImages;
    }


    /**
     * Give the list of time images of the gamestate
     * @return  the list of images of the time
     */

    public List<Image> getTimeImages()
    {
        return timeImages;
    }


    /**
     * Class Player
     * 
     * @author Loïs Bilat (258560)
     * @author Nicolas Jeitziner (258692)
     *
     */

    public static final class Player
    {
        private final PlayerID id;
        private final int lives;
        private final SubCell position; 
        private final Image image;
        
        /**
         * Create a player for the client side
         * @param id
         *          the PlayerID of the player
         * @param lives
         *          the number of lives of the player
         * @param position
         *          the subcell representing the position of the playsr
         * @param image
         *          the image represnting the player
         */
        public Player(PlayerID id, int lives, SubCell position, Image image)
        {
            this.id = id;
            this.lives = lives;
            this.position = position;
            this.image = image; 
        }
        
        /**
         * Gives the id of the player
         * @return the id of the player
         */
        
        public PlayerID getId()
        {
            return id;
        }

        /**
         * Gives the number of lives of the player
         * @return the number of lives
         */
        
        public int getLives()
        {
            return lives;
        }

        /**
         * Gives the subcell representing the position of the player
         * @return the subcell representing the position
         */
        
        public SubCell getPosition()
        {
            return position;
        }

        /**
         * GIves the image represnting the player 
         * @return the image represnting the player 
         */
        public Image getImage()
        {
            return image;
        }
        
    }
    
}
