/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import justinhodgec482.Model.*;
import justinhodgec482.View_Controller.*;

/**
 *
 * @author MRCY
 */
public class JustinHodgeC482 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Inventory inv = new Inventory();
        addTestData(inv);
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_screen.fxml"));
        Main_screenController controller = new Main_screenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    void addTestData(Inventory inv){
        //Inhouse part adds
        Part A1 = new InhousePart(1, "A1wheel", 1.99, 1, 1, 11, 0001);
        Part A2 = new InhousePart(2, "A2spring", 2.99, 2, 2, 22, 0002);
        Part A3 = new InhousePart(3, "A3Pin", 3.99, 3, 3, 33, 0003);
        inv.addPart(A1);
        inv.addPart(A2);
        inv.addPart(A3);
        inv.addPart(new InhousePart(4, "A4Link", 4.99, 4, 4, 44, 0004));
        inv.addPart(new InhousePart(5, "A5Roller", 5.99, 5, 5, 55, 0005));
        
        //Outsource part adds
        Part B6 = new OutsourcePart(6, "B6Widget", 6.99, 6, 6, 66, "Widget World");
        Part B7 = new OutsourcePart(7, "B7Gizmo", 7.99, 7, 7, 77, "Gizmo Gallery");
        Part B8 = new OutsourcePart(8, "B8Thingy", 8.99, 8, 8, 88, "Thingy Thumper");
        inv.addPart(B6);
        inv.addPart(B7);
        inv.addPart(B8);
        inv.addPart(new OutsourcePart(9, "B9Bauble", 9.99, 9, 9, 99, "Trinkets and Baubles"));
        inv.addPart(new OutsourcePart(10, "B10Whatzit", 10.99, 10, 10, 1010, "Whatzittoya"));
        
        //Product adds
        Product C11 = new Product(11, "C11Thingumiwhut", 11.99, 511, 511, 1111);
        C11.addAssociatedPart(A1);
        C11.addAssociatedPart(B6);
        inv.addProduct(C11);
        Product C12 = new Product(12, "C12Dillybob", 12.99, 512, 512, 1212);
        C12.addAssociatedPart(A2);
        C12.addAssociatedPart(B7);
        inv.addProduct(C12);
        Product C13 = new Product(13, "C13Chungus", 13.99, 513, 513, 1313);
        C13.addAssociatedPart(A3);
        C13.addAssociatedPart(B8);
        inv.addProduct(C13);
        Product C14 = new Product(14, "C14Nicknack", 14.99, 514, 514, 1414);
        C14.addAssociatedPart(A1);
        inv.addProduct(C14);
        Product C15 = new Product(15, "C15Thatun", 15.99, 515, 515, 1515);
        C15.addAssociatedPart(A3);
        inv.addProduct(C15);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
