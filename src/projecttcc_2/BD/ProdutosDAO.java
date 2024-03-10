package projecttcc_2.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import projecttcc_2.DTO.ProdutosDTO;

public class ProdutosDAO {
    Connection conn;

    public ProdutosDAO() {
        // Estabelece a conexão com o banco de dados ao instanciar a classe UsuarioDAO
        conn = ConexaoBD.conectar();
    }

    public boolean inserirProdutos(ProdutosDTO objProdutosDTO) {
        try (PreparedStatement pstm = conn.prepareStatement(
                "INSERT INTO produtos (nome, quantidade, preco, preco_venda, fornecedor_id) VALUES (?, ?, ?, ?, ?)")) {

            pstm.setString(1, objProdutosDTO.getNome());
            pstm.setInt(2, objProdutosDTO.getQuantidade());
            pstm.setBigDecimal(3, objProdutosDTO.getPreco());
            pstm.setBigDecimal(4, objProdutosDTO.getPrecoVenda());
            pstm.setInt(5, objProdutosDTO.getId_fornecedor());

            pstm.executeUpdate();

            // Confirme explicitamente a transação
            conn.commit();

            // Verifica se a inserção foi bem-sucedida
            return true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir produto: " + erro.getMessage());
            return false;
        }
}


    // Método para fechar a conexão com o banco de dados
    public void fecharConexao() {
        ConexaoBD.desconectar(conn);
    }
}
