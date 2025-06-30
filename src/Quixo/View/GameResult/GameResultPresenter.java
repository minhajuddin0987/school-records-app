package Quixo.View.GameResult;

import Quixo.Model.GameResult;
import Quixo.Model.GameResults;
import Quixo.Model.LineChartModel;
import Quixo.View.LineChart.LineChartPresenter;
import Quixo.View.LineChart.LineChartView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

/**
 * Presenter class for the Game Result screen.
 * This class handles the logic for displaying game results and manages user interactions
 * with the game result view.
 */
public class GameResultPresenter {
    /** The view component for displaying game results */
    private final GameResultView view;
    /** The primary stage of the application */
    private final Stage primaryStage;
    /** The ID of the game whose results are being displayed */
    private final int gameId;
    /** The scene for the menu screen to return to */
    private final Scene menuScene;
    /** The scene for the game result screen */
    private final Scene gameResultScene;
    /** Flag indicating if the presenter is in a valid state */
    private boolean valid = true;

    /**
     * Constructor for the GameResultPresenter.
     * 
     * @param view The view component for displaying game results
     * @param primaryStage The primary stage of the application
     * @param gameId The ID of the game whose results are being displayed
     * @param menuScene The scene for the menu screen to return to
     */
    public GameResultPresenter(GameResultView view, Stage primaryStage, int gameId, Scene menuScene) {
        this.view = view;
        this.primaryStage = primaryStage;
        this.gameId = gameId;
        this.menuScene = menuScene;

        // Validate the game ID
        if (gameId <= 0) {
            System.err.println("[GameResultPresenter] Invalid gameId: " + gameId);
            valid = false;
            this.gameResultScene = new Scene(new GameResultView(), 1300, 800);
            return;
        }

        // Create the scene and initialize the view
        this.gameResultScene = new Scene(view, 1300, 800);
        addEventHandlers();
        loadGameResults();
    }

    /**
     * Loads the game results for the specified game ID and updates the view.
     */
    private void loadGameResults() {
        // Retrieve game results from the model
        List<GameResult> results = GameResults.getGameResults(gameId);
        // Update the view with the retrieved results
        view.updateView(results);
    }

    /**
     * Sets up event handlers for the view components.
     */
    private void addEventHandlers() {
        // Set action for the show chart button
        view.getShowChartButton().setOnAction(e -> showLineChart());
        // Set action for the menu button
        view.getMenuButton().setOnAction(e -> goBackToMenu());
    }

    /**
     * Displays the line chart view for visualizing game results.
     */
    private void showLineChart() {
        // Create new instances of the chart view and model
        LineChartView chartView = new LineChartView();
        LineChartModel model = new LineChartModel();

        // Create a presenter for the line chart
        LineChartPresenter presenter = new LineChartPresenter(
                chartView,
                model,
                primaryStage,
                gameResultScene
        );

        // Update the chart view with the current game's data
        presenter.updateView(gameId);
    }

    /**
     * Navigates back to the menu screen.
     */
    private void goBackToMenu() {
        // Set the scene to the menu scene
        primaryStage.setScene(menuScene);
    }

    /**
     * Gets the scene for the game result screen.
     * 
     * @return The game result scene
     */
    public Scene getScene() {
        return gameResultScene;
    }
}
