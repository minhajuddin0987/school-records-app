package Quixo.Model.quixobasedsystem.rules;

import Quixo.Model.ComputerPlayer;
import Quixo.Model.GameLogic;
import Quixo.Model.quixobasedsystem.QuixoRule;
import Quixo.Model.quixobasedsystem.ScoredMove;

import java.util.List;
import java.util.Optional;

/**
 * AI implementation that focuses on controlling the center of the board.
 * This strategy prioritizes moves that push cubes toward the center of the board,
 * which can create more strategic positioning and potential winning opportunities.
 */
public class QuixoCenterControlAI implements QuixoRule {
    /**
     * Evaluates the game state and returns a move that pushes cubes toward the center.
     * 
     * @param gameLogic The current game state
     * @param aiSymbol The symbol ('X' or 'O') of the AI player
     * @return An Optional containing a ScoredMove if a center-controlling move is found, or empty if none exists
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
                    // Look for moves that push toward the center of the board
                    if ((row == 0 && dir.equals("down")) ||     // From top edge, push down
                            (row == 4 && dir.equals("up")) ||   // From bottom edge, push up
                            (col == 0 && dir.equals("right")) || // From left edge, push right
                            (col == 4 && dir.equals("left"))) {  // From right edge, push left
                        // Return this move with a medium score (300) to prioritize center control
                        return Optional.of(new ScoredMove(new ComputerPlayer.Move(row, col, dir), 300));
                    }
                }
            }
        }
        // No center-controlling move found
        return Optional.empty();
    }
}
