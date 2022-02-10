/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justinhodgec482.Model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 *
 * @author MRCY
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String productName;
    private Double productPrice;
    private int productStock;
    private int productMaxStock;
    private int productMinStock;
    
    //constructor
    public Product(int ID, String name, double price,int stock, int min, int max){
        productID = ID;
        productName = name;
        productPrice = price;
        productStock = stock;
        productMinStock = min;
        productMaxStock = max;
    }
    
    //set methods
    public void setProductID(int ID){
        this.productID = ID;
    }
    
    public void setProductName(String name){
        this.productName = name;
    }
    
    public void setProductPrice(double price){
        this.productPrice = price;
    }
    
    public void setProductStock(int stock){
        this.productStock = stock;
    }
    
    public void setProductMinStock(int min){
        this.productMinStock = min;
    }
    
    public void setProductMaxStock(int max){
        this.productMaxStock = max;
    }
    

    // get methods
    
    public int getProductID(){
        return productID;
    }
    
    public String getProductName(){
        return productName;
    }
    
    public double getProductPrice(){
        return productPrice;
    }
    
    public int getProductStock(){
        return productStock;
    }
    
    public int getProductMinStock(){
        return productMinStock;
    }
    
    public int getProductMaxStock(){
        return productMaxStock;
    }
    
    //other methods
        public void addAssociatedPart(Part part){
            if (associatedParts.indexOf(part) == -1){
                associatedParts.add(part);
            }
            else{
            }
    }
    
        public boolean deleteAssociatedPart(Part selectedAsPart){
            int indexOfPart;
            indexOfPart = associatedParts.indexOf(selectedAsPart);
            if (indexOfPart == -1){
                return false;
            }
            else{
                associatedParts.remove(indexOfPart);
                return true;
            }            
        }
        
        public ObservableList<Part> getAllAssociatedParts(){
            return associatedParts;
        }
}
