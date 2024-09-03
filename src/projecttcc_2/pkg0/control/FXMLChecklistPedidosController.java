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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import projecttcc_2.BD.DepositoDAO;
import projecttcc_2.BD.PedidosDAO;
import projecttcc_2.DTO.ItemPedidoDTO;
import projecttcc_2.DTO.PedidosDTO;

public class FXMLChecklistPedidosController implements Initializable {

    @FXML
    private VBox checkboxContainer;

    private List<ItemPedidoDTO> itensPedido;
    
    private List<PedidosDTO> pedidos;
    
     private int pedidoId;
    
    @FXML
    private Button btnConcluir;
    
    @FXML
    void concluirActionButton(ActionEvent event) {
        for (Node node : checkboxContainer.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) {
                    String nomeProduto = checkBox.getText().split(" - ")[0]; // Obtém o nome do produto do texto do checkbox
                    int quantidadeSelecionada = Integer.parseInt(checkBox.getText().split(" - ")[1].replace("Quantidade: ", "")); // Obtém a quantidade selecionada do texto do checkbox
                    DepositoDAO.atualizarQuantidadeEstoque2(nomeProduto, quantidadeSelecionada); // Atualiza a quantidade em estoque no banco de dados

                    // Obtém o ID do pedido selecionado a partir do checkBox
                    int idPedidoSelecionado = obterIdPedidoSelecionado(nomeProduto);

                    // Chama o método para concluir o pedido passando o ID do pedido
                    PedidosDAO.concluirPedido(idPedidoSelecionado);
                }
            }
        }
        PedidosDAO.concluirPedido(pedidoId);
        fecharJanela(event);
    }

// Método para obter o ID do pedido selecionado a partir do nome do produto
    private int obterIdPedidoSelecionado(String nomeProduto) {
        // Verifica se pedidos está inicializado
        if (pedidos != null) {
            // Itera sobre a lista de pedidos para encontrar o pedido correspondente ao nome do produto
            for (PedidosDTO pedido : pedidos) {
                // Se o nome do produto do pedido for igual ao nome do produto passado como parâmetro
                // Retorna o ID desse pedido
                if (pedido.getNomeFornecedor().equals(nomeProduto)) {
                    return pedido.getId();
                }
            }
        }
        return -1; // Retorna -1 se não encontrar o pedido correspondente ou se pedidos for nulo
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
    
    public void setPedidos(List<PedidosDTO> pedidos) {
        this.pedidos = pedidos;
    }
    
     public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    // Método chamado quando o botão Fechar é clicado
    @FXML
    void fecharJanela(ActionEvent event) {
        // Fecha a janela atual
        checkboxContainer.getScene().getWindow().hide();
    }
}
