package covidv1;


import java.util.Scanner;
import java.util.Vector;

public class DispClient {
    

    public static void main(String args[]) {
	String regHost = "localhost";
	String regPort = "1099";  // porto do binder
        String username;
        
        
//	if (args.length !=2) { // requer 3 argumentos
//	    System.out.println
//		("Usage: java covidv1.DispClient registryHost registryPort");
//	    System.exit(1);
//	}
//	regHost= args[0];
//	regPort= args[1];

        regHost = "localhost";
        regPort = "2000";
        

	try {
	    // objeto que fica associado ao proxy para objeto remoto
	    Disp obj =
		(Disp) java.rmi.Naming.lookup("rmi://" + regHost + ":" + 
						  regPort + "/disp");
	    

	    // invocacao de metodos remotos

        boolean flag = true;
        Scanner stdi = new Scanner(System.in);
        
        int command;
        String str, loja;
        Vector<String> strs;
        
        System.out.println("Username:");
        username = stdi.next();
        
        while(flag) {
            
            System.out.println("\n1 - Listar stocks\n"
                    + "2 - Listar stock de uma loja\n"
                    + "3 - Listar produtos criticos\n"
                    + "4 - Requisitar produto\n"
                    + "5 - Reportar disponibilidade de produto\n"
                    + "6 - Sair");
            
            command = stdi.nextInt();
            
            System.out.println("\n");
            
            switch(command) {
                case 1:
                    strs = obj.listarSotck();
                    
                    if(strs.size() == 0)
                        System.out.println("Vazio");
                    
                    for(int i = 0; i < strs.size(); i++)
                        System.out.println(strs.get(i));
                    break;
                    
                case 2:
                    str = stdi.next();
                    
                    strs = obj.listarSotck(str);
                    
                    if(strs.size() == 0)
                        System.out.println("Vazio");
                    
                    for(int i = 0; i < strs.size(); i++)
                        System.out.println(strs.get(i));
                    break;
                    
                case 3:
                    strs = obj.listarProdCrit();
                    
                    if(strs.size() == 0)
                        System.out.println("Vazio");
                    
                    for(int i = 0; i < strs.size(); i++)
                        System.out.println(strs.get(i));
                    break;
                    
                case 4:
                    str = stdi.next();
                    
                    str = obj.reqProd(username, str);
                    
                    System.out.println(str);
                    break; 
                    
                case 5:
                    str = stdi.next();
                    loja = stdi.next();
                    
                    obj.reportarProd(loja, str);
                    break;
                    
                case 6:
                    flag = false;
                    obj.disconnect();
                    break;
                    
                default:
                    break;
                   
            }
            
        }

	} 
	catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
}
