package disponibilidade;

/**
 *
 * @author Tiago Martinho, João Marques
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
        return this.nomeLoja + "\t" + this.produto.getNomeProduto();
    }
}
