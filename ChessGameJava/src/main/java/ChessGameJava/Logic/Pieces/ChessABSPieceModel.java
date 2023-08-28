package ChessGameJava.Logic.Pieces;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.ChessABSMove;

import java.util.ArrayList;

/**
 * Abstract class from which all pieces inherit from. This class keeps track of
 * the board attribute and colour attribute. Can check if a move is legal as well
 * obtain al possible moves a piece can make.
 * @Author Charles Degrandpré
 * @Last_Updated 2022-12-23
 */
public abstract class ChessABSPieceModel {
    protected ChessBoardModel board;
    protected Colour colour;

    /**
     * Returns all the possibles moves the piece can make by receiving its square
     * as a parameter. This method can check if a move is legal (does not cross a piece
     * etc.) but cannot check if the king is in check after the move. As an example, this
     * would return all possible legal squares a knight can move to but not if that move
     * puts the players king in check.
     * @param currentPos the square identifying
     * @return an array of the possible squares the piece can get to.
     */
    abstract public ArrayList<ChessABSMove> getListMoves(ChessSquareModel currentPos);

    public Colour getColour() {
        return this.colour;
    }

    /** TODO
     * Method that checks if the move would be attacking a piece of the same colour.
     * @param currentPos the position the move starts at.
     * @param destSquare the position the move ends at.
     * @return boolean indicating if we are attacking a piece of the same colour.
     */
    protected boolean checkIfMoveAttacksSameColour(ChessSquareModel currentPos, ChessSquareModel destSquare) {
        return this.colour.equals(destSquare.getPiece().colour);
    }
}
