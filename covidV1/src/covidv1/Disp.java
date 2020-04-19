package covidv1;


import java.util.Vector;
        
/**
 *
 * @author rute
 */
public interface Disp extends java.rmi.Remote {
   
   void login(String username) throws java.rmi.RemoteException;
   Vector<String> listarSotck() throws java.rmi.RemoteException;
   Vector<String> listarSotck(String nomeLoja) throws java.rmi.RemoteException;
   Vector<String> listarProdCrit() throws java.rmi.RemoteException;
   String reqProd(String username, String nomeProd) throws java.rmi.RemoteException;
   void reportarProd(String nomeLoja, String nomeProd) throws java.rmi.RemoteException;
   void disconnect() throws java.rmi.RemoteException;
}
