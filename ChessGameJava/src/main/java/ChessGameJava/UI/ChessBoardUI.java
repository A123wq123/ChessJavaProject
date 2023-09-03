package ChessGameJava.UI;

import javax.swing.*;

import ChessGameJava.Controller.ChessController;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Utility.PieceName;
import ChessGameJava.Utility.UiChange;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class representing the chess board on the User Interface side of this project.
 * This class is capable of loading a chess game with the basic pieces in place
 * as well as add a new square or piece if such is desired.
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-03
 */
public class ChessBoardUI extends JFrame{

    private static final int numberOfRows = 8;
    private final ArrayList<ChessSquareUI> listSquares = new ArrayList<>();

    /**
     * Constructor for the ChessBoardUI class. Initiates all attributes of the class
     * and initiates base values in terms of size and titles for the board.
     * This constructor adds all the squares and pieces to the UI. The pieces are
     * identified with the unicode strings from the chess pieces icons and not
     * images.
     */
    public ChessBoardUI() {
        super(); 
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(450, 450);
        super.setTitle("ChessGame");

        super.setLayout(new GridLayout(8, 8));

        this.addSquaresUI();
        this.addPieces();

        super.setVisible(true);
    }

    /**
     * This method is in charge of taking an ArrayList<UiCHange> and make the necessary
     * changes on the Ui of the application. 
     * 
     * @param changes The ArrayList<UiChange> to make the changes for. 
     */
    public void makeChanges(ArrayList<UiChange> changes) {
        this.listSquares.forEach(square -> square.removeHighlight());

        for(UiChange change: changes) {
            ChessSquareUI square = this.getSquareUI(change.getPosition().getCoordX(), change.getPosition().getCoordY());
            
            if(change.getIsHighlightedMove()) {
                square.highlight();
            } else {
                square.setText(this.getPieceName(change.getPieceName(), change.getColour()));
            }
        }
    }

    /**
     * Function in charge of mapping a provided PIECENAME and a Colour to a given string representing that piece. 
     * 
     * @param pieceName The PIECENAME associated to the piece. Ex: KING, ROOK, QUEEN etc...
     * @param colour The Colour of the piece. 
     * @return The string representing the given piece. 
     */
    private String getPieceName(PieceName pieceName, Colour colour) {
        switch (pieceName) {
            case KING:
                return colour == Colour.BLACK ? "\u265A" : "\u2654";
            case QUEEN:
                return colour == Colour.BLACK ? "\u265B" : "\u2655";
            case BISHOP:
                return colour == Colour.BLACK ? "\u265D" : "\u2657";
            case ROOK:
                return colour == Colour.BLACK ? "\u265C" : "\u2656";
            case KNIGHT:
                return colour == Colour.BLACK ? "\u265E" : "\u2658";
            case PAWN:
                return colour == Colour.BLACK ? "\u265F" : "\u2659";
            case NULL:
                return "";
        }
        return "";
    }

    /**
     * This private class method creates and all necessary squares to
     * initiate a ChessBoardUI matching the numberOfRows attribute.
     */
    private void addSquaresUI() {
        ChessController controller = new ChessController();
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfRows; column++) {
                ChessSquareUI square = new ChessSquareUI(column, row, controller, this);
                super.add(square);
                listSquares.add(square);
            }
        }
    }

    /**
     * Adds all pieces to the instance of the ChessBoardUI.
     */
    private void addPieces() {
        addWhitePieces();
        addBlackPieces();
    }

    /**
     * Adds all whites pieces by changing the name of the squares
     * to that of the unicode character that represents the correct piece.
     */
    private void addWhitePieces() {
        // Add Pawns
        for (int i = 0; i < numberOfRows; i++) {
            getSquareUI(i, 6).setText(this.getPieceName(PieceName.PAWN, Colour.WHITE));
        }
        // Add towers
        for (int i  = 0; i < 2; i++) {
            getSquareUI(i*7, 7).setText(this.getPieceName(PieceName.ROOK, Colour.WHITE));
        }
        // Add knights
        for (int i  = 0; i < 2; i++) {
            getSquareUI(1+i*5, 7).setText(this.getPieceName(PieceName.KNIGHT, Colour.WHITE));
        }
        // Add bishops
        for (int i  = 0; i < 2; i++) {
            getSquareUI(2+i*3, 7).setText(this.getPieceName(PieceName.BISHOP, Colour.WHITE));
        }
        // Add queen
        getSquareUI(3, 7).setText(this.getPieceName(PieceName.QUEEN, Colour.WHITE));
        // Add king
        getSquareUI(4, 7).setText(this.getPieceName(PieceName.KING, Colour.WHITE));
    }

    /**
     * Adds all black pieces by changing the name of the squares
     * to that of the unicode character that represents the correct piece.
     */
    private void addBlackPieces() {
        // Add Pawns
        for (int i = 0; i < numberOfRows; i++) {
            getSquareUI(i, 1).setText(this.getPieceName(PieceName.PAWN, Colour.BLACK));
        }
        // Add towers
        for (int i  = 0; i < 2; i++) {
            getSquareUI(i*7, 0).setText(this.getPieceName(PieceName.ROOK, Colour.BLACK));
        }
        // Add knights
        for (int i  = 0; i < 2; i++) {
            getSquareUI(1+i*5, 0).setText(this.getPieceName(PieceName.KNIGHT, Colour.BLACK));
        }
        // Add bishops
        for (int i  = 0; i < 2; i++) {
            getSquareUI(2+i*3, 0).setText(this.getPieceName(PieceName.BISHOP, Colour.BLACK));
        }
        // Add queen
        getSquareUI(3, 0).setText(this.getPieceName(PieceName.QUEEN, Colour.BLACK));
        // Add king
        getSquareUI(4, 0).setText(this.getPieceName(PieceName.KING, Colour.BLACK));
    }

    /**
     * Returns a square from the ChessBoardUI instance by its given coordinates.
     * 
     * @param column the column of the square, also its x coordinate.
     * @param row the row of the square, also its y coordinate.
     * @return the ChessSquareUI associated with the given coordinates.
     */
    private ChessSquareUI getSquareUI(int column, int row) {
        int index = numberOfRows * row + column;
        return listSquares.get(index);
    }

}
