package Quixo.View.GameRulesMenu;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Presenter class for the Game Rules screen.
 * Handles the logic and user interactions for the game rules view.
 */
public class GameRulesPresenter {
    // View component reference
    private final Gamerulesview view;
    // Main application stage
    private final Stage primaryStage;
    // Reference to the menu scene for navigation
    private final Scene menuScene;

    /**
     * Constructor for GameRulesPresenter.
     * 
     * @param view The game rules view component
     * @param primaryStage The main application stage
     * @param menuScene The menu scene for navigation
     */
    public GameRulesPresenter(Gamerulesview view, Stage primaryStage, Scene menuScene) {
        this.view = view;
        this.primaryStage = primaryStage;
        this.menuScene = menuScene;

        // Initialize the presenter
        EventHandlers();
        initializeNodes();
    }

    /**
     * Sets up event handlers for UI components.
     * Configures the back button to return to the menu scene.
     */
    private void EventHandlers() {
        // Set action for back button to return to menu
        view.getBackButton().setOnAction(e -> primaryStage.setScene(menuScene));
    }

    /**
     * Initializes UI nodes with data or additional configuration.
     * Currently empty but available for future enhancements.
     */
    private void initializeNodes() {
        // No initialization needed at this time
    }

    /**
     * Creates and returns a new Scene containing the game rules view.
     * 
     * @return A new Scene object with the game rules view
     */
    public Scene createScene() {
        // Create a new scene with the view and specified dimensions
        return new Scene(view, 1300, 800);
    }
}
