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
    

    // Método para atualizar o valor da quantidade_estoque na tabela deposito
    public static boolean atualizarQuantidadeEstoque(int quantidade, int produtoId) {
        String sql = "UPDATE deposito SET quantidade_estoque = ? WHERE produto_id = ?";

        try (
                Connection conn = ConexaoBD.conectar(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, quantidade);
            stmt.setInt(2, produtoId);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a quantidade_estoque: " + e.getMessage());
            return false;
        }
    }
    
    public static void atualizarQuantidadeEstoque2(String nomeProduto, int quantidade) {
        try (Connection conexao = ConexaoBD.conectar()) {
            String sql = "UPDATE deposito SET quantidade_estoque = quantidade_estoque + ? WHERE produto_id = (SELECT id FROM produtos WHERE nome = ?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, quantidade);
                stmt.setString(2, nomeProduto);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Método para fechar a conexão com o banco de dados
    public void fecharConexao() {
        ConexaoBD.desconectar(conn);
    }

}

