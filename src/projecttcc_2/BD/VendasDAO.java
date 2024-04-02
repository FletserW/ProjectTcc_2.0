package projecttcc_2.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class VendasDAO {

    Connection conn;

    public VendasDAO() {
        try {
            // Estabelece a conexão com o banco de dados ao instanciar a classe UsuarioDAO
            conn = ConexaoBD.conectar();
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
            // Adicione um tratamento adicional se necessário
        }
    }

    // Método para verificar se já existe um registro para o produto no mês atual
    public boolean existeRegistroParaProdutoNoMesAtual(int idProduto) {
        boolean existe = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM vendas WHERE id_produto = ? AND mes_ano = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idProduto);
            stmt.setString(2, LocalDate.now().toString().substring(0, 7)); // Obtém o mês/ano atual

            rs = stmt.executeQuery();
            existe = rs.next(); // Se houver algum resultado, significa que já existe registro para o produto no mês atual
        } catch (SQLException e) {
            System.out.println("Erro ao verificar registro para o produto no mês atual: " + e.getMessage());
        }

        return existe;
    }

    // Método para atualizar a quantidade vendida do produto no mês atual
        public boolean atualizarQuantidadeVendida(int idProduto, int quantidadeVendida) {
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE vendas SET quantidade_vendida = quantidade_vendida + ? WHERE id_produto = ? AND mes_ano = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, quantidadeVendida);
            stmt.setInt(2, idProduto);
            stmt.setString(3, LocalDate.now().toString().substring(0, 7)); // Obtém o mês/ano atual

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0; // Retorna true se houver linhas afetadas (atualização bem-sucedida)
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar quantidade vendida do produto: " + e.getMessage());
            return false;
        }
    }

    // Método para adicionar um novo registro de produto vendido
    public boolean adicionarProdutoVendido(int idProduto, int quantidadeVendida, String mesAno) {
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO vendas (id_produto, quantidade_vendida, mes_ano) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idProduto);
            stmt.setInt(2, quantidadeVendida);
            stmt.setString(3, LocalDate.now().toString().substring(0, 7)); // Obtém o mês/ano atual

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0; // Retorna true se houver linhas afetadas (inserção bem-sucedida)
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar produto vendido: " + e.getMessage());
            return false;
        }
    }
    
   
}
