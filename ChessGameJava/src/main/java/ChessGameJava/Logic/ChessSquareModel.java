package ChessGameJava.Logic;

import ChessGameJava.Logic.Pieces.ChessABSPieceModel;
import ChessGameJava.Logic.Pieces.NullPiece;
import ChessGameJava.Utility.Position;

/**
 * Class representing a chess square on the logic side of this project. This class is in charge of
 * managing the piece it possesses wether that piece is a nullPiece or a concrete piece. This class
 * can obtain its position, obtain its piece, provide a copy of itself and act as a middle man between
 * the chess board and the pieces.
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-03
 */
public class ChessSquareModel {

    protected ChessABSPieceModel piece;
    protected Position position;

    /**
     * @return the Position of the square.
     */
    public Position getPosition() { return position; }

    /**
     * @return the ChessABSPiece on the square.
     */
    public ChessABSPieceModel getPiece() { return piece; }

    /**
     * Constructor of a ChessSquareModel instance. Initiates the piece attribute
     * to a NullPiece instance.
     * 
     * @param column the column the square is positioned at, also its x coordinate.
     * @param row the row the square is positioned at, also its y coordinate.
     */
    public ChessSquareModel(int column, int row) {
        this.position = new Position(column, row);
        piece = new NullPiece();
    }

    /**
     * Copy method that returns a copy of self. 
     * 
     * @return A ChessSquareModel copy of self. 
     */
    public ChessSquareModel copy() {
        ChessSquareModel copy = new ChessSquareModel(this.position.getCoordX(), this.position.getCoordY());
        copy.piece = this.getPiece();
        return copy;
    }
}
