/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package projecttcc_2.pkg0.control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author reido
 */
public class ProjectTcc_20 extends Application {
    
   @Override
    public void start(Stage stage) throws Exception {
        ScreenManager screenManager = new ScreenManager(stage);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLDocument.fxml"));
        Parent root = loader.load();
        
        FXMLDocumentController controller = loader.getController();
        controller.setScreenManager(screenManager);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
 