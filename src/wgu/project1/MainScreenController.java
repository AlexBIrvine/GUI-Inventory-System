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

public class MainScreenController implements Initializable {
    
    //Parts
    @FXML private TextField PartSearchField;
    @FXML private TableView<Part> PartTable;
    @FXML private TableColumn<Part, Integer> PartIdColumn;
    @FXML private TableColumn<Part, String> PartNameColumn;
    @FXML private TableColumn<Part, Integer> PartInventoryColumn;
    @FXML private TableColumn<Part, Double> PartPriceColumn;
   
    //Products
    @FXML private TextField ProductSearchField;
    @FXML private TableView<Product> ProductTable;
    @FXML private TableColumn<Product, Integer> ProductIdColumn;
    @FXML private TableColumn<Product, String> ProductNameColumn;
    @FXML private TableColumn<Product, Integer> ProductInventoryColumn;
    @FXML private TableColumn<Product, Double> ProductPriceColumn;
    
    //Changes window to the "Add Part" scene
    public void addPartButtonPushed (ActionEvent e) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        
        window.setScene(addPartScene);
        window.show();
    }
    
    //Changes window to the "Modify Part" scene, passing in selected Part data
    public void modifyPartButtonPushed (ActionEvent e) throws IOException {
        
        //If a part is selected, change scene and pass in data
        if (PartTable.getSelectionModel().getSelectedItem() != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyPart.fxml"));
            Parent modifyPartParent = loader.load();
            Scene modifyPartScene = new Scene(modifyPartParent);
        
            //access the controller and call a method
            ModifyPartController controller = loader.getController();
            controller.partToModify(PartTable.getSelectionModel().getSelectedItem());
        
            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
            window.setScene(modifyPartScene);
            window.show();
        }
        
        //If no part is selected, warn user and don't change scenes
        else {
            DialogBoxes.warnAboutNoSelection("Part");
        }
    }
    
    //If user hits "YES" to warning box, deletes Part from  Inventory list
    //If no part is selected, nothing happens
    public void deletePartButtonPushed (ActionEvent e) {
        
        Part selectedPart = PartTable.getSelectionModel().getSelectedItem();
        
        if (selectedPart != null) {
            if (DialogBoxes.confirmAction("Delete")) {
                Inventory.deletePart(selectedPart);
            }
        }
    }
    
    //Searches allParts list for Part with name in Searchbar
    public void searchPartButtonPushed () {
        
        //If searchbar is empty, updates PartTable with all parts
       if (!PartSearchField.getText().trim().isEmpty()) {
           PartTable.setItems(Inventory.lookupPart(PartSearchField.getText().trim()));
       }
       
       //If searchbar has a value, updates PartTable with matches
       else {
           PartTable.setItems(Inventory.getAllParts());
       }    
    }
    
    //Changes window to the "Add Product" scene
    public void addProductButtonPushed (ActionEvent e) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        
        window.setScene(addProductScene);
        window.show();
    }
    
    //Changes window to the "Modify Product" scene, passing in selected Product data
    public void modifyProductButtonPushed (ActionEvent e) throws IOException {
        
        //If a proudct is selected, change scene and pass in data
        if (ProductTable.getSelectionModel().getSelectedItem() != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
            Parent modifyProductParent = loader.load();
        
            Scene modifyProductScene = new Scene(modifyProductParent);
        
            //access the controller and call a method
            ModifyProductController controller = loader.getController();
            controller.productToModify(ProductTable.getSelectionModel().getSelectedItem());
        
            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
            window.setScene(modifyProductScene);
            window.show();
        }
        
        //If no product is selected, warn user and don't change scenes
        else {
            DialogBoxes.warnAboutNoSelection("Product");
        } 
    }
    
    //If user hits "YES" to warning box, deletes Product from Inventory list
    //If no product is selected, nothing happens
    public void deleteProductButtonPushed (ActionEvent e) {
        
        Product selectedProduct = ProductTable.getSelectionModel().getSelectedItem();
        
        if (selectedProduct != null){
            if (DialogBoxes.confirmAction("Delete")) {
                Inventory.deleteProduct(selectedProduct);
            }
        }   
    }
    
    //Searches allProducts list for Product with name in Searchbar
    public void searchProductButtonPushed () {
        
       //If searchbar is empty, updates ProductTable with all products
       if (!ProductSearchField.getText().trim().isEmpty()) {
           ProductTable.setItems(Inventory.lookupProduct(ProductSearchField.getText().trim()));
       }
       
       //If searchbar has a value, updates ProductTable with matches
       else {
           ProductTable.setItems(Inventory.getAllProducts());
       }    
    }
    
    //If user hits "YES" to warning box, exits program
    public void exitButtonPushed(ActionEvent e) {
        if (DialogBoxes.confirmAction("Exit")) {
            Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Sets up Parts table
        PartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        PartTable.setItems(Inventory.getAllParts());
        PartTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Sets up Products table
        ProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductTable.setItems(Inventory.getAllProducts());
        ProductTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }    
}