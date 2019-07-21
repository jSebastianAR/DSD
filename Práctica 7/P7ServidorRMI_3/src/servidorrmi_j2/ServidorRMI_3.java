/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorrmi_j2;

import BD.Cls_Servidor;
import interfazp7.InterfaceJugadores;
import interfazp7.InterfaceServidores;
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


/**
 *
 * @author jSeba
 */
//public class ServidorRMI_3 extends UnicastRemoteObject implements InterfaceJugadores,ConexionServer3{
  public class ServidorRMI_3 extends UnicastRemoteObject implements InterfaceJugadores,InterfaceServidores{  
    //private static final long serialVersionUID = 9061868373895430621L; //serializacion 
    private static final int PUERTO = 5000;
    public List<String> consultas = new ArrayList();
    public List<String> cartas = new ArrayList();
    public String cartaActual;
    public String clave;
    public boolean peticion = false;
    Hashtable<String,String> tipos;
    public String ipJugadorActual;
    public String valorCartaActual;
    public boolean primario=false;
    public boolean jugandoPartida = false;
    public String hora_Inicio,fecha;//guarda la hora de inicio de cada partida
    //Para algoritmo de lamport
    public int cLamport = 0;
    /*public ConexionServer1 servidor1Obj;
    public ConexionServer2 servidor2Obj;*/
    public InterfaceServidores servidor1Obj;
    public InterfaceServidores servidor2Obj;
    
    public ServidorRMI_3() throws RemoteException
    {
        this.cartas = llenarLista2(this.cartas);
        this.tipos = new Hashtable<String,String>();
        this.tipos = agregarTipos(this.tipos);
    }

    public void iniciarServidor()
    {
        try {
            String dirIP = (InetAddress.getLocalHost().toString());
            System.out.println("Escuchando en: "+dirIP+":"+PUERTO);
            Registry registry = LocateRegistry.createRegistry(PUERTO);//Se crea el registro de los objetos que pueden ser instanciados en el PUERTO 
            registry.bind("opServidor3",(InterfaceJugadores)this);//se guarda con la clave "operacionservidor" en el registro la instancia de la clase OperacionServidor pero con un cast a la interface
            registry.bind("server3", (InterfaceServidores)this);//hace un registro para la conexion entre servidores
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
    
    @Override
    public String darCarta(String ipJugador, String hora) throws RemoteException {
        System.out.println("darCarta S3");
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


    //Metodo exclusivo para harcer consultas en la BD de otro servidor
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
        cartas.remove(clave);//elimina el elemento de la lista
        imprimeCartas(cartas);
    }

    @Override
    public void lamport(int contadorRemoto) throws RemoteException {
        if(cLamport<=contadorRemoto)
        {
            cLamport=contadorRemoto+1;
            System.out.println("Hice actualización del contador en S2");
        }else
        {
            System.out.println("el contador es correcto en S2");
        }
    }

    @Override
    public void setPrimario() throws RemoteException {
        primario = true;
    }

    @Override
    public void agregaServer1(InterfaceServidores server1) throws RemoteException {
        servidor1Obj = server1;
    }

    @Override
    public void agregaServer2(InterfaceServidores server2) throws RemoteException {
        servidor2Obj = server2;
    }

    @Override
    public void jugandoPartida(String horaInicio, String sfecha) throws RemoteException {
        jugandoPartida = true; //se le especifica al servidor que hay una partida jugandose
        hora_Inicio = horaInicio;
        fecha = sfecha;
    }
    
    @Override
    public void agregaServer3(InterfaceServidores server3) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
