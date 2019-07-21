/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p5servidorj1;

import BD.Cls_Servidor;
import static Ping.Ping.hacerPing;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import p5interfaces.ConexionServer1;
import p5interfaces.ConexionServer2;
import p5interfaces.InterfazParaJugadores;
import p5interfaces.InterfazServidorUTC;
import p5interfaces.InterfazServidoresSincronización;

/**
 *
 * @author jSeba
 */
public class P5ServidorJ1 extends UnicastRemoteObject implements ConexionServer1,InterfazParaJugadores,InterfazServidoresSincronización{

    private static final long serialVersionUID = 9061868373895430621L; //serializacion 
    private static final int PUERTO = 3233;
    public List<String> cartas = new ArrayList();
    public String cartaActual;
    public String clave;
    public boolean peticion = false;
    Hashtable<String,String> tipos;
    public String ipJugadorActual;
    public String valorCartaActual;
    public String serverHora;
    public ConexionServer2 servidor2Obj=null;//referencia al servidor 2 para hacer consultas remotas en su BD
    //variables para servidor UTC
    public InterfazServidorUTC serverUTC;
    public boolean hacerSincronizacion=false;
    public String utcAddress = "localhost";//ip del servidor 1
    public int utcPort = 3232;//puerto del server 1
    
    public P5ServidorJ1()throws RemoteException
    {
        this.cartas = llenarLista2(this.cartas);
        this.tipos = new Hashtable<String,String>();
        this.tipos = agregarTipos(this.tipos);
        try
        {
            Registry registry = LocateRegistry.getRegistry(utcAddress,utcPort);
            serverUTC = (InterfazServidorUTC)registry.lookup("serverUTC");
            serverUTC.agregaServer(this);//agrega el servidor actual a las referencias del server UTC
        }catch(Exception e)
        {
            System.out.println("Error al obtener referencia del servidor UTC en server 1: "+e);
        }
    }
    
    public void iniciarServidor()
    {
        try {
            String dirIP = (InetAddress.getLocalHost().toString());
            System.out.println("Escuchando en: "+dirIP+":"+PUERTO);
            Registry registry = LocateRegistry.createRegistry(PUERTO);//Se crea el registro de los objetos que pueden ser instanciados en el PUERTO 
            registry.bind("registroJ1",(InterfazParaJugadores)this);//se guarda con la clave "operacionservidor" en el registro la instancia de la clase OperacionServidor pero con un cast a la interface
            registry.bind("server1",(ConexionServer1)this);//REGISTRO PARA EL SERVER 2
        } catch (Exception e) {
            System.out.println("No se pudo hacer el inicio del Servidor 1");
        }
    }
    
    public List<String> llenarLista(List<String> lista)//llena la lista con los nombres en clave de las imagenes 
    {
        for(int i=1; i<=4; i++)
        {
            for(int j=1; j<14; j++)
            {
               //System.out.println("elemento: "+Integer.toString(i)+Integer.toString(j)); 
               lista.add(Integer.toString(i)+Integer.toString(j));
            }
        }
        return lista;
    }
    
    
    public List<String> llenarLista2(List<String> lista)//llena la lista con los nombres en clave de las imagenes
    {
        
            for(int j=1; j<6; j++)
            {
               //System.out.println("elemento: "+Integer.toString(i)+Integer.toString(j)); 
               lista.add("1"+Integer.toString(j));
            }
        
        return lista;
    }
    
        public Hashtable<String,String> agregarTipos(Hashtable<String,String> tipo)//tabla que tiene todos los tipos de cartas
    {
        tipo.put("1", "Corazones");
        tipo.put("2", "Picas");
        tipo.put("3", "Treboles");
        tipo.put("4", "Diamantes");
        
        return tipo;
    }
    
    public String buscarTipo(String claveCarta)
    {
        String tipo = ""+claveCarta.charAt(0);//se concatena para que sea un string y no se tome como char, el primer caracter de la cadena siempre indica el tipo de carta que es en la hashtable
        String tipoCarta = "";
        for(int i=1; i<claveCarta.length(); i++)//se guardan el resto de caracteres de la cadena para obtener el número de carta del tipo ya obtenido
        {
            tipoCarta += claveCarta.charAt(i);
        }
        
        switch(tipoCarta)//en casos especiales donde las cartas no representan un número
        {
            case "1":
                tipoCarta = "As";
            break;
            
            case "11":
                tipoCarta = "J";
            break;    
            
            case "12":
                tipoCarta = "Q";
            break;
            
            case "13":
                tipoCarta = "K";
            break;
        }
        this.valorCartaActual = tipoCarta;
        //System.out.println("Tipo: "+tipo);
        tipoCarta += " de "+(String)tipos.get(tipo);//concatena el valor ya obtenido en el for y busca de que tipo es esa carta(picas,corazones,trebol,diamante)
        return tipoCarta;
    }
    
    public String getFecha()
    {
        Calendar calendario = Calendar.getInstance();
        String date = calendario.getTime().toString();
        String parts[] = date.split(" ");
        String fecha = parts[2]+"/"+parts[1]+"/"+parts[5];//pone el mes/dia/año
     //   System.out.println("RESULTADO getfecha: "+fecha);
        return fecha;
    }
    
        public String getHora()
    {
        Calendar calendario = Calendar.getInstance();
        String date = calendario.getTime().toString();
        String parts[] = date.split(" ");
        String hora = parts[3];//pone la hora actual
    //    System.out.println("RESULTADO getHora: "+hora);
        return hora;
    }
        
    public void imprimeCartas(List<String> cartas)
    {
        for(int i= 0; i<cartas.size(); i++)
        {
            System.out.println(cartas.get(i));
        }
    }

    
    //FUNCIONES REMOTAS DEL SERVER 2 AL SERVER 1
    @Override
    public void agregaServer2(ConexionServer2 server2) throws RemoteException {
        servidor2Obj = server2;
    }

    @Override
    public void queryRemoto(String consulta) throws RemoteException {
        Cls_Servidor BD = new Cls_Servidor();//crea una variable temporal para conectarse a la BD local del servidor
        try {
            BD.consultaRemota(consulta);//hace la consulta
        } catch (SQLException ex) {
            System.out.println("No se pudo recibir consulta remota en server 1: "+ex);
        }
    }

    @Override
    public void eliminaCartaRemota(String clave) throws RemoteException {
        imprimeCartas(cartas);
        System.out.println("Eliminare la carta: "+clave);
        cartas.remove(clave);//elimina el elemento de la lista
        imprimeCartas(cartas);
    }
    //FUNCIONES PARA HACER FUNCIONES REMOTAMENTE DEL SERVER 2 AL SERVER 1
    
    //FUNCIONES REMOTAS PARA JUGADOR 1
    @Override
    public String darCarta(String ipJugador, String hora) throws RemoteException {
        this.ipJugadorActual = ipJugador;
        Random random = new Random(); //intancia para valor random
        if(!this.cartas.isEmpty())//si aun hay cartas que repartir
        {
            int indice = random.nextInt(this.cartas.size());//obtiene una posicion random de la lista
            this.clave = this.cartas.get(indice);//toma el elemento en ese indice
            this.cartaActual = buscarTipo(this.clave);//busca que tipo de carta es
            this.peticion = true;//indica que ya hay una peticion
            this.cartas.remove(indice);//elimina el elemento para no volver a ser usado en esa partida
        }else
        {
            this.cartaActual = "n";
            //this.cartas = llenarLista(this.cartas);
        }
        return this.cartaActual;
    }
    //FUNCIONES REMOTAS PARA JUGADOR 1

    
    //FUNCION PARA QUE EL SERVER UTC MANDE LA ACTUALIZACION AL SERVER 1
    @Override
    public void sincronizarServidor() throws RemoteException {
        this.hacerSincronizacion=true;
    }
    
    //FUNCION PARA QUE EL SERVER UTC MANDE LA ACTUALIZACION AL SERVER 1
    
}
