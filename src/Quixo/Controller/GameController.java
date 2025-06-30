package Quixo.Controller;

import Quixo.Model.*;
import Quixo.View.GameResult.GameResultPresenter;
import Quixo.View.GameResult.GameResultView;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The GameController class manages the game flow, player moves, and game state.
 * It acts as a bridge between the game logic and the user interface.
 * This controller handles player moves, AI moves, win conditions, and game completion.
 */
public class GameController {
    /** The game logic that manages the rules and state of the game */
    private final GameLogic gameLogic;
    /** Controller for the game board UI and interactions */
    private final BoardController boardController;
    /** The primary stage of the application */
    private final Stage primaryStage;
    /** The scene containing the menu to return to after game completion */
    private final Scene menuScene;
    /** Flag indicating whether the game has been completed */
    private boolean gameCompleted = false;

    /** Counter for tracking the number of human player moves */
    private int humanMoveNumber = 1;

    /**
     * Constructor for the GameController.
     * Initializes the game controller with the provided game logic and UI components.
     * Also initializes the game in the database if player 1 is a human player.
     *
     * @param gameLogic The game logic that manages the rules and state of the game
     * @param primaryStage The primary stage of the application
     * @param menuScene The scene containing the menu to return to after game completion
     * @throws RuntimeException if game initialization fails
     */
    public GameController(GameLogic gameLogic, Stage primaryStage, Scene menuScene) {
        this.gameLogic = gameLogic;
        this.boardController = new BoardController(gameLogic.getBoard());
        this.primaryStage = primaryStage;
        this.menuScene = menuScene;

        Player player1 = gameLogic.getPlayer1();
        if (!(player1 instanceof ComputerPlayer)) {
            this.gameLogic.initializeGameInDatabase();
        }

        if (gameLogic.getGameId() <= 0) {
            throw new RuntimeException("Game initialization failed");
        }
    }

    /**
     * Executes a move in the game based on the provided row, column, and direction.
     * This method handles the game flow after a move is made, including:
     * - Recording move duration for human players
     * - Refreshing the UI
     * - Checking for win conditions
     * - Switching players
     * - Triggering AI moves when it's the computer's turn
     *
     * @param row The row index of the cube to move
     * @param col The column index of the cube to move
     * @param direction The direction to move the cube ("UP", "DOWN", "LEFT", "RIGHT")
     * @return true if the move was successfully made, false otherwise
     * @throws InvalidMoveException if the move is not valid according to game rules
     */
    public boolean makeMove(int row, int col, String direction) throws InvalidMoveException {
        MoveDuration.startMove();
        Player currentPlayerBeforeMove = gameLogic.getCurrentPlayer();

        boolean moveMade = gameLogic.makeMove(row, col, direction);

        //  Only record human moves with properly incremented move numbers
        if (moveMade && !(currentPlayerBeforeMove instanceof ComputerPlayer)) {
            MoveDuration.recordMove(
                    gameLogic.getGameId(), humanMoveNumber++);
        }

        boardController.refreshUI();

        if (gameLogic.checkWin()) {
            handleWin(currentPlayerBeforeMove);
            return true;
        }

        gameLogic.switchPlayer();

        if (gameLogic.getCurrentPlayer() instanceof ComputerPlayer) {
            makeAIMove();
        }

        return moveMade;
    }

    /**
     * Handles the computer player's move.
     * This method:
     * - Gets the next move from the AI algorithm
     * - Executes the move on the game board
     * - Refreshes the UI
     * - Checks for win conditions
     * - Switches to the next player if the game continues
     * - Handles any exceptions that occur during the AI move
     */
    private void makeAIMove() {
        ComputerPlayer ai = (ComputerPlayer) gameLogic.getCurrentPlayer();
        try {
            ComputerPlayer.Move aiMove = ai.getNextMove(gameLogic);
            if (aiMove == null) return;

            boolean moved = gameLogic.makeMove(aiMove.row, aiMove.col, aiMove.direction);
            boardController.refreshUI();

            if (gameLogic.checkWin()) {
                handleWin(ai);
                return;
            }

            gameLogic.switchPlayer();

        } catch (InvalidMoveException e) {
            System.out.println("[GameController] InvalidMoveException during AI move: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("[GameController] Unexpected error during AI move: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the game completion when a player wins.
     * This method:
     * - Marks the game as completed
     * - Records the game result in the database (only for human winners)
     * - Displays the game result screen
     * 
     * @param winner The player who won the game
     */
    private void handleWin(Player winner) {
        if (gameCompleted) return;

        gameCompleted = true;
        if (!(winner instanceof ComputerPlayer)) {
            gameLogic.recordGameResult(winner);
        } else {
            gameLogic.recordGameResult(null);
        }

        int gameId = gameLogic.getGameId();
        if (gameId > 0) {
            GameResultView resultView = new GameResultView();
            GameResultPresenter resultPresenter = new GameResultPresenter(
                    resultView, primaryStage, gameId, menuScene
            );
            primaryStage.setScene(resultPresenter.getScene());
        } else {
            System.err.println("Invalid Game ID, cannot show result screen.");
        }
    }


    /**
     * Gets the current player in the game.
     * @return The current player
     */
    public Player getCurrentPlayer() {
        return gameLogic.getCurrentPlayer();
    }

    /**
     * Checks if the current player has won the game.
     * @return true if the current player has won, false otherwise
     */
    public boolean checkWin() {
        return boardController.checkWin(gameLogic.getCurrentPlayer().getSymbol());
    }

    /**
     * Checks if the specified position is on the edge of the board.
     * Only cubes on the edge can be moved according to Quixo rules.
     * 
     * @param row The row index to check
     * @param col The column index to check
     * @return true if the position is on the edge, false otherwise
     */
    public boolean isEdge(int row, int col) {
        return gameLogic.isEdge(row, col);
    }

    /**
     * Gets the symbol of the cube at the specified position.
     * 
     * @param row The row index of the cube
     * @param col The column index of the cube
     * @return The symbol of the cube ('X', 'O', or ' ' for empty)
     */
    public char getCubeSymbol(int row, int col) {
        return boardController.getCube(row, col).getSymbol();
    }

    /**
     * Gets the current state of the game board as a 2D array of symbols.
     * 
     * @return A 2D array representing the board state
     */
    public char[][] getBoardState() {
        return boardController.getBoardState();
    }

    /**
     * Sets the symbol of the cube at the specified position.
     * 
     * @param row The row index of the cube
     * @param col The column index of the cube
     * @param sign The symbol to set ('X', 'O', or ' ')
     * @throws InvalidMoveException if the move is not valid
     */
    public void setCubeSymbol(int row, int col, char sign) throws InvalidMoveException {
        gameLogic.getBoard().getCube(row, col).setSymbol(sign);
    }

    /**
     * Gets the game logic instance.
     * 
     * @return The game logic
     */
    public GameLogic getGameLogic() {
        return gameLogic;
    }
}
