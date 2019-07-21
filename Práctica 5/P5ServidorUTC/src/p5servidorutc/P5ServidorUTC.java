/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p5servidorutc;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import p5interfaces.InterfazServidorUTC;
import p5interfaces.InterfazServidoresSincronización;

/**
 *
 * @author jSeba
 */
public class P5ServidorUTC extends UnicastRemoteObject implements InterfazServidorUTC{

   private static final long serialVersionUID = 9061868373895430621L; //serializacion 
    public int port = 3232;
    public String ServerAddress;
    public InterfazServidoresSincronización server1=null,server2=null;
    int nserver=1;
    public String hora;
    public String minutos;
    public String segundos;

    public P5ServidorUTC()throws RemoteException
    {
    }
    
    public void iniciarServidor()
    {
        try{
            ServerAddress = InetAddress.getLocalHost().toString();
            System.out.println("Escuchando en: "+ServerAddress+" En puerto: "+port);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("serverUTC",(InterfazServidorUTC)this);
        }catch(Exception e)
        {
            System.out.println("Error al hacer el registro: "+e);
        }
    }
    
    @Override
    public void agregaServer(InterfazServidoresSincronización server) throws RemoteException {
        if(nserver==1)//si es el primer server a agregar
        {
            server1=server;
            nserver=2;
        }else
        {
            if(nserver==2)
            server2=server;
        }
    }


    public long toMilis(String hora)
    {
        //cada equivalencia de la unidad hora,minuto y segundo a milisegundos
        long horamili = 3600000;
        long minmili = 60000;
        long segmili = 1000;
        
        String parts[] = hora.split(":");//divide la hora
      //System.out.println("Hora separada:"+parts[0]+":"+parts[1]+":"+parts[2]);
        long lhoras = horamili*Integer.parseUnsignedInt(parts[0]);//convierte las horas a milisegundos
        long lminutos = minmili*Integer.parseUnsignedInt(parts[1]);//convierte los minutos a milisegundos
        long lsegundos = segmili*Integer.parseUnsignedInt(parts[2]);//convierte los segundos a milisegundos
        long total = lhoras+lminutos+lsegundos;//hace la suma total de las horas
      //  System.out.println("De: "+hora+" a: "+total);
        return total;
        
        // 10:30:25 => 36 000 000 + 1 800 000 + 25 000 = 37 825 000
    }
    
    
    public void guardaHora(String horaS)
    {
        String parts[] = horaS.split(":");
        hora = parts[0];
        minutos = parts[1];
        segundos = parts[2];
    }

    @Override
    public long actualizaHora(String horaCliente, int latencia) throws RemoteException {
        String horaUTC = UTC.obtenerUTC();
        System.out.println("---------------ActualizaHora--------------");
        System.out.println("Hora UTC servidor: "+horaUTC);
        guardaHora(horaUTC);//guarda la hora del servidor donde se manda la actualizacion
        System.out.println("Hora del cliente: "+horaCliente);
        long lhoraUTC = toMilis(horaUTC);//convierte en milisegundos
        long lhoraCliente = toMilis(horaCliente);//convierte en milisegundos
        //algoritmo de cristian
        long ajuste = (lhoraUTC-lhoraCliente)-(latencia/2);
        System.out.println("---------------ActualizaHora--------------");
        System.out.println("\n\n");
        return ajuste;
    }

    @Override
    public String[] darHora() throws RemoteException {
        String[] Hora = new String[3];
        Hora[0] = hora;
        Hora[1] = minutos;
        Hora[2] = segundos;
        return Hora;
    }

    
    
}
