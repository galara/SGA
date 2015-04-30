/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package reporte;

import BD.LeePropiedades;
import static BD.LeePropiedades.archivoRecurso;
import GUI.frmventas;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author otto
 */
public class cortecaja extends javax.swing.JInternalFrame {

    /**
     * Creates new form cortecaja
     */
    public cortecaja() {
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
        Fincial = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        totalventa = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Tefectico = new javax.swing.JLabel();
        jcMousePanel2 = new jcMousePanel.jcMousePanel();
        Tcredito = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        Tdev = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jcMousePanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N
        jcMousePanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Corte Inicio");
        jcMousePanel1.add(jLabel1);
        jLabel1.setBounds(0, 28, 76, 25);
        jcMousePanel1.add(Fincial);
        Fincial.setBounds(80, 28, 95, 20);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Q.    Ventas Totales");
        jcMousePanel1.add(jLabel3);
        jLabel3.setBounds(0, 71, 175, 22);

        totalventa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totalventa.setForeground(new java.awt.Color(255, 255, 255));
        jcMousePanel1.add(totalventa);
        totalventa.setBounds(193, 71, 136, 21);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Ventas");
        jcMousePanel1.add(jLabel5);
        jLabel5.setBounds(34, 104, 141, 22);

        jPanel1.setBackground(new java.awt.Color(175, 201, 175));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("En Efectivo");

        Tefectico.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Tefectico.setText("0.0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tefectico, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tefectico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jcMousePanel1.add(jPanel1);
        jPanel1.setBounds(5, 130, 175, 30);

        jcMousePanel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N

        Tcredito.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Tcredito.setForeground(new java.awt.Color(255, 255, 255));
        Tcredito.setText("0.0");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("A Credito");

        javax.swing.GroupLayout jcMousePanel2Layout = new javax.swing.GroupLayout(jcMousePanel2);
        jcMousePanel2.setLayout(jcMousePanel2Layout);
        jcMousePanel2Layout.setHorizontalGroup(
            jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel2Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tcredito, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jcMousePanel2Layout.setVerticalGroup(
            jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tcredito, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jcMousePanel1.add(jcMousePanel2);
        jcMousePanel2.setBounds(5, 160, 175, 30);

        jPanel2.setBackground(new java.awt.Color(175, 201, 175));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Devoluciones");

        Tdev.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Tdev.setText("0.0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tdev, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tdev, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jcMousePanel1.add(jPanel2);
        jPanel2.setBounds(5, 190, 175, 30);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("0.0");
        jLabel11.setToolTipText("");
        jcMousePanel1.add(jLabel11);
        jLabel11.setBounds(115, 230, 90, 30);

        jButton1.setText("Cuadre");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jcMousePanel1.add(jButton1);
        jButton1.setBounds(200, 30, 90, 23);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jcMousePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            LeePropiedades.archivoRecurso = archivoRecurso;
            Connection conexion = (Connection) DriverManager.getConnection(LeePropiedades.leeID("url"), LeePropiedades.leeID("usuario"), LeePropiedades.leeID("password"));
            Connection conexion2 = (Connection) DriverManager.getConnection(LeePropiedades.leeID("url"), LeePropiedades.leeID("usuario"), LeePropiedades.leeID("password"));
            // Se crea un Statement, para realizar la consulta
            Statement s = (Statement) conexion.createStatement();
            Statement s2 = (Statement) conexion2.createStatement();
            String fechai="",fechaf="";
        int años = Fincial.getCalendar().get(Calendar.YEAR);
            int dias = Fincial.getCalendar().get(Calendar.DAY_OF_MONTH);
            
            int mess = Fincial.getCalendar().get(Calendar.MONTH) + 1;

 fechai = "" + años + "-" + mess + "-" + dias;
 fechaf="" + años + "-" + mess + "-" + dias;
 Calendar cal = new GregorianCalendar();
            
            cal.add(Calendar.DAY_OF_MONTH,1);
        
        int mes2 = cal.get(Calendar.MONTH) + 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        String fechaf2 = cal.get(Calendar.YEAR) + "-" + mes2 + "-" + cal.get(Calendar.DAY_OF_MONTH);        
            //String sql = "select producto.precioventa,producto.cantidad,producto.idProducto,producto.Codigo,producto.nombre,proveedor.nombre from producto INNER JOIN proveedor ON proveedor_idProveedor=idProveedor where nombre like '%" + busqueda.getText() + "%'and idproducto!='1'";
            String sql = "select total from salida  where fecha BETWEEN '"+fechai+"' AND '"+fechaf2+"' and tipopago_idtipopago=1";
            ResultSet rs = s.executeQuery(sql);
float suma=0;
            while (rs.next()) {
                suma=suma+rs.getFloat("total");
                System.out.println(suma);
              
            }
            Tefectico.setText(""+suma);
            sql = "select total from salida  where fecha BETWEEN '"+fechai+"' AND '"+fechaf2+"' and tipopago_idtipopago>1";
            ResultSet rs1 = s.executeQuery(sql);
float suma1=0;
            while (rs1.next()) {
                suma1=suma1+rs1.getFloat("total");
              
            }
            String cliente="cliente";
            Tcredito.setText(""+suma1);
      sql = "select subtotal,idcompra from devoluciones  where fecha BETWEEN '"+fechai+"' AND '"+fechaf+"' and entradasalida='"+cliente+"'";
            ResultSet rs2 = s.executeQuery(sql);
float suma2=0;
float resta=0;
java.util.Date fecha1 = sdf.parse(fechai , new ParsePosition(0));
            while (rs2.next()) {
                
                suma2=suma2+rs2.getFloat("subtotal");
                int fechas=rs2.getInt("idcompra");
                   ResultSet rs5 = s2.executeQuery("select fecha from salida  where idsalida='"+fechas+"'");
            while(rs5.next()){
                
           
           java.util.Date fecha3 = sdf.parse(rs5.getString("fecha") , new ParsePosition(0));
           if(fecha3.equals(fecha1)){
           JOptionPane.showMessageDialog(null, fecha3+" ee");}
            }  
              
            }
            Tdev.setText(""+suma2);
           
            
            conexion.close();
            
        } catch (Exception ex) {

            Logger.getLogger(frmventas.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
    }//GEN-LAST:event_jButton1ActionPerformed
public void reporterunn(String id, String ids, String id3){

}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Fincial;
    private javax.swing.JLabel Tcredito;
    private javax.swing.JLabel Tdev;
    private javax.swing.JLabel Tefectico;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private jcMousePanel.jcMousePanel jcMousePanel1;
    private jcMousePanel.jcMousePanel jcMousePanel2;
    private javax.swing.JLabel totalventa;
    // End of variables declaration//GEN-END:variables
}
