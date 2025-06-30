package Quixo.View.SignInScreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * View class for the Sign-In Screen of the Quixo game.
 * This class creates and manages the UI components for user authentication,
 * including username and password fields, login and back buttons, and error display.
 * It extends VBox to organize the layout of these components vertically.
 */
public class SignInView extends VBox {

    /** Text field for entering username */
    private final TextField usernameField = new TextField();
    /** Password field for entering password securely */
    private final PasswordField passwordField = new PasswordField();
    /** Button to submit login credentials */
    private final Button loginButton = new Button("Login");
    /** Button to navigate back to previous screen */
    private final Button backButton = new Button("Back");
    /** Label for displaying error messages */
    private final Label errorLabel = new Label();

    /**
     * Constructor for SignInView.
     * Initializes and configures UI components, applies styling,
     * sets up the layout, and loads the background image.
     */
    public SignInView() {
        // Set placeholder text for input fields
        usernameField.setPromptText("Enter username");
        passwordField.setPromptText("Enter password");
        errorLabel.setVisible(false);

        // Apply CSS style classes to components
        this.getStyleClass().add("auth-root");
        usernameField.getStyleClass().add("auth-field");
        passwordField.getStyleClass().add("auth-field");
        loginButton.getStyleClass().add("auth-button");
        backButton.getStyleClass().add("auth-button");
        errorLabel.getStyleClass().add("error-label");

        // Set up the layout and background
        layoutNodes();
        setBackgroundImage();
        getStylesheets().add(getClass().getResource("/CSS/signin.css").toExternalForm());
    }

    /**
     * Sets up the layout of UI components.
     * Configures spacing, padding, alignment, and adds all components to the VBox.
     */
    private void layoutNodes() {
        // Configure the VBox layout properties
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        // Create and style the title
        Label titleLabel = new Label("Welcome Back");
        titleLabel.getStyleClass().add("auth-title");

        // Set maximum width for input fields
        usernameField.setMaxWidth(300);
        passwordField.setMaxWidth(300);

        // Add all components to the VBox
        this.getChildren().addAll(titleLabel, usernameField, passwordField, errorLabel, loginButton, backButton);
    }

    /**
     * Sets the background image for the sign-in screen.
     * Loads the image from resources and configures its display properties.
     */
    private void setBackgroundImage() {
        // Load and configure the background image
        Image backgroundImage = new Image(getClass().getResource("/picture4.png").toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(800, 600, false, false, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        this.setBackground(new Background(background));
    }

    /**
     * Gets the username text field.
     * @return The text field for entering username
     */
    public TextField getUsernameField() { return usernameField; }

    /**
     * Gets the password field.
     * @return The password field for entering password
     */
    public PasswordField getPasswordField() { return passwordField; }

    /**
     * Gets the login button.
     * @return The button for submitting login credentials
     */
    public Button getLoginButton() { return loginButton; }

    /**
     * Gets the back button.
     * @return The button for navigating back to the previous screen
     */
    public Button getBackButton() { return backButton; }

    /**
     * Displays an error message to the user.
     * Sets the error label text and makes it visible.
     * 
     * @param message The error message to display
     */
    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    /**
     * Clears any displayed error message.
     * Resets the error label text and hides it.
     */
    public void clearError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }
}
