package Quixo.View.HelpScreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HelpScreenView extends VBox {
    private final Button backButton = new Button("Back");
    private final Label helpLabel = new Label();

    public HelpScreenView() {
        this.getStyleClass().add("root");

        Label title = new Label("\uD83D\uDD98 How to Play Quixo");
        title.getStyleClass().add("title");

        helpLabel.getStyleClass().add("help-content");
        helpLabel.setWrapText(true);
        helpLabel.setMaxWidth(800);

        VBox card = new VBox(helpLabel);
        card.getStyleClass().add("card");
        card.setAlignment(Pos.CENTER);

        backButton.getStyleClass().add("back");

        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
        this.setPadding(new Insets(40));
        this.getChildren().addAll(title, card, backButton);

        this.getStylesheets().add(getClass().getResource("/CSS/helpScreen.css").toExternalForm());
    }

    public Button getBackButton() {
        return backButton;
    }

    public void setHelpText(String text) {
        helpLabel.setText(text);
    }
}