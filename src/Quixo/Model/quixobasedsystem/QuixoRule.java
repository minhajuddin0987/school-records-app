package Quixo.Model.quixobasedsystem;

import Quixo.Model.GameLogic;
import Quixo.Model.ComputerPlayer;

import java.util.Optional;

/**
 * Interface defining the contract for all AI rule implementations in the Quixo game.
 * Each rule represents a different strategic approach or heuristic that the AI can use
 * to evaluate and select moves. Rules are evaluated in order of priority by the InferenceEngine.
 */
public interface QuixoRule {
    /**
     * Evaluates the current game state and returns a scored move based on the rule's strategy.
     * 
     * @param gameLogic The current game state to evaluate
     * @param aiSymbol The symbol ('X' or 'O') that the AI player is using
     * @return An Optional containing a ScoredMove if this rule can suggest a move,
     *         or an empty Optional if no suitable move is found by this rule
     */
    Optional<ScoredMove> getScoredMove(GameLogic gameLogic, char aiSymbol);
}
