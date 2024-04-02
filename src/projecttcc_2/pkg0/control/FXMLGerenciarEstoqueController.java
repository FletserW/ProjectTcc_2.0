/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import projecttcc_2.BD.DepositoDAO;
import projecttcc_2.BD.ProdutosDAO;
import projecttcc_2.DTO.ProdutosDTO;
import java.time.LocalDate; // Importe a classe LocalDate para obter a data atual
import projecttcc_2.BD.VendasDAO;


/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLGerenciarEstoqueController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private ToggleGroup acao;

    @FXML
    private Button bntCancelarEditar;

    @FXML
    private Button bntEditar;

    @FXML
    private ToggleGroup quantidade;

    @FXML
    private RadioButton raddioVender;

    @FXML
    private RadioButton radioAdicionar;

    @FXML
    private RadioButton radioCaixa;

    @FXML
    private RadioButton radioPerder;

    @FXML
    private RadioButton radioUnidade;

    @FXML
    public TextField txtQtdDeposito;
    
    @FXML
    private TextField txtQuantidadeEstoque;
    
    private ProdutosDTO produtoSelecionado;
    
    private TableView<ProdutosDTO> tblProdutos; // Declare uma variável para armazenar a referência da tabela
    
    @FXML
    private FXMLDepositoController depositoController;

    @FXML
    void cancelarEditarActionButton(ActionEvent event) {
         fecharJanela(event);
    }

    @FXML
    void editarActionButton(ActionEvent event) {
        // Obter o produto selecionado na tabela
        ProdutosDTO produtoSelecionado = tblProdutos.getSelectionModel().getSelectedItem();
        

        // Verificar se um produto foi selecionado
        if (produtoSelecionado != null) {
            if (txtQtdDeposito.getText().isEmpty()) {
                exibirMensagemErro("Campo Vazio", "Por favor, insira uma quantidade antes de editar o estoque.");
                return;
            }

            int quantidadeDigitada = Integer.parseInt(txtQtdDeposito.getText());

            // Verificar se a quantidade digitada é maior que 0
            if (quantidadeDigitada <= 0) {
                exibirMensagemErro("Quantidade Inválida", "A quantidade digitada deve ser maior que 0.");
                return;
            }

            // Verificar se a opção "radioCaixa" está selecionada
            if (radioCaixa.isSelected()) {
                // Multiplicar pela quantidade do produto se a opção "radioCaixa" estiver selecionada
                quantidadeDigitada *= produtoSelecionado.getQuantidade();
            }

            if (radioAdicionar.isSelected()) {
                System.out.println(produtoSelecionado.getQuantidadeEstoque() + quantidadeDigitada);
                produtoSelecionado.setQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque() + quantidadeDigitada);
            } else if (raddioVender.isSelected()) {
                produtoSelecionado.setQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque() - quantidadeDigitada);
                
                VendasDAO vendasDAO = new VendasDAO();
                if (vendasDAO.existeRegistroParaProdutoNoMesAtual(produtoSelecionado.getId())) {
                    // Atualize o registro existente com a nova quantidade vendida
                    boolean atualizadoComSucesso = vendasDAO.atualizarQuantidadeVendida(produtoSelecionado.getId(), quantidadeDigitada);

                    if (atualizadoComSucesso) {
                        System.out.println("Quantidade vendida atualizada com sucesso!");
                    } else {
                        System.out.println("Erro ao atualizar quantidade vendida.");
                    }
                } else {
                    // Se não houver um registro para o produto no mês atual, adicione um novo registro
                    LocalDate dataAtual = LocalDate.now(); // Obtenha a data atual
                    String mesAno = String.format("%02d/%d", dataAtual.getMonthValue(), dataAtual.getYear()); // Formate o mês/ano
                    boolean adicionadoComSucesso = vendasDAO.adicionarProdutoVendido(produtoSelecionado.getId(), quantidadeDigitada, mesAno);


                    if (adicionadoComSucesso) {
                        System.out.println("Produto vendido adicionado com sucesso!");
                    } else {
                        System.out.println("Erro ao adicionar produto vendido.");
                    }
                }
                    
            } else if (radioPerder.isSelected()) {
                produtoSelecionado.setQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque() - quantidadeDigitada);
            }
            
            // Em algum lugar onde você precisa chamar preencherTabela()
            if (depositoController != null) {
                depositoController.preencherTabela();
            }

            DepositoDAO depositoDAO = new DepositoDAO();
            // Atualizar o estoque no banco de dados utilizando o DepositoDAO
            boolean atualizadoComSucesso = DepositoDAO.atualizarQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque(), produtoSelecionado.getId());
        } else {
            // Exibir uma mensagem de erro ao usuário informando que nenhum produto foi selecionado
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um produto para editar.");
            alert.showAndWait();
        }
    }

    // Método para receber a referência do tblProdutos do FXMLDepositoController
    public void setTabelaProdutos(TableView<ProdutosDTO> tblProdutos) {
        this.tblProdutos = tblProdutos;
    }
    
    public void setDepositoController(FXMLDepositoController depositoController) {
    this.depositoController = depositoController;
}
    
    // Método para definir o produto selecionado
    public void setProdutoSelecionado(ProdutosDTO produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }
    
    private void exibirMensagemErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
