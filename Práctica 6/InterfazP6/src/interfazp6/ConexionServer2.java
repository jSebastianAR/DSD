/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazp6;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jSeba
 */
public interface ConexionServer2 extends Remote{
    
    public void queryRemoto(String consulta) throws RemoteException;
    
    public void eliminaCartaRemota(String clave) throws RemoteException;
    
    public void lamport(int contadorRemoto) throws RemoteException;
}
