package wgu.project1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart (Part newPart){
        allParts.add(newPart);
    }
    
    public static void addProduct (Product newProduct){
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart (int partID) {
        for (Part part : allParts) {
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }
    
    public static Product lookupProduct (int productID) {
        for (Product product : allProducts) {
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }
    
    public static ObservableList<Part> lookupPart (String partName) {
        ObservableList<Part> tempParts = FXCollections.observableArrayList();
        
        for (Part part : allParts) {
            if (partName.compareTo(part.getName()) == 0) {
                tempParts.add(part);
            }
        }
        return tempParts;
    }

    public static ObservableList<Product> lookupProduct (String productName) {
        ObservableList<Product> tempProducts = FXCollections.observableArrayList();
        
        for (Product product : allProducts) {
            if (productName.compareTo(product.getName()) == 0) {
                tempProducts.add(product);
            }
        }
        return tempProducts;
    }
  
    public static void updatePart (int index, Part selectedPart) {
        Part originalPart = Inventory.lookupPart(index);

        Inventory.deletePart(originalPart);
        Inventory.addPart(selectedPart);
    }
   
    public static void updateProduct (int index, Product selectedProduct) {
        Product originalProduct = Inventory.lookupProduct(index);

        Inventory.deleteProduct(originalProduct);
        Inventory.addProduct(selectedProduct);
    }
    
    public static void deletePart (Part selectedPart) {
        allParts.remove(selectedPart);
    }
    
    public static void deleteProduct (Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }
    
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
