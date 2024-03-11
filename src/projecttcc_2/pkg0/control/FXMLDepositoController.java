/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.awt.event.MouseEvent;
import java.math.BigDecimal;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import projecttcc_2.BD.ConexaoBD;
import projecttcc_2.DTO.ProdutosDTO;  // Alteração do nome da classe
import projecttcc_2.DTO.ProdutosDTO;

/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLDepositoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        preencherTabela();
    }    
    
    @FXML
    private Button btnProduto;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private ImageView imgBuscar;

    @FXML
    private TableView<ProdutosDTO> tblProdutos;  // Alteração do tipo da tabela

    @FXML
    void buscarActionButton(MouseEvent event) {

    }

    @FXML
    void addProdutoActionButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLAddProdutos.fxml"));
            Parent root = loader.load();

            // Crie um novo controlador, se necessário  
            FXMLAddProdutosController addProdutosController = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Adicionar Produto");
            stage.setScene(new Scene(root));

            // Configurar mais propriedades da janela, se necessário
            // Impede a redimensionamento da janela
            stage.setResizable(false);

            stage.showAndWait(); // Mostrar a janela e esperar até que ela seja fechada
        } catch (Exception e) {
            e.printStackTrace(); // Lidar com exceções, como IOException ou FXMLLoaderException
        }
    }

    // Método para preencher a tabela
    private void preencherTabela() {
    try {
        Connection conexao = ConexaoBD.conectar();

        // Consulta SQL para obter informações dos produtos
        String sql = "SELECT p.id, p.nome, p.quantidade, p.preco, p.preco_venda, d.quantidade_estoque, p.fornecedor_id, f.nome as fornecedor_nome "
                + "FROM produtos p "
                + "LEFT JOIN deposito d ON p.id = d.produto_id "
                + "LEFT JOIN fornecedores f ON p.fornecedor_id = f.id";


        try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            // Criação do modelo da tabela
            ObservableList<ProdutosDTO> dadosTabela = FXCollections.observableArrayList();  // Alteração do tipo da lista

            // Adiciona as colunas à tabela
            TableColumn<ProdutosDTO, String> colunaNome = new TableColumn<>("Nome do Produto");
            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

            TableColumn<ProdutosDTO, BigDecimal> colunaPreco = new TableColumn<>("Preço(caixa)");
            colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
            colunaPreco.setCellFactory(column -> new TableCell<ProdutosDTO, BigDecimal>() {
                @Override
                protected void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText("R$ " + item);
                    }
                }
            });

            TableColumn<ProdutosDTO, BigDecimal> colunaPrecoVenda = new TableColumn<>("Preço de Venda(Unid)");
            colunaPrecoVenda.setCellValueFactory(new PropertyValueFactory<>("preco_venda"));
            colunaPrecoVenda.setCellFactory(column -> new TableCell<ProdutosDTO, BigDecimal>() {
                @Override
                protected void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText("R$ " + item);
                    }
                }
            });

            TableColumn<ProdutosDTO, String> colunaFornecedor = new TableColumn<>("Fornecedor");
            colunaFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor_nome"));
            colunaFornecedor.setCellFactory(column -> new TableCell<ProdutosDTO, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }
            });


            
            TableColumn<ProdutosDTO, Integer> colunaQuantidadeEstoque = new TableColumn<>("Quantidade em Estoque");
            colunaQuantidadeEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidade_estoque"));
            
            // Coluna de Edição
            TableColumn<ProdutosDTO, Void> colunaEditar = new TableColumn<>("Opções");
            colunaEditar.setCellFactory(new Callback<TableColumn<ProdutosDTO, Void>, TableCell<ProdutosDTO, Void>>() {
                @Override
                public TableCell<ProdutosDTO, Void> call(TableColumn<ProdutosDTO, Void> param) {
                    return new TableCell<ProdutosDTO, Void>() {
                        private final Button btnEditar = new Button("Editar");

                        {
                            btnEditar.setOnAction(event -> {
                                ProdutosDTO produtoDTO = getTableView().getItems().get(getIndex());
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLEditarEstoque.fxml"));
                                    Parent root = loader.load();

                                    // Crie um novo controlador, se necessário  
                                    FXMLEditarEstoqueController editarEstoqueController = loader.getController();

                                    Stage stage = new Stage();
                                    stage.initModality(Modality.APPLICATION_MODAL);
                                    stage.setTitle("Editar Produto");
                                    stage.setScene(new Scene(root));

                                    // Configurar mais propriedades da janela, se necessário
                                    // Impede a redimensionamento da janela
                                    stage.setResizable(false);

                                    stage.showAndWait(); // Mostrar a janela e esperar até que ela seja fechada
                                } catch (Exception e) {
                                    e.printStackTrace(); // Lidar com exceções, como IOException ou FXMLLoaderException
                                }
    
                            });

                            // Estilo para aumentar a fonte
                            btnEditar.setStyle("-fx-font-size: 14;");
                            
                        }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(btnEditar);
                            }
                        }
                    };
                }
            });

            // Tamanho fixo para a coluna "Opções"
            colunaEditar.setMaxWidth(145);
            colunaEditar.setMinWidth(65);

            tblProdutos.getColumns().addAll(colunaNome, colunaPreco, colunaPrecoVenda, colunaFornecedor, colunaQuantidadeEstoque, colunaEditar);


            // Ajuste do estilo CSS para as células de dados
           // tblProdutos.setStyle("-fx-font-size: 18;");
           // tblProdutos.setFixedCellSize(30);
           tblProdutos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            // Adiciona as linhas à tabela
            while (rs.next()) {
                ProdutosDTO produtoDTO = new ProdutosDTO(
                        Integer.parseInt(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getInt("quantidade"),
                        new BigDecimal(rs.getDouble("preco")),
                        new BigDecimal(rs.getDouble("preco_Venda")),
                        rs.getString("fornecedor_nome") 
                );

                produtoDTO.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                dadosTabela.add(produtoDTO);
            }

            tblProdutos.setItems(dadosTabela);
        }

        conexao.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela de produtos.");
    }
}

}
