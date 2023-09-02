package ChessGameJava.Logic.Moves;

import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Pieces.Pawn;
import ChessGameJava.Utility.UiChange;

/**
 * This class describes the en-passant move. We consider an en-passant move to be when a pawn
 * has an opponent's pawn do a double pawn move (see BasicMovePawn) that puts it next to our pawn
 * (on the same row). On the turn following the opponent's pawn move as described above, we may
 * take this pawn by moving diagonally behind if the square is open (it should be). 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-02
 */
public class EnPassant extends BasicMove {

    protected ChessSquareModel opponentPawnSquare;
    protected ChessSquareModel temp;

    /**
     * Constructor for the en-passant move. It takes the first square, which is the square where our
     * pawn currently is, the second square, which is the square we have selected to take over from
     * and it takes the the opponentPawnSquare, the square where our opponents pawn is. 
     * 
     * @param firstSquare The first ChessSquareModel we selected, represents where the piece is.
     * @param secondSquare The first ChessSquareModel we selected, represents where we want to go.
     * @param opponentPawnSquare The ChessSquareModel where the opponents pawn is. 
     */
    public EnPassant(ChessSquareModel firstSquare, ChessSquareModel secondSquare, ChessSquareModel opponentPawnSquare) {
        super(firstSquare, secondSquare);
        this.opponentPawnSquare = opponentPawnSquare;
        this.temp = new ChessSquareModel(opponentPawnSquare.getPosition().getCoordX(), opponentPawnSquare.getPosition().getCoordY());
    }

    /**
     * This method executes the en-passant move by first executing a BasicMove to move our 
     * own pawn. Then it simply swaps the opponent square with a new empty square, effectively 
     * removing the opponent pawn from the game. 
     * 
     * It also updates the Pawn.turnOfLastPlayedPawn attribute with the current turn registered 
     * for this board. 
     */
    @Override
    protected ArrayList<UiChange> executeMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = new ArrayList<>();

        listChanges.addAll(super.executeMove(board));
        board.swappSquare(this.temp);

        listChanges.add(new UiChange(this.temp.getPosition(), this.temp.getPiece(), false));

        Pawn.turnOfLastPlayedPawn = ChessABSMove.boardIdMap.get(board);

        return listChanges;
    }

    /**
     * This method reverts the en-passant move by first reverting the basic move that was used 
     * to move our own paw. Then, we swap back the opponent square where the opponent pawn is 
     * with the empty square we had previously swapped, effectively brining back the opponent 
     * pawn.
     * 
     * It also updates the Pawn.turnOfLastPlayedPawn attribute with the current turn registered 
     * for this board. 
     */
    @Override
    protected ArrayList<UiChange> revertMove(ChessBoardModel board) {
        ArrayList<UiChange> listChanges = new ArrayList<>();

        listChanges.addAll(super.revertMove(board));
        board.swappSquare(this.opponentPawnSquare);

        listChanges.add(new UiChange(this.temp.getPosition(), this.temp.getPiece(), false));

        Pawn.turnOfLastPlayedPawn = ChessABSMove.boardIdMap.get(board);

        return listChanges;
    }
    
}
