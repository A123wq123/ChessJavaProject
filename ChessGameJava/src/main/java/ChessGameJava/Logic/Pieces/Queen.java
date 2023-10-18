package ChessGameJava.Logic.Pieces;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Utility.MoveCalculatorHelper;
import ChessGameJava.Utility.MoveCalculatorHelper.DIRECTION;

/**
 * The class representing a queen within the project. This class is capable of returning all
 * possible moves that the queen can do and all the squares it can attack. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-02
 */
public class Queen extends ChessABSPieceModel {
    /**
     * Constructor of the queen class. Assigns the colour of the piece.
     * 
     * @param colourOfPiece the colour of the queen.
     */
    public Queen(Colour colourOfPiece) {
        super(colourOfPiece);
    }


    /**
     * Method used to return all the squares a queen can attack. A queen can attack all 
     * squares diagonal to her current square and all squares in any of the four cardinal 
     * directions to her current square up to and including the first occupied square. 
     */
    @Override
    public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = new ArrayList<>();
        for (DIRECTION direction : MoveCalculatorHelper.lines) {
            listSquares.addAll(MoveCalculatorHelper.calculateLine(direction, currentSquare.getPosition(), board));
        }
        for (DIRECTION direction : MoveCalculatorHelper.diagonals) {
            listSquares.addAll(MoveCalculatorHelper.calculateDiagonal(direction, currentSquare.getPosition(), board));
        }
        return listSquares;
    }
    
}
