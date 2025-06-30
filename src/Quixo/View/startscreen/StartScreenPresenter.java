package Quixo.View.startscreen;

/**
 * Presenter class for the start screen of the application.
 * This class handles the logic for the start screen and manages navigation to other screens.
 */

import Quixo.Model.SignUpModel;
import Quixo.Model.StartScreenModel;
import Quixo.View.BackgroundScreen.BackgroundView;
import Quixo.View.SignInScreen.SignInPresenter;
import Quixo.View.SignInScreen.SignInView;
import Quixo.View.SignInSignUp.SignInSignUpView;
import Quixo.View.SignUp.SignUpPresenter;
import Quixo.View.SignUp.SignUpView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import Quixo.View.ComboBox.ComboBoxPresenter;


public class StartScreenPresenter {

    private StartScreenView view;
    private StartScreenModel model;
    private Stage primaryStage;
    // private Scene gameScene;

    /**
     * Constructor for the StartScreenPresenter.
     * Initializes the presenter with the view, model, and primary stage.
     * 
     * @param view The StartScreenView to be controlled by this presenter
     * @param model The StartScreenModel containing the business logic
     * @param primaryStage The main application stage
     */
    public StartScreenPresenter(StartScreenView view, StartScreenModel model, Stage primaryStage) {
        this.view = view;
        this.model = model;
        this.primaryStage = primaryStage;
        // this.gameScene = gameScene;
        addEventHandler();
    }

    /**
     * Sets up event handlers for the view components.
     * Connects the start button to its click handler.
     */
    private void addEventHandler() {
        view.getStartButton().setOnAction(event -> handleStartButtonClick());
    }

    /**
     * Handles the start button click event.
     * Creates the sign-in/sign-up screen and sets up navigation between screens.
     */
    private void handleStartButtonClick() {
        SignInSignUpView signInSignUpView = new SignInSignUpView();
        Scene signInSignUpScene = new Scene(signInSignUpView, 1300, 800);

        // Sign In button logic
        signInSignUpView.getSignInButton().setOnAction(e -> {
            SignInView signInView = new SignInView();
            Scene signInScene = new Scene(signInView, 1300, 800);
            SignInPresenter signInPresenter = new SignInPresenter(signInView, new Quixo.Model.SignInModel(), primaryStage);
            signInPresenter.setPreviousScene(signInSignUpScene);
            primaryStage.setScene(signInScene);
        });

        // Sign Up button logic
        signInSignUpView.getSignUpButton().setOnAction(e -> {
            SignUpView signUpView = new SignUpView();
            Scene signUpScene = new Scene(signUpView, 1300, 800);
            SignUpPresenter signUpPresenter = new SignUpPresenter(signUpView, new SignUpModel(), primaryStage);
            signUpPresenter.setPreviousScene(signInSignUpScene);
            primaryStage.setScene(signUpScene);
        });

        primaryStage.setScene(signInSignUpScene);
    }
    /**
     * Static initialization method to set up the complete start screen.
     * Creates the background, start screen view, and initializes the presenter.
     * This method is called from the Main class to set up the initial application screen.
     * 
     * @param primaryStage The main application stage
     */
    public static void init(Stage primaryStage) {
        BackgroundView backgroundView = new BackgroundView();
        StartScreenView startScreenView = new StartScreenView();

        StackPane stackPane = new StackPane(backgroundView, startScreenView);
        BorderPane root = new BorderPane();
        root.setCenter(stackPane);

        Scene mainScene = new Scene(root, 1300, 800);

        ComboBoxPresenter.init(primaryStage, mainScene);
        new StartScreenPresenter(startScreenView, new StartScreenModel(), primaryStage);

        primaryStage.setScene(mainScene);
    }

}
