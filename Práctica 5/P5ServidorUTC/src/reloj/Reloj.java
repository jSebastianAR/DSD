
package reloj;

import java.util.*;


public class Reloj extends javax.swing.JFrame implements Runnable {
    public    Calendar calendario;
    public    Thread h1;
    public    Relojes r1;
    public    int auxhora = 0;
    public    int auxmin = 0; 
    public    int auxseg = 0; 
    public    int auxseg2 = 0;
    public    int auxr1 = 0;
    public    int timer1 = 1000;
    /**
     * Creates new form Reloj
     */
    public Reloj() {
        r1 = new Relojes(); //inicializando cada reloj por separado
        initComponents();
        
        h1 = new Thread(this); //inicializando cada hilo
        
        h1.start(); //ejecutando cada hilo
        
        setLocationRelativeTo(null);
        setTitle("Reloj");
        setVisible(true);
    }
    
       @Override
    public void run() {
       Thread ct = Thread.currentThread();
       
       
       while(ct==h1) //mientras el hilo se este ejecutando
       {
           
           
           if(r1.aux!=1) //si aun no entra por primera vez
           {
             //r1.calcula();
             r1.generaHora();
             //System.out.println("Acabo de ejecutar r1.calcula()");
             //System.out.println("hilo: "+h1.getName()+"Entre a calcular, auxiliar:"+r1.aux);
           }
           lblReloj1.setText(r1.hora+":"+r1.minutos+":"+r1.segundos);
           r1.actualiza();
           try
           {
             Thread.sleep(timer1);
             
           }catch(InterruptedException e){}
       }
       
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ModificarR1 = new javax.swing.JButton();
        lblReloj1 = new javax.swing.JLabel();

        jLabel2.setText("jLabel1");

        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("lblReloj2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ModificarR1.setText("Modificar");
        ModificarR1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarR1ActionPerformed(evt);
            }
        });

        lblReloj1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        lblReloj1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReloj1.setText("lblReloj1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblReloj1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(ModificarR1)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ModificarR1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblReloj1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ModificarR1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarR1ActionPerformed
        h1.suspend();
        Modifica m = new Modifica();
        m.setVisible(true);
        m.setLocationRelativeTo(null);
        m.setTitle("Modificar Reloj 1");
        m.invocador=1;
        m.relojOriginal=this;
        //this.setVisible(false);

    }//GEN-LAST:event_ModificarR1ActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ModificarR1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblReloj1;
    // End of variables declaration//GEN-END:variables


 
}
