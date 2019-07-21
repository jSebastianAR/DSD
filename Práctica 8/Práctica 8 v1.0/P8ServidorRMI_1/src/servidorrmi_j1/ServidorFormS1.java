/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorrmi_j1;

import BD.Cls_Servidor;
import java.util.Calendar;
import Reloj.Modifica;
import Reloj.Relojes;
import interfazp8.InterfaceJugadores;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author jSeba
 */
public class ServidorFormS1 extends javax.swing.JFrame implements Runnable{
    
    ServidorRMI_1 server;
    public Thread h1,h2,h3,h4;
    public DisplayImage IMAGEN;
    public Cls_Servidor BD;
    public Modifica m;
    public Relojes reloj; 
    public int partidaNueva = 0; //indica si la partida acaba de empezar
    public Calendar calendario;
    public String fecha,hora_inicio,hora_fin;//fecha completa, hora inicio de la partida y hora fin de la partida
    public int timer1=1000;//tiempo de actualizacion del reloj del servidor
    public String serverHora;
    int activaHilos=0;//activara los hilos hasta que detecte que el servidor 2 ya esta conectado y se pueden hacer procedimientos remotos
    public boolean ejecuteUpdate=false;
    //datos para server 2
    public String server1Address = "localhost";//ip del servidor 1
    public int server1Port = 3233;//puerto del server 1
    public String QueryActual;
    public int reinicio=0;
    public InterfaceJugadores FE;
    
    
    public ServidorFormS1() throws RemoteException {
        initComponents();
        server = new ServidorRMI_1();
        server.iniciarServidor();
        IMAGEN = new DisplayImage();
        BD = new Cls_Servidor();
        reloj = new Relojes();
        h1 = new Thread(this);
        h2 = new Thread(this);
        h3 = new Thread(this);
        h4 = new Thread(this);
        h1.start();//hilo para el reloj
        h2.start();//hilo para atender peticiones y registrar tanto en su propia BD como en las remotas
        h3.start();//hilo para buscar a los otros servers
        h4.start();//hilo para comprobar que el coordinador sigue online
        this.setLocationRelativeTo(null);
        this.setTitle("Servidor 1");
        this.setVisible(true);
        try {
            ImageIcon icon = IMAGEN.DisplayImage("0");//pone el inverso de la carta
            imagenCarta.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(ServidorFormS1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Modificar = new javax.swing.JButton();
        imagenCarta = new javax.swing.JLabel();
        Reiniciar = new javax.swing.JButton();
        lblLamport = new javax.swing.JLabel();
        ReiniciarC = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Servidor 1");

        lblReloj.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        lblReloj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Modificar.setText("Modificar");
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        Reiniciar.setText("Reiniciar");
        Reiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReiniciarActionPerformed(evt);
            }
        });

        lblLamport.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        lblLamport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLamport.setText("lblLamport");

        ReiniciarC.setText("Reiniciar contador");
        ReiniciarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReiniciarCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLamport, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(ReiniciarC)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(Modificar)
                                .addGap(50, 50, 50)))))
                .addComponent(imagenCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Reiniciar)
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLamport, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ReiniciarC))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Modificar)))
                        .addGap(0, 21, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(imagenCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)))
                .addComponent(Reiniciar)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        h1.suspend();
        Modifica m = new Modifica();
        m.setVisible(true);
        m.setLocationRelativeTo(null);
        m.setTitle("Modificar Reloj 1");
        m.invocador=1;
        m.relojOriginal=this;
    }//GEN-LAST:event_ModificarActionPerformed

    private void ReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReiniciarActionPerformed
        server.cartas = server.llenarLista(server.cartas);//llena la lista de donde se sacan las claves de las cartas
        partidaNueva = 0;
        reinicio=1;
        ejecuteUpdate=false;
        try {
            ImageIcon icon = IMAGEN.DisplayImage("0");//pone el inverso de la carta
            imagenCarta.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(ServidorFormS1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ReiniciarActionPerformed

    private void ReiniciarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReiniciarCActionPerformed
        server.cLamport=0;
    }//GEN-LAST:event_ReiniciarCActionPerformed
    
    //=====================================================================================================================
    //=====================================================================================================================
    //=====================================================================================================================
    
    public void QueryServer2()
    {
        try
        {
            server.servidor2Obj.queryRemoto(QueryActual);//Hace el query remoto para el server 1;
            server.servidor2Obj.lamport(server.cLamport);//manda su contador de lamport para actualizacion
        }catch(RemoteException e)
        {
            System.out.println("Excepcion haciendo consultas a server 2 :"+e);
        }
    }
    
    public void QueryServer3()
    {
        try
        {
            server.servidor3Obj.queryRemoto(QueryActual);//Hace el query remoto para el server 1;
            server.servidor3Obj.lamport(server.cLamport);//manda su contador de lamport para actualizacion
        }catch(RemoteException e)
        {
            System.out.println("Excepcion haciendo consultas a server 3 :"+e);
        }
    }
    
    public void eliminaCartaS2(String clave)
    {
        try
        {
            server.servidor2Obj.eliminaCartaRemota(clave);
            server.servidor2Obj.lamport(server.cLamport);//manda su contador de lamport para actualizacion
        }catch(RemoteException e)
        {
            System.out.println("Excepcion eliminando cartas en server 1 :"+e);
        }
    }
    
    public void eliminaCartaS3(String clave)
    {
        try
        {
            server.servidor3Obj.eliminaCartaRemota(clave);
            server.servidor3Obj.lamport(server.cLamport);//manda su contador de lamport para actualizacion
        }catch(RemoteException e)
        {
            System.out.println("Excepcion eliminando cartas en server 3 :"+e);
        }
    }
    
    public void jugandoPartidaS2(String horai, String fecha)
    {
        try
        {
            server.servidor2Obj.jugandoPartida(horai, fecha);//avisa que esta jugando una partida
            server.servidor2Obj.lamport(server.cLamport);//manda su contador de lamport para actualizacion
        }catch(RemoteException e)
        {
            System.out.println("Excepcion eliminando cartas en server 3 :"+e);
        }
    }
    
    public void jugandoPartidaS3(String horai, String fecha)
    {
        try
        {
            server.servidor3Obj.jugandoPartida(horai, fecha);
            server.servidor3Obj.lamport(server.cLamport);//manda su contador de lamport para actualizacion
        }catch(RemoteException e)
        {
            System.out.println("Excepcion eliminando cartas en server 3 :"+e);
        }
    }
    
    public void agregaFES2()
    {
        try
        {
            server.servidor2Obj.agregaFE_Server(server.FE);//envia su arreglo de FE al otro servidor para que tambien tenga referencia en caso de que el coordinador muera
            server.servidor2Obj.lamport(server.cLamport);//manda su contador de lamport para actualizacion
        }catch(RemoteException e)
        {
            System.out.println("Excepcion agregando FE en S2 :"+e);
        }
    }
    
    public void agregaFES3()
    {
        try
        {
            server.servidor3Obj.agregaFE_Server(server.FE);//envia su arreglo de FE al otro servidor para que tambien tenga referencia en caso de que el coordinador muera
            server.servidor3Obj.lamport(server.cLamport);//manda su contador de lamport para actualizacion
        }catch(RemoteException e)
        {
            System.out.println("Excepcion agregando FE en S3 :"+e);
        }
    }
    
    public void actualizaFE(int i)//actualizara los datos del cooordinador en cada FE
    {
        try
        {
            server.FE[i].cambioCoordinador("localhost", server.PUERTO, server.serverPass);//avisa a los jugadores la direccion del nuevo coordinador
        }catch(RemoteException e)
        {
            System.out.println("Excepcion actualizando FE:"+i);
        }
    }
    
    //=====================================================================================================================
    //=====================================================================================================================
    //=====================================================================================================================
    
    @Override
    public void run() {
        Thread ct = new Thread();
        ct = Thread.currentThread();//obtiene el hilo ejecutado en ese momento
            
        while(ct==h1)//hilo para la actualizacion del reloj
        {
            //System.out.println("reloj.aux = "+reloj.aux);
            if (reloj.aux != 1) //si aun no entra por primera vez
            {
                reloj.generaHora();
                //System.out.println("Acabo de ejecutar r1.calcula()");
                //System.out.println("hilo: "+h1.getName()+"Entre a calcular, auxiliar:"+r1.aux);
            }
            //.out.println("reloj.aux = "+reloj.aux+" : "+reloj.hora +":"+ reloj.minutos +":"+ reloj.segundos);
            lblReloj.setText(reloj.hora +":"+ reloj.minutos +":"+ reloj.segundos);//pone la hora en el label
            serverHora = lblReloj.getText();
            reloj.actualiza();
            
            lblLamport.setText("");
            lblLamport.setText("Contador: "+Integer.toString(server.cLamport));//pone el valor del contador en el label
            server.cLamport+=1;//cada segundo aumenta el contador de lamport
            System.out.println("Server primario = "+ server.primario);
            try {
                Thread.sleep(timer1);
            } catch (InterruptedException e) {
                System.out.println("Error al generar hora"+e);
            }
        }
        
        
        while(ct==h2)//hilo para las peticiones del cliente
        {
            //System.out.println("Server.peticion = "+ server.peticion);
            if(server.FE[0]==null)
                System.out.println("referencia a FE J1 es null");
            
            if(server.FE[1]==null)
                System.out.println("referencia a FE J2 es null");
            
            if(server.FE[0]!=null && server.FE[1]!=null)
            {
                agregaFES2();
                agregaFES3();//agrega los Front End a los demas servidores
            }
            
            if(server.primario==true)
            {    
                if(partidaNueva == 0 && activaHilos==1 && server.jugandoPartida==false)//revisa si es partida nueva y que ya haya referencia al server 2
                {
                    partidaNueva=1;
                    server.jugandoPartida = true;
                    fecha = server.getFecha();//pone el mes/dia/año
                    server.fecha = server.getFecha();
                    hora_inicio = serverHora;//
                    server.hora_Inicio = hora_inicio; //guarda su hora inicio en server
                    jugandoPartidaS2(hora_inicio,fecha);//avisa que hay una partida en juego y se manda la hora inicio del servidor que pudo se enviada remotamente
                    jugandoPartidaS3(hora_inicio,fecha);
                    try 
                    {
                        System.out.println("fecha: "+fecha+" hora: "+hora_inicio);
                        QueryActual=BD.insertPartida(fecha, hora_inicio);
                    }catch (SQLException ex) 
                    {
                        System.out.println("No se pudo hacer insertPartida");
                    }
                        QueryServer2();
                        QueryServer3();
                }

                if(server.peticion==true && activaHilos==1)//Revisa si hay una nueva peticion y que ya haya referencia al server 2
                {
                    server.peticion = false;//como se acepta la peticion no se reciben en este momento
                    String nombreCarta = server.cartaActual;//obtiene la carta entregada al usuario
                    String clave = server.clave;//obtiene la clave de esa carta actual
                    if(!nombreCarta.equals(" "))//sino recibe cadena vacía
                    {    
                        System.out.println("nombreCarta: "+nombreCarta+" Clave: "+clave);
                        try 
                        {
                            ImageIcon icon = IMAGEN.DisplayImage(clave);//se busca la imagen en la carpeta con el nombre "clave" correspondiente
                            imagenCarta.setIcon(icon);//pone la imagen en el label
                            QueryActual=BD.insertPeticion(clave, server.ipJugadorActual, server.valorCartaActual,serverHora,1); //inserta la peticion en la bd
                        }catch (IOException ex) 
                        {
                            System.out.println("Error: "+ex);
                            JOptionPane.showMessageDialog(null,"No se encontro la imagen"); 
                        }catch (SQLException ex) 
                        {
                            System.out.println("No se pudo hacer el insertPeticion");
                        }

                        QueryServer2();
                        QueryServer3();

                        if(reinicio==0)//si aun no se reinicia por primera vez
                        {
                            eliminaCartaS2(clave);
                            eliminaCartaS3(clave);
                        }//elimina la carta en el server 1
                    }else
                    {
                        JOptionPane.showMessageDialog(null,"Ingresa nombre de la cadena"); 
                    }    
                }

                System.out.println("isEmpty="+server.cartas.isEmpty()+" update:"+ejecuteUpdate+" server.primario="+server.primario);
                if(server.cartas.isEmpty()==true && ejecuteUpdate==false)//si ya no hay más elementos en la lista la partida termino
                {
                    hora_fin = serverHora;//llamada a funcion que retorna la hora actual
                    try
                    {
                        System.out.println("Haciendo updatePartida en S1");
                        QueryActual=BD.updatePartida(hora_fin, server.hora_Inicio, server.fecha);//se manda la hora inicio del servidor que pudo se enviada remotamente para encontrar la hora de inicio correcta de la partida
                        System.out.println("QueryActual = "+QueryActual);
                    }catch(SQLException ex)
                    {
                        System.out.println("No se pudo hacer el updatePartida");
                    }
                        QueryServer2();
                        QueryServer3();
                        ejecuteUpdate=true;
                }
            }
            
            try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServidorFormS1.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
        while(ct==h3)//hilo para permitir el aviso de que se tiene referencia al servidor 2 y 3
        {
            System.out.println("activaHilos="+activaHilos);
            if(server.servidor2Obj==null)
                System.out.println("referencia a server 2 es null");
            
            if(server.servidor3Obj==null)
                System.out.println("referencia a server 3 es null");
            
            if(!(server.servidor2Obj == null) && !(server.servidor3Obj == null))//Si ya hay referencia no nula al servidor 2 y 3 y a los FE
            {
                activaHilos=1;//permite a los otros hilos correr
                
                h3.stop();//elimina el hilo    
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorFormS1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        while(ct==h4)//hilo para la comparacion de consultas entre cada servidor
        {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException ex) {
                System.out.println("excepcion esperando 8 segundos");
            }
            
            System.err.println("Verificando si el coordinador sigue en linea");
            switch(server.id_coordinador)
            {
                case 2://si el coordinador es el server 1
                    try
                    {
                        server.servidor2Obj.online();//pregunta al server 1 si sigue vivo
                    }catch(RemoteException e)
                    {
                        try 
                        {
                            if(server.servidor3Obj.comparaID(server.ID)==true)//hace la comparacion con el id del server 3
                            {
                                server.id_coordinador = server.ID;//si es mayor entonces server 2 sera el nuevo coordinador
                                server.primario = true;
                                server.servidor3Obj.soyCoordinador("localhost", server.ID);
                                actualizaFE(0);
                                actualizaFE(1);
                            }
                        }catch (RemoteException ex) //sino responde
                        {
                             //sino responde
                            
                                server.id_coordinador = server.ID;//automaticamente server 2 es nuevo coordinador
                                server.primario = true;
                                actualizaFE(0);
                                actualizaFE(1);
                        }
                    }
                    System.out.println("El coordinador tiene ID="+server.id_coordinador);
                break;
                
                case 3://si el coordinador es el server 1
                    try
                    {
                        server.servidor3Obj.online();//pregunta al server 1 si sigue vivo
                        if(server.servidor3Obj.comparaID(server.ID)==true)//hace la comparacion con el id del server 3
                            {
                                server.id_coordinador = server.ID;//si es mayor entonces server 2 sera el nuevo coordinador
                                server.primario = true;
                                server.servidor3Obj.soyCoordinador("localhost", server.ID);
                                actualizaFE(0);
                                actualizaFE(1);
                            }
                    }catch(RemoteException e)
                    {
                            server.id_coordinador = server.ID;
                            server.primario = true;
                            actualizaFE(0);
                            actualizaFE(1);
                    }
                    System.out.println("El coordinador tiene ID="+server.id_coordinador);
                break;
                
            }
            
            
        }
        
        
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Reiniciar;
    private javax.swing.JButton ReiniciarC;
    public static javax.swing.JLabel imagenCarta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblLamport;
    private static final javax.swing.JLabel lblReloj = new javax.swing.JLabel();
    // End of variables declaration//GEN-END:variables
}
