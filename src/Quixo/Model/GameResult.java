package Quixo.Model;

/**
 * GameResult class represents the outcome and statistics of a completed game.
 * It stores information about the player, game duration, moves made, and final result.
 */
public class GameResult {
    /** Player's name */
    private String name;
    /** Total time spent playing the game */
    private String totalPlayTime;
    /** Total number of moves made during the game */
    private int totalMoves;
    /** Total duration of all moves in seconds */
    private double totalMoveDuration;
    /** Average duration per move in seconds */
    private double avgMoveDuration;
    /** Result of the game (win/loss/draw) */
    private String result;
    /**
     * Constructor for creating a new GameResult object.
     * 
     * @param name Player's name
     * @param totalPlayTime Total time spent playing the game
     * @param totalMoves Total number of moves made during the game
     * @param totalMoveDuration Total duration of all moves in seconds
     * @param avgMoveDuration Average duration per move in seconds
     * @param result Result of the game (win/loss/draw)
     */
    public GameResult(String name, String totalPlayTime, int totalMoves, double
            totalMoveDuration, double avgMoveDuration, String result) {

        this.name = name;
        this.totalPlayTime = totalPlayTime;
        this.totalMoves = totalMoves;
        this.totalMoveDuration = totalMoveDuration;
        this.avgMoveDuration = avgMoveDuration;
        this.result = result;

    }
    /**
     * Gets the player's name.
     * @return The player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the total time spent playing the game.
     * @return The total play time
     */
    public String getTotalPlayTime() {
        return totalPlayTime;
    }

    /**
     * Gets the total number of moves made during the game.
     * @return The total number of moves
     */
    public int getTotalMoves() {
        return totalMoves;
    }

    /**
     * Gets the total duration of all moves in seconds.
     * @return The total move duration in seconds
     */
    public double getTotalMoveDuration() {
        return totalMoveDuration;
    }

    /**
     * Gets the average duration per move in seconds.
     * @return The average move duration in seconds
     */
    public double getAvgMoveDuration() {
        return avgMoveDuration;
    }

    /**
     * Gets the result of the game (win/loss/draw).
     * @return The game result
     */
    public String getResult() {
        return result;
    }
    /**
     * Returns a string representation of the GameResult object.
     * The string includes player name, total play time, number of moves,
     * total move duration, average move duration, and game result.
     * 
     * @return A formatted string representation of the game result
     */
    @Override
    public String toString() {
        return String.format("Name: %s | Total Time: %s | Moves: %d | Total Duration: %.2fs | Avg Duration: %.2fs | Result: %s",
                name, totalPlayTime, totalMoves, totalMoveDuration, avgMoveDuration, result);
    }

}
