/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Psychedelic
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;


public class SegundoForm extends javax.swing.JFrame implements Runnable{
Calendar calendario;
    Thread segundoHiloC1;
    Relojes segundoC1;
    int timer1 = 1000;
    String str;
    int valorRecibido=-1;
    int actual=0;
    
    SockerEscucha SocketReceive=null;
    /**
     * Creates new form SegundoForm
     */
    public SegundoForm() {
        segundoC1=new Relojes();
        initComponents();
        
        segundoHiloC1 = new Thread(this); //inicializando cada hilo       
        segundoHiloC1.start();
        
        SocketReceive = new SockerEscucha();
        SocketReceive.start();
    }
     @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == segundoHiloC1) //mientras el hilo se este ejecutando
        {
            if (segundoC1.aux != 1) //si aun no entra por primera vez
            {
              //  System.out.println("Entre si aun no entra por primera vez");
               segundoC1.calcula();
                //System.out.println("Acabo de ejecutar r1.calcula()");
                //System.out.println("hilo: "+h1.getName()+"Entre a calcular, auxiliar:"+r1.aux);
            }
           
            if(valorRecibido==-1)
            {
            //System.out.println("valor recibido 0");
            LabelHora.setText(segundoC1.segundos );
            segundoC1.actualiza();
            }
            else
            {
                //System.out.println("Else");
                LabelHora.setText(segundoC1.segundos );
                segundoC1.actualiza2(actual);
                if(actual<=58)
                actual++;
                else actual=1;
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
        jLabel1.setText("Segundos");

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
                        .addGap(120, 120, 120)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(141, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(SegundoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SegundoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SegundoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SegundoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SegundoForm().setVisible(true);
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
            serverSocket=new ServerSocket(6679);
            socketC1= new Socket("198.168.1.73",6679);
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
                    //horaC1.actualiza();
                    //RelojC1 relojNuevo=new RelojC1();
                    
                    //segundoHiloC1.stop();
                    segundoHiloC1.suspend();
                    String[] parts=str.split(":");
                    actual=Integer.parseInt(parts[2]);
                    if(parts[2]!=null)
                    {
                        valorRecibido++;
                       
                    }
                    LabelHora.setText(parts[2]);
                    //this.run();
                    segundoHiloC1.resume();
                    //segundoHiloC1.start();      
                    
                    socketC1.close(); 
                }
                }catch(Exception e){System.out.println(e);}  
            }
    }

}


