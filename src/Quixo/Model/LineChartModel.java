package Quixo.Model;

import javafx.scene.chart.XYChart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for retrieving and formatting game move data
 * for display in line charts within the application.
 * It interacts with the database to fetch move durations for visualization.
 */
public class LineChartModel {
    // Maximum number of moves to retrieve from the database to prevent excessive data loading
    private static final int MAX_MOVES_TO_FETCH = 1000;

    /**
     * Retrieves the duration of each move in a specific game and formats it
     * for display in an XY chart.
     *
     * @param gameId The unique identifier of the game to retrieve move data for
     * @return A series containing move numbers (x-axis) and their durations in seconds (y-axis)
     */
    public XYChart.Series<Number, Number> getPlayerMoveDurations(int gameId) {
        // Initialize the data series for the chart
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Your Match Statistics");

        // SQL query to retrieve move number and duration for the specified game
        String sql = """
            SELECT move_number, duration
            FROM moves
            WHERE game_id = ?
            ORDER BY move_number ASC
            LIMIT ?
        """;

        try (
            // Establish database connection and prepare the SQL statement
            Connection conn = DbConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // Set query parameters
            stmt.setInt(1, gameId);
            stmt.setInt(2, MAX_MOVES_TO_FETCH);

            // Execute the query and process results
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int moveNumber = rs.getInt("move_number");
                    double duration = rs.getDouble("duration");
                    // Add each data point to the series
                    series.getData().add(new XYChart.Data<>(moveNumber, duration));
                }
            }

        } catch (SQLException e) {
            // Handle database errors
            System.err.println("Error fetching move durations: " + e.getMessage());
            e.printStackTrace();
        }

        return series;
    }
}
