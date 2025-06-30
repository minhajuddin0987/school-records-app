package Quixo.View.PlayerSignSelection;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * View class for the Player Sign Selection screen of the Quixo game.
 * This class creates a UI that allows players to choose between 'X' and 'O'
 * as their game symbol before starting a game.
 */
public class PlayerSignSelectionView {
    /** Root container for all UI elements */
    private VBox root;
    /** Button for selecting 'X' as the player's symbol */
    private Button xButton;
    /** Button for selecting 'O' as the player's symbol */
    private Button oButton;
    /** The scene containing the player sign selection UI */
    private Scene scene;

    /**
     * Constructor for PlayerSignSelectionView.
     * Initializes the UI components by calling createUI().
     */
    public PlayerSignSelectionView() {
        createUI();
    }

    /**
     * Creates and configures all UI components for the player sign selection screen.
     * Sets up the title, buttons, layout, and applies CSS styling.
     */
    private void createUI() {
        // Create and style the title
        Text title = new Text("Choose Your Symbol");
        title.getStyleClass().add("player-sign-title");

        // Create and style the X button
        xButton = new Button("X");
        xButton.getStyleClass().add("player-sign-button");

        // Create and style the O button
        oButton = new Button("O");
        oButton.getStyleClass().add("player-sign-button");

        // Create the root container and add all components
        root = new VBox(40, title, xButton, oButton);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("player-sign-root");

        // Create the scene and apply CSS
        scene = new Scene(root, 1300, 800);
        scene.getStylesheets().add(getClass().getResource("/CSS/playersignselection.css").toExternalForm());
    }

    /**
     * Gets the scene containing the player sign selection UI.
     * @return The JavaFX scene for this view
     */
    public Scene getScene() { return scene; }

    /**
     * Gets the button for selecting 'X' as the player's symbol.
     * @return The X button
     */
    public Button getXButton() { return xButton; }

    /**
     * Gets the button for selecting 'O' as the player's symbol.
     * @return The O button
     */
    public Button getOButton() { return oButton; }
}
