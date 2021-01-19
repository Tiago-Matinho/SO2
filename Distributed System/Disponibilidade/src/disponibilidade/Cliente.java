package disponibilidade;

import java.io.*;
import java.net.*;
import java.util.*;
import rmi.*;
import so2.ObjFile;

/**
 *
 * @author João Marques, Tiago Martinho
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
                    System.err.println("Enviado novo id");
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
        
        try {
            this.dbConn =
                (PostgresConn) java.rmi.Naming.lookup("rmi://" + regHost + ":" + 
                                                  regPort + "/PostgresConnector");
            
            
            
        }
        catch(Exception e) {
            System.err.println("Erro a criar objecto remoto");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /*
    Regista um novo utilizador na BD, ou caso o username ja exista, faz login.
    Pretende-se num futuro escalar para passwords associadas ao username. 
    */
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
                            System.out.println("Nome de utilizador " + userName
                                    + " registado com sucesso");
                            dbConn.login(userName);
                            System.out.println("\nBem vindo\n");
                            return;
                        }
                        System.out.println("Utilizador já existente");
                        break;

                    case 2:
                        System.out.println("Nome de utilizador:");
                        System.out.print("-> ");
                        userName = sc.nextLine();
                        System.out.println();
                        if(dbConn.login(userName)) { // sai da funcao se existir na BD o username
                            System.out.println("\nBem vindo\n");
                            return;
                        }
                        System.out.println("Utilizador não existente\n");
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
  
    /*
    Procura localmente os requisitos feitos pelo utilizador numa sessao anterior.
    De seguida verifica se os requisitos foram respondidos enquanto o utilizador
    estava inativo.
    Caso nao tenham sido, inicia o processo para receber respostas, criando um
    ClienteSub que se liga ao servidor usando o id do pedido e espera por uma resposta
    nesse socket num novo thread.
    */
    public void ultimoRequisitos() {
        try {
            ObjFile of = new ObjFile("Requisitos_" + userName);
            Object obj = of.le();
            
            
            if(obj instanceof Requisito) {
                Requisito req = (Requisito) obj;
                
                Requisito update;
                ClienteSub novo;
                String id;
                
                Vector<Stock> pesquisa;
                
                requisitos = req.getVID();
                
                for(int i = 0; i < requisitos.size(); i++ ) {
                    //verificar se já foi respondido
                    id = requisitos.get(i);
                    pesquisa = dbConn.listarProd(id.substring(userName.length()));
                    
                    // ainda nao foi respondido
                    if(pesquisa.isEmpty()) {
                        novo = new ClienteSub(host, porta, requisitos.get(i));
                        novo.start();
                    }
                    else {
                        System.out.println("\n+ Pedido com ID: " + id + " foi respondido enquanto estava ofline +\n");
                        System.out.println("Nome da Loja\tNome do Produto\n"
                            + "-----------------------------------");
        
                        for(int j = 0; j < pesquisa.size(); j++)
                            System.out.println(pesquisa.get(j).toString());
                        
                        System.out.println("\n");
                        
                        // remove pq foi respondido
                        requisitos.remove(i);
                        update = new Requisito(requisitos, userName);
                        // guarda o novo estado de requisitos
                        update.guardar();
                        
                    }
                }
            }   
        }
        catch(Exception e) {
            System.err.println("\n--- Sem requisitos da ultima da sessão ---\n");
            this.requisitos = new Vector<String>();
        }
    }
    
    /*
    Procura localmente os ultimos registos feitos pelo utilizador na sessao anterior.
    Caso nao tenham recebido uma resposta do servidor, pergunta se o utilizador
    quer reenviar.
    Caso tenha obtido uma resposta pergunta se o utilizador a quer ver.
    */
    public void ultimaSessao() {
        Scanner sc = new Scanner(System.in);
        int escolha;
        
        try {
            ObjFile of = new ObjFile("Registo_" + userName);
            
            Object obj = of.le();
            
            if(obj instanceof Registo) {
                Registo ultimo = (Registo) obj;
                
                if(ultimo.getTipoPedido() == 7)
                    return;
                
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
                                        System.out.println("Pediu para ver os stocks\n");
                                    case 2:
                                    case 3:
                                        if(ultimo.getTipoPedido() == 2) {
                                            System.out.println("Pediu para ver stock da loja: "
                                                    + ultimo.getNomeLoja() + "\n");
                                        }
                                        if(ultimo.getTipoPedido() == 3) {
                                            System.out.println("Pediu para ver lojas com o produto: "
                                                    + ultimo.getNomeProduto()+ "\n");
                                        }
                                        
                                        System.out.println("Nome da Loja\tNome do Produto\n"
                                            + "-----------------------------------");
        
                                        for(int i = 0; i < ultimo.getVStock().size(); i++)
                                            System.out.println(ultimo.getVStock().get(i).toString());
                                        break;
                                        
                                    case 4:
                                        System.out.println("Pediu para ver os produtos requisitados\n");
                                        System.out.println("Nome do Produto\tProcura\n"
                                            + "-----------------------------------");
        
                                        for(int i = 0; i < ultimo.getVProduto().size(); i++)
                                            System.out.println(ultimo.getVProduto().get(i).toString());
                                        break;
                                        
                                    case 5:
                                        System.out.println("Requisitou: " + ultimo.getNomeProduto() + "\n");
                                        if(ultimo.getResultado()) 
                                            System.out.println("Produto requisitado com id: " + ultimo.getId());
                                        else
                                            System.out.println("Impossível requisitar produto");
                                        break;
                                        
                                    case 6:
                                        System.out.println("Reportou: " + ultimo.getNomeProduto() +
                                                "na loja: " + ultimo.getNomeLoja()+ "\n");
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
                                        System.out.println("Pediu para ver os stocks\n");
                                        simula();
                                        listarStock(null);
                                        break;
                                        
                                    case 2:
                                        System.out.println("Pediu para ver stock da loja: "
                                                    + ultimo.getNomeLoja() + "\n");
                                        simula();
                                        listarStock(ultimo.getNomeLoja());
                                        break;
                                        
                                    case 3:
                                        System.out.println("Pediu para ver lojas com o produto: "
                                                    + ultimo.getNomeProduto()+ "\n");
                                        simula();
                                        procurarProd(ultimo.getNomeProduto());
                                        break;
                                    case 4:
                                        System.out.println("Pediu para ver os produtos requisitados\n");
                                        simula();
                                        listarProduto();
                                        break;
                                        
                                    case 5:
                                        System.out.println("Requisitou: " + ultimo.getNomeProduto() + "\n");
                                        simula();
                                        requisitarProd(ultimo.getNomeProduto());
                                        break;
                                        
                                    case 6:
                                        System.out.println("Reportou: " + ultimo.getNomeProduto() +
                                                "na loja: " + ultimo.getNomeLoja()+ "\n");
                                        simula();
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
            System.err.println("\n--- Sem pedidos ou respostas da última sessão ---\n");
            //e.printStackTrace();
        }
    }
    
    /*
    Simula o tempo de comunicacao entre o servidor e o cliente.
    */
    public void simula() throws InterruptedException {
        System.out.print("\nfetching");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.println(".\n");
        Thread.sleep(500);
    }
    
    /*
    Menu onde se encontra o ciclo de atendimento dos pedidos do cliente.
    Quando o cliente faz uma escolha de pedido guarda um registo localmente.
    
    De seguida chama um metodo do objecto remoto que establece a ligacao a BD e
    responde ao pedido.
    
    Entre a escolha do cliente e a chamada do metodo chama a funcao simula, que
    simula o tempo de resposta do servidor.
    */
    public void menu() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int escolha;
        String nomeLoja, nomeProduto;
        
        Registo registo;
        
        while(true) {
            System.out.println("\n"
                    + "1 - Listar stock\n"
                    + "2 - Listar stock de uma loja\n"
                    + "3 - Procurar produto em lojas\n"
                    + "4 - Listar produtos necessários\n"
                    + "5 - Requisitar produto\n"
                    + "6 - Reportar produto\n"
                    + "7 - Sair\n");
            
            System.out.print("-> ");
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
                    System.out.print("-> ");
                    nomeLoja = sc.nextLine();
                    
                    registo = new Registo(userName, escolha, nomeLoja, null);
                    registo.guardar();
                    
                    simula();
                    
                    listarStock(nomeLoja);
                    break;
                case 3:
                    System.out.println("Produto:");
                    System.out.print("-> ");
                    nomeProduto = sc.nextLine();
                    
                    registo = new Registo(userName, escolha, null, nomeProduto);
                    registo.guardar();
                    
                    simula();
                    
                    procurarProd(nomeProduto);
                break;
                case 4:
                    registo = new Registo(userName, escolha, null, null);
                    registo.guardar();
                    
                    simula();
                    
                    listarProduto();
                    break;
                case 5:
                    System.out.println("Produto:");
                    System.out.print("-> ");
                    nomeProduto = sc.nextLine();
                    
                    registo = new Registo(userName, escolha, null, nomeProduto);
                    registo.guardar();
                    
                    simula();
                    
                    requisitarProd(nomeProduto);
                    break;
                case 6:
                    System.out.println("Produto:");
                    System.out.print("-> ");
                    nomeProduto = sc.nextLine();
                    System.out.println("Loja:");
                    System.out.print("-> ");
                    nomeLoja = sc.nextLine();
                    
                    registo = new Registo(userName, escolha, nomeLoja, nomeProduto);
                    registo.guardar();
                    
                    simula();
                    
                    reportarProd(nomeProduto, nomeLoja);
                    break;
                case 7:
                    registo = new Registo(escolha, userName);
                    registo.guardar();
                    return;
                default:
                    System.out.println("Escolha não válida\n");
                    break;
            }
        } 
    }
    
    /*
    Usando o objecto remoto procura na BD os stocks e imprime.
    Ao receber a resposta do servidor guarda localmente.
    */
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
                registo.setNomeLoja(nomeLoja);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }
        
        // Guarda resposta
        registo.guardar();
        
        if(resultado.isEmpty()) {
            System.out.println("Sem stocks disponíveis");
            return;
        }
        
        System.out.println("Nome da Loja\tNome do Produto\n"
                + "-----------------------------------");
        
        for(int i = 0; i < resultado.size(); i++)
            System.out.println(resultado.get(i).toString());
    }
    
    /*
    Usando o objecto remoto procura na BD as lojas que tem o produto desejado.
    Ao receber a resposta do servidor guarda localmente.
    */
    public void procurarProd(String nomeProd) {
        Vector<Stock> resultado;
        Registo registo;
        
        try {
            resultado = dbConn.listarProd(nomeProd);
            registo = new Registo(userName, 3, resultado, null, null, true);
            registo.setNomeProd(nomeProd);
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }
        
        // Guarda resposta
        registo.guardar();
        
        if(resultado.isEmpty()) {
            System.out.println("Sem stocks disponíveis");
            return;
        }
        
        System.out.println("Nome da Loja\tNome do Produto\n"
                + "-----------------------------------");
        
        for(int i = 0; i < resultado.size(); i++)
            System.out.println(resultado.get(i).toString());
    }
    
    /*
    Usando o objecto remoto procura na BD os produtos requisitados.
    Ao receber a resposta do servidor guarda localmente.
    */
    public void listarProduto() {
        Vector<Produto> resultado;
        Registo registo;
        
        try {
            resultado = dbConn.listarProd();
            
            registo = new Registo(userName, 4, null, resultado, null, true);
            
            // guarda registo
            registo.guardar();
            
            if(resultado.isEmpty()) {
                System.out.println("Sem produtos requisitados");
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
    
    /*
    Usando o objecto remoto requisita o produto.
    Caso ja exista em stock chama a funcao procuraProd que procura as lojas onde
    o prouto foi reportado.
    Caso o utilizador ja tenha requisitado o mesmo produto nao e permitido o
    requisito.
    Caso suceda tudo como suposto recebe um id do requisito.
    Ao receber a resposta do servidor guarda localmente.
    */
    public void requisitarProd(String nomeProduto) {
        ClienteSub novo;
        String id;
        Registo registo;
        
        try {
            id = dbConn.reqProd(userName, nomeProduto);
            
            if(id.equals("0")){
                System.out.println(nomeProduto + " já existe em stock");
                procurarProd(nomeProduto);
                registo = new Registo(userName, 5, null, null, id, false);
            }
            
            else if(id.equals("-1")){
                System.out.println("Já requisitou " + nomeProduto + "antes");
                registo = new Registo(userName, 5, null, null, id, false);
            }
            
            else{
                registo = new Registo(userName, 5, null, null, id, true);
                
                registo.setNomeProd(nomeProduto);
                
                //novo requisito
                requisitos.add(id);
                
                Requisito update = new Requisito(requisitos, userName);
                
                //guarda localmente
                update.guardar();
                
                System.out.println("Produto requisitado com id: " + id);
                
                //cria um clienteSub que comunica com o servidor de modo a receber
                //uma mensagem caso o produto seja encontrado
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
    
    /*
    Usando o objecto remoto insere na BD o produto e loja onde foi encontrado.
    Ao receber a resposta do servidor guarda localmente.
    */
    public void reportarProd(String nomeProduto, String nomeLoja) {
        Registo registo;
        
        try {            
            if(dbConn.repProd(nomeProduto, nomeLoja)) {
                registo = new Registo(userName, 6, null, null, null, true);
                registo.guardar();
                System.out.println("Enviado com sucesso");
                return;
            }
        
            registo = new Registo(userName, 6, null, null, null, false);
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
        
        cliente.ultimoRequisitos();
        
        cliente.ultimaSessao();
        
        try {
            cliente.menu();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        System.out.println("\nAdeus");
        System.exit(0); 
    }
}
