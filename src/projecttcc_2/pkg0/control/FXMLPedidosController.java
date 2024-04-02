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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLPedidosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private Button btnProduto;

    @FXML
    private ToggleGroup filtrar;

    @FXML
    private Pane paneCardPedido;

    @FXML
    private Pane panePedidos;

    @FXML
    private Label txtData;

    @FXML
    private Label txtFornecedor;

    @FXML
    private Label txtId;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Label txtPreco;

    @FXML
    private Label txtProdutos;

    @FXML
    void addPedidoActionButton(ActionEvent event) {

    }
}
