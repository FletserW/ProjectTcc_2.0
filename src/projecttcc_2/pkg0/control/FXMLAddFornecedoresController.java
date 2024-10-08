/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
 /*
 * Controlador para a tela de adição de fornecedores.
 * Esta classe é responsável por lidar com eventos e operações na interface de adição de fornecedores.
 */
package projecttcc_2.pkg0.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import projecttcc_2.BD.ConexaoBD;
import projecttcc_2.pkg0.control.FXMLAddProdutosController;

public class FXMLAddFornecedoresController {

    @FXML
    private Button btnRegistrar;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    private AnchorPane addFornecedorInclude;

    private FXMLAddProdutosController addProdutosController;

    // Método chamado quando o botão de registro é clicado
    @FXML
    void registrarAnctionButton(ActionEvent event) {
        // Pegar os valores dos campos do formulário
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText(); // Novo campo de e-mail

        try {
            // Conectar ao banco de dados
            Connection conexao = ConexaoBD.conectar();

            // Inserção de um novo fornecedor
            String sql = "INSERT INTO fornecedores (nome, telefone, email) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setString(2, telefone);
                pstmt.setString(3, email);

                int linhasAfetadas = pstmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    // Exibir mensagem de sucesso
                    JOptionPane.showMessageDialog(null, "Fornecedor salvo com sucesso!");

                    // Atualizar a lista de fornecedores no controlador de produtos
                    if (addProdutosController != null) {
                        addProdutosController.preencherComboBoxFornecedores();
                    }
                } else {
                    // Exibir mensagem de falha
                    JOptionPane.showMessageDialog(null, "Falha ao salvar o fornecedor.");
                }
            }

            // Fechar a conexão
            conexao.close();

        } catch (SQLException ex) {
            // Lidar com exceções de banco de dados
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados.");
        }
    }


    // Método para definir o controlador da classe FXMLAddProdutosController
    public void setAddProdutosController(FXMLAddProdutosController addProdutosController) {
        this.addProdutosController = addProdutosController;
    }
}
