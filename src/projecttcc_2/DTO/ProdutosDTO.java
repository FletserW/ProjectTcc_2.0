/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecttcc_2.DTO;

import java.math.BigDecimal;

/**
 *
 * @author reido
 */
public class ProdutosDTO {

    private int id;
    private String nome;
    private int quantidade;
    private BigDecimal preco;
    private BigDecimal preco_venda; 
    private int id_fornecedor;  // Adicionado o campo fornecedor_id
    private String localizacao;
    private int quantidade_estoque;

    // Construtor padrão
    public ProdutosDTO() {
    }

    // Construtor com todos os parâmetros, incluindo fornecedor_id
    public ProdutosDTO(int id, String nome, int quantidade, BigDecimal preco, BigDecimal preco_venda, int id_fornecedor, String localizacao) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.preco_venda = preco_venda;
        this.id_fornecedor = id_fornecedor;
        this.localizacao = localizacao;
    }

    // Métodos getters e setters para cada campo

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(BigDecimal preco_venda) {
        this.preco_venda = preco_venda;
    }

    public int getId_fornecedor() {
        return id_fornecedor;
    }

    public void setId_fornecedor(int id_fornecedor) {
        this.id_fornecedor = id_fornecedor;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
    
    public int getQuantidadeEstoque() {
        return quantidade_estoque;
    }

    public void setQuantidadeEstoque(int quantidade_estoque) {
        this.quantidade_estoque = quantidade_estoque;
    }

   
}
