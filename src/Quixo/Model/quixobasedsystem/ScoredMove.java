package Quixo.Model.quixobasedsystem;

import Quixo.Model.ComputerPlayer;

/**
 * A data class that associates a move with a score for AI decision making.
 * Used by the InferenceEngine and rule implementations to evaluate and compare
 * different possible moves based on their strategic value.
 */
public class ScoredMove {
    /**
     * The move to be made (position and direction)
     */
    public final ComputerPlayer.Move move;

    /**
     * The score assigned to this move, with higher values indicating better moves.
     * Different AI rules assign different scores based on their strategic priorities:
     * - Winning moves: 1000
     * - Blocking opponent's winning moves: 900
     * - Creating dual threats: 800
     * - Controlling center positions: 700
     */
    public final int score;

    /**
     * Creates a new ScoredMove with the specified move and score.
     *
     * @param move The move to be made
     * @param score The strategic value of this move
     */
    public ScoredMove(ComputerPlayer.Move move, int score) {
        this.move = move;
        this.score = score;
    }
}
