package Quixo.Model;


import Quixo.Model.quixobasedsystem.InferenceEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * ComputerPlayer class represents an AI player in the Quixo game.
 * It extends the Player class and uses an inference engine to determine its moves.
 */
public class ComputerPlayer extends Player {
    // Inference engine used to determine the best move for the AI
    private final InferenceEngine engine = new InferenceEngine();

    /**
     * Constructor for creating a new ComputerPlayer
     * 
     * @param playerId Unique identifier for the player
     * @param symbol Character symbol representing this player on the board
     */
    public ComputerPlayer(String playerId, char symbol) {
        super(playerId, symbol);
    }

    /**
     * Determines the next move for the AI player based on the current game state
     * 
     * @param gameLogic The current game state and rules
     * @return A Move object containing the position and direction of the move, or null if no valid move is found
     */
    public Move getNextMove(GameLogic gameLogic) {
        try {
            Move move = engine.decideMove(gameLogic, getSymbol());
            if (move == null) {
                System.out.println("[AI WARNING] No valid move could be determined!");
            }
            return move;
        } catch (Exception e) {
            System.out.println("[AI ERROR] Exception in getNextMove: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Inner class representing a move in the Quixo game
     * A move consists of a position (row, col) and a direction to push
     */
    public static class Move {
        public int row, col;      // Position coordinates on the board
        public String direction;  // Direction to push ("UP", "DOWN", "LEFT", "RIGHT")

        /**
         * Constructor for creating a new Move
         * 
         * @param row Row coordinate on the board (0-4)
         * @param col Column coordinate on the board (0-4)
         * @param direction Direction to push the piece
         */
        public Move(int row, int col, String direction) {
            this.row = row;
            this.col = col;
            this.direction = direction;
        }
    }

    /**
     * Inner class representing a position on the edge of the board
     * In Quixo, only edge pieces can be moved
     */
    public static class EdgePosition {
        public int row, col;  // Position coordinates on the board

        /**
         * Constructor for creating a new EdgePosition
         * 
         * @param row Row coordinate on the board (0-4)
         * @param col Column coordinate on the board (0-4)
         */
        public EdgePosition(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    /**
     * Utility method to get all valid edge positions on a 5x5 Quixo board
     * 
     * @return A list of all edge positions on the board
     */
    public static List<EdgePosition> getAllEdgePositions() {
        List<EdgePosition> edges = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            // Add top and bottom row positions
            edges.add(new EdgePosition(0, i));
            edges.add(new EdgePosition(4, i));

            // Add left and right column positions (excluding corners which are already added)
            if (i != 0 && i != 4) {
                edges.add(new EdgePosition(i, 0));
                edges.add(new EdgePosition(i, 4));
            }
        }
        return edges;
    }
}
