package ChessGameJava.Utility;

import ChessGameJava.Logic.Colour;

/**
 * This class represents a given change that needs to be made to the UI of the application. In short, 
 * it describes a coordinate that needs to receive a change. This means it changes only one square at a time. 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-08-27
 */
public class UiChange {
    private Position position;
    private PIECENAME pieceName;
    private Colour colour;
    private boolean isHighlightedMove;

    /**
     * Constructor of a UiChange.
     * @param pos The coordinate at which the change happens. Should map to a square.
     * @param name The name of the piece that will now be on the square.
     * @param colour The color of the piece that is now on the square.
     * @param isPossibleMove If true, then highlight the square as a move we can do but do not execute it. 
     *                       If false then make the change on the UI. 
     */
    public UiChange(Position pos, PIECENAME name,Colour colour, boolean isPossibleMove) {
        this.position = pos;
        this.pieceName = name;
        this.colour = colour;
        this.isHighlightedMove = isPossibleMove;
    }

    /**
     * Getter for the position argument. 
     * @return The position of the change. 
     */
    public Position getPosition() { return this.position; }

    /**
     * Getter for the piece name attribute.
     * @return The piece name attribute.
     */
    public PIECENAME getPieceName() { return this.pieceName; }

    /**
     * Getter for the colour of the piece. 
     * @return The colour of the piece. 
     */
    public Colour getColour() { return this.colour; }

    /**
     * Getter for the isHighlightedMove boolean. If true then the move should get displayed as
     * a possible move on teh screen, if false then simply execute the move and make the change. 
     * @return The isHighlightedMove boolean. 
     */
    public boolean getIsHighlightedMove() { return this.isHighlightedMove; }

    /**
     * Enum of all the possible piece we can be. 
     */
    public enum PIECENAME {
        KING, 
        QUEEN,
        ROOK,
        BISHOP,
        KNIGHT,
        PAWN, 
        NULL
    }
}
