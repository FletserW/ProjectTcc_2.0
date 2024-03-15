/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLGerenciarEstoqueController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private ToggleGroup acao;

    @FXML
    private Button bntCancelarEditar;

    @FXML
    private Button bntEditar;

    @FXML
    private ToggleGroup quantidade;

    @FXML
    private RadioButton raddioVender;

    @FXML
    private RadioButton radioAdicionar;

    @FXML
    private RadioButton radioCaixa;

    @FXML
    private RadioButton radioPerder;

    @FXML
    private RadioButton radioUnidade;

    @FXML
    private TextField txtQtd;

    @FXML
    void cancelarEditarActionButton(ActionEvent event) {
         fecharJanela(event);
    }

    @FXML
    void editarActionButton(ActionEvent event) {

    }

    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
