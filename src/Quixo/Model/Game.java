package Quixo.Model;

/**
 * A simple game state tracking class for the Quixo game.
 * This class maintains basic information about the current game session.
 */
public class Game {
    /** ID of the player whose turn it currently is */
    private int currentPlayerId;

    /** Flag indicating whether the game has been started */
    private boolean gameStarted;

    /**
     * Creates a new game instance with the specified game ID.
     * Initializes the game with player 1 as the current player and sets
     * the game status to not started.
     *
     * @param gameId The unique identifier for this game
     */
    public Game(int gameId) {
        this.currentPlayerId = 1;
        this.gameStarted = false;
    }

}
