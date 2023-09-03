package ChessGameJava.Logic.Pieces;

import java.util.ArrayList;
import java.util.Arrays;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Logic.Moves.BasicMovePawn;
import ChessGameJava.Logic.Moves.ChessABSMove;
import ChessGameJava.Logic.Moves.EnPassant;
import ChessGameJava.Utility.Position;

/**
 * This class represents a pawn within the project. It is capable of returning 
 * all the moves a pawn can do and return all the squares it can attack. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-02
 */
public class Pawn extends ChessABSPieceModel{

    public static Integer turnOfLastPlayedPawn = null;
    public Integer turnOfDoubleMove = null;

    /**
     * Constructor of the Pawn class. Assigns the colour of the piece.
     * 
     * @param colourOfPiece the colour of the Pawn.
     */
    public Pawn(Colour colourOfPiece) {
        this.colour = colourOfPiece;
    }

    /**
     * Returns all the possible moves a pawn can do. These moves include any 
     * take in diagonal of a piece, any regular forward moves as well as any 
     * possible en-passant moves. 
     */
    @Override
    public ArrayList<ChessABSMove> getListMoves(ChessSquareModel currentSquare, ChessBoardModel board) {
        Colour opponentColour = this.colour == Colour.BLACK ? Colour.WHITE : Colour.BLACK;

        ArrayList<ChessABSMove> listMoves = new ArrayList<>();

        for(ChessSquareModel square : this.getListAttackingSquares(currentSquare, board)) {
            if(square.getPiece().getColour() == opponentColour) {
                listMoves.add(new BasicMovePawn(currentSquare, square)); 
            }
        }

        listMoves.addAll(this.getMovesInFront(currentSquare, board));
        listMoves.addAll(this.getMovesEnPassant(currentSquare, board));

        return listMoves;
    }

    /**
     * This method returns the list of attacking squares depending on the colour of the player. 
     * 
     * For a pawn, this is the two squares in diagonal of the pawn directly in front of him. 
     * The front is determine by the direction the pawn moves towards. 
     */
    @Override
    public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board) {
        return this.colour == Colour.WHITE ? this.getListAttackingSquaresWhite(currentSquare, board) : this.getListAttackingSquaresBlack(currentSquare, board);
    }

    /**
     * Helper function to calculate the two squares a pawn can attack if he is White.
     * 
     * These squares are the two squares in diagonally directly in front of the pawn. 
     * 
     * Note: Be careful, as this method throws a RuntimeException if it is being called 
     * from a pawn whose colour isn't white. 
     * 
     * @param currentSquare The ChessSquareModel the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an ArrayList of ChessABSMove the pawn can execute.
     */
    protected ArrayList<ChessSquareModel> getListAttackingSquaresWhite(ChessSquareModel currentSquare, ChessBoardModel board) {
        if (this.colour != Colour.WHITE) {
            throw new RuntimeException("Cannot call method, the piece is not of colour White");
        }

        ArrayList<ChessSquareModel> listSquares = new ArrayList<>();

        for (int x : Arrays.asList(-1, 1)) {
            try {
                Position position = currentSquare.getPosition().sumPosition(x, -1);
                listSquares.add(board.getSquareModel(position));
            } catch (Exception e) {
                continue;
            }
        }

        return listSquares;
    }

    /**
     * Helper function to calculate the two squares a pawn can attack if he is Black.
     * 
     * These squares are the two squares in diagonally directly in front of the pawn. 
     * 
     * Note: Be careful, as this method throws a RuntimeException if it is being called 
     * from a pawn whose colour isn't black. 
     * 
     * @param currentSquare The ChessSquareModel the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an ArrayList of ChessABSMove the pawn can execute.
     */
    protected ArrayList<ChessSquareModel> getListAttackingSquaresBlack(ChessSquareModel currentSquare, ChessBoardModel board) {
        if (this.colour != Colour.BLACK) {
            throw new RuntimeException("Cannot call method, the piece is not of colour Black");
        }

        ArrayList<ChessSquareModel> listSquares = new ArrayList<>();

        for (int x : Arrays.asList(-1, 1)) {
            try {
                Position position = currentSquare.getPosition().sumPosition(x, 1);
                listSquares.add(board.getSquareModel(position));
            } catch (Exception e) {
                continue;
            }
        }

        return listSquares;
    }

    /**
     * Helper function to calculate the squares a pawn can move to in front of him. It is
     * to note here that we consider the moves in front to be the squares a pawn can move 
     * directly in front of him, not diagonally. 
     * 
     * It is possible for a pawn to make two types of such moves, he can move one square forward
     * or, if he has yet to move during the game, two squares forward. 
     * 
     * @param currentSquare The ChessSquareModel the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an ArrayList of ChessABSMove the pawn can execute.
     */
    protected ArrayList<ChessABSMove> getMovesInFront(ChessSquareModel currentSquare, ChessBoardModel board) {
        return this.colour == Colour.WHITE ? this.getMovesInFrontWhite(currentSquare, board) : this.getMovesInFrontBlack(currentSquare, board);
    }

    /**
     * Helper function to calculate the squares a pawn can move to in front of him if he is white.
     * 
     * It is to note here that we consider the moves in front to be the squares a pawn can move 
     * directly in front of him, not diagonally. 
     * 
     * It is possible for a pawn to make two types of such moves, he can move one square forward
     * or, if he has yet to move during the game, two squares forward.
     * 
     * Note: Be careful, as this method throws a RuntimeException if it is being called 
     * from a pawn whose colour isn't white.
     * 
     * @param currentSquare The ChessSquareModel the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an ArrayList of ChessABSMove the pawn can execute.
     */
    protected ArrayList<ChessABSMove> getMovesInFrontWhite(ChessSquareModel currentSquare, ChessBoardModel board) {
        if (this.colour != Colour.WHITE) {
            throw new RuntimeException("Cannot call method, the piece is not of colour White");
        }

        ArrayList<ChessABSMove> listMoves = new ArrayList<>();

        try {
            Position position1 = currentSquare.getPosition().sumPosition(0, -1);
            if(board.getSquareModel(position1).getPiece().getColour() == Colour.NULL) {
                listMoves.add(new BasicMovePawn(currentSquare, board.getSquareModel(position1)));
                if(!this.hasMoved) {
                    Position position2 = currentSquare.getPosition().sumPosition(0, -2);
                    if(board.getSquareModel(position2).getPiece().getColour() == Colour.NULL) {
                        listMoves.add(new BasicMovePawn(currentSquare, board.getSquareModel(position2)));
                    }
                }
            }
        } catch (Exception e) {
            // Do nothing
        }

        return listMoves;
    }

    /**
     * Helper function to calculate the squares a pawn can move to in front of him if he is black.
     * 
     * It is to note here that we consider the moves in front to be the squares a pawn can move 
     * directly in front of him, not diagonally. 
     * 
     * It is possible for a pawn to make two types of such moves, he can move one square forward
     * or, if he has yet to move during the game, two squares forward.
     * 
     * Note: Be careful, as this method throws a RuntimeException if it is being called 
     * from a pawn whose colour isn't black.
     * 
     * @param currentSquare The ChessSquareModel the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an ArrayList of ChessABSMove the pawn can execute.
     */
    protected ArrayList<ChessABSMove> getMovesInFrontBlack(ChessSquareModel currentSquare, ChessBoardModel board) {
        if (this.colour != Colour.BLACK) {
            throw new RuntimeException("Cannot call method, the piece is not of colour Black");
        }

        ArrayList<ChessABSMove> listMoves = new ArrayList<>();

        try {
            Position position1 = currentSquare.getPosition().sumPosition(0, 1);
            if(board.getSquareModel(position1).getPiece().getColour() == Colour.NULL) {
                listMoves.add(new BasicMovePawn(currentSquare, board.getSquareModel(position1)));
                if(!this.hasMoved) {
                    Position position2 = currentSquare.getPosition().sumPosition(0, 2);
                    if(board.getSquareModel(position2).getPiece().getColour() == Colour.NULL) {
                        listMoves.add(new BasicMovePawn(currentSquare, board.getSquareModel(position2))); 
                    }
                }
            }
        } catch (Exception e) {
            // Do nothing
        }

        return listMoves;
    }

    /**
     * Method used to return the possible en-passant moves a pawn ca make. 
     * 
     * We define an en-passant move as when a pawn is on the same row as a pawn 
     * from his opponent who has just moved two squares in front (what we refer to 
     * as a double pawn move). On this turn, and only this turn, we may capture the 
     * opponents pawn my moving to the square behind the opponents pawn and removing
     * it from the game. 
     * 
     * @param currentSquare The ChessSquareModel the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an ArrayList of ChessABSMove the pawn can execute.
     */
    protected ArrayList<ChessABSMove> getMovesEnPassant(ChessSquareModel currentSquare, ChessBoardModel board) {
        if(Pawn.turnOfLastPlayedPawn == null) {
            return new ArrayList<>();
        }

        return this.colour == Colour.WHITE ? this.getMovesEnPassantWhite(currentSquare, board) : this.getMovesEnPassantBlack(currentSquare, board);
    }

    /**
     * Method used to return the possible en-passant moves a pawn ca make if he is white. 
     * 
     * We define an en-passant move as when a pawn is on the same row as a pawn 
     * from his opponent who has just moved two squares in front (what we refer to 
     * as a double pawn move). On this turn, and only this turn, we may capture the 
     * opponents pawn my moving to the square behind the opponents pawn and removing
     * it from the game. 
     * 
     * Note: Be careful, as this method throws a RuntimeException if it is being called 
     * from a pawn whose colour isn't white.
     * 
     * @param currentSquare The ChessSquareModel the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an ArrayList of ChessABSMove the pawn can execute.
     */
    protected ArrayList<ChessABSMove> getMovesEnPassantWhite(ChessSquareModel currentSquare, ChessBoardModel board) {
        if (this.colour != Colour.WHITE) {
            throw new RuntimeException("Cannot call method, the piece is not of colour White");
        }

        ArrayList<ChessABSMove> listMoves = new ArrayList<>();
        
        for (int x : Arrays.asList(-1, 1)) {
            try {
                Position position = currentSquare.getPosition().sumPosition(x, 0);
                if(board.getSquareModel(position).getPiece() instanceof Pawn) {
                    Pawn pawn = (Pawn) board.getSquareModel(position).getPiece();
                    if(pawn.turnOfDoubleMove == ChessABSMove.getMoveCountForBoard(board) -1) {
                        if(board.getSquareModel(position.sumPosition(0, -1)).getPiece().getColour() == Colour.NULL) {
                            listMoves.add(new EnPassant(currentSquare, board.getSquareModel(position.sumPosition(0, -1)), board.getSquareModel(position))); 
                        }
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }

        return listMoves;
    }

    /**
     * Method used to return the possible en-passant moves a pawn ca make if he is black. 
     * 
     * We define an en-passant move as when a pawn is on the same row as a pawn 
     * from his opponent who has just moved two squares in front (what we refer to 
     * as a double pawn move). On this turn, and only this turn, we may capture the 
     * opponents pawn my moving to the square behind the opponents pawn and removing
     * it from the game. 
     * 
     * Note: Be careful, as this method throws a RuntimeException if it is being called 
     * from a pawn whose colour isn't black.
     * 
     * @param currentSquare The ChessSquareModel the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an ArrayList of ChessABSMove the pawn can execute.
     */
    protected ArrayList<ChessABSMove> getMovesEnPassantBlack(ChessSquareModel currentSquare, ChessBoardModel board) {
        if (this.colour != Colour.BLACK) {
            throw new RuntimeException("Cannot call method, the piece is not of colour Black");
        }

        ArrayList<ChessABSMove> listMoves = new ArrayList<>();
        
        for (int x : Arrays.asList(-1, 1)) {
            try {
                Position position = currentSquare.getPosition().sumPosition(x, 0);
                if(board.getSquareModel(position).getPiece() instanceof Pawn) {
                    Pawn pawn = (Pawn) board.getSquareModel(position).getPiece();
                    if(pawn.turnOfDoubleMove == ChessABSMove.getMoveCountForBoard(board) -1 ) {
                        if(board.getSquareModel(position.sumPosition(0, 1)).getPiece().getColour() == Colour.NULL) {
                            listMoves.add(new EnPassant(currentSquare, board.getSquareModel(position.sumPosition(0, 1)), board.getSquareModel(position))); 
                        }
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }

        return listMoves;
    }
    
}
