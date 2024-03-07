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
        // Carrega a nova cena
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLInscrever.fxml"));
        Parent root = loader.load();
        
        // Obtém o palco atual
        Stage stage = (Stage) bntAcessar.getScene().getWindow();
        
        // Define a nova cena no palco
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
