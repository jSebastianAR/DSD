/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorrmi_j1;

import java.rmi.RemoteException;

/**
 *
 * @author jSeba
 */
public class ClaseMain {
    public static void main(String args[]) throws RemoteException
    {
        ServidorFormS1 server = new ServidorFormS1();
    }
}
