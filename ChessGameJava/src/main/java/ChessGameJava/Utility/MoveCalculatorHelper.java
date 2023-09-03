package ChessGameJava.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;

/**
 * A class that acts as a helper to calculate various basic lines and moves on a board. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-03
 */
public abstract class MoveCalculatorHelper {
    public final static List<DIRECTION> lines = Arrays.asList(DIRECTION.NORTH, DIRECTION.EAST, DIRECTION.SOUTH, DIRECTION.WEST);
    public final static List<DIRECTION> diagonals = Arrays.asList(DIRECTION.NORTH_EAST, DIRECTION.SOUTH_EAST, DIRECTION.SOUTH_WEST, DIRECTION.NORTH_WEST);
    public static enum DIRECTION {
        NORTH,
        NORTH_EAST,
        EAST,
        SOUTH_EAST,
        SOUTH,
        SOUTH_WEST,
        WEST,
        NORTH_WEST
    }

    /**
     * Helper function to calculate all the squares on a line from the board that can be considered as a valid 
     * square to move the piece to. It checks if the path to the square is empty and if we are trying to attack 
     * a piece of the correct colour. 
     * 
     * As a note, it considers all squares on a line of one of the 4 cardinal directions up to and including 
     * the first occupied square. 
     * 
     * @param direction The DIRECTION we want to calculate for. 
     * @param currentPos The Position we want to calculate from.
     * @param board The current ChessBoardModel we want to calculate on.
     * @return And ArrayList<ChessSquareModel> containing all the valid squares. 
     */
    public static ArrayList<ChessSquareModel> calculateLine(DIRECTION direction, Position currentPos, ChessBoardModel board) {
        if(!lines.contains(direction)) {
            throw new RuntimeException("Incorrect parameters. Direction specified isn't part of lines.");
        }

        ArrayList<ChessSquareModel> listSquares = new ArrayList<>();

        switch (direction) {
            case NORTH:
                for(int y = 1; y < ChessBoardModel.numberOfRows; y++) {
                    try {
                        Position dest = new Position(currentPos.getCoordX(), currentPos.getCoordY() + y);
                        ChessSquareModel destSquare = board.getSquareModel(dest);
                        listSquares.add(destSquare);
                        if(destSquare.getPiece().getColour() != Colour.NULL) {
                            break;
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
                break;
            case EAST:
                for(int x = 1; x < ChessBoardModel.numberOfRows; x++) {
                    try {
                        Position dest = new Position(currentPos.getCoordX() + x, currentPos.getCoordY());
                        ChessSquareModel destSquare = board.getSquareModel(dest);
                        listSquares.add(destSquare);
                        if(destSquare.getPiece().getColour() != Colour.NULL) {
                            break;
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
                break;
            case SOUTH:
                for(int y = 1; y < ChessBoardModel.numberOfRows; y++) {
                    try {
                        Position dest = new Position(currentPos.getCoordX(), currentPos.getCoordY() - y);
                        ChessSquareModel destSquare = board.getSquareModel(dest);
                        listSquares.add(destSquare);
                        if(destSquare.getPiece().getColour() != Colour.NULL) {
                            break;
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
                break;
            case WEST:
                for(int x = 1; x < ChessBoardModel.numberOfRows; x++) {
                    try {
                        Position dest = new Position(currentPos.getCoordX() - x, currentPos.getCoordY());
                        ChessSquareModel destSquare = board.getSquareModel(dest);
                        listSquares.add(destSquare);
                        if(destSquare.getPiece().getColour() != Colour.NULL) {
                            break;
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
                break;
            default:
                break;
        }

        return listSquares;
    }

    /**
     * Helper function to calculate all the squares on a diagonal from the board that can be considered as a valid 
     * square to move the piece to. It checks if the path to the square is empty and if we are trying to attack 
     * a piece of the correct colour. 
     * 
     * As a note, we only consider diagonals to be the line of squares forming a 45 degree angle with either 
     * two cardinal lines it is closest to. 
     * 
     * @param direction The DIRECTION we want to calculate for. 
     * @param currentPos The Position we want to calculate from.
     * @param board The current ChessBoardModel we want to calculate on.
     * @return And ArrayList<ChessSquareModel> containing all the valid squares. 
     */
    public static ArrayList<ChessSquareModel> calculateDiagonal(DIRECTION direction, Position currentPos, ChessBoardModel board) {
        if(!diagonals.contains(direction)) {
            throw new RuntimeException("Incorrect parameters. Direction specified isn't part of diagonals.");
        }

        ArrayList<ChessSquareModel> listSquares = new ArrayList<>();

        switch (direction) {
            case NORTH_EAST:
                for(int index = 1; index < ChessBoardModel.numberOfRows; index++) {
                    try {
                        Position dest = new Position(currentPos.getCoordX() + index, currentPos.getCoordY() + index);
                        ChessSquareModel destSquare = board.getSquareModel(dest);
                        listSquares.add(destSquare);
                        if(destSquare.getPiece().getColour() != Colour.NULL) {
                            break;
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
                break;
            case SOUTH_EAST:
                for(int index = 1; index < ChessBoardModel.numberOfRows; index++) {
                    try {
                        Position dest = new Position(currentPos.getCoordX() + index, currentPos.getCoordY() - index);
                        ChessSquareModel destSquare = board.getSquareModel(dest);
                        listSquares.add(destSquare);
                        if(destSquare.getPiece().getColour() != Colour.NULL) {
                            break;
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
                break;
            case SOUTH_WEST:
                for(int index = 1; index < ChessBoardModel.numberOfRows; index++) {
                    try {
                        Position dest = new Position(currentPos.getCoordX() - index, currentPos.getCoordY() - index);
                        ChessSquareModel destSquare = board.getSquareModel(dest);
                        listSquares.add(destSquare);
                        if(destSquare.getPiece().getColour() != Colour.NULL) {
                            break;
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
                break;
            case NORTH_WEST:
                for(int index = 1; index < ChessBoardModel.numberOfRows; index++) {
                    try {
                        Position dest = new Position(currentPos.getCoordX() - index, currentPos.getCoordY() + index);
                        ChessSquareModel destSquare = board.getSquareModel(dest);
                        listSquares.add(destSquare);
                        if(destSquare.getPiece().getColour() != Colour.NULL) {
                            break;
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
                break;
            default:
                break;
        }

        return listSquares;
    }
}
