/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p5interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jSeba
 */
public interface InterfazServidorUTC extends Remote{
    public long actualizaHora(String horaCliente,int latencia) throws RemoteException;
    public void agregaServer(InterfazServidoresSincronización server) throws RemoteException;
    public String[] darHora() throws RemoteException;
}
