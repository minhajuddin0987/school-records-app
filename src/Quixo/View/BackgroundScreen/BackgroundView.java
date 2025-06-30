package Quixo.View.BackgroundScreen;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * BackgroundView class for the Quixo game.
 * This class creates and manages the background image for the game screens.
 * It extends BorderPane to provide layout capabilities for positioning the background image.
 */
public class BackgroundView extends BorderPane {

    /**
     * The ImageView that displays the main background image.
     */
    private ImageView imageView;

    /**
     * The ImageView that can be used to display a logo (currently unused).
     */
    private ImageView logoView;

    /**
     * Constructor for the BackgroundView.
     * Initializes and lays out the UI components.
     */
    public BackgroundView() {
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Initializes the UI components.
     * Creates and configures the background image.
     */
    private void initialiseNodes() {
        // Create a new ImageView with the Quixo background image
        imageView = new ImageView(new Image("/Quixo.png"));

        // Resize the background image to fit the screen
        imageView.setFitWidth(1300);
        imageView.setFitHeight(1300);
        imageView.setPreserveRatio(true);
    }

    /**
     * Lays out the UI components.
     * Places the background image in the center of the BorderPane.
     */
    private void layoutNodes() {
        // Set the background image to the center of the BorderPane
        setCenter(imageView);

        // Set margin to 0 for the background image
        BorderPane.setMargin(imageView, new Insets(0));
    }
}
