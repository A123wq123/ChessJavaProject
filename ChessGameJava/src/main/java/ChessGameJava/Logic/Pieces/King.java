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
 * possible moves that the king can do and all squares the king can attack. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-08-31
 */
public class King extends ChessABSPieceModel {

    /**
     * Constructor of the King class. Assigns the colour of the piece. 
     * 
     * @param colourOfPiece the colour of the king.
     */
    public King(Colour colourOfPiece) {
        this.colour = colourOfPiece;
    }

    /**
     * Returns all the possibles moves the king can make by receiving its square
     * as a parameter. This method can check if a move is legal (does not cross a piece
     * etc.) but cannot check if the king is in check after the move.
     * 
     * This method gets the moves for a regular king move (moving on square next to its position) 
     * and the castling. 
     */
    @Override
    public ArrayList<ChessABSMove> getListMoves(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = this.getListAttackingSquares(currentSquare, board);
        ArrayList<ChessABSMove> listMoves = new ArrayList<>();
        for (ChessSquareModel destSquare : listSquares) {
            if(!this.checkIfMoveAttacksSameColour(destSquare)) {
                listMoves.add(new BasicMoveKing(currentSquare, destSquare));
            }
        }

        listMoves.addAll(this.getCastleMoves(currentSquare, board));

        return listMoves;
    }

    /**
     * This method returns a list of all the squares the king can attack. This includes all squares 
     * that are around the provided currentSquare. 
     */
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

    /**
     * This method is used to calculate the possible FIDE accurate castling moves a king can do. 
     * See here for reference: https://www.fide.com/FIDE/handbook/LawsOfChess.pdf. 
     * 
     * The official Fédération Internationnale des Échecs (FIDE for short) defines a castling 
     * move as when a king, who has not moved previously in the game, moves two squares towards 
     * a rook, that also has not moved during this game, while not being in check on any of the 
     * squares he traverses (this includes the one he start at). We then move the rook to the square
     * the king has first moved to and then check again to see if he is in check at the end. If the
     * king is not in check at the end of the move, and has met all restrictions noted then the move 
     * is considered valid. All squares the king move to must also be unoccupied. 
     * 
     * This allows for some unusual behavior such as castling in diagonal for long as after moving 
     * those two squares the king is closer to the rook than when he began. Since moving towards a destination
     * (the rook) is simply getting closer to it, this interpretation is valid. The rules also say nothing
     * of if the rook must be freed and have access to teh square que gets moved to. You can see it as if 
     * the rook gets teleported to its destination. 
     * 
     * @param currentSquare The ChessSquareModel the king is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an ArrayList of ChessABSMove the piece can execute.
     */
    protected ArrayList<ChessABSMove> getCastleMoves(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessABSMove> listMoves = new ArrayList<>();
        ArrayList<Position> positionRooks = new ArrayList<>();

        Colour colourOfAttacker = this.colour == Colour.WHITE ? Colour.BLACK : Colour.WHITE;

        if(board.isSquareUnderAttackBy(currentSquare, colourOfAttacker)) {
            return listMoves;
        }

        for(ChessSquareModel square : board.getSquareList()) {
            ChessABSPieceModel piece = square.getPiece();
            if(piece instanceof Rook && !piece.hasMoved && piece.getColour() == this.colour) {
                positionRooks.add(square.getPosition());
            }
        }

        for(Position positionRook : positionRooks) {
            for(ChessABSMove move1 : super.getListMoves(currentSquare, board)) {
                if(move1.getSecondSquare().getPiece().getColour() == Colour.NULL) { 
                    if(!board.isSquareUnderAttackBy(move1.getSecondSquare(), colourOfAttacker)) { 
                        for(ChessABSMove move2 : super.getListMoves(move1.getSecondSquare(), board)) {
                            if(move2.getSecondSquare().getPiece().getColour() == Colour.NULL && move2.getSecondSquare() != currentSquare) { 
                                if(!board.isSquareUnderAttackBy(move2.getSecondSquare(), colourOfAttacker)) { 
                                    if(positionRook.distanceTo(currentSquare.getPosition()) > positionRook.distanceTo(move2.getSecondSquare().getPosition())) { 
                                        Castle castleMove = new Castle(move1.getSecondSquare(), move2.getSecondSquare(), board.getSquareModel(positionRook), currentSquare);
                                        castleMove.processExecuteMove(board);
                                        if(!board.isSquareUnderAttackBy(board.getSquareOfKing(this.colour), colourOfAttacker)) {
                                            listMoves.add(castleMove);
                                        }
                                        castleMove.processRevertMove(board);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return listMoves;
    }
}
