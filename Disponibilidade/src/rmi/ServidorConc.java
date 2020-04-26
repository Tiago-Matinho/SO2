package rmi;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author Tiago Martinho, João Marques
 */
public class ServidorConc extends Thread {
    
    private int porta;
    private boolean ativo;
    private Vector<String> idList;
    private HashMap<String,Socket> map;
    
    public ServidorConc(int porta) {
        this.porta = porta;
        this.ativo = true;
        this.idList = new Vector<String>();
        this.map = new HashMap<String, Socket>();
    }
    
    public boolean novoID(String id) {
        if(idList.contains(id))
            return false;
        
        idList.add(id);
        return true;
    }
    
    // chamado uma vez
    public void run() {
        ServerSocket server = null;
        
        try {
            server = new ServerSocket(porta);
            System.out.println("+ Servidor de subscrições ativo");
                     
            while(ativo) {
                Socket data = null;

                data = server.accept();
                System.err.println("+ Chegou um pedido");

                if(atende(data))
                    System.out.println("+ Pedido foi aceite");

                else
                   System.out.println("+ Pedido foi recusado");
            }
        }
        catch(Exception e) {
            System.out.println("+ Erro a inicializar servidor concorrente");
            e.printStackTrace();
        }
        
        System.out.println("+ Servidor de subscrições desligado");
    }
    
    public boolean atende(Socket data) {
        
        try {
            BufferedReader breader = new BufferedReader(new InputStreamReader(data.getInputStream()));
            
            String id = breader.readLine();
            
            System.err.println("+ recebeu ID");
            
            // o cliente tinha ido a baixo e ligou-se com outra porta
            if(map.containsKey(id))
                map.replace(id, data);

            // o servidor reconhece como uma id valida
            else if(idList.contains(id)) {
                idList.remove(id);
                map.put(id, data);
            }
            
            else
                return false;
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("PROBLEMA no atendimento do pedido: " + e);
        }
        return false;
    }

    public boolean enviar(String id, String mensagem) {
        if(!map.containsKey(id))
            return false;
        
        Socket data = map.get(id);
        
        
        try {
            DataOutputStream sout = new DataOutputStream(data.getOutputStream());
            sout.writeUTF(mensagem);
            System.err.println("Enviada mensagem: " + mensagem);
            map.remove(id);
        }
        catch (Exception e) {
           e.printStackTrace();
            System.err.println("PROBLEMA no atendimento do pedido: " + e);
        }
        
        finally {  // haja ou nao excepcao
            // muito importante: fechar o socket de dados
            if (data != null) {
                try {
                    data.close();        //  MUITO IMPORTANTE... fechar sempre
                } catch (Exception ec) {
                    System.err.println(ec);
                }
            }
        }
        return true;
    }
}
