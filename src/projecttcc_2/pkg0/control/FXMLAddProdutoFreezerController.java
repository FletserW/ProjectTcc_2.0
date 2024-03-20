/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLAddProdutoFreezerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private Button btnAdicionarProdutoFreezer;

    @FXML
    private Button btnCancelarProdutoFreezer;

    @FXML
    private TableView<?> tblNovoProdutoFreezer;

    @FXML
    private TextField txtPesquisarFreezer;
    
    private FXMLFreezerController freezerController;

    
    @FXML
    void AdicionarProdutoActionButton(ActionEvent event) {

    }

    @FXML
    void CancelarActionButton(ActionEvent event) {

    }
    
    
    public void setFreezerController(FXMLFreezerController freezerController) {
        this.freezerController = freezerController;
    }

}

