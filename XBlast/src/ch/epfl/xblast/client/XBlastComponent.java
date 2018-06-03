package ch.epfl.xblast.client;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import javax.swing.JComponent;


import ch.epfl.xblast.client.GameState;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.client.GameState.Player;


/**
 * Class XBlastComponent extends JComponent
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

@SuppressWarnings("serial")
public final class XBlastComponent extends JComponent
{
    private static final int BLOCK_IMAGE_HEIGHT = 48;
    private static final int BLOCK_IMAGE_WIDTH = 64;
    private static final int SCORE_IMAGE_SIDE = 48;
    private static final int TIME_IMAGE_WIDTH = 16;
    private static final int PLAYER_SCORE_Y_POS = 659;
    
    private GameState gameState;
    private PlayerID id;
    
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(960, 688);
    }
    
    /**
     * Draw on the component
     * @param g0
     *          a Graphics element given
     */
    
    @Override
    public void paintComponent(Graphics g0)
    {
        Font font = new Font("Arial", Font.BOLD, 25);
        List<Player> playersList = gameState.getPlayers();
        
        Graphics2D g = (Graphics2D) g0;
        
        for (Cell cell : Cell.ROW_MAJOR_ORDER)
        {
            g.drawImage(gameState.getBoardImages().get(cell.rowMajorIndex()), cell.x()*BLOCK_IMAGE_WIDTH, cell.y()*BLOCK_IMAGE_HEIGHT, null);
            g.drawImage(gameState.getExplosionsImages().get(cell.rowMajorIndex()), cell.x()*BLOCK_IMAGE_WIDTH, cell.y()*BLOCK_IMAGE_HEIGHT, null);
        }
        
        
        
        for (int l = 0; l < gameState.getScoreImages().size(); ++l)
        {
            g.drawImage(gameState.getScoreImages().get(l), SCORE_IMAGE_SIDE*l, Cell.ROWS*BLOCK_IMAGE_HEIGHT, null);  
        }
        for (int t = 0; t < gameState.getTimeImages().size(); ++t)
        {
            g.drawImage(gameState.getTimeImages().get(t), TIME_IMAGE_WIDTH*t, Cell.ROWS*BLOCK_IMAGE_HEIGHT + SCORE_IMAGE_SIDE, null);
        }
        
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(playersList.get(0).getLives()), 96, PLAYER_SCORE_Y_POS);
        g.drawString(Integer.toString(playersList.get(1).getLives()), 240, PLAYER_SCORE_Y_POS);
        g.drawString(Integer.toString(playersList.get(2).getLives()), 768, PLAYER_SCORE_Y_POS);
        g.drawString(Integer.toString(playersList.get(3).getLives()), 912, PLAYER_SCORE_Y_POS);
       
        Comparator<Player> pComparator = (p1,p2) ->
        {
            
            int index = id.ordinal();
            if (p1.getPosition().y() != p2.getPosition().y())
            {
                return Integer.compare(p1.getPosition().y(), p2.getPosition().y());
            }
            else
            {
                return Integer.compare((playersList.indexOf(p2)+(4-index))%4, (playersList.indexOf(p1)+(4-index))%4);
            }
              
        };
    
        Collections.sort(playersList, pComparator);
        
        for (Player player : playersList)
        {
            g.drawImage(player.getImage(), 4*player.getPosition().x()-24, 3*player.getPosition().y()-52, null);
        }
    
        
        
    }
    
    /**
     * Set the GameState of the actual state of the component for a specific player and repaint it
     * @param gs
     *          the given gameState
     * @param pId   
     *          the id the the player we want to draw for
     *          
     */
    public void setGameState(GameState gs, PlayerID pId)
    {
        this.gameState = gs;
        this.id = pId;
        repaint();
    }
}
