/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justinhodgec482.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import justinhodgec482.Model.Part;
import justinhodgec482.Model.Inventory;
import justinhodgec482.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MRCY
 */
public class AddproductController implements Initializable {
    
    Inventory inv;
    
    @FXML
    private Button addProductSearch;
    @FXML
    private TextField addProductSearchBox;
    @FXML
    private TextField addProductID;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductInv;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductMin;
    @FXML
    private Button addProductDelete;
    @FXML
    private Button addProductCancel;
    @FXML
    private Button addProductSave;
    @FXML
    private Button addProductAdd;
    @FXML
    private TableView<Part> addProductAllPartTable;
    @FXML
    private TableView<Part> addProductAscPartTable;
    
    private ObservableList<Part> productPartSearch = FXCollections.observableArrayList();
    private ObservableList<Part> addProductAllPartList = FXCollections.observableArrayList();
    private ObservableList<Part> addProductAscPartList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    
    public AddproductController(Inventory inv){
        this.inv = inv;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateProductAddTable();
        int testProductID = 1;
        while (inv.getAllProducts().contains(inv.lookupProduct(testProductID))){
            testProductID = testProductID + 1;
        }
        addProductID.setText(String.valueOf(testProductID));
    }    
    
    private void generateProductAddTable(){
        addProductAllPartList.setAll(inv.getAllParts());
        addProductAllPartTable.setItems(addProductAllPartList);
        addProductAllPartTable.refresh();
    }

    @FXML
    private void addProductSearchHandler(MouseEvent event) {
        if(!addProductSearchBox.getText().trim().isEmpty()){
            productPartSearch.clear();
            for (Part p : inv.getAllParts()){
                if (p.getPartName().toLowerCase().contains(addProductSearchBox.getText().trim().toLowerCase()) ){
                    productPartSearch.add(p);
                }
            }
            addProductAllPartTable.setItems(productPartSearch);
            addProductAllPartTable.refresh();
        }
        else{
            addProductAllPartTable.setItems(inv.getAllParts());
            addProductAllPartTable.refresh();
        }
    }

    @FXML
    private void addProductDeleteHandler(MouseEvent event) {
        addProductAscPartList.remove(addProductAscPartTable.getFocusModel().getFocusedItem());
        addProductAscPartTable.setItems(addProductAscPartList);
        addProductAscPartTable.refresh();
    }

    @FXML
    private void addProductCancelHandler(MouseEvent event) throws IOException {
        if(isCancelConfirmed()){
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_screen.fxml"));
            Main_screenController controller = new Main_screenController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void addProductSaveHandler(MouseEvent event) throws IOException{
        //***************VALIDATE ALL FIELDS******************
        if (addProductName.getText().trim().isEmpty() || addProductPrice.getText().trim().isEmpty() || 
                addProductInv.getText().trim().isEmpty() || addProductMax.getText().trim().isEmpty() ||
                addProductMin.getText().trim().isEmpty()){
            //Fields Required Error Here
           showError(1);
            return;

        }

        if ((!isDouble(addProductPrice.getText().trim()) || (Double.parseDouble(addProductPrice.getText().trim())) <= 0)){
           //Invalid Price entered error here
           showError(3);
           return;
        }

        if ((!isInteger(addProductMin.getText().trim()) || (Integer.parseInt(addProductMin.getText().trim())) <= 0)){
            //Invalid Minimum error here
            showError(4);
            return;
        }
        if ((!isInteger(addProductMax.getText().trim()) || (Integer.parseInt(addProductMax.getText().trim())) < Integer.parseInt(addProductMin.getText().trim()))){
            //Invalid Maximum error here
            showError(5);
            return;
        }
        if (!isInteger(addProductInv.getText().trim())){
            //inv must be integer error
            showError(6);
            return;
        }
        if ((Integer.parseInt(addProductInv.getText().trim()) > Integer.parseInt(addProductMax.getText().trim())) || 
                (Integer.parseInt(addProductInv.getText().trim()) < Integer.parseInt(addProductMin.getText().trim()))){
            // stock must be between min and max error
            showError(7);
            return;
        }
        
        if(addProductAscPartList.size() < 1){
            //product must contain at least 1 part
            showError(8);
            return;
        }
        
        if(sumOfPartsPrice(addProductAscPartList) > Double.parseDouble(addProductPrice.getText()) ){
            showError(9);
            return;
        }
        
    //****************** SAVE ALL DATA*******************
        Product productToAdd = new Product(Integer.parseInt(addProductID.getText().trim()), addProductName.getText().trim(), Double.parseDouble(addProductPrice.getText().trim()),
                    Integer.parseInt(addProductInv.getText().trim()), Integer.parseInt(addProductMin.getText().trim()), Integer.parseInt(addProductMax.getText().trim()));
        for (Part p : addProductAscPartList){
            productToAdd.addAssociatedPart(p);
        }
        inv.addProduct(productToAdd);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_screen.fxml"));
        Main_screenController controller = new Main_screenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addProductAddHandler(MouseEvent event) {
        if (!addProductAscPartList.contains(addProductAllPartTable.getFocusModel().getFocusedItem())){
            addProductAscPartList.add(addProductAllPartTable.getFocusModel().getFocusedItem());
            addProductAscPartTable.setItems(addProductAscPartList);
            addProductAscPartTable.refresh();  
        }
        
        else{
            //part already associated error
            showError(2);
    }

    }
    
    
    private static boolean isDouble(String str){
        try{
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }
    
    private static boolean isInteger(String str){
        try{
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }
    
    private boolean isCancelConfirmed(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Action");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("All unsaved changes will be lost!");
        Optional<ButtonType> confirm = alert.showAndWait();
        return confirm.get() == ButtonType.OK;
    }
    
    private double sumOfPartsPrice(ObservableList<Part> partsList){
        double total = 0;
        for(Part p : partsList){
            total = total + p.getPartPrice();
        }
        
        return total;
    }   
    
    private void showError(int errorCode){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR!");
        
        if (errorCode == 1){
           //all Fields are required error 
           alert.setHeaderText("Missing Fields");
           alert.setContentText("All Fields Are Required!");
        }
        
        if (errorCode == 2){
            // machine ID is invalid error
           alert.setHeaderText("Associated Part Error");
           alert.setContentText("This Part is already associated with this product");
        }
        
        if (errorCode == 3){
            //price is invalid error
           alert.setHeaderText("Invalid Price");
           alert.setContentText("Price must be  number greater than zero (0)!");
        }
        
        if (errorCode == 4){
           //invalid minimum error
           alert.setHeaderText("Minimum Error");
           alert.setContentText("Minimum must be an integer greater than zero (0)!");
        }
        
        if (errorCode == 5){
            //invalid maximum error
           alert.setHeaderText("Maximum Error");
           alert.setContentText("Maximum must be an integer greater than or equal to the Minimum!");
        }
        
        if (errorCode == 6){
            //current stock must be integer error
           alert.setHeaderText("Inv Error");
           alert.setContentText("The current stock must be an Integer!");
        }
        
        if (errorCode == 7){
            //inventory must be between min and max error
           alert.setHeaderText("Stock Error");
           alert.setContentText("The current stock must be between the minimum and maximum");
        }
        
        if (errorCode == 8){
            alert.setHeaderText("Associated Part Error");
            alert.setContentText("A product must have Associated parts.");
        }
        
        if (errorCode == 9){
            alert.setHeaderText("Associated Part Error");
            alert.setHeaderText("The price of the associated parts may \n not exceed the price of the product");
        }
        alert.showAndWait();
        alert.showAndWait();
        
    }
}



