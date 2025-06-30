package Quixo.Model.quixobasedsystem.rules;

import Quixo.Model.ComputerPlayer;
import Quixo.Model.GameLogic;
import Quixo.Model.quixobasedsystem.QuixoRule;
import Quixo.Model.quixobasedsystem.ScoredMove;

import java.util.Optional;

/**
 * AI implementation that focuses on blocking the opponent's winning moves.
 * This AI analyzes the game board to find and prevent potential winning moves
 * by the opponent, giving these blocking moves a high priority.
 */
public class QuixoBlockOpponentAI implements QuixoRule {
    /**
     * Evaluates the game state and returns a move that blocks the opponent from winning.
     * 
     * @param gameLogic The current game state
     * @param aiSymbol The symbol ('X' or 'O') of the AI player
     * @return An Optional containing a ScoredMove if a blocking move is found, or empty if none exists
     */
    @Override
    public Optional<ScoredMove> getScoredMove(GameLogic gameLogic, char aiSymbol) {
        // Determine the opponent's symbol
        char opponent = (aiSymbol == 'X') ? 'O' : 'X';

        // Iterate through all edge positions on the board
        for (ComputerPlayer.EdgePosition edge : ComputerPlayer.getAllEdgePositions()) {
            int row = edge.row, col = edge.col;
            char symbol = gameLogic.getBoard().getCube(row, col).getSymbol();

            // Check if the cube is empty or belongs to the opponent (can be moved)
            if (symbol == ' ' || symbol == opponent) {
                // Try all valid directions for this cube
                for (String dir : gameLogic.getValidDirections(row, col)) {
                    try {
                        // Create a temporary copy of the game to simulate moves
                        GameLogic temp = gameLogic.deepCopy();
                        if (temp.makeMove(row, col, dir)) {
                            temp.switchPlayer(); // Let opponent play
                            // If the opponent would win after this move, block it
                            if (temp.checkWin()) {
                                // Return this move with a high score (900) to prioritize blocking
                                return Optional.of(new ScoredMove(new ComputerPlayer.Move(row, col, dir), 900));
                            }
                        }
                    } catch (Exception ignored) {
                        // Ignore any exceptions during simulation
                    }
                }
            }
        }
        // No blocking move found
        return Optional.empty();
    }
}
