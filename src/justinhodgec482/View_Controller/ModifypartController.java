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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import justinhodgec482.Model.InhousePart;
import justinhodgec482.Model.Inventory;
import justinhodgec482.Model.OutsourcePart;
import justinhodgec482.Model.Part;

/**
 * FXML Controller class
 *
 * @author MRCY
 */
public class ModifypartController implements Initializable {

    @FXML
    private RadioButton modifyPartInHouse;
    @FXML
    private ToggleGroup modifyPartToggleGroup;
    @FXML
    private RadioButton modifyPartOutsource;
    @FXML
    private TextField modifyPartID;
    @FXML
    private TextField modifyPartName;
    @FXML
    private TextField modifyPartInv;
    @FXML
    private TextField modifyPartPriceCost;
    @FXML
    private TextField modifyPartMax;
    @FXML
    private Button modifyPartSave;
    @FXML
    private Button modifyPartCancel;
    @FXML
    private TextField modifyMachineIDField;
    @FXML
    private Label modifyMachineID;
    @FXML
    private TextField modifyPartMin;
    @FXML
    private Label modifyCompanyName;
    @FXML
    private TextField modifyCompanyNameField;
    @FXML
    private AnchorPane companyNameToggle;
    @FXML
    private AnchorPane machineIDToggle;
    
    private Inventory inv;
    
    private Part part;

    
    
    public ModifypartController(Inventory inv, Part part){
        this.inv = inv;
        this.part = part;
    }
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modifyPartID.setText(Integer.toString(part.getPartID()));
        modifyPartName.setText(part.getPartName());
        modifyPartInv.setText(Integer.toString(part.getPartStock()));
        modifyPartPriceCost.setText(Double.toString(part.getPartPrice()));
        modifyPartMax.setText(Integer.toString(part.getPartMax()));
        modifyPartMin.setText(Integer.toString(part.getPartMin()));
        if(part instanceof InhousePart){
            modifyPartInHouse.setSelected(true);
            modifyPartOutsource.setSelected(false);
            machineIDToggle.setVisible(true);
            companyNameToggle.setVisible(false);
            modifyMachineIDField.setText(Integer.toString(((InhousePart) part).getMachineID())); 
        }
        if(part instanceof OutsourcePart){
            modifyPartInHouse.setSelected(false);
            modifyPartOutsource.setSelected(true);
            machineIDToggle.setVisible(false);
            companyNameToggle.setVisible(true);
            modifyCompanyNameField.setText(((OutsourcePart) part).getCompanyName());
        }
    } 
    
    @FXML
    private void toggleCompanyNameOff(MouseEvent event) {
        machineIDToggle.setVisible(true);
        companyNameToggle.setVisible(false);
    }

    @FXML
    private void toggleCompanyNameOn(MouseEvent event) {
        companyNameToggle.setVisible(true);
        machineIDToggle.setVisible(false);
    }
    

    @FXML
    private void modifyPartSaveHandler(MouseEvent event) throws IOException {
        //***************VALIDATE ALL FIELDS******************
        if (modifyPartName.getText().trim().isEmpty() || modifyPartPriceCost.getText().trim().isEmpty() || 
                modifyPartInv.getText().trim().isEmpty() || modifyPartMax.getText().trim().isEmpty() ||
                modifyPartMin.getText().trim().isEmpty() || 
                (modifyPartOutsource.isSelected() && modifyCompanyNameField.getText().trim().isEmpty()) ||
                (modifyPartInHouse.isSelected() && modifyMachineIDField.getText().trim().isEmpty())) {
            //Fields Required Error Here
            showError(1);
            return;

        }
        if (modifyPartInHouse.isSelected() && !isInteger(modifyMachineIDField.getText().trim())){
            //Invalid Machine ID error here
            showError(2);
            return;
        }
            
        if ((!isDouble(modifyPartPriceCost.getText().trim()) || (Double.parseDouble(modifyPartPriceCost.getText().trim())) <= 0)){
           //Invalid Price entered error here
           showError(3);
           return;
        }
        
        if ((!isInteger(modifyPartMin.getText().trim()) || (Integer.parseInt(modifyPartMin.getText().trim())) <= 0)){
            //Invalid Minimum error here
            showError(4);
            return;
        }
        if ((!isInteger(modifyPartMax.getText().trim()) || (Integer.parseInt(modifyPartMax.getText().trim())) < Integer.parseInt(modifyPartMin.getText().trim()))){
            //Invalid Maximum error here
            showError(5);
            return;
        }
        if (!isInteger(modifyPartInv.getText().trim())){
            //stock must be an integer error
            showError(6);
            return;
        }
        if ((Integer.parseInt(modifyPartInv.getText().trim()) > Integer.parseInt(modifyPartMax.getText().trim())) || 
                (Integer.parseInt(modifyPartInv.getText().trim()) < Integer.parseInt(modifyPartMin.getText().trim()))){
            //stock must be between minimum and maximum error
            showError(7);
            return;
        }
        if(modifyPartInHouse.isSelected()){
            //new Inhouse Part
            int indexToReplace = inv.getAllParts().indexOf(part);
            inv.updatePart(indexToReplace, new InhousePart(Integer.parseInt(modifyPartID.getText().trim()), modifyPartName.getText().trim(), Double.parseDouble(modifyPartPriceCost.getText().trim()),
                    Integer.parseInt(modifyPartInv.getText().trim()), Integer.parseInt(modifyPartMin.getText().trim()), Integer.parseInt(modifyPartMax.getText().trim()), 
                    Integer.parseInt(modifyMachineIDField.getText().trim())));
        }
        
        if(modifyPartOutsource.isSelected()){
            //new Outsource Part
            int indexToReplace = inv.getAllParts().indexOf(part);
            inv.updatePart(indexToReplace, new OutsourcePart(Integer.parseInt(modifyPartID.getText().trim()), modifyPartName.getText().trim(), Double.parseDouble(modifyPartPriceCost.getText().trim()),
                    Integer.parseInt(modifyPartInv.getText().trim()), Integer.parseInt(modifyPartMin.getText().trim()), Integer.parseInt(modifyPartMax.getText().trim()), 
                    modifyCompanyNameField.getText().trim()));
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
    private void modifyPartCancelHandler(MouseEvent event) throws IOException {
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
        
}
