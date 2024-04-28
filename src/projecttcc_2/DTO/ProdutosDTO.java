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
    private String fornecedor_id;  
    private String fornecedor_nome;
    private String localizacao;
    private int quantidade_estoque;
    

    // Construtor padrão
    public ProdutosDTO() {
    }
    
    public ProdutosDTO(String nome) {
        this.nome = nome;
    }

    // Construtor com todos os parâmetros
    public ProdutosDTO(int id, String nome, BigDecimal preco, BigDecimal preco_venda, String fornecedor_nome) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.preco_venda = preco_venda;
        this.fornecedor_nome = fornecedor_nome;
    }

    public ProdutosDTO(int id, int quantidade) {
        this.id = id;
        this.quantidade = quantidade;
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

    public String getFornecedor_id() {
        return fornecedor_id;
    }

    public void setFornecedor_id(String fornecedor_id) {
        this.fornecedor_id = fornecedor_id;
    }
    
    public String getFornecedor_nome() {
        return fornecedor_nome;
    }

    public void setFornecedor_nome(String fornecedor_nome) {
        this.fornecedor_nome = fornecedor_nome;
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
