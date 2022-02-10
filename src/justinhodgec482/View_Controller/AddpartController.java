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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import justinhodgec482.Model.*;

/**
 * FXML Controller class
 *
 * @author MRCY
 */
public class AddpartController implements Initializable {

    @FXML
    private RadioButton addPartInHouse;
    @FXML
    private ToggleGroup inHouseOutsourceToggle;
    @FXML
    private RadioButton addPartOutsource;
    @FXML
    private TextField addPartID;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartInv;
    @FXML
    private TextField addPartPriceCost;
    @FXML
    private TextField addPartMax;
    @FXML
    private Button addPartSave;
    @FXML
    private TextField addMachineIDField;
    @FXML
    private Label addMachineID;
    @FXML
    private TextField addPartMin;
    @FXML
    private Label addCompanyName;
    @FXML
    private TextField addCompanyNameField;
    @FXML
    private AnchorPane companyNameToggle;
    @FXML
    private AnchorPane machineIDToggle;

    private Inventory inv;

    
    //constructor to get the inventory into the controller
    public AddpartController(Inventory inv){
        this.inv = inv;
    }

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int testPartID = 1;
        while (inv.getAllParts().contains(inv.lookupPart(testPartID))){
            testPartID = testPartID + 1;
        }
        addPartID.setText(String.valueOf(testPartID));
    }  
    
    @FXML
    private void toggleCompanyNameOff(MouseEvent event) {
        companyNameToggle.setVisible(false);
        machineIDToggle.setVisible(true);
        addPartID.setEditable(false);
        addPartID.setPromptText("Disabled - Auto Generate");
        addPartID.setFocusTraversable(false);
        addPartID.setDisable(true);

    }

    @FXML
    private void toggleCompanyNameOn(MouseEvent event) {
        companyNameToggle.setVisible(true);
        machineIDToggle.setVisible(false);
        addPartID.setEditable(false);
        addPartID.setPromptText("Disabled - Auto Generate");
        addPartID.setFocusTraversable(false);
        addPartID.setDisable(true);
    }

    @FXML
    private void addPartSaveHandler(MouseEvent event) throws IOException {
//***************VALIDATE ALL FIELDS******************
        if (addPartName.getText().trim().isEmpty() || addPartPriceCost.getText().trim().isEmpty() || 
                addPartInv.getText().trim().isEmpty() || addPartMax.getText().trim().isEmpty() ||
                addPartMin.getText().trim().isEmpty() || 
                (addPartOutsource.isSelected() && addCompanyNameField.getText().trim().isEmpty()) ||
                (addPartInHouse.isSelected() && addMachineIDField.getText().trim().isEmpty())) {
            //Fields Required Error Here
            showError(1);
            return;

        }
        if (addPartInHouse.isSelected() && !isInteger(addMachineIDField.getText().trim())){
            //Invalid Machine ID error here
            showError(2);
            return;
        }
            
        if ((!isDouble(addPartPriceCost.getText().trim()) || (Double.parseDouble(addPartPriceCost.getText().trim())) <= 0)){
           //Invalid Price entered error here
           showError(3);
           return;
        }
        
        if ((!isInteger(addPartMin.getText().trim()) || (Integer.parseInt(addPartMin.getText().trim())) <= 0)){
            //Invalid Minimum error here
            showError(4);
            return;
        }
        if ((!isInteger(addPartMax.getText().trim()) || (Integer.parseInt(addPartMax.getText().trim())) < Integer.parseInt(addPartMin.getText().trim()))){
            //Invalid Maximum error here
            showError(5);
            return;
        }
        if (!isInteger(addPartInv.getText().trim())){
            showError(6);
            return;
        }
        if ((Integer.parseInt(addPartInv.getText().trim()) > Integer.parseInt(addPartMax.getText().trim())) || 
                (Integer.parseInt(addPartInv.getText().trim()) < Integer.parseInt(addPartMin.getText().trim()))){
            showError(7);
            return;
        }
//****************** SAVE ALL DATA*******************

        if(addPartInHouse.isSelected()){
            //new Inhouse Part
            inv.addPart(new InhousePart(Integer.parseInt(addPartID.getText().trim()), addPartName.getText().trim(), Double.parseDouble(addPartPriceCost.getText().trim()),
                    Integer.parseInt(addPartInv.getText().trim()), Integer.parseInt(addPartMin.getText().trim()), Integer.parseInt(addPartMax.getText().trim()), 
                    Integer.parseInt(addMachineIDField.getText().trim())));
        }
        
        if(addPartOutsource.isSelected()){
            //new Outsource Part
            inv.addPart(new OutsourcePart(Integer.parseInt(addPartID.getText().trim()), addPartName.getText().trim(), Double.parseDouble(addPartPriceCost.getText().trim()),
                    Integer.parseInt(addPartInv.getText().trim()), Integer.parseInt(addPartMin.getText().trim()), Integer.parseInt(addPartMax.getText().trim()), 
                    addCompanyNameField.getText().trim()));
        }
        
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
    private void addPartCancelHandler(MouseEvent event) throws IOException {
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
    
    private void showError(int errorCode){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR!");
        
        if (errorCode == 1){
           //all Fields are required error 
           alert.setHeaderText("Missing Fields");
           alert.setContentText("All Fields Are Required!");
        }
        
        if (errorCode == 2){
            // machine ID is invalid error
           alert.setHeaderText("Machine ID Error");
           alert.setContentText("Machine ID must be an Integer!");
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
        alert.showAndWait();        
    }
    
    private boolean isCancelConfirmed(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancel Action");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("All unsaved changes will be lost!");
        Optional<ButtonType> confirm = alert.showAndWait();
        return confirm.get() == ButtonType.OK;
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
    

    
}
