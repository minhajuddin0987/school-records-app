package Quixo.View.BackButton;

import javafx.scene.control.Button;

/**
 * BackButtonView class for the Quixo game.
 * This class creates and manages a back button that can be used in various screens
 * to navigate back to previous screens.
 */
public class BackButtonView {
    /**
     * The Button instance representing the back button.
     */
    private Button backButton;

    /**
     * Constructor for the BackButtonView.
     * Creates and configures a back button with specific text and size.
     */
    public BackButtonView() {
        // Create a new button with spaced text for better visual appearance
        backButton = new Button("B A C K");

        // Set the preferred size of the button
        backButton.setPrefSize(150, 50);
    }

    /**
     * Returns the back button instance.
     * This allows other components to access and use the button.
     *
     * @return The configured back button
     */
    public Button getBackButton() {
        return backButton;
    }
}
