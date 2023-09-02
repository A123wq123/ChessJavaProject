package ChessGameJava.Logic.Moves;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Utility.UiChange;

/**
 * This class describes a basic move for the king. This class functions almost entirely the same way a
 * BasicMove would but it makes sure to update the position of the king when making a move. 
 * This is relevant as the normal implementation of the ChessBoardModel keeps track of the position
 * of both kings for optimization purposes. 
 * 
 * It is recommended to keep that behavior on implementation of all moves that a king can make. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-01
 */
public class BasicMoveKing extends BasicMove {

    /**
     * Constructor of the BasicMoveKing class. It takes the first ans second squares associated
     * with the movement of the king. 
     * 
     * @param firstSquare The first ChessSquareModel we selected, represents where the king is.
     * @param secondSquare The first ChessSquareModel we selected, represents where we want to go.
     */
    public BasicMoveKing(ChessSquareModel firstSquare, ChessSquareModel secondSquare) {
        super(firstSquare, secondSquare);
    }


    /**
     * Overrides implementation of the processExecuteMove class and adds the side effect 
     * of updating the position of the king on the board. The implementation is otherwise
     * the exact same as BasicMove.processExecuteMove.
     */
    @Override
    public ArrayList<UiChange> processExecuteMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = super.processExecuteMove(board);
        
        board.updatePositionOfKing(this.firstSquare.getPiece().getColour());

        return listChanges;
    }

    /**
     * Overrides implementation of the processRevertMove class and adds the side effect 
     * of updating the position of the king on the board. The implementation is otherwise
     * the exact same as BasicMove.processRevertMove.
     */
    @Override
    public ArrayList<UiChange> processRevertMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = super.processRevertMove(board);
        
        board.updatePositionOfKing(this.firstSquare.getPiece().getColour());

        return listChanges;
    }
    
}
