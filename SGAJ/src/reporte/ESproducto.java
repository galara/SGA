/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reporte;

import BD.LeePropiedades;
import GUI.Busproducto;
import com.mysql.jdbc.Connection;
import java.awt.image.ImageObserver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Otto
 */
public class ESproducto extends javax.swing.JFrame {
public class hilos implements Runnable
{
    String id;
     java.util.Date fecha,fechaf;
    
    private Connection conn;
    private String archivoRecurso="controlador-bd";
    private boolean terminar = false;
       
   
    public void run ()
    {
fecha=Busproducto.fechaR();
id=Busproducto.id();
fechaf=Busproducto.fechaRFin();

try
{ 
           LeePropiedades.archivoRecurso = archivoRecurso;
           Class.forName(LeePropiedades.leeID("driver"));
                conn = (Connection) DriverManager.getConnection(LeePropiedades.leeID("url"),LeePropiedades.leeID("usuario"),LeePropiedades.leeID("password"));
                //isConected = true;

        try
        {

            String archivo="salidaproducto.jasper";
            System.out.println(".... "+archivo+" d . "+fecha);
        //System.out.println("caragdo desdesss "+archivo);
        if(archivo==null)
        {

        System.out.println("no hAT ARCHIVO "+archivo);
        System.exit(2);
        }
        JasperReport masterReport=null;
        try
        {
            //masterReport= (JasperReport) JRLoader.loadObject(matricula);
              masterReport= (JasperReport) JRLoader.loadObject(archivo);

        }
        catch(JRException e)
        {
        System.out.println("error cargado el reporte maestro "+e.getMessage());
        System.exit(3);
        }
        //
        //JOptionPane.showMessageDialog(null, id);
        Map parametro= new HashMap();
        parametro.put("fecha1",fecha);
        parametro.put("fechaf",fechaf);
        parametro.put("id",id);
        
        
        JasperPrint impresor= JasperFillManager.fillReport(masterReport, parametro,conn);
        //JasperPrintManager.printReport(impresor, false);
        JasperViewer jviewer= new JasperViewer(impresor,false);
        jviewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
        jviewer.setTitle("Reporte Entrada salida por Producto");
        jviewer.setVisible(true);
        conn.close();
        dispose();
        
        }
        catch(Exception j)
        {
          System.out.println("Mensajer de error "+j.getMessage());
        }

            } catch (SQLException ex) {
                Logger.getLogger(ESproducto.class.getName()).log(Level.SEVERE, null, ex);
            }
catch(ClassNotFoundException ex)
{
                Logger.getLogger(ESproducto.class.getName()).log(Level.SEVERE, null, ex);
}


        //System.out.println ("Esto se ejecuta en otro hilo");
    }//fin fin fin
    }

   
public void descargando(){

this.dispose();
}
    /**
     * Creates new form imprimiendo
     */
    public ESproducto() {
        initComponents();
        hilos miRunnable = new hilos();
Thread hilo = new Thread (miRunnable);
hilo.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(200, 200, 0, 0));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cargando.gif"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ESproducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ESproducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ESproducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ESproducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ESproducto().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
