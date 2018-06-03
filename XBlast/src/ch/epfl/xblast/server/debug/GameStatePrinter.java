package ch.epfl.xblast.server.debug;

import java.util.List;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.Bomb;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Player;

public final class GameStatePrinter {
    private GameStatePrinter() {}

    public static void printGameState(GameState s) {
        List<Player> ps = s.alivePlayers();
        Board board = s.board();
        String boardOfString = "";
        
        for (int y = 0; y < Cell.ROWS; ++y) {
            
            xLoop: for (int x = 0; x < Cell.COLUMNS; ++x) {
                Cell c = new Cell(x, y);
                for (Player p: ps) {
                    if (p.position().containingCell().equals(c)) {
                        String newText;
                        if (p.position().isCentral()) {
                            newText = "\u001b[35m" + stringForPlayer(p) + "\u001b[m";
                        }
                        else
                        {
                            newText = stringForPlayer(p);
                        }
                        
                        
                        if (s.bombedCells().containsKey(c))
                        {
                            boardOfString += "\u001b[41m" + newText + "\u001b[m";
                        }
                        
                        else if (s.blastedCells().contains(c))
                        {
                            boardOfString += "\u001b[43m" + newText + "\u001b[m";
                        }
                        else if (s.board().blockAt(c).isBonus())
                        {
                            boardOfString += "\u001b[46m" + newText + "\u001b[m";
                        }
                        else {
                            boardOfString += newText;
                        }
                        
                        //System.out.print(stringForPlayer(p));
                        continue xLoop;
                    }
                }
                Block b = board.blockAt(c);
                if (s.blastedCells().contains(c) && b.isFree())
                {
                    boardOfString += "\u001b[43m**\u001b[m";
                    //System.out.print("**");
                }
                else if (s.bombedCells().containsKey(c))
                {
                    boardOfString += "\u001b[41mòò\u001b[m";
                    //System.out.print("òò");
                }
                else
                {   boardOfString += stringForBlock(b);
                    //System.out.print(stringForBlock(b)); 
                }
                
                
            }
            
        boardOfString += "\n";
        //System.out.println(); 
            
        }
        System.out.println(boardOfString);
       
    }

    private static String stringForPlayer(Player p) {
        StringBuilder b = new StringBuilder();
        b.append(p.id().ordinal() + 1);
        switch (p.direction()) {
        case N: b.append('^'); break;
        case E: b.append('>'); break;
        case S: b.append('v'); break;
        case W: b.append('<'); break;
        }
        return b.toString();
    }

    private static String stringForBlock(Block b) {
        switch (b) {
        case FREE: return "  ";
        case INDESTRUCTIBLE_WALL: return "\u001b[40m##\u001b[m";
        case DESTRUCTIBLE_WALL: return "\u001b[42m??\u001b[m";
        case CRUMBLING_WALL: return "\u001b[42m¿¿\u001b[m";
        case BONUS_BOMB: return "\u001b[46m+b\u001b[m";
        case BONUS_RANGE: return "\u001b[46m+r\u001b[m";
        default: throw new Error();
        }
    }
}
