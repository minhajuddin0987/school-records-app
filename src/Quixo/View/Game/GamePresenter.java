package Quixo.View.Game;

import Quixo.Controller.GameController;
import Quixo.Model.*;
import Quixo.View.GameResult.GameResultPresenter;
import Quixo.View.GameResult.GameResultView;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * GamePresenter class handles the game logic and user interactions for the Quixo game.
 * It acts as a mediator between the game view and the game controller.
 * This class manages player turns, validates moves, updates the game board,
 * and handles AI player moves.
 */
public class GamePresenter {
    /** The controller that manages game logic and state */
    private final GameController gameController;
    /** The view that displays the game board and UI elements */
    private final GameScreenView gameScreen;
    /** The primary stage of the application */
    private final Stage primaryStage;
    /** The scene containing the menu screen to return to */
    private final Scene menuScene;

    /**
     * Constructor for the GamePresenter.
     * 
     * @param gameController The controller that manages game logic
     * @param gameScreen The view that displays the game board
     * @param primaryStage The primary stage of the application
     * @param menuScene The scene containing the menu screen
     */
    public GamePresenter(GameController gameController, GameScreenView gameScreen, Stage primaryStage, Scene menuScene) {
        this.gameController = gameController;
        this.gameScreen = gameScreen;
        this.primaryStage = primaryStage;
        this.menuScene = menuScene;
        updateView(); // Initialize the view with current game state
    }

    /**
     * Handles the event when a player clicks on a cube on the game board.
     * Validates if the move is legal according to game rules and shows direction dialog if valid.
     * 
     * @param row The row index of the clicked cube
     * @param col The column index of the clicked cube
     */
    public void onCubeClicked(int row, int col) {
        try {
            // Check if it's the human player's turn
            if (!isHumanPlayerTurn()) {
                showErrorAlert("Please wait for your turn!");
                return;
            }

            // Get information about the selected cube
            boolean isEdge = gameController.isEdge(row, col);
            char cubeSymbol = gameController.getCubeSymbol(row, col);
            char currentPlayerSymbol = gameController.getCurrentPlayer().getSymbol();

            // Validate that the cube is on the edge (game rule)
            if (!isEdge) {
                throw new InvalidMoveException("You must pick a cube from the edge!");
            }

            // Validate that the cube is either empty or belongs to the current player
            if (cubeSymbol != ' ' && cubeSymbol != currentPlayerSymbol) {
                throw new InvalidMoveException("You can't pick your opponent's cube!");
            }

            // Show direction selection dialog for the valid move
            gameScreen.showDirectionDialog(row, col);
        } catch (InvalidMoveException e) {
            showErrorAlert(e.getMessage());
        }
    }

    /**
     * Handles the event when a player chooses a direction to move a cube.
     * Executes the move, checks for win condition, and triggers AI move if it's computer's turn.
     * 
     * @param row The row index of the selected cube
     * @param col The column index of the selected cube
     * @param direction The direction to move the cube ("UP", "DOWN", "LEFT", "RIGHT")
     */
    public void onDirectionChosen(int row, int col, String direction) {
        try {
            // Attempt to make the move with the selected cube and direction
            boolean moved = gameController.makeMove(row, col, direction);

            if (moved) {
                // Check if the move resulted in a win
                if (gameController.checkWin()) {
                    showGameResultScreen();
                    return;
                }

                // Update the game board view with the new state
                updateView();

                // If it's the computer player's turn, trigger AI move
                if (gameController.getCurrentPlayer() instanceof ComputerPlayer) {
                    makeAIMove();
                }
            }
        } catch (InvalidMoveException e) {
            showErrorAlert(e.getMessage());
        }
    }

    /**
     * Handles the computer player's move.
     * Gets the AI's next move, adds a delay for better user experience,
     * and executes the move after the delay.
     */
    private void makeAIMove() {
        // Get the current AI player and calculate its next move
        ComputerPlayer ai = (ComputerPlayer) gameController.getCurrentPlayer();
        ComputerPlayer.Move aiMove = ai.getNextMove(gameController.getGameLogic());

        if (aiMove != null) {
            // Add a delay before executing the AI move for better user experience
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                try {
                    // Execute the AI's move
                    boolean moved = gameController.makeMove(aiMove.row, aiMove.col, aiMove.direction);
                    if (moved) {
                        // Check if the AI won with this move
                        if (gameController.checkWin()) {
                            showGameResultScreen();
                        } else {
                            // Update the game board with the new state
                            updateView();
                        }
                    }
                } catch (InvalidMoveException e) {
                    // Handle the case where AI calculated an invalid move
                    showErrorAlert("AI made invalid move: " + e.getMessage());
                    updateView();
                }
            });
            pause.play();
        } else {
            // Handle the case where AI couldn't find any valid move
            showErrorAlert("AI couldn't find a valid move!");
            updateView();
        }
    }

    /**
     * Checks if it's currently a human player's turn.
     * 
     * @return true if the current player is human, false otherwise
     */
    private boolean isHumanPlayerTurn() {
        return gameController.getCurrentPlayer() instanceof Player &&
                !(gameController.getCurrentPlayer() instanceof ComputerPlayer);
    }

    /**
     * Updates the game view with the current board state and player information.
     * This method is called after each move to refresh the UI.
     */
    private void updateView() {
        // Get the current board state from the controller
        char[][] boardState = gameController.getBoardState();
        // Update the board display in the view
        gameScreen.updateBoard(boardState);
        // Update the player turn information in the view
        gameScreen.setPlayerTurn(
                gameController.getCurrentPlayer().getPlayerId(),
                gameController.getCurrentPlayer().getSymbol()
        );
    }

    /**
     * Displays an error alert with the specified message.
     * 
     * @param message The error message to display
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Move");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows the game result screen when a game is completed.
     * Creates a new GameResultPresenter and switches to its scene.
     */
    private void showGameResultScreen() {
        // Create a new game result view
        GameResultView gameResultView = new GameResultView();
        int gameId = gameController.getGameLogic().getGameId();

        // Create a presenter for the game result screen
        GameResultPresenter resultPresenter = new GameResultPresenter(
                gameResultView,
                primaryStage,
                gameId,
                menuScene
        );

        // Switch to the game result scene
        primaryStage.setScene(resultPresenter.getScene());
    }

    /**
     * Handles the event when a player chooses a symbol for a cube.
     * Sets the symbol on the cube and shows the direction dialog.
     * 
     * @param row The row index of the cube
     * @param col The column index of the cube
     * @param sign The symbol to set on the cube
     */
    public void onSignChosen(int row, int col, char sign) {
        try {
            // Set the chosen symbol on the cube
            gameController.setCubeSymbol(row, col, sign);
            // Show direction dialog for the next step
            gameScreen.showDirectionDialog(row, col);
        } catch (InvalidMoveException e) {
            showErrorAlert(e.getMessage());
        }
    }
}
