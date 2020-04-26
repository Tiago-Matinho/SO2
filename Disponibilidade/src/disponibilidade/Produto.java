package disponibilidade;

/**
 *
 * @author Tiago Martinho, João Marques
 */
public class Produto implements java.io.Serializable {
    private String nomeProd;
    private int procura;
    
    
    public Produto(String nomeProd) {
        this.nomeProd = nomeProd;
        this.procura = 0;
    }
    
    public Produto(String nomeProd, int procura) {
        this.nomeProd = nomeProd;
        this.procura = procura;
    }
    
    public String getNomeProduto() {
        return this.nomeProd;
    }
    
    public int getProcura() {
        return this.procura;
    }
    
    @Override
    public String toString() {
        return this.nomeProd +"\t" + this.procura;
    }
}
