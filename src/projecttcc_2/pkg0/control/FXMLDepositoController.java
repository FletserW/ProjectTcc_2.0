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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import projecttcc_2.BD.ConexaoBD;
import projecttcc_2.DTO.ProdutosDTO;

/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLDepositoController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherTabela();
    }    
    
    @FXML
    private Button btnProduto;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private ImageView imgBuscar;

    @FXML
    private TableView<ProdutosDTO> tblProdutos;

    @FXML
    void buscarActionButton(MouseEvent event) {

    }

    @FXML
    void addProdutoActionButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLAddProdutos.fxml"));
            Parent root = loader.load();

            FXMLAddProdutosController addProdutosController = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Adicionar Produto");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherTabela() {
        try {
            
            // Limpar as colunas da tabela
            tblProdutos.getColumns().clear();

            // Limpar os dados da tabela existente
            tblProdutos.getItems().clear();
            
            Connection conexao = ConexaoBD.conectar();

            String sql = "SELECT p.id, p.nome, p.preco, p.preco_venda, d.quantidade_estoque AS quantidade_estoque, p.fornecedor_id, f.nome AS fornecedor_nome "
                    + "FROM produtos p "
                    + "LEFT JOIN deposito d ON p.id = d.produto_id "
                    + "LEFT JOIN fornecedores f ON p.fornecedor_id = f.id "
                    + "WHERE p.localizacao = 'deposito'";

            try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                ObservableList<ProdutosDTO> dadosTabela = FXCollections.observableArrayList();

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

                TableColumn<ProdutosDTO, Void> colunaOpcoes = new TableColumn<>("Opções");
                colunaOpcoes.setCellFactory(new Callback<TableColumn<ProdutosDTO, Void>, TableCell<ProdutosDTO, Void>>() {
                    @Override
                    public TableCell<ProdutosDTO, Void> call(TableColumn<ProdutosDTO, Void> param) {
                        return new TableCell<ProdutosDTO, Void>() {
                            private final Button btnEditar = new Button("Editar");
                            private final Button btnGerenciar = new Button("Gerenciar");

                            {
                                btnEditar.setOnAction(event -> {
                                    ProdutosDTO produtoSelecionado = getTableView().getItems().get(getIndex());
                                    if (produtoSelecionado != null) {
                                        // Aqui você pode abrir a janela de edição com os dados do produto selecionado
                                        try {
                                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLEditarEstoque.fxml"));
                                            Parent root = loader.load();

                                            FXMLEditarEstoqueController editarEstoqueController = loader.getController();
                                            editarEstoqueController.setProdutoSelecionado(produtoSelecionado);

                                            Stage stage = new Stage();
                                            stage.initModality(Modality.APPLICATION_MODAL);
                                            stage.setTitle("Editar Produto");
                                            stage.setScene(new Scene(root));
                                            stage.setResizable(false);

                                            stage.showAndWait();

                                            // Atualize a tabela após a edição
                                            preencherTabela();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                btnGerenciar.setOnAction(event -> {
                                    ProdutosDTO produtoDTO = getTableView().getItems().get(getIndex());
                                    try {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLGerenciarEstoque.fxml"));
                                        Parent root = loader.load();

                                        FXMLGerenciarEstoqueController gerenciarEstoqueController = loader.getController();

                                        Stage stage = new Stage();
                                        stage.initModality(Modality.APPLICATION_MODAL);
                                        stage.setTitle("Gerenciar Produto");
                                        stage.setScene(new Scene(root));
                                        stage.setResizable(false);

                                        stage.showAndWait();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });

                                btnEditar.setStyle("-fx-font-size: 14;");
                                btnGerenciar.setStyle("-fx-font-size: 14;");
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    HBox hbox = new HBox(btnEditar, btnGerenciar);
                                    hbox.setSpacing(5);
                                    setGraphic(hbox);
                                }
                            }
                        };
                    }
                });


                colunaOpcoes.setMaxWidth(170);
                colunaOpcoes.setMinWidth(170);

                tblProdutos.getColumns().addAll(colunaNome, colunaPreco, colunaPrecoVenda, colunaFornecedor,colunaQuantidadeEstoque, colunaOpcoes);

                tblProdutos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                
                while (rs.next()) {
                    ProdutosDTO produtoDTO = new ProdutosDTO(
                            Integer.parseInt(rs.getString("id")),
                            rs.getString("nome"),
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
