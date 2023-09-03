package ChessGameJava.Logic.Pieces;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Utility.MoveCalculatorHelper;
import ChessGameJava.Utility.MoveCalculatorHelper.DIRECTION;

/**
 * The class representing a rook within the project. This class is capable of returning all
 * possible moves that the queen can do and all the squares it can attack. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-02
 */
public class Rook extends ChessABSPieceModel {

    /**
     * Constructor of the Rook class. Assigns the colour of the piece.
     * 
     * @param colourOfPiece the colour of the rook.
     */
    public Rook(Colour colourOfPiece) {
        this.colour = colourOfPiece;
    }

    /**
     * Method used to return all the squares a rook can attack. A rook can attack all squares
     * from his current position going in the four cardinal positions up to an including the 
     * first occupied square. 
     */
    @Override
    public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = new ArrayList<>();
        for (DIRECTION direction : MoveCalculatorHelper.lines) {
            listSquares.addAll(MoveCalculatorHelper.calculateLine(direction, currentSquare.getPosition(), board));
        }
        return listSquares;
    }
    
}
