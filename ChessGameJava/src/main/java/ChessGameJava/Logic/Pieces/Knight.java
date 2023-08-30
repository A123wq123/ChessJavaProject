package ChessGameJava.Logic.Pieces;

import java.util.ArrayList;
import java.util.Arrays;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Logic.Colour;
import ChessGameJava.Utility.Position;

/**
 * The class representing a knight within the project. This class is capable of returning all
 * possible moves that the knight can do.
 * @Author Charles Degrandpré
 * @Last_Updated 2023-08-30
 */
public class Knight extends ChessABSPieceModel {

    /**
     * Constructor of the Knight class. Assigns the board as well as the colour of the piece.
     * @param board the instance of the chess board.
     * @param colourOfPiece the colour of the knight.
     */
    public Knight(Colour colourOfPiece) {
        super();
        this.colour = colourOfPiece;
    }

    @Override
    public ArrayList<ChessSquareModel> getListAttackingSquares(ChessSquareModel currentSquare, ChessBoardModel board) {
        ArrayList<ChessSquareModel> listSquares = new ArrayList<>();
        Position currentPosition = currentSquare.getPosition();
        
        for (int x : Arrays.asList(-2, 2)) { 
            for (int y : Arrays.asList(-1, 1)) { 
                try {
                    Position position1 = currentPosition.sumPosition(x,y);
                    listSquares.add(board.getSquareModel(position1));
                } catch (Exception e) {
                }
                try {
                    System.out.println("Got in second try");
                    Position position2 = currentPosition.sumPosition(y,x);
                    listSquares.add(board.getSquareModel(position2));
                } catch (Exception e) {
                }
            }    
        }

        for (ChessSquareModel chessSquareModel : listSquares) {
            System.out.println("Valid square");
            System.out.println(String.format("x: %s, y: %s", chessSquareModel.getPosition().getCoordX(), chessSquareModel.getPosition().getCoordY()));
        }

        return listSquares;
    }
    
}
