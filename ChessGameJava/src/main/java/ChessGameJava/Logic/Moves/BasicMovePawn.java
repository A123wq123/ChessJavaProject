package ChessGameJava.Logic.Moves;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Pieces.Pawn;
import ChessGameJava.Utility.UiChange;

public class BasicMovePawn extends BasicMove {

    public BasicMovePawn(ChessSquareModel firstSquare, ChessSquareModel secondSquare) {
        super(firstSquare, secondSquare);
    }
    
    @Override
    public ArrayList<UiChange> executeMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = super.executeMove(board);
        Pawn.turnOfLastPlayedPawn = ChessABSMove.boardIdMap.get(board);

        Pawn piece = (Pawn) this.firstSquare.getPiece();

        if(Math.abs(secondSquare.getPosition().getCoordY() - firstSquare.getPosition().getCoordY()) == 2) {
            piece.turnOfDoubleMove = ChessABSMove.getMoveCountForBoard(board);
            piece.canMoveTwice = false;
        }

        return listChanges;
    }

    @Override
    public ArrayList<UiChange> revertMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = super.revertMove(board);
        Pawn.turnOfLastPlayedPawn = ChessABSMove.boardIdMap.get(board);

        Pawn piece = (Pawn) this.firstSquare.getPiece();

        if(Math.abs(secondSquare.getPosition().getCoordY() - firstSquare.getPosition().getCoordY()) == 2) {
            piece.turnOfDoubleMove = ChessABSMove.getMoveCountForBoard(board);
            piece.canMoveTwice = true;
        }

        return listChanges;
    }
}
