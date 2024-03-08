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
    private String fornecedor_produto;
    private String localizacao_produto;

    // Construtores, métodos getters e setters, etc.

    // Construtor padrão
    public ProdutosDTO() {
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

    public String getFornecedor_produto() {
        return fornecedor_produto;
    }

    public void setFornecedor_produto(String fornecedor_produto) {
        this.fornecedor_produto = fornecedor_produto;
    }

    public String getLocalizacao_produto() {
        return localizacao_produto;
    }

    public void setLocalizacao_produto(String localizacao_produto) {
        this.localizacao_produto = localizacao_produto;
    }

    // Outros métodos, se necessário
}
