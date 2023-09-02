package ChessGameJava.Logic.Pieces;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.BasicMove;
import ChessGameJava.Logic.Moves.ChessABSMove;

import java.util.ArrayList;

/**
 * Abstract class from which all pieces inherit from. This class keeps track of
 * the hasMoved attribute and colour attribute. 
 * 
 * This class can check for all the squares a piece is attacking and all the moves
 * a piece can currently do. It also provides a few sugarcoat functions to help in 
 * development such as a getter for the colour. 
 * 
 * We believe that all pieces should be self contained in their implementation. This 
 * means that a pieces movements should not depend on the implementation of an other 
 * piece. This is also why the pieces must verify themselves if a move is to them legal
 * as it promotes a more self contained and maintainable code. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-02
 */
public abstract class ChessABSPieceModel {
    protected Colour colour;
    public boolean hasMoved = false; 

    /**
     * Simple getter for the colour of the piece. 
     * @return The Colour of the piece. 
     */
    public Colour getColour() {
        return this.colour;
    }

    /**
     * Returns all the possibles moves the piece can make by receiving its square current
     * as a parameter and the board it is on. This method can check if a move is legal (does 
     * not cross a piece etc.) but cannot check if the king is in check after the move. We believe 
     * such a behavior needs to be verified at a higher level as this behavior depends on the game mode 
     * the game is running on. 
     * 
     * As an example, this would return all possible legal squares a knight can move to but not if that move
     * puts the players king in check. As a general rule, it should do all necessary checks that
     * do not depend on the rules of the game mode. You may want to override this behavior in 
     * child classes. 
     * 
     * This function provide a base behavior, it gets all the squares a piece can attack 
     * and generates a BasicMove from the current square the piece is on to the square the piece
     * is attacking. 
     * 
     * @param currentSquare The ChessSquareModel the piece is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an ArrayList of ChessABSMove the piece can execute. 
     */
    public ArrayList<ChessABSMove> getListMoves(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = this.getListAttackingSquares(currentSquare, board);
        ArrayList<ChessABSMove> listMoves = new ArrayList<>();

        for (ChessSquareModel destSquare : listSquares) {
            if(!this.checkIfMoveAttacksSameColour(destSquare)) {
                listMoves.add(new BasicMove(currentSquare, destSquare));
            }
        }
        return listMoves;
    }

    /**
     * Returns all the squares that the piece can attack. For most pieces this is the same as the 
     * getListMoves method but the result is formatted to only return squares and not moves. 
     * 
     * However, it is possible for a piece to have squares they can attack but not move to 
     * hence the need for two methods (Pawns can attack in diagonal but not always move there). 
     * 
     * Note: Even if it would be illegal for a piece to move to one of the returned squares, 
     * that square is still being attacked and needs to be considered by this method. 
     * 
     * @param currentPos The ChessSquareModel the piece is currently on. 
     * @param board The ChessBoardModel instance the game is played on. 
     * @return An ArrayList of ChessSquareModel squares a piece is attacking. 
     */
    abstract public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board);

    /**
     * Method that checks if the move would be attacking a piece of the same colour as itself.
     * 
     * @param destSquare the ChessSquareModel we are attacking.
     * @return boolean indicating if we are attacking a piece of the same colour.
     */
    protected boolean checkIfMoveAttacksSameColour(ChessSquareModel destSquare) {
        return this.colour == destSquare.getPiece().colour;
    }
}
