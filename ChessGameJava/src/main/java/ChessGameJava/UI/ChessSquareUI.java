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
 * This class also manages the events necessary to this project such as mouse click and
 * pressed in order to move the pieces. This lass extends the JLabel class from the swing package
 * and implements the MouseListener interface from the same package.
 * @Author Charles Degrandpré
 * @Last_Updated 2022-12-23
 */
public class ChessSquareUI extends JLabel implements MouseListener {

    public Position position;
    public String pieceName;
    private ChessController controller;

    /**
     * Constructor of a ChessSquareUI component. Sets every variable necessary for proper ui
     * style except the name of the piece standing on it.
     * @param column the column of the square, which is also its x coordinate.
     * @param row the row of the square, which is also its y coordinate.
     */
    public ChessSquareUI(int column, int row, ChessController controller) {
        position = new Position(column, row);
        //pieceName = String.valueOf("\u2654");
        pieceName = null;
        //String noName = "\u2654";
        this.controller = controller;

        if ((position.sumCoordinates())%2 == 0) {
            this.setBackground(Color.WHITE);
        }
        else {
            this.setBackground(Color.LIGHT_GRAY);
        }

        this.setOpaque(true);

        this.setVerticalAlignment(0);
        this.setHorizontalAlignment(0);
        this.setFont(new Font(Font.MONOSPACED,Font.PLAIN, 80));
        this.setText(pieceName);

        this.addMouseListener(this);
    }

    /** TODO
     * Catches and process the event linked with a mouse button clicked in order
     * to correctly mouve a piece of the chess board.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        ArrayList<UiChange> changes = this.controller.processClick(position);

        ((ChessBoardUI) e.getComponent().getParent()).makeChanges(changes);
    }

    /**
     * TODO
     * Catches and process the event linked with a mouse button pressed in order
     * to correctly mouve a piece of the chess board.
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * TODO
     * Catches and process the event linked with a mouse button released in order
     * to correctly mouve a piece of the chess board.
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * TODO
     * Catches and process the event linked with a mouse button entered in order
     * to correctly mouve a piece of the chess board. This is for when a mouse enters the UI
     * area for a square, you may see it as when we are hovering a square.
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * TODO
     * Catches and process the event linked with a mouse button exited in order
     * to correctly mouve a piece of the chess board. This is for when a mouse leaves the UI
     * area for a square, you may see it as when we are no longer hovering a square.
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Highlights a the current square by changing its colour. 
     */
    public void highlight() {
        this.setBackground(Color.GRAY);
    }

    /**
     * Removes the highlight on a square by changing back its colour. 
     */
    public void removeHighlight() {
        if ((position.sumCoordinates())%2 == 0) {
            this.setBackground(Color.WHITE);
        } else {
            this.setBackground(Color.LIGHT_GRAY);
        }
    }
}
