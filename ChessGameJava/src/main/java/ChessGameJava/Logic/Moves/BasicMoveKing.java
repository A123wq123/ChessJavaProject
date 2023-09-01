package ChessGameJava.Logic.Moves;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Utility.UiChange;

public class BasicMoveKing extends BasicMove {

    public BasicMoveKing(ChessSquareModel firstSquare, ChessSquareModel secondSquare) {
        super(firstSquare, secondSquare);
    }

    @Override
    protected ArrayList<UiChange> executeMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = super.executeMove(board);
        
        board.updatePositionOfKing(this.firstSquare.getPiece().getColour());

        return listChanges;
    }

    @Override
    protected ArrayList<UiChange> revertMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = super.revertMove(board);
        
        board.updatePositionOfKing(this.firstSquare.getPiece().getColour());

        return listChanges;
    }
    
}
