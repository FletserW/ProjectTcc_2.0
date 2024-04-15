/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import projecttcc_2.BD.ConexaoBD;

/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLAddPedidosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Preencher o ComboBox com os nomes dos fornecedores do banco de dados
        preencherComboBoxFornecedores();
        preencherComboBoxProdutos();

        // Adicione isso para garantir que o valor padrão seja definido, evitando NullPointerException
        comboFornecedor.getSelectionModel().selectFirst();
        comboProdutos.getSelectionModel().selectFirst();
    }    
    
    @FXML
    private Button addContinuar;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConcluir;

    @FXML
    private Tab tabPedido;

    @FXML
    private Tab tabProdutos;
    
    @FXML
    private ComboBox<String> comboFornecedor;

    @FXML
    private ComboBox<String> comboProdutos;

    @FXML
    private DatePicker dateEntrega;

    @FXML
    private TextField txtPreco;

    @FXML
    private TextField txtQtd;

    @FXML
    void addActionButton(ActionEvent event) throws SQLException {
        // Obtendo os valores dos campos
        String nomeProduto = comboProdutos.getValue();
        int quantidade = Integer.parseInt(txtQtd.getText());

        // Obtendo o ID do último pedido adicionado ao banco de dados
        int idUltimoPedido = obterUltimoIdPedido();

        // Obtendo o ID do produto com base no nome
        int idProduto = obterIdProduto(nomeProduto);

        // Salvando os valores no banco de dados
        try (Connection conexao = ConexaoBD.conectar()) {
            String sql = "INSERT INTO PedidoItens (id_pedido, id_produto, quantidade) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, idUltimoPedido);
                stmt.setInt(2, idProduto);
                stmt.setInt(3, quantidade);

                // Executando a inserção
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Produto adicionado ao pedido com sucesso!");
                } else {
                    System.out.println("Falha ao adicionar o produto ao pedido.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao adicionar o produto ao pedido.");
        }
    }


    @FXML
    void cancelarActionButton(ActionEvent event) {
        fecharJanela(event);
    }

    @FXML
    void concluirActionButton(ActionEvent event) {
        fecharJanela(event);
    }

    @FXML
    void continuarActionButton(ActionEvent event) {
        // Obtendo os valores dos campos
        LocalDate dataPedido = dateEntrega.getValue();
        String fornecedor = comboFornecedor.getValue();
        BigDecimal valorTotal = new BigDecimal(txtPreco.getText());

        // Salvando os valores no banco de dados
        try (Connection conexao = ConexaoBD.conectar()) {
            String sql = "INSERT INTO Pedidos (data_pedido, id_fornecedor, valor_total) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setDate(1, Date.valueOf(dataPedido));
                // Você precisará obter o ID do fornecedor com base no nome dele
                int idFornecedor = obterIdFornecedor(fornecedor);
                stmt.setInt(2, idFornecedor);
                stmt.setBigDecimal(3, valorTotal);

                // Executando a inserção
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Pedido salvo com sucesso!");
                } else {
                    System.out.println("Falha ao salvar o pedido.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar o pedido.");
        }

        // Trocar para o tab "tabProdutos"
        tabProdutos.setDisable(false);
        tabPedido.getTabPane().getSelectionModel().select(tabProdutos);
        tabPedido.setDisable(true);
    }
    
    // Método para obter o ID do último pedido adicionado ao banco de dados
    private int obterUltimoIdPedido() throws SQLException {
        int idPedido = 0;
        Connection conexao = ConexaoBD.conectar();
        String sql = "SELECT MAX(id) AS ultimo_id FROM Pedidos";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idPedido = rs.getInt("ultimo_id");
                }
            }
        } finally {
            ConexaoBD.desconectar(conexao);
        }
        return idPedido;
    }

// Método para obter o ID do produto com base no nome
    private int obterIdProduto(String nomeProduto) throws SQLException {
        int idProduto = 0;
        Connection conexao = ConexaoBD.conectar();
        String sql = "SELECT id FROM Produtos WHERE nome = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idProduto = rs.getInt("id");
                }
            }
        } finally {
            ConexaoBD.desconectar(conexao);
        }
        return idProduto;
    }

// Método para obter o ID do fornecedor com base no nome dele
    private int obterIdFornecedor(String nomeFornecedor) throws SQLException {
        int idFornecedor = 0;
        Connection conexao = ConexaoBD.conectar();
        String sql = "SELECT id FROM fornecedores WHERE nome = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeFornecedor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idFornecedor = rs.getInt("id");
                }
            }
        } finally {
            ConexaoBD.desconectar(conexao);
        }
        return idFornecedor;
    }

    
    private void preencherComboBoxFornecedores() {
        ObservableList<String> fornecedores = FXCollections.observableArrayList();
        Connection conn = ConexaoBD.conectar();
        String query = "SELECT nome FROM fornecedores";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                fornecedores.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBD.desconectar(conn);
        }

        comboFornecedor.setItems(fornecedores);
    }

    private void preencherComboBoxProdutos() {
        ObservableList<String> produtos = FXCollections.observableArrayList();
        Connection conn = ConexaoBD.conectar();
        String query = "SELECT nome FROM produtos";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                produtos.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBD.desconectar(conn);
        }

        comboProdutos.setItems(produtos);
    }
    
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}

