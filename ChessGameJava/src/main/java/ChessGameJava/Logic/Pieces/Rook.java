package ChessGameJava.Logic.Pieces;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.BasicMove;
import ChessGameJava.Logic.Moves.ChessABSMove;
import ChessGameJava.Utility.MoveCalculatorHelper;
import ChessGameJava.Utility.MoveCalculatorHelper.DIRECTION;

/**
 * The class representing a rook within the project. This class is capable of returning all
 * possible moves that the rook can do.
 * @Author Charles Degrandpré
 * @Last_Updated 2023-08-29
 */
public class Rook extends ChessABSPieceModel {

    /**
     * Constructor of the Rook class. Assigns the board as well as the colour of the piece.
     * @param board the instance of the ches board.
     * @param colourOfPiece the colour of the king.
     */
    public Rook(Colour colourOfPiece) {
        super();
        this.colour = colourOfPiece;
    }

    @Override
    public ArrayList<ChessABSMove> getListMoves(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = getListAttackingSquares(currentSquare, board);
        ArrayList<ChessABSMove> listMoves = new ArrayList<>();

        for (ChessSquareModel destSquare : listSquares) {
            if(!checkIfMoveAttacksSameColour(currentSquare, destSquare)) {
                listMoves.add(new BasicMove(currentSquare, destSquare));
            }
        }
        return listMoves;
    }

    @Override
    public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = new ArrayList<>();
        for (DIRECTION direction : MoveCalculatorHelper.lines) {
            listSquares.addAll(MoveCalculatorHelper.calculateLine(direction, currentSquare.getPosition(), currentSquare.getPiece().getColour(), board));
        }
        return listSquares;
    }
    
}
