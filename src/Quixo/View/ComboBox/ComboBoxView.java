package Quixo.View.ComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

/**
 * ComboBoxView class for the Quixo game.
 * This class creates and manages a dropdown menu (ComboBox) that allows users
 * to navigate between different sections of the application.
 * It extends BorderPane to provide layout capabilities for positioning the ComboBox.
 */
public class ComboBoxView extends BorderPane {
    /**
     * The ComboBox that contains navigation options.
     */
    private ComboBox<String> names;

    /**
     * Constructor for the ComboBoxView.
     * Initializes and lays out the UI components.
     */
    public ComboBoxView() {
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Initializes the UI components.
     * Creates the ComboBox and populates it with navigation options.
     */
    private void initialiseNodes() {
        // Create a new ComboBox
        names = new ComboBox<>();

        // Create a list of navigation options
        ObservableList<String> values =
                FXCollections.observableArrayList(
                        "H E L P",
                        "G A M E",
                        "C O M P A N Y");

        // Set the options in the ComboBox
        names.setItems(values);

        // Set the default selected value
        names.setValue("H E L P");
    }

    /**
     * Lays out the UI components.
     * Places the ComboBox at the top of the BorderPane and sets its width.
     */
    private void layoutNodes() {
        // Position the ComboBox at the top of the BorderPane
        setTop(names);

        // Set the preferred width of the ComboBox
        names.setPrefWidth(150);
    }

    /**
     * Returns the ComboBox instance.
     * This allows other components to access and use the ComboBox.
     *
     * @return The configured ComboBox
     */
    public ComboBox<String> getComboBox() {
        return names;
    }
}
