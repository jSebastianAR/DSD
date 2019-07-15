/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazp3;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author jSeba
 */
public interface InterfaceServidor extends Remote{
     public String darCarta(String ipJugador,String hora) throws RemoteException;
     
     public long actualizaHora(String HoraCliente,int ping) throws RemoteException;
     
     public String[] darHora() throws RemoteException;
}
