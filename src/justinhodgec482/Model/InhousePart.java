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
public class InhousePart extends Part{
    
    private int machineID;
    
    public InhousePart(int ID, String name, double price, 
            int instock, int minStock, int maxStock, int machID){
        setID(ID);
        setName(name);
        setPrice(price);
        setStock(instock);
        setMin(minStock);
        setMax(maxStock);
        machineID = machID;              
    }
    
    public void setMachineID(int machID){
        this.machineID = machID;
    }
    
    public int getMachineID(){
        return machineID;
    }
}