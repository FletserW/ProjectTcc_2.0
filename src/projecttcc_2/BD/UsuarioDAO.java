package projecttcc_2.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import projecttcc_2.DTO.UsuariosDTO;

public class UsuarioDAO{
    Connection conn;

    public UsuarioDAO() {
        // Estabelece a conexão com o banco de dados ao instanciar a classe UsuarioDAO
        conn = ConexaoBD.conectar(); // Correção aqui
    }

    public boolean inserirUsuario(UsuariosDTO objUsuariosDTO) {
        try {
            String sql = "INSERT INTO Usuarios (nome, email, senha) VALUES (?, ?, ?)";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, objUsuariosDTO.getNome_usuario());
            pstm.setString(2, objUsuariosDTO.getEmail_usuario());
            pstm.setString(3, objUsuariosDTO.getSenha_usuario());

            int rowsAffected = pstm.executeUpdate();

            // Verifica se a inserção foi bem-sucedida
            return rowsAffected > 0;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir usuário: " + erro.getMessage());
            return false;
        }
    }

    public boolean autenticacaoUsuario(UsuariosDTO objUsuariosDTO) {
        try {
            String sql = "SELECT * FROM Usuarios WHERE email = ? AND senha = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, objUsuariosDTO.getEmail_usuario());
            pstm.setString(2, objUsuariosDTO.getSenha_usuario());

            ResultSet rs = pstm.executeQuery();

            // Verifica se encontrou algum registro com o email e senha fornecidos
            return rs.next();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao autenticar usuário: " + erro.getMessage());
            return false;
        }
    }

    // Método para fechar a conexão com o banco de dados
    public void fecharConexao() {
        ConexaoBD.desconectar(conn);
    }
}
