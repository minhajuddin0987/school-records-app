package Quixo.Model;

import Quixo.AppConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The MoveDuration class is responsible for tracking and recording the duration of moves in a game.
 * It provides functionality to measure the time between moves and store this information in the database.
 * This data can be used for analytics and performance tracking of players.
 */
public class MoveDuration {
    /**
     * Stores the timestamp when the last move ended.
     * A value of -1 indicates that no previous move has been recorded yet.
     */
    private static long lastMoveEndTime = -1;

    /**
     * Marks the start of a move.
     * Currently this is a placeholder method and doesn't perform any operations.
     * It may be used in future implementations to add more precise timing functionality.
     */
    public static void startMove() {
        // Just a marker method, no-op now
    }

    /**
     * Records the duration of a move in the database.
     * This method calculates the time elapsed since the last move ended,
     * and stores this information along with the game ID and move number.
     *
     * @param gameId     The unique identifier of the game
     * @param moveNumber The sequential number of the move within the game
     */
    public static void recordMove(int gameId, int moveNumber) {
        long currentTime = System.currentTimeMillis();
        double durationSeconds = (lastMoveEndTime != -1) ? (currentTime - lastMoveEndTime) / 1000.0 : 0;
        lastMoveEndTime = currentTime;

        if (AppConfig.USE_DATABASE) {
            try (Connection conn = DbConnect.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO moves (game_id, move_number, duration) VALUES (?, ?, ?)")) {
                stmt.setInt(1, gameId);
                stmt.setInt(2, moveNumber);
                stmt.setDouble(3, durationSeconds);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println(" Error recording move duration: " + e.getMessage());
            }
        } else {
            InMemoryStorage.moves.add(new InMemoryStorage.MoveRecord(
                    gameId, moveNumber, "HUMAN", 'X', durationSeconds));
            System.out.println("Recorded offline move #" + moveNumber + ": " + durationSeconds + "s");
        }
    }


    /**
     * Resets the move duration timer.
     * This should be called when starting a new game or when timing needs to be reinitialized.
     * After calling this method, the next call to recordMove will not calculate a duration
     * (it will record 0) since there is no previous move to measure against.
     */
    public static void reset() {
        lastMoveEndTime = -1;
    }
}
