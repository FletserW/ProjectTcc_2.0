package projecttcc_2.pkg0.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projecttcc_2.BD.DepositoDAO;
import projecttcc_2.BD.ProdutosDAO;
import projecttcc_2.DTO.ProdutosDTO;

import java.math.BigDecimal;
import javafx.scene.control.Button;

public class FXMLEditarEstoqueController {

    @FXML
    private Button bntCancelarEditar;

    @FXML
    private Button bntEditar;

    @FXML
    private TextField txtNomeEditar;

    @FXML
    private TextField txtPrecoEditar;

    @FXML
    private TextField txtPrecoVendaEditar;

    @FXML
    private TextField txtQtdEditar;

    private ProdutosDTO produtoSelecionado;
    private ProdutosDAO produtosDAO;
    private DepositoDAO depositoDAO;

    public FXMLEditarEstoqueController() {
        this.produtosDAO = new ProdutosDAO();
        this.depositoDAO = new DepositoDAO();
    }

    public void setProdutoSelecionado(ProdutosDTO produtoDTO) {
        this.produtoSelecionado = produtoDTO;
        txtNomeEditar.setText(produtoDTO.getNome());
        txtPrecoEditar.setText(produtoDTO.getPreco().toString());
        txtPrecoVendaEditar.setText(produtoDTO.getPreco_venda().toString());
        txtQtdEditar.setText(String.valueOf(produtoDTO.getQuantidadeEstoque()));
    }

    @FXML
    void cancelarEditarActionButton(ActionEvent event) {
        Stage stage = (Stage) txtNomeEditar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void editarActionButton(ActionEvent event) {
        try {
            // Obter os novos valores dos campos de texto
            String novoNome = txtNomeEditar.getText();
            BigDecimal novoPreco = new BigDecimal(txtPrecoEditar.getText());
            BigDecimal novoPrecoVenda = new BigDecimal(txtPrecoVendaEditar.getText());
            int novaQuantidade = Integer.parseInt(txtQtdEditar.getText());

            // Atualizar o produtoSelecionado
            produtoSelecionado.setNome(novoNome);
            produtoSelecionado.setPreco(novoPreco);
            produtoSelecionado.setPreco_venda(novoPrecoVenda);
            produtoSelecionado.setQuantidadeEstoque(novaQuantidade);

            // Atualizar o produto no banco de dados
            produtosDAO.atualizarProduto(produtoSelecionado);

            // Atualizar a quantidade no depósito no banco de dados
            depositoDAO.atualizarQuantidade(produtoSelecionado.getId(), novaQuantidade);

            // Fechar a janela de edição
            Stage stage = (Stage) txtNomeEditar.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            // Tratar caso o usuário tenha digitado um valor inválido para algum campo numérico
            e.printStackTrace();
        } catch (Exception e) {
            // Tratar outras exceções ou exibir uma mensagem de erro
            e.printStackTrace();
        }
    }

}
