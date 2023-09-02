package ChessGameJava.Logic.Pieces;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Utility.MoveCalculatorHelper;
import ChessGameJava.Utility.MoveCalculatorHelper.DIRECTION;

/**
 * The class representing a bishop within the project. This class is capable of returning all
 * possible moves that the bishop can do and all the squares it can attack.
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-08-29
 */
public class Bishop extends ChessABSPieceModel{

    /**
     * Constructor of the bishop class. Assigns the colour of the piece. 
     * 
     * @param colourOfPiece the colour of the bishop.
     */
    public Bishop(Colour colourOfPiece) {
        this.colour = colourOfPiece;
    }

    /**
     * Method used to return all the squares a bishop can attack. A bishop can attack all squares 
     * that are diagonal from its current position up to and including the first occupied square. 
     */
    @Override
    public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = new ArrayList<>();
        for (DIRECTION direction : MoveCalculatorHelper.diagonals) {
            listSquares.addAll(MoveCalculatorHelper.calculateDiagonal(direction, currentSquare.getPosition(), currentSquare.getPiece().getColour(), board));
        }
        return listSquares;
    }
    
}
