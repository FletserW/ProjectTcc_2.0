package projecttcc_2.pkg0.control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FXMLInscreverController implements Initializable {

    public FXMLInscreverController() {
        // Este é um construtor padrão sem argumentos
    }

    @FXML
    private Button bntCadastrar1;

    @FXML
    private Button bntRegistrar;

    @FXML
    private Button btnFacebook;

    @FXML
    private Button btnGoogle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void cadastroGoogle(ActionEvent event) {
        // Implemente aqui a lógica para o cadastro com Google
        System.out.println("Cadastro com Google");
    }

    @FXML
    private void cadastroFacebook(ActionEvent event) {
        // Implemente aqui a lógica para o cadastro com Facebook
        System.out.println("Cadastro com Facebook");
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

}
