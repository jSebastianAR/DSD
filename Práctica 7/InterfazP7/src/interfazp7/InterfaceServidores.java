/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazp7;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jSeba
 */
public interface InterfaceServidores extends Remote{
    
    public void agregaServer1(InterfaceServidores server1) throws RemoteException;
    
    public void agregaServer2(InterfaceServidores server2) throws RemoteException;
    
    public void agregaServer3(InterfaceServidores server3) throws RemoteException;
    
    public void queryRemoto(String consulta) throws RemoteException;
    
    public void eliminaCartaRemota(String clave) throws RemoteException;
    
    public void lamport(int contadorRemoto) throws RemoteException;
    
    public void jugandoPartida(String horaInicio, String sfecha) throws RemoteException;
}
