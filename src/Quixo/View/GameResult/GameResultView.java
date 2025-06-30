package Quixo.View.GameResult;

import Quixo.Model.GameResult;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

/**
 * GameResultView class is responsible for displaying game results to the user.
 * It extends VBox to create a vertical layout for showing game summary and results.
 */
public class GameResultView extends VBox {
    /** Button to display chart visualization of game results */
    private final Button showChartButton;

    /** Button to navigate back to the main menu */
    private final Button menuButton;

    /** Container for displaying game results */
    private final VBox resultsContainer;

    /** Title text for the game summary screen */
    private final Text title;

    /**
     * Constructor initializes UI components and configures the layout
     */
    public GameResultView() {
        showChartButton = new Button("📊 Show Line Chart");
        menuButton = new Button("↩ Back to Menu");
        resultsContainer = new VBox(20);
        title = new Text("🏆 Game Summary");

        configureLayout();
    }

    /**
     * Sets up the visual layout of the game result screen
     * Configures background, styling, and arranges components
     */
    private void configureLayout() {
        setPadding(new Insets(50));
        setSpacing(30);
        setAlignment(Pos.TOP_CENTER);

        // Background
        setBackground(new Background(new BackgroundImage(
                new Image("Startback.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, true)
        )));

        title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 50));
        title.setFill(Color.GOLD);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 5, 0.4, 2, 2);");



        ScrollPane scrollPane = new ScrollPane(resultsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        resultsContainer.setAlignment(Pos.CENTER);
        resultsContainer.setPadding(new Insets(20));
        styleButton(showChartButton, "#00cfff");
        styleButton(menuButton, "#ff6e40");

        HBox buttonBox = new HBox(30, showChartButton, menuButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        getChildren().addAll(title, scrollPane, buttonBox);
    }

    /**
     * Applies consistent styling to buttons
     * 
     * @param button The button to style
     * @param color The background color for the button in hex format
     */
    private void styleButton(Button button, String color) {
        button.setFont(Font.font("Arial", 16));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: " + color + ";" +
                "-fx-background-radius: 20;" +
                "-fx-padding: 10 20;" +
                "-fx-cursor: hand;");
    }

    /**
     * Updates the view with game results
     * Displays a message if no results are available, otherwise shows each result
     * 
     * @param results List of GameResult objects to display
     */
    public void updateView(List<GameResult> results) {
        Platform.runLater(() -> {
            resultsContainer.getChildren().clear();

            // Display message if no results are available
            if (results == null || results.isEmpty()) {
                Label noData = new Label("No game result found.");
                noData.setFont(Font.font("Arial", 18));
                noData.setTextFill(Color.WHITE);
                noData.setAlignment(Pos.CENTER);
                resultsContainer.getChildren().add(noData);
                return;
            }

            // Create a display box for each game result
            for (GameResult result : results) {
                VBox entryBox = new VBox(10);
                entryBox.setAlignment(Pos.CENTER);

                // Result line at the top
                Label resultText = new Label("Result: " + result.getResult());
                resultText.setFont(Font.font("Consolas", FontWeight.EXTRA_BOLD, 26));
                resultText.setTextFill(result.getResult().equalsIgnoreCase("Win") ? Color.LIME : Color.RED);

                // Create summary with player details and statistics
                Label summary = new Label(
                        "Name: " + result.getName() +
                                "\nTotal Time: " + result.getTotalPlayTime() +
                                "\nMoves: " + result.getTotalMoves() +
                                "\nTotal Duration: " + String.format("%.2f", result.getTotalMoveDuration()) + "s" +
                                "\nAvg Duration: " + String.format("%.2f", result.getAvgMoveDuration()) + "s"
                );
                summary.setFont(Font.font("Consolas", 16));
                summary.setTextFill(Color.WHITE);
                summary.setAlignment(Pos.CENTER);
                summary.setWrapText(true);

                entryBox.getChildren().addAll(resultText, summary);
                resultsContainer.getChildren().add(entryBox);
            }
        });
    }

    /**
     * @return Button for showing the line chart visualization
     */
    public Button getShowChartButton() {
        return showChartButton;
    }

    /**
     * @return Button for navigating back to the main menu
     */
    public Button getMenuButton() {
        return menuButton;
    }
}
