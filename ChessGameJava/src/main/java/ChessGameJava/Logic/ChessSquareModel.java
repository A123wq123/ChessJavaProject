package ChessGameJava.Logic;

import ChessGameJava.Logic.Pieces.ChessABSPieceModel;
import ChessGameJava.Logic.Pieces.NullPiece;
import ChessGameJava.Utility.Position;

/**
 * Class representing a chess square on the logic side of this project. This class is in charge of
 * managing the piece it possesses weather that piece is a nullPiece or a concrete piece. This class
 * can obtain its position, obtain its piece, remove its piece and acts as a middle man between
 * the chess board and the pieces.
 * @Author Charles Degrandpré
 * @Last_Updated 2022-12-23
 */
public class ChessSquareModel {

    private ChessABSPieceModel piece;
    private Position position;

    /**
     * Constructor of a ChessSquareModel instance. Initiates the piece attribute
     * to a NullPiece instance.
     * @param row the row the square is positioned at, also its y coordinate.
     * @param column the column the square is positioned at, also its x coordinate.
     */
    public ChessSquareModel(int column, int row) {
        this.position = new Position(column, row);
        piece = new NullPiece();
    }

    /**
     * @return the Position element of the square.
     */
    public Position getPosition() { return position; }

    /**
     * @return the piece positioned on the square.
     */
    public ChessABSPieceModel getPiece() { return piece; }

    /**
     * Adds a piece onto a square, takes a ChessABSPieceModel
     * element to do so.
     * @param newPiece the piece to add to the square.
     */
    public void addPiece(ChessABSPieceModel newPiece) {
        piece = newPiece;
    }

    /** TODO
     * Remove a piece from a square and replaces it with a NullPiece instance.
     */
    public void removePiece() {
        piece = new NullPiece();
    }

    /**
     * Changes the position of a give ChessUISquare and is used when we need to make
     * a move on the chess board.
     * @param newPosition the new position the ChessSquare will be located at.
     */
    public void changePosition(Position newPosition) {
        position = newPosition;
    }
}
