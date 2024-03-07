/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLInscreverController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button bntCadastrar;
    
    @FXML
    private Button bntRegistrar;

    @FXML
    private Button btnFacebook;

    @FXML
    private Button btnGoogle;
    
    private ScreenManager screenManager;
    private Stage stage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        screenManager = new ScreenManager(stage);
    }    
    
    @FXML
    private void cadastroGoogle(ActionEvent event) {
        // Implemente aqui a lógica para o cadastro com Google
        System.out.println("Cadastro com Google");
    }

    @FXML
    private void cadastroFacebook(ActionEvent event) {
        // Implemente aqui a lógica para o cadastro com Facebook
        System.out.println("Cadastro com Facebook");
    }
    
    @FXML
    void cadastraButtonAction(ActionEvent event) {
        System.out.println("Botão de entrada pressionado..");
        screenManager.switchScreen("/projecttcc_2/pkg0/View/FXMLCadastrar.fxml");
    } 
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
