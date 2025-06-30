package Quixo.View.LineChart;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * View class for displaying a line chart of game statistics.
 * Extends VBox to organize chart and navigation elements vertically.
 * Displays move durations for players during a game.
 */
public class LineChartView extends VBox {
    // The line chart component to display move duration data
    private final LineChart<Number, Number> lineChart;
    // Button to navigate back to the previous screen
    private final Button backButton;

    /**
     * Constructor for LineChartView.
     * Initializes and configures the line chart and back button.
     * Sets up the layout for the view.
     */
    public LineChartView() {
        // Create and configure X-axis for move numbers
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Move Number");

        // Create and configure Y-axis for duration in seconds
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Duration (seconds)");

        // Initialize the line chart with the configured axes
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Match Statistics");
        // Allow the chart to expand to fill available space
        lineChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(lineChart, Priority.ALWAYS);

        // Create the back button for navigation
        backButton = new Button("Back to Results");

        // Configure the layout properties of the VBox
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setPadding(new Insets(40));

        // Add the chart and button to the view
        getChildren().addAll(lineChart, backButton);
    }

    /**
     * Sets the data to be displayed in the line chart.
     * Clears any existing data before adding the new series.
     * 
     * @param series The data series containing move numbers and durations
     */
    public void setChartData(XYChart.Series<Number, Number> series) {
        // Clear any existing data from the chart
        lineChart.getData().clear();
        // Add the new data series to the chart
        lineChart.getData().add(series);
    }

    /**
     * Gets the back button for event handling.
     * 
     * @return The back button instance
     */
    public Button getBackButton() {
        return backButton;
    }
}
