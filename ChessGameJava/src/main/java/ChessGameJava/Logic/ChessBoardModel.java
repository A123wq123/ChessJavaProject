package ChessGameJava.Logic;

import ChessGameJava.Logic.Pieces.Bishop;
import ChessGameJava.Logic.Pieces.King;
import ChessGameJava.Logic.Pieces.Knight;
import ChessGameJava.Logic.Pieces.Pawn;
import ChessGameJava.Logic.Pieces.Queen;
import ChessGameJava.Logic.Pieces.Rook;
import ChessGameJava.Utility.Position;
import ChessGameJava.Utility.UiChange;

/**
 * Class representing a chess board on the logic side of this project. This class is in charge of
 * managing the board and every square that is placed within it. This class contains an array of
 * arrays representing the board, the width of teh board and the position of both kings to optimise
 * processing time. This class is in charge of checking for board wide events such as if a king
 * is under attack, removing a piece or adding a piece.
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2022-12-23
 */
public class ChessBoardModel {
    public final static int numberOfRows = 8;
    private final ChessSquareModel[] squareList = new ChessSquareModel[numberOfRows * numberOfRows];
    private ChessSquareModel squareOfWhiteKing;
    private ChessSquareModel squareOfBlackKing;

    /**
     * This function returns the square a king of the given colour is currently 
     * at. 
     * 
     * @param colourOfKing the Colour of the king we want to locate.
     * @return the ChessSquareModel on which the king is located.
     */
    public ChessSquareModel getSquareOfKing(Colour colourOfKing) {
        return colourOfKing == Colour.WHITE ? this.squareOfWhiteKing : this.squareOfBlackKing;
    }

    /**
     * Returns the given ChessSquareModel associated with the same coordinates 
     * as the provided position value. 
     * 
     * @param position the Position representing the coordinates of the square.
     * @return the ChessSquareModel element associated with the coordinates.
     */
    public ChessSquareModel getSquareModel(Position position) {
        return squareList[position.getCoordY() * numberOfRows +  position.getCoordX()];
    }

    /**
     * Getter for the array of ChessSquareModel. 
     * 
     * @return ChessSquareModel[] representing the list of squares. 
     */
    public ChessSquareModel[] getSquareList() {
        return this.squareList;
    }

    /** 
     * Constructor of a ChessBoardModel, this initiates every square and also every
     * piece present in the chess board.
     * 
     * It initiates the pieces as if we where playing standard chess, meaning two players, 
     * black and white, two kings, two queens, 4 bishops, 4 knights, 4 rooks and 16 pawns. 
     * 
     * Refer to section 2.3 of: https://www.fide.com/FIDE/handbook/LawsOfChess.pdf for an 
     * example of the placement of the pieces. 
     */
    public ChessBoardModel() {
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfRows; column++) {
                squareList[row * numberOfRows + column] = new ChessSquareModel(column, row);
            }
        }

        this.addPieces();
    }

    /**
     * This method is in charge of checking if a given square is currently being attacked 
     * by the pieces of the provided colourOfAttacker parameter. 
     * 
     * It works by iterating over all squares to locate all the pieces that match the provided 
     * colourOfAttacker and then asking them for the squares they attack. If the observedSquare
     * is one of them we return true. 
     * 
     * @param observedSquare The ChessSquareModel we want to know if it is being attacked.
     * @param colourOfAttacker The Colour of the attacking pieces.
     * @return True if square is being attacked, false otherwise. 
     */
    public boolean isSquareUnderAttackBy(ChessSquareModel observedSquare, Colour colourOfAttacker) {
        for(ChessSquareModel currentSquare : this.squareList) {
            if(currentSquare.getPiece().getColour() == colourOfAttacker) {
                if(currentSquare.getPiece().getListAttackingSquares(currentSquare, this).stream().anyMatch(square -> square.getPosition().equals(observedSquare.getPosition()))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * This function is in charge of finding the king of a given colour on the board 
     * and then updating the board attribute that contains it's position. 
     * 
     * This method should be called once a king of a certain colour has been moved as
     * the attribute it affects is used to optimize performances when checking 
     * for a winner or a null game. 
     * 
     * @param colourOfKIng The colour of the king we want to update the position for. 
     */
    public void updatePositionOfKing(Colour colourOfKIng) {
        for(ChessSquareModel currentSquare : this.squareList) {
            if(currentSquare.getPiece() instanceof King) {
                if(currentSquare.getPiece().getColour() == Colour.WHITE) {
                    this.squareOfWhiteKing = currentSquare;
                } else {
                    this.squareOfBlackKing = currentSquare;
                }
            }
        }
    }

    /**
     * This method is used to initialize a board with its pieces. 
     * 
     * It initiates the pieces as if we where playing standard chess, meaning two players, 
     * black and white, two kings, two queens, 4 bishops, 4 knights, 4 rooks and 16 pawns. 
     * 
     * Refer to section 2.3 of: https://www.fide.com/FIDE/handbook/LawsOfChess.pdf for an 
     * example of the placement of the pieces.
     */
    private void addPieces() {
        this.addWhitePieces();
        this.addBlackPieces();
    }

    /**
     * Initiates all white pieces on the board according to a standard game of chess. 
     * 
     * Refer to section 2.3 of: https://www.fide.com/FIDE/handbook/LawsOfChess.pdf for an 
     * example of the placement of the pieces.
     */
    private void addWhitePieces() {
         // Add Pawns
        for (int i = 0; i < numberOfRows; i++) {
            ChessSquareModel square = getSquareModel(new Position(i, 6));
            square.addPiece(new Pawn(Colour.WHITE));
        }

        // Add towers
        for (int i  = 0; i < 2; i++) {
            ChessSquareModel square = getSquareModel(new Position(i*7, 7));
            square.addPiece(new Rook(Colour.WHITE));
        }

        // Add knights
        for (int i  = 0; i < 2; i++) {
            ChessSquareModel square = getSquareModel(new Position(1+i*5, 7));
            square.addPiece(new Knight(Colour.WHITE));
        }

        // Add bishops
        for (int i  = 0; i < 2; i++) {
            ChessSquareModel square = getSquareModel(new Position(2+i*3, 7));
            square.addPiece(new Bishop(Colour.WHITE));
        }

        // Add queen
        {
            ChessSquareModel square = getSquareModel(new Position(3, 7));
            square.addPiece(new Queen(Colour.WHITE));
        }

        // Add king
        {
            ChessSquareModel square = getSquareModel(new Position(4, 7));
            square.addPiece(new King(Colour.WHITE));
            this.squareOfWhiteKing = square;
        }
    }

    /**
     * Initiates all black pieces on the board according to a standard game of chess. 
     * 
     * Refer to section 2.3 of: https://www.fide.com/FIDE/handbook/LawsOfChess.pdf for an 
     * example of the placement of the pieces.
     */
    private void addBlackPieces() {
        // Add Pawns
        for (int i = 0; i < numberOfRows; i++) {
            ChessSquareModel square = getSquareModel(new Position(i, 1));
            square.addPiece(new Pawn(Colour.BLACK));
        }

        // Add towers
        for (int i  = 0; i < 2; i++) {
            ChessSquareModel square = getSquareModel(new Position(i*7, 0));
            square.addPiece(new Rook(Colour.BLACK));
        }

        // Add knights
        for (int i  = 0; i < 2; i++) {
            ChessSquareModel square = getSquareModel(new Position(1+i*5, 0));
            square.addPiece(new Knight(Colour.BLACK));
        }

        // Add bishops
        for (int i  = 0; i < 2; i++) {
            ChessSquareModel square = getSquareModel(new Position(2+i*3, 0));
            square.addPiece(new Bishop(Colour.BLACK));
        }

        // Add queen
        {
            ChessSquareModel square = getSquareModel(new Position(3, 0));
            square.addPiece(new Queen(Colour.BLACK));
        }

        // Add king
        {
            ChessSquareModel square = getSquareModel(new Position(4, 0));
            square.addPiece(new King(Colour.BLACK));
            this.squareOfBlackKing = square;
        }
    }

    /**
     * A method that swaps a provided ChessSquareModel with the one at the same position.
     * It then returns the swapped out ChessSquareModel
     * 
     * @param newSquare The new square to add to the board.
     * @return the old square.
     */
    public ChessSquareModel swappSquare(ChessSquareModel newSquare) {
        ChessSquareModel oldSquare = this.getSquareModel(newSquare.getPosition());
        squareList[newSquare.getPosition().getCoordY() * numberOfRows + newSquare.getPosition().getCoordX()] = newSquare;
        return oldSquare;
    }

    /**
     * This method takes the coordinates of two ChessSquareModel and simply interchanges them.
     * You may see this as the two squares on the board swapping places. 
     * 
     * It also updates the position attribute of both of these squares to that of their new 
     * position. 
     * 
     * @param first the Position of the first square.
     * @param second the Position of the second square.
     */
    public void swappSquares(Position first, Position second) {
        ChessSquareModel firstSquare = this.getSquareModel(first);
        squareList[first.getCoordY() * numberOfRows + first.getCoordX()] = this.getSquareModel(second);
        squareList[second.getCoordY() * numberOfRows + second.getCoordX()] = firstSquare;

        this.getSquareModel(first).changePosition(first);
        this.getSquareModel(second).changePosition(second);
    }

    /**
     * Debugging function, prints a ChessBoardModel in the console. Helps in understanding where the 
     * pieces are and why they got there. 
     * 
     * Should never be used in a production release. 
     */
    public void printBoard() {
        for (int row = 0; row < numberOfRows; row++) {
            String line = "";
            for (int column = 0; column < numberOfRows; column++) {
                line = line + String.format("%s | ", UiChange.getNameFromABSPiece(squareList[row * numberOfRows +  column].getPiece()));
            }
            System.out.println(line);
       }
    }
}
