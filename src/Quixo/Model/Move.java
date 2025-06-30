package Quixo.Model;

/**
 * Represents a move in the Quixo game.
 * A move consists of selecting a cube at a specific position (row, col) 
 * and pushing it in a specific direction.
 */
public class Move {
    /**
     * The row index of the cube to be moved.
     */
    public int row;

    /**
     * The column index of the cube to be moved.
     */
    public int col;

    /**
     * The direction in which to push the cube.
     * Typically values are "UP", "DOWN", "LEFT", or "RIGHT".
     */
    public String direction;

    /**
     * Creates a new Move with the specified position and direction.
     *
     * @param row The row index of the cube to move
     * @param col The column index of the cube to move
     * @param direction The direction to push the cube ("UP", "DOWN", "LEFT", or "RIGHT")
     */
    public Move(int row, int col, String direction) {
        this.row = row;
        this.col = col;
        this.direction = direction;
    }
}
