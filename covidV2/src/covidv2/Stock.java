/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package covidv2;

/**
 *
 * @author rute
 */
public class Stock implements java.io.Serializable {
    private String nomeLoja;
    private Produto produto;
    
    
    public Stock(String nomeLoja, String nomeProd) {
        this.nomeLoja = nomeLoja;
        this.produto = new Produto(nomeProd);
    }
    
    
    public String getNomeLoja() {
        return this.nomeLoja;
    }
    
    public Produto getProduto() {
        return this.produto;
    }
    
    @Override
    public String toString() {
        return this.nomeLoja + " " + this.produto.getNomeProduto();
    }
}
