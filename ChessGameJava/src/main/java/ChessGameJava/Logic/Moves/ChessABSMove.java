package ChessGameJava.Logic.Moves;

import java.util.ArrayList;
import java.util.WeakHashMap;

import ChessGameJava.Logic.ChessBoardModel;
import ChessGameJava.Logic.ChessSquareModel;
import ChessGameJava.Utility.UiChange;

/**
 * Abstract class that describes the functions every move should follow. Notably, the functions to execute a move and revert
 * a move from the board. In theory, the state of the board before the execution of the executeMove method should be the same
 * after we call revertMove
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
     * This method in in charge of making the necessary calls to process a given request to execute the move object. 
     * it is recommend for the method to call the executeMove function and only contain code that serve to trigger
     * behavior upon the request call. 
     * 
     * The basic implementation increments a counter to keep track of how many moves where executed. It is recommended
     * to keep this behavior in overriding implementation to help with developer experience. This is useful to keep track
     * of execution call when building composite moves (moves made from two CHessABSMove instances).
     * 
     * @param board the ChessBoardModel we want to execute the move on.
     * @return An ArrayList of UiChanges meant to describe the visual changes that should be made to the UI.
     */
    public ArrayList<UiChange> processExecuteMove(ChessBoardModel board) {
        System.out.println("Called execute move");

        if (!ChessABSMove.boardIdMap.containsKey(board)) {
            ChessABSMove.boardIdMap.put(board, 0);
        }

        if(this.mouveCount != null) {
            if(this.mouveCount != ChessABSMove.boardIdMap.get(board)) {
                System.out.println(this.mouveCount);
                System.out.println(ChessABSMove.boardIdMap.get(board));
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
        this.firstSquare.getPiece().hasMoved = true;
        return listChanges;
    };

    /**
     * This method must be overriden in child classes. It should execute a move on the provided ChessBoardModel.
     * @param board the ChessBoardModel we want to execute the move on.
     * @return An ArrayList of UiChanges meant to describe the visual changes that should be made to the UI.
     */
    abstract protected ArrayList<UiChange> executeMove(ChessBoardModel board);

    /**
     * This method in in charge of making the necessary calls to process a given request to revert the move object. 
     * It is recommend for the method to call the revertMove function and only contain code that serve to trigger
     * behavior upon the request call. 
     * 
     * The basic implementation decrements a counter to keep track of how many moves where executed. It is recommended
     * to keep this behavior in overriding implementation to help with developer experience. This is useful to keep track
     * of execution call when building composite moves (moves made from two CHessABSMove instances) and help make sure
     * the moves get reverted in the correct order.
     * 
     * @param board the ChessBoardModel we want to revert the move on.
     * @return An ArrayList of UiChanges meant to describe the visual changes that should be made to the UI.
     */
    public ArrayList<UiChange> processRevertMove(ChessBoardModel board) {
        System.out.println("Called revert move");

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
     * This method must be overriden in child classes. It should revert a move that was made during the execution
     * of the executeMove method. If the move was not executed, it should do nothing. 
     * @param board the ChessBoardModel we want to execute the move on.
     * @return An ArrayList of UiChanges meant to describe the visual changes that should be made to the UI.
     */
    abstract protected ArrayList<UiChange> revertMove(ChessBoardModel board);

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

    /**
     * Returns the move count for a given board instance. In order words, it returns how many moves have been played 
     * on that game that uses the provided board. 
     * @param board the ChessBoardModel we want to egt many moves have been played on.
     * @return The number of moves that have been played. 
     */
    public static Integer getMoveCountForBoard(ChessBoardModel board) { return ChessABSMove.boardIdMap.get(board); }
}

