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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projecttcc_2.BD.ConexaoBD;
import projecttcc_2.DTO.PedidosDTO;
import projecttcc_2.DTO.ItemPedidoDTO;

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
        // Chamado quando a janela é inicializada
        // Aqui você pode buscar os pedidos do banco de dados e exibi-los
        List<PedidosDTO> pedidos = buscarPedidosDoBancoDeDados(); // Chamada corrigida

        exibirPedidos(pedidos);
    }

    @FXML
    private Button btnProduto;

    @FXML
    private ToggleGroup filtrar;

    @FXML
    private RadioButton radioEntregue;

    @FXML
    private RadioButton radioPendente;

    @FXML
    private RadioButton radioTodos;

    @FXML
    private GridPane gridPedidos;

    @FXML
    private TextField txtPesquisa;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void radioFiltrarPedidos(ActionEvent event) {
        
        System.out.println("Radio Filtrar Pedidos");    
        if (radioEntregue.isSelected()) {
            List<PedidosDTO> pedidos = buscarPedidosDoBancoDeDados(); // Chamada corrigida
            System.out.println("Radio Entrada");
            exibirPedidos(pedidos);
        } else if (radioPendente.isSelected()) {
            List<PedidosDTO> pedidos = buscarPedidosDoBancoDeDados(); // Chamada corrigida
            exibirPedidos(pedidos);
            System.out.println("Radio Pendente");
        } else {
            List<PedidosDTO> pedidos = buscarPedidosDoBancoDeDados(); // Chamada corrigida
            exibirPedidos(pedidos);
            System.out.println("Radio todos");
        }
     
    }

    // Método para buscar os pedidos do banco de dados
    private List<PedidosDTO> buscarPedidosDoBancoDeDados() { // Parâmetro removido
        List<PedidosDTO> pedidos = new ArrayList<>();

        try (Connection conexao = ConexaoBD.conectar()) {
            if (conexao != null) {
                String sql;
                // Verifica qual botão de rádio está selecionado para determinar o status
                if (radioEntregue.isSelected()) {
                    sql = "SELECT p.id, p.data_pedido, p.id_fornecedor, f.nome AS nome_fornecedor, p.valor_total "
                            + "FROM Pedidos p "
                            + "JOIN Fornecedores f ON p.id_fornecedor = f.id "
                            + "WHERE p.status = 'concluido'";
                } else if (radioPendente.isSelected()) {
                    sql = "SELECT p.id, p.data_pedido, p.id_fornecedor, f.nome AS nome_fornecedor, p.valor_total "
                            + "FROM Pedidos p "
                            + "JOIN Fornecedores f ON p.id_fornecedor = f.id "
                            + "WHERE p.status = 'pendente'";
                } else {
                    sql = "SELECT p.id, p.data_pedido, p.id_fornecedor, f.nome AS nome_fornecedor, p.valor_total "
                            + "FROM Pedidos p "
                            + "JOIN Fornecedores f ON p.id_fornecedor = f.id";
                }
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

    // Método para criar e exibir os cards de pedidos
    public void exibirPedidos(List<PedidosDTO> pedidos) {
        int row = 0;
        int col = 0;
        int maxCols = 5; // Máximo de colunas por linha

        // Configura as colunas para terem o mesmo tamanho
        for (int i = 0; i < maxCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(110.0 / maxCols); // Divide igualmente o espaço
            gridPedidos.getColumnConstraints().add(colConstraints);
        }

        for (PedidosDTO pedido : pedidos) {
            // Carrega os dados do pedido para o card
            Pane cardPedido = criarCardPedido(pedido);

            // Adiciona o card ao GridPane
            gridPedidos.add(cardPedido, col, row);

            // Atualiza a posição para o próximo card
            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }
    }

    // Método para criar um card de pedido
    private Pane criarCardPedido(PedidosDTO pedido) {
        // Crie um novo Pane (você pode usar o FXMLLoader para carregar o FXML se preferir)
        Pane paneCardPedido = new Pane();
        paneCardPedido.setPrefHeight(237);
        paneCardPedido.setPrefWidth(169);
        paneCardPedido.setStyle("-fx-background-color: #fffff5; -fx-background-radius: 20;");
        paneCardPedido.getStyleClass().add("anchoShadow");

        // Crie os Labels com as informações do pedido
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

        // Adicione os Labels ao Pane
        paneCardPedido.getChildren().addAll(lblId, lblData, lblFornecedor, lblPreco, lblProdutos);

        // Adicione um evento de clique ao card
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

            // Busca os itens do pedido do banco de dados
            List<ItemPedidoDTO> itensPedido = buscarItensPedidoDoBancoDeDados(pedido.getId());

            // Configura a lista de itens do pedido no controlador do checklist
            checklistController.configurarItensPedido(itensPedido);

            // Cria uma nova janela
            Stage stage = new Stage();
            stage.setTitle("Checklist do Pedido");

            // Define a cena com o layout do checklist
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Mostra a janela
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Método para buscar os itens do pedido do banco de dados
    private List<ItemPedidoDTO> buscarItensPedidoDoBancoDeDados(int idPedido) {
        List<ItemPedidoDTO> itensPedido = new ArrayList<>();

        // Conectar ao banco de dados e executar a consulta SQL para recuperar os itens do pedido
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
                            // Você precisará buscar o nome do produto com base no idProduto
                            // Aqui estou assumindo que você tem um método para isso
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

    // Método para buscar o nome do produto com base no ID do produto
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
