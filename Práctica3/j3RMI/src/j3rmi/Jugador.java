package j3rmi;

import interfazp3.InterfaceServidor;
import java.io.IOException;
import java.util.Calendar;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class Jugador extends javax.swing.JFrame implements Runnable {

    Calendar calendario;
    Thread hm,h1;
    Relojes rm;
    String entrada;
    String ip;
    int timer1 = 1000;
    Registry registry;
    InterfaceServidor servidorObj;
    String serverAddress = "localhost";//la ip del servidor
    int serverPort = 3232;

    /**
     * Creates new form Reloj
     */
    public Jugador() throws UnknownHostException {
        rm = new Relojes(); //inicializando cada reloj por separado
        initComponents();
        ip = InetAddress.getLocalHost().toString();
        hm = new Thread(this); //inicializando cada hilo       
        h1 = new Thread(this); //inicializando cada hilo       
        hm.start(); //ejecutando cada hilo
        h1.start(); //ejecutando cada hilo
        setLocationRelativeTo(null);
        setTitle("Jugador 3");
        setVisible(true);
        try
        {
            registry = LocateRegistry.getRegistry(serverAddress, serverPort);//obtiene el registro de la maquina con la ip seleccionada
            servidorObj = (InterfaceServidor)(registry.lookup("operacionservidor"));//localiza lo guardado en el registro con la palabra "operacionservidor"
        }catch(Exception e)
        {
           e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == hm) //mientras el hilo se este ejecutando
        {
            if (rm.aux != 1) //si aun no entra por primera vez
            {
                rm.generaHora();
                //System.out.println("Acabo de ejecutar r1.calcula()");
                //System.out.println("hilo: "+h1.getName()+"Entre a calcular, auxiliar:"+r1.aux);
            }
            RelojJ1.setText(rm.hora + ":" + rm.minutos + ":" + rm.segundos);
            rm.actualiza();
            try {
                Thread.sleep(timer1);
            } catch (InterruptedException e) {
                System.out.println("Error al generar hora"+e);
            }
        }
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RelojJ1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        PedirCarta = new javax.swing.JButton();
        lblnombreCarta = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        RelojJ1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        RelojJ1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RelojJ1.setText("Reloj jugador 3");

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Jugador 3");

        PedirCarta.setText("Pedir Carta");
        PedirCarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PedirCartaActionPerformed(evt);
            }
        });

        lblnombreCarta.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        lblnombreCarta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RelojJ1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(PedirCarta))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(lblnombreCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RelojJ1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PedirCarta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblnombreCarta, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PedirCartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PedirCartaActionPerformed
   try {
            
            String carta = servidorObj.darCarta(ip, RelojJ1.getText());
            if(!carta.equals("n"))
            {
                lblnombreCarta.setText(" ");
                lblnombreCarta.setText(carta);
            }else
            {
                JOptionPane.showMessageDialog(null, "Se repartieron todas las cartas");
            }
        } catch (IOException ex) {
            System.out.println("No se pudo hacer la peticion de carta");
        }
   
        
   
    }//GEN-LAST:event_PedirCartaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PedirCarta;
    private javax.swing.JLabel RelojJ1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblnombreCarta;
    // End of variables declaration//GEN-END:variables
}
