/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jugadorrmi_s2;

import interfazp8.InterfaceFE;
import interfazp8.InterfaceJugadores;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author jSeba
 */
public class FrontEndJ2 extends UnicastRemoteObject implements InterfaceFE{
    public ArrayList<String> ipList;
    public ArrayList<String> claveList;
    public int[] puertoList;
    InterfaceJugadores servidor=null;
    Registry registry;
    public String addressCoordinador="localhost" , passCoordinador="opServidor1";
    public int portCoordinador=3000;
    
    
    public FrontEndJ2(String ip1,String ip2,String ip3) throws RemoteException
    {
        ipList = new ArrayList();
        claveList = new ArrayList();
        puertoList = new int[3];
        //Agrega los puertos
        puertoList[0] = 3000;//puerto de la ip1
        puertoList[1] = 4000;//puerto de la ip2
        puertoList[2] = 5000;//puerto de la ip3
        //Agrega las ip
        ipList.add(ip1);
        ipList.add(ip2);
        ipList.add(ip3);
        //agrega las claves
        claveList.add("opServidor1");
        claveList.add("opServidor2");
        claveList.add("opServidor3");
        obtenerCoordinador();
    }

  public void obtenerCoordinador()
    {
        int i=0;
        for(String ip: ipList)
        {
            System.out.println("ip: "+ip+" puerto: "+puertoList[i]+" clave: "+claveList.get(i));
            if(conectar(ip,puertoList[i],claveList.get(i)) == true)//si logro hacer la conexion
            {//se actualizan los datos del coordinador en el FE
                addressCoordinador = ip;
                passCoordinador = claveList.get(i);
                portCoordinador = puertoList[i];
                break;//rompe el ciclo for
            }else
            {
                i++;//aumenta el indice para el resto de conexiones
            }
        }
    }
    
    public String pedirCarta(String ipJugador, String hora) throws RemoteException
    {
        
        String carta = "";
        int i = 0;
        for(String ip: ipList)
        {
            System.out.println("ip: "+ip+" puerto: "+puertoList[i]+" clave: "+claveList.get(i));
            if(conectar(ip,puertoList[i],claveList.get(i)) == true)//si logro hacer la conexion
            {
                carta = servidor.darCarta(ipJugador, hora);//pide la carta
                servidor.setPrimario();//lo pone como el primario
                break;//rompe el ciclo for
            }else
            {
                i++;//aumenta el indice para el resto de conexiones
            }
        }
        return carta;
    }
    
    public String pedirCarta2(String ipJugador, String hora) throws RemoteException
    {
        
        String carta = "";
        conectaCoordinador();
        if(servidor!=null)
        {    
            carta = servidor.darCarta(ipJugador, hora);//pide la carta
            servidor.setPrimario();//lo pone como el primario
        }    
        return carta;
    }
    
    public boolean conectar(String serverAddress, int serverPort,String serverPass)
    {
        boolean b = false;
            try
            {
                registry = LocateRegistry.getRegistry(serverAddress, serverPort);//obtiene el registro de la maquina con la ip seleccionada
                this.servidor = (InterfaceJugadores)(registry.lookup(serverPass));//localiza lo guardado en el registro con la palabra "operacionservidor"
                if(servidor!=null)
                {
                    System.out.println("Me conecte a :"+ serverAddress);
                    b = true;
                    servidor.agregaFE_cliente(this);//se agrega
                    return b;
                }
            }catch(NotBoundException | RemoteException e)
            {
                System.out.println("No se pudo obtener el registro de server : "+serverPort+" la excepcion es: "+e);
                servidor = null;
                b = false;
////                return b;
            }
        return b;
        
    }
    
    public void conectaCoordinador()
    {
        try
            {
                registry = LocateRegistry.getRegistry(addressCoordinador, portCoordinador);//obtiene el registro de la maquina con la ip seleccionada
                this.servidor = (InterfaceJugadores)(registry.lookup(passCoordinador));//localiza lo guardado en el registro con la palabra "operacionservidor"
                this.servidor.agregaFE_cliente(this);//se agrega el FE
                if(servidor!=null)
                {
                    System.out.println("Me conecte a :"+ addressCoordinador);
                    
                }
            }catch(NotBoundException | RemoteException e)
            {
                System.out.println("No se pudo obtener el registro de server : "+portCoordinador+" la excepcion es: "+e);
                servidor = null;
            }
    }

    @Override
    public void cambioCoordinador(String ipCoordinador, int puerto, String pass) throws RemoteException {
        addressCoordinador = ipCoordinador;
        portCoordinador = puerto;
        passCoordinador = pass;
        
        System.out.println("El nuevo coordinador es:"+addressCoordinador+ "puerto: "+portCoordinador);
    }

}
