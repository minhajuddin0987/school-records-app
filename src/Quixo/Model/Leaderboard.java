package Quixo.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Leaderboard class handles retrieving and formatting player statistics
 * from the database to display in the game's leaderboard.
 * It provides functionality to sort players by different metrics.
 */
public class Leaderboard {
    /**
     * Retrieves the leaderboard data from the database and formats it as a list of strings.
     * Each entry contains rank, player ID, total games, games won, games lost, and win percentage.
     * 
     * @param sortBy The column to sort the leaderboard by (total_games, games_won, games_loss, or win_percentage)
     * @return A list of formatted strings representing leaderboard entries
     */
    public List<String> getLeaderboard(String sortBy) {
        List<String> entries = new ArrayList<>();

        // Construct SQL query with dynamic sorting column
        // Calculate win percentage in SQL for efficiency
        String sql = """
    SELECT 
        player_id,
        total_games,
        games_won,
        games_loss,
        CASE 
            WHEN total_games > 0 THEN ROUND(games_won * 100.0 / total_games, 1)
            ELSE 0
        END AS win_percentage
    FROM players
    ORDER BY %s DESC
""".formatted(validateSortColumn(sortBy));


        // Connect to database and execute query
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Process results and format each row as a string
            int rank = 1;
            while (rs.next()) {
                String entry = String.format(
                        "%d | %s | %d | %d | %d | %.1f%%",
                        rank++,
                        rs.getString("player_id"),
                        rs.getInt("total_games"),
                        rs.getInt("games_won"),
                        rs.getInt("games_loss"),
                        rs.getDouble("win_percentage")
                );
                entries.add(entry);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching leaderboard: " + e.getMessage());
        }
        return entries;
    }

    /**
     * Validates the sort column parameter to ensure it's one of the allowed values.
     * If an invalid column name is provided, defaults to sorting by win percentage.
     *
     * @param column The column name to validate
     * @return A valid column name for sorting
     */
    private String validateSortColumn(String column) {
        return switch (column) {
            case "total_games", "games_won", "games_loss", "win_percentage" -> column;
            default -> "win_percentage"; // Default to win percentage if invalid column provided
        };
    }
}
