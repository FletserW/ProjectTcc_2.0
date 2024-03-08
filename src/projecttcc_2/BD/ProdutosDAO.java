/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecttcc_2.BD;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import projecttcc_2.DTO.ProdutosDTO;

/**
 *
 * @author reido
 */
public class ProdutosDAO {
    Connection conn;

    public ProdutosDAO() {
        // Estabelece a conexão com o banco de dados ao instanciar a classe UsuarioDAO
        conn = ConexaoBD.conectar(); // Correção aqui
    }

    public boolean inserirProdutos(ProdutosDTO objProdutosDTO) {
        try {
            String sql = "INSERT INTO produtos (nome, quantidade, preco, preco_venda, fornecedor, localizacao) VALUES (?, ?, ?, ?, ?, deposito)";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, objProdutosDTO.getNome_produto());
            pstm.setInt(2, objProdutosDTO.getQuantidade_produto());
            pstm.setBigDecimal(3, objProdutosDTO.getPreco_produto());
            pstm.setBigDecimal(4, objProdutosDTO.getPrecoVenda_produto());
            pstm.setString(5, objProdutosDTO.getFornecedor_produto());
            pstm.setString(6, objProdutosDTO.getLocalizacao_produto());
            

            int rowsAffected = pstm.executeUpdate();

            // Verifica se a inserção foi bem-sucedida
            return rowsAffected > 0;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir usuário: " + erro.getMessage());
            return false;
        }
    }

    // Método para fechar a conexão com o banco de dados
    public void fecharConexao() {
        ConexaoBD.desconectar(conn);
    }
}
