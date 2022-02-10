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
public class ModifyproductController implements Initializable {

    @FXML
    private Button modifyProductSearch;
    @FXML
    private TextField modifyProductSearchBox;
    @FXML
    private TextField modifyProductID;
    @FXML
    private TextField modifyProductName;
    @FXML
    private TextField modifyProductInv;
    @FXML
    private TextField modifyProductPrice;
    @FXML
    private TextField modifyProductMax;
    @FXML
    private TextField modifyProductMin;
    @FXML
    private Button modifyProductDelete;
    @FXML
    private Button modifyProductCancel;
    @FXML
    private Button modifyProductSave;
    @FXML
    private Button modifyProductAdd;
    @FXML
    private TableView<Part> modifyProductAllPartTable;
    @FXML
    private TableView<Part> modifyProductAscPartTable;

    
    private Inventory inv;
    private Product product
;    
    private ObservableList<Part> productModifyPartSearch = FXCollections.observableArrayList();
    private ObservableList<Part> modifyProductAllPartList = FXCollections.observableArrayList();
    private ObservableList<Part> modifyProductAscPartList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        generateProductAddTable();
        generateProductDeleteTable();
        modifyProductID.setText(Integer.toString(product.getProductID()));
        modifyProductName.setText(product.getProductName());
        modifyProductInv.setText(Integer.toString(product.getProductStock()));
        modifyProductPrice.setText(Double.toString(product.getProductPrice()));
        modifyProductMax.setText(Integer.toString(product.getProductMaxStock()));
        modifyProductMin.setText(Integer.toString(product.getProductMinStock()));
    }    
    
    private void generateProductAddTable(){
        modifyProductAllPartList.setAll(inv.getAllParts());
        modifyProductAllPartTable.setItems(modifyProductAllPartList);
        modifyProductAllPartTable.refresh();
    }
    
    private void generateProductDeleteTable(){
        modifyProductAscPartList = product.getAllAssociatedParts();
        modifyProductAscPartTable.setItems(modifyProductAscPartList);
        modifyProductAscPartTable.refresh();
    }
    
    public ModifyproductController(Inventory inv, Product product){
        this.inv = inv;
        this.product = product;
    }

    

    @FXML
    private void modifyProductSearchHandler(MouseEvent event) {
        if(!modifyProductSearchBox.getText().trim().isEmpty()){
            productModifyPartSearch.clear();
            for (Part p : inv.getAllParts()){
                if (p.getPartName().toLowerCase().contains(modifyProductSearchBox.getText().trim().toLowerCase()) ){
                    productModifyPartSearch.add(p);
                }
            }
            modifyProductAllPartTable.setItems(productModifyPartSearch);
            modifyProductAllPartTable.refresh();
        }
        else{
            modifyProductAllPartTable.setItems(inv.getAllParts());
            modifyProductAllPartTable.refresh();
        }
    }

    @FXML
    private void modifyProductDeleteHandler(MouseEvent event) {
        Part partToRemove = modifyProductAscPartTable.getFocusModel().getFocusedItem();
        modifyProductAscPartList.remove(partToRemove);
        modifyProductAscPartTable.setItems(modifyProductAscPartList);
        modifyProductAscPartTable.refresh();
        
    }

    @FXML
    private void modifyProductCancelHandler(MouseEvent event) throws IOException {
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
    private void modifyProductSaveHandler(MouseEvent event) throws IOException {
         //***************VALIDATE ALL FIELDS******************
        if (modifyProductName.getText().trim().isEmpty() || modifyProductPrice.getText().trim().isEmpty() || 
                modifyProductInv.getText().trim().isEmpty() || modifyProductMax.getText().trim().isEmpty() ||
                modifyProductMin.getText().trim().isEmpty()){
            //Fields Required Error Here
            showError(1);
            return;

        }

        if ((!isDouble(modifyProductPrice.getText().trim()) || (Double.parseDouble(modifyProductPrice.getText().trim())) <= 0)){
           //Invalid Price entered error here
            showError(3);
            return;
        }

        if ((!isInteger(modifyProductMin.getText().trim()) || (Integer.parseInt(modifyProductMin.getText().trim())) <= 0)){
            //Invalid Minimum error here
            showError(4);
            return;
        }
        if ((!isInteger(modifyProductMax.getText().trim()) || (Integer.parseInt(modifyProductMax.getText().trim())) < Integer.parseInt(modifyProductMin.getText().trim()))){
            //Invalid Maximum error here
            showError(5);
            return;
        }
        if (!isInteger(modifyProductInv.getText().trim())){
            showError(6);
            return;
        }
        if ((Integer.parseInt(modifyProductInv.getText().trim()) > Integer.parseInt(modifyProductMax.getText().trim())) || 
                (Integer.parseInt(modifyProductInv.getText().trim()) < Integer.parseInt(modifyProductMin.getText().trim()))){
            showError(7);
            return;
        }
        
        if(modifyProductAscPartList.size() < 1){
            //product must contain at least 1 part
            showError(8);
            return;
        }
        
        if(sumOfPartsPrice(modifyProductAscPartList) > Double.parseDouble(modifyProductPrice.getText()) ){
            showError(9);
            return;
        }
    //****************** SAVE ALL DATA*******************
        Product productToAdd = new Product(Integer.parseInt(modifyProductID.getText().trim()), modifyProductName.getText().trim(), Double.parseDouble(modifyProductPrice.getText().trim()),
                    Integer.parseInt(modifyProductInv.getText().trim()), Integer.parseInt(modifyProductMin.getText().trim()), Integer.parseInt(modifyProductMax.getText().trim()));
            for (Part p : modifyProductAscPartList){
            productToAdd.addAssociatedPart(p);
        }
        int indexToReplace = inv.getAllProducts().indexOf(product);
        inv.updateProduct(indexToReplace, productToAdd);
        
        
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
    private void modifyProductAddHandler(MouseEvent event) {
        if (!modifyProductAscPartList.contains(modifyProductAllPartTable.getFocusModel().getFocusedItem())){
            modifyProductAscPartList.add(modifyProductAllPartTable.getFocusModel().getFocusedItem());
            modifyProductAscPartTable.setItems(modifyProductAscPartList);
            modifyProductAscPartTable.refresh();  
        }
        
        else{
            //Part Is Already Associated Error
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
        
    }
}
    

