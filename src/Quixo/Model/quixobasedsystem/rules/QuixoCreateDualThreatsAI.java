package Quixo.Model.quixobasedsystem.rules;

import Quixo.Model.GameLogic;
import Quixo.Model.ComputerPlayer;
import Quixo.Model.quixobasedsystem.QuixoRule;
import Quixo.Model.quixobasedsystem.ScoredMove;

import java.util.Optional;

/**
 * AI implementation that focuses on creating multiple threats on the board.
 * This strategy aims to create situations where the AI has two or more potential
 * winning paths (dual threats), making it difficult for the opponent to block all of them.
 */
public class QuixoCreateDualThreatsAI implements QuixoRule {
    /**
     * Evaluates the game state and returns a move that creates multiple winning threats.
     * 
     * @param gameLogic The current game state
     * @param aiSymbol The symbol ('X' or 'O') of the AI player
     * @return An Optional containing a ScoredMove if a dual-threat move is found, or empty if none exists
     */
    @Override
    public Optional<ScoredMove> getScoredMove(GameLogic gameLogic, char aiSymbol) {
        // Iterate through all edge positions on the board
        for (ComputerPlayer.EdgePosition edge : ComputerPlayer.getAllEdgePositions()) {
            int row = edge.row, col = edge.col;
            char symbol = gameLogic.getBoard().getCube(row, col).getSymbol();

            // Check if the cube is empty or belongs to the AI (can be moved)
            if (symbol == ' ' || symbol == aiSymbol) {
                // Try all valid directions for this cube
                for (String dir : gameLogic.getValidDirections(row, col)) {
                    try {
                        // Create a temporary copy of the game to simulate moves
                        GameLogic temp = gameLogic.deepCopy();
                        if (temp.makeMove(row, col, dir)) {
                            // Count how many potential winning positions exist after this move
                            int threats = temp.countPotentialWins(aiSymbol);
                            // If there are 2 or more threats, this is a strong position
                            if (threats >= 2) {
                                // Return this move with a high score (700) to prioritize creating dual threats
                                return Optional.of(new ScoredMove(new ComputerPlayer.Move(row, col, dir), 700));
                            }
                        }
                    } catch (Exception ignored) {
                        // Ignore any exceptions during simulation
                    }
                }
            }
        }
        // No dual-threat move found
        return Optional.empty();
    }
}
