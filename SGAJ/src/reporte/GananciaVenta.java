/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reporte;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Otto
 */
public class GananciaVenta extends javax.swing.JInternalFrame {
public static String fecha11,fecha21,fecha31;
    /**
     * Creates new form reporteventa
     */
    public GananciaVenta() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcMousePanel1 = new jcMousePanel.jcMousePanel();
        jLabel1 = new javax.swing.JLabel();
        Fecha1 = new org.freixas.jcalendar.JCalendarCombo();
        Fecha2 = new org.freixas.jcalendar.JCalendarCombo();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setName("GananciaVenta"); // NOI18N

        jcMousePanel1.setColor1(new java.awt.Color(204, 204, 204));
        jcMousePanel1.setColor2(new java.awt.Color(0, 153, 153));
        jcMousePanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N
        jcMousePanel1.setModo(4);
        jcMousePanel1.setName(""); // NOI18N
        jcMousePanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 2, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Reporte de salidas");

        Fecha1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        Fecha2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 2, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Desde:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 2, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Hasta:");

        jButton1.setBackground(new java.awt.Color(255, 204, 0));
        jButton1.setText("Generar");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(0));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jcMousePanel1Layout = new javax.swing.GroupLayout(jcMousePanel1);
        jcMousePanel1.setLayout(jcMousePanel1Layout);
        jcMousePanel1Layout.setHorizontalGroup(
            jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel1Layout.createSequentialGroup()
                .addGroup(jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jcMousePanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jcMousePanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jcMousePanel1Layout.createSequentialGroup()
                                .addGroup(jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(Fecha1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Fecha2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jcMousePanel1Layout.setVerticalGroup(
            jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jcMousePanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
public void reporterunn(String repo, String repo1, String fecha3) {
     fecha11=repo;
     fecha21=repo1;
     fecha31=fecha3;
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
                dispose();


        int año = Fecha1.getCalendar().get(Calendar.YEAR);
        int dia = Fecha1.getCalendar().get(Calendar.DAY_OF_MONTH);
        int mes = Fecha1.getCalendar().get(Calendar.MONTH) + 1;
        int año1 = Fecha2.getCalendar().get(Calendar.YEAR);
        int dia1 = Fecha2.getCalendar().get(Calendar.DAY_OF_MONTH);
        int mes1 = Fecha2.getCalendar().get(Calendar.MONTH) + 1;

        String id, ids, id3;

        id = "" + año + "-" + mes + "-" + dia;
      
         id3 = "" + año1 + "-" + mes1 + "-" + dia1;
        
            ids = "" + año1 + "-" + mes1 + "-" + dia1;
           reporterunn(id, ids, id3);

            new Rganancia().setVisible(true);

            System.out.print("El día de hoy es :" + id + " y " + ids);
        
        GregorianCalendar calendarios =
                new GregorianCalendar();
    }//GEN-LAST:event_jButton1ActionPerformed
public static String fecha1(){
    return fecha11;
    }

public static String fecha2(){
    return fecha21;
    }

public static String fecha3(){
    return fecha31;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.freixas.jcalendar.JCalendarCombo Fecha1;
    private org.freixas.jcalendar.JCalendarCombo Fecha2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private jcMousePanel.jcMousePanel jcMousePanel1;
    // End of variables declaration//GEN-END:variables
}
