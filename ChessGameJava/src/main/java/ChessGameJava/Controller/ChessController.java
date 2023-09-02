package ChessGameJava.Controller;


import java.util.ArrayList;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.ChessABSMove;
import ChessGameJava.Utility.Position;
import ChessGameJava.Utility.UiChange;

/**
 * Class that acts as a controller of the ongoing game. It is in charge of bridging the gap between the front end and the backend.
 * In other words, this class receives request from the front end code ans translates it into a format the logic (backend) can 
 * communicate and work with. It also is in charge of returning the response to the UI in case certain events or changes need 
 * to be made. 
 * 
 * @future_improvements We are planning to add a function that gets the colour of the attacker or attackers. That change is 
 * not planned soon. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-01
 */
public class ChessController {
    private ChessBoardModel board;
    private Position firstPosition;
    private Position secondPosition;
    private Colour currentPlayer;

    /**
     * This method is in charge of determining who the next player should be. it is recommend to override 
     * this method in personal implementations to create games with multiple players. 
     */
    protected void setNextPlayer() {
        this.currentPlayer = this.currentPlayer == Colour.BLACK ? Colour.WHITE : Colour.BLACK;
    }

    /**
     * Constructor of the ChessCOntroller class. Initializes the basic state it needs to function. 
     * Notably, this is where the starting player is set, if you wish for it to be changed, you may override 
     * this constructor and set your desired colour of starting player. It also initializes the backend board. 
     */
    public ChessController() {
        board = new ChessBoardModel();
        firstPosition = null;
        secondPosition = null;
        currentPlayer = Colour.WHITE;
    }

    /**
     * This method is in charge of processing a click made from the UI. It takes the provided 
     * position of the click and saves it withing this class. Depending on if a first click was already 
     * registered, it can either process the click as a "first click" or as a "second click". A first click
     * will return an array of UiChanges containing all the possible squares the piece can go to. On the
     * other hand, a second click will contain all the necessary changes that need to be made in order to 
     * reflect the move (remove piece from UI, change position of piece, etc.).
     * 
     * @param click The Position object containing the coordinates of the square we clicked.
     * @return An ArrayList<UiChange> that contains all the changes. the UI needs to do. 
     */
    public ArrayList<UiChange> processClick(Position click) {
        ArrayList<UiChange> listChanges = new ArrayList<UiChange>();

        // TODO in final release, remove these debugging prints. 
        board.printBoard();
        System.out.println("--------------------------------------------------------------");

        if (this.firstPosition != null) {
            this.secondPosition = click;

            ChessABSMove validMove = this.isMoveValid();
            if (validMove != null) {
                listChanges = validMove.processExecuteMove(board);
                this.setNextPlayer();
            }

            this.firstPosition = null;
            this.secondPosition = null;
        }
        else if (board.getSquareModel(click).getPiece().getColour() == this.currentPlayer){
            this.firstPosition = click;
            ChessSquareModel selectedSquare = board.getSquareModel(click);
            for (ChessABSMove move : selectedSquare.getPiece().getListMoves(selectedSquare, this.board)) {
                listChanges.add(new UiChange(move.getSecondSquare().getPosition(), move.getFirstSquare().getPiece(), true));
            }
        }

        return listChanges;
    }

    /**
     * This method is in charge of verifying if the current registered move of the controller is a valid move.
     * If it is it will return the associated ChessABSMove instance. This function does not create any 
     * side effect but simply return the ChessABSMove object if the provided coordinates do match a move
     * and will return null otherwise. 
     * 
     * If multiple ChessABSMove objects match the provided positions of the first and second click, 
     * it will return the first one found. 
     * 
     * @return the ChessABSMove of the move if it is valid and null otherwise. 
     */
    protected ChessABSMove isMoveValid() {
        if(firstPosition == null || secondPosition == null) {
            return null;
        }

        ChessSquareModel firstSquare = this.board.getSquareModel(firstPosition);
        ChessSquareModel secondSquare = this.board.getSquareModel(secondPosition);
        ChessABSMove validMove = null;

        if (secondSquare.getPiece().getColour() != this.currentPlayer) {
            ArrayList<ChessABSMove> possibleMoves = firstSquare.getPiece().getListMoves(firstSquare, this.board);

            for(ChessABSMove move: possibleMoves) {
                if((move.getFirstSquare() == firstSquare) && (move.getSecondSquare() == secondSquare)) {
                    validMove = move;
                    break;
                }
            }

            if(validMove != null) {
                validMove.processExecuteMove(board);
                if(board.isSquareUnderAttackBy(board.getPositionOfKing(currentPlayer), currentPlayer == Colour.BLACK ? Colour.WHITE : Colour.BLACK)) {
                    validMove.processRevertMove(board);
                    validMove = null;
                } else {
                    validMove.processRevertMove(board);
                }
            }
        }

        return validMove;
    }
}
