package projecttcc_2.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import projecttcc_2.DTO.ProdutosDTO;
import java.sql.SQLException;


public class ProdutosDAO {
    Connection conn;

    public ProdutosDAO() {
        try {
            // Estabelece a conexão com o banco de dados ao instanciar a classe UsuarioDAO
            conn = ConexaoBD.conectar();
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
            // Adicione um tratamento adicional se necessário
        }
    }




    public boolean inserirProdutos(ProdutosDTO objProdutosDTO) {
        try (PreparedStatement pstm = conn.prepareStatement(
                "INSERT INTO produtos (nome, quantidade, preco, preco_venda, fornecedor_id) VALUES (?, ?, ?, ?, ?)")) {

            pstm.setString(1, objProdutosDTO.getNome());
            pstm.setInt(2, objProdutosDTO.getQuantidade());
            pstm.setBigDecimal(3, objProdutosDTO.getPreco());
            pstm.setBigDecimal(4, objProdutosDTO.getPreco_venda());
            pstm.setString(5, objProdutosDTO.getFornecedor_id());

            pstm.executeUpdate();

            // Verifica se a inserção foi bem-sucedida
            return true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir produto: " + erro.getMessage());
            return false;
        }
}
    
    public boolean atualizarProduto(ProdutosDTO produto) {
        try (PreparedStatement pstm = conn.prepareStatement(
                "UPDATE produtos SET nome = ?, quantidade = ?, preco = ?, preco_venda = ? WHERE id = ?")) {

            pstm.setString(1, produto.getNome());
            pstm.setInt(2, produto.getQuantidade());
            pstm.setBigDecimal(3, produto.getPreco());
            pstm.setBigDecimal(4, produto.getPreco_venda());
            pstm.setInt(5, produto.getId());

            int rowsAffected = pstm.executeUpdate();

            // Verifica se a atualização foi bem-sucedida
            return rowsAffected > 0;

        } catch (SQLException erro) {
            erro.printStackTrace();
            // Lidar com a exceção ou exibir uma mensagem de erro
            return false;
        }
    }



    // Método para fechar a conexão com o banco de dados
    public void fecharConexao() {
        ConexaoBD.desconectar(conn);
    }
}
