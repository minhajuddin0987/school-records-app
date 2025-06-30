package Quixo.View.CompanyScreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * CompanyScreenView class displays information about the company.
 * This view extends VBox to organize UI elements vertically.
 */
public class CompanyScreenView extends VBox {
    // UI components
    private final Button backButton = new Button("Back");
    private final Label titleLabel = new Label("Company Information");

    /**
     * Constructor for CompanyScreenView.
     * 
     * @param primaryStage The main application stage
     * @param mainScene The scene to return to when back button is clicked
     */
    public CompanyScreenView(Stage primaryStage, Scene mainScene) {
        // Apply root style class to this VBox
        this.getStyleClass().add("root");

        // Apply title style to the title label
        titleLabel.getStyleClass().add("title");

        // Create and configure the information label with company details
        Label infoLabel = new Label(
                "Our Company: Ten Cubes\n\n" +
                        "\uD83D\uDCCD Founded: 2023\n" +
                        "\uD83D\uDCBB Focus: JavaFX Desktop Applications\n" +
                        "\uD83D\uDE80 Mission: Build intuitive and modern software experiences"
        );
        infoLabel.getStyleClass().add("info");
        infoLabel.setWrapText(true);
        infoLabel.setMaxWidth(700);

        // Create a card container for the information
        VBox card = new VBox(infoLabel);
        card.getStyleClass().add("card");
        card.setAlignment(Pos.CENTER);

        // Configure back button with style and action
        backButton.getStyleClass().add("back");
        backButton.setOnAction(e -> primaryStage.setScene(mainScene));

        // Configure this VBox layout properties
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
        this.setPadding(new Insets(40));

        // Add all components to the layout
        this.getChildren().addAll(titleLabel, card, backButton);

        // Apply CSS stylesheet
        this.getStylesheets().add(getClass().getResource("/CSS/company.css").toExternalForm());
    }

    /**
     * Creates and returns a new Scene containing this view.
     * 
     * @return A new Scene with this view as the root and dimensions of 1300x800
     */
    public Scene createScene() {
        return new Scene(this, 1300, 800);
    }
}
