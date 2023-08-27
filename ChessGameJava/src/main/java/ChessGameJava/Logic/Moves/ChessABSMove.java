package ChessGameJava.Logic.Moves;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;

/**
 * Abstract class that describes the functions every move should follow. Notably, the functions to execute a move and revert
 * a move from the board. In theory, the state of the board before the execution of the executeMove method should be the same
 * after we call revertMove
 * @Author Charles Degrandpré
 * @Last_Updated 2022-12-23
 */
public abstract class ChessABSMove {
    protected ChessSquareModel firstSquare;
    protected ChessSquareModel secondSquare;
    protected boolean moveWasExecuted;

    /**
     * This method must be overriden in child classes. It should execute a move on the provided ChessBoardModel.
     * @param board the ChessBoardModel we want to execute the move on
     */
    abstract public void executeMove(ChessBoardModel board);

    /**
     * This method must be overriden in child classes. It should revert a move that was made during the execution
     * of the executeMove method. If the move was not executed, it should do nothing. 
     * @param board the ChessBoardModel we want to revert the move on. 
     */
    abstract public void revertMove(ChessBoardModel board);

    /**
     * Getter for the first square, the square where the piece is currently at.
     * @return the ChessSquareModel where the piece is currently at. 
     */
    public ChessSquareModel getFirstSquare() { return this.firstSquare; }

    /**
     * Getter for the second square, the square where the piece wants to go.
     * @return the ChessSquareModel where the piece wants to go. 
     */
    public ChessSquareModel getSecondSquare() { return this.secondSquare; }
}

