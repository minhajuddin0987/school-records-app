package Quixo.Model;

/**
 * This class represents the model for the start screen of the Quixo game.
 * It maintains the state of whether a game has been started.
 */
public class StartScreenModel {
    /**
     * Flag indicating whether a game has been started.
     * True if a game is in progress, false otherwise.
     */
    private boolean gameStarted;

    /**
     * Constructor for the StartScreenModel.
     * Initializes the gameStarted flag to false, indicating no game is in progress.
     */
    public StartScreenModel() {
        this.gameStarted = false;
    }

}
