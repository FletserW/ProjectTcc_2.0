
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author reido
 */
public class ProjectTcc_20 extends Application {
    
   @Override
    public void start(Stage stage) throws Exception {
        // Define o idioma padrão como inglês (Estados Unidos)
        Locale.setDefault(Locale.US);
        
        ScreenManager screenManager = new ScreenManager(stage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLDocument.fxml"));
        Parent root = loader.load();

        FXMLDocumentController controller = loader.getController();
        controller.setScreenManager(screenManager);

        Scene scene = new Scene(root);

        // Carrega o ícone (favicon) da imagem
        Image icon = new Image(getClass().getResourceAsStream("/projecttcc_2/pkg0/img/favicon.png"));
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}