/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecttcc_2.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import projecttcc_2.BD.ConexaoBD;

public class PedidosDAO {

    public static void concluirPedido(int pedidoId) {
        System.out.println("id pedide sendo pedido");
        try (Connection conexao = ConexaoBD.conectar()) {
            // Atualizar o status do pedido para 'concluido'
            String sqlUpdatePedido = "UPDATE Pedidos SET status = 'entregue' WHERE id = ?";
            try (PreparedStatement stmtUpdatePedido = conexao.prepareStatement(sqlUpdatePedido)) {
                stmtUpdatePedido.setInt(1, pedidoId);
                stmtUpdatePedido.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
