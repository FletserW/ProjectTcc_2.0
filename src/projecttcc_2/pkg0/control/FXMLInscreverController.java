package projecttcc_2.pkg0.control;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import projecttcc_2.BD.UsuarioDAO;
import projecttcc_2.DTO.UsuariosDTO;

public class FXMLInscreverController implements Initializable {

    @FXML
    private Button bntCadastrar1;

    @FXML
    private Button bntRegistrar;

    @FXML
    private Button btnFacebook;

    @FXML
    private Button btnGoogle;
    
    @FXML
    private Button bntViewPassword;
    
    @FXML
    private PasswordField password1;

    @FXML
    private TextField txtEmail;
    
    private boolean passwordVisible = false;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void RegistrarButtonAction(ActionEvent event) throws IOException {
        Logar();
    }

    @FXML
    private void cadastroButtonAction(ActionEvent event) {
        try {
            // Carrega a nova cena
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLCadastrar.fxml"));
            Parent root = loader.load();

            // Obtém o palco atual
            Stage stage = (Stage) bntCadastrar1.getScene().getWindow();

            // Define a nova cena no palco
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     @FXML
    void cadastroFacebookAction(ActionEvent event) {

    }

    @FXML
    void cadastroGoogleAction(ActionEvent event) {

    }
    
    @FXML
    void showPassworldActionButtons(ActionEvent event) {
            System.out.println("botao selecionado");
        // Inverte o estado da visibilidade da senha
        passwordVisible = !passwordVisible;

        // Configura a visibilidade da senha com base no estado atual
        password1.setManaged(passwordVisible);

    }
    
    private void Logar() throws IOException{
        String emailUsuario = txtEmail.getText();
        String senhaUsuario = password1.getText();
        UsuariosDTO usuarioDTO = new UsuariosDTO();
        usuarioDTO.setEmail_usuario(emailUsuario);
        usuarioDTO.setSenha_usuario(senhaUsuario);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean autenticado = usuarioDAO.autenticacaoUsuario(usuarioDTO);
        if (autenticado) {
            // Chamar tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLHomePage.fxml"));
            Parent root = loader.load();
            
            // Obtém o palco atual
            Stage stage = (Stage) bntRegistrar.getScene().getWindow();
            
            // Define a nova cena no palco
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
        } else {
            // deu errado
            JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos");
        }
    }
    }

