package ChessGameJava.Logic.Moves;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Utility.UiChange;

/**
 * This class describes a castle move according to the official FIDE rules, see
 * this link for reference: https://www.fide.com/FIDE/handbook/LawsOfChess.pdf.
 * 
 * This move is described within the FIDE rules as the king moving two squares towards 
 * the tower and then moving the tower to the first square the king has visited. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-01
 */
public class Castle extends ChessABSMove {

    protected ChessSquareModel squareRook;
    protected BasicMove moveKing;
    protected BasicMove moveRook;

    /**
     * Constructor for the Castle class. it takes all squares related to the castle move. 
     * @param firstSquareKingChose The first square the king will move to.
     * @param secondSquareKingChose The second square the king will move to. 
     * @param squareRook The square the rook is currently on. 
     * @param squareKing The square the king is currently on. 
     */
    public Castle(ChessSquareModel firstSquareKingChose, ChessSquareModel secondSquareKingChose, ChessSquareModel squareRook, ChessSquareModel squareKing) {
        this.firstSquare = squareKing;
        this.secondSquare = secondSquareKingChose;
        this.squareRook = squareRook;

        this.moveKing = new BasicMove(squareKing, secondSquareKingChose);
        this.moveRook = new BasicMove(this.squareRook, firstSquareKingChose);
    }

    /**
     * Overrides the base implementation of the processExecuteMove method and 
     * sets the hasMoved parameter of the rook to true. The implementation is 
     * otherwise exactly the same as the base one. 
     * 
     * The method also makes sure to update the position of the king on the provided
     * board once the move has been executed. 
     */
    @Override
    public ArrayList<UiChange> processExecuteMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = super.processExecuteMove(board);

        this.squareRook.getPiece().hasMoved = true;
        board.updatePositionOfKing(this.firstSquare.getPiece().getColour());

        return listChanges;
    };

    /**
     * The method executes a castling move for the king. It does so by combining 
     * two basic moves in the form of one move from the king and one move from the rook. 
     */
    @Override
    protected ArrayList<UiChange> executeMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = new ArrayList<>();

        listChanges.addAll(this.moveKing.executeMove(board));
        listChanges.addAll(this.moveRook.executeMove(board));

        return listChanges;
    }

    /**
     * Overrides the base implementation of the processRevertMove method and 
     * sets the hasMoved parameter of the rook to false if we are permitted to do so. 
     * The implementation is otherwise exactly the same as the base one. 
     * 
     * The method also makes sure to update the position of the king on the provided
     * board once the move has been reverted. 
     */
    @Override
    public ArrayList<UiChange> processRevertMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = super.processRevertMove(board);

        if(this.canModifyPieceHasMoved) {
            this.squareRook.getPiece().hasMoved = false;
        }

        board.updatePositionOfKing(this.firstSquare.getPiece().getColour());

        return listChanges;
    }

    /**
     * The method reverts a castling move for the king. It does so by combining 
     * two basic moves in the form of one move from the king and one move from the rook 
     * and reverting them in the opposite order the executeMove function did. 
     */
    @Override
    protected ArrayList<UiChange> revertMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = new ArrayList<>();

        listChanges.addAll(this.moveRook.revertMove(board));
        listChanges.addAll(this.moveKing.revertMove(board));

        return listChanges;
    }
    
}
