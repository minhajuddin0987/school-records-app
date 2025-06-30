package Quixo.View.SignUp;

import Quixo.Model.Player;
import Quixo.Model.SignInModel;
import Quixo.Model.SignUpModel;
import Quixo.View.SignInScreen.SignInPresenter;
import Quixo.View.SignInScreen.SignInView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class SignUpPresenter {
    private final SignUpView view;
    private final SignUpModel model;
    private final Stage primaryStage;
    private Scene previousScene;

    public SignUpPresenter(SignUpView view, SignUpModel model, Stage primaryStage) {
        this.view = view;
        this.model = model;
        this.primaryStage = primaryStage;
        attachEvents();
    }

    private void attachEvents() {
        view.getDoneButton().setOnAction(e -> handleRegistration());
        view.getBackButton().setOnAction(e -> returnToPreviousScreen());
    }

    private void handleRegistration() {
        String playerId = view.getUsernameField().getText().trim();
        String password = view.getPasswordField().getText();
        String confirmPassword = view.getConfirmPasswordField().getText();

        try {
            Player player = model.registerPlayer(playerId, password, confirmPassword);
            showSuccessAlert("Success", "Account created for " + player.getPlayerId());
            returnToLoginScreen();
        } catch (Exception e) {
            showErrorAlert("Registration Failed", e.getMessage());
        }
    }

    private void returnToPreviousScreen() {
        primaryStage.setScene(previousScene);
    }

    private void returnToLoginScreen() {
        SignInView signInView = new SignInView();
        SignInPresenter signInPresenter = new SignInPresenter(signInView, new SignInModel(), primaryStage);

        // Create scene with proper dimensions (1300x800)
        Scene signInScene = new Scene(signInView, 1300, 800);

        // Set the previous scene for the SignInPresenter
        signInPresenter.setPreviousScene(previousScene);

        primaryStage.setScene(signInScene);
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
}