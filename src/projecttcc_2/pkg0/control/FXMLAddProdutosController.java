/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import projecttcc_2.BD.ProdutosDAO;
import projecttcc_2.DTO.ProdutosDTO;
/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLAddProdutosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private ImageView addFornecedor;

    @FXML
    private Button bntAdicionar;

    @FXML
    private Button bntCancelar;

    @FXML
    private ComboBox<?> comboFornecedor;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPreco;

    @FXML
    private TextField txtPrecoUnid;

    @FXML
    private Spinner<?> txtQtd;

      @FXML
    void adicionarActionButton(ActionEvent event) {
        try {
            String nomeProduto = txtNome.getText();
            BigDecimal precoProduto = new BigDecimal(txtPreco.getText());
            BigDecimal precoVendaProduto = new BigDecimal(txtPrecoUnid.getText()); // Obtendo preço de venda
            int qtdProduto = ((Number) txtQtd.getValue()).intValue();
            String fornecedorProduto = comboFornecedor.getSelectionModel().getSelectedItem().toString();
 // Obtendo fornecedor

            ProdutosDTO novoProduto = new ProdutosDTO();
            novoProduto.setNome_produto(nomeProduto);
            novoProduto.setPreco_produto(precoProduto);
            novoProduto.setPrecoVenda_produto(precoVendaProduto);
            novoProduto.setQuantidade_produto(qtdProduto);
            novoProduto.setFornecedor_produto(fornecedorProduto);

            // Instanciando ProdutosDAO e inserindo o produto no banco de dados
            ProdutosDAO produtosDAO = new ProdutosDAO();
            boolean insercaoBemSucedida = produtosDAO.inserirProdutos(novoProduto);

            if (insercaoBemSucedida) {
                exibirMensagem("Produto cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            } else {
                exibirMensagem("Erro ao cadastrar produto.", Alert.AlertType.ERROR);
            }

            // Fechando a conexão com o banco de dados
            produtosDAO.fecharConexao();
        } catch (NumberFormatException e) {
            exibirMensagem("Erro ao cadastrar produto. Certifique-se de inserir valores válidos.", Alert.AlertType.ERROR);
        }
    }

    // ... Outros métodos existentes ...

    private void exibirMensagem(String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }


    @FXML
    void cancelarActionButton(ActionEvent event) {
        System.exit(0); // Fechar o aplicativo
    }
    
}
