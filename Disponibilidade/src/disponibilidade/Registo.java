package disponibilidade;

import java.util.Vector;
import so2.ObjFile;

/**
 *
 * @author Tiago Martinho, João Marques
 */
public class Registo implements java.io.Serializable {
    private String userName;
    private boolean resposta;
    
    private int tipoPedido;
    
    private Vector<Stock> vStock;
    private Vector<Produto> vProduto;
    private String id;
    private boolean resultado;
    
    private String nomeLoja, nomeProduto;
    
    // gracefull exit
    public Registo(int tipoPedido, String userName) {
        this.tipoPedido = tipoPedido;
        this.userName = userName;
    }
    
    // registo de resposta
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
    
    // registo de pedido
    public Registo(String userName, int tipoPedido, String nomeLoja, String nomeProduto) {
        this.userName = userName;
        this.resposta = false;
        this.tipoPedido = tipoPedido;
        this.nomeLoja = nomeLoja;
        this.nomeProduto = nomeProduto;
    }
    
    /***************************************************************************/
    
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
    
    /*************************************************************************/
    
    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }
    
    public void setNomeProd(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    
    public void guardar() {
        
        try {
            ObjFile of = new ObjFile("Registo_" + userName);
            of.escreve(this);
        }
        catch(Exception e) {
            System.err.println("\n- Erro a escrever último registo feito pelo utilizador: " + userName + "...\n");
        }
    }
}
