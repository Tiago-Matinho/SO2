package covidv1;

/**
 * CLASSE DO OBJECTO REMOTO Tem a parte funcional... a implementação das
 * operações do serviço.
 */
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author Tiago Martinho, João Marques
 */
public class DispImpl extends UnicastRemoteObject implements Disp, java.io.Serializable {
    
    private PostgresConnector pc;
    private Statement stm;
    int req;
    
    /**
     * 
     * @param host
     * @param db
     * @param user
     * @param pw
     * @throws Exception 
     */
    public DispImpl(String host, String db, String user, String pw) throws Exception {
        pc = new PostgresConnector(host, db, user, pw);
        
        pc.connect();
        stm = pc.getStatement();

        req = 0;
    }
    
 
   /**
    * 
    * @param str
    * @return 
    */
   private boolean queryValida(String str) {
       return !(str.contains(";") || str.contains("'") );
   }
   
   /**
    * 
    * @param username 
    */
   public void login(String username) throws java.rmi.RemoteException {
       
       if(!queryValida(username))
           return;
       
       String query = "INSERT INTO Utilizador VALUES('" + username + "');";
       
       try {
           stm.executeQuery(query);
       }
       catch(Exception e){
           e.printStackTrace();
           System.err.println("Problems retrieving data from db...");
       }
   }
   
   /**
    * 
    * @return 
    */
   public Vector<String> listarSotck() throws java.rmi.RemoteException {
       
       Vector<String> ret = new Vector<String>();
       ResultSet rs;
       String query = "SELECT nome_loja"
                        + ", nome_produto"
                        + " FROM Stock"
                        + " ORDER BY nome_loja ASC;";
       
       try {
           rs = stm.executeQuery(query);
           
           while(rs.next())
               ret.add(rs.getString("nome_loja") + rs.getString("nome_produto"));
           
           rs.close();
       }
       catch(Exception e){
           e.printStackTrace();
           System.err.println("Problems retrieving data from db...");
       }
       return ret;
   }
    
   /**
    * 
    * @param nomeLoja
    * @return 
    */
   public Vector<String> listarSotck(String nomeLoja) throws java.rmi.RemoteException {
       Vector<String> ret = new Vector<String>();
       ResultSet rs;
       
       if(!queryValida(nomeLoja))
           return ret;
       
        String query = "SELECT nome_loja"
        + ", nome_produto"
        + " FROM Stock"
        + "WHERE nome_loja='" + nomeLoja + "';";

       try {
           rs = stm.executeQuery(query);
           
           while(rs.next())
               ret.add(rs.getString("nome_loja") + rs.getString("nome_produto"));
           
           rs.close();
       }
       catch(Exception e){
           e.printStackTrace();
           System.err.println("Problems retrieving data from db...");
       }
       return ret;
   }

   /**
    * 
    * @return 
    */
   public Vector<String> listarProdCrit() throws java.rmi.RemoteException {
    Vector<String> ret = new Vector<String>();
    ResultSet rs;

    String query = "SELECT nome_produto"
                    + ", COUNT(id) as \"procura\""
                    + " FROM Requisitos"
                    + " ORDER BY (nome_produto);";
       
    try {
        rs = stm.executeQuery(query);
        
        while(rs.next())
            ret.add(rs.getString("nome_produto") + rs.getString("procura"));
        
        rs.close();
    }
    catch(Exception e){
        e.printStackTrace();
        System.err.println("Problems retrieving data from db...");
    }
    return ret;
   }

   // TODO: CRIA NOVO THREAD E LIGAÇÃO?
   /**
    * 
    * @param username
    * @param nomeProd
    * @return 
    */
   public String reqProd(String username, String nomeProd) throws java.rmi.RemoteException {
    ResultSet rs;

    // Query não va'lida
    if(!queryValida(username) || !queryValida(nomeProd))
        return "-1";

    String query1 = "SELECT id"
                    + " FROM Requisitos"
                    + " WHERE username = '" + username + "'"
                    + " AND nome_produto = '" + nomeProd + "';";

    String query2 = "INSERT INTO Produto VALUES('" + nomeProd + "');";

    String query3 = "INSERT INTO Requisitos VALUES(" + req + ", '" + username
                    + "', '" + nomeProd + "');";
       
    try {
        rs = stm.executeQuery(query1);
        
        // Ja' existe na tabela
        if(rs.next()) {
            rs.close();
            return "-2";
        }

        stm.executeQuery(query2);

        stm.executeQuery(query3);

        rs.close();
    }
    catch(Exception e){
        e.printStackTrace();
        System.err.println("Problems retrieving data from db...");
    }
    
    return "" + (++req);
   }

   // TODO: ENVIA SIGNAL PARA THREAD?
   /**
    * 
    * @param nomeLoja
    * @param nomeProd 
    */
   public void reportarProd(String nomeLoja, String nomeProd) throws java.rmi.RemoteException {

    // Query não va'lida
    if(!queryValida(nomeLoja) || !queryValida(nomeProd))
        return;

    String query1 = "INSERT INTO Produto VALUES('" + nomeProd + "');";

    String query2 = "INSERT INTO Stock VALUES('" + nomeLoja
                    + "', '" + nomeProd + "');";
       
    try {
        stm.executeQuery(query1);
        
        stm.executeQuery(query2);
    }
    catch(Exception e){
        e.printStackTrace();
        System.err.println("Problems retrieving data from db...");
    }
   }
   
    public void disconnect() throws java.rmi.RemoteException {
        pc.disconnect();
    }
}
