package ChessGameJava.Logic.Pieces;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.BasicMoveKing;
import ChessGameJava.Logic.Moves.Castle;
import ChessGameJava.Logic.Moves.ChessABSMove;
import ChessGameJava.Utility.Position;

import java.util.ArrayList;

/**
 * The class representing a king within the project. This class is capable of returning all
 * possible moves that the king can do.
 * @Author Charles Degrandpré
 * @Last_Updated 2023-08-31
 */
public class King extends ChessABSPieceModel {

    /**
     * Constructor of the King class. Assigns the board as well as the colour of the piece.
     * @param board the instance of the chess board.
     * @param colourOfPiece the colour of the king.
     */
    public King(Colour colourOfPiece) {
        super();
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
    public ArrayList<ChessABSMove> getListMoves(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = this.getListAttackingSquares(currentSquare, board);
        ArrayList<ChessABSMove> listMoves = new ArrayList<>();
        for (ChessSquareModel destSquare : listSquares) {
            if(!checkIfMoveAttacksSameColour(currentSquare, destSquare)) {
                listMoves.add(new BasicMoveKing(currentSquare, destSquare));
            }
        }

        System.out.println(this.getCastleMoves(currentSquare, board).size()); // Finds none. 

        listMoves.addAll(this.getCastleMoves(currentSquare, board));

        return listMoves;
    }

    @Override
    public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = new ArrayList<>();
        Position currentPosition = currentSquare.getPosition();

        for (int rowMove : new int[] {-1, 0, 1}) {
            for (int columnMove : new int[] {-1, 0, 1}) {
                try {
                    Position position = currentPosition.sumPosition(rowMove, columnMove);
                    if(!position.equals(currentPosition)) {
                        listSquares.add(board.getSquareModel(position));
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }

        return listSquares;
    }

    protected ArrayList<ChessABSMove> getCastleMoves(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessABSMove> listMoves = new ArrayList<>();
        ArrayList<Position> positionRooks = new ArrayList<>();

        Colour colourOfAttacker = this.colour == Colour.WHITE ? Colour.BLACK : Colour.WHITE;

        if(board.isSquareUnderAttackBy(currentSquare, colourOfAttacker)) {
            return listMoves;
        }

        for (int row = 0; row < ChessBoardModel.numberOfRows; row++) {
            for (int column = 0; column < ChessBoardModel.numberOfRows; column++) {
                try {
                    Position postion = new Position(column, row);
                    if(board.getSquareModel(postion).getPiece() instanceof Rook) {
                        Rook rook = (Rook) board.getSquareModel(postion).getPiece();
                        if (!rook.hasMoved && rook.getColour() == this.colour) {
                            positionRooks.add(postion);
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }

        for(Position positionRook : positionRooks) {
            for(ChessABSMove move1 : super.getListMoves(currentSquare, board)) {
                if(move1.getSecondSquare().getPiece().getColour() == Colour.NULL) { // If square not occupied, even with opponent colour.
                    if(!board.isSquareUnderAttackBy(move1.getSecondSquare(), colourOfAttacker)) { // The first square of king is not under check.
                        for(ChessABSMove move2 : super.getListMoves(move1.getSecondSquare(), board)) {
                            if(move2.getSecondSquare().getPiece().getColour() == Colour.NULL && move2.getSecondSquare() != move1.getSecondSquare()) { // If square not occupied, and not same as first first square
                                if(!board.isSquareUnderAttackBy(move2.getSecondSquare(), colourOfAttacker)) { // The second square of king is not under attack.
                                    if(positionRook.distanceTo(currentSquare.getPosition()) > positionRook.distanceTo(move2.getSecondSquare().getPosition())) {
                                        Castle castleMove = new Castle(move1.getSecondSquare(), move2.getSecondSquare(), board.getSquareModel(positionRook), currentSquare);
                                        castleMove.processExecuteMove(board);
                                        System.out.println("Normally just called processExecuteMOve on castle");
                                        if(!board.isSquareUnderAttackBy(board.getPositionOfKing(this.colour), colourOfAttacker)) {
                                            listMoves.add(castleMove);
                                        }
                                        System.out.println("Just before processRevertMove on castle");
                                        castleMove.processRevertMove(board);
                                        System.out.println("Normally just called processRevertMove on castle");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println(positionRooks.size()); // Finds both, regular behavior. 

        // for(Position positionRook : positionRooks) {
        //     for(ChessSquareModel firstSquare : this.getListAttackingSquares(currentSquare, board)) {
        //         if (board.isSquareUnderAttackBy(firstSquare, colourOfAttacker) || firstSquare.getPiece().getColour() != Colour.NULL) {
        //             continue;
        //         }
        //         System.out.println("Found a valid square");
        //         for (int rowMove : new int[] { -1, 0, 1}) { // This is a cause for bugs. 
        //             for (int columnMove : new int[] {-1, 0, 1}) { // So is this btw. 
        //                 try {
        //                     Position position = firstSquare.getPosition().sumPosition(rowMove, columnMove);
        //                     if(!position.equals(currentSquare.getPosition()) && !position.equals(firstSquare.getPosition())) {
        //                         ChessSquareModel secondSquare = board.getSquareModel(position);
        //                         if(board.isSquareUnderAttackBy(secondSquare, colourOfAttacker)) {
        //                             continue;
        //                         }
        //                         if(currentSquare.getPosition().distanceTo(positionRook) < currentSquare.getPosition().distanceTo(position) && secondSquare.getPiece().getColour() == Colour.NULL) {
        //                             Castle castleMove = new Castle(firstSquare, secondSquare, board.getSquareModel(positionRook), currentSquare);
        //                             castleMove.processExecuteMove(board);
        //                             if(!board.isSquareUnderAttackBy(board.getPositionOfKing(this.colour), colourOfAttacker)) {
        //                                 listMoves.add(castleMove);
        //                             }
        //                             castleMove.processRevertMove(board);
        //                         }
        //                     }
        //                 } catch (Exception e) {
        //                     continue;
        //                 }
        //             }
        //         }
        //     }
        // }

        return listMoves;
    }
}
