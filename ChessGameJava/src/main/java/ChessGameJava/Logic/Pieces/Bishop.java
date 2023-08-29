package ChessGameJava.Logic.Pieces;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Utility.MoveCalculatorHelper;
import ChessGameJava.Utility.MoveCalculatorHelper.DIRECTION;

/**
 * The class representing a bishop within the project. This class is capable of returning all
 * possible moves that the bishop can do.
 * @Author Charles Degrandpré
 * @Last_Updated 2023-08-29
 */
public class Bishop extends ChessABSPieceModel{

    /**
     * Constructor of the bishop class. Assigns the board as well as the colour of the piece.
     * @param board the instance of the chess board.
     * @param colourOfPiece the colour of the bishop.
     */
    public Bishop(Colour colourOfPiece) {
        super();
        this.colour = colourOfPiece;
    }

    @Override
    public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = new ArrayList<>();
        for (DIRECTION direction : MoveCalculatorHelper.diagonals) {
            listSquares.addAll(MoveCalculatorHelper.calculateDiagonal(direction, currentSquare.getPosition(), currentSquare.getPiece().getColour(), board));
        }
        return listSquares;
    }
    
}
