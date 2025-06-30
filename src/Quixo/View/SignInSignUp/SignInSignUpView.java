package Quixo.View.SignInSignUp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * This class represents the view for the Sign In and Sign Up screen.
 * It provides buttons for users to either sign up or sign in to the application.
 * The view extends VBox to arrange elements vertically.
 */
public class SignInSignUpView extends VBox {
    // Buttons for sign up and sign in actions
    private final Button signUpButton = new Button("Sign Up");
    private final Button signInButton = new Button("Sign In");

    /**
     * Constructor for SignInSignUpView.
     * Initializes the view with styling, layout, and background.
     */
    public SignInSignUpView() {
        // Add CSS styling class to the root element
        this.getStyleClass().add("auth-root");
        // Apply transparent button styling to both buttons
        signUpButton.getStyleClass().add("transparent-button");
        signInButton.getStyleClass().add("transparent-button");

        // Set up the layout and background
        layoutNodes();
        setBackgroundImage();
        // Load external CSS stylesheet
        this.getStylesheets().add(getClass().getResource("/CSS/signinsignup.css").toExternalForm());
    }

    /**
     * Sets up the layout of UI components.
     * Configures spacing, padding, alignment, and button sizes.
     */
    private void layoutNodes() {
        // Set vertical spacing between elements
        this.setSpacing(20);
        // Add padding around the container
        this.setPadding(new Insets(20));
        // Center-align all elements
        this.setAlignment(Pos.CENTER);

        // Set preferred size for buttons
        signUpButton.setPrefSize(200, 100);
        signInButton.setPrefSize(200, 100);

        // Add buttons to the view
        this.getChildren().addAll(signUpButton, signInButton);
    }

    /**
     * Sets the background image for the view.
     * Loads an image and configures its display properties.
     */
    private void setBackgroundImage() {
        // Load background image from resources
        Image backgroundImage = new Image(getClass().getResource("/picture4.png").toExternalForm());
        // Configure background size properties
        BackgroundSize backgroundSize = new BackgroundSize(500, 400, false, false, false, true);
        // Create background image with positioning and repeat settings
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        // Apply background to the view
        this.setBackground(new Background(background));
    }

    /**
     * Gets the sign up button.
     * @return The sign up button
     */
    public Button getSignUpButton() { return signUpButton; }

    /**
     * Gets the sign in button.
     * @return The sign in button
     */
    public Button getSignInButton() { return signInButton; }
}
