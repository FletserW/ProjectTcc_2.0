package projecttcc_2.pkg0.control;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projecttcc_2.BD.ConexaoBD;
import projecttcc_2.DTO.ProdutosDTO;


public class FXMLAddProdutosController implements Initializable {

    @FXML
    private Button addFornecedor;

    @FXML
    private Button bntAdicionar;
    
    @FXML
    private Button bntFornecedorCancelar;

    @FXML
    private Button bntCancelar;

    @FXML
    private ComboBox<String> comboFornecedor;

    @FXML
    private ImageView iconBeer;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPreco;

    @FXML
    private TextField txtPrecoUnid;

    @FXML
    private TextField txtQtd;
      
    @FXML
    private AnchorPane addFornecedorInclude;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Preencher o ComboBox com os nomes dos fornecedores do banco de dados
        preencherComboBoxFornecedores();

        // Adicione isso para garantir que o valor padrão seja definido, evitando NullPointerException
        comboFornecedor.getSelectionModel().selectFirst();
    }
    
    private FXMLDepositoController depositoController; // Referência do controlador da classe DepositoController

    // Método para definir o controlador da classe DepositoController
    public void setDepositoController(FXMLDepositoController depositoController) {
        this.depositoController = depositoController;
    }
    
    // Método para chamar o método limparTabela() da classe DepositoController
    public void limparTabelaDeposito() {
        if (depositoController != null) {
            depositoController.limparTabela();
        }
    }
    
    public void preencherTabelaDeposito() {
        if (depositoController != null) {
            depositoController.preencherTabela();
        }
    }

    @FXML
    void addFornecedorActionButton(ActionEvent event) {
        if (addFornecedorInclude.isVisible()) {
            // Se o addFornecedorInclude for visível, torne-o invisível
            addFornecedorInclude.setVisible(false);
            bntFornecedorCancelar.setVisible(false);

            // Reposicione a imagem para a posição inicial
            iconBeer.setLayoutY(iconBeer.getLayoutY() + 120);
        } else {
            // Se o addFornecedorInclude não estiver visível, torne-o visível
            addFornecedorInclude.setVisible(true);
            bntFornecedorCancelar.setVisible(true);

            // Ajuste a posição do iconBeer, se necessário
            iconBeer.setLayoutY(iconBeer.getLayoutY() - 120);
        }

        System.out.println("addFornecedorActionButton chamado");
    }


    @FXML
    void adicionarActionButton(ActionEvent event) {
        // Obter dados dos campos
        String nomeProduto = txtNome.getText();

        // Convertendo o texto do campo de texto para um número inteiro
        int quantidade = Integer.parseInt(txtQtd.getText());

        BigDecimal preco = new BigDecimal(txtPreco.getText());
        BigDecimal precoVenda = new BigDecimal(txtPrecoUnid.getText());
        int idFornecedor = comboFornecedor.getSelectionModel().getSelectedIndex() + 1; // +1 para compensar a indexação baseada em 1

        // Inserir o produto no banco de dados
        if (inserirProdutos(nomeProduto, quantidade, preco, precoVenda, idFornecedor)) {
            // Produto adicionado com sucesso, agora vamos atualizar a tabela
            exibirAlerta("Produto adicionado com sucesso!", Alert.AlertType.INFORMATION);
            // Obtendo a referência do FXMLDepositoController
            if (depositoController != null) {
                depositoController.atualizarTabela();
                depositoController.limparTabela();
                depositoController.preencherTabela();
                
            }

            fecharJanela(event);
        } else {
            exibirAlerta("Erro ao adicionar produto.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    void cancelarActionButton(ActionEvent event) {
        fecharJanela(event);
    }

    private void preencherComboBoxFornecedores() {
        ObservableList<String> fornecedores = FXCollections.observableArrayList();
        Connection conn = ConexaoBD.conectar();
        String query = "SELECT nome FROM fornecedores";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

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

    private boolean inserirProdutos(String nomeProduto, int quantidade, BigDecimal preco, BigDecimal precoVenda, int idFornecedor) {
        Connection conn = ConexaoBD.conectar();
        String query = "INSERT INTO produtos (nome, quantidade, preco, preco_venda, fornecedor_id, localizacao) VALUES (?, ?, ?, ?, ?, 'deposito')";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomeProduto);
            stmt.setInt(2, quantidade);
            stmt.setBigDecimal(3, preco);
            stmt.setBigDecimal(4, precoVenda);
            stmt.setInt(5, idFornecedor);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConexaoBD.desconectar(conn);
        }
    }

    private void exibirAlerta(String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
