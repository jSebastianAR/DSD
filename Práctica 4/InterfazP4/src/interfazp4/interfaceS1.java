/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazp4;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jSeba
 */
public interface interfaceS1 extends Remote{
    
    public String darCarta(String ipJugador,String hora) throws RemoteException;
    
    public void recibirConsultaRemotaS1(String consulta) throws RemoteException;
}
