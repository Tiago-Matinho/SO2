package covidv2;

import java.util.Vector;
import so2.ObjFile;

public class Registo implements java.io.Serializable {
    private String userName;
    private boolean resposta;
    
    private int tipoPedido;
    
    private Vector<Stock> vStock;
    private Vector<Produto> vProduto;
    private String id;
    private boolean resultado;
    
    private String nomeLoja, nomeProduto;
    
    
    public Registo(String userName, int tipoPedido,
            Vector<Stock> vStock, Vector<Produto> vProduto, String id, boolean resultado) {
        this.userName = userName;
        this.resposta = true;
        this.tipoPedido = tipoPedido;
        this.vStock = vStock;
        this.vProduto = vProduto;
        this.id = id;
        this.resultado = resultado;
    }
    
    public Registo(String userName, int tipoPedido, String nomeLoja, String nomeProduto) {
        this.userName = userName;
        this.resposta = false;
        this.tipoPedido = tipoPedido;
        this.nomeLoja = nomeLoja;
        this.nomeProduto = nomeProduto;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public boolean getResposta() {
        return this.resposta;
    }
    
    public int getTipoPedido() {
        return this.tipoPedido;
    }
    
    public Vector<Stock> getVStock() {
        return this.vStock;
    }
    
    public Vector<Produto> getVProduto() {
        return this.vProduto;
    }
    
    public String getId() {
        return this.id;
    }
    
    public boolean getResultado() {
        return this.resultado;
    }
    
    public String getNomeLoja() {
        return this.nomeLoja;
    }
    
    public String getNomeProduto() {
        return this.nomeProduto;
    }
    
    
    public boolean guardar() {
        
        try {
            ObjFile of = new ObjFile("Registo_" + this.userName);
            of.escreve(this);
            return true;
        }
        catch(Exception e) {
            System.err.println("Erro a gravar registo...");
        }
        return false;
    }
}
