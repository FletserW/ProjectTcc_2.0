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

    private PedidosDTO pedido;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    public void setPedido(PedidosDTO pedido) {
        if (pedido != null) {
            this.pedido = pedido;

            // Preencher os campos do card com os dados do pedido
            if (txtId != null) {
                txtId.setText(String.valueOf(pedido.getId()));
            }
            if (txtData != null && pedido.getDataPedido() != null) {
                txtData.setText(pedido.getDataPedido().toString());
            }
            if (txtFornecedor != null) {
                txtFornecedor.setText(String.valueOf(pedido.getIdFornecedor()));
            }
            if (txtPreco != null && pedido.getValorTotal() != null) {
                txtPreco.setText(pedido.getValorTotal().toString());
            }
        } else {
            System.err.println("Pedido é nulo.");
        }
    }

}
