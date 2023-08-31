package ChessGameJava.Logic.Moves;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Utility.UiChange;

public class BasicMove extends ChessABSMove{

    protected ChessSquareModel temp;

    public BasicMove(ChessSquareModel firstSquare, ChessSquareModel secondSquare) {
        this.firstSquare = firstSquare;
        this.secondSquare = secondSquare;
        this.temp = new ChessSquareModel(secondSquare.getPosition().getCoordX(), secondSquare.getPosition().getCoordY());
    }

    @Override
    protected ArrayList<UiChange> executeMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = new ArrayList<>();

        // Make the move
        board.swappSquare(this.temp);

        board.swappSquares(this.firstSquare.getPosition(), this.temp.getPosition());

        // Get the changes
        listChanges.add(new UiChange(this.firstSquare.getPosition(), this.firstSquare.getPiece(), false));
        listChanges.add(new UiChange(this.temp.getPosition(), this.temp.getPiece(), false));

        return listChanges;
    }

    @Override
    protected ArrayList<UiChange> revertMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = new ArrayList<>();

        // Revert the move
        board.swappSquares(this.temp.getPosition(), this.firstSquare.getPosition());
        
        board.swappSquare(this.secondSquare);

        // Get the changes
        listChanges.add(new UiChange(this.firstSquare.getPosition(), this.firstSquare.getPiece(), false));
        listChanges.add(new UiChange(this.secondSquare.getPosition(), this.secondSquare.getPiece(), false));

        return listChanges;
    }
    
}
