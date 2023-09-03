package ChessGameJava.Logic.Pieces;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.ChessABSMove;

import java.util.ArrayList;

/**
 * This class acts as a null piece, in other words a piece that does nothing. This class is
 * usefully within the architecture of this project as all squares require pieces.
 * 
 * This allows us to interface with squares without fear of there not being a piece. But 
 * rather a piece that returns empty lists and values clearly stated as null. 
 * 
 * All instances of this class have the Colour of NULL.
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2022-12-23
 */
public class NullPiece extends ChessABSPieceModel {
    /**
     * Constructor of a null piece. as the piece is meant to be
     * unable to do anything, it doesn't have parameters.
     * 
     * It sets the Colour of the piece to NULL. Useful when checking 
     * if a square is occupied. If the Colour is NULL, then the square can
     * be considered empty. 
     */
    public NullPiece() {
        this.colour = Colour.NULL;
    }

    /**
     * Method used to return the list of squares a NullPiece may attack. Always
     * returns a new empty ArrayList of ChessSquareModel. 
     */
    @Override
    public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board) {
        return new ArrayList<>();
    }
}
