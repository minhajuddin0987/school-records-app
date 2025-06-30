package Quixo.View.MenuScreen;

import Quixo.View.Leaderboard.LeaderBoardButtonView;
import Quixo.View.BackButton.BackButtonView;
import Quixo.View.StartButton.StartButtonView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;

/**
 * View class for the Menu Screen of the Quixo game.
 * This class is responsible for creating and laying out the UI components
 * of the main menu, including buttons for game start, leaderboard, game rules,
 * and navigation back to the previous screen.
 * It extends BorderPane to organize the layout of these components.
 */
public class MenuScreenView extends BorderPane {

    /** Container for all menu buttons */
    private VBox buttonContainer;
    /** Button view for starting the game */
    private StartButtonView startButtonView;
    /** Button view for accessing the leaderboard */
    private LeaderBoardButtonView leaderBoardView;
    /** Button view for navigating back */
    private BackButtonView backButtonView;
    /** Button for accessing game rules */
    private Button gameRulesButton;
    /** Background image view for the menu screen */
    private ImageView backgroundImageView;

    /**
     * Constructor for MenuScreenView.
     * Initializes the UI components, sets up their layout, and applies styling.
     */
    public MenuScreenView() {
        initialiseNodes();
        layoutNodes();
        loadStyles();
    }

    /**
     * Initializes all UI components for the menu screen.
     * Creates buttons, sets their style classes, and organizes them in a vertical container.
     */
    private void initialiseNodes() {
        // Set up the background image first
        setupBackgroundImage();

        // Create button views
        startButtonView = new StartButtonView();
        leaderBoardView = new LeaderBoardButtonView();
        backButtonView = new BackButtonView();
        gameRulesButton = new Button("Game Rules");

        // Apply CSS style classes to buttons
        startButtonView.getStartButton().getStyleClass().add("start-button");
        leaderBoardView.getLeaderBoardButton().getStyleClass().add("leaderboard-button");
        backButtonView.getBackButton().getStyleClass().add("back-button");
        gameRulesButton.getStyleClass().add("rules-button");

        // Create a vertical container for all buttons
        buttonContainer = new VBox(
                startButtonView.getStartButton(),
                leaderBoardView.getLeaderBoardButton(),
                gameRulesButton,
                backButtonView.getBackButton()
        );
        buttonContainer.setSpacing(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getStyleClass().add("button-container");
    }

    /**
     * Sets up the layout of UI components.
     * Creates a layered pane with background image and button container,
     * and positions them in the center of the BorderPane.
     */
    private void layoutNodes() {
        // Create a stack pane to layer background and buttons
        StackPane layeredPane = new StackPane();
        // Bind background image size to the pane size
        backgroundImageView.fitWidthProperty().bind(layeredPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(layeredPane.heightProperty());

        // Add background and buttons to the layered pane
        layeredPane.getChildren().addAll(backgroundImageView, buttonContainer);
        StackPane.setAlignment(buttonContainer, Pos.CENTER);

        // Set the layered pane as the center of the BorderPane
        setCenter(layeredPane);
    }

    /**
     * Sets up the background image for the menu screen.
     * Loads the image from resources and configures its display properties.
     */
    private void setupBackgroundImage() {
        try {
            // Load image from resources
            URL resource = getClass().getResource("/Startback.png");

            if (resource == null) {
                System.err.println("Image file not found!");
                return;
            }

            // Create and configure the image view
            Image backgroundImage = new Image(resource.toExternalForm());
            backgroundImageView = new ImageView(backgroundImage);
            backgroundImageView.setPreserveRatio(false);
            backgroundImageView.toBack();
        } catch (Exception ex) {
            System.err.println("Failed to load background image: " + ex.getMessage());
        }
    }

    /**
     * Loads CSS styles for the menu screen.
     * Applies the menuScreen.css stylesheet to this view.
     */
    private void loadStyles() {
        this.getStylesheets().add(getClass().getResource("/CSS/menuScreen.css").toExternalForm());
    }

    /**
     * Gets the game rules button.
     * @return The button for accessing game rules
     */
    public Button getGameRulesButton() {
        return gameRulesButton;
    }

    /**
     * Gets the start button view.
     * @return The view containing the start game button
     */
    public StartButtonView getStartButtonView() {
        return startButtonView;
    }

    /**
     * Gets the leaderboard button view.
     * @return The view containing the leaderboard button
     */
    public LeaderBoardButtonView getLeaderBoardView() {
        return leaderBoardView;
    }

    /**
     * Gets the back button view.
     * @return The view containing the back navigation button
     */
    public BackButtonView getBackButtonView() {
        return backButtonView;
    }
}
