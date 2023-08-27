package ChessGameJava.Logic.Pieces;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.ChessABSMove;

import java.util.ArrayList;

/**
 * The class representing a king within the project. This class is capable of returning all
 * possible moves that the king can do.
 * @Author Charles Degrandpré
 * @Last_Updated 2022-12-23
 */
public class King extends ChessABSPieceModel {

    /**
     * Constructor of the King class. Assigns the board as well as the colour of the piece.
     * @param board the instance of the ches board.
     * @param colourOfPiece the colour of the king.
     */
    public King(ChessBoardModel board, Colour colourOfPiece) {
        this.board = board;
        this.colour = colourOfPiece;
    }

    /**
     * Returns all the possibles moves the king can make by receiving its square
     * as a parameter. This method can check if a move is legal (does not cross a piece
     * etc.) but cannot check if the king is in check after the move.
     * @param currentSquare the square where the pice is positionned.
     * @return an array of the possible squares the king can get to.
     */
    @Override
    public ArrayList<ChessABSMove> getListMoves(ChessSquareModel currentSquare) {

        return null;

        // ArrayList<ChessSquareModel> listOfMoves = new ArrayList<>();
        // Position currentPosition = currentSquare.getPosition();

        // for (int rowMove : new int[] {-1, 0, 1}) {
        //     for (int columnMove : new int[] {-1, 0, 1}) {
        //         Position destPosition;
        //         try {
        //             destPosition = currentPosition.sumPosition(rowMove, columnMove);
        //         } catch (Exception e) {
        //             continue;
        //         }
        //         ChessSquareModel destSquare = this.board.getSquareModel(destPosition);
        //         if (checkIfMoveLegal(currentSquare, destSquare)) {
        //             listOfMoves.add(destSquare);
        //         }
        //     }
        // }

        // return listOfMoves;
    }
}
