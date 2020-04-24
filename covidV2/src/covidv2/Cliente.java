package covidv2;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.covid.*;
import so2.ObjFile;

/**
 *
 * @author Tiago Martinho, João Marques
 */
public class Cliente {
    
    public class ClienteSub extends Thread {
        private String host;
        private int porta;
        private String id;
        Socket s;
        
        public ClienteSub(String host, int porta, String id) {
            this.host = host;
            this.porta = porta;
            this.id = id;
            
            try{
                try {
                    this.s = new Socket(host, porta);
                }
                catch(Exception e) {
                    System.err.println("ERRO: ao ligar ao servidor");
                    e.printStackTrace();
                    System.exit(1);
                }
                
                OutputStream socketOut = s.getOutputStream();
                
                try {
                    String message = id + "\n";
                    socketOut.write( message.getBytes() );
                    socketOut.flush();
                }
                catch (Exception e) {
                    System.err.println("ERRO: ao enviar o pedido");
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        public void run() {
            try {
                InputStream socketIn = s.getInputStream();
                
                
                byte[] b= new byte[256];
                int lidos= 0;
                
                try {
                    lidos = socketIn.read(b);
                }
                catch (IOException ioe) {
                    System.err.println("ERRO: ao ler a resposta: "+ioe);
                    throw ioe;
                }

                String resp= new String(b,0,lidos);

                System.out.println(resp);

            }
            catch (Exception e) {
                e.printStackTrace();
                System.err.println("ERRO: durante a comunicacao com o servidor: "+e);
            }
            finally {
                // fechar sempre o socket
                try {
                    s.close();
                }
                catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }
    
    private String host, userName;
    private int porta;
    private PostgresConn dbConn;
    private String regHost, regPort;
    private Vector<String> requisitos;
    
    public Cliente(String host, int porta, String regHost, String regPort) {
        this.host = host;
        this.porta = porta;
        this.regHost = regHost;
        this.regPort = regPort;
        this.requisitos = new Vector<>();
        
        try {
            this.dbConn =
                (PostgresConn) java.rmi.Naming.lookup("rmi://" + regHost + ":" + 
                                                  regPort + "/PostgresConnector");
            
            dbConn.connect();
            
        }
        catch(Exception e) {
            System.err.println("Erro a criar objecto remoto");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    public void login() {
        Scanner sc = new Scanner(System.in);
        int escolha;
        
        while(true) {
            System.out.println("1 - Registar novo utilizador\n2 - Login\n");
            escolha = sc.nextInt();
            sc.nextLine();

            //TODO meter try caso nao exista int?
            try {
                switch(escolha) {
                    case 1:
                        System.out.println("Nome de utilizador:");
                        System.out.print("-> ");
                        userName = sc.nextLine();
                        System.out.println();
                        if(dbConn.registar(userName)) {
                            System.out.println("Nome de utilizador: " + userName
                                    + " registado com sucesso");
                            dbConn.login(userName);
                            System.out.println("\nBem vindo");
                            return;
                        }
                        System.out.println("Utilizador já existente");
                        break;

                    case 2:
                        System.out.println("Nome de utilizador:");
                        System.out.print("-> ");
                        userName = sc.nextLine();
                        System.out.println();
                        if(dbConn.login(userName)) {
                            System.out.println("\nBem vindo");
                            return;
                        }
                        System.out.println("Utilizador não existente");
                        break;

                    default:
                        System.out.println("Opcção não válida\n");
                        break;
                }
            }
            catch(Exception e) {
               //e.printStackTrace();
            }
        }
    }
    
    public void ultimaSessao() {
        Scanner sc = new Scanner(System.in);
        int escolha;
        
        try {
            ObjFile of = new ObjFile("Registo_" + userName);
            
            Object obj = of.le();
            
            if(obj instanceof Registo) {
                Registo ultimo = (Registo) obj;
                
                // O ultimo pedido foi respondido
                if(ultimo.getResposta()) {
                    while(true) {
                        System.out.println("Mostar ultimo resultado");
                        System.out.println("1 - Sim\n2 - Não");
                        escolha = sc.nextInt();
                        sc.nextLine();
                        System.out.println();
                        
                        switch(escolha) {
                            case 1:
                                switch(ultimo.getTipoPedido()) {
                                    case 1:
                                    case 2:
                                        System.out.println("Nome da Loja\tNome do Produto\n"
                                            + "-----------------------------------");
        
                                        for(int i = 0; i < ultimo.getVStock().size(); i++)
                                            System.out.println(ultimo.getVStock().get(i).toString());
                                        break;
                                        
                                    case 3:
                                        System.out.println("Nome do Produto\tProcura\n"
                                            + "-----------------------------------");
        
                                        for(int i = 0; i < ultimo.getVProduto().size(); i++)
                                            System.out.println(ultimo.getVProduto().get(i).toString());
                                        break;
                                        
                                    case 4:
                                        if(ultimo.getResultado()) 
                                            System.out.println("Produto requisitado com id:" + ultimo.getId());
                                        else
                                            System.out.println("Impossível requisitar produto");
                                        break;
                                        
                                    case 5:
                                        if(ultimo.getResultado()) 
                                            System.out.println("Enviado com sucesso");
                                        else
                                            System.out.println("Erro a enviar");
                                        break;
                                        
                                    default:
                                        System.err.println("Erro no tipo do ultimo resultado");
                                        break;
                                }
                                return;
                            case 2:
                                return;
                                
                            default:
                                System.out.println("Escolha não válida");
                                break;
                        }
                    }
                }
                
                // O ultimo pedido nao foi respondido
                else {
                    while(true) {
                        System.out.println("Reenviar ultimo pedido");
                        System.out.println("1 - Sim\n2 - Não");
                        escolha = sc.nextInt();
                        sc.nextLine();
                        System.out.println();
                        
                        switch(escolha) {
                            case 1:
                                switch(ultimo.getTipoPedido()) {
                                    case 1:
                                        listarStock(null);
                                        break;
                                        
                                    case 2:
                                        listarStock(ultimo.getNomeLoja());
                                        break;
                                        
                                    case 3:
                                        listarProduto();
                                        break;
                                        
                                    case 4:
                                        requisitarProd(ultimo.getNomeProduto());
                                        break;
                                        
                                    case 5:
                                        reportarProd(ultimo.getNomeProduto(), ultimo.getNomeLoja());
                                        break;
                                        
                                    default:
                                        System.err.println("Erro no tipo do ultimo resultado");
                                        break;
                                }
                                return;
                            case 2:
                                return;
                                
                            default:
                                System.out.println("Escolha não válida");
                                break;
                        }
                    }
                }
            }
            else {
                System.err.println("Erro no tipo de registo");
            }
        }
        catch(Exception e) {
            System.err.println("Erro a ler registo");
            //e.printStackTrace();
        }
    }
    
    public void simula() throws InterruptedException {
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.println(".");
        Thread.sleep(500);
    }
    
    public void menu() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int escolha;
        String nomeLoja, nomeProduto;
        
        Registo registo;
        
        while(true) {
            System.out.println("\n1 - Listar stock\n"
                    + "2 - Listar stock de uma loja\n"
                    + "3 - Listar produtos necessários\n"
                    + "4 - Requisitar produto\n"
                    + "5 - Reportar produto\n");
            
            escolha = sc.nextInt();
            sc.nextLine();
            System.out.println();
            
            switch(escolha) {
                case 1:
                    registo = new Registo(userName, escolha, null, null);
                    registo.guardar();
                    
                    simula();
                    
                    listarStock(null);
                    break;
                case 2:
                    System.out.println("Loja:");
                    nomeLoja = sc.nextLine();
                    
                    registo = new Registo(userName, escolha, nomeLoja, null);
                    registo.guardar();
                    
                    simula();
                    
                    listarStock(nomeLoja);
                    break;
                case 3:
                    registo = new Registo(userName, escolha, null, null);
                    registo.guardar();
                    
                    simula();
                    
                    listarProduto();
                    break;
                case 4:
                    System.out.println("Produto:");
                    nomeProduto = sc.nextLine();
                    
                    registo = new Registo(userName, escolha, null, nomeProduto);
                    registo.guardar();
                    
                    simula();
                    
                    requisitarProd(nomeProduto);
                    break;
                case 5:
                    System.out.println("Produto:");
                    nomeProduto = sc.nextLine();
                    System.out.println("Loja:");
                    nomeLoja = sc.nextLine();
                    
                    registo = new Registo(userName, escolha, nomeLoja, nomeProduto);
                    registo.guardar();
                    
                    simula();
                    
                    reportarProd(nomeProduto, nomeLoja);
                    break;
                default:
                    System.out.println("Escolha não válida");
                    break;
            }
        }
        
    }
    
    public void listarStock(String nomeLoja) {
        Vector<Stock> resultado;
        Registo registo;
        
        try {
            if(nomeLoja == null){
                resultado = dbConn.listarStock();
                registo = new Registo(userName, 1, resultado, null, null, true);
            }
            else{
                resultado = dbConn.listarStock(nomeLoja);
                registo = new Registo(userName, 2, resultado, null, null, true);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }
        
        // Guarda resposta
        registo.guardar();
        
        if(resultado.isEmpty()) {
            System.out.println("Sem dados para mostar");
            return;
        }
        
        System.out.println("Nome da Loja\tNome do Produto\n"
                + "-----------------------------------");
        
        for(int i = 0; i < resultado.size(); i++)
            System.out.println(resultado.get(i).toString());
    }
    
    public void listarProduto() {
        Vector<Produto> resultado;
        Registo registo;
        
        try {
            resultado = dbConn.listarProdNec();
            
            registo = new Registo(userName, 3, null, resultado, null, true);
            
            // guarda registo
            registo.guardar();
            
            if(resultado.isEmpty()) {
                System.out.println("Sem dados para mostar");
                return;
            }
        
            System.out.println("Nome do Produto\tProcura\n"
                + "-----------------------------------");
        
        for(int i = 0; i < resultado.size(); i++)
            System.out.println(resultado.get(i).toString());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void requisitarProd(String nomeProduto) {
        ClienteSub novo;
        String id;
        Registo registo;
        
        try {
            id = dbConn.reqProd(userName, nomeProduto);
            
            if(id.equals("0")){
                System.out.println(nomeProduto + " existe em Stock");
                registo = new Registo(userName, 4, null, null, id, false);
            }
            
            else if(id.equals("-1")){
                System.out.println("Já requisitou " + nomeProduto + "antes");
                registo = new Registo(userName, 4, null, null, id, false);
            }
            
            else{
                registo = new Registo(userName, 4, null, null, id, true);
                
                requisitos.add(id);
                
                // TODO requisitos guardar
                
                System.out.println("Produto requisitado com id:" + id);
                
                novo = new ClienteSub(host, porta, id);
                
                novo.start();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }
        
        // Guardar registo
        registo.guardar();
    }
    
    public void reportarProd(String nomeProduto, String nomeLoja) {
        Registo registo;
        
        try {            
            if(dbConn.repProd(nomeProduto, nomeLoja)) {
                registo = new Registo(userName, 5, null, null, null, true);
                registo.guardar();
                System.out.println("Enviado com sucesso");
                return;
            }
        
            registo = new Registo(userName, 5, null, null, null, false);
            registo.guardar();
            System.out.println("Não foi possível enviar");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        
        if(args.length != 4) {
            System.err.println("Args: Subscribe host, Subscribe port, Registry host, Registry port");
            System.exit(1);
        }
        
        String host = args[0];
        int porta = Integer.parseInt(args[1]);
        String regHost = args[2];
        String regPort = args[3];
        
        
        Cliente cliente = new Cliente(host, porta, regHost, regPort);
        
        System.out.println("+ Connectado à base de dados");
        
        cliente.login();
        
        cliente.ultimaSessao();
        
        try {
            cliente.menu();
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
