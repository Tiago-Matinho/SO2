package rmi;

import disponibilidade.*;
import java.util.Vector;

/**
 *
 * @author João Marques, Tiago Martinho
 */
public interface PostgresConn extends java.rmi.Remote {
    public boolean connect() throws java.rmi.RemoteException;
    
    public boolean registar(String userName) throws java.rmi.RemoteException;
    public boolean login(String userName) throws java.rmi.RemoteException;
    
    public Vector<Stock> listarStock() throws java.rmi.RemoteException;
    public Vector<Stock> listarStock(String nomeLoja) throws java.rmi.RemoteException;
    
    public Vector<Produto> listarProd() throws java.rmi.RemoteException;
    public Vector<Stock> listarProd(String nomeProd) throws java.rmi.RemoteException;
    
    public String reqProd(String userName, String nomeProd) throws java.rmi.RemoteException;
    
    public boolean repProd(String nomeProd, String nomeLoja) throws java.rmi.RemoteException;
    
    public boolean disconnect() throws java.rmi.RemoteException;
}
