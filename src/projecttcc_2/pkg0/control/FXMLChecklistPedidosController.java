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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import projecttcc_2.DTO.ItemPedidoDTO;

public class FXMLChecklistPedidosController implements Initializable {

    @FXML
    private VBox checkboxContainer;

    private List<ItemPedidoDTO> itensPedido;
    
    @FXML
    private Button btnConcluir;
    
    @FXML
    void concluirActionButton(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nenhum item é exibido inicialmente
    }

    // Método para configurar a lista de itens do pedido
    public void configurarItensPedido(List<ItemPedidoDTO> itensPedido) {
        this.itensPedido = itensPedido;
        exibirItensPedido();
    }

    // Método para exibir os itens do pedido como checkboxes
    private void exibirItensPedido() {
        checkboxContainer.getChildren().clear(); // Limpa quaisquer checkboxes existentes

        for (ItemPedidoDTO item : itensPedido) {
            CheckBox checkBox = new CheckBox(item.getNomeProduto() + " - Quantidade: " + item.getQuantidade());
            checkboxContainer.getChildren().add(checkBox);
        }
    }

    // Método chamado quando o botão Fechar é clicado
    @FXML
    void fecharJanela(ActionEvent event) {
        // Fecha a janela atual
        checkboxContainer.getScene().getWindow().hide();
    }
}
