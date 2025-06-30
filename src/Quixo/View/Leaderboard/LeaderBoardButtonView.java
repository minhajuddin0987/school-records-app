package Quixo.View.Leaderboard;

import javafx.scene.control.Button;

/**
 * View class for creating a styled leaderboard navigation button.
 * Provides a button that can be used to navigate to the leaderboard screen.
 */
public class LeaderBoardButtonView {
    // Button for navigating to the leaderboard screen
    private Button leaderBoardButton;

    /**
     * Constructor for LeaderBoardButtonView.
     * Creates and styles a button labeled "LEADERBOARD".
     */
    public LeaderBoardButtonView() {
        // Create button with appropriate label
        leaderBoardButton = new Button("LEADERBOARD");
        // Set button size
        leaderBoardButton.setPrefSize(200, 50);
        // Apply styling to make the button visually appealing
        leaderBoardButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    }

    /**
     * Get the leaderboard button.
     * 
     * @return Button for navigating to the leaderboard screen
     */
    public Button getLeaderBoardButton() {
        return leaderBoardButton;
    }
}
