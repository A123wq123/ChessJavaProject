package ChessGameJava.Logic.Moves;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Utility.UiChange;

/**
 * #TODO come back and do this. 
 */
public class Castle extends ChessABSMove {

    protected ChessSquareModel squareRook;
    protected BasicMove moveKing;
    protected BasicMove moveRook;

    public Castle(ChessSquareModel firstSquareKingChose, ChessSquareModel secondSquareKingChose, ChessSquareModel squareRook, ChessSquareModel squareKing) {
        this.firstSquare = squareKing;
        this.secondSquare = secondSquareKingChose;
        this.squareRook = squareRook;

        this.moveKing = new BasicMove(squareKing, secondSquareKingChose);
        this.moveRook = new BasicMove(this.squareRook, firstSquareKingChose);
    }

    @Override
    public ArrayList<UiChange> processExecuteMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = super.processExecuteMove(board);

        this.squareRook.getPiece().hasMoved = true;

        return listChanges;
    };

    @Override
    protected ArrayList<UiChange> executeMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = new ArrayList<>();

        // Execute move
        listChanges.addAll(this.moveKing.executeMove(board));
        listChanges.addAll(this.moveRook.executeMove(board));

        board.updatePositionOfKing(this.firstSquare.getPiece().getColour());

        return listChanges;
    }

    @Override
    public ArrayList<UiChange> processRevertMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = super.processRevertMove(board);

        if(this.canModifyPieceHasMoved) {
            this.squareRook.getPiece().hasMoved = false;
        }

        board.updatePositionOfKing(this.firstSquare.getPiece().getColour());

        return listChanges;
    }

    @Override
    protected ArrayList<UiChange> revertMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = new ArrayList<>();

        // Revert move
        listChanges.addAll(this.moveRook.revertMove(board));
        listChanges.addAll(this.moveKing.revertMove(board));

        return listChanges;
    }
    
}
