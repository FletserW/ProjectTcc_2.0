/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecttcc_2.DTO;

import java.math.BigDecimal;
import java.sql.Date;

public class PedidosDTO {

    private int id;
    private Date dataPedido;
    private int idFornecedor;
    private BigDecimal valorTotal;

    // Construtor
    public PedidosDTO(Date dataPedido, int idFornecedor, BigDecimal valorTotal) {
        this.dataPedido = dataPedido;
        this.idFornecedor = idFornecedor;
        this.valorTotal = valorTotal;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
