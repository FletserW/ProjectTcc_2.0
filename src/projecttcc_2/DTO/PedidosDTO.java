/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecttcc_2.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class PedidosDTO {

    private int id;
    private Date dataPedido;
    private int idFornecedor;
    private BigDecimal valorTotal;
    private String nomeFornecedor;
    private String status;

    // Construtor
    public PedidosDTO(Date dataPedido, int idFornecedor, BigDecimal valorTotal) {
        this.dataPedido = dataPedido;
        this.idFornecedor = idFornecedor;
        this.valorTotal = valorTotal;
    }
    
    public PedidosDTO(int id, Date dataPedido, int idFornecedor, String nomeFornecedor, BigDecimal valorTotal) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.idFornecedor = idFornecedor;
        this.nomeFornecedor = nomeFornecedor;
        this.valorTotal = valorTotal;
    }

    public PedidosDTO(int id, Date dataPedido, int idFornecedor, BigDecimal valorTotal, String nomeFornecedor) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.idFornecedor = idFornecedor;
        this.valorTotal = valorTotal;
        this.nomeFornecedor = nomeFornecedor;
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
    
    // Getters e Setters
    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
