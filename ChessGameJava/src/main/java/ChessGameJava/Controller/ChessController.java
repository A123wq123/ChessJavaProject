package ChessGameJava.Controller;


import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.ChessABSMove;
import ChessGameJava.Utility.Position;
import ChessGameJava.Utility.UiChange;

/** TODO
 * Add java doc.
 * - Charles
 */
public class ChessController {
    private ChessBoardModel board;
    private Position firstSquare;
    private Position secondSquare;
    private Colour currentPlayer;

    public ChessController() {
        board = new ChessBoardModel();
        firstSquare = null;
        secondSquare = null;
        currentPlayer = Colour.WHITE;
    }

    /**
     * This method is in charge of processing a click made from the UI. It takes the provided 
     * position of the click
     * @param click
     */
    public ArrayList<UiChange> processClick(Position click) {
        ArrayList<UiChange> listChanges = new ArrayList<UiChange>();

        board.printBoard();
        System.out.println("--------------------------------------------------------------");

        if (this.firstSquare != null) {
            this.secondSquare = click;

            ChessABSMove validMove = this.isMoveValid();
            System.out.println(validMove);
            if (validMove != null) {
                listChanges = validMove.executeMove(board);
                this.board.getSquareModel(this.firstSquare).getPiece().hasMoved = true;
                this.currentPlayer = this.currentPlayer == Colour.BLACK ? Colour.WHITE : Colour.BLACK;
            }

            this.firstSquare = null;
            this.secondSquare = null;
        }
        else if (board.getSquareModel(click).getPiece().getColour() == this.currentPlayer){
            this.firstSquare = click;
            ChessSquareModel selectedSquare = board.getSquareModel(click);
            for (ChessABSMove move : selectedSquare.getPiece().getListMoves(selectedSquare, this.board)) {
                // Might need to further filter the moves depending on like a game mode. 
                // The idea being that a piece returns the moves it can do but has no knowledge of the game mode. 
                // A great example is checkmate vrs est all. in eat all a move is valid even if it puts the king 
                // in check which is NOT the case of checkmate. 
                listChanges.add(new UiChange(move.getSecondSquare().getPosition(), move.getFirstSquare().getPiece(), true));
            }
        }

        return listChanges;
    }

    /**
     * This method is in charge of verifying if the current registered move of the controller is a valid move.
     * If it is it will return the associated ChessABSMove instance. 
     * @return the ChessABSMove of the move if it is valid and null otherwise. 
     */
    private ChessABSMove isMoveValid() {
        if(firstSquare == null || secondSquare == null) {
            return null;
        }

        ChessSquareModel firstClick = this.board.getSquareModel(firstSquare);
        ChessSquareModel secondClick = this.board.getSquareModel(secondSquare);

        ChessABSMove validMove = null;

        if (secondClick.getPiece().getColour() != this.currentPlayer) {
            ArrayList<ChessABSMove> possibleMoves = firstClick.getPiece().getListMoves(firstClick, this.board);

            for(ChessABSMove move: possibleMoves) {
                if((move.getFirstSquare() == firstClick) && (move.getSecondSquare() == secondClick)) {
                    validMove = move;
                    break;
                }
            }

            if(validMove != null) {
                validMove.executeMove(board);
                if(board.isKingUnderAttack(currentPlayer == Colour.BLACK ? Colour.WHITE : Colour.BLACK)) {
                    System.out.println("King is under attack?!");
                    validMove.revertMove(board);
                    validMove = null;
                } else {
                    validMove.revertMove(board);
                }
            }
        }

        return validMove;
    }
}
