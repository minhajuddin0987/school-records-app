package Quixo.Model;

/**
 * Represents a player in the Quixo game.
 * Each player has a unique ID and a symbol ('X' or 'O') that represents their pieces on the game board.
 */
public class Player {
    /**
     * The unique identifier for the player. Must contain only alphanumeric characters.
     */
    private final String playerId;

    /**
     * The symbol representing the player's pieces on the game board.
     * Must be either 'X' or 'O'.
     */
    private final char symbol;

    /**
     * Creates a new Player with the specified ID and symbol.
     *
     * @param playerId The player's unique identifier (alphanumeric characters only)
     * @param symbol The player's symbol ('X' or 'O')
     * @throws IllegalArgumentException If the playerId contains non-alphanumeric characters
     *                                  or if the symbol is not 'X' or 'O'
     */
    public Player(String playerId, char symbol) {
        // Validate that player ID contains only alphanumeric characters
        if (!playerId.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Invalid player ID format");
        }

        // Validate that symbol is either 'X' or 'O'
        if (symbol != 'X' && symbol != 'O') {
            throw new IllegalArgumentException("Symbol must be either 'X' or 'O'");
        }
        this.playerId = playerId;
        this.symbol = symbol;
    }

    /**
     * Returns the player's ID.
     *
     * @return The player's unique identifier
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Returns the player's symbol.
     *
     * @return The player's symbol ('X' or 'O')
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Returns a string representation of the Player object.
     *
     * @return A string containing the player's ID and symbol
     */
    @Override
    public String toString() {
        return "Player{" +
                "playerId='" + playerId + '\'' +
                ", symbol=" + symbol +
                '}';
    }
}
