package Quixo.Model;

import Quixo.managers.AuthManager;

/**
 * This class handles the sign-up functionality for the Quixo game.
 * It manages user registration by validating credentials and communicating with the AuthManager.
 */
public class SignUpModel {
    /**
     * AuthManager instance to handle authentication and registration operations.
     */
    private final AuthManager authManager;

    /**
     * Constructor initializes a new AuthManager instance.
     */
    public SignUpModel() {
        this.authManager = new AuthManager();
    }

    /**
     * Registers a new player with the provided credentials.
     * 
     * @param playerId The unique identifier for the player
     * @param password The player's password
     * @param confirmPassword The confirmation of the player's password
     * @return Player object if registration is successful
     * @throws IllegalArgumentException if playerId or password is empty, or if passwords don't match
     * @throws RuntimeException if registration fails for other reasons
     */
    public Player registerPlayer(String playerId, String password, String confirmPassword) {
        try {
            // Validate that playerId is not null or empty
            if (playerId == null || playerId.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            // Validate that password is not null or empty
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }
            // Validate that password and confirmPassword match
            if (!password.equals(confirmPassword)) {
                throw new IllegalArgumentException("Passwords do not match");
            }

            // Attempt to register using the AuthManager
            return authManager.register(playerId, password, confirmPassword);
        } catch (IllegalArgumentException e) {
            // Re-throw validation errors directly
            throw e;
        } catch (Exception e) {
            // Wrap other exceptions with more user-friendly message
            throw new RuntimeException("Registration failed: " + e.getMessage(), e);
        }
    }
}
