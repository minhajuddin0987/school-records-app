package Quixo.View.ComboBox;

import Quixo.Model.HelpScreenModel;
import Quixo.View.CompanyScreen.CompanyScreenView;
import Quixo.View.GameRules.GameRulesView;
import Quixo.View.HelpScreen.HelpScreenPresenter;
import Quixo.View.HelpScreen.HelpScreenView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Quixo.Model.ComboBoxModel;

/**
 * ComboBoxPresenter class handles the presentation logic for the ComboBox component.
 * It manages user interactions with the combo box and navigates to different screens
 * based on user selection.
 */
public class ComboBoxPresenter {
    /** The view component containing the ComboBox UI element */
    private final ComboBoxView view;
    /** The model component containing the data and business logic */
    private final ComboBoxModel model;
    /** The primary stage of the application */
    private final Stage primaryStage;
    /** The main scene of the application */
    private final Scene mainScene;

    /**
     * Constructor for ComboBoxPresenter
     * 
     * @param view The ComboBox view component
     * @param model The ComboBox model component
     * @param primaryStage The primary stage of the application
     * @param mainScene The main scene of the application
     */
    public ComboBoxPresenter(ComboBoxView view, ComboBoxModel model,
                             Stage primaryStage, Scene mainScene) {
        this.view = view;
        this.model = model;
        this.primaryStage = primaryStage;
        this.mainScene = mainScene;
        addEventHandler();
        setupLayout();
    }

    /**
     * Sets up the event handler for the combo box selection
     */
    private void addEventHandler() {
        view.getComboBox().setOnAction(event -> handleComboBoxSelection());
    }

    /**
     * Handles the combo box selection event
     * Gets the selected value from the combo box and navigates to the appropriate screen
     */
    private void handleComboBoxSelection() {
        String selected = view.getComboBox().getValue();
        if (selected == null) {
            return;
        }

        // Store the selected item in the model
        model.setSelectedItem(selected);

        // Navigate to the appropriate screen based on selection
        switch (selected.trim()) {
            case "H E L P":
                showHelpScreen();
                break;
            case "G A M E":
                showGameScreen();
                break;
            case "C O M P A N Y":
                showCompanyScreen();
                break;
        }
    }

    /**
     * Displays the Help screen
     * Creates a new HelpScreenView and HelpScreenPresenter and sets the scene
     * Includes error handling with a fallback simple scene
     */
    private void showHelpScreen() {
        try {
            HelpScreenView helpView = new HelpScreenView();
            HelpScreenModel helpModel = new HelpScreenModel();

            HelpScreenPresenter helpPresenter = new HelpScreenPresenter(
                    helpView,
                    helpModel,
                    primaryStage,
                    mainScene
            );

            Scene helpScene = helpPresenter.createScene();

            primaryStage.setScene(helpScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback to a simple scene
            VBox fallback = new VBox(new Label("Help Screen Content"));
            primaryStage.setScene(new Scene(fallback, 1300, 800));
        }
    }

    /**
     * Displays the Game Rules screen
     * Creates a new GameRulesView and sets the scene
     */
    private void showGameScreen() {
        GameRulesView gameRulesView = new GameRulesView(primaryStage, mainScene);
        primaryStage.setScene(new Scene(gameRulesView, 1300, 800));
    }

    /**
     * Displays the Company screen
     * Creates a new CompanyScreenView and sets the scene
     */
    private void showCompanyScreen() {
        CompanyScreenView companyScreenView = new CompanyScreenView(primaryStage, mainScene);
        primaryStage.setScene(companyScreenView.createScene());
    }
    /**
     * Sets up the layout of the ComboBox in the main scene
     * Positions the ComboBox at the top-left of the BorderPane with appropriate margins
     */
    private void setupLayout() {
        if (mainScene.getRoot() instanceof BorderPane) {
            BorderPane root = (BorderPane) mainScene.getRoot();
            root.setTop(view);  // view is a BorderPane with ComboBox at top
            BorderPane.setAlignment(view, Pos.TOP_LEFT);
            BorderPane.setMargin(view, new Insets(10));
        }
    }

    /**
     * Static initialization method to create and set up the ComboBox component
     * 
     * @param primaryStage The primary stage of the application
     * @param mainScene The main scene of the application
     */
    public static void init(Stage primaryStage, Scene mainScene) {
        ComboBoxView view = new ComboBoxView();
        ComboBoxModel model = new ComboBoxModel();
        new ComboBoxPresenter(view, model, primaryStage, mainScene);
    }

}
