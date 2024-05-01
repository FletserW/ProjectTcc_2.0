/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    private ScreenManager screenManager;

    public FXMLDocumentController() {
        // Construtor vazio
    }

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
    @FXML
    private Button bntCadastrar;

    @FXML
    private Button bntEntrar;

    @FXML
    private Button bntSair;

    @FXML
    private Pane homePage;

    @FXML
    private ImageView imgLogo;

    
    @FXML
    void entrarButtonAction(ActionEvent event) {
        screenManager.switchScreen("/projecttcc_2/pkg0/View/FXMLInscrever.fxml");
    }
    
    @FXML
    void cadastrarButtonAction(ActionEvent event) {
        screenManager.switchScreen("/projecttcc_2/pkg0/View/FXMLCadastrar.fxml");
    }

    
    @FXML
    private void sairButtonAction(ActionEvent event) {
       System.exit(0); // Fechar o aplicativo
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Determina o caminho absoluto do arquivo de propriedades com base no idioma
            String fileName = "/projecttcc_2/ProjectTcc_2.0/src/resources/resources_en_US.properties";

            // Mensagem de depuração
            System.out.println("Arquivo de propriedades: " + fileName);

            // Carrega o arquivo de propriedades
            InputStream inputStream = new FileInputStream(fileName);

            // Arquivo de propriedades encontrado, carrega
            Properties properties = new Properties();
            properties.load(inputStream);

            // Define o texto dos botões usando as mensagens do arquivo de propriedades
            bntCadastrar.setText(properties.getProperty("button.register"));
            bntEntrar.setText(properties.getProperty("button.enter"));
            bntSair.setText(properties.getProperty("button.exit"));

            // Fecha o fluxo de entrada
            inputStream.close();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo de propriedades: " + e.getMessage());
        }
    }

    
    /*    public void setScreenManager(ScreenManager screenManager) {
    this.screenManager = screenManager;
    }*/
}
