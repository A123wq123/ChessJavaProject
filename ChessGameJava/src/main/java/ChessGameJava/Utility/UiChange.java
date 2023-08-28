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

    public getColour() { return this.colour; }

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
