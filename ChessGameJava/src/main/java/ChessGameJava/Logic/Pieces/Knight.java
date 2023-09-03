package ChessGameJava.Logic.Pieces;

import java.util.ArrayList;
import java.util.Arrays;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Utility.Position;

/**
 * The class representing a knight within the project. This class is capable of returning all
 * possible moves that the knight can do and all the squares it can attack.
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-02
 */
public class Knight extends ChessABSPieceModel {

    /**
     * Constructor of the Knight class. Assigns the colour of the piece.
     * 
     * @param colourOfPiece the colour of the knight.
     */
    public Knight(Colour colourOfPiece) {
        this.colour = colourOfPiece;
    }

    /**
     * Method used to return all the squares a knight can attack. A knight can attack all all 
     * squares that are two squares away from him in all cardinal directions (so two squares up, 
     * two squares down, two squares right and tqo squares left) and one square away in a direction
     * perpendicular to the direction of the two squares previously mentioned giving a sort of
     * L shape. The squares can be attacked regardless of if the path is blocked as the knight 
     * can jump over pieces. 
     */
    @Override
    public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = new ArrayList<>();
        Position currentPosition = currentSquare.getPosition();
        
        for (int x : Arrays.asList(-2, 2)) { 
            for (int y : Arrays.asList(-1, 1)) { 
                try {
                    Position position1 = currentPosition.sumPosition(x,y);
                    listSquares.add(board.getSquareModel(position1));
                } catch (Exception e) {
                }
                try {
                    Position position2 = currentPosition.sumPosition(y,x);
                    listSquares.add(board.getSquareModel(position2));
                } catch (Exception e) {
                }
            }    
        }

        return listSquares;
    }
    
}
