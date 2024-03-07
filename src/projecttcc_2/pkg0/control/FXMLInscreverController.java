package projecttcc_2.pkg0.control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class FXMLInscreverController implements Initializable {

    private ScreenManager screenManager;

    public FXMLInscreverController() {
        // Este é um construtor padrão sem argumentos
    }

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
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
        System.out.println("Botão cadastro selecionado");
        if (screenManager != null) {
            screenManager.switchScreen("/projecttcc_2/pkg0/View/FXMLCadastrar.fxml");
        } else {
            System.err.println("ScreenManager não foi definido.");
        }
    }
}
