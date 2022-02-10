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
public class OutsourcePart extends Part{
    
    private String companyName;
    
    public OutsourcePart(int ID, String name, double price, 
            int instock, int minStock, int maxStock, String compName){
        setID(ID);
        setName(name);
        setPrice(price);
        setStock(instock);
        setMin(minStock);
        setMax(maxStock);
        companyName = compName;              
    }
    
    public void setCompanyName(String compName){
        this.companyName = compName;
    }
    
    public String getCompanyName(){
        return companyName;
    }
}