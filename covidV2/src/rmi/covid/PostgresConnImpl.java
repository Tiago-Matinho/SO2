package rmi.covid;

import covidv2.*;
import server.conc.*;
import java.sql.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 *
 * @author Tiago Martinho, João Marques
 */
public class PostgresConnImpl extends UnicastRemoteObject implements PostgresConn, java.rmi.Remote {
    private String PG_HOST;
    private String PG_DB;
    private String USER;
    private String PWD;
    
    private ServidorConc subs;

    Connection con = null;
    Statement stmt = null;

    public PostgresConnImpl(String host, String db, String user, String pw, int porta) throws Exception {
        PG_HOST=host;
        PG_DB= db;
        USER=user;
        PWD= pw;
        
        // inicia servidor concorrente para enviar satisfazer subscricoes
        subs = new ServidorConc(porta);
        subs.start();   //comeca noutro thread
    }

    /*************************************************************************/
    
    public boolean connect() throws java.rmi.RemoteException {
        try {
            Class.forName("org.postgresql.Driver");
            
            con = DriverManager.getConnection("jdbc:postgresql://" + PG_HOST +
                    ":5432/" + PG_DB, USER, PWD);
            stmt = con.createStatement();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\nErro a establecer ligação...\n");
            return false;
        }
    }

    public boolean registar(String userName) throws java.rmi.RemoteException {
        // query1 -> verifica se ja existe um utilizador com esse username
        String query1 = "SELECT exists(SELECT 1 FROM Utilizador"
                + " WHERE username = '" + userName + "')";
        
        //query2 -> adiciona o username na BD
        String query2 = "INSERT INTO Utilizador VALUES('" + userName + "')";
       
        try {
            ResultSet rs = stmt.executeQuery(query1);
            
            // ja existe um utilizador com esse nome...
            if(rs.next()){
                if(rs.getBoolean("exists")){
                    rs.close();
                    return false;
                }
            }
            
            // cria esse utilizador
            stmt.executeUpdate(query2);
            return true;
        }
        
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nErro a registar username: " + userName + "...\n");
            return false;
        }
    }
    
    public boolean login(String userName) throws java.rmi.RemoteException {
        //query1 -> procura na BD um utilizador com este username
        String query1 = "SELECT exists(SELECT 1 FROM Utilizador"
                + " WHERE username = '" + userName + "')";
        
        try {
            ResultSet rs = stmt.executeQuery(query1);
            
            // existe um utilizador com esse nome
            if(rs.next()){
                if(rs.getBoolean("exists")){
                    rs.close();
                    return true;
                }
            }
        }
        
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nErro a fazer login com username: " + userName + "...\n");
        }
        
        return false;
    }
    
    public Vector<Stock> listarStock() throws java.rmi.RemoteException {
       Vector<Stock> ret = new Vector<Stock>();
       
       // query1 -> retorna todas as lojas e produtos que ja foram reportados
       String query1 = "SELECT nome_loja"
                        + ", nome_produto"
                        + " FROM Stock"
                        + " ORDER BY nome_loja ASC";
       Stock curr;
       
       try {
           ResultSet rs = stmt.executeQuery(query1);
           
           while(rs.next()){
               curr = new Stock(rs.getString("nome_loja"), rs.getString("nome_produto"));
               ret.add(curr);
           }
           
           rs.close();
       }
       catch(Exception e){
           e.printStackTrace();
           System.out.println("\nErro a listar stocks...\n");
       }
       return ret;
    }
    
    public Vector<Stock> listarStock(String nomeLoja) throws java.rmi.RemoteException {
       Vector<Stock> ret = new Vector<Stock>();
       //query1 -> procura a loja e retorna a loja e os produtos que foram reportados
       String query1 = "SELECT nome_loja"
                        + ", nome_produto"
                        + " FROM Stock"
                        + " WHERE nome_loja = '" + nomeLoja + "'";
       Stock curr;
       
       try {
           ResultSet rs = stmt.executeQuery(query1);
           
           while(rs.next()){
               curr = new Stock(rs.getString("nome_loja"), rs.getString("nome_produto"));
               ret.add(curr);
           }
           
           rs.close();
       }
       catch(Exception e){
           e.printStackTrace();
           System.out.println("\nErro a listar stocks da loja: " + nomeLoja + "...\n");
       }
       return ret;
    }
    
    public Vector<Stock> listarLojas(String nomeProd) throws java.rmi.RemoteException {
        Vector<Stock> ret = new Vector<Stock>();
       //query1 -> procura em que lojas o produto ja foi reportados
       String query1 = "SELECT nome_loja"
                        + ", nome_produto"
                        + " FROM Stock"
                        + " WHERE nome_produto = '" + nomeProd + "'";
       Stock curr;
       
       try {
           ResultSet rs = stmt.executeQuery(query1);
           
           while(rs.next()){
               curr = new Stock(rs.getString("nome_loja"), rs.getString("nome_produto"));
               ret.add(curr);
           }
           
           rs.close();
       }
       catch(Exception e){
           e.printStackTrace();
           System.out.println("\nErro a listar lojas com o produto " + nomeProd + "...\n");
       }
       return ret;
    }
    
    public Vector<Produto> listarProd() throws java.rmi.RemoteException {
        Vector<Produto> ret = new Vector<Produto>();

        String query1 = "SELECT nome_produto"
                        + ", COUNT(nome_produto) as \"procura\""
                        + " FROM Produto"
                        + " GROUP BY (nome_produto)";
        
        Produto produto;
        
        try {
            ResultSet rs = stmt.executeQuery(query1);

            while(rs.next()) {
                produto = new Produto(rs.getString("nome_produto"), rs.getInt("procura"));
                ret.add(produto);
            }
            
            rs.close();
        }
        catch(Exception e){
            System.out.println("Erro a listar produtos...");
            // e.printStackTrace();
        }
        return ret;
    }
    
    public Vector<Produto> listarProdNec() throws java.rmi.RemoteException {
        Vector<Produto> ret = new Vector<Produto>();

        String query1 = "SELECT nome_produto"
                        + ", COUNT(id) as \"procura\""
                        + " FROM Requisitos"
                        + " GROUP BY (nome_produto)";
        
        Produto produto;
        
        try {
            ResultSet rs = stmt.executeQuery(query1);

            while(rs.next()) {
                produto = new Produto(rs.getString("nome_produto"), rs.getInt("procura"));
                ret.add(produto);
            }
            
            rs.close();
        }
        catch(Exception e){
            System.out.println("Erro a listar produtos criticos...");
            e.printStackTrace();
        }
        return ret;
    }
    
    public String reqProd(String userName, String nomeProd) throws java.rmi.RemoteException {
        String query1 = "SELECT exists(SELECT 1"
                    + " FROM Stock"
                    + " WHERE nome_produto = '" + nomeProd + "')";
        
        String query2 = "SELECT exists(SELECT 1"
                    + " FROM Requisitos"
                    + " WHERE username = '" + userName + "'"
                    + " AND nome_produto = '" + nomeProd + "')";
    
        String query3 = "SELECT exists(SELECT 1"
                    + " FROM Produto"
                    + " WHERE nome_produto = '" + nomeProd + "')";

        String query4 = "INSERT INTO Produto VALUES('" + nomeProd + "')";

        String query5 = "INSERT INTO Requisitos VALUES('" + userName+nomeProd + "', '"
                    + userName + "', '" + nomeProd + "')";
       
    
        try {
            ResultSet rs = stmt.executeQuery(query1);

            // Produto existe em stock
            if(rs.next()) {
                if(rs.getBoolean("exists")) {
                    rs.close();
                    return "0";
                }
            }

            rs.close();
            
            rs = stmt.executeQuery(query2);

            // Este utilizador ja requisitou esse produto
            if(rs.next()) {
                if(rs.getBoolean("exists")) {
                    rs.close();
                    return "-1";
                }
            }

            rs.close();

            rs = stmt.executeQuery(query3);

            // Ainda nao foi encontrado este produto
            if(rs.next()) {
                if(!rs.getBoolean("exists"))
                    stmt.executeUpdate(query4); // Adiciona
            }

            rs.close();

            // Adiciona o requisito
            stmt.executeUpdate(query5);
        }

        catch(Exception e){
            // e.printStackTrace();
            System.out.println("Erro a inserir pedido de: " + nomeProd
                    + "feito por: " + userName + "...");
        }
        
        // adiciona o pedido ao servidor concorrente de subscricoes
        subs.novoID(userName+nomeProd);
        
        return userName+nomeProd;
    }
    
    public boolean repProd(String nomeProd, String nomeLoja) throws java.rmi.RemoteException {
        String query1 = "SELECT exists(SELECT 1"
                    + " FROM Stock"
                    + " WHERE nome_produto ='" + nomeProd + "'"
                    + " AND nome_loja ='" + nomeLoja + "')";   
        
        String query2 = "SELECT id"
                    + " FROM Requisitos"
                    + " WHERE nome_produto ='" + nomeProd + "'"; 
       
        String query3 = "SELECT exists(SELECT 1"
                    + " FROM Produto"
                    + " WHERE nome_produto ='" + nomeProd + "')";
       
        String query4 = "INSERT INTO Produto VALUES('" + nomeProd + "')";

        String query5 = "INSERT INTO Stock VALUES('" + nomeLoja
                    + "', '" + nomeProd + "')";
        
        try {
            ResultSet rs = stmt.executeQuery(query1);

            // Este produto ja existe em stock
            if(rs.next()) {
               if(rs.getBoolean("exists")) {
                    rs.close();
                    return false;
                }
            }

            rs.close();

            rs = stmt.executeQuery(query3);
            
            // Ainda nao foi encontrado este produto
            if(rs.next()) {
                if(!rs.getBoolean("exists"))
                    stmt.executeUpdate(query4); // Adiciona
            }

            rs.close();
            
            
            rs = stmt.executeQuery(query2);
            
            String id;
            String removerReq;
            Vector<String> satisfeitos = new Vector<>();
            
            // Este produto é requisitado por um ou mais utilizadores
            while(rs.next()) {
                id = rs.getString("id");
                if(subs.enviar(id, "\n--> " + nomeProd + " foi encontrado na loja: " + nomeLoja + "\n")) {
                    System.out.println("Pedido: " + id + " satisfeito");
                    removerReq = "DELETE FROM Requisitos WHERE id = '" + id + "'";
                    satisfeitos.add(removerReq);
                }
                
                else
                    System.out.println("Erro a satisfazer pedido: " + id);
            }
            
            rs.close();
           
            // Adiciona aos stocks
            stmt.executeUpdate(query5);

            for(int i = 0; i < satisfeitos.size(); i++)
                stmt.executeUpdate(satisfeitos.get(i));
            
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Erro a inserir: " + nomeProd + "na loja: "
                    + nomeLoja +"...");
            return false;
        }
        return true;
    }
    
    public boolean disconnect() throws java.rmi.RemoteException {
        try {
            stmt.close();
            con.close();
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }
    
}
