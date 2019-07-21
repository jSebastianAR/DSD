/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jugadorrmi_s1;

import Reloj.Modifica;
import Reloj.Relojes;
import interfazp7.InterfaceJugadores;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;


/**
 *
 * @author jSeba
 */
public class JugadorForm extends javax.swing.JFrame implements Runnable{

    public Thread h1;
    public Relojes reloj;
    String entrada;
    String ip;
    int timer1 = 1000;
    Registry registry;
    InterfaceJugadores servidorObj;
    String serverAddress = "localhost";//la ip del servidor
    int serverPort = 3232;
    FrontEnd FE;
    
    public JugadorForm() throws UnknownHostException {
        initComponents();
        ip = InetAddress.getLocalHost().toString();
        reloj = new Relojes();
        h1 = new Thread(this);
        h1.start();
        setLocationRelativeTo(null);
        setTitle("Jugador en Server 1");
        setVisible(true);
        lblReloj.setText(" ");
        lblnombreCarta.setText(" ");
        FE = new FrontEnd("localhost","localhost","localhost");

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblReloj = new javax.swing.JLabel();
        Modificar = new javax.swing.JButton();
        lblnombreCarta = new javax.swing.JLabel();
        pedirCarta = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Jugador 1");

        lblReloj.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        lblReloj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReloj.setText("lblReloj");

        Modificar.setText("Modificar");
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        lblnombreCarta.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        lblnombreCarta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblnombreCarta.setText("lblnombreCarta");

        pedirCarta.setText("Pedir Carta");
        pedirCarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pedirCartaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(Modificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pedirCarta)
                .addGap(91, 91, 91))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(lblnombreCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblnombreCarta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblReloj, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Modificar)
                    .addComponent(pedirCarta))
                .addContainerGap(26, Short.MAX_VALUE))
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
        m.relojOriginal = this;
    }//GEN-LAST:event_ModificarActionPerformed

    private void pedirCartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pedirCartaActionPerformed
        try {
            String carta = FE.pedirCarta(ip, lblReloj.getText());//Hace la peticion al front end
           // String carta = servidorObj.darCarta(ip, lblReloj.getText());
            if(!carta.equals("n"))
            {
                lblnombreCarta.setText(" ");
                lblnombreCarta.setText(carta);
            }else
            {
                JOptionPane.showMessageDialog(null, "Se repartieron todas las cartas");
            }
        } catch (RemoteException ex) {
            System.out.println("No se pudo hacer la conexion remota al sistema");
        }
    }//GEN-LAST:event_pedirCartaActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Modificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblReloj;
    private javax.swing.JLabel lblnombreCarta;
    private javax.swing.JButton pedirCarta;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        
        while(ct == h1)
        {
            if(reloj.aux!=1)//aun no entra por primera vez
            {
                reloj.generaHora();
            }
            lblReloj.setText(reloj.hora+":"+reloj.minutos+":"+reloj.segundos);//pone la hora en el label
            reloj.actualiza();
            
            try {
                Thread.sleep(timer1);
            } catch (InterruptedException e) {
                System.out.println("Error al generar hora"+e);
            } 
        }
    }
}
