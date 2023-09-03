package ChessGameJava.UI;


import ChessGameJava.Controller.ChessController;
import ChessGameJava.Utility.Position;
import ChessGameJava.Utility.UiChange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.String;
import java.util.ArrayList;

/**
 * Class representing a chess square on the User Interface side of this project. This class is
 * capable of rendering a chess square on the UI and manage the piece present on it.
 * This class extends the JLabel class from the swing package and implements the MouseListener interface 
 * from the same package.
 * 
 * For now, we only trigger behavior on the mouse click but this behavior can be overridden. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-03
 */
public class ChessSquareUI extends JLabel implements MouseListener {

    public Position position;
    private ChessController controller;
    private ChessBoardUI uiBoard;

    /**
     * Constructor of a ChessSquareUI component. Sets every variable necessary for proper ui
     * style except the name of the piece standing on it.
     * 
     * @param column the column of the square, which is also its x coordinate.
     * @param row the row of the square, which is also its y coordinate.
     * @param controller The ChessController instance managing this game. 
     * @param uiBoard The ChessBoardUI instance the ChessSquareUI is part of. 
     */
    public ChessSquareUI(int column, int row, ChessController controller, ChessBoardUI uiBoard) {
        this.position = new Position(column, row);
        this.controller = controller;
        this.uiBoard = uiBoard;

        this.setBlackOrWHite();

        this.setOpaque(true);

        this.setVerticalAlignment(0);
        this.setHorizontalAlignment(0);
        this.setFont(new Font(Font.MONOSPACED,Font.PLAIN, 80));
        this.setText(null);

        this.addMouseListener(this);
    }

    /**
     * Catches and process the event linked with a mouse button clicked in order
     * to correctly move a piece of the chess board.
     * 
     * It makes a call to ChessController.processClick() to obtain a series of 
     * UIChanges that it sends to ChessBoardUI.makeChanges to apply them. 
     * 
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(String.format("x: %s, y: %s", position.getCoordX(), position.getCoordY()));
        ArrayList<UiChange> changes = this.controller.processClick(position);
        this.uiBoard.makeChanges(changes);
    }

    /**
     * Event handler for mouse press. Does nothing. 
     * 
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Do nothing.
    }

    /**
     * Event handler for mouse release. Does nothing.
     * 
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Do nothing
    }

    /**
     * Event handler for mouse entered. Does nothing.
     * 
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Do nothing
    }

    /**
     * Event handler for mouse exited. Does nothing.
     * 
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Do nothing
    }

    /**
     * Highlights self by changing its colour. 
     */
    public void highlight() {
        this.setBackground(Color.GRAY);
    }

    /**
     * Removes the highlight on a square by changing back its colour. 
     */
    public void removeHighlight() {
        this.setBlackOrWHite();
    }

    /**
     * This method sets the colour of the square, its background 
     * colour, to either white or black depending on its position. 
     */
    private void setBlackOrWHite() {
        if ((position.getCoordX() + position.getCoordY())%2 == 0) {
            this.setBackground(Color.WHITE);
        } else {
            this.setBackground(Color.LIGHT_GRAY);
        }
    }
}
