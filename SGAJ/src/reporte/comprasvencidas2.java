/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reporte;

import BD.BdConexion;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import static reporte.Rmovimientos.conn;

/**
 *
 * @author Otto
 */
public class comprasvencidas2 extends javax.swing.JInternalFrame {

    public static String fecha11, fecha21;

    /**
     * Creates new form reporteventa
     */
    public comprasvencidas2() {
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
        jButton1 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();

        setName("comprasvencidas2"); // NOI18N

        jcMousePanel1.setColor1(new java.awt.Color(204, 204, 204));
        jcMousePanel1.setColor2(new java.awt.Color(0, 153, 153));
        jcMousePanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N
        jcMousePanel1.setModo(4);
        jcMousePanel1.setName(""); // NOI18N
        jcMousePanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 2, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Compras al Crédito");

        jButton1.setBackground(new java.awt.Color(255, 204, 0));
        jButton1.setText("Generar");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(0));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jRadioButton1.setText("Creditos Vencidos");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Todos los creditos");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jcMousePanel1Layout = new javax.swing.GroupLayout(jcMousePanel1);
        jcMousePanel1.setLayout(jcMousePanel1Layout);
        jcMousePanel1Layout.setHorizontalGroup(
            jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addGap(65, 65, 65))
            .addGroup(jcMousePanel1Layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jcMousePanel1Layout.setVerticalGroup(
            jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1))
                .addContainerGap(57, Short.MAX_VALUE))
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
public void reporterunn(String repo, String repo1) {

        fecha11 = repo;
        fecha21 = repo1;
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (jRadioButton1.isSelected() == true || jRadioButton2.isSelected() == true) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una Opcion");
        }

        if (jRadioButton1.isSelected() == true) {
            Calendar dcFech = Calendar.getInstance();
            String fecha;
            int años = dcFech.get(Calendar.YEAR);
            int dias = dcFech.get(Calendar.DAY_OF_MONTH);
            int mess = dcFech.get(Calendar.MONTH) + 1;
            fecha = "" + años + "-" + mess + "-" + dias;

            try {

                String archivo = "";
                archivo = "creditosvencidosprov.jasper";
                if (archivo == null) {
                    //System.out.println("No hay ARCHIVO " + archivo);
                    System.exit(2);
                }
                JasperReport masterReport = null;

                try {
                    masterReport = (JasperReport) JRLoader.loadObject(archivo);
                } catch (JRException e) {
                    //System.out.println("error cargado el reporte maestro " + e.getMessage());
                    System.exit(3);
                }

                conn = BdConexion.getConexion();
                Map parametro = new HashMap();
                //parametro.put("idsalida", idabono);
                parametro.put("defecha", fecha);
                //parametro.put("afecha", fech2);
                JasperPrint impresor = JasperFillManager.fillReport(masterReport, parametro, conn);
                JasperViewer jviewer = new JasperViewer(impresor, false);
                jviewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                jviewer.setTitle("Compras Vencidas");
                jviewer.setVisible(true);
                //conn.close();
            } catch (JRException e) {
                JOptionPane.showMessageDialog(null, "Error " + e.toString());
            }

        } else if (jRadioButton2.isSelected() == true) {

           // String id, ids;

//            int año = Fecha1.getCalendar().get(Calendar.YEAR);
//            int dia = Fecha1.getCalendar().get(Calendar.DAY_OF_MONTH);
//            int mes = Fecha1.getCalendar().get(Calendar.MONTH) + 1;
//
//            int año1 = Fecha2.getCalendar().get(Calendar.YEAR);
//            int dia1 = Fecha2.getCalendar().get(Calendar.DAY_OF_MONTH);
//            int mes1 = Fecha2.getCalendar().get(Calendar.MONTH) + 1;
//
//            id = "" + año + "-" + mes + "-" + dia;
//            ids = "" + año1 + "-" + mes1 + "-" + dia1;
            //id3 = fecha;

//            reporterunn(id, ids);

            new comprasvencidasv2().setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        if (jRadioButton1.isSelected() == true) {
            jRadioButton2.setSelected(false);
//            Fecha1.setEnabled(false);
//            Fecha2.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        if (jRadioButton2.isSelected() == true) {
            jRadioButton1.setSelected(false);
//            Fecha1.setEnabled(true);
//            Fecha2.setEnabled(true);
        } else {
//            Fecha1.setEnabled(false);
//            Fecha2.setEnabled(false);
        }


    }//GEN-LAST:event_jRadioButton2ActionPerformed
    public static String fecha1() {
        return fecha11;
    }

    public static String fecha2() {
        return fecha21;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private jcMousePanel.jcMousePanel jcMousePanel1;
    // End of variables declaration//GEN-END:variables
}
