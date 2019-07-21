/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazp8;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jSeba
 */
public interface InterfaceFE extends Remote{
    
    public void cambioCoordinador(String ipCoordinador, int puerto, String password) throws RemoteException;
}
