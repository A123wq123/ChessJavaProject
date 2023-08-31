package ChessGameJava.Logic.Moves;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Utility.UiChange;

/**
 * #TODO come back and do this. 
 */
public class Castle extends ChessABSMove {

    private ChessSquareModel opponentPawn;

    public Castle(ChessSquareModel firstSquare, ChessSquareModel secondSquare, ChessSquareModel opponentPawn) {
        super();
        this.firstSquare = firstSquare;
        this.secondSquare = secondSquare;
        this.opponentPawn = opponentPawn;
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
