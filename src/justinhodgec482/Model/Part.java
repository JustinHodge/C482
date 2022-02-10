/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justinhodgec482.Model;

/**
 *
 * @author MRCY
 */
public abstract class Part {
    
    //variables of Part class
    protected int partID;
    protected String partName;
    protected double partPrice;
    protected int partStock;
    protected int partMinStock;
    protected int partMaxStock;
    
    //normal constructor
    public void Part(int ID, String name, double price, 
            int instock, int minStock, int maxStock){
      this.partID = ID;
      this.partName = name;
      this.partPrice = price;
      this.partStock = instock;
      this.partMinStock = minStock;
      this.partMaxStock = maxStock;
    }
    
    //@overload for empty constructor
    public void Part(){
        
    }
    
    //methods of Part class
    
    //setters
    public void setID(int ID){
        this.partID = ID;
    }
    
    public void setName(String name){
        this.partName = name;
    }
    
    public void setPrice(double price){
        this.partPrice = price;
    }
    
    public void setStock(int inStock){
        this.partStock = inStock;
    }
    
    public void setMin(int minStock){
        this.partMinStock = minStock;
    }
    
    public void setMax (int maxStock){
        this.partMaxStock = maxStock;
    }
    
    //getters
    
    public int getPartID(){
        return this.partID;
    }
    
    public String getPartName(){
        return this.partName;
    }
    
    public double getPartPrice(){
        return this.partPrice;
    }
    
    public int getPartStock(){
        return this.partStock;
    }
    
    public int getPartMin(){
        return this.partMinStock;
    }
    
    public int getPartMax (){
        return this.partMaxStock;
    }
}
