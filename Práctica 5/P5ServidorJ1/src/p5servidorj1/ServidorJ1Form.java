/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p5servidorj1;

import BD.Cls_Servidor;
import Reloj.Modifica;
import Reloj.Relojes;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import Ping.Ping;
/**
 *
 * @author jSeba
 */
public class ServidorJ1Form extends javax.swing.JFrame implements Runnable{

    public P5ServidorJ1 server1;
    public Cls_Servidor BD;
    public Relojes reloj;
    public DisplayImage IMAGEN;
    public Thread h1,h2,h3,h4;
    public int partidaNueva = 0;
    public Calendar calendario;
    public String fecha,hora_inicio,hora_fin;//fecha completa, hora inicio de la partida y hora fin de la partida
    public int timer1=1000;//tiempo de actualizacion del reloj del servidor
    public String serverHora;
    int activaHilos=0;//activara los hilos hasta que detecte que el servidor 2 ya esta conectado y se pueden hacer procedimientos remotos
    public boolean ejecuteUpdate=false;
    //datos para server 2
    public String QueryActual;
    public int reinicio=0;
    //datos para server utc
    public Ping ping;
    public int latencia;
    public long ajuste;
    public String[] hora = new String[3];//guardara la hora del server utc
    
    public ServidorJ1Form() throws RemoteException {
        initComponents();
        server1=new P5ServidorJ1();
        server1.iniciarServidor();
        BD = new Cls_Servidor();
        reloj = new Relojes();
        IMAGEN = new DisplayImage();
        ping = new Ping();
        this.setLocationRelativeTo(null);
        this.setTitle("Servidor Juego De Cartas J1");
        this.setVisible(true);
        
        try {
            ImageIcon icon = IMAGEN.DisplayImage("0");//pone el inverso de la carta
            imagenCarta.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(ServidorJ1Form.class.getName()).log(Level.SEVERE, null, ex);
        }
        h1 = new Thread(this);
        h2 = new Thread(this);
        h3 = new Thread(this);
        h4 = new Thread(this);
        h1.start();
        h2.start();
        h3.start();
        h4.start();
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblReloj = new javax.swing.JLabel();
        Modificar = new javax.swing.JButton();
        imagenCarta = new javax.swing.JLabel();
        Reiniciar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Servidor J1");

        lblReloj.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        lblReloj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReloj.setText("lblReloj");

        Modificar.setText("Modificar");
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        imagenCarta.setText("lblCarta");

        Reiniciar.setText("Reiniciar");
        Reiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReiniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(Modificar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(Reiniciar))
                    .addComponent(imagenCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Modificar))
                    .addComponent(imagenCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Reiniciar)
                .addContainerGap(36, Short.MAX_VALUE))
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
        server1.cartas = server1.llenarLista(server1.cartas);//llena la lista de donde se sacan las claves de las cartas
        partidaNueva = 0;
        reinicio=1;
        ejecuteUpdate=false;
        try {
            ImageIcon icon = IMAGEN.DisplayImage("0");//pone el inverso de la carta
            imagenCarta.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(ServidorJ1Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ReiniciarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Reiniciar;
    private javax.swing.JLabel imagenCarta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblReloj;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        Thread ct = new Thread();
        ct = Thread.currentThread();
        while(ct==h1)
        {
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
            try {
                Thread.sleep(timer1);
            } catch (InterruptedException e) {
                System.out.println("Error al generar hora"+e);
            }
        }
        
        while(ct==h2)//hilo para las peticiones del cliente
        {
            //System.out.println("Server.peticion = "+ server.peticion);
            if(partidaNueva == 0 && activaHilos==1)//revisa si es partida nueva y que ya haya referencia al server 2
            {
                partidaNueva=1;
                fecha = server1.getFecha();//pone el mes/dia/año
                hora_inicio = serverHora;//
                try {
                    System.out.println("fecha: "+fecha+" hora: "+hora_inicio);
                    QueryActual=BD.insertPartida(fecha, hora_inicio);
                    server1.servidor2Obj.queryRemoto(QueryActual);
   
                } catch (SQLException ex) {
                    System.out.println("No se pudo hacer insertPartida");
                } catch (RemoteException ex) {
                    System.out.println("No se pudo hacer el query remoto de insert partida a server 2");
                }
            }
            
            if(server1.peticion==true && activaHilos==1)//Revisa si hay una nueva peticion y que ya haya referencia al server 2
            {
                server1.peticion = false;//como se acepta la peticion no se reciben en este momento
                String nombreCarta = server1.cartaActual;//obtiene la carta entregada al usuario
                String clave = server1.clave;//obtiene la clave de esa carta actual
                if(!nombreCarta.equals(" "))//sino recibe cadena vacía
                {    
                    System.out.println("nombreCarta: "+nombreCarta+" Clave: "+clave);
                    try {
                        ImageIcon icon = IMAGEN.DisplayImage(clave);//se busca la imagen en la carpeta con el nombre "clave" correspondiente
                        imagenCarta.setIcon(icon);//pone la imagen en el label
                        QueryActual=BD.insertPeticion(clave, server1.ipJugadorActual, server1.valorCartaActual,serverHora,1); //inserta la peticion en la bd
                        server1.servidor2Obj.queryRemoto(QueryActual);//hace el query remoto a server 2
                        if(reinicio==0)//si aun no se reinicia por primera vez
                        {server1.servidor2Obj.eliminaCartaRemota(clave);}//elimina la carta en el server 1
                    } catch (IOException ex) {
                        System.out.println("Error: "+ex);
                        JOptionPane.showMessageDialog(null,"No se encontro la imagen"); 
                    } catch (SQLException ex) {
                        System.out.println("No se pudo hacer el insertPeticion");
                    }
                }else
                {
                    JOptionPane.showMessageDialog(null,"Ingresa nombre de la cadena"); 
                }    
            }
            
            //REVISA SI ACABO DE REPARTIR TODAS LAS CARTAS Y AUN NO SE EJECUTA EL UPDATE PARA REGISTRAR LA HORA FINAL DE LA PARTIDA
            if(server1.cartas.isEmpty() && ejecuteUpdate==false)//si ya no hay más elementos en la lista la partida termino
            {
                hora_fin = serverHora;//llamada a funcion que retorna la hora actual
                try{
                    QueryActual=BD.updatePartida(hora_fin, hora_inicio, fecha);
                    server1.servidor2Obj.queryRemoto(QueryActual);//hace el query remoto a server 2
                    ejecuteUpdate=true;
                }catch(SQLException ex)
                {
                    System.out.println("No se pudo hacer el updatePartida");
                } catch (RemoteException ex) {
                    Logger.getLogger(ServidorJ1Form.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorJ1Form.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
        //Hilo para revisar la sincronizacion
        while(ct==h3)
        {
            //SI SE INDICA DESDE SERVER UTC QUE SE HARÁ SINCORNIZACION
            
            if(server1.hacerSincronizacion==true)
            {
                //System.out.println("Hare sincronizacion en server 1");
                try {
                    server1.hacerSincronizacion=false;//se desactiva la bandera
                    latencia = ping.hacerPing(server1.utcAddress);//se hace ping al server UTC
                    ajuste = server1.serverUTC.actualizaHora(serverHora, latencia);//manda la funcion que obtendrá el ajuste entre los dos servidores

                    if(ajuste<0)//debe haber ralentizacion
                    {
                        timer1=3000;//el timer de actualizacion del reloj aumenta
                        System.out.println("Hice ralentizacion");
                    }else
                    {
                        if(ajuste>0)//debe haber actualizacion
                        {
                            try {
                                hora = server1.serverUTC.darHora();//obtiene la hora del reloj de la interfaz
                                reloj.hora = hora[0];
                                reloj.minutos = hora[1];      
                                reloj.segundos = hora[2];
                                timer1=1000;//devuelve el timer a su actualizacion de 1 segundo
                                System.out.println("Hice actualizacion");
                             }catch (RemoteException ex) {
                                System.out.println("No se pudo actualizar");
                             }

                        }
                    }
                } catch (Exception e) {
                    System.out.println("No pude obtener actualizacion del servidor UTC en servidor 1: "+e);
                } 
            }else
            {
                //System.out.println("SYN: "+server1.hacerSincronizacion);
            }
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorJ1Form.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        while(ct==h4)//hilo para permitir el aviso de que se tiene referencia al servidor 2
        {
            System.out.println("activaHilos= "+activaHilos);
            if(!(server1.servidor2Obj == null))//Si ya hay referencia no nula al servidor 2
            {
                activaHilos=1;//permite a los otros hilos correr
                h4.stop();//elimina el hilo
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorJ1Form.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
