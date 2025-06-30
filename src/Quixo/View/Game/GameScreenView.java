package Quixo.View.Game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * GameScreenView class is responsible for creating and managing the game board UI.
 * It displays the 5x5 grid of buttons representing the Quixo game board and handles
 * user interactions with the game elements.
 */
public class GameScreenView {
    /** Grid layout for the 5x5 game board */
    private GridPane gridPane;
    /** Root container for all UI elements */
    private VBox root;
    /** Presenter that handles game logic and user interactions */
    private GamePresenter presenter;
    /** Label that displays whose turn it is */
    private Label playerTurnLabel;
    /** The main scene containing the game UI */
    private Scene scene;

    /**
     * Constructor for GameScreenView.
     * Initializes the grid pane and player turn label, then sets up all UI components.
     */
    public GameScreenView() {
        gridPane = new GridPane();
        playerTurnLabel = new Label("Player's Turn: ");
        initializeNodes();
    }

    /**
     * Sets the presenter for this view.
     * @param presenter The GamePresenter that will handle game logic
     */
    public void setPresenter(GamePresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Initializes all UI nodes and sets up the game board.
     * Creates a 5x5 grid of buttons and configures their appearance and event handlers.
     */
    private void initializeNodes() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Button button = new Button();
                button.setMinSize(100, 100);
                button.getStyleClass().add("game-button");

                int finalI = i;
                int finalJ = j;
                button.setOnAction(e -> presenter.onCubeClicked(finalI, finalJ));

                gridPane.add(button, j, i);
            }
        }

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        playerTurnLabel.getStyleClass().add("player-turn-label");

        root = new VBox(20, playerTurnLabel, gridPane);
        root.getStyleClass().add("game-root");

        scene = new Scene(root, 1300, 800);
        scene.getStylesheets().add(getClass().getResource("/CSS/gamescreen.css").toExternalForm());
    }


    /**
     * Returns the default CSS style for game buttons.
     * @return A string containing CSS styling properties
     */
    private String getButtonStyle() {
        return "-fx-background-color: #ecf0f1; " +
                "-fx-background-radius: 10; " +
                "-fx-border-color: #2980b9; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 10; " +
                "-fx-text-fill: #2c3e50;";
    }

    /**
     * Adds hover effect to a button.
     * Changes the button's appearance when mouse enters and exits.
     * @param button The button to add hover effect to
     */
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #bdc3c7; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: #2980b9; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 10; " +
                        "-fx-text-fill: #2c3e50;"
        ));
        button.setOnMouseExited(e -> button.setStyle(getButtonStyle()));
    }

    /**
     * Gets the scene containing the game UI.
     * Creates a new scene if one doesn't exist yet.
     * @return The JavaFX scene for this view
     */
    public Scene getScene() {
        if (scene == null) {
            scene = new Scene(root, 1300, 800);
        }
        return scene;
    }

    /**
     * Updates the game board UI based on the current board state.
     * Sets the text of each button to match the corresponding character in the board state.
     * @param boardState A 2D array representing the current state of the game board
     */
    public void updateBoard(char[][] boardState) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Button button = (Button) getNodeByRowColumnIndex(i, j, gridPane);
                if (button != null) {
                    button.setText(String.valueOf(boardState[i][j]));
                }
            }
        }
    }

    /**
     * Finds a node in the grid pane by its row and column indices.
     * @param row The row index of the node
     * @param column The column index of the node
     * @param gridPane The grid pane to search in
     * @return The node at the specified position, or null if not found
     */
    private javafx.scene.Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    /**
     * Updates the player turn label to show whose turn it is.
     * @param playerId The ID of the current player
     * @param symbol The symbol (X or O) of the current player
     */
    public void setPlayerTurn(String playerId, char symbol) {
        playerTurnLabel.setText("Player " + playerId + " (" + symbol + ")'s Turn");
    }


    /**
     * Shows a dialog for the player to choose a direction to push the cube.
     * The available directions depend on the position of the selected cube.
     * Only cubes on the edges of the board can be selected.
     * 
     * @param row The row index of the selected cube
     * @param col The column index of the selected cube
     */
    public void showDirectionDialog(int row, int col) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose Direction");
        alert.setHeaderText("Select a direction to push the cube");
        alert.setContentText("Choose your option:");

        List<ButtonType> options = new ArrayList<>();
        if (col == 0) {
            // Left edge - can push right, up, or down
            options.add(new ButtonType("Right"));
            options.add(new ButtonType("Up"));
            options.add(new ButtonType("Down"));
        } else if (col == 4) {
            // Right edge - can push left, up, or down
            options.add(new ButtonType("Left"));
            options.add(new ButtonType("Up"));
            options.add(new ButtonType("Down"));
        } else if (row == 0) {
            // Top edge - can push down, left, or right
            options.add(new ButtonType("Down"));
            options.add(new ButtonType("Left"));
            options.add(new ButtonType("Right"));
        } else if (row == 4) {
            // Bottom edge - can push up, left, or right
            options.add(new ButtonType("Up"));
            options.add(new ButtonType("Left"));
            options.add(new ButtonType("Right"));
        } else {
            // Not on an edge - show error message
            Alert invalidAlert = new Alert(Alert.AlertType.WARNING);
            invalidAlert.setTitle("Invalid Move");
            invalidAlert.setHeaderText("Invalid cube selection.");
            invalidAlert.setContentText("You can only select from the edges!");
            invalidAlert.showAndWait();
            return;
        }

        // Add cancel button
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        options.add(cancel);

        alert.getButtonTypes().setAll(options);

        // Show dialog and process result
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() != cancel) {
            String direction = result.get().getText().toLowerCase();
            presenter.onDirectionChosen(row, col, direction);
        }
    }

}
