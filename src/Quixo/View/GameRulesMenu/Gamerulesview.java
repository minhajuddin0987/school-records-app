package Quixo.View.GameRulesMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * View class for displaying the game rules.
 * Extends VBox to provide a vertical layout for the rules text and navigation button.
 */
public class Gamerulesview extends VBox {
    // Label to display the game rules text
    private Label rulesLabel;
    // Button to navigate back to the previous screen
    private Button backButton;

    /**
     * Constructor for Gamerulesview.
     * Initializes and sets up the UI components.
     */
    public Gamerulesview() {
        initialiseNodes();
        layoutNodes();
        loadStyles();
    }

    /**
     * Initializes the UI components.
     * Creates the rules label with formatted text and the back button.
     */
    private void initialiseNodes() {
        // Create label with multi-line text containing the game rules
        rulesLabel = new Label("""
                QUIXO RULES
                🌟 Goal:
                Align 5 of your symbols in a row (horizontal, vertical, or diagonal).
                How to Play:
                - Pick a cube from the outer edge.
                - Push it into the board in a valid direction.
                - Your symbol replaces the cube.
                Rules:
                - Only edge cubes are selectable.
                - Cannot immediately reverse the last opponent move.
                - The center cube is wild: counts for both X and O.
                Have fun!
                """);
        // Enable text wrapping for better readability
        rulesLabel.setWrapText(true);
        // Set maximum width to ensure proper formatting
        rulesLabel.setMaxWidth(500);

        // Create back button for navigation
        backButton = new Button("Back");
    }

    /**
     * Arranges the UI components in the layout.
     * Sets alignment, spacing, and padding for the VBox container.
     */
    private void layoutNodes() {
        // Center-align all components
        this.setAlignment(Pos.CENTER);
        // Set vertical spacing between components
        this.setSpacing(30);
        // Add padding around the container
        this.setPadding(new Insets(40));
        // Add the components to the VBox
        this.getChildren().addAll(rulesLabel, backButton);
    }

    /**
     * Applies CSS styles to the UI components.
     * Adds style classes and loads the external CSS file.
     */
    private void loadStyles() {
        // Apply style class to the container
        this.getStyleClass().add("rules-view");
        // Apply style class to the rules label
        rulesLabel.getStyleClass().add("rules-label");
        // Apply style class to the back button
        backButton.getStyleClass().add("back-button");

        // Attempt to load the external CSS file
        try {
            this.getStylesheets().add(getClass().getResource("/CSS/rules.css").toExternalForm());
        } catch (Exception e) {
            // Log error if CSS file cannot be loaded
            System.err.println("Failed to load CSS: " + e.getMessage());
        }
    }

    /**
     * Getter for the back button.
     * Allows the presenter to access the button for setting up event handlers.
     * 
     * @return The back button component
     */
    public Button getBackButton() {
        return backButton;
    }
}
