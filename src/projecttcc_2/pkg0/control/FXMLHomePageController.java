/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLHomePageController implements Initializable {

    @FXML
    private AnchorPane includeAnchorPane;
    
    @FXML
    private Button btnCofig;

    @FXML
    private Button btnDeposito;

    @FXML
    private Button btnFreezer;

    @FXML
    private Button btnPedidos;

    @FXML
    private Button btnRelatorios;

    @FXML
    private Button btnVendas;

    @FXML
    private Label lblUsuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

    @FXML
    void depositoActionButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLDeposito.fxml"));
            AnchorPane estoquePane = loader.load();

            // Substituir apenas o conteúdo dentro do AnchorPane
            includeAnchorPane.getChildren().setAll(estoquePane.getChildren());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void freezerActionButton(ActionEvent event) {
        try {
            System.out.println("Botão Freezer funcionando");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLFreezer.fxml"));
            AnchorPane freezerPane = loader.load();

            // Substituir apenas o conteúdo dentro do AnchorPane
            includeAnchorPane.getChildren().setAll(freezerPane.getChildren());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar o arquivo FXML do freezer: " + e.getMessage());
        }
    }


    @FXML
    void vendasActionButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLVendas.fxml"));
            AnchorPane estoquePane = loader.load();

            // Substituir apenas o conteúdo dentro do AnchorPane
            includeAnchorPane.getChildren().setAll(estoquePane.getChildren());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void pedidosActionButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLPedidos.fxml"));
            AnchorPane estoquePane = loader.load();

            // Substituir apenas o conteúdo dentro do AnchorPane
            includeAnchorPane.getChildren().setAll(estoquePane.getChildren());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void relatorioActionButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projecttcc_2/pkg0/View/FXMLRelatorios.fxml"));
            AnchorPane estoquePane = loader.load();

            // Substituir apenas o conteúdo dentro do AnchorPane
            includeAnchorPane.getChildren().setAll(estoquePane.getChildren());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    
}
