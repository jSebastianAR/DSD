/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorrmi_j2;

import BD.Cls_Servidor;
import Reloj.Modifica;
import Reloj.Relojes;
import interfazp4.ConexionServer1;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author jSeba
 */
public class ServidorForm extends javax.swing.JFrame implements Runnable{

    ServidorRMI_J2 server;
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
    public boolean ejecuteUpdate=false;
    
    //datos para server 1
    public ConexionServer1 server1;
    public String server1Address = "localhost";//ip del servidor 1
    public int server1Port = 3232;//puerto del server 1
    public String QueryActual;
    public int reinicio=0;
    
    public ServidorForm() throws RemoteException {
        initComponents();
        server = new ServidorRMI_J2();
        server.iniciarServidor();
        IMAGEN = new DisplayImage();
        BD = new Cls_Servidor();
        reloj = new Relojes();
        h1 = new Thread(this);
        h2 = new Thread(this);
        //h3 = new Thread();
        h1.start();
        h2.start();
       // h3.start();*/
        this.setLocationRelativeTo(null);
        this.setTitle("Servidor Juego De Cartas J2");
        this.setVisible(true);
        
        //OBTENIENDO LA REFERENCIA DEL SERVER 1
        
        try {
            Registry registry = LocateRegistry.getRegistry(server1Address, server1Port); //obtiene el registro de la maquina con la ip seleccionada
            server1 = (ConexionServer1)(registry.lookup("server1"));//localiza lo guardado en el registro con la palabra "operacionservidor"
            server1.agregaServer2(server);//agregamos el server2 a la referencia del server 1
        } catch (NotBoundException ex) {
            System.out.println("No se pudo obtener la referencia del servidor 1 en servidor 2");
        } 
        
        //OBTENIENDO LA REFERENCIA DEL SERVER 1
        
        try {
            ImageIcon icon = IMAGEN.DisplayImage("0");//pone el inverso de la carta
            imagenCarta.setIcon(icon);
        } catch (IOException ex) {
            Logger.getLogger(ServidorForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblReloj = new javax.swing.JLabel();
        imagenCarta = new javax.swing.JLabel();
        Modificar = new javax.swing.JButton();
        Reiniciar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Servidor 2");

        lblReloj.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N

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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(lblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(Modificar)))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(imagenCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(Reiniciar)
                                .addGap(23, 23, 23)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(lblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imagenCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Reiniciar)
                    .addComponent(Modificar))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        h1.suspend();
        Modifica m = new Modifica();
        m.setVisible(true);
        m.setLocationRelativeTo(null);
        m.setTitle("Modificar Reloj 2");
        m.invocador=1;
        m.relojOriginal=this;
    }//GEN-LAST:event_ModificarActionPerformed


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
        ct = Thread.currentThread();//obtiene el hilo ejecutado en ese momento
        
        while(ct==h1)//hilo para la actualizacion del reloj
        {
            try{
                //System.out.println("reloj.aux = "+reloj.aux);
                if (reloj.aux != 1) //si aun no entra por primera vez
                {
                    reloj.generaHora();
                    //System.out.println("Acabo de ejecutar r1.calcula()");
                    //System.out.println("hilo: "+h1.getName()+"Entre a calcular, auxiliar:"+r1.aux);
                }
                //System.out.println("reloj.aux = "+reloj.aux+" : "+reloj.hora +":"+ reloj.minutos +":"+ reloj.segundos);
                lblReloj.setText(reloj.hora +":"+ reloj.minutos +":"+ reloj.segundos);//pone la hora en el label
                serverHora = lblReloj.getText();
                reloj.actualiza();
            }catch(Exception e){System.out.println("Error al generar hora"+e);}    
                
                try {
                        Thread.sleep(timer1);
                    } catch (InterruptedException e) {
                        System.out.println("Error al esperar"+e);
                    }
                    
        }
        
        
        while(ct==h2)//hilo para las peticiones del cliente
        {
            //System.out.println("Server.peticion = "+ server.peticion);
            if(partidaNueva == 0)
            {
                partidaNueva=1;
                fecha = server.getFecha();//pone el mes/dia/año
                hora_inicio = serverHora;
                try {
                    System.out.println("fecha: "+fecha+" hora: "+hora_inicio);
                    QueryActual=BD.insertPartida(fecha, hora_inicio);
                    server1.queryRemoto(QueryActual);//Hace el query remoto para el server 1;
                } catch (SQLException ex) {
                    System.out.println("No se pudo hacer insertPartida");
                } catch (RemoteException ex) {
                    System.out.println("Error haciendo el insert remoto partida");
                }
            }
            
            if(server.peticion==true)//Revisa si hay una nueva peticion
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
                        QueryActual=BD.insertPeticion(clave, server.ipJugadorActual, server.valorCartaActual,serverHora,2); //inserta la peticion en la bd
                        server1.queryRemoto(QueryActual);//Hace el query remoto para el server 1;
                        if(reinicio==0)//si aun no se reinicia por primera vez
                        {server1.eliminaCartaRemota(clave);}//elimina la carta en el server 1
                        
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
                    server1.queryRemoto(QueryActual);//Hace el query remoto para el server 1;
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
            } catch (Exception e) {
                System.out.println("no pudo esperar 500 milisegundos en h2: "+e);
            }
        }
        
        /*while(ct==h3)//hilo para la comparacion de consultas entre cada servidor
        {
        
        }*/
    }
}
