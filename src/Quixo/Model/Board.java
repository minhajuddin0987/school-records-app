package Quixo.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board for Quixo.
 * The board is a 5x5 grid of cubes that players can manipulate.
 */
public class Board {
    /** 2D array representing the game board grid */
    private Cube[][] cubes;
    /** The size of the board (5x5) */
    private static final int BOARD_SIZE = 5;

    /**
     * Constructor that initializes the board with empty cubes.
     * Creates a 5x5 grid of Cube objects.
     */
    public Board() {
        cubes = new Cube[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                cubes[i][j] = new Cube();
            }
        }
    }

    /**
     * Gets the cube at the specified position.
     * 
     * @param row The row index (0-4)
     * @param col The column index (0-4)
     * @return The Cube at the specified position
     */
    public Cube getCube(int row, int col) {
        return cubes[row][col];
    }

    /**
     * Pushes a cube in the specified direction and places the player's symbol.
     * This is a core game mechanic where cubes are shifted in a row or column.
     * 
     * @param row The row index of the cube to push
     * @param col The column index of the cube to push
     * @param direction The direction to push ("up", "down", "left", or "right")
     * @param symbol The player's symbol to place on the inserted cube
     * @return true if the push was successful, false otherwise
     */
    public boolean pushCube(int row, int col, String direction, char symbol) {
        // Push cube upward - shift all cubes in the column down from the selected position
        if (direction.equalsIgnoreCase("up")) {
            for (int i = row; i > 0; i--) {
                cubes[i][col].setSymbol(cubes[i - 1][col].getSymbol());
            }
            cubes[0][col].setSymbol(symbol);
            return true;
        }

        // Push cube downward - shift all cubes in the column up from the selected position
        if (direction.equalsIgnoreCase("down")) {
            for (int i = row; i < 4; i++) {
                cubes[i][col].setSymbol(cubes[i + 1][col].getSymbol());
            }
            cubes[4][col].setSymbol(symbol);
            return true;
        }

        // Push cube leftward - shift all cubes in the row right from the selected position
        if (direction.equalsIgnoreCase("left")) {
            for (int j = col; j > 0; j--) {
                cubes[row][j].setSymbol(cubes[row][j - 1].getSymbol());
            }
            cubes[row][0].setSymbol(symbol);
            return true;
        }

        // Push cube rightward - shift all cubes in the row left from the selected position
        if (direction.equalsIgnoreCase("right")) {
            for (int j = col; j < 4; j++) {
                cubes[row][j].setSymbol(cubes[row][j + 1].getSymbol());
            }
            cubes[row][4].setSymbol(symbol);
            return true;
        }

        return false;
    }

    /**
     * Checks if a player has won the game.
     * A player wins by having a complete row or column of their symbol.
     * 
     * @param playerSymbol The symbol of the player to check for a win
     * @return true if the player has won, false otherwise
     */
    public boolean checkWin(char playerSymbol) {
        // Check rows - a player wins if they have all 5 cubes in any row
        for (int i = 0; i < 5; i++) {
            boolean rowWin = true;
            for (int j = 0; j < 5; j++) {
                if (cubes[i][j].getSymbol() != playerSymbol) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) return true;
        }

        // Check columns - a player wins if they have all 5 cubes in any column
        for (int j = 0; j < 5; j++) {
            boolean colWin = true;
            for (int i = 0; i < 5; i++) {
                if (cubes[i][j].getSymbol() != playerSymbol) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) return true;
        }

        return false;
    }

    /**
     * Gets the valid directions in which a cube at the specified position can be moved.
     * In Quixo, only edge cubes can be moved, and the direction depends on the position.
     * 
     * @param row The row index of the cube
     * @param col The column index of the cube
     * @return A list of valid directions for the cube at the specified position
     */
    public List<String> getValidDirections(int row, int col) {
        List<String> directions = new ArrayList<>();

        // Based on Quixo logic: only edge cubes can be moved
        if (row == 0) directions.add("DOWN");  // Top edge can be pushed down
        if (row == 4) directions.add("UP");    // Bottom edge can be pushed up
        if (col == 0) directions.add("RIGHT"); // Left edge can be pushed right
        if (col == 4) directions.add("LEFT");  // Right edge can be pushed left

        return directions;
    }

    /**
     * Copies the state of another board to this board.
     * This is useful for creating a duplicate board for AI calculations or game state saving.
     * 
     * @param other The board to copy from
     */
    public void copyFrom(Board other) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.cubes[i][j].setSymbol(other.cubes[i][j].getSymbol());
            }
        }
    }

}
