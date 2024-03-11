/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecttcc_2.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import projecttcc_2.DTO.FornecedoresDTO;

public class FornecedoresDAO {

    private Connection conn;

    public FornecedoresDAO() {
        // Estabelece a conexão com o banco de dados ao instanciar a classe FornecedoresDAO
        conn = ConexaoBD.conectar();
    }

    public boolean inserirFornecedor(FornecedoresDTO objFornecedoresDTO) {
        try (PreparedStatement pstm = conn.prepareStatement(
                "INSERT INTO fornecedores (nome, telefone, email) VALUES (?, ?, ?)")) {

            pstm.setString(1, objFornecedoresDTO.getNome());
            pstm.setString(2, objFornecedoresDTO.getTelefone());
            pstm.setString(3, objFornecedoresDTO.getEmail());

            pstm.executeUpdate();

            // Confirme explicitamente a transação
            conn.commit();

            // Verifica se a inserção foi bem-sucedida
            return true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir fornecedor: " + erro.getMessage());
            return false;
        }
    }

    public List<FornecedoresDTO> listarFornecedores() {
        List<FornecedoresDTO> listaFornecedores = new ArrayList<>();
        try (PreparedStatement pstm = conn.prepareStatement("SELECT * FROM fornecedores"); ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                FornecedoresDTO fornecedorDTO = new FornecedoresDTO();
                fornecedorDTO.setId(rs.getInt("id"));
                fornecedorDTO.setNome(rs.getString("nome"));
                fornecedorDTO.setTelefone(rs.getString("telefone"));
                fornecedorDTO.setEmail(rs.getString("email"));
                listaFornecedores.add(fornecedorDTO);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar fornecedores: " + erro.getMessage());
        }
        return listaFornecedores;
    }

    // Método para fechar a conexão com o banco de dados
    public void fecharConexao() {
        ConexaoBD.desconectar(conn);
    }
}
