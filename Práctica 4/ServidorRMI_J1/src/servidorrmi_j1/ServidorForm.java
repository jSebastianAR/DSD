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
import interfazp4.ConexionServer2;
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
public class ServidorForm extends javax.swing.JFrame implements Runnable{
    
    ServidorRMI_J1 server;
    public Thread h1,h2,h3;
    public DisplayImage IMAGEN;
    public Cls_Servidor BD;
    public Modifica m;
    public Relojes reloj; 
    public int partidaNueva = 0;
    public Calendar calendario;
    public String fecha,hora_inicio,hora_fin;
    public int timer1=1000;
    public String serverHora;
    int activaHilos=0;//activara los hilos hasta que detecte que el servidor 2 ya esta conectado y se pueden hacer procedimientos remotos
    public boolean ejecuteUpdate=false;
    //datos para server 2
    public ConexionServer2 server2;
    public String server1Address = "localhost";//ip del servidor 1
    public int server1Port = 3233;//puerto del server 1
    public String QueryActual;
    public int reinicio=0;
    
    public ServidorForm() throws RemoteException {
        initComponents();
        server = new ServidorRMI_J1();
        server.iniciarServidor();
        IMAGEN = new DisplayImage();
        BD = new Cls_Servidor();
        reloj = new Relojes();
        h1 = new Thread(this);
        h2 = new Thread(this);
        h3 = new Thread(this);
        h1.start();
        h2.start();
        h3.start();
        this.setLocationRelativeTo(null);
        this.setTitle("Servidor Juego De Cartas J1");
        this.setVisible(true);
        try {
            ImageIcon icon = IMAGEN.DisplayImage("0");//pone el inverso de la carta
            imagenCarta.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(ServidorForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Modificar = new javax.swing.JButton();
        imagenCarta = new javax.swing.JLabel();
        Reiniciar = new javax.swing.JButton();

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(Modificar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(lblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagenCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Reiniciar)
                        .addGap(16, 16, 16)))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagenCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Reiniciar)
                    .addComponent(Modificar))
                .addContainerGap(27, Short.MAX_VALUE))
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
            Logger.getLogger(ServidorForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ReiniciarActionPerformed

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
            try {
                Thread.sleep(timer1);
            } catch (InterruptedException e) {
                System.out.println("Error al generar hora"+e);
            }
        }
        
        
        while(ct==h2 )//hilo para las peticiones del cliente
        {
            //System.out.println("Server.peticion = "+ server.peticion);
            if(partidaNueva == 0 && activaHilos==1)//revisa si es partida nueva y que ya haya referencia al server 2
            {
                partidaNueva=1;
                fecha = server.getFecha();//pone el mes/dia/año
                hora_inicio = serverHora;//
                try {
                    System.out.println("fecha: "+fecha+" hora: "+hora_inicio);
                    QueryActual=BD.insertPartida(fecha, hora_inicio);
                    server.servidor2Obj.queryRemoto(QueryActual);
                    
                } catch (SQLException ex) {
                    System.out.println("No se pudo hacer insertPartida");
                } catch (RemoteException ex) {
                    System.out.println("No se pudo hacer el query remoto de insert partida a server 2");
                }
            }
            
            if(server.peticion==true && activaHilos==1)//Revisa si hay una nueva peticion y que ya haya referencia al server 2
            {
                server.peticion = false;//como se acepta la peticion no se reciben en este momento
                String nombreCarta = server.cartaActual;//obtiene la carta entregada al usuario
                String clave = server.clave;//obtiene la clave de esa carta actual
                if(!nombreCarta.equals(" "))//sino recibe cadena vacía
                {    
                    System.out.println("nombreCarta: "+nombreCarta+" Clave: "+clave);
                    try {
                        ImageIcon icon = IMAGEN.DisplayImage(clave);//se busca la imagen en la carpeta con el nombre "clave" correspondiente
                        imagenCarta.setIcon(icon);//pone la imagen en el label
                        QueryActual=BD.insertPeticion(clave, server.ipJugadorActual, server.valorCartaActual,serverHora,1); //inserta la peticion en la bd
                        server.servidor2Obj.queryRemoto(QueryActual);//hace el query remoto a server 2
                        if(reinicio==0)//si aun no se reinicia por primera vez
                        {server.servidor2Obj.eliminaCartaRemota(clave);}//elimina la carta en el server 1
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
            
            if(server.cartas.isEmpty() && ejecuteUpdate==false)//si ya no hay más elementos en la lista la partida termino
            {
                hora_fin = serverHora;//llamada a funcion que retorna la hora actual
                try{
                    QueryActual=BD.updatePartida(hora_fin, hora_inicio, fecha);
                    server.servidor2Obj.queryRemoto(QueryActual);//hace el query remoto a server 2
                    ejecuteUpdate=true;
                }catch(SQLException ex)
                {
                    System.out.println("No se pudo hacer el updatePartida");
                } catch (RemoteException ex) {
                    Logger.getLogger(ServidorForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        while(ct==h3)//hilo para permitir el aviso de que se tiene referencia al servidor 2
        {
            System.out.println("activaHilos="+activaHilos);
            if(!(server.servidor2Obj == null))//Si ya hay referencia no nula al servidor 2
            {
                activaHilos=1;//permite a los otros hilos correr
                h3.stop();//elimina el hilo
            }
        }
        
        
        
    }
    
    /*
     h1.suspend();
        Modifica m = new Modifica();
        m.setVisible(true);
        m.setLocationRelativeTo(null);
        m.setTitle("Modificar Reloj 1");
        m.invocador=1;
        m.relojOriginal=this;
    */

     /*
    server.cartas = server.llenarLista(server.cartas);//llena la lista de donde se sacan las claves de las cartas
        partidaNueva = 0;
        
        try {
            ImageIcon icon = IMAGEN.DisplayImage("0");//pone el inverso de la carta
            imagenCarta.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(ServidorForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Reiniciar;
    public static javax.swing.JLabel imagenCarta;
    private javax.swing.JLabel jLabel1;
    private static final javax.swing.JLabel lblReloj = new javax.swing.JLabel();
    // End of variables declaration//GEN-END:variables
}
