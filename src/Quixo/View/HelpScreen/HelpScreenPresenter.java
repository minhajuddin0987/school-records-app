package Quixo.View.HelpScreen;

import Quixo.Model.HelpScreenModel;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Presenter class for the Help Screen view.
 * Handles the interaction between the HelpScreenView and HelpScreenModel.
 * Responsible for initializing the view with help text and handling navigation.
 */
public class HelpScreenPresenter {
    // View component for displaying help information
    private final HelpScreenView view;
    // Model component containing the help text data
    private final HelpScreenModel model;
    // Main application window
    private final Stage primaryStage;
    // Reference to the previous scene to enable navigation back
    private final Scene previousScene;

    /**
     * Constructor for HelpScreenPresenter.
     * Initializes the presenter with the view, model, and navigation references.
     * 
     * @param view The HelpScreenView instance to be controlled
     * @param model The HelpScreenModel containing the help text data
     * @param primaryStage The main application window
     * @param previousScene The scene to return to when back button is clicked
     */
    public HelpScreenPresenter(HelpScreenView view, HelpScreenModel model,
                               Stage primaryStage, Scene previousScene) {
        this.view = view;
        this.model = model;
        this.primaryStage = primaryStage;
        this.previousScene = previousScene;

        // Initialize the view with help text from the model
        initializeView();
        // Set up event handlers for user interactions
        setupEventHandlers();
    }

    /**
     * Initializes the view with help text from the model.
     * Populates the view with the appropriate help content.
     */
    private void initializeView() {
        // Get help text from the model and set it in the view
        view.setHelpText(model.getHelpText());
    }

    /**
     * Sets up event handlers for user interactions with the view.
     * Currently handles the back button click event to return to the previous screen.
     */
    private void setupEventHandlers() {
        // Set action for back button to return to the previous scene
        view.getBackButton().setOnAction(e -> primaryStage.setScene(previousScene));
    }

    /**
     * Creates a new scene containing the help screen view.
     * 
     * @return A new Scene containing the help screen view with standard dimensions
     */
    public Scene createScene() {
        // Create and return a new scene with the view and standard dimensions
        return new Scene(view, 1300, 800);
    }
}
