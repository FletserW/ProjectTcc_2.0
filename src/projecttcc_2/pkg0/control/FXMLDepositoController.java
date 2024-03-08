/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLDepositoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     @FXML
    private Button btnProduto;

    @FXML
    private TextField txtPesquisa;

     @FXML
    private ImageView imgBuscar;

     @FXML
    void buscarActionButton(MouseEvent event) {

    }
    
    @FXML
    void addProdutoActionButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLAddProdutos.fxml"));
            Parent root = loader.load();

            // Crie um novo controlador, se necessário  
            FXMLAddProdutosController addProdutosController = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Adicionar Produto");
            stage.setScene(new Scene(root));

            // Configurar mais propriedades da janela, se necessário
            // Impede a redimensionamento da janela
            stage.setResizable(false);

            stage.showAndWait(); // Mostrar a janela e esperar até que ela seja fechada
        } catch (Exception e) {
            e.printStackTrace(); // Lidar com exceções, como IOException ou FXMLLoaderException
        }
    }
}
