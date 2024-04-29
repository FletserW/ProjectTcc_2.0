package projecttcc_2.pkg0.control;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projecttcc_2.BD.ConexaoBD;
import projecttcc_2.DTO.PedidosDTO;
import projecttcc_2.DTO.ItemPedidoDTO;

public class FXMLPedidosController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<PedidosDTO> pedidos = buscarPedidosDoBancoDeDados();
        exibirPedidos(pedidos);
    }

    @FXML
    private Button btnProduto;

    @FXML
    private GridPane gridPedidos;


    @FXML
    void addPedidoActionButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLAddPedidos.fxml"));
            Parent root = loader.load();

            FXMLAddPedidosController addPedidosController = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Adicionar Produto");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            stage.showAndWait();
            atualizarPedidos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void atualizarPedidos() {
        List<PedidosDTO> pedidos = buscarPedidosDoBancoDeDados();
        gridPedidos.getChildren().clear(); // Limpa os pedidos antigos
        exibirPedidos(pedidos);
    }


    private List<PedidosDTO> buscarPedidosDoBancoDeDados() {
        List<PedidosDTO> pedidos = new ArrayList<>();

        try (Connection conexao = ConexaoBD.conectar()) {
            if (conexao != null) {
                String sql = "SELECT p.id, p.data_pedido, p.id_fornecedor, f.nome AS nome_fornecedor, p.valor_total "
                        + "FROM Pedidos p "
                        + "JOIN Fornecedores f ON p.id_fornecedor = f.id "
                        + "WHERE p.status = 'pendente'";

                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            Date dataPedido = rs.getDate("data_pedido");
                            int idFornecedor = rs.getInt("id_fornecedor");
                            String nomeFornecedor = rs.getString("nome_fornecedor");
                            BigDecimal valorTotal = rs.getBigDecimal("valor_total");

                            PedidosDTO pedido = new PedidosDTO(id, dataPedido, idFornecedor, nomeFornecedor, valorTotal);
                            pedidos.add(pedido);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return pedidos;
    }

    public void exibirPedidos(List<PedidosDTO> pedidos) {
        int row = 0;
        int col = 0;
        int maxCols = 5;

        for (int i = 0; i < maxCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(100.0 / maxCols);
            gridPedidos.getColumnConstraints().add(colConstraints);
        }

        for (PedidosDTO pedido : pedidos) {
            Pane cardPedido = criarCardPedido(pedido);
            gridPedidos.add(cardPedido, col, row);
            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }
    }

    private Pane criarCardPedido(PedidosDTO pedido) {
        Pane paneCardPedido = new Pane();
        paneCardPedido.setPrefHeight(237);
        paneCardPedido.setPrefWidth(169);
        paneCardPedido.setStyle("-fx-background-color: #fffff5; -fx-background-radius: 20;");
        paneCardPedido.getStyleClass().add("anchoShadow");

        Label lblId = new Label("Pedido: N°" + pedido.getId());
        lblId.setLayoutX(40);
        lblId.setLayoutY(14);

        Label lblData = new Label("Data: " + pedido.getDataPedido());
        lblData.setLayoutX(14);
        lblData.setLayoutY(47);

        Label lblFornecedor = new Label("Fornecedor: " + pedido.getNomeFornecedor());
        lblFornecedor.setLayoutX(14);
        lblFornecedor.setLayoutY(78);

        Label lblPreco = new Label("Preço: " + pedido.getValorTotal());
        lblPreco.setLayoutX(14);
        lblPreco.setLayoutY(107);

        Label lblProdutos = new Label("Produtos: +");
        lblProdutos.setLayoutX(14);
        lblProdutos.setLayoutY(136);

        paneCardPedido.getChildren().addAll(lblId, lblData, lblFornecedor, lblPreco, lblProdutos);

        paneCardPedido.setOnMouseClicked(event -> {
            abrirJanelaItensPedido(pedido);
        });

        return paneCardPedido;
    }

    private void abrirJanelaItensPedido(PedidosDTO pedido) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLChecklistPedidos.fxml"));
            Parent root = loader.load();

            FXMLChecklistPedidosController checklistController = loader.getController();

            List<ItemPedidoDTO> itensPedido = buscarItensPedidoDoBancoDeDados(pedido.getId());
            checklistController.configurarItensPedido(itensPedido);
            checklistController.setPedidoId(pedido.getId()); // Passa o ID do pedido para o controlador do checklist

            Stage stage = new Stage();
            stage.setTitle("Checklist do Pedido");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.showAndWait();
            atualizarPedidos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<ItemPedidoDTO> buscarItensPedidoDoBancoDeDados(int idPedido) {
        List<ItemPedidoDTO> itensPedido = new ArrayList<>();

        try (Connection conexao = ConexaoBD.conectar()) {
            if (conexao != null) {
                String sql = "SELECT * FROM PedidoItens WHERE id_pedido = ?";
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setInt(1, idPedido);
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            int idItem = rs.getInt("id");
                            int idProduto = rs.getInt("id_produto");
                            int quantidade = rs.getInt("quantidade");
                            String nomeProduto = buscarNomeProduto(idProduto);

                            ItemPedidoDTO item = new ItemPedidoDTO(idItem, idPedido, idProduto, quantidade, nomeProduto);
                            itensPedido.add(item);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return itensPedido;
    }

    private String buscarNomeProduto(int idProduto) {
        String nomeProduto = "";

        try (Connection conexao = ConexaoBD.conectar()) {
            String sql = "SELECT nome FROM produtos WHERE id = ?";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, idProduto);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        nomeProduto = rs.getString("nome");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return nomeProduto;
    }
}
