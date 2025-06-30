package Quixo;

/**
 * Main application class for the Quixo game.
 * This class initializes the JavaFX application and handles the primary setup.
 */

import Quixo.Model.DbConnect;
import Quixo.View.ExitHandler.ExitHandler;
import Quixo.View.startscreen.StartScreenPresenter;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.sql.Connection;

/**
 * Main class that extends JavaFX Application.
 * Responsible for initializing the game, connecting to the database,
 * and setting up the primary stage.
 */
public class Main extends Application {
    /**
     * JavaFX application entry point. This method is called when the application is launched.
     * It initializes the database connection, sets up the primary stage, and handles any errors.
     * 
     * @param primaryStage The primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            try (Connection conn = DbConnect.getConnection()) {
                // Connection test
            } catch (Exception e) {
                System.err.println("Database connection failed!");
                e.printStackTrace();
                showErrorAlert("Database Error", "Failed to connect to database. Check logs for details.");
                return;
            }

            // ✅ Start screen handles everything: combo box, sign-in, transitions, etc.
            StartScreenPresenter.init(primaryStage);

            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/AppLogo.png")));
            primaryStage.setTitle("Game Application");
            primaryStage.show();

            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                ExitHandler.showExitConfirmation(primaryStage);
            });

        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Fatal Error", "Application failed to initialize. See logs for details.");
        }
    }

    /**
     * Displays an error alert dialog with the specified title and message.
     * 
     * @param title The title of the error dialog
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
     * The main entry point for the application.
     * This method launches the JavaFX application.
     * 
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
