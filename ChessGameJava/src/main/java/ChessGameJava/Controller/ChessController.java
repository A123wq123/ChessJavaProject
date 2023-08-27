package ChessGameJava.Controller;


import java.util.ArrayList;

import javax.lang.model.type.NullType;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.ChessABSMove;
import ChessGameJava.Utility.Position;

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
        // Input here the board game mode? Or maybe add a function down the line that can do this but with more
        // options.
    }

    /**
     * @TODO this method should return at some point an array of positions that are all the squares we can go to.
     * 
     * @param click
     */
    public ArrayList<Position> executeMove(Position click) {

        if (this.firstSquare != null) {
            this.secondSquare = click;

            // If the move is valid, then execute it and return appropriate data
            ChessABSMove validMove = this.isMoveValid();
            if (validMove != null) {
                validMove.executeMove(board);
            }
        }
        else {
            if (board.getSquareModel(click).getPiece().getColour() == this.currentPlayer){
                this.firstSquare = click;
                ChessSquareModel selectedSquare = board.getSquareModel(click);
                // return (ArrayList<Position>) selectedSquare.getPiece().getListMoves(selectedSquare).stream().map(move -> move.getSecondSquare().getPosition()).toList();
            }
        }

        return null;
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
            ArrayList<ChessABSMove> possibleMoves = firstClick.getPiece().getListMoves(firstClick);

            for(ChessABSMove move: possibleMoves) {
                if((move.getFirstSquare() == firstClick) && (move.getSecondSquare() == secondClick)) {
                    validMove = move;
                    break;
                }
            }

            if(validMove != null) {
                validMove.executeMove(board);
                if(board.isKingUnderAttack(currentPlayer)) {
                    validMove = null;
                }
                validMove.revertMove(board);
            }
        }

        return validMove;
    }

}
