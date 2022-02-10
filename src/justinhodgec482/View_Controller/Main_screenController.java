/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justinhodgec482.View_Controller;

import java.io.IOException;
import javafx.application.Platform;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author MRCY
 */
public class Main_screenController implements Initializable {
    
    Inventory inv;

    @FXML
    private Button mainScreenPartSearch;
    @FXML
    private TextField mainScreenPartSearchBox;
    @FXML
    private TableView<Part> mainPartsTable;
    @FXML
    private Button mainScreenPartAdd;
    @FXML
    private Button mainScreenPartModify;
    @FXML
    private Button mainScreenPartDelete;
    @FXML
    private Button mainScreenProductSearch;
    @FXML
    private TextField mainScreenProductSearchBox;
    @FXML
    private TableView<Product> mainProductsTable;
    @FXML
    private Button mainScreenProductAdd;
    @FXML
    private Button mainScreenProductModify;
    @FXML
    private Button mainScreenProductDelete;
    @FXML
    private Button mainScreenExit;
    
    private ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Product> productInventorySearch = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> mainPartColumn;
    @FXML
    private TableColumn<?, ?> mainPartName;
    @FXML
    private TableColumn<?, ?> mainPartInven;
    
    
    
    public Main_screenController(Inventory inv){
        this.inv = inv;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateMainPartsTable();
        generateMainProductsTable();
    }    
    
    private void generateMainPartsTable(){
        partsInventory.setAll(inv.getAllParts());
        
        mainPartsTable.setItems(partsInventory);
        mainPartsTable.refresh();
    }
    
    private void generateMainProductsTable(){
        productInventory.setAll(inv.getAllProducts());
        
        mainProductsTable.setItems(productInventory);
        mainProductsTable.refresh(); 
    }

    @FXML
    private void mainScreenPartSearchHandler(MouseEvent event) {
        if(!mainScreenPartSearchBox.getText().trim().isEmpty()){
            partsInventorySearch.clear();
            for (Part p : inv.getAllParts()){
                if (p.getPartName().toLowerCase().contains(mainScreenPartSearchBox.getText().trim().toLowerCase()) ){
                    partsInventorySearch.add(p);
                }
            }
            mainPartsTable.setItems(partsInventorySearch);
            mainPartsTable.refresh();
        }
        else{
            mainPartsTable.setItems(partsInventory);
            mainPartsTable.refresh();
        }
    }

    @FXML
    private void mainScreenPartAddHandler(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/addpart.fxml"));
        AddpartController controller = new AddpartController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void mainScreenPartModifyHandler(MouseEvent event) throws IOException {
        Part chosenPart = mainPartsTable.getFocusModel().getFocusedItem();
        if(chosenPart == null){
            //must make a selection to modify Error
            showError(1);
            return;
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modifypart.fxml"));
        ModifypartController controller = new ModifypartController(inv, mainPartsTable.getFocusModel().getFocusedItem());
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }

    @FXML
    private void mainScreenPartDeleteHandler(MouseEvent event) {
        Part chosenPart = mainPartsTable.getFocusModel().getFocusedItem();
        if (chosenPart != null){
            if (isDeleteConfirmed(chosenPart.getPartName())){
                partsInventory.remove(chosenPart);
                inv.deletePart(chosenPart);
                mainPartsTable.refresh();  
            }
        }
    }

    @FXML
    private void mainScreenProductSearchHandler(MouseEvent event) {
        if(!mainScreenProductSearchBox.getText().trim().isEmpty()){
            productInventorySearch.clear();
            for (Product p : inv.getAllProducts()){
                if (p.getProductName().toLowerCase().contains(mainScreenProductSearchBox.getText().trim().toLowerCase()) ){
                    productInventorySearch.add(p);
                }
            }
            mainProductsTable.setItems(productInventorySearch);
            mainProductsTable.refresh();
        }
        
        else{
            mainProductsTable.setItems(productInventory);
            mainProductsTable.refresh();
        }
    }

    @FXML
    private void mainScreenProductAddHandler(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/addproduct.fxml"));
        AddproductController controller = new AddproductController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void mainScreenProductModifyHandler(MouseEvent event) throws IOException{
                Product chosenProduct = mainProductsTable.getFocusModel().getFocusedItem();
        if(chosenProduct == null){
            //must choose a Part to modify Error
            showError(1);
            return;
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modifyproduct.fxml"));
        ModifyproductController controller = new ModifyproductController(inv, mainProductsTable.getFocusModel().getFocusedItem());
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();         
    }

    @FXML
    private void mainScreenProductDeleteHandler(MouseEvent event) {
        Product chosenProduct = mainProductsTable.getFocusModel().getFocusedItem();
        if (chosenProduct != null){
            if (isDeleteConfirmed(chosenProduct.getProductName())){
                productInventory.remove(chosenProduct);
                inv.deleteProduct(chosenProduct);
                mainProductsTable.refresh();
            }
        }
    }

    @FXML
    private void mainScreenExitHandler(MouseEvent event) {
        if(isExitConfirmed()){
            Platform.exit(); 
        }
    }
    
    //Alerts and Errors
    private void showError(int errorCode){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle ("ERROR!");
        
        if (errorCode == 1){
            //Part or Product Must be chosen to modify error
            alert.setHeaderText("Selection Error");
            alert.setContentText("You must make a selection to modify!");
        }
        alert.showAndWait();
    }
    
    private boolean isDeleteConfirmed(String chosenItem){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Are you sure you want to delete " + chosenItem + "?");
        alert.setContentText("This is irreversible!");
        Optional<ButtonType> confirm = alert.showAndWait();
        return confirm.get() == ButtonType.OK; 
    }
    
    private boolean isExitConfirmed(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Program");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Click OK to close program.");
        Optional<ButtonType> confirm = alert.showAndWait();
        return confirm.get() == ButtonType.OK;
    }
}
