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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import projecttcc_2.BD.DepositoDAO;
import projecttcc_2.BD.ProdutosDAO;
import projecttcc_2.DTO.ProdutosDTO;


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
    
    private TableView<ProdutosDTO> tblProdutos; // Declare uma variável para armazenar a referência da tabela

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
            }else{
                int quantidadeDigitada = Integer.parseInt(txtQtdDeposito.getText());
                if (radioCaixa.isSelected()) {
                    quantidadeDigitada *= produtoSelecionado.getQuantidade();
                }

                if (radioAdicionar.isSelected()) {
                    produtoSelecionado.setQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque() + quantidadeDigitada);

                } else if (raddioVender.isSelected()) {
                    produtoSelecionado.setQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque() - quantidadeDigitada);
                } else if (radioPerder.isSelected()) {
                    produtoSelecionado.setQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque() - quantidadeDigitada);
                }

                DepositoDAO depositoDAO = new DepositoDAO();
                // Atualizar o estoque no banco de dados utilizando o DepositoDAO
                boolean atualizadoComSucesso = DepositoDAO.atualizarQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque(), produtoSelecionado.getId());

            }
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
