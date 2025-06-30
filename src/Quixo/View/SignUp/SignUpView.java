package Quixo.View.SignUp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * This class represents the view for the Sign Up screen.
 * It provides input fields for username, password, and password confirmation,
 * along with buttons to complete or cancel the sign-up process.
 * The view extends VBox to arrange elements vertically.
 */
public class SignUpView extends VBox {
    // Input fields for user registration
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    // Action buttons
    private Button doneButton;
    private Button backButton;

    /**
     * Constructor for SignUpView.
     * Initializes the view with input fields, buttons, layout, and background.
     */
    public SignUpView() {
        initialiseNodes();
        layoutNodes();
        setBackgroundImage();
    }

    /**
     * Initializes and styles all UI components.
     * Creates text fields, password fields, and buttons with appropriate styling.
     */
    private void initialiseNodes() {
        // Initialize username field with prompt text
        usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        // Initialize password field with prompt text
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        // Initialize confirm password field with prompt text
        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm password");

        // Initialize action buttons
        doneButton = new Button("Done");
        backButton = new Button("Back");

        // Define common style for text fields
        String textFieldStyle = "-fx-background-color: rgba(255, 255, 255, 0.9); " +
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 15; " +
                "-fx-border-color: transparent; " +
                "-fx-font-size: 16px; " +
                "-fx-padding: 10 15; " +
                "-fx-text-fill: #333333; " +
                "-fx-prompt-text-fill: #888888;";

        // Apply text field style to all input fields
        usernameField.setStyle(textFieldStyle);
        passwordField.setStyle(textFieldStyle);
        confirmPasswordField.setStyle(textFieldStyle);

        // Add focus effect to all input fields
        addFocusEffect(usernameField, textFieldStyle);
        addFocusEffect(passwordField, textFieldStyle);
        addFocusEffect(confirmPasswordField, textFieldStyle);

        // Define common style for buttons
        String buttonStyle = "-fx-background-color: #2196F3; " +
                "-fx-background-radius: 15; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 16px; " +
                "-fx-padding: 10 20;";

        // Apply button style to all buttons
        doneButton.setStyle(buttonStyle);
        backButton.setStyle(buttonStyle);

        // Add hover effects to buttons
        doneButton.setOnMouseEntered(e -> doneButton.setStyle(buttonStyle + "-fx-background-color: #1976D2;"));
        doneButton.setOnMouseExited(e -> doneButton.setStyle(buttonStyle));

        backButton.setOnMouseEntered(e -> backButton.setStyle(buttonStyle + "-fx-background-color: #1976D2;"));
        backButton.setOnMouseExited(e -> backButton.setStyle(buttonStyle));
    }

    /**
     * Adds a focus effect to a text field.
     * When the field gains focus, a blue border is added. When focus is lost, the border is removed.
     * 
     * @param field The text field to add the focus effect to
     * @param baseStyle The base style of the text field
     */
    private void addFocusEffect(TextField field, String baseStyle) {
        // Add listener to the focused property
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                // When field gains focus, add blue border
                field.setStyle(baseStyle + "-fx-border-color: #2196F3; -fx-border-width: 2;");
            } else {
                // When field loses focus, revert to base style
                field.setStyle(baseStyle);
            }
        });
    }

    /**
     * Sets up the layout of UI components.
     * Configures spacing, padding, alignment, and adds all components to the view.
     */
    private void layoutNodes() {
        // Set vertical spacing between elements
        this.setSpacing(15);
        // Add padding around the container
        this.setPadding(new Insets(20));
        // Center-align all elements
        this.setAlignment(Pos.CENTER);

        // Create and style the title label
        Label titleLabel = new Label("Create New Account");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        // Set maximum width for input fields
        this.usernameField.setMaxWidth(300);
        this.passwordField.setMaxWidth(300);
        this.confirmPasswordField.setMaxWidth(300);

        // Add all components to the view in order
        this.getChildren().addAll(
                titleLabel,
                usernameField,
                passwordField,
                confirmPasswordField,
                doneButton,
                backButton
        );
    }

    /**
     * Sets the background image for the view.
     * Loads an image and configures its display properties.
     */
    private void setBackgroundImage() {
        // Load background image from resources
        Image backgroundImage = new Image(getClass().getResource("/picture4.png").toExternalForm());
        // Configure background size properties
        BackgroundSize backgroundSize = new BackgroundSize(800, 600, false, false, false, true);
        // Create background image with positioning and repeat settings
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );
        // Apply background to the view
        this.setBackground(new Background(background));
    }

    // Getters for accessing UI components

    /**
     * Gets the username field.
     * @return The username text field
     */
    public TextField getUsernameField() {
        return usernameField;
    }

    /**
     * Gets the password field.
     * @return The password field
     */
    public PasswordField getPasswordField() {
        return passwordField;
    }

    /**
     * Gets the confirm password field.
     * @return The confirm password field
     */
    public PasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    /**
     * Gets the done button.
     * @return The done button
     */
    public Button getDoneButton() {
        return doneButton;
    }

    /**
     * Gets the back button.
     * @return The back button
     */
    public Button getBackButton() {
        return backButton;
    }
}
