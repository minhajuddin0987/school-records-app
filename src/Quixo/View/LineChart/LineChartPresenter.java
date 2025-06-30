package Quixo.View.LineChart;

import Quixo.Model.LineChartModel;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * Presenter class for the Line Chart view.
 * Handles the interaction between the LineChartView and LineChartModel.
 * Responsible for updating the view with data from the model and handling user events.
 */
public class LineChartPresenter {
    // View component for displaying the line chart
    private final LineChartView view;
    // Model component containing the data for the line chart
    private final LineChartModel model;
    // Main application window
    private final Stage primaryStage;
    // Reference to the previous scene to enable navigation back
    private final Scene previousScene;

    /**
     * Constructor for LineChartPresenter.
     * Initializes the presenter with the view, model, and navigation references.
     * 
     * @param view The LineChartView instance to be controlled
     * @param model The LineChartModel containing the data to be displayed
     * @param primaryStage The main application window
     * @param previousScene The scene to return to when back button is clicked
     */
    public LineChartPresenter(LineChartView view, LineChartModel model, Stage primaryStage, Scene previousScene) {
        this.view = view;
        this.model = model;
        this.primaryStage = primaryStage;
        this.previousScene = previousScene;

        // Set up event handlers for user interactions
        addEventHandlers();
    }

    /**
     * Updates the view with data for a specific game.
     * Retrieves player move durations from the model and displays them in the chart.
     * 
     * @param gameId The ID of the game to display data for
     */
    public void updateView(int gameId) {
        // Get player move duration data from the model
        XYChart.Series<Number, Number> playerSeries = model.getPlayerMoveDurations(gameId);
        // Update the view with the retrieved data
        view.setChartData(playerSeries);

        // Create a new scene with the updated view and apply CSS styling
        Scene scene = new Scene(view, 1300, 800);
        scene.getStylesheets().add(getClass().getResource("/CSS/linechart.css").toExternalForm());
        // Display the new scene in the application window
        primaryStage.setScene(scene);
    }

    /**
     * Sets up event handlers for user interactions with the view.
     * Currently handles the back button click event to return to the previous screen.
     */
    private void addEventHandlers() {
        // Set action for back button to return to the previous scene
        view.getBackButton().setOnAction(e -> primaryStage.setScene(previousScene));
    }
}
