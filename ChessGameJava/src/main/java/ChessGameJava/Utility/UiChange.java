package ChessGameJava.Utility;

import ChessGameJava.Logic.Colour;

enum PIECENAME {
    KING, 
    QUEEN,
    ROOK,
    BISHOP,
    KNIGHT,
    PAWN
}

public class UiChange {
    Position position;
    PIECENAME pieceName;
    Colour colour;
    boolean isHighlightedMove;

    UiChange(Position pos, PIECENAME name,Colour colour, boolean isPossibleMove) {
        this.position = pos;
        this.pieceName = name;
        this.colour = colour;
        this.isHighlightedMove = isPossibleMove;
    }
}
