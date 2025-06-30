package Quixo.View.GameRules;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameRulesView extends VBox {
    private final Button backButton = new Button("Back");
    private final Label rulesContent = new Label();
    private final Label heading = new Label("\uD83C\uDFAE Game Rules");

    public GameRulesView(Stage primaryStage, Scene mainScene) {
        this.getStyleClass().add("root");

        heading.getStyleClass().add("heading");

        rulesContent.getStyleClass().add("rules-content");
        rulesContent.setWrapText(true);
        rulesContent.setMaxWidth(850);

        VBox card = new VBox(rulesContent);
        card.getStyleClass().add("card");
        card.setAlignment(Pos.CENTER);

        backButton.getStyleClass().add("back");
        backButton.setOnAction(e -> primaryStage.setScene(mainScene));

        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(30);
        this.setPadding(new Insets(40));
        this.getChildren().addAll(heading, card, backButton);

        this.getStylesheets().add(getClass().getResource("/CSS/gameRules.css").toExternalForm());
        setRulesText();
    }

    private void setRulesText() {
        rulesContent.setText(
                "QUIXO - The Strategic Block Puzzle Game\n\n" +
                        "\uD83C\uDF1F Objective:\n" +
                        "Be the first to align 5 of your symbols (X or O) horizontally, vertically, or diagonally.\n\n" +
                        "▶ Gameplay:\n" +
                        "1. Select a cube from the edge of the board.\n" +
                        "2. Choose a direction to push (UP, DOWN, LEFT, RIGHT).\n" +
                        "3. Your symbol replaces the cube.\n\n" +
                        "⚠ Rules:\n" +
                        "- Only edge cubes can be selected (no corners).\n" +
                        "- You may not reverse the opponent's last move.\n" +
                        "- Center cube is wild and counts for both symbols.\n\n" +
                        "\uD83C\uDF10 Visit 10.134.178.30 for more details."
        );
    }

    public Scene createScene() {
        return new Scene(this, 1300, 800);
    }
}