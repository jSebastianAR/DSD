/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorrmi_j1;

import BD.Cls_Servidor;
import interfazp8.InterfaceFE;
import interfazp8.InterfaceJugadores;
import interfazp8.InterfaceServidores;
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

public class ServidorRMI_1 extends UnicastRemoteObject implements InterfaceJugadores,InterfaceServidores{
    
    public int PUERTO = 3000;
    public String serverPass = "opServidor1";
    public List<String> cartas = new ArrayList();//Lista que contiene el maso de cartas
    public String cartaActual; //valor de la carta actual que el servidor entrego al jugador
    public String clave;//clave de la cartaActual con la que se identifica su tipo y valor
    public boolean peticion = false;//se vuelve true si un jugador hizo una peticion
    Hashtable<String,String> tipos;//contiene los tipos de cartas(corazon,diamante,trebol y picas)
    public String ipJugadorActual;//ip del jugador que hizo la peticion
    public String valorCartaActual;//valor de la carta que se entrego al jugador(AS,1,2,3,4,5,6,7,8,9,J,K,Q)
    public String hora_Inicio,fecha;//guarda la hora de inicio de cada partida
    public InterfaceServidores servidor2Obj; //referencia que permite la comunicación con el servidor 2 
    public InterfaceServidores servidor3Obj; //referencia que permite la comunicación con el servidor 3   
    public boolean primario=false;//es true si el servidor se designa como primario
    public boolean jugandoPartida = false;//es true si el servidor esta jugando una partida y cae entonces los demas deben saber que había una partida en juego
    public final int ID=1; //Servidor con prioridad 1, es decir es el de mayor prioridad
    public int id_coordinador = 1;
    public String ipCoordinador; //guarda la ip del servidor actual
    //Para algoritmo de lamport
    public int cLamport = 0;
    public InterfaceFE[] FE;///REFERENCIA AL FRONT END DEL JUGADOR
    public int numJugador = 0;//para especificar el jugador en el arreglo de FE
    
    public ServidorRMI_1() throws RemoteException
    {
        FE = new InterfaceFE[2];
        FE[0] = null;
        FE[1] = null;
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
            //System.setProperty("java.rmi.server.hostname","localhost");
            registry.bind("opServidor1",(InterfaceJugadores)this);//se guarda con la clave "operacionservidor" en el registro la instancia de la clase OperacionServidor pero con un cast a la interface
            registry.bind("server1", (InterfaceServidores)this);//registro para que los otros servidores se conecten
        } catch (Exception e) {
            System.out.println("No se pudo hacer el inicio del Servidor 1");
        }
        primario=true;
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
    
    public List<String> llenarLista3(List<String> lista)//llena la lista con los nombres en clave de las imagenes
    {
        
            for(int j=1; j<3; j++)
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
        System.out.println("darCarta S1");
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

    //Agrega la referencia al servidor 2
    @Override
    public void agregaServer2(InterfaceServidores server2) throws RemoteException {
        servidor2Obj = server2;
    }
    
    //Agrega la referencia al servidor 2
    @Override
    public void agregaServer3(InterfaceServidores server3) throws RemoteException {
        servidor3Obj = server3;
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
        System.out.println("Eliminare la carta: "+clave);
        cartas.remove(clave);//elimina el elemento de la lista
        imprimeCartas(cartas);
        
    }

    @Override
    public void lamport(int contadorRemoto) throws RemoteException {
        if(cLamport<=contadorRemoto)
        {
            cLamport=contadorRemoto+1;
            System.out.println("Hice actualización del contador en S1");
        }else
        {
            System.out.println("el contador es correcto en S1");
        }
    }

    @Override
    public void setPrimario() throws RemoteException {
        primario = true;
    }

    @Override
    public void jugandoPartida(String horaInicio, String ifecha) throws RemoteException {
        jugandoPartida = true; //se le especifica al servidor que hay una partida jugandose
        hora_Inicio = horaInicio;
        fecha = ifecha;
    }
    
    @Override
    public boolean comparaID(int id) throws RemoteException {
        if(ID>id)//si es mayor tiene menos prioridad
        {
            return true;
        }else
        {
            return false;
        }
    }

    @Override
    public void soyCoordinador(String ipCoordinador, int id) throws RemoteException{
       this.ipCoordinador = ipCoordinador;
       id_coordinador  = id;
       primario = false;//se pone en false si era primario
    }
    
    @Override
    public boolean online() throws RemoteException {
        System.out.println("Sigo en linea");
        return true;
    }
    
    @Override
    public void agregaServer1(InterfaceServidores server1) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void agregaFE_cliente(InterfaceFE FE) throws RemoteException {//esta funcion es llamada por el Front end para agregarse al server
        System.out.println("numJugador:"+numJugador);
        if(numJugador==0)
        {
            this.FE[numJugador] = FE;
            numJugador++;
            System.out.println("Agregue FE[0]");
        }else
        {
            this.FE[numJugador] = FE;
            System.out.println("Agregue FE[1]");
            if(numJugador==1)
            numJugador=0;
        }
        
    }

    @Override
    public void agregaFE_Server(InterfaceFE[] FE) throws RemoteException {//esta funcion es llamada por el servidor coordinador para agregar los FE al resto de servers
        this.FE = FE;
        System.out.println("Tengo referencia a FE");
    }

}
