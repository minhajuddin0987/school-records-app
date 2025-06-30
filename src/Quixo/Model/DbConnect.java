package Quixo.Model;


import Quixo.AppConfig;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database connection manager for the Quixo game.
 * This class handles all database operations including connection establishment,
 * table creation, and game data persistence.
 */
public class DbConnect {
    /** Database connection URL */
    private static final String DB_URL = "jdbc:postgresql://10.134.178.30:5432/game";
    /** Database username */
    private static final String DB_USER = "game";
    /** Database password */
    private static final String DB_PASSWORD = "7sur7";
    /** Flag to track if database has been initialized */
    private static boolean initialized = false;

    /**
     * Establishes and returns a connection to the database.
     * If this is the first connection, it also initializes the database tables.
     * 
     * @return Connection object to the database
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {

        if (!AppConfig.USE_DATABASE) {
            throw new RuntimeException("Database is not enabled");
        }
            try {
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                if (!initialized) {
                    initializeDatabase(conn);
                    initialized = true;
                }
                return conn;
            } catch (SQLException e) {
                System.err.println("Database connection failed!");
                e.printStackTrace();
                throw e;
            }
        }


    /**
     * Initializes the database by creating necessary tables if they don't exist.
     * 
     * @param conn Active database connection
     * @throws RuntimeException if database initialization fails
     */
    private static void initializeDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            if (!tablesExist(stmt)) {
                createTables(stmt);
            } else {
                // Tables already exist, no action needed
            }

        } catch (SQLException e) {
            System.err.println("Database initialization failed!");
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database", e);
        }
    }


    /**
     * Checks if the required database tables already exist.
     * This method checks for the existence of the 'players' table as an indicator.
     * 
     * @param stmt SQL statement object to execute queries
     * @return true if tables exist, false otherwise
     * @throws SQLException if the query fails
     */
    private static boolean tablesExist(Statement stmt) throws SQLException {
        String checkPlayers = "SELECT EXISTS (SELECT FROM information_schema.tables " +
                "WHERE table_name = 'players')";

        try (ResultSet rs = stmt.executeQuery(checkPlayers)) {
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        }
        return false;
    }


    /**
     * Creates the necessary database tables for the game.
     * This includes:
     * - players: Stores player information and statistics
     * - games: Tracks game sessions between players
     * - moves: Records individual moves within games
     *
     * @param stmt SQL statement object to execute table creation
     * @throws SQLException if table creation fails
     */
    private static void createTables(Statement stmt) throws SQLException {
        // Players table - stores user accounts and game statistics
        String playersTable = "CREATE TABLE players (" +
                "player_id VARCHAR(50) PRIMARY KEY, " +  // Unique identifier for each player
                "name VARCHAR(50) NOT NULL, " +          // Player's display name
                "password VARCHAR(255) NOT NULL, " +     // Hashed password for authentication
                "symbol CHAR(1) NOT NULL CHECK (symbol IN ('X', 'O')), " + // Player's game symbol (X or O)
                "total_games INTEGER DEFAULT 0, " +      // Total number of games played
                "games_won INTEGER DEFAULT 0, " +        // Number of games won
                "games_loss INTEGER DEFAULT 0)";         // Number of games lost

        // Games table - tracks individual game sessions
        String gamesTable = "CREATE TABLE games (" +
                "game_id SERIAL PRIMARY KEY, " +         // Unique identifier for each game
                "player_id VARCHAR(50) NOT NULL REFERENCES players(player_id), " + // Human player (foreign key)
                "start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +                // When game started
                "end_time TIMESTAMP, " +                                            // When game ended
                "winner BOOLEAN)";                // TRUE if human won, FALSE if AI won, NULL if not finished

        // Moves table - records individual moves within a game
        String movesTable = "CREATE TABLE moves (" +
                "move_id SERIAL PRIMARY KEY, " +         // Unique identifier for each move
                "game_id INTEGER NOT NULL REFERENCES games(game_id), " +  // Game this move belongs to
                "player_id VARCHAR(50) NOT NULL REFERENCES players(player_id), " +  // Player who made the move
                "value CHAR(1) CHECK (value IN ('X', 'O')), " +  // Symbol placed (X or O)
                "duration DOUBLE PRECISION)";            // Time taken to make the move


        executeTableCreation(stmt, playersTable, "players");
        executeTableCreation(stmt, gamesTable, "games");
        executeTableCreation(stmt, movesTable, "moves");
    }
    /**
     * Creates a new game record in the database.
     * 
     * @param playerId The ID of the player starting the game
     * @return The generated game_id if successful, -1 if failed
     */
    public static int insertGame(String playerId) {
        String sql = "INSERT INTO games (player_id) VALUES (?) RETURNING game_id";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, playerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("game_id");
            }
        } catch (SQLException e) {
            System.err.println("[DbConnect] Failed to insert game: " + e.getMessage());
        }
        return -1;
    }


    /**
     * Executes a SQL statement to create a database table.
     * This helper method handles the execution and error reporting for table creation.
     * 
     * @param stmt SQL statement object to execute the creation
     * @param sql The SQL CREATE TABLE statement to execute
     * @param tableName The name of the table being created (for logging purposes)
     * @throws SQLException if table creation fails
     */
    private static void executeTableCreation(Statement stmt, String sql, String tableName) throws SQLException {
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
