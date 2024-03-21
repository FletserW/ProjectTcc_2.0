package projecttcc_2.pkg0.control;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
    
    // Declaração do campo dadosTabela
    private ObservableList<ProdutosDTO> dadosTabela = FXCollections.observableArrayList();

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
        // Adiciona um ouvinte para monitorar as alterações no texto de pesquisa
        txtPesquisarFreezer.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filtrarTabela(newValue);
        });
    }


    @FXML
    void AdicionarProdutoActionButton(ActionEvent event) {
        System.out.println("Adicionando o novo produto");
        // Obtém o produto selecionado na tabela
        ProdutosDTO produtoSelecionado = tblNovoProdutoFreezer.getSelectionModel().getSelectedItem();

        // Verifica se há um produto selecionado
        if (produtoSelecionado != null) {
            // Atualiza a localização do produto para "freezer"
            produtoSelecionado.setLocalizacao("freezer");

            // Atualiza a localização do produto no banco de dados
            atualizarLocalizacaoNoBanco(produtoSelecionado);

            // Atualiza a exibição da tabela
            tblNovoProdutoFreezer.refresh();

            // Preenche novamente a tabela para garantir que os dados estejam atualizados
            preencherTabela();
            
            // Atualiza a exibição da tabela na janela FXMLFreezer
            if (freezerController != null) {
                freezerController.atualizarTabela();
            }
        }
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
                // Não declare a variável dadosTabela novamente aqui
                //ObservableList<ProdutosDTO> dadosTabela = FXCollections.observableArrayList();

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
    
    private void filtrarTabela(String textoPesquisa) {
        // Se o texto de pesquisa estiver vazio, mostra todos os itens
        if (textoPesquisa == null || textoPesquisa.isEmpty()) {
            tblNovoProdutoFreezer.setItems(dadosTabela);
            return;
        }

        // Obtém os itens da tabela
        //ObservableList<ProdutosDTO> dadosTabela = tblNovoProdutoFreezer.getItems(); // Remova esta linha

        // Cria um filtro para os itens da tabela
        FilteredList<ProdutosDTO> filteredData = new FilteredList<>(dadosTabela, p -> true);

        // Define o predicado de filtragem com base no texto de pesquisa
        filteredData.setPredicate(produto -> {
            // Converte o texto de pesquisa e o nome do produto para minúsculas para tornar a pesquisa insensível a maiúsculas e minúsculas
            String lowerCaseFilter = textoPesquisa.toLowerCase();
            return produto.getNome().toLowerCase().contains(lowerCaseFilter);
        });

        // Atualiza a exibição da tabela com os dados filtrados
        tblNovoProdutoFreezer.setItems(filteredData);
    }
    
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        
        // Atualiza a tabela na janela FXMLFreezer quando a janela for fechada
        if (freezerController != null) {
            freezerController.atualizarTabela();
        }
    }
    
    private void atualizarLocalizacaoNoBanco(ProdutosDTO produto) {
        try {
            Connection conexao = ConexaoBD.conectar();
            Statement stmt = conexao.createStatement();
            System.out.println("Produto id: "+ produto.getId());
            // Atualiza a localização do produto no banco de dados
            String sql = "UPDATE produtos SET localizacao = 'freezer' WHERE nome = '" + produto.getNome() + "'";
            stmt.executeUpdate(sql);

            // Fecha a conexão com o banco de dados
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
