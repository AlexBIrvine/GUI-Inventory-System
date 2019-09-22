package wgu.project1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddPartController implements Initializable {
    // Radio Buttons and ToggleGroup
    @FXML private RadioButton InHouseRadioButton;
    @FXML private RadioButton OutsourcedRadioButton;
    private ToggleGroup SupplierInfoToggleGroup;
    
    //TextFields & labels
    @FXML private TextField IDTextField;
    @FXML private TextField NameTextField;
    @FXML private TextField InventoryLevelTextField;
    @FXML private TextField PriceTextField;
    @FXML private TextField MaxTextField;
    @FXML private TextField MinTextField;
    @FXML private Label CompanyNameorMachineIDLabel;
    @FXML private TextField CompanyNameorMachineIDField;
    
    //Creates Part object based on input & adds it to Inventory class list
    //If textfields are left blank, the default value (0 or empty string) is assigned.
    public void SaveButtonClicked(ActionEvent e) throws IOException {
        
        //InHouse Option
        if (this.SupplierInfoToggleGroup.getSelectedToggle().equals(this.InHouseRadioButton)) {
            
            Part newPart = new InHouse(Integer.parseInt(IDTextField.getText()), "", 0.0, 0,0,0,0);
            
            if (!NameTextField.getText().isEmpty()) {
                newPart.setName(NameTextField.getText());  
            }
            if (!PriceTextField.getText().isEmpty()) {
                newPart.setPrice(Double.parseDouble(PriceTextField.getText())); 
            }
            if (!InventoryLevelTextField.getText().isEmpty()) {
                newPart.setStock(Integer.parseInt(InventoryLevelTextField.getText()));
            }
            if (!MinTextField.getText().isEmpty()) {
                newPart.setMin(Integer.parseInt(MinTextField.getText()));
            }
            if (!MaxTextField.getText().isEmpty()) {
                newPart.setMax(Integer.parseInt(MaxTextField.getText()));
            }
            if (!CompanyNameorMachineIDField.getText().isEmpty()) {
                ((InHouse)newPart).setMachineId(Integer.parseInt(CompanyNameorMachineIDField.getText()));
            }
            Inventory.addPart(newPart);
        }
        
        //Outsourced Option
        if (this.SupplierInfoToggleGroup.getSelectedToggle().equals(this.OutsourcedRadioButton)) {
            
            Part newPart = new Outsourced(Integer.parseInt(IDTextField.getText()), "", 0.0,0,0,0,"");
            
            if (!NameTextField.getText().isEmpty()) {
                newPart.setName(NameTextField.getText());  
            }
            if (!PriceTextField.getText().isEmpty()) {
                newPart.setPrice(Double.parseDouble(PriceTextField.getText())); 
            }
            if (!InventoryLevelTextField.getText().isEmpty()) {
                newPart.setStock(Integer.parseInt(InventoryLevelTextField.getText()));
            }
            if (!MinTextField.getText().isEmpty()) {
                newPart.setMin(Integer.parseInt(MinTextField.getText()));
            }
            if (!MaxTextField.getText().isEmpty()) {
                newPart.setMax(Integer.parseInt(MaxTextField.getText()));
            }
            if (!CompanyNameorMachineIDField.getText().isEmpty()) {
                ((Outsourced)newPart).setCompanyName(CompanyNameorMachineIDField.getText());
            }            
            Inventory.addPart(newPart);
        }    
        ReturnToMainScreen(e);
    }
    
    //If user hits "YES" to warning box, cancels Part creation and return to main screen
    public void CancelButtonClicked(ActionEvent e) throws IOException {
        if (DialogBoxes.confirmAction("Cancel")) {
            ReturnToMainScreen(e);
        }
    }
    
    //Changes Label and TextField prompt based on selection.
    public void radioButtonChanged() {
        
        //InHouse Option
        if (this.SupplierInfoToggleGroup.getSelectedToggle().equals(this.InHouseRadioButton)) {
            CompanyNameorMachineIDLabel.setText("Machine ID");
            CompanyNameorMachineIDField.setPromptText("Mach ID");
        }
            
        //Outsourced Option
        if (this.SupplierInfoToggleGroup.getSelectedToggle().equals(this.OutsourcedRadioButton)) {
            CompanyNameorMachineIDLabel.setText("Company Name");
            CompanyNameorMachineIDField.setPromptText("Comp Nm");
        }
    }
    
    //Logic for returning to main screen
    public void ReturnToMainScreen(ActionEvent e) throws IOException {
        
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenParent);
        
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        
        window.setScene(mainScreenScene);
        window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize radio button group & add radio buttons
        SupplierInfoToggleGroup = new ToggleGroup();
        this.InHouseRadioButton.setToggleGroup(SupplierInfoToggleGroup);
        this.OutsourcedRadioButton.setToggleGroup(SupplierInfoToggleGroup);
        this.IDTextField.setText(String.valueOf(Inventory.getAllParts().size()+1));
    }    
}
