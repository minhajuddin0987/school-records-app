package Quixo.View.StartButton;

import javafx.scene.control.Button;

/**
 * View class for the Start Game button in the Quixo game.
 * This class creates and configures a button that allows users to start a new game.
 * It's designed to be used within other views like the menu screen.
 */
public class StartButtonView {
    /** Button for starting a new game */
    private Button startButton;

    /**
     * Constructor for StartButtonView.
     * Creates and configures a new button with appropriate text and size.
     */
    public StartButtonView() {
        // Create button with spaced text for visual emphasis
        startButton = new Button("S T A R T   G A M E");
        // Set preferred size for the button
        startButton.setPrefSize(150, 50);
    }

    /**
     * Gets the start game button.
     * @return The button for starting a new game
     */
    public Button getStartButton() {
        return startButton;
    }
}
