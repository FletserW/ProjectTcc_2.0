/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import projecttcc_2.DTO.PedidosDTO;

/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLCardPedidosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Pane paneCardPedido;

    @FXML
    private Label txtData;

    @FXML
    private Label txtFornecedor;

    @FXML
    private Label txtId;

    @FXML
    private Label txtPreco;

    @FXML
    private Label txtProdutos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    
     // Método para definir o ID do pedido no label
    public void setIdPedido(int id) {
        txtId.setText(String.valueOf(id));
    }
}
