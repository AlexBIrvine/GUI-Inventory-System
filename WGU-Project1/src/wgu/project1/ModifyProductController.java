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

public class ModifyProductController implements Initializable {
    
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
    
    //Product to modify
    private Product selectedProduct;

    //Modifies selected Product object based on input & updates Inventory class list
    public void SaveButtonClicked(ActionEvent e) throws IOException {
        
        //Checks to see if Product has associated Parts, warns user if there is none
        if (ProductPartTable.getItems().isEmpty()) {
            DialogBoxes.warnAboutPartRequirement();
        }
        
        //If Product has associated Parts, proceeds with the update
        else {
            selectedProduct.setId(Integer.parseInt(IDTextField.getText()));
            selectedProduct.setName(NameTextField.getText());
            selectedProduct.setPrice(Double.parseDouble(PriceTextField.getText()));
            selectedProduct.setStock(Integer.parseInt(InventoryTextField.getText()));
            selectedProduct.setMin(Integer.parseInt(MinTextField.getText()));
            selectedProduct.setMax(Integer.parseInt(MaxTextField.getText()) );

            Inventory.updateProduct(Integer.parseInt(IDTextField.getText()), selectedProduct);
            ReturnToMainScreen(e);  
        }
    }
    
    //If user hits "YES" to warning box, cancels Product modification and return to main screen
    public void CancelButtonClicked(ActionEvent e) throws IOException {
        if (DialogBoxes.confirmAction("Cancel")) {
            ReturnToMainScreen(e);
        }
    }
    
    //Gets Part data from selection in PartLookupTable, adds it to the assosicated parts list of Product
    public void AddButtonClicked() {
        
        Part selectedPart = PartLookupTable.getSelectionModel().getSelectedItem();
        
        if (selectedPart != null) {
            selectedProduct.addAssociatedPart(selectedPart);
            ProductPartTable.setItems(selectedProduct.getAllAssociatedParts());
        }
    }
    
    //If user hits "YES" to warning box, deletes Part from the associated parts list of the Product
    public void DeleteButtonClicked() {
        
        Part selectedPart = ProductPartTable.getSelectionModel().getSelectedItem();
        
        if (selectedPart != null) {
            if (DialogBoxes.confirmAction("Delete")) {    
                selectedProduct.deleteAssociatedPart(selectedPart);
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

    //Allows Product data to be passed into this controller from MainScreen
    public void productToModify(Product product){
        selectedProduct = product;
        IDTextField.setText(String.valueOf(selectedProduct.getId()));
        NameTextField.setText(selectedProduct.getName());
        InventoryTextField.setText(String.valueOf(selectedProduct.getStock()));
        PriceTextField.setText(String.valueOf(selectedProduct.getPrice()));
        MaxTextField.setText(String.valueOf(selectedProduct.getMax()));
        MinTextField.setText(String.valueOf(selectedProduct.getMin()));
        ProductPartTable.setItems(selectedProduct.getAllAssociatedParts());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
