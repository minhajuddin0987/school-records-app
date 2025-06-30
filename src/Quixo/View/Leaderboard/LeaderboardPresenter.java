package Quixo.View.Leaderboard;

import Quixo.Model.Leaderboard;
import Quixo.View.MenuScreen.MenuScreenPresenter;
import Quixo.View.MenuScreen.MenuScreenView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;

/**
 * Presenter class for the Leaderboard screen.
 * Handles the interaction between the LeaderboardView and Leaderboard model.
 * Manages user actions and updates the view accordingly.
 */
public class LeaderboardPresenter {
    // View component reference
    private final LeaderboardView view;
    // Model component reference
    private final Leaderboard model;
    // Main application stage
    private final Stage primaryStage;
    // Reference to the menu screen view
    private final MenuScreenView gameScreenView;
    // Reference to the menu screen scene
    private final Scene gameScreenScene;
    // Flag indicating if the game is against computer
    private final boolean vsComputer;

    /**
     * Constructor for LeaderboardPresenter.
     * 
     * @param view The leaderboard view component
     * @param model The leaderboard data model
     * @param primaryStage The main application stage
     * @param gameScreenView The menu screen view to return to
     * @param gameScreenScene The menu screen scene to return to
     * @param vsComputer Flag indicating if game is against computer
     */
    public LeaderboardPresenter(LeaderboardView view, Leaderboard model, Stage primaryStage,
                                MenuScreenView gameScreenView, Scene gameScreenScene, boolean vsComputer) {
        this.view = view;
        this.model = model;
        this.primaryStage = primaryStage;
        this.gameScreenView = gameScreenView;
        this.gameScreenScene = gameScreenScene;
        this.vsComputer = vsComputer;

        initialize();
    }

    /**
     * Static initialization method to create the leaderboard screen.
     * Creates the view, model, and presenter, then returns the scene.
     * 
     * @param primaryStage The main application stage
     * @param gameScreenView The menu screen view to return to
     * @param gameScreenScene The menu screen scene to return to
     * @param vsComputer Flag indicating if game is against computer
     * @return A new Scene containing the leaderboard
     */
    public static Scene init(Stage primaryStage, MenuScreenView gameScreenView,
                             Scene gameScreenScene, boolean vsComputer) {
        LeaderboardView leaderboardView = new LeaderboardView();
        Leaderboard model = new Leaderboard();
        LeaderboardPresenter presenter = new LeaderboardPresenter(
                leaderboardView, model, primaryStage, gameScreenView, gameScreenScene, vsComputer
        );
        return presenter.getScene();
    }

    /**
     * Initialize the presenter by loading data and setting up event handlers.
     */
    private void initialize() {
        // Load leaderboard data sorted by win percentage
        loadData("win_percentage DESC NULLS LAST");
        // Set up button event handlers
        addEventHandlers();
    }

    /**
     * Add event handlers to the view components.
     * Sets up actions for sort buttons and back button.
     */
    private void addEventHandlers() {
        // Sort by win percentage when sort wins button is clicked
        view.getSortWinsButton().setOnAction(e -> loadData("win_percentage"));
        // Sort by total games when sort time button is clicked
        view.getSortTimeButton().setOnAction(e -> loadData("total_games"));
        // Navigate back to game screen when back button is clicked
        view.getBackButton().setOnAction(e -> goBackToGameScreen());
    }

    /**
     * Load leaderboard data with specified sorting.
     * 
     * @param sortBy SQL-style sort expression
     */
    private void loadData(String sortBy) {
        // Get leaderboard entries from the model
        List<String> entries = model.getLeaderboard(sortBy);
        // Update the view with the retrieved entries
        view.updateLeaderboard(entries);
    }

    /**
     * Navigate back to the game screen.
     * If the game screen is null, creates a new menu screen as fallback.
     */
    private void goBackToGameScreen() {
        if (gameScreenScene != null) {
            // Return to the existing game screen
            primaryStage.setScene(gameScreenScene);
        } else {
            // Create a new menu screen as fallback
            MenuScreenView newGameScreenView = new MenuScreenView();
            Scene fallbackScene = new Scene(newGameScreenView, 1300, 800);
            new MenuScreenPresenter(newGameScreenView, primaryStage, fallbackScene, vsComputer, gameScreenScene, null);
            primaryStage.setScene(fallbackScene);
        }
    }

    /**
     * Get the scene containing the leaderboard view.
     * 
     * @return A new Scene containing the leaderboard view
     */
    public Scene getScene() {
        return new Scene(view, 1300, 800);
    }
}
