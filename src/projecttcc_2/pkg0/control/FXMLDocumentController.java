/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    private ScreenManager screenManager;

    public FXMLDocumentController() {
        // Construtor vazio
    }

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
    @FXML
    private Button bntCadastrar;

    @FXML
    private Button bntEntrar;

    @FXML
    private Button bntSair;

    @FXML
    private Pane homePage;

    @FXML
    private ImageView imgLogo;

    
    @FXML
    void entrarButtonAction(ActionEvent event) {
        screenManager.switchScreen("/projecttcc_2/pkg0/View/FXMLInscrever.fxml");
    }
    
    @FXML
    void cadastrarButtonAction(ActionEvent event) {
        screenManager.switchScreen("/projecttcc_2/pkg0/View/FXMLCadastrar.fxml");
    }

    
    @FXML
    private void sairButtonAction(ActionEvent event) {
       System.exit(0); // Fechar o aplicativo
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /*    public void setScreenManager(ScreenManager screenManager) {
    this.screenManager = screenManager;
    }*/
}
