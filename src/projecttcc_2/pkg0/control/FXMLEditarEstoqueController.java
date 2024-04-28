package projecttcc_2.pkg0.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projecttcc_2.BD.DepositoDAO;
import projecttcc_2.DTO.DepositoDTO;
import projecttcc_2.BD.ProdutosDAO;
import projecttcc_2.DTO.ProdutosDTO;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
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


    private ProdutosDTO produtoSelecionado;
    private ProdutosDAO produtosDAO;
    private DepositoDAO depositoDAO;
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  

    public FXMLEditarEstoqueController() {
        this.produtosDAO = new ProdutosDAO();
        this.depositoDAO = new DepositoDAO();
    }

    public void setProdutoSelecionado(ProdutosDTO produtoDTO) {
        this.produtoSelecionado = produtoDTO;
        txtNomeEditar.setText(produtoDTO.getNome());
        txtPrecoEditar.setText(produtoDTO.getPreco().toString());
        txtPrecoVendaEditar.setText(produtoDTO.getPreco_venda().toString());
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

            // Atualizar os valores do produto selecionado
            produtoSelecionado.setNome(novoNome);
            produtoSelecionado.setPreco(novoPreco);
            produtoSelecionado.setPreco_venda(novoPrecoVenda);
            

            // Atualizar o produto no banco de dados
            produtosDAO.atualizarProduto(produtoSelecionado);

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
