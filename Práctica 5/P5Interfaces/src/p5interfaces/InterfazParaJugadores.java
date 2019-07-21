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
public interface InterfazParaJugadores extends Remote{
    public String darCarta(String ipJugador,String hora) throws RemoteException;
}
