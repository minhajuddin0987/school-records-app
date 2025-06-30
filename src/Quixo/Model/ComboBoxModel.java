package Quixo.Model;

/**
 * A model class for combo box components in the Quixo game UI.
 * This class stores the currently selected item in a combo box.
 */
public class ComboBoxModel {

    /**
     * The currently selected item in the combo box
     */
    private String selectedItem;

    /**
     * Sets the selected item in the combo box
     * 
     * @param selectedValue The value to set as the selected item
     */
    public void setSelectedItem(String selectedValue) {
        this.selectedItem = selectedValue;
    }
}
