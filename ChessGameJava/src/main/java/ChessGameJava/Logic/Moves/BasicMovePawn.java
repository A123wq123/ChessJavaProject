package ChessGameJava.Logic.Moves;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Pieces.Pawn;
import ChessGameJava.Utility.UiChange;

/**
 * This class describes a basic move for the pawn. This class functions almost entirely the same way a
 * BasicMove would but it checks if we did a double move for the pawn. We define a double move 
 * on a pawn as the possible first move when a pawn may move two squares forward and only on it's
 * first move. 
 * 
 * If this was the case, we update internally to the pawn the turn it did this double move from 
 * the current turn recorded by the board in ChessABSMove. This is useful for the implementation
 * of the en-passant mechanic described in the EnPassant move. 
 * 
 * If you decide to override this behavior to add more possible moves to the pawn, make sure to take 
 * in account the en-passant move. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-01
 */
public class BasicMovePawn extends BasicMove {

    /**
     * Constructor of the BasicMovePawn class. It takes the first ans second squares associated
     * with the movement of the pawn. 
     * 
     * @param firstSquare The first ChessSquareModel we selected, represents where the king is.
     * @param secondSquare The first ChessSquareModel we selected, represents where we want to go.
     */
    public BasicMovePawn(ChessSquareModel firstSquare, ChessSquareModel secondSquare) {
        super(firstSquare, secondSquare);
    }
    
    /**
     * Overrides the implementation of the executeMove method and adds a check to 
     * verify if the move was a "double move pawn". We define a double move as the 
     * possible first move when a pawn may move two squares forward and only on it's
     * first move.
     * 
     * If the pawn did such a move, we update internally to the pawn the turn at which 
     * it did this move. This is useful for the implementation of the en-passant move
     * for the pawn. 
     * 
     * The implementation is otherwise the exact same as that of BasicMove.executeMove.
     */
    @Override
    protected ArrayList<UiChange> executeMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = super.executeMove(board);
        Pawn.turnOfLastPlayedPawn = ChessABSMove.boardIdMap.get(board);

        Pawn piece = (Pawn) this.firstSquare.getPiece();

        if(Math.abs(temp.getPosition().getCoordY() - firstSquare.getPosition().getCoordY()) == 2) {
            piece.turnOfDoubleMove = ChessABSMove.getMoveCountForBoard(board);
        }

        return listChanges;
    }

    /**
     * Overrides the implementation of the revertMove method and adds a check to 
     * verify if the move was a "double move pawn". We define a double move as the 
     * possible first move when a pawn may move two squares forward and only on it's
     * first move.
     * 
     * If the pawn did such a move, we update internally to the pawn the turn at which 
     * it did this move. This is useful for the implementation of the en-passant move
     * for the pawn. 
     * 
     * The implementation is otherwise the exact same as that of BasicMove.revertMove.
     */
    @Override
    protected ArrayList<UiChange> revertMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = super.revertMove(board);
        Pawn.turnOfLastPlayedPawn = ChessABSMove.boardIdMap.get(board);

        Pawn piece = (Pawn) this.firstSquare.getPiece();

        if(Math.abs(secondSquare.getPosition().getCoordY() - firstSquare.getPosition().getCoordY()) == 2) {
            piece.turnOfDoubleMove = ChessABSMove.getMoveCountForBoard(board);
        }

        return listChanges;
    }
}
