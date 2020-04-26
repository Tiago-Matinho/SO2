package disponibilidade;

import java.util.Vector;
import so2.ObjFile;

/**
 *
 * @author João Marques, Tiago Martinho
 */
public class Requisito implements java.io.Serializable {
    private Vector<String> vID;
    private String userName;
    
    public Requisito(Vector<String> vID, String userName) {
        this.vID = vID;
        this.userName = userName;
    }
    
    public Vector<String> getVID() {
        return this.vID;
    }
    
    public void guardar() {
        try {
            ObjFile of = new ObjFile("Requisitos_" + userName);
            
            of.escreve(this);
        }
        catch(Exception e) {
            System.err.println("\n- Erro a escrever requisitos feitos pelo utilizador: " + userName + "...\n");
        }
    }
}
