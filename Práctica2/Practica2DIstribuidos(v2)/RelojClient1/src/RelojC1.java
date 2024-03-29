
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Psychedelic
 */
public class RelojC1 extends javax.swing.JFrame implements Runnable {
    Calendar calendario;
    Thread horaHiloC1;
    Relojes horaC1;
    int timer1 = 1000;
    String str;
    int valorRecibido=-1;
    int actual=0;
    
    SockerEscucha SocketReceive=null;

    public RelojC1() {
        horaC1=new Relojes();
        initComponents();
        
        horaHiloC1 = new Thread(this); //inicializando cada hilo       
        horaHiloC1.start();
        
        SocketReceive = new SockerEscucha();
        SocketReceive.start(); 
       
    }
     @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == horaHiloC1) //mientras el hilo se este ejecutando
        {
            if (horaC1.aux != 1) //si aun no entra por primera vez
            {
                horaC1.calcula();
                //System.out.println("Acabo de ejecutar r1.calcula()");
                //System.out.println("hilo: "+h1.getName()+"Entre a calcular, auxiliar:"+r1.aux);
            }
            if(valorRecibido==-1)
            {
            LabelHora.setText(horaC1.hora );
            horaC1.actualiza();
            }
             else
            {
                //System.out.println("Else");
                /*int xd=Integer.parseInt(horaC1.segundos);
                LabelHora.setText(Integer.toString(xd-1));
                horaC1.actualiza2(actual);                */
                LabelHora.setText(horaC1.hora );
                horaC1.actualiza2(actual);
            }
            try {
                Thread.sleep(timer1);
            } catch (InterruptedException e) {
            }
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

        LabelHora = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        LabelHora.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        LabelHora.setForeground(new java.awt.Color(0, 0, 255));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 36)); // NOI18N
        jLabel1.setText("Horas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(LabelHora, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addComponent(LabelHora, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RelojC1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RelojC1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RelojC1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RelojC1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RelojC1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelHora;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

     

public class SockerEscucha extends Thread {
    ServerSocket serverSocket;
    Socket socketC1;
    public SockerEscucha(){
        try {  
            serverSocket=new ServerSocket(6677);
            socketC1= new Socket("198.168.1.73",6677);//se conecta al puerto ip del maestro
        } catch (IOException ex) {
            //Logger.getLogger(Reloj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
                try{  
                while(true){
                    socketC1=serverSocket.accept();//establishes connection   
                    DataInputStream dis=new DataInputStream(socketC1.getInputStream());  
                    str=(String)dis.readUTF();  
                    
                    /*horaHiloC1.stop();
                    String[] parts=str.split(":");
                    LabelHora.setText(parts[0]);
                    horaHiloC1.start();      */
                    
                    horaHiloC1.suspend();
                    String[] parts=str.split(":");
                    actual=Integer.parseInt(parts[0]);
                    if(parts[0]!=null)
                    {
                        valorRecibido++;
                    }
                    LabelHora.setText(parts[0]);
                    horaC1.hora=parts[0];
                    horaHiloC1.resume();

                    socketC1.close(); 
                }
                }catch(Exception e){System.out.println(e);}  
            }
    }

}


