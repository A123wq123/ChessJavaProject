package ChessGameJava.Logic.Moves;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Pieces.Pawn;
import ChessGameJava.Utility.UiChange;

public class EnPassant extends BasicMove {

    protected ChessSquareModel opponentPawnSquare;
    protected ChessSquareModel temp;

    public EnPassant(ChessSquareModel firstSquare, ChessSquareModel secondSquare, ChessSquareModel opponentPawnSquare) {
        super(firstSquare, secondSquare);
        this.opponentPawnSquare = opponentPawnSquare;
        this.temp = new ChessSquareModel(opponentPawnSquare.getPosition().getCoordX(), opponentPawnSquare.getPosition().getCoordY());
    }

    @Override
    protected ArrayList<UiChange> executeMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = new ArrayList<>();

        // Execute move 
        listChanges.addAll(super.executeMove(board));
        board.swappSquare(this.temp);

        // Get the changes
        listChanges.add(new UiChange(this.temp.getPosition(), this.temp.getPiece(), false));

        Pawn.turnOfLastPlayedPawn = ChessABSMove.boardIdMap.get(board);
        Pawn piece = (Pawn) this.firstSquare.getPiece();

        if(Math.abs(secondSquare.getPosition().getCoordY() - firstSquare.getPosition().getCoordY()) == 2) {
            piece.turnOfDoubleMove = ChessABSMove.getMoveCountForBoard(board);
        }

        return listChanges;
    }

    @Override
    protected ArrayList<UiChange> revertMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = new ArrayList<>();

        // revert move 
        listChanges.addAll(super.revertMove(board));
        board.swappSquare(this.opponentPawnSquare);

        // Get the changes
        listChanges.add(new UiChange(this.temp.getPosition(), this.temp.getPiece(), false));

        Pawn.turnOfLastPlayedPawn = ChessABSMove.boardIdMap.get(board);
        Pawn piece = (Pawn) this.firstSquare.getPiece();

        if(Math.abs(secondSquare.getPosition().getCoordY() - firstSquare.getPosition().getCoordY()) == 2) {
            piece.turnOfDoubleMove = ChessABSMove.getMoveCountForBoard(board);
        }

        return listChanges;
    }
    
}
