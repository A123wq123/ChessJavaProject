package ChessGameJava.Utility;

import ChessGameJava.Logic.ChessBoardModel;


/**
 * A class that contains two coordinates to work with inside a 2 dimension matrix.
 * Provides a variate of functions to execute on it's two coordinates such as obtaining the sum
 * of two coordinates.
 * 
 * @Author Charles Degrandpré
 * @Last_Updated 2023-09-03
 */
public class Position {
    private final int coordX;
    private final int coordY;

    /**
     * @return position in x of coordinate.
     */
    public int getCoordX() { return coordX; }

    /**
     * @return position in y of coordinate.
     */
    public int getCoordY() { return coordY; }

    /**
     * Constructor of a coordinate element. It's x and y values are the same as if
     * you would place this coordinate on a cartesian plan. If the coordinate is not valid
     * the constructor throws a RuntimeException.
     * 
     * @param x the x coordinate of the position.
     * @param y the y coordinate of the position.
     */
    public Position(int x, int y) {
        coordX = x;
        coordY = y;
        if (!this.isValid()) {
            throw new RuntimeException("Incorrect parameters");
        }
    }

    /**
     * Function that verifies that the Position instance does not fall outside
     * the limits of the chess board.
     * 
     * @return boolean indicating if the coordinate is valid.
     */
    private boolean isValid() {
        return (((this.coordX < ChessBoardModel.numberOfRows ) && (this.coordX >= 0))
                && ((this.coordY < ChessBoardModel.numberOfRows ) && (this.coordY >= 0)));
    }

    /**
     * Sums the instance of Position with two given coordinates in parameter and creates 
     * a new Position instance with the result to be returned. 
     * 
     * @param x integer representing the x value to sum with.
     * @param y integer value representing the y value to sum with.
     * @return new position element representing the sum of coordinates.
     */
    public Position sumPosition(int x, int y) {
        return new Position(this.coordX + x, this.coordY + y);
    }

    /**
     * This method serves to verify equality between two Positions. It is not a deep equality
     * as it simply compares the contained values for the x and y coordinates and if they match 
     * it returns true. 
     * 
     * @param secondPosition The Position to compare equality to. 
     * @return A boolean indicating if both are equal. 
     */
    public boolean equals(Position secondPosition) {
        return this.coordX == secondPosition.getCoordX() && this.coordY == secondPosition.getCoordY();
    }

    /**
     * This method serves to calculate the distance between the current position 
     * to a provided Position. It returns the distance between both as a double. 
     * 
     * @param position The Position to check the distance to. 
     * @return The distance between both as a double. 
     */
    public double distanceTo(Position position) {
        int x = Math.abs(this.coordX - position.getCoordX());
        int y = Math.abs(this.coordY - position.getCoordY());

        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
