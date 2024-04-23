/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecttcc_2.BD;

/**
 * Classe responsável pela conexão com o banco de dados.
 * Esta classe fornece métodos para conectar e desconectar do banco de dados.
 * É utilizada para estabelecer uma conexão com o banco de dados MySQL.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    // URL de conexão com o banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/gerenciamento_db";

    // Nome de usuário do banco de dados
    private static final String USUARIO = "root";

    // Senha do banco de dados
    private static final String SENHA = "";

    static {
        try {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            // Lidar com a exceção de carregamento do driver
            ex.printStackTrace();
        }
    }

    // Método para estabelecer uma conexão com o banco de dados
    public static Connection conectar() {
        try {
            // Estabelecer a conexão com o banco de dados
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException ex) {
            // Lidar com exceções de conexão
            ex.printStackTrace();
            return null;
        }
    }

    // Método para fechar a conexão com o banco de dados
    public static void desconectar(Connection conexao) {
        try {
            // Verificar se a conexão está aberta antes de fechar
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException ex) {
            // Lidar com exceções de fechamento da conexão
            ex.printStackTrace();
        }
    }
}
