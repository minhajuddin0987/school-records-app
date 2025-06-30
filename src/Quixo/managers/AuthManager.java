package Quixo.managers;

import Quixo.Model.DbConnect;
import Quixo.Model.Player;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * AuthManager handles all authentication-related operations for the Quixo game.
 * This includes user login, registration, and player profile management.
 * The class provides secure password handling through hashing.
 */
public class AuthManager {
    /**
     * Authenticates a player with their ID and password.
     * 
     * @param playerId The unique identifier for the player
     * @param password The player's password (will be hashed before comparison)
     * @return Player object if authentication is successful
     * @throws IllegalArgumentException If credentials are empty or invalid
     * @throws RuntimeException If a database error occurs
     */
    public Player login(String playerId, String password) {
        if (playerId == null || playerId.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Player ID and password cannot be empty");
        }

        // SQL query to find a player with matching ID and password
        String sql = "SELECT player_id, symbol FROM players WHERE player_id = ? AND password = ?";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set query parameters
            stmt.setString(1, playerId);
            stmt.setString(2, hashPassword(password)); // Hash the password before comparison

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Player found, create and return a Player object
                    return new Player(
                            rs.getString("player_id"),
                            rs.getString("symbol").charAt(0)
                    );
                } else {
                    // No matching player found with the given credentials
                    System.out.println("Login failed - no matching user");
                    throw new IllegalArgumentException("Invalid username or password");
                }
            }
        } catch (SQLException e) {
            // Log detailed database error information for troubleshooting
            System.err.println("Database error during login:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            throw new RuntimeException("Login service unavailable", e);
        }
    }

    /**
     * Registers a new player in the system.
     * 
     * @param playerId The unique identifier for the new player
     * @param password The player's chosen password (will be hashed before storage)
     * @param confirmPassword Confirmation of the password (must match password)
     * @return Player object for the newly registered player
     * @throws IllegalArgumentException If inputs are invalid or player already exists
     * @throws RuntimeException If registration fails due to database errors
     */
    public Player register(String playerId, String password, String confirmPassword) {
        // Validate inputs
        if (playerId == null || playerId.isEmpty()) {
            throw new IllegalArgumentException("Player ID cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (playerId.length() > 50) {
            throw new IllegalArgumentException("Player ID must be 50 characters or less");
        }

        try (Connection conn = DbConnect.getConnection()) {
            // Disable auto-commit to enable transaction
            conn.setAutoCommit(false);

            try {
                // Check if player exists before attempting to create a new one
                if (playerExists(conn, playerId)) {
                    throw new IllegalArgumentException("Player ID already exists");
                }

                // Insert new player with default 'X' symbol
                String insertSql = "INSERT INTO players (player_id, name, password, symbol) VALUES (?, ?, ?, 'X')";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, playerId);
                    insertStmt.setString(2, playerId); // Use playerId as the default name
                    insertStmt.setString(3, hashPassword(password)); // Store hashed password for security

                    int affectedRows = insertStmt.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("Creating player failed, no rows affected");
                    }

                    // Commit the transaction if everything succeeded
                    conn.commit();
                    return new Player(playerId, 'X');
                }
            } catch (SQLException e) {
                // Roll back the transaction if any error occurred
                conn.rollback();
                throw e;
            } finally {
                // Restore auto-commit mode
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Database error during registration:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            throw new RuntimeException("Registration failed: " + e.getMessage(), e);
        }
    }

    /**
     * Checks if a player with the given ID already exists in the database.
     * 
     * @param conn Active database connection
     * @param playerId The player ID to check
     * @return true if the player exists, false otherwise
     * @throws SQLException If a database error occurs
     */
    private boolean playerExists(Connection conn, String playerId) throws SQLException {
        String checkSql = "SELECT 1 FROM players WHERE player_id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, playerId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Hashes a password using SHA-256 algorithm for secure storage.
     * 
     * @param password The plain text password to hash
     * @return A hexadecimal string representation of the hashed password
     * @throws RuntimeException If the hashing algorithm is not available
     */
    private String hashPassword(String password) {
        try {
            // Use SHA-256 for secure password hashing
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convert password to bytes using UTF-8 encoding and generate hash
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string representation
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                // Convert each byte to a 2-digit hex value
                String hex = Integer.toHexString(0xff & b);
                // Ensure each byte is represented by 2 characters
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }
    /**
     * Updates the game symbol associated with a player.
     * 
     * @param playerId The ID of the player whose symbol will be updated
     * @param symbol The new symbol to assign to the player
     * @throws IllegalArgumentException If the player is not found
     * @throws RuntimeException If a database error occurs
     */
    public void updatePlayerSymbol(String playerId, char symbol) {
        // SQL query to update a player's symbol
        String sql = "UPDATE players SET symbol = ? WHERE player_id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set query parameters
            stmt.setString(1, String.valueOf(symbol)); // Convert char to String
            stmt.setString(2, playerId);

            // Execute the update and check if any rows were affected
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                // No rows updated means the player wasn't found
                throw new IllegalArgumentException("Player not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update symbol", e);
        }
    }
}
