
package reloj;

import java.util.*;


public class Reloj extends javax.swing.JFrame implements Runnable {
    Calendar calendario;
    Thread h1,h2,h3,h4;
    Relojes r1,r2,r3,r4;
    int auxhora = 0;
    int auxmin = 0; 
    int auxseg = 0; 
    int auxseg2 = 0;
    int auxr1 = 0;
    int timer1 = 1000, timer2 = 1000, timer3 = 1000, timer4 = 1000;
    /**
     * Creates new form Reloj
     */
    public Reloj() {
        r1 = new Relojes(); //inicializando cada reloj por separado
        r2 = new Relojes();
        r3 = new Relojes();
        r4 = new Relojes();
        initComponents();
        
        h1 = new Thread(this); //inicializando cada hilo
        h2 = new Thread(this);
        h3 = new Thread(this);
        h4 = new Thread(this);
        h1.start(); //ejecutando cada hilo
        h2.start();
        h3.start();
        h4.start();
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
             r1.calcula();
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
       
       while(ct==h2)
       {
           if(r2.aux!=1)//si aun no se genera la hora
           {
              r2.generaHora();
           }
           
           lblReloj2.setText(r2.hora+":"+r2.minutos+":"+r2.segundos);
           r2.actualiza();//actualiza la hora
           try
           {
             Thread.sleep(timer2);
           }catch(InterruptedException e){}
       }
       
       while(ct==h3)
       {
           if(r3.aux!=1)//si aun no se genera la hora
           {
              r3.generaHora();
           }
           
           lblReloj3.setText(r3.hora+":"+r3.minutos+":"+r3.segundos);
           r3.actualiza();//actualiza la hora
           try
           {
             Thread.sleep(timer3);
           }catch(InterruptedException e){}
       }
       
       while(ct==h4)
       {
           if(r4.aux!=1)//si aun no se genera la hora
           {
              r4.generaHora();
           }
           lblReloj4.setText(r4.hora+":"+r4.minutos+":"+r4.segundos);
           r4.actualiza();//actualiza la hora
           try
           {
             Thread.sleep(timer4);
           }catch(InterruptedException e){}
       }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblReloj2 = new javax.swing.JLabel();
        lblReloj4 = new javax.swing.JLabel();
        lblReloj3 = new javax.swing.JLabel();
        ModificarR1 = new javax.swing.JButton();
        ModificarR2 = new javax.swing.JButton();
        ModificarR3 = new javax.swing.JButton();
        ModificarR4 = new javax.swing.JButton();
        lblReloj1 = new javax.swing.JLabel();

        jLabel2.setText("jLabel1");

        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("lblReloj2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblReloj2.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        lblReloj2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReloj2.setText("lblReloj2");

        lblReloj4.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        lblReloj4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReloj4.setText("lblReloj4");

        lblReloj3.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        lblReloj3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReloj3.setText("lblReloj3");

        ModificarR1.setText("Modificar");
        ModificarR1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarR1ActionPerformed(evt);
            }
        });

        ModificarR2.setText("Modificar");
        ModificarR2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarR2ActionPerformed(evt);
            }
        });

        ModificarR3.setText("Modificar");
        ModificarR3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarR3ActionPerformed(evt);
            }
        });

        ModificarR4.setText("Modificar");
        ModificarR4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarR4ActionPerformed(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblReloj1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(lblReloj3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(ModificarR1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(ModificarR3)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblReloj4, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addComponent(lblReloj2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ModificarR2)
                                .addGap(68, 68, 68)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                        .addComponent(ModificarR4)
                        .addGap(117, 117, 117))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModificarR1)
                    .addComponent(ModificarR2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReloj1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReloj2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModificarR3)
                    .addComponent(ModificarR4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReloj3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReloj4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
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

    private void ModificarR2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarR2ActionPerformed
        //h2.stop();
        h2.suspend();
        Modifica m = new Modifica();
        m.setVisible(true);
        m.setLocationRelativeTo(null);
        m.setTitle("Modificar Reloj 2");
        m.invocador=2;
        m.relojOriginal=this;
        //this.setVisible(false);
    }//GEN-LAST:event_ModificarR2ActionPerformed

    private void ModificarR3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarR3ActionPerformed
        //h3.stop();
        h3.suspend();
        Modifica m = new Modifica();
        m.setVisible(true);
        m.setLocationRelativeTo(null);
        m.setTitle("Modificar Reloj 3");
        m.invocador=3;
        m.relojOriginal=this;
        //this.setVisible(false);
    }//GEN-LAST:event_ModificarR3ActionPerformed

    private void ModificarR4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarR4ActionPerformed
        //h4.stop();
        h4.suspend();
        Modifica m = new Modifica();
        m.setVisible(true);
        m.setLocationRelativeTo(null);
        m.setTitle("Modificar Reloj 4");
        m.invocador=4;
        m.relojOriginal=this;
        //this.setVisible(false);
    }//GEN-LAST:event_ModificarR4ActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ModificarR1;
    private javax.swing.JButton ModificarR2;
    private javax.swing.JButton ModificarR3;
    private javax.swing.JButton ModificarR4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblReloj1;
    private javax.swing.JLabel lblReloj2;
    private javax.swing.JLabel lblReloj3;
    private javax.swing.JLabel lblReloj4;
    // End of variables declaration//GEN-END:variables


 
}
