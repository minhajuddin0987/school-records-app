package Quixo.Model;

/**
 * Exception thrown when an invalid move is attempted in the Quixo game.
 * This exception is used to signal that a player has attempted to make a move
 * that violates the rules of the game.
 */
public class InvalidMoveException extends Exception {
    /**
     * Creates a new InvalidMoveException with the specified error message.
     *
     * @param message A description of why the move is invalid
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
