package Quixo.Model.quixobasedsystem;

import Quixo.Model.GameLogic;
import Quixo.Model.ComputerPlayer;
import Quixo.Model.quixobasedsystem.rules.*;

import java.util.*;

/**
 * The InferenceEngine class is responsible for determining the best move for the AI player
 * in the Quixo game. It uses a rule-based system where different AI strategies are evaluated
 * in order of priority, and the highest-scoring move is selected.
 */
public class InferenceEngine {
    /**
     * List of AI rules/strategies in order of priority
     */
    private final List<QuixoRule> rules = new ArrayList<>();

    /**
     * Random number generator for selecting random moves when needed
     */
    private final Random random = new Random();

    /**
     * Constructor that initializes the AI rules in order of priority:
     * 1. Winning position - try to win the game immediately
     * 2. Block opponent - prevent opponent from winning
     * 3. Create dual threats - create multiple winning opportunities
     * 4. Control center - prioritize center positions for strategic advantage
     */
    public InferenceEngine() {
        rules.add(new QuixoWinningPositionAI());
        rules.add(new QuixoBlockOpponentAI());
        rules.add(new QuixoCreateDualThreatsAI());
        rules.add(new QuixoCenterControlAI());
    }

    /**
     * Determines the best move for the AI player based on the current game state.
     * Evaluates each rule in order of priority and selects the highest-scoring move.
     * If no rule produces a valid move, falls back to a random valid move.
     *
     * @param game The current game state
     * @param aiSymbol The symbol (X or O) that the AI player is using
     * @return The best move for the AI to make
     */
    public ComputerPlayer.Move decideMove(GameLogic game, char aiSymbol) {
        ScoredMove bestMove = null;

        // Evaluate each rule and keep track of the highest-scoring move
        for (QuixoRule rule : rules) {
            Optional<ScoredMove> result = rule.getScoredMove(game, aiSymbol);
            if (result.isPresent()) {
                ScoredMove move = result.get();
                if (bestMove == null || move.score > bestMove.score) {
                    bestMove = move;
                }
            }
        }

        // Return the best move if found, otherwise get a random valid move
        return bestMove != null ? bestMove.move : getRandomValidMove(game, aiSymbol);
    }

    /**
     * Fallback method to find a random valid move when no strategic move is available.
     * This ensures the AI can always make a move as long as valid moves exist.
     * 
     * @param game The current game state
     * @param aiSymbol The symbol (X or O) that the AI player is using
     * @return A random valid move, or null if no valid moves exist
     */
    private ComputerPlayer.Move getRandomValidMove(GameLogic game, char aiSymbol) {
        // Get all possible edge positions (only edge pieces can be moved in Quixo)
        List<ComputerPlayer.EdgePosition> edges = ComputerPlayer.getAllEdgePositions();
        // Shuffle to ensure randomness
        Collections.shuffle(edges);

        // Try each edge position until we find one with valid directions
        for (ComputerPlayer.EdgePosition edge : edges) {
            List<String> dirs = game.getValidDirections(edge.row, edge.col);
            if (!dirs.isEmpty()) {
                // Return a move with a random valid direction
                return new ComputerPlayer.Move(edge.row, edge.col, dirs.get(random.nextInt(dirs.size())));
            }
        }
        // No valid moves found (should not happen in a normal game)
        return null;
    }
}
