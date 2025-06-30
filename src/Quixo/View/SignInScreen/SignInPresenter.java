package Quixo.View.SignInScreen;

import Quixo.Model.Player;
import Quixo.Model.SignInModel;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import Quixo.View.MenuScreen.MenuScreenPresenter;
import Quixo.View.MenuScreen.MenuScreenView;

/**
 * Presenter class for the Sign-In Screen of the Quixo game.
 * This class handles the logic for user authentication, including validating credentials,
 * displaying appropriate messages, and navigating to the game menu upon successful login.
 * It follows the MVP (Model-View-Presenter) pattern to separate UI from business logic.
 */
public class SignInPresenter {
    /** The view component of the sign-in screen */
    private final SignInView view;
    /** The model component handling authentication logic */
    private final SignInModel model;
    /** The primary stage of the application */
    private final Stage primaryStage;
    /** The scene to return to when back button is pressed */
    private Scene previousScene;

    /**
     * Constructor for SignInPresenter.
     * Initializes the presenter with view, model, and stage references,
     * and sets up event handlers.
     *
     * @param view The view component for the sign-in screen
     * @param model The model component handling authentication
     * @param primaryStage The primary stage of the application
     */
    public SignInPresenter(SignInView view, SignInModel model, Stage primaryStage) {
        this.view = view;
        this.model = model;
        this.primaryStage = primaryStage;
        attachEvents();
    }

    /**
     * Attaches event handlers to UI components.
     * Sets up actions for login and back buttons.
     */
    private void attachEvents() {
        view.getLoginButton().setOnAction(e -> handleLogin());
        view.getBackButton().setOnAction(e -> primaryStage.setScene(previousScene));
    }

    /**
     * Handles the login button click event.
     * Retrieves username and password from the view,
     * attempts authentication, and navigates to the game screen on success.
     */
    private void handleLogin() {
        // Get user input from the view
        String playerId = view.getUsernameField().getText().trim();
        String password = view.getPasswordField().getText();

        try {
            // Attempt authentication
            Player player = model.authenticate(playerId, password);
            showSuccessAlert("Login Successful", "Welcome back, " + player.getPlayerId() + "!");

            // Navigate to game screen on successful login
            navigateToGameScreen(player);
        } catch (Exception e) {
            // Show error message if authentication fails
            showErrorAlert("Login Failed", e.getMessage());
        }
    }

    /**
     * Navigates to the game menu screen after successful authentication.
     * Creates the game menu view and presenter, and sets them as the current scene.
     *
     * @param player The authenticated player object
     */
    private void navigateToGameScreen(Player player) {
        // Create the game menu view and scene
        MenuScreenView gameScreenView = new MenuScreenView();
        Scene gameScene = new Scene(gameScreenView, 1300, 800);

        // Create the game menu presenter with the authenticated player
        MenuScreenPresenter gameScreenPresenter = new MenuScreenPresenter(
                gameScreenView,
                primaryStage,
                previousScene,
                false,
                gameScene, 
                player
        );

        // Set the scene and title
        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Game Menu");
    }

    /**
     * Displays an error alert dialog with the specified title and message.
     *
     * @param title The title of the error alert
     * @param message The error message to display
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a success alert dialog with the specified title and message.
     *
     * @param title The title of the success alert
     * @param message The success message to display
     */
    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Sets the previous scene to return to when the back button is pressed.
     *
     * @param scene The scene to set as the previous scene
     */
    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
}
