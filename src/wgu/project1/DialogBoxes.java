package wgu.project1;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class DialogBoxes {
    
    //Asks the user for confirmation on cancel/delete/exit options. 
    public static boolean confirmAction(String buttonPressed) {
        //Setup alertbox.
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm " + buttonPressed);
        alert.setHeaderText(null);
        alert.setContentText("Do you really want to " + buttonPressed.toLowerCase() + "?");

        //Create Yes/No buttons.
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No", ButtonData.CANCEL_CLOSE);

        //Group buttons and add them to alert box.
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        
        //Returns true if yes button pressed.  
        return (result.get() == yesButton);
    }
    
    //Creates alert box warning about requirements.
    public static void warnAboutPartRequirement() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Products must have associated parts");
        alert.setHeaderText(null);
        alert.setContentText("Products must have at least 1 associated part.\nPlease add a part and try again.");
        alert.showAndWait();
    }
    
    //Warns the user that no Part/Product is selected
    public static void warnAboutNoSelection(String partOrProduct) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No " + partOrProduct + " Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a " + partOrProduct.toLowerCase() + " to modify first");
            alert.showAndWait();
    }
}
