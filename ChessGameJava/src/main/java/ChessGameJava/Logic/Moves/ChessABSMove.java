package ChessGameJava.Logic.Moves;

import java.util.ArrayList;
import java.util.WeakHashMap;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Utility.UiChange;

/**
 * Abstract class that describes the functions every move should follow. Notably, the functions to execute a move and revert
 * a move from the board. In theory, the state of the board before the execution of the processExecuteMove method should be the same
 * after we call processRevertMove to avoid any unintended side effects when verifying possible moves. 
 * 
 * This class is NOT in charge of verifying if a move is valid, this check is left to the implementation of the pieces. 
 * Child classes should only provide a way to execute or revert a move. 
 * 
 * This class also keeps track internally for every game and what move count we are and uses this to determine if some moves have 
 * not been reverted or executed before the current move. The idea being that if we create a move on a board in state A and then 
 * create a second move on that same state but execute it. The board is not in state B and the initially created move may no longer 
 * be valid. 
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-08-31
 */
public abstract class ChessABSMove {
    protected static WeakHashMap<ChessBoardModel, Integer> boardIdMap = new WeakHashMap<>();
    protected Integer mouveCount = null;
    protected ChessSquareModel firstSquare;
    protected ChessSquareModel secondSquare;
    protected boolean canModifyPieceHasMoved;

    /**
     * Getter for the first square, the square where the piece is currently at. 
     * 
     * @return the ChessSquareModel where the piece is currently at. 
     */
    public ChessSquareModel getFirstSquare() { return this.firstSquare; }

    /**
     * Getter for the second square, the square where the piece wants to go.
     * 
     * @return the ChessSquareModel where the piece wants to go. 
     */
    public ChessSquareModel getSecondSquare() { return this.secondSquare; }

    /**
     * Returns the move count for a given board instance. In other words, it returns how many moves have been played 
     * on a game using the provided board. 
     * 
     * @param board the ChessBoardModel we want to get many moves have been played on.
     * @return The number of moves that have been played on that board.
     */
    public static Integer getMoveCountForBoard(ChessBoardModel board) { return ChessABSMove.boardIdMap.get(board); }

    /**
     * This method in in charge of making the necessary calls to process a given request to execute the move object. 
     * it is recommend for the method to call the executeMove function and only contain code that serves to trigger
     * behavior upon the request call delegating all actual move logic to the executeMove method. 
     * 
     * The basic implementation increments a counter to keep track of how many moves where executed. It is recommended
     * to keep this behavior in overriding implementation to help with developer experience. This is useful to keep track
     * of execution call when building composite moves (moves made from two ChessABSMove instances) and helping revert
     * or execute moves in the correct order.
     * 
     * If we are trying to execute the move but an other one was not previously executed, it throws a RuntimeException. 
     * 
     * @param board the ChessBoardModel we want to execute the move on.
     * @return An ArrayList of UiChanges meant to describe the visual changes that should be made to the UI.
     */
    public ArrayList<UiChange> processExecuteMove(ChessBoardModel board) {
        if (!ChessABSMove.boardIdMap.containsKey(board)) {
            ChessABSMove.boardIdMap.put(board, 0);
        }

        if(this.mouveCount != null) {
            if(this.mouveCount != ChessABSMove.boardIdMap.get(board)) {
                throw new RuntimeException("Cannot execute move, there is a move that was either not executed or not reverted before");
            }
        } else {
            this.mouveCount = ChessABSMove.boardIdMap.get(board);
        }

        this.canModifyPieceHasMoved = false;
        if (!this.firstSquare.getPiece().hasMoved) {
            canModifyPieceHasMoved = true;
        }
        
        ArrayList<UiChange> listChanges = this.executeMove(board);
        ChessABSMove.boardIdMap.put(board, ChessABSMove.boardIdMap.get(board) + 1);
        if(this.canModifyPieceHasMoved) {
            this.firstSquare.getPiece().hasMoved = true;
        }
        return listChanges;
    };

    /**
     * This method must be overridden in child classes. It should execute a move on the provided ChessBoardModel and return
     * an ArrayList<UiChange> that contains all changes necessary to reflect the move on the front end side of the application. 
     * 
     * @param board the ChessBoardModel we want to execute the move on.
     * @return An ArrayList of UiChanges meant to describe the visual changes that should be made to the UI.
     */
    abstract protected ArrayList<UiChange> executeMove(ChessBoardModel board);

    /**
     * This method in in charge of making the necessary calls to process a given request to revert the move object. 
     * It is recommend for the method to call the revertMove function and only contain code that serves to trigger
     * behavior upon the request call delegating all actual move logic to the executeMove method. 
     * 
     * The basic implementation decrements a counter to keep track of how many moves where executed. It is recommended
     * to keep this behavior in overriding implementation to help with developer experience. This is useful to keep track
     * of execution call when building composite moves (moves made from two CHessABSMove instances) and help make sure
     * the moves get reverted in the correct order.
     * 
     * If we are trying to revert the move but an other one was not previously reverted, it throws a RuntimeException. 
     * Similarly, if the move was never executed, it also throws a RuntimeException.
     * 
     * @param board the ChessBoardModel we want to revert the move on.
     * @return An ArrayList of UiChanges meant to describe the visual changes that should be made to the UI.
     */
    public ArrayList<UiChange> processRevertMove(ChessBoardModel board) {
        if (!ChessABSMove.boardIdMap.containsKey(board)) {
            throw new RuntimeException("This move was never executed, cannot revert it");
        }
        if(this.mouveCount != ChessABSMove.boardIdMap.get(board) -1) {
            throw new RuntimeException("Cannot execute move, there is a move that was either not executed or reverted before");
        }

        ArrayList<UiChange> listChanges = this.revertMove(board);
        ChessABSMove.boardIdMap.put(board, ChessABSMove.boardIdMap.get(board) - 1);
        if(this.canModifyPieceHasMoved) {
            this.firstSquare.getPiece().hasMoved = false;
        }
        return listChanges;
    };

    /**
     * This method must be overridden in child classes. It should revert a move that was made during the execution
     * of the executeMove method and return an ArrayList<UiChange> that contains all changes necessary to reflect 
     * the revert request on the front end side of the application. 
     *  
     * @param board the ChessBoardModel we want to execute the move on.
     * @return An ArrayList of UiChanges meant to describe the visual changes that should be made to the UI.
     */
    abstract protected ArrayList<UiChange> revertMove(ChessBoardModel board);
}

