package ChessGameJava.Logic;

import ChessGameJava.Logic.Pieces.Bishop;
import ChessGameJava.Logic.Pieces.ChessABSPieceModel;
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
 * @Author Charles Degrandpré
 * @Last_Updated 2022-12-23
 */
public class ChessBoardModel {
    private final ChessSquareModel[][] squareList = new ChessSquareModel[numberOfRows][numberOfRows];
    public final static int numberOfRows = 8;
    private ChessSquareModel positionOfWhiteKing;
    private ChessSquareModel positionOfBlackKing;

    /** 
     * Constructor of a ChessBoardModel, this initiates every square and also every
     * piece present in the chess board.
     */
    public ChessBoardModel() {
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfRows; column++) {
                squareList[row][column] = new ChessSquareModel(column, row);
            }
        }

        this.addPieces();
    }

    /**
     * Returns the given square associated with a position element.
     * @param position the position representing the coordinates of the square.
     * @return the square model element associated with the coordinates.
     */
    public ChessSquareModel getSquareModel(Position position) {
        return squareList[position.getCoordY()][position.getCoordX()];
    }

    /**
     * Adds a piece to a square associated with a given set of coordinates.
     * @param position the position element representing the coordinates of the square
     *                 we want to add the piece to.
     * @param piece the piece we want to add.
     */
    public void addPieceToSquare(Position position, ChessABSPieceModel piece) {
        getSquareModel(position).addPiece(piece);
    }

    /**
     * @deprecated this method is subject to being removed.
     * Adds a piece to a given square in parameter.
     * @param square the square we want to add the piece to.
     * @param piece the piece we want to add.
     */
    public void addPieceToSquare(ChessSquareModel square, ChessABSPieceModel piece) {
        square.addPiece(piece);
    }

    /** TODO
     * Removes a square from a given set of coordinates.
     * @param position the position of thw square we want to remove the piece from.
     */
    public void removePieceFromSquare(Position position) {

    }

    /**
     * This method is in charge of checking if a given square is currently being attacked 
     * by the pieces of the provided colourOfAttacker parameter.
     * @param observedSquare The square we want to know if it is being attacked.
     * @param colourOfAttacker The colour of the attacking pieces.
     * @return True if square is being attacked, false otherwise. 
     */
    public boolean isSquareUnderAttackBy(ChessSquareModel observedSquare, Colour colourOfAttacker) {
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfRows; column++) {
                try {
                    ChessSquareModel currentSquare = this.getSquareModel(new Position(column, row));
                    if(currentSquare.getPiece().getColour() == colourOfAttacker) {
                        if(currentSquare.getPiece().getListAttackingSquares(currentSquare, this).stream().anyMatch(square -> square.getPosition().equals(observedSquare.getPosition()))) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return false;
    }

    /**
     * This function is in charge of finding the king of a given colour on the board 
     * and then updating the board attribute that contains it's position. 
     * @param colourOfKIng The colour of the king we want to update the position for. 
     */
    public void updatePositionOfKing(Colour colourOfKIng) {
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfRows; column++) {
                try {
                    ChessSquareModel currentSquare = this.getSquareModel(new Position(column, row));
                    if(currentSquare.getPiece() instanceof King) {
                        if(currentSquare.getPiece().getColour() == Colour.WHITE) {
                            this.positionOfWhiteKing = currentSquare;
                        } else {
                            this.positionOfBlackKing = currentSquare;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
    }

    /**
     * #TODO 
     * This method is used to initialize a board with its pieces. 
     */
    private void addPieces() {
        this.addWhitePieces();
        this.addBlackPieces();
    }

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
            // getSquareUI(7, 1+i*5).setText(this.getPieceName(PIECENAME.KNIGHT, Colour.WHITE));
        }

        // Add bishops
        for (int i  = 0; i < 2; i++) {
            ChessSquareModel square = getSquareModel(new Position(2+i*3, 7));
            square.addPiece(new Bishop(Colour.WHITE));
        }

        // // Add queen
        {
            ChessSquareModel square = getSquareModel(new Position(3, 7));
            square.addPiece(new Queen(Colour.WHITE));
        }

        // Add king
        {
            ChessSquareModel square = getSquareModel(new Position(4, 7));
            square.addPiece(new King(Colour.WHITE));
            this.positionOfWhiteKing = square;
        }
    }

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
            this.positionOfBlackKing = square;
        }
    }


    /**
     * @return returns the square of the white king.
     */
    private ChessSquareModel getPositionOfWhiteKing() {return this.positionOfWhiteKing;}

    /**
     * @return returns the square of the black king.
     */
    private ChessSquareModel getPositionOfBlackKing() {return this.positionOfBlackKing;}

    /**
     * A function to return the colour of a king depending on a given parameter.
     * @param colourOfKing the colour of the king we want to locate.
     * @return the square on which the king is located.
     */
    public ChessSquareModel getPositionOfKing(Colour colourOfKing) {
        return colourOfKing == Colour.WHITE ? getPositionOfWhiteKing() : getPositionOfBlackKing();
    }

    /**
     * A funtion that swaps a provided square with the one at the same position.
     * @param newSquare The new square to add to the board.
     * @return the old square.
     */
    public ChessSquareModel swappSquare(ChessSquareModel newSquare) {
        ChessSquareModel oldSquare = this.getSquareModel(newSquare.getPosition());
        squareList[newSquare.getPosition().getCoordY()][newSquare.getPosition().getCoordX()] = newSquare;
        return oldSquare;
    }

    /**
     * This method takes the coordinates of two squares and simply interchanges them.
     * @param first the position of one of the squares
     * @param second the position of the second square
     */
    public void swappSquares(Position first, Position second) {

        ChessSquareModel firstSquare = this.getSquareModel(first);
        squareList[first.getCoordY()][first.getCoordX()] = this.getSquareModel(second);
        squareList[second.getCoordY()][second.getCoordX()] = firstSquare;

        this.getSquareModel(first).changePosition(first);
        this.getSquareModel(second).changePosition(second);
    }

    public void printBoard() {
       for (int row = 0; row < numberOfRows; row++) {
            String line = "";
            for (int column = 0; column < numberOfRows; column++) {
                line = line + String.format("%s | ", UiChange.getNameFromABSPiece(squareList[row][column].getPiece()));
            }
            System.out.println(line);
       }
    }
}
