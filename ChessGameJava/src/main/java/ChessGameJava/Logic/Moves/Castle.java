package ChessGameJava.Logic.Moves;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Utility.UiChange;

/**
 * #TODO come back and do this. 
 */
public class Castle extends ChessABSMove {

    private ChessSquareModel temp;

    public Castle(ChessSquareModel firstSquare, ChessSquareModel secondSquare) {
        super();
        this.firstSquare = firstSquare;
        this.secondSquare = secondSquare;
        this.temp = new ChessSquareModel(secondSquare.getPosition().getCoordX(), secondSquare.getPosition().getCoordY());
    }

    @Override
    public ArrayList<UiChange> executeMove(ChessBoardModel board) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executeMove'");
    }

    @Override
    public ArrayList<UiChange> revertMove(ChessBoardModel board) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'revertMove'");
    }
    
}
