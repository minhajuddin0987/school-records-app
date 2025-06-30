package Quixo.View.startscreen;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * View class for the Start Screen of the Quixo game.
 * This class creates a simple screen with a centered start button
 * that uses an image as its graphic. It extends StackPane to allow
 * for centered positioning of the button.
 */
public class StartScreenView extends StackPane {
    /** Button for starting the application */
    private Button startButton;

    /**
     * Constructor for StartScreenView.
     * Initializes the UI components and sets up their layout.
     */
    public StartScreenView() {
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Initializes and configures the start button.
     * Creates a button with an image and sets its dimensions.
     */
    private void initialiseNodes() {
        // Load the button image from resources
        Image imageOk = new Image("/StartButton1.png");
        // Create button with the image as its graphic
        startButton = new Button("", new ImageView(imageOk));

        // Configure the image size
        ImageView imageView = (ImageView) startButton.getGraphic();
        imageView.setFitHeight(50);
        imageView.setFitWidth(150);

        // Set the button size
        startButton.setPrefSize(50, 50);
    }

    /**
     * Sets up the layout of UI components.
     * Adds the start button to the pane and centers it.
     */
    private void layoutNodes() {
        // Add the button to the pane
        this.getChildren().add(startButton);
        // Center the button in the pane
        this.setAlignment(javafx.geometry.Pos.CENTER);
    }

    /**
     * Gets the start button.
     * @return The button for starting the application
     */
    public Button getStartButton() {
        return startButton;
    }
}
