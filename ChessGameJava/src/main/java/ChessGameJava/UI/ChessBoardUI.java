package ChessGameJava.UI;

import javax.swing.*;

import ChessGameJava.Controller.ChessController;
import ChessGameJava.Utility.UiChange;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class representing the chess board on the User Interface side of this project.
 * This class is capable of loading a chess game with the basic pieces in place
 * as well as add a new square of piece if such is desired.
 * @Author Charles Degrandpré
 * @Last_Updated 2022-12-23
 */
public class ChessBoardUI extends JFrame{

    private static final int numberOfRows = 8;
    private final ArrayList<ChessSquareUI> list = new ArrayList<>();

    /**
     * Constructor for the ChessBoardUI class. Initiates all attributes of the class
     * and initiates base values in terms of size and titles for the board.
     * This constructor adds all the squares and pieces to the UI. The pieces are
     * identified with the unicode strings from the chess pieces icons and not
     * images.
     */
    public ChessBoardUI() {
        super(); // Create the JFrame.
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(450, 450);
        super.setTitle("ChessGame");

        super.setLayout(new GridLayout(8, 8));

        this.addSquaresUI();
        this.addPieces();

        super.setVisible(true);
    }

    public void makeChanges(ArrayList<UiChange> changes) {
        for(UiChange change: changes) {
            this.getSquareUI(change.position)
        }
    }

    /**
     * This private class method creates and all necessary squares to
     * initiate a ChessBoardUI matching the numberOfRows attribute.
     */
    private void addSquaresUI() {
        ChessController controller = new ChessController();
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfRows; column++) {
                ChessSquareUI square = new ChessSquareUI(column, row, controller);
                super.add(square);
                list.add(square);
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
            getSquareUI(6, i).setText("\u2659");
        }
        // Add towers
        for (int i  = 0; i < 2; i++) {
            getSquareUI(7, i*7).setText("\u2656");
        }
        // Add knights
        for (int i  = 0; i < 2; i++) {
            getSquareUI(7, 1+i*5).setText("\u2658");
        }
        // Add bishops
        for (int i  = 0; i < 2; i++) {
            getSquareUI(7, 2+i*3).setText("\u2657");
        }
        // Add queen
        getSquareUI(7,3).setText("\u2655");
        // Add king
        getSquareUI(7,4).setText("\u2654");
    }

    /**
     * Adds all black pieces by changing the name of the squares
     * to that of the unicode character that represents the correct piece.
     */
    private void addBlackPieces() {
        // Add Pawns
        for (int i = 0; i < numberOfRows; i++) {
            getSquareUI(1, i).setText("\u265F");
        }
        // Add towers
        for (int i  = 0; i < 2; i++) {
            getSquareUI(0, i*7).setText("\u265C");
        }
        // Add knights
        for (int i  = 0; i < 2; i++) {
            getSquareUI(0, 1+i*5).setText("\u265E");
        }
        // Add bishops
        for (int i  = 0; i < 2; i++) {
            getSquareUI(0, 2+i*3).setText("\u265D");
        }
        // Add queen
        getSquareUI(0,3).setText("\u265B");
        // Add king
        getSquareUI(0,4).setText("\u265A");
    }

    /**
     * Returns a square from the ChessBoardUI instance by its given coordinates.
     * @param row the row of the square, also its y coordinate.
     * @param column the column of the square, also its x coordinate.
     * @return the square associated with the given coordinates.
     */
    private ChessSquareUI getSquareUI(int row, int column) {
        int index = numberOfRows * row + column;
        return list.get(index);
    }

}
