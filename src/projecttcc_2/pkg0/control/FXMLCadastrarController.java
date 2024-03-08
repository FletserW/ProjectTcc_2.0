/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import projecttcc_2.BD.UsuarioDAO;
import projecttcc_2.DTO.UsuariosDTO;


/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLCadastrarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private Button bntAcessar;

    @FXML
    private Button bntRegistrar;

    @FXML
    private Button btnFacebook;

    @FXML
    private Button btnGoogle;
    
     @FXML
    private PasswordField password1;

    @FXML
    private PasswordField password2;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    
     @FXML
    void acessarButtonAction(ActionEvent event) {
        try {
        // Carrega a nova cena
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLInscrever.fxml"));
        Parent root = loader.load();
        
        // Obtém o palco atual
        Stage stage = (Stage) bntAcessar.getScene().getWindow();
        
        // Define a nova cena no palco
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void registarButtonAction(ActionEvent event) throws IOException {
        try {
            // Obter os dados do formulário
            String nomeUsuario = txtNome.getText();
            String emailUsuario = txtEmail.getText();
            String senhaUsuario = password1.getText();
            String senhaUsuarioConfirmacao = password2.getText();

            // Verificar se as senhas coincidem
            if (!senhaUsuario.equals(senhaUsuarioConfirmacao)) {
                // Se as senhas não coincidirem, exibir uma mensagem de erro
                JOptionPane.showMessageDialog(null, "As senhas não coincidem");
                return; // Sair do método, pois não podemos continuar sem senhas válidas
            }

            // Criar um objeto DTO com os dados do usuário
            UsuariosDTO usuarioDTO = new UsuariosDTO();
            usuarioDTO.setNome_usuario(nomeUsuario);
            usuarioDTO.setEmail_usuario(emailUsuario);
            usuarioDTO.setSenha_usuario(senhaUsuario);

            // Criar uma instância do DAO e inserir o usuário no banco de dados
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean inseridoComSucesso = usuarioDAO.inserirUsuario(usuarioDTO);

            // Verificar se o usuário foi inserido com sucesso
            if (inseridoComSucesso) {
                // Se o usuário foi inserido com sucesso, exibir uma mensagem de sucesso
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLHomePage.fxml"));
            Parent root = loader.load();
            
            // Obtém o palco atual
            Stage stage = (Stage) bntRegistrar.getScene().getWindow();
            
            // Define a nova cena no palco
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
                
               // JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
            } else {
                // Se houve algum problema ao inserir o usuário, exibir uma mensagem de erro
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário");
            }
        } catch (Exception e) {
            // Se ocorrer uma exceção, exibir uma mensagem de erro
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário: " + e.getMessage());
        }

    }   

    @FXML
    void cadastroFacebook(ActionEvent event) {

    }

    @FXML
    void cadastroGoogle(ActionEvent event) {

    }

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
