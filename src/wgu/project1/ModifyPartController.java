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

public class ModifyPartController implements Initializable {
    
    // Radio Buttons and ToggleGroup
    @FXML private RadioButton InHouseRadioButton;
    @FXML private RadioButton OutsourcedRadioButton;
    private ToggleGroup SupplierInfoToggleGroup;
    
    //TextFields
    @FXML private TextField IDTextField;
    @FXML private TextField NameTextField;
    @FXML private TextField InventoryLevelTextField;
    @FXML private TextField PriceTextField;
    @FXML private TextField MaxTextField;
    @FXML private TextField MinTextField;
    @FXML private Label CompanyNameorMachineIDLabel;
    @FXML private TextField CompanyNameorMachineIDField;
    
    //Part to modify
    private Part selectedPart;

    //Modifies selected Part object based on input & updates Inventory class list
    public void SaveButtonClicked(ActionEvent e) throws IOException {
        
        //InHouse option
         if (this.SupplierInfoToggleGroup.getSelectedToggle().equals(this.InHouseRadioButton)) {
            Part newPart = new InHouse(
                    Integer.parseInt(IDTextField.getText()), 
                    NameTextField.getText(), 
                    Double.parseDouble(PriceTextField.getText()), 
                    Integer.parseInt(InventoryLevelTextField.getText()), 
                    Integer.parseInt(MinTextField.getText()), 
                    Integer.parseInt(MaxTextField.getText()), 
                    Integer.parseInt(CompanyNameorMachineIDField.getText())
            );
            Inventory.updatePart(Integer.parseInt(IDTextField.getText()), newPart);
        }
         
        //Outsourced option
        if (this.SupplierInfoToggleGroup.getSelectedToggle().equals(this.OutsourcedRadioButton)) {
            Part newPart = new Outsourced(
                    Integer.parseInt(IDTextField.getText()), 
                    NameTextField.getText(), 
                    Double.parseDouble(PriceTextField.getText()), 
                    Integer.parseInt(InventoryLevelTextField.getText()), 
                    Integer.parseInt(MinTextField.getText()), 
                    Integer.parseInt(MaxTextField.getText()), 
                    CompanyNameorMachineIDField.getText()
            );
            Inventory.updatePart(Integer.parseInt(IDTextField.getText()), newPart);
        }
        
        //Returns to main screen after update
        ReturnToMainScreen(e);
    }
    
    //If user hits "YES" to warning box, cancels Part creation and return to main screen
    public void CancelButtonClicked(ActionEvent e) throws IOException {
        if (DialogBoxes.confirmAction("Cancel")) {
            ReturnToMainScreen(e);
        }
    }
    
    //Changes Label and TextField prompt based on selection
    public void radioButtonChanged() {
        if (this.SupplierInfoToggleGroup.getSelectedToggle().equals(this.InHouseRadioButton)) {
            CompanyNameorMachineIDLabel.setText("Machine ID");
            CompanyNameorMachineIDField.setPromptText("Mach ID");
        }
            
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
    
    //Allows Part data to be passed into this controller from MainScreen
    public void partToModify(Part part){
        
        //Sets TextFields based on passed Part
        selectedPart = part;
        IDTextField.setText(String.valueOf(selectedPart.getId()));
        NameTextField.setText(selectedPart.getName());
        InventoryLevelTextField.setText(String.valueOf(selectedPart.getStock()));
        PriceTextField.setText(String.valueOf(selectedPart.getPrice()));
        MaxTextField.setText(String.valueOf(selectedPart.getMax()));
        MinTextField.setText(String.valueOf(selectedPart.getMin()));
        
        //InHouse option
        if (selectedPart instanceof InHouse){        
            InHouseRadioButton.setSelected(true);
            CompanyNameorMachineIDLabel.setText("Machine ID");
            CompanyNameorMachineIDField.setText(String.valueOf(((InHouse)selectedPart).getMachineId()));
        }
        
        //Outsourced option
        else if (selectedPart instanceof Outsourced){
            OutsourcedRadioButton.setSelected(true);
            CompanyNameorMachineIDLabel.setText("Company Name");
            CompanyNameorMachineIDField.setText(((Outsourced)selectedPart).getCompanyName());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize radio button group & add radio buttons
        SupplierInfoToggleGroup = new ToggleGroup();
        this.InHouseRadioButton.setToggleGroup(SupplierInfoToggleGroup);
        this.OutsourcedRadioButton.setToggleGroup(SupplierInfoToggleGroup);
    }    
}
