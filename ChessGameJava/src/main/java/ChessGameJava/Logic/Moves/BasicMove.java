package ChessGameJava.Logic.Moves;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Utility.UiChange;

/**
 * This class describes a basic move. We define a basic move as move the piece on the first square
 * to the second square. This is only possible if the second square is occupied by a piece of a colour 
 * different than that of the piece making the move. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-08-31
 */
public class BasicMove extends ChessABSMove{

    protected ChessSquareModel temp;

    /**
     * Constructor for a BasicMove. It takes the first square that we selected (which we consider as the square
     * where the move begins) and the second square we selected (which we consider to be the square where we want
     * to go). 
     * 
     * @param firstSquare The first ChessSquareModel we selected, represents where the piece is.
     * @param secondSquare The first ChessSquareModel we selected, represents where we want to go.
     */
    public BasicMove(ChessSquareModel firstSquare, ChessSquareModel secondSquare) {
        this.firstSquare = firstSquare;
        this.secondSquare = secondSquare;
        this.temp = new ChessSquareModel(secondSquare.getPosition().getCoordX(), secondSquare.getPosition().getCoordY());
    }

    /**
     * This method executes a simple move by fist swapping the secondSquare to a new empty square
     * and then swapping the first and temp squares within the board, effectively removing the 
     * captured piece if there is any. 
     */
    @Override
    protected ArrayList<UiChange> executeMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = new ArrayList<>();

        board.swappSquare(this.temp);
        board.swappSquares(this.firstSquare.getPosition(), this.temp.getPosition());

        listChanges.add(new UiChange(this.firstSquare.getPosition(), this.firstSquare.getPiece(), false));
        listChanges.add(new UiChange(this.temp.getPosition(), this.temp.getPiece(), false));

        return listChanges;
    }

    /**
     * This method reverts a simple move by first swapping the firsSquare and temp squares on the board 
     * and the swapping the temp square with the second square effectively putting back the piece that
     * was potentially captured. 
     */
    @Override
    protected ArrayList<UiChange> revertMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = new ArrayList<>();

        board.swappSquares(this.temp.getPosition(), this.firstSquare.getPosition());
        board.swappSquare(this.secondSquare);

        listChanges.add(new UiChange(this.firstSquare.getPosition(), this.firstSquare.getPiece(), false));
        listChanges.add(new UiChange(this.secondSquare.getPosition(), this.secondSquare.getPiece(), false));

        return listChanges;
    }
    
}
