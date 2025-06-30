package Quixo.View.Leaderboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.List;

/**
 * View class for the Leaderboard screen.
 * Displays player statistics in a tabular format with sorting options.
 * Extends VBox to create a vertical layout for the leaderboard components.
 */
public class LeaderboardView extends VBox {
    // Container for leaderboard entries
    private VBox content;
    // Button to sort entries by win percentage
    private Button sortWinsButton;
    // Button to sort entries by number of games
    private Button sortTimeButton;
    // Button to navigate back to previous screen
    private Button backButton;

    /**
     * Constructor for LeaderboardView.
     * Initializes and lays out all UI components.
     */
    public LeaderboardView() {
        initializeNodes();
        layoutNodes();
    }

    /**
     * Initialize all UI components.
     * Creates buttons and content container.
     */
    private void initializeNodes() {
        // Create content container with 5px spacing between elements
        content = new VBox(5);
        // Create buttons with descriptive labels
        sortWinsButton = new Button("Sort by Win %");
        sortTimeButton = new Button("Sort by Game");
        backButton = new Button("Back");
    }

    /**
     * Layout all UI components and apply styling.
     * Sets up the structure of the leaderboard view.
     */
    private void layoutNodes() {
        // Apply root style class and padding
        this.getStyleClass().add("leaderboard-root");
        this.setPadding(new Insets(20));

        // Create and style the header text
        Text header = new Text("LEADERBOARD");
        header.getStyleClass().add("leaderboard-header");

        // Reinitialize content container with spacing
        content = new VBox(5);
        content.getStyleClass().add("leaderboard-content");

        // Create scrollable container for leaderboard entries
        ScrollPane scroll = new ScrollPane(content);
        scroll.getStyleClass().add("leaderboard-scroll");
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Style buttons with appropriate CSS classes
        sortWinsButton.getStyleClass().addAll("leaderboard-button", "sort-wins-button");
        sortTimeButton.getStyleClass().addAll("leaderboard-button", "sort-time-button");
        backButton.getStyleClass().addAll("leaderboard-button", "back-button");

        // Create horizontal container for buttons with spacing
        HBox controls = new HBox(20, sortWinsButton, sortTimeButton, backButton);
        controls.getStyleClass().add("button-container");
        controls.setAlignment(Pos.CENTER);

        // Create main container with all components
        VBox container = new VBox(20, header, scroll, controls);
        container.getStyleClass().add("main-container");
        container.setAlignment(Pos.TOP_CENTER);

        // Add container to the view and apply CSS stylesheet
        getChildren().add(container);
        this.getStylesheets().add(getClass().getResource("/CSS/leaderboard.css").toExternalForm());
    }

    /**
     * Update the leaderboard with new data.
     * Clears existing entries and adds new ones based on the provided data.
     *
     * @param entries List of string entries, each representing a row in the leaderboard
     */
    public void updateLeaderboard(List<String> entries) {
        // Clear existing content
        content.getChildren().clear();
        // Add header row
        content.getChildren().add(createHeaderRow());

        // Check if there are any entries
        if (entries.isEmpty()) {
            // Display message when no data is available
            Text noData = new Text("No leaderboard data available.");
            noData.getStyleClass().add("no-data-text");
            content.getChildren().add(noData);
        } else {
            // Add each entry as a row in the table
            for (String entry : entries) {
                // Split entry by pipe character and create a row
                content.getChildren().add(createTableRow(entry.split("\\|")));
            }
        }
    }

    /**
     * Create the header row for the leaderboard table.
     * 
     * @return HBox containing the header cells
     */
    private HBox createHeaderRow() {
        // Create row container with spacing
        HBox row = new HBox(20);
        row.getStyleClass().add("header-row");
        row.setPadding(new Insets(10, 5, 10, 5));

        // Define column headers and their widths
        String[] columns = {"Rank", "Player", "Games", "Wins", "Losses", "Win %"};
        double[] widths = {50, 150, 60, 60, 60, 80};

        // Create and add each header cell
        for (int i = 0; i < columns.length; i++) {
            Text text = new Text(columns[i]);
            text.getStyleClass().add("header-text");
            text.setWrappingWidth(widths[i]);
            row.getChildren().add(text);
        }

        return row;
    }

    /**
     * Create a data row for the leaderboard table.
     * 
     * @param columns Array of string values for each column
     * @return HBox containing the data cells
     */
    private HBox createTableRow(String... columns) {
        // Create row container with spacing
        HBox row = new HBox(20);
        row.getStyleClass().add("table-row");
        row.setPadding(new Insets(5));

        // Define column widths (note: includes extra width for potential additional column)
        double[] widths = {50, 150, 60, 60, 60, 80, 100};

        // Create and add each data cell
        for (int i = 0; i < columns.length; i++) {
            Text text = new Text(columns[i]);
            text.getStyleClass().add("table-text");
            text.setWrappingWidth(widths[i]);
            row.getChildren().add(text);
        }

        return row;
    }

    /**
     * Get the sort by wins button.
     * @return Button for sorting by win percentage
     */
    public Button getSortWinsButton() { return sortWinsButton; }

    /**
     * Get the sort by games button.
     * @return Button for sorting by number of games
     */
    public Button getSortTimeButton() { return sortTimeButton; }

    /**
     * Get the back navigation button.
     * @return Button for navigating back
     */
    public Button getBackButton() { return backButton; }
}
