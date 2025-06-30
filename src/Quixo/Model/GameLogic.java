package Quixo.Model;

import Quixo.AppConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * GameLogic class manages the core game mechanics of Quixo.
 * It handles player turns, move validation, win condition checking,
 * and game state persistence to the database.
 */
public class GameLogic {
    /** The game board containing the 5x5 grid of cubes */
    private Board board;
    /** The first player (usually human player) */
    private Player player1;
    /** The second player (usually AI/computer player) */
    private Player player2;
    /** Reference to the player whose turn it currently is */
    private Player currentPlayer;
    /** Database ID for the current game, or 0 if not yet saved */
    private int gameId = 0;
    /** Flag indicating whether the game has been completed */
    private boolean gameCompleted = false;
    /** Counter tracking the number of moves made in the game */
    private int moveNumber = 0;

    /**
     * Constructor for creating a new game with a human player and an AI opponent.
     * 
     * @param player The human player (player1)
     * @param aiSymbol The symbol to be used by the AI player
     */
    public GameLogic(Player player, char aiSymbol) {
        board = new Board();
        this.player1 = player;
        this.player2 = new ComputerPlayer("2", aiSymbol);
        this.currentPlayer = player1;
    }

    /**
     * Attempts to make a move on the board for the current player.
     * Validates that the move is legal according to Quixo rules.
     * 
     * @param row The row coordinate of the cube to move (0-4)
     * @param col The column coordinate of the cube to move (0-4)
     * @param direction The direction to push the cube ("UP", "DOWN", "LEFT", "RIGHT")
     * @return true if the move was successful
     * @throws InvalidMoveException if the move violates game rules
     */
    public boolean makeMove(int row, int col, String direction) throws InvalidMoveException {
        // Validate that the selected cube is on the edge of the board
        if (!isEdge(row, col)) {
            throw new InvalidMoveException("You must pick a cube from the edge!");
        }

        Cube cube = board.getCube(row, col);
        char cubeSymbol = cube.getSymbol();
        char playerSymbol = currentPlayer.getSymbol();

        // Validate that the player is not trying to move the opponent's cube
        if (cubeSymbol != ' ' && cubeSymbol != playerSymbol) {
            throw new InvalidMoveException("You can't move your opponent's cube!");
        }

        // Attempt to push the cube in the specified direction
        boolean moved = board.pushCube(row, col, direction, playerSymbol);
        if (!moved) {
            throw new InvalidMoveException("You can't push in that direction!");
        }

        // Increment move counter after successful move
        moveNumber++;
        return true;
    }

    /**
     * Creates a deep copy of the current game state.
     * Useful for AI move evaluation without modifying the actual game.
     * 
     * @return A new GameLogic instance with the same state as this one
     */
    public GameLogic deepCopy() {
        GameLogic copy = new GameLogic(this.player1, this.player2.getSymbol());
        copy.board.copyFrom(this.board);
        copy.currentPlayer = this.currentPlayer;
        return copy;
    }

    /**
     * Checks if the current player has won the game.
     * 
     * @return true if the current player has formed a winning line
     */
    public boolean checkWin() {
        return board.checkWin(currentPlayer.getSymbol());
    }

    /**
     * Determines which directions are valid for pushing a cube at the specified position.
     * A direction is valid if it's opposite to an edge the cube is on.
     * 
     * @param row The row coordinate of the cube (0-4)
     * @param col The column coordinate of the cube (0-4)
     * @return List of valid directions ("UP", "DOWN", "LEFT", "RIGHT")
     */
    public List<String> getValidDirections(int row, int col) {
        List<String> validDirections = new ArrayList<>();

        // Only edge cubes can be moved
        if (!isEdge(row, col)) {
            return validDirections;
        }

        char currentSymbol = board.getCube(row, col).getSymbol();

        // Can only move blank cubes or the current player's cubes
        if (currentSymbol != ' ' && currentSymbol != getCurrentPlayer().getSymbol()) {
            return validDirections;
        }

        // Add valid directions based on which edge(s) the cube is on
        if (row == 0) validDirections.add("DOWN");
        if (row == 4) validDirections.add("UP");
        if (col == 0) validDirections.add("RIGHT");
        if (col == 4) validDirections.add("LEFT");

        return validDirections;
    }

    /**
     * Counts the number of potential winning lines for a given player symbol.
     * A potential winning line is one where the player has at least 3 symbols
     * and the rest of the positions are empty (can be filled later).
     * 
     * @param symbol The player's symbol to check for
     * @return The count of potential winning lines
     */
    public int countPotentialWins(char symbol) {
        int count = 0;

        // Check rows for potential wins
        for (int i = 0; i < 5; i++) {
            int symbolCount = 0, emptyCount = 0;
            for (int j = 0; j < 5; j++) {
                char c = board.getCube(i, j).getSymbol();
                if (c == symbol) symbolCount++;
                else if (c == ' ') emptyCount++;
            }
            // A potential win requires at least 3 symbols and no opponent symbols
            if (symbolCount + emptyCount == 5 && symbolCount >= 3) count++;
        }

        // Check columns for potential wins
        for (int j = 0; j < 5; j++) {
            int symbolCount = 0, emptyCount = 0;
            for (int i = 0; i < 5; i++) {
                char c = board.getCube(i, j).getSymbol();
                if (c == symbol) symbolCount++;
                else if (c == ' ') emptyCount++;
            }
            if (symbolCount + emptyCount == 5 && symbolCount >= 3) count++;
        }

        // Check both diagonals for potential wins
        int diag1 = 0, empty1 = 0, diag2 = 0, empty2 = 0;
        for (int i = 0; i < 5; i++) {
            char c1 = board.getCube(i, i).getSymbol();                // Main diagonal (top-left to bottom-right)
            char c2 = board.getCube(i, 4 - i).getSymbol();            // Other diagonal (top-right to bottom-left)
            if (c1 == symbol) diag1++;
            else if (c1 == ' ') empty1++;
            if (c2 == symbol) diag2++;
            else if (c2 == ' ') empty2++;
        }
        if (diag1 + empty1 == 5 && diag1 >= 3) count++;
        if (diag2 + empty2 == 5 && diag2 >= 3) count++;

        return count;
    }

    /**
     * Records the game result in the database and updates player statistics.
     * This method is called when a game ends (either by a win or a draw).
     * 
     * @param winner The player who won the game, or null if it's a draw
     */
    public void recordGameResult(Player winner) {
        if (gameCompleted || gameId <= 0) return;

        if (AppConfig.USE_DATABASE) {
            try (Connection conn = DbConnect.getConnection()) {
                conn.setAutoCommit(false);
                try {
                    // Update games table
                    String gameSql = "UPDATE games SET end_time = CURRENT_TIMESTAMP, winner = ? WHERE game_id = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(gameSql)) {
                        boolean humanWon = winner != null && !(winner instanceof ComputerPlayer);
                        pstmt.setObject(1, humanWon ? Boolean.TRUE : Boolean.FALSE, Types.BOOLEAN);
                        pstmt.setInt(2, gameId);
                        pstmt.executeUpdate();
                    }

                    if (!(player1 instanceof ComputerPlayer)) {
                        updatePlayerStats(conn, winner);
                    }

                    conn.commit();
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
            } catch (SQLException e) {
                System.err.println("[GameLogic] Error recording game result: " + e.getMessage());
            }
        } else {
            // Fallback offline version
            for (InMemoryStorage.GameRecord game : InMemoryStorage.games) {
                if (game.gameId == gameId) {
                    game.endTime = new Date();
                    game.winner = winner != null && !(winner instanceof ComputerPlayer);
                }
            }

            if (!(player1 instanceof ComputerPlayer)) {
                updatePlayerStatsInMemory(winner);
            }
        }

        gameCompleted = true;
    }
    private void updatePlayerStatsInMemory(Player winner) {
        Player humanPlayer = getPlayer1();

        // Ensure the player exists in memory
        InMemoryStorage.players.putIfAbsent(humanPlayer.getPlayerId(), humanPlayer);

        // Simulate stats tracking — optional: store actual win/loss counters in a player wrapper if needed
        System.out.println("[Offline] Updating stats for " + humanPlayer.getPlayerId());

        if (winner == null || !winner.equals(humanPlayer)) {
            System.out.println("[Offline] Recorded a LOSS or DRAW");
        } else {
            System.out.println("[Offline] Recorded a WIN");
        }
    }



    /**
     * Updates the statistics for the human player in the database.
     * Increments total games played and either wins or losses based on the game result.
     * 
     * @param conn Active database connection with transaction started
     * @param winner The player who won the game, or null if it's a draw
     * @throws SQLException If a database error occurs
     */
    private void updatePlayerStats(Connection conn, Player winner) throws SQLException {
        Player humanPlayer = getPlayer1();

        // Always increment total_games counter
        String updateTotal = "UPDATE players SET total_games = total_games + 1 WHERE player_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateTotal)) {
            pstmt.setString(1, humanPlayer.getPlayerId());
            pstmt.executeUpdate();
        }

        // Update wins or losses based on game outcome
        if (winner == null || !winner.equals(humanPlayer)) {
            // Player lost or game was a draw
            String updateLoss = "UPDATE players SET games_loss = games_loss + 1 WHERE player_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateLoss)) {
                pstmt.setString(1, humanPlayer.getPlayerId());
                pstmt.executeUpdate();
            }
        } else {
            // Player won
            String updateWin = "UPDATE players SET games_won = games_won + 1 WHERE player_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateWin)) {
                pstmt.setString(1, humanPlayer.getPlayerId());
                pstmt.executeUpdate();
            }
        }
    }

    /**
     * Creates a new game record in the database.
     * This should be called when a new game starts.
     */
    public void initializeGameInDatabase() {
        if (!(player1 instanceof ComputerPlayer)) {
            if (AppConfig.USE_DATABASE) {

                // Only record games with human players
                gameId = DbConnect.insertGame(player1.getPlayerId());
            } else {
                gameId = InMemoryStorage.games.size() + 1;
                InMemoryStorage.games.add(new InMemoryStorage.GameRecord(gameId, player1.getPlayerId()));
            }
        } else {
            // Mark as not recorded in database
            gameId = -1;
        }
    }

    /**
     * Gets the database ID for this game.
     * 
     * @return The game ID, or 0/negative if not saved in database
     */
    public int getGameId() {
        return this.gameId;
    }

    /**
     * Gets the current move number in the game.
     * 
     * @return The number of moves made so far
     */
    public int getMoveNumber() {
        return moveNumber;
    }

    /**
     * Gets the first player (usually human player).
     * 
     * @return Player 1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Gets the second player (usually AI player).
     * 
     * @return Player 2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Gets the game board.
     * 
     * @return The current board state
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the player whose turn it currently is.
     * 
     * @return The current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Switches the current player to the other player.
     * Should be called after a player completes their move.
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    /**
     * Checks if a position is on the edge of the board.
     * In Quixo, only edge cubes can be moved.
     * 
     * @param row The row coordinate to check (0-4)
     * @param col The column coordinate to check (0-4)
     * @return true if the position is on any edge of the board
     */
    public boolean isEdge(int row, int col) {
        return row == 0 || row == 4 || col == 0 || col == 4;
    }
}
