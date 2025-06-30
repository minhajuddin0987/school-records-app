package Quixo.Model;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles retrieving game results from the database.
 * It provides functionality to fetch and process game statistics.
 */
public class GameResults {
    /**
     * Retrieves game results for a specific game from the database.
     * 
     * @param gameId The ID of the game to retrieve results for
     * @return A list of GameResult objects containing player statistics
     */
    public static List<GameResult> getGameResults(int gameId) {
        List<GameResult> results = new ArrayList<>();
        // Validate game ID
        if (gameId <= 0) {
            System.out.println("Invalid game ID: " + gameId);
            return results;
        }

        // SQL query to retrieve game statistics from the database
        String sql = """
            SELECT 
                p.player_id AS name,
                g.start_time,
                g.end_time,
                COUNT(m.move_id) AS total_moves,
                COALESCE(SUM(m.duration), 0) AS total_move_duration,
                COALESCE(AVG(m.duration), 0) AS average_move_duration,
                CASE 
                    WHEN g.winner = TRUE THEN 'Win'
                    WHEN g.winner = FALSE THEN 'Loss'
                    ELSE 'In Progress'
                END AS game_result
            FROM games g
            JOIN players p ON g.player_id = p.player_id
            LEFT JOIN moves m ON g.game_id = m.game_id
            WHERE g.game_id = ?
            GROUP BY p.player_id, g.start_time, g.end_time, g.winner
        """;

        // Establish database connection and execute the query
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the game ID parameter in the prepared statement
            pstmt.setInt(1, gameId);
            ResultSet rs = pstmt.executeQuery();

            // Process each row in the result set
            while (rs.next()) {
                String name = rs.getString("name");
                Timestamp start = rs.getTimestamp("start_time");
                Timestamp end = rs.getTimestamp("end_time");

                // Calculate total play time if the game has ended
                String totalPlayTime = "Not Ended";
                if (start != null && end != null) {
                    // Calculate duration between start and end times
                    Duration duration = Duration.between(start.toLocalDateTime(), end.toLocalDateTime());
                    // Format duration as minutes:seconds
                    totalPlayTime = String.format("%02d:%02d", duration.toMinutes(), duration.getSeconds() % 60);
                }

                // Create a new GameResult object and add it to the results list
                results.add(new GameResult(
                        name,
                        totalPlayTime,
                        rs.getInt("total_moves"),
                        rs.getDouble("total_move_duration"),
                        rs.getDouble("average_move_duration"),
                        rs.getString("game_result")
                ));
            }

        } catch (SQLException e) {
            // Handle any SQL exceptions that occur during database operations
            System.out.println("Error fetching game result: " + e.getMessage());
            e.printStackTrace();
        }

        // Return the list of game results (may be empty if an error occurred)
        return results;
    }
}
