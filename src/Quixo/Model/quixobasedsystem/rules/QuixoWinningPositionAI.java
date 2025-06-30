package Quixo.Model.quixobasedsystem.rules;

import Quixo.Model.GameLogic;
import Quixo.Model.ComputerPlayer;
import Quixo.Model.quixobasedsystem.QuixoRule;
import Quixo.Model.quixobasedsystem.ScoredMove;

import java.util.Optional;

/**
 * AI implementation that focuses on finding immediate winning moves.
 * This strategy has the highest priority as it identifies moves that will
 * result in an immediate victory for the AI player.
 */
public class QuixoWinningPositionAI implements QuixoRule {
    /**
     * Evaluates the game state and returns a move that results in an immediate win.
     * 
     * @param gameLogic The current game state
     * @param aiSymbol The symbol ('X' or 'O') of the AI player
     * @return An Optional containing a ScoredMove if a winning move is found, or empty if none exists
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
                        // If the move can be made and results in a win
                        if (temp.makeMove(row, col, dir) && temp.checkWin()) {
                            // Return this move with the highest score (1000) to prioritize winning moves
                            return Optional.of(new ScoredMove(new ComputerPlayer.Move(row, col, dir), 1000));
                        }
                    } catch (Exception ignored) {
                        // Ignore any exceptions during simulation
                    }
                }
            }
        }
        // No winning move found
        return Optional.empty();
    }
}
