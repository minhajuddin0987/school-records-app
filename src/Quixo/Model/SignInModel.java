package Quixo.Model;

import Quixo.managers.AuthManager;

/**
 * This class handles the sign-in functionality for the Quixo game.
 * It manages user authentication by validating credentials and communicating with the AuthManager.
 */
public class SignInModel {
    /**
     * AuthManager instance to handle authentication operations.
     */
    private final AuthManager authManager;

    /**
     * Constructor initializes a new AuthManager instance.
     */
    public SignInModel() {
        this.authManager = new AuthManager();
    }

    /**
     * Authenticates a player using their ID and password.
     * 
     * @param playerId The unique identifier for the player
     * @param password The player's password
     * @return Player object if authentication is successful
     * @throws IllegalArgumentException if playerId or password is empty
     * @throws RuntimeException if login fails for other reasons
     */
    public Player authenticate(String playerId, String password) {
        try {
            // Validate that playerId is not null or empty
            if (playerId == null || playerId.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            // Validate that password is not null or empty
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }

            // Attempt to login using the AuthManager
            return authManager.login(playerId, password);
        } catch (IllegalArgumentException e) {
            // Re-throw validation exceptions
            throw e;
        } catch (Exception e) {
            // Wrap other exceptions in a RuntimeException with a descriptive message
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        }
    }
}
