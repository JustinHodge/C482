/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justinhodgec482.Model;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import justinhodgec482.Model.Part;
import justinhodgec482.Model.Product;


/**
 *
 * @author MRCY
 */
public class Inventory {
    
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public void addPart(Part newPart){
        for (Part partToCheck : allParts){
            if (partToCheck.getPartID() == newPart.getPartID()){
                break;
            }
        }
        allParts.add(newPart);
        
    }
    
    public void addProduct(Product newProduct){
       for (Product productToCheck : allProducts){
           if(productToCheck.getProductID() == newProduct.getProductID()){
               break;
           }
       }
       allProducts.add(newProduct);
    }
    
    public Part lookupPart(int partID){
        for (Part lookupCheckPart : allParts){
            if (lookupCheckPart.getPartID() == partID){
                return lookupCheckPart;
            }
        }
        return null;
    }
    
    public Product lookupProduct(int productID){
        for (Product lookupCheckProduct : allProducts){
            if (lookupCheckProduct.getProductID() == productID){
                return lookupCheckProduct;
            }
        }
        return null;
    }
    
    public ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partsByName = FXCollections.observableArrayList();
        
        for (Part lookupCheckPart : allParts){
            if (lookupCheckPart.getPartName().equals(partName)){
                partsByName.add(lookupCheckPart);
            }
        }
        return partsByName;
    }
    
    public ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productsByName = FXCollections.observableArrayList();
        
        for (Product lookupCheckProduct : allProducts){
            if (lookupCheckProduct.getProductName().equals(productName)){
                productsByName.add(lookupCheckProduct);
            }
        }
        return productsByName;
    }
    
    public void updatePart(int partIndex, Part selectedPart){
        allParts.set(partIndex, selectedPart);
    }
    
    public void updateProduct(int productIndex, Product newProduct){
        allProducts.set(productIndex, newProduct);
    }
    
    public boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }
    
    public boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }
    
    public ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    public ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
