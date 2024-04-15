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
        List<PedidosDTO> pedidos = recuperarPedidosDoBancoDeDados();

        // Criar um card para cada pedido
        for (PedidosDTO pedido : pedidos) {
            criarCardPedido(pedido);
        }
    }    
    
    private void criarCardPedido(PedidosDTO pedido) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/caminho/para/CardPedido.fxml"));
            Parent cardPedido = loader.load();

            // Obtendo o controlador do card
            FXMLCardPedidosController cardPedidoController = loader.getController();

            // Configurando os dados do pedido no card
            FXMLCardPedidosController.setIdPedido(pedido.getId());
            FXMLCardPedidosController.setDataPedido(pedido.getDataPedido());
            cardPedidoController.setFornecedor(pedido.getIdFornecedor());
            cardPedidoController.setPreco(pedido.getValorTotal());

            // Adicionando o card à lista de pedidos
            panePedidos.getChildren().add(cardPedido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     // Método para definir o ID do pedido no label
    public void setIdPedido(int id) {
        txtId.setText(String.valueOf(id));
    }
}
