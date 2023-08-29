package ChessGameJava.Logic.Pieces;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.ChessABSMove;

/**
 * The class representing a rook within the project. This class is capable of returning all
 * possible moves that the rook can do.
 * @Author Charles Degrandpré
 * @Last_Updated 2023-08-29
 */
public class Rook extends ChessABSPieceModel{

    /**
     * Constructor of the Rook class. Assigns the board as well as the colour of the piece.
     * @param board the instance of the ches board.
     * @param colourOfPiece the colour of the king.
     */
    public Rook(ChessBoardModel board, Colour colourOfPiece) {
        super();
        this.board = board;
        this.colour = colourOfPiece;
    }

    @Override
    public ArrayList<ChessABSMove> getListMoves(ChessSquareModel currentSquare) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListMoves'");
    }

    @Override
    public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListAttackingSquares'");
    }
    
}
