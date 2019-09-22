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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AddProductController implements Initializable {
    
    //TextFields
    @FXML private TextField IDTextField;
    @FXML private TextField NameTextField;
    @FXML private TextField InventoryTextField;
    @FXML private TextField PriceTextField;
    @FXML private TextField MaxTextField;
    @FXML private TextField MinTextField;
    @FXML private TextField SearchTextField;
    
    //Search & Add parts table
    @FXML private TableView<Part> PartLookupTable;
    @FXML private TableColumn<Part, Integer> PartLookupIdColumn;
    @FXML private TableColumn<Part, String> PartLookupNameColumn;
    @FXML private TableColumn<Part, Integer> PartLookupInventoryColumn;
    @FXML private TableColumn<Part, Double> PartLookupPriceColumn;
   
    
    //Parts of Product table
    @FXML private TableView<Part> ProductPartTable;
    @FXML private TableColumn<Part, Integer> ProductPartIdColumn;
    @FXML private TableColumn<Part, String> ProductPartNameColumn;
    @FXML private TableColumn<Part, Integer> ProductPartInventoryColumn;
    @FXML private TableColumn<Part, Double> ProductPartPriceColumn;
    
    //Product to add
    Product newProduct = new Product(0, "", 0.0, 0, 0, 0);
    
    //Adds selected Product object based on input & updates Inventory class list
    public void SaveButtonClicked(ActionEvent e) throws IOException {
                
        //Checks to see if Product has associated Parts, warns user if there is none
        if (ProductPartTable.getItems().isEmpty()) {
            DialogBoxes.warnAboutPartRequirement();
        }
        
        //If Product has associated Parts, proceeds with adding the product
        //If textfields are left blank, the default value (0 or empty string) is assigned.
        else {
            newProduct.setId(Integer.parseInt(IDTextField.getText()));
            if (!NameTextField.getText().isEmpty()) {
                newProduct.setName(NameTextField.getText());  
            }
            if (!PriceTextField.getText().isEmpty()) {
                newProduct.setPrice(Double.parseDouble(PriceTextField.getText()));  
            }
            if (!InventoryTextField.getText().isEmpty()) {
                newProduct.setStock(Integer.parseInt(InventoryTextField.getText()));
            }
            if (!MinTextField.getText().isEmpty()) {
                newProduct.setMin(Integer.parseInt(MinTextField.getText()));
            }  
            if (!MaxTextField.getText().isEmpty()) {
                newProduct.setMax(Integer.parseInt(MaxTextField.getText()));
            }  
             
            Inventory.addProduct(newProduct);
            ReturnToMainScreen(e);     
        }
    }
    
    //If user hits "YES" to warning box, cancels Product creation and return to main screen
    public void CancelButtonClicked(ActionEvent e) throws IOException {
        if (DialogBoxes.confirmAction("Cancel")) {
            ReturnToMainScreen(e);
        }
    }
    
    //Gets Part data from selection in PartLookupTable, adds it to the assosicated parts list of Product
    public void AddButtonClicked() {
        
        Part selectedPart = PartLookupTable.getSelectionModel().getSelectedItem();
        
        if (selectedPart != null) {
            newProduct.addAssociatedPart(selectedPart);
            ProductPartTable.setItems(newProduct.getAllAssociatedParts());
        }
        
    }
    
    //If user hits "YES" to warning box, deletes Part from the associated parts list of the Product
    public void DeleteButtonClicked() {
        Part selectedPart = ProductPartTable.getSelectionModel().getSelectedItem();
        
        if (selectedPart != null) {
            if (DialogBoxes.confirmAction("Delete")) {
                newProduct.deleteAssociatedPart(selectedPart);
            }
        }
    }
    
    //Searches allParts list for Part with name in Searchbar
    public void SearchButtonClicked() {
        
        //If searchbar is empty, updates PartLookupTable with all parts
        if (!SearchTextField.getText().trim().isEmpty()) {
           PartLookupTable.setItems(Inventory.lookupPart(SearchTextField.getText().trim()));
       }
       
       //If searchbar has a value, updates PartLookupTable with matches
       else {
           PartLookupTable.setItems(Inventory.getAllParts());
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
        //Sets Current Product ID
        this.IDTextField.setText(String.valueOf(Inventory.getAllProducts().size()+1));
        
        //Sets up PartLookup table
        PartLookupIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartLookupNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartLookupInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartLookupPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        PartLookupTable.setItems(Inventory.getAllParts());
        PartLookupTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Sets up ProductPart table
        ProductPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductPartTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }    
}