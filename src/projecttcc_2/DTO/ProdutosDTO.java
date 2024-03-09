/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecttcc_2.DTO;

/**
 *
 * @author reido
 */

import java.math.BigDecimal;

public class ProdutosDTO {

    private int id_produto;
    private String nome_produto;
    private int quantidade_produto;
    private BigDecimal preco_produto;
    private BigDecimal precoVenda_produto; 
    private int id_fornecedor;  // Adicionado o campo fornecedor_id
    private String localizacao_produto;

    // Construtor padrão
    public ProdutosDTO() {
    }

    // Construtor com todos os parâmetros, incluindo fornecedor_id
    public ProdutosDTO(int id_produto, String nome_produto, int quantidade_produto, BigDecimal preco_produto, BigDecimal precoVenda_produto, int id_fornecedor, String localizacao_produto) {
        this.id_produto = id_produto;
        this.nome_produto = nome_produto;
        this.quantidade_produto = quantidade_produto;
        this.preco_produto = preco_produto;
        this.precoVenda_produto = precoVenda_produto;
        this.id_fornecedor = id_fornecedor;
        this.localizacao_produto = localizacao_produto;
    }


    // Métodos getters e setters para cada campo

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public int getQuantidade_produto() {
        return quantidade_produto;
    }

    public void setQuantidade_produto(int quantidade_produto) {
        this.quantidade_produto = quantidade_produto;
    }

    public BigDecimal getPreco_produto() {
        return preco_produto;
    }

    public void setPreco_produto(BigDecimal preco_produto) {
        this.preco_produto = preco_produto;
    }

    public BigDecimal getPrecoVenda_produto() {
        return precoVenda_produto;
    }

    public void setPrecoVenda_produto(BigDecimal precoVenda_produto) {
        this.precoVenda_produto = precoVenda_produto;
    }

    public int getId_fornecedor() {
        return id_fornecedor;
    }

    public void setId_fornecedor(int id_fornecedor) {
        this.id_fornecedor = id_fornecedor;
    }


    public String getLocalizacao_produto() {
        return localizacao_produto;
    }

    public void setLocalizacao_produto(String localizacao_produto) {
        this.localizacao_produto = localizacao_produto;
    }

    // Outros métodos, se necessário
}
