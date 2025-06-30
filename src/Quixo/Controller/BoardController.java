package Quixo.Controller;

import Quixo.Model.Board;
import Quixo.Model.Cube;

/**
 * Controller class for the Quixo game board.
 * Handles interactions between the UI and the board model.
 */
public class BoardController {
    private final Board board;

    /**
     * Constructor for the BoardController.
     * @param board The board model to control
     */
    public BoardController(Board board) {
        this.board = board;
    }

    /**
     * Gets a cube at the specified position on the board.
     * @param row The row index (0-4)
     * @param col The column index (0-4)
     * @return The cube at the specified position
     */
    public Cube getCube(int row, int col) {
        return board.getCube(row, col);
    }

    /**
     * Checks if a player has won the game.
     * @param playerSymbol The symbol of the player to check for ('X' or 'O')
     * @return true if the player has won, false otherwise
     */
    public boolean checkWin(char playerSymbol) {
        return board.checkWin(playerSymbol);
    }

    /**
     * Gets the current state of the board as a 2D array of characters.
     * @return A 5x5 array representing the board state with player symbols
     */
    public char[][] getBoardState() {
        char[][] state = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                state[i][j] = board.getCube(i, j).getSymbol();
            }
        }
        return state;
    }

    /**
     * Refreshes the UI to reflect the current state of the board.
     * This method is currently empty and can be implemented as needed.
     */
    public void refreshUI() {
    }
}
