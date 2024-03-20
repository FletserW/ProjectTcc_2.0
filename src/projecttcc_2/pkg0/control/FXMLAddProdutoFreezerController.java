package projecttcc_2.pkg0.control;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import projecttcc_2.BD.ConexaoBD;
import projecttcc_2.DTO.ProdutosDTO;

public class FXMLAddProdutoFreezerController implements Initializable {

    @FXML
    private Button btnAdicionarProdutoFreezer;

    @FXML
    private Button btnCancelarProdutoFreezer;

    @FXML
    private TableView<ProdutosDTO> tblNovoProdutoFreezer;

    @FXML
    private TextField txtPesquisarFreezer;

    private FXMLFreezerController freezerController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Oculta os títulos das colunas da TableView
        tblNovoProdutoFreezer.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblNovoProdutoFreezer.setStyle("-fx-table-header-background: transparent;");

        // Adiciona uma coluna para exibir o nome do produto
        TableColumn<ProdutosDTO, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Adiciona a coluna à TableView
        tblNovoProdutoFreezer.getColumns().add(colunaNome);

        // Preenche a tabela com os produtos no depósito
        preencherTabela();

        // Oculta o cabeçalho da TableView
        tblNovoProdutoFreezer.widthProperty().addListener((obs, oldVal, newVal) -> {
            Pane header = (Pane) tblNovoProdutoFreezer.lookup("TableHeaderRow");
            if (header != null && header.isVisible()) {
                header.setMaxHeight(0);
                header.setMinHeight(0);
                header.setPrefHeight(0);
                header.setVisible(false);
            }
        });
    }


    @FXML
    void AdicionarProdutoActionButton(ActionEvent event) {

    }

    @FXML
    void CancelarActionButton(ActionEvent event) {
       fecharJanela(event);
    }

    public void setFreezerController(FXMLFreezerController freezerController) {
        this.freezerController = freezerController;
    }

    public void preencherTabela() {
        try {
            Connection conexao = ConexaoBD.conectar();

            String sql = "SELECT nome FROM produtos WHERE localizacao = 'deposito'";
            try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                ObservableList<ProdutosDTO> dadosTabela = FXCollections.observableArrayList();

                while (rs.next()) {
                    String nomeProduto = rs.getString("nome");
                    // Cria um objeto ProdutosDTO apenas com o nome do produto
                    ProdutosDTO produto = new ProdutosDTO();
                    produto.setNome(nomeProduto);
                    // Adiciona o produto à lista de dados da tabela
                    dadosTabela.add(produto);
                }

                tblNovoProdutoFreezer.setItems(dadosTabela);
            }

            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

 
