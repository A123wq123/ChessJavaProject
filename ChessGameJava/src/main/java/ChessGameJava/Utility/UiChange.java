package ChessGameJava.Utility;

import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Pieces.Bishop;
import ChessGameJava.Logic.Pieces.ChessABSPieceModel;
import ChessGameJava.Logic.Pieces.King;
import ChessGameJava.Logic.Pieces.Knight;
import ChessGameJava.Logic.Pieces.Pawn;
import ChessGameJava.Logic.Pieces.Queen;
import ChessGameJava.Logic.Pieces.Rook;

/**
 * This class represents a given change that needs to be made to the UI of the application. In short, 
 * it describes a coordinate that needs to receive a change. This means it changes only one square at a time. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-03
 */
public class UiChange {
    private Position position;
    private PieceName pieceName;
    private Colour colour;
    private boolean isHighlightedMove;

    /**
     * Getter for the position argument. 
     * 
     * @return The position of the change. 
     */
    public Position getPosition() { return this.position; }

    /**
     * Getter for the piece name attribute.
     * 
     * @return The piece name attribute.
     */
    public PieceName getPieceName() { return this.pieceName; }

    /**
     * Getter for the colour of the piece. 
     * 
     * @return The colour of the piece. 
     */
    public Colour getColour() { return this.colour; }

    /**
     * Getter for the isHighlightedMove boolean. If true then the move should get displayed as
     * a possible move on the screen, if false then simply execute the move and make the change. 
     * 
     * @return The isHighlightedMove boolean. 
     */
    public boolean getIsHighlightedMove() { return this.isHighlightedMove; }

    /**
     * Constructor of a UiChange.
     * 
     * @param pos The Position at which the change happens. 
     * @param name The name of the piece in string format that will now be on the square.
     * @param colour The Colour of the piece that is now on the square.
     * @param isPossibleMove If true, then highlight the square as a move we can do but do not execute it. 
     *                       If false then make the change on the UI. 
     */
    public UiChange(Position pos, ChessABSPieceModel piece, boolean isPossibleMove) {
        this.position = pos;
        this.pieceName = UiChange.getNameFromABSPiece(piece);
        this.colour = piece.getColour();
        this.isHighlightedMove = isPossibleMove;
    }

    /**
     * This method takes a given ChessABSPieceModel and maps it to of the possible PieceNames.
     * 
     * This is to allow an interface from which to communicate between the UI and Logic of this 
     * application without them knowing what class they are dealing with. 
     * 
     * @param piece The ChessABSPieceModel we want to map.
     * @return The corresponding PieceName.
     */
    public static PieceName getNameFromABSPiece(ChessABSPieceModel piece) {
        if (piece instanceof King) {
            return PieceName.KING;
        } else if (piece instanceof Rook) {
            return PieceName.ROOK;
        } else if (piece instanceof Bishop) {
            return PieceName.BISHOP;
        } else if (piece instanceof Queen){
            return PieceName.QUEEN;
        } else if (piece instanceof Knight) {
            return PieceName.KNIGHT; 
        } else if (piece instanceof Pawn) {
            return PieceName.PAWN; 
        } else {
            return PieceName.NULL;
        }
    }
}
