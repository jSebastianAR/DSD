/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j1rmi;

import java.net.UnknownHostException;
import java.rmi.RemoteException;

/**
 *
 * @author jSeba
 */
public class J1RMI {

    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws RemoteException, UnknownHostException {
        Jugador j1 = new Jugador();
    }
    
}
