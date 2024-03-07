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

/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLCadastrarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private Button bntAcessar;

    @FXML
    private Button bntRegistrar;

    @FXML
    private Button btnFacebook;

    @FXML
    private Button btnGoogle;
    
     @FXML
    void acessarButtonAction(ActionEvent event) {
        try {
            // Carrega o FXML da segunda tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInscrever.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da segunda tela
            FXMLInscreverController controller = loader.getController();

            // Obtém a cena atual
            Scene scene = bntAcessar.getScene();

            // Substitui a cena atual pela cena da segunda tela
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cadastroFacebook(ActionEvent event) {

    }

    @FXML
    void cadastroGoogle(ActionEvent event) {

    }

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
