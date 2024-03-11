/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecttcc_2.BD;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import projecttcc_2.DTO.DepositoDTO;
import projecttcc_2.BD.ProdutosDAO;

/**
 *
 * @author reido
 */
public class DepositoDAO {

    Connection conn;

    public DepositoDAO() {
        // Estabelece a conexão com o banco de dados ao instanciar a classe
        conn = ConexaoBD.conectar();
    }
    

    public boolean atualizarQuantidade(int produtoId, int novaQuantidade) {
        try (PreparedStatement pstm = conn.prepareStatement(
                "UPDATE deposito SET quantidade = ? WHERE produto_id = ?")) {

            pstm.setInt(1, novaQuantidade);
            pstm.setInt(2, produtoId);

            int rowsAffected = pstm.executeUpdate();

            // Confirme explicitamente a transação
            conn.commit();

            // Verifica se a atualização foi bem-sucedida
            return rowsAffected > 0;

        } catch (SQLException erro) {
            erro.printStackTrace();
            // Lidar com a exceção ou exibir uma mensagem de erro
            return false;
        }
    }

}

