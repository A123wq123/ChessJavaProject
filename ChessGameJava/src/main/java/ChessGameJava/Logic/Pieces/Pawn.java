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

public class Pawn extends ChessABSPieceModel{

    public static Integer turnOfLastPlayedPawn = null;
    public Integer turnOfDoubleMove = null;
    public boolean canMoveTwice = true;

    /**
     * Constructor of the Pawn class. Assigns the board as well as the colour of the piece.
     * @param board the instance of the chess board.
     * @param colourOfPiece the colour of the Pawn.
     */
    public Pawn(Colour colourOfPiece) {
        super();
        this.colour = colourOfPiece;
    }

    @Override
    public ArrayList<ChessABSMove> getListMoves(ChessSquareModel currentSquare, ChessBoardModel board) {
        // Might want to refactor this line: 
        Colour opponentColour = this.colour == Colour.BLACK ? Colour.WHITE : Colour.BLACK;

        ArrayList<ChessABSMove> listMoves = new ArrayList<>();

        for(ChessSquareModel square : this.getListAttackingSquares(currentSquare, board)) {
            if(square.getPiece().getColour() == opponentColour) {
                listMoves.add(new BasicMovePawn(currentSquare, square)); // Refactor this to new special move
            }
        }

        listMoves.addAll(this.getMovesInFront(currentSquare, board));


        listMoves.addAll(this.getMovesEnPassant(currentSquare, board));


        return listMoves;
    }

    @Override
    public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board) {
        return this.colour == Colour.WHITE ? this.getListAttackingSquaresWhite(currentSquare, board) : this.getListAttackingSquaresBlack(currentSquare, board);
    }

    /**
     * Helper function to calculate the two squares a pawn can attack if he is White.
     * @param currentSquare The square the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an array of the possible moves the pawn can do.
     */
    private ArrayList<ChessSquareModel> getListAttackingSquaresWhite(ChessSquareModel currentSquare, ChessBoardModel board) {
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
     * @param currentSquare The square the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an array of the possible moves the pawn can do.
     */
    private ArrayList<ChessSquareModel> getListAttackingSquaresBlack(ChessSquareModel currentSquare, ChessBoardModel board) {
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
     * Helper function to calculate the squares a pawn can move to in front of him. 
     * @param currentSquare The square the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an array of the possible moves the pawn can do.
     */
    private ArrayList<ChessABSMove> getMovesInFront(ChessSquareModel currentSquare, ChessBoardModel board) {
        return this.colour == Colour.WHITE ? this.getMovesInFrontWhite(currentSquare, board) : this.getMovesInFrontBlack(currentSquare, board);
    }

    /**
     * Helper function to calculate the squares a pawn can move to in front of him if he is white.
     * @param currentSquare The square the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an array of the possible moves the pawn can do.
     */
    private ArrayList<ChessABSMove> getMovesInFrontWhite(ChessSquareModel currentSquare, ChessBoardModel board) {
        if (this.colour != Colour.WHITE) {
            throw new RuntimeException("Cannot call method, the piece is not of colour White");
        }

        ArrayList<ChessABSMove> listMoves = new ArrayList<>();

        try {
            Position position1 = currentSquare.getPosition().sumPosition(0, -1);
            if(board.getSquareModel(position1).getPiece().getColour() == Colour.NULL) {
                listMoves.add(new BasicMovePawn(currentSquare, board.getSquareModel(position1)));
                if(this.canMoveTwice) {
                    Position position2 = currentSquare.getPosition().sumPosition(0, -2);
                    if(board.getSquareModel(position2).getPiece().getColour() == Colour.NULL) {
                        listMoves.add(new BasicMovePawn(currentSquare, board.getSquareModel(position2)));
                    }
                }
            }
        } catch (Exception e) {
            // Skip the moved it is outside the board.
        }

        return listMoves;
    }

    /**
     * Helper function to calculate the squares a pawn can move to in front of him if he is black.
     * @param currentSquare The square the pawn is currently on.
     * @param board The ChessBoardModel instance the game is played on. 
     * @return an array of the possible moves the pawn can do.
     */
    private ArrayList<ChessABSMove> getMovesInFrontBlack(ChessSquareModel currentSquare, ChessBoardModel board) {
        if (this.colour != Colour.BLACK) {
            throw new RuntimeException("Cannot call method, the piece is not of colour Black");
        }

        ArrayList<ChessABSMove> listMoves = new ArrayList<>();

        try {
            Position position1 = currentSquare.getPosition().sumPosition(0, 1);
            if(board.getSquareModel(position1).getPiece().getColour() == Colour.NULL) {
                listMoves.add(new BasicMovePawn(currentSquare, board.getSquareModel(position1)));
                if(this.canMoveTwice) {
                    Position position2 = currentSquare.getPosition().sumPosition(0, 2);
                    if(board.getSquareModel(position2).getPiece().getColour() == Colour.NULL) {
                        listMoves.add(new BasicMovePawn(currentSquare, board.getSquareModel(position2))); 
                    }
                }
            }
        } catch (Exception e) {
            // Skip the moved it is outside the board. 
        }

        return listMoves;
    }

    private ArrayList<ChessABSMove> getMovesEnPassant(ChessSquareModel currentSquare, ChessBoardModel board) {
        if(Pawn.turnOfLastPlayedPawn == null) {
            return new ArrayList<>();
        }

        return this.colour == Colour.WHITE ? this.getMovesEnPassantWhite(currentSquare, board) : this.getMovesEnPassantBlack(currentSquare, board);
    }

    private ArrayList<ChessABSMove> getMovesEnPassantWhite(ChessSquareModel currentSquare, ChessBoardModel board) {
        if (this.colour != Colour.WHITE) {
            throw new RuntimeException("Cannot call method, the piece is not of colour White");
        }

        ArrayList<ChessABSMove> listMoves = new ArrayList<>();
        
        for (int x : Arrays.asList(-1, 1)) {
            try {
                Position position = currentSquare.getPosition().sumPosition(x, 0);
                if(board.getSquareModel(position).getPiece() instanceof Pawn) {
                    Pawn pawn = (Pawn) board.getSquareModel(position).getPiece();
                    if(pawn.turnOfDoubleMove == ChessABSMove.getMoveCountForBoard(board)) {
                        if(board.getSquareModel(position.sumPosition(0, -1)).getPiece().getColour() == Colour.NULL) {
                            listMoves.add(new EnPassant(currentSquare, board.getSquareModel(position.sumPosition(0, -1)), board.getSquareModel(position))); // TODO change for en passant move
                        }
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }

        return listMoves;
    }

    private ArrayList<ChessABSMove> getMovesEnPassantBlack(ChessSquareModel currentSquare, ChessBoardModel board) {
        if (this.colour != Colour.BLACK) {
            throw new RuntimeException("Cannot call method, the piece is not of colour Black");
        }

        ArrayList<ChessABSMove> listMoves = new ArrayList<>();
        
        for (int x : Arrays.asList(-1, 1)) {
            try {
                Position position = currentSquare.getPosition().sumPosition(x, 0);
                if(board.getSquareModel(position).getPiece() instanceof Pawn) {
                    Pawn pawn = (Pawn) board.getSquareModel(position).getPiece();
                    if(pawn.turnOfDoubleMove == ChessABSMove.getMoveCountForBoard(board)) {
                        if(board.getSquareModel(position.sumPosition(0, 1)).getPiece().getColour() == Colour.NULL) {
                            listMoves.add(new EnPassant(currentSquare, board.getSquareModel(position.sumPosition(0, 1)), board.getSquareModel(position))); // TODO change for en passant move
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
