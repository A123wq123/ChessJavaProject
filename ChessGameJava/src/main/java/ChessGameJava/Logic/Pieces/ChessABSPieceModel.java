package ChessGameJava.Logic.Pieces;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.BasicMove;
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
    protected Colour colour;
    public boolean hasMoved = false; 

    /**
     * Returns all the possibles moves the piece can make by receiving its square
     * as a parameter. This method can check if a move is legal (does not cross a piece
     * etc.) but cannot check if the king is in check after the move. As an example, this
     * would return all possible legal squares a knight can move to but not if that move
     * puts the players king in check. As a general rule, it should do all necessary checks that
     * do not depend on the rules of the game mode. You may want to override this behavior in 
     * child classes. 
     * @param currentSquare The square the piece is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an array of the possible moves the piece can do.
     */
    public ArrayList<ChessABSMove> getListMoves(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = getListAttackingSquares(currentSquare, board);
        ArrayList<ChessABSMove> listMoves = new ArrayList<>();

        for (ChessSquareModel destSquare : listSquares) {
            if(!checkIfMoveAttacksSameColour(currentSquare, destSquare)) {
                listMoves.add(new BasicMove(currentSquare, destSquare));
            }
        }
        return listMoves;
    }

    /**
     * Returns all the squares that the piece can attack. For most pieces this is the same as the 
     * getListMoves method but the result is formatted to only return squares and not moves. 
     * However, it is possible for a piece to have squares they can attack but not move to 
     * hence the need for two methods (Pawns can attack in diagonal but not always move there). 
     * @param currentPos The square the piece is currently on. 
     * @param board The ChessBoardModel instance the game is played on. 
     * @return An array of the possible squares a piece is attacking. 
     */
    abstract public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board);

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
