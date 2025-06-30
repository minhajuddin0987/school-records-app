package Quixo.View.MenuScreen;

import Quixo.Controller.GameController;
import Quixo.Model.GameLogic;
import Quixo.Model.Leaderboard;
import Quixo.Model.Player;
import Quixo.View.Game.GamePresenter;
import Quixo.View.Game.GameScreenView;
import Quixo.View.GameRulesMenu.GameRulesPresenter;
import Quixo.View.GameRulesMenu.Gamerulesview;
import Quixo.View.Leaderboard.LeaderboardPresenter;
import Quixo.View.Leaderboard.LeaderboardView;
import Quixo.View.PlayerSignSelection.PlayerSignSelectionView;
import Quixo.managers.AuthManager;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Presenter class for the Menu Screen.
 * This class handles the logic and user interactions for the main menu of the Quixo game.
 * It manages navigation to different screens like game start, leaderboard, and game rules.
 */
public class MenuScreenPresenter {
    /** The view component of the menu screen */
    private final MenuScreenView view;
    /** The primary stage of the application */
    private final Stage primaryStage;
    /** The scene for sign-in/sign-up screen to return to */
    private final Scene signInSignUpScene;
    /** Flag indicating if the game is against computer */
    private final boolean vsComputer;
    /** The scene for the menu screen */
    private final Scene menuScene;
    /** The currently authenticated player */
    private final Player authenticatedPlayer;

    /**
     * Constructor for the MenuScreenPresenter.
     * 
     * @param view The view component for the menu screen
     * @param primaryStage The primary stage of the application
     * @param signInSignUpScene The scene to return to for sign-in/sign-up
     * @param vsComputer Flag indicating if the game is against computer
     * @param menuScene The scene for the menu screen
     * @param authenticatedPlayer The currently authenticated player
     */
    public MenuScreenPresenter(MenuScreenView view, Stage primaryStage, Scene signInSignUpScene,
                               boolean vsComputer, Scene menuScene, Player authenticatedPlayer) {
        this.view = view;
        this.primaryStage = primaryStage;
        this.signInSignUpScene = signInSignUpScene;
        this.vsComputer = vsComputer;
        this.menuScene = menuScene;
        this.authenticatedPlayer = authenticatedPlayer;

        addEventHandlers();
    }

    /**
     * Sets up event handlers for all buttons in the menu screen.
     * This connects user interactions with their corresponding handler methods.
     */
    private void addEventHandlers() {
        view.getStartButtonView().getStartButton().setOnAction(event -> handleStartButtonClick());
        view.getBackButtonView().getBackButton().setOnAction(event -> handleBackButtonClick());
        view.getLeaderBoardView().getLeaderBoardButton().setOnAction(event -> handleLeaderboardButtonClick());
        view.getGameRulesButton().setOnAction(event -> handleGameRulesClick());
    }

    /**
     * Handles the start button click event.
     * Navigates to the player sign selection screen.
     */
    private void handleStartButtonClick() {
        showPlayerSignSelectionScreen();
    }

    /**
     * Displays the player sign selection screen.
     * Allows the player to choose between X and O symbols.
     */
    private void showPlayerSignSelectionScreen() {
        PlayerSignSelectionView selectionView = new PlayerSignSelectionView();
        selectionView.getXButton().setOnAction(e -> launchQuixoGame('X', 'O'));
        selectionView.getOButton().setOnAction(e -> launchQuixoGame('O', 'X'));
        primaryStage.setScene(selectionView.getScene());
        primaryStage.setTitle("Select Your Symbol");
    }

    /**
     * Launches the Quixo game with the selected player symbols.
     * Updates the player's symbol in the database, initializes the game logic,
     * and sets up the game screen.
     *
     * @param player1Symbol The symbol (X or O) chosen by player 1
     * @param player2Symbol The symbol (X or O) for player 2
     */
    private void launchQuixoGame(char player1Symbol, char player2Symbol) {
        try {
            // Update the player's symbol in the database
            AuthManager authManager = new AuthManager();
            authManager.updatePlayerSymbol(authenticatedPlayer.getPlayerId(), player1Symbol);
        } catch (Exception e) {
            showAlert("Error", "Failed to update symbol: " + e.getMessage());
            return;
        }

        // Create updated player with the selected symbol
        Player updatedPlayer = new Player(authenticatedPlayer.getPlayerId(), player1Symbol);
        // Initialize game logic with player symbols
        GameLogic gameLogic = new GameLogic(updatedPlayer, player2Symbol);
        gameLogic.initializeGameInDatabase();

        // Set up the game screen and its presenter
        GameScreenView gameScreen = new GameScreenView();
        GameController gameController = new GameController(gameLogic, primaryStage, menuScene);
        GamePresenter gamePresenter = new GamePresenter(gameController, gameScreen, primaryStage, menuScene);
        gameScreen.setPresenter(gamePresenter);

        // Display the game screen
        primaryStage.setScene(gameScreen.getScene());
        primaryStage.setTitle("Quixo Game Started");
    }

    /**
     * Handles the leaderboard button click event.
     * Creates and displays the leaderboard screen showing player rankings.
     */
    private void handleLeaderboardButtonClick() {
        try {
            // Initialize leaderboard view and model
            LeaderboardView leaderboardView = new LeaderboardView();
            Leaderboard leaderboardModel = new Leaderboard();

            // Store current scene to return to later
            Scene currentGameScene = primaryStage.getScene();

            // Create new scene for leaderboard
            Scene leaderboardScene = new Scene(leaderboardView, primaryStage.getWidth(), primaryStage.getHeight());

            // Set up leaderboard presenter with all necessary components
            new LeaderboardPresenter(
                    leaderboardView,
                    leaderboardModel,
                    primaryStage,
                    view,
                    currentGameScene,
                    vsComputer
            );

            // Display the leaderboard scene
            primaryStage.setScene(leaderboardScene);
        } catch (Exception e) {
            showAlert("Error", "Error opening leaderboard: " + e.getMessage());
        }
    }

    /**
     * Handles the game rules button click event.
     * Creates and displays the game rules screen with instructions on how to play.
     */
    private void handleGameRulesClick() {
        try {
            // Initialize game rules view and presenter
            Gamerulesview rulesView = new Gamerulesview();
            GameRulesPresenter rulesPresenter = new GameRulesPresenter(rulesView, primaryStage, menuScene);

            // Create and display the game rules scene
            Scene rulesScene = new Scene(rulesView, 1300, 800);
            primaryStage.setScene(rulesScene);
        } catch (Exception e) {
            showAlert("Error", "Failed to load game rules: " + e.getMessage());
        }
    }

    /**
     * Handles the back button click event.
     * Returns to the sign-in/sign-up screen.
     */
    private void handleBackButtonClick() {
        primaryStage.setScene(signInSignUpScene);
    }

    /**
     * Displays an error alert dialog with the specified title and message.
     * 
     * @param title The title of the alert dialog
     * @param message The error message to display
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Static initialization method to create and set up the menu screen.
     * This method creates the view, scene, and presenter for the menu screen.
     * 
     * @param primaryStage The primary stage of the application
     * @param signInSignUpScene The scene to return to for sign-in/sign-up
     * @return The created menu scene
     */
    public static Scene init(Stage primaryStage, Scene signInSignUpScene) {
        // Create the menu screen view and scene
        MenuScreenView gameScreenView = new MenuScreenView();
        Scene gameScene = new Scene(gameScreenView, 1300, 800);

        // Create the presenter with null Player for non-authenticated session or to be set later
        new MenuScreenPresenter(
                gameScreenView,
                primaryStage,
                signInSignUpScene,
                false,
                gameScene,
                null
        );

        return gameScene;
    }

}
