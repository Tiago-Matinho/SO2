package covidv1;


/**
 * Classe que ativa o serviço via RMI.
 * 
 * @author jsaias
 */
public class DispServer {

    public static void main(String args[]) {
	// assume-se que o Servico de Nomes e' local
	// pode ser um processo autonomo ou 
	// parte desta aplicacao servidor
	// aqui usamos o externo

	int regPort= 1099; // default RMIRegistry port
	String host, db, user, pw;

//	if (args.length !=5) { // obrigar 'a presenca de um argumento
//	    System.out.println("Usage: java so2.rmi.PalavrasServer registryPort DB_host db user passwd");
//	    System.exit(1);
//	}
	

	try {
	    // ATENCAO: este porto e' relativo ao binder e nao ao servidor RMI
//		regPort=Integer.parseInt(args[0]);
//		host = args[1];
//		db = args[2];
//		user = args[3];
//		pw = args[4];

            regPort = 2000;


	    // criar um Objeto Remoto
	    Disp obj= new DispImpl("localhost", "disp", "disp_admin", "covid");


	    // registar este objeto no servico de nomes
            
            /**
             * Se quiser substituir o processo rmiregisty...
             * pode ativar o servidor de nomes neste mesmo processo (antes do bind)
             */
            java.rmi.registry.LocateRegistry.createRegistry(regPort);            
            
            
	    // usar o Registry local (em execução na mesma máquina)
            // e no porto regPort
	    java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(regPort);

            // mas podiamos tb criar um novo, 
	    // integrado nesta mesma aplicacao servidor!

	    // ... e bind
	    registry.rebind("disp", obj);  // NOME DO SERVICO

	    /*
	      OUTRO MODO, indicando o servidor onde está o serviço de nomes
	    java.rmi.Naming.rebind("rmi://"+regHost+":" +
				   regPort + "/disp", stub);
	    
	    */
	    System.out.println("Bound RMI object in registry");

            System.out.println("servidor pronto");
	} 
	catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
    
}
