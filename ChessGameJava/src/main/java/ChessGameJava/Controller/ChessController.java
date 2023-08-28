package ChessGameJava.Controller;


import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.ChessABSMove;
import ChessGameJava.Logic.Pieces.ChessABSPieceModel;
import ChessGameJava.Logic.Pieces.King;
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
        // Input here the board game mode? Or maybe add a function down the line that can do this but with more
        // options.
    }

    /**
     * This method is in charge of processing a click made from the UI. It takes the provided 
     * position of the click
     * @param click
     */
    public ArrayList<UiChange> processClick(Position click) {
        ArrayList<UiChange> listChanges = new ArrayList<UiChange>();

        if (this.firstSquare != null) {
            this.secondSquare = click;

            ChessABSMove validMove = this.isMoveValid();
            if (validMove != null) {
                listChanges = validMove.executeMove(board);
                this.currentPlayer = this.currentPlayer == Colour.BLACK ? Colour.WHITE : Colour.BLACK;
            }

            this.firstSquare = null;
            this.secondSquare = null;
        }
        else if (board.getSquareModel(click).getPiece().getColour() == this.currentPlayer){
            this.firstSquare = click;
            ChessSquareModel selectedSquare = board.getSquareModel(click);
            for (ChessABSMove move : selectedSquare.getPiece().getListMoves(selectedSquare)) {
                listChanges.add(createUiChange(move, true));
            }
        }

        return listChanges;
    }

    /**
     * This method creates the UiCHange associated with a given move and a boolean that indicates if this
     * is a possible move we have not yet executed. 
     * @param move
     * @param isPossibleMove
     * @return The associated UiChange instance. 
     */
    private UiChange createUiChange(ChessABSMove move, boolean isPossibleMove) {
        ChessSquareModel destination = move.getSecondSquare();
        UiChange.PIECENAME pieceName = getPieceName(destination.getPiece());

        return new UiChange(destination.getPosition(), pieceName, destination.getPiece().getColour(), isPossibleMove);
    }

    /**
     * This function is in charge of mapping a provided piece to one of the valid UiChange.PIECENAME, this is 
     * to decouple the UI from the logic and not have them hold direct pieces but morse so only what they represent.
     * @param piece the piece we want to map.
     * @return The associated UiChange.PIECENAME
     */
    private UiChange.PIECENAME getPieceName(ChessABSPieceModel piece) {
        if (piece instanceof King) {
            return UiChange.PIECENAME.KING;
        }

        return UiChange.PIECENAME.NULL;
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
