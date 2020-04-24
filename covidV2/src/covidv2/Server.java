package covidv2;

import rmi.covid.*;


/**
 * Classe que ativa o serviço via RMI.
 * 
 * @author jsaias
 */
public class Server {

    public static void main(String args[]) {

	int regPort, subPort;
        String host, db, user, pw;

	if (args.length != 6) { // obrigar 'a presenca de um argumento
	    System.out.println("Args: Registry port, Subscribe port, Database host,"
                    + "Database name, Database user, Database password");
	    System.exit(1);
	}
	

	try {
	    // ATENCAO: este porto e' relativo ao binder e nao ao servidor RMI
            
            regPort = Integer.parseInt(args[0]);
            subPort = Integer.parseInt(args[1]);
            host = args[2];
            db = args[3];
            user = args[4];
            pw = args[5];


	    // criar um Objeto Remoto
            PostgresConn objRemoto = new PostgresConnImpl(host, db, user, pw, subPort);

            java.rmi.registry.LocateRegistry.createRegistry(regPort);            

            java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(regPort);

            registry.rebind("PostgresConnector", objRemoto);  // NOME DO SERVICO

            System.out.println("+ SERVER PRONTO...");
           
	} 
	catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
    
}
