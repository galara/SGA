/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporte;

import BD.Conectiondb;
import BD.LeePropiedades;
import static BD.LeePropiedades.archivoRecurso;
import GUI.frmventas;
import Modelos.musuario;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author otto
 */
public class cortecajausuario extends javax.swing.JInternalFrame {
    java.sql.Connection conn;//getConnection intentara establecer una conexión.
    /**
     * Creates new form cortecaja
     */
    public cortecajausuario() {
        initComponents();
        llenarcombo();
    }
    
    public void llenarcombo() {
        DefaultComboBoxModel value1;
        value1 = new DefaultComboBoxModel();
        cusuario.setModel(value1);
        
        try {
            conn = Conectiondb.Enlace(conn);
            // Se crea un Statement, para realizar la consulta
            Statement s = (Statement) conn.createStatement();
            String sql = "select idusuario,nombreusuario from usuario";

            // Se realiza la consulta. Los resultados se guardan en el 
            // ResultSet rs
            ResultSet rs = s.executeQuery(sql);

            // Se recorre el ResultSet, mostrando por pantalla los resultados.
            value1.addElement(new musuario(" ", "0"));
            while (rs.next()) {
                value1.addElement(new musuario(rs.getString("nombreusuario"), "" + rs.getInt("idusuario")));
            }

            // Se cierra la conexión con la base de datos.
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showInternalMessageDialog(this,"Ocurrio un error :"+e, "Error", JOptionPane.ERROR_MESSAGE);
            //System.out.print("Error " + e);
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

        jcMousePanel1 = new jcMousePanel.jcMousePanel();
        pnlPaginador = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        totalefectivo = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        Fincial = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        abonosefectivo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        devefectivo = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        totalventa = new javax.swing.JLabel();
        efectivocaja = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        devefectivo2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cusuario = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setTitle("Corte de Efectivo");

        jcMousePanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N
        jcMousePanel1.setLayout(null);

        pnlPaginador.setBackground(new java.awt.Color(0, 153, 204));
        pnlPaginador.setPreferredSize(new java.awt.Dimension(786, 40));
        pnlPaginador.setLayout(new java.awt.GridBagLayout());

        jLabel9.setFont(new java.awt.Font("Script MT Bold", 1, 32)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("<--Corte del Día-->");
        pnlPaginador.add(jLabel9, new java.awt.GridBagConstraints());

        jcMousePanel1.add(pnlPaginador);
        pnlPaginador.setBounds(0, 0, 490, 40);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Corte de día:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);

        totalefectivo.setBackground(javax.swing.UIManager.getDefaults().getColor("List.selectionBackground"));
        totalefectivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        totalefectivo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalefectivo.setText("0.00");
        totalefectivo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalefectivo.setOpaque(true);
        jPanel1.add(totalefectivo);
        totalefectivo.setBounds(320, 100, 80, 30);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Entradas de Efectivo:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(40, 70, 280, 22);
        jPanel1.add(jSeparator2);
        jSeparator2.setBounds(40, 70, 380, 10);

        jButton1.setText("Cuadre");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(260, 20, 130, 23);
        jPanel1.add(jSeparator1);
        jSeparator1.setBounds(30, 260, 380, 2);

        Fincial.setDate(Calendar.getInstance().getTime());
        jPanel1.add(Fincial);
        Fincial.setBounds(150, 20, 100, 20);

        jLabel14.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.activeTitleBackground"));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Devoluciones en Efectivo a Clientes");
        jLabel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel14.setOpaque(true);
        jPanel1.add(jLabel14);
        jLabel14.setBounds(40, 190, 280, 30);

        abonosefectivo.setBackground(javax.swing.UIManager.getDefaults().getColor("List.selectionBackground"));
        abonosefectivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        abonosefectivo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        abonosefectivo.setText("0.00");
        abonosefectivo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        abonosefectivo.setOpaque(true);
        jPanel1.add(abonosefectivo);
        abonosefectivo.setBounds(320, 130, 80, 30);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Salidas de Efectivo:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(40, 160, 280, 22);

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Total Efectivo del día:  Q.");
        jLabel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jLabel13.setOpaque(true);
        jPanel1.add(jLabel13);
        jLabel13.setBounds(40, 270, 280, 30);

        devefectivo.setBackground(javax.swing.UIManager.getDefaults().getColor("List.selectionBackground"));
        devefectivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        devefectivo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        devefectivo.setText("0.00");
        devefectivo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        devefectivo.setOpaque(true);
        jPanel1.add(devefectivo);
        devefectivo.setBounds(320, 190, 80, 30);

        jLabel12.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.activeTitleBackground"));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Ventas En Efectivo");
        jLabel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel12.setOpaque(true);
        jPanel1.add(jLabel12);
        jLabel12.setBounds(40, 100, 280, 30);

        totalventa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totalventa.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(totalventa);
        totalventa.setBounds(170, 20, 136, 21);

        efectivocaja.setBackground(new java.awt.Color(0, 0, 0));
        efectivocaja.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        efectivocaja.setForeground(new java.awt.Color(255, 255, 255));
        efectivocaja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        efectivocaja.setText("0.00");
        efectivocaja.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        efectivocaja.setOpaque(true);
        jPanel1.add(efectivocaja);
        efectivocaja.setBounds(320, 270, 80, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Fecha: ");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(70, 20, 76, 20);

        jLabel8.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.activeTitleBackground"));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Abonos en Efectivo (Pago de Clientes)");
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel8.setOpaque(true);
        jPanel1.add(jLabel8);
        jLabel8.setBounds(40, 130, 280, 30);

        jLabel15.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.activeTitleBackground"));
        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Devoluciones en Efectivo a Clientes");
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel15.setOpaque(true);
        jPanel1.add(jLabel15);
        jLabel15.setBounds(40, 220, 280, 30);

        devefectivo2.setBackground(javax.swing.UIManager.getDefaults().getColor("List.selectionBackground"));
        devefectivo2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        devefectivo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        devefectivo2.setText("0.00");
        devefectivo2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        devefectivo2.setOpaque(true);
        jPanel1.add(devefectivo2);
        devefectivo2.setBounds(320, 220, 80, 30);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Usuario:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(120, 50, 49, 15);
        jPanel1.add(cusuario);
        cusuario.setBounds(180, 50, 160, 20);

        jcMousePanel1.add(jPanel1);
        jPanel1.setBounds(20, 40, 450, 320);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ser la misma en la computadora para evitar inconsistencias en los datos.");
        jLabel2.setOpaque(true);
        jcMousePanel1.add(jLabel2);
        jLabel2.setBounds(10, 387, 470, 20);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("*Nota: Antes de realizar el corte tomar en cuenta que la fecha actual debe");
        jLabel3.setOpaque(true);
        jcMousePanel1.add(jLabel3);
        jLabel3.setBounds(10, 370, 470, 17);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
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
            String fechai = "", fechaf = "";
            int años = Fincial.getCalendar().get(Calendar.YEAR);
            int dias = Fincial.getCalendar().get(Calendar.DAY_OF_MONTH);

            int mess = Fincial.getCalendar().get(Calendar.MONTH) + 1;

            fechai = "" + años + "-" + mess + "-" + dias;
            fechaf = "" + años + "-" + mess + "-" + dias;
            
//            Calendar cal = new GregorianCalendar();
//            cal.add(Calendar.DAY_OF_MONTH, 1);

//            int mes2 = cal.get(Calendar.MONTH) + 1;
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            
            //String fechaf2 = cal.get(Calendar.YEAR) + "-" + mes2 + "-" + cal.get(Calendar.DAY_OF_MONTH);
            //String sql = "select producto.precioventa,producto.cantidad,producto.idProducto,producto.Codigo,producto.nombre,proveedor.nombre from producto INNER JOIN proveedor ON proveedor_idProveedor=idProveedor where nombre like '%" + busqueda.getText() + "%'and idproducto!='1'";
            //String idusu=""+Login.idusu();
            musuario musuario = (musuario) cusuario.getSelectedItem();
            String idusu = musuario.getID();//, proveedores = proveedor.toString();
                    
            String sql = "select salida.total,salida.fecha from salida  where salida.fecha BETWEEN '" + fechai + "' AND '" + fechaf + "' and salida.tipopago_idtipopago='1' and salida.usuario_idusuario="+idusu;
            ResultSet rs = s.executeQuery(sql);
            float suma = 0;
            while (rs.next()) {
                suma = suma + rs.getFloat("salida.total");
                //System.out.println(suma);

            }

//            sql = "select salida.total from salida  where salida.fecha BETWEEN '" + fechai + "' AND '" + fechaf2 + "' and salida.tipopago_idtipopago>1";
//            ResultSet rs1 = s.executeQuery(sql);
//            float suma1 = 0;
//            while (rs1.next()) {
//                suma1 = suma1 + rs1.getFloat("salida.total");
//
//            }
            String cliente = "CLIENTE";

            sql = "select xcobrarclientes.monto,xcobrarclientes.fecha from xcobrarclientes where xcobrarclientes.fecha BETWEEN '" + fechai + "' AND '" + fechaf + "' and xcobrarclientes.tipopago='Contado' and xcobrarclientes.usuario_idusuario="+idusu;
            ResultSet rs6 = s.executeQuery(sql);
            float suma6 = 0;
            while (rs6.next()) {
                suma6 = suma6 + rs6.getFloat("xcobrarclientes.monto");

            }
            abonosefectivo.setText("" + suma6);
            
            
            sql = "select xcobrarclientes.monto,xcobrarclientes.fecha,salida.fecha from xcobrarclientes INNER JOIN salida ON xcobrarclientes.salida_idsalida = salida.idsalida  where salida.fecha BETWEEN '" + fechai + "' AND '" + fechaf + "' and xcobrarclientes.monto < 0 and xcobrarclientes.usuario_idusuario="+idusu;
            ResultSet rs9= s.executeQuery(sql);
            float suma9 = 0;
            while (rs9.next()) {
                suma9 = suma9 + rs9.getFloat("xcobrarclientes.monto");
            }
            suma9=Math.abs(suma9);
            
            
            
            sql = "select xcobrarclientes.monto,xcobrarclientes.fecha from xcobrarclientes where xcobrarclientes.fecha BETWEEN '" + fechai + "' AND '" + fechaf + "' and xcobrarclientes.monto < 0 and xcobrarclientes.usuario_idusuario="+idusu;
            ResultSet rs7= s.executeQuery(sql);
            float suma7 = 0;
            while (rs7.next()) {
                suma7 = suma7 + rs7.getFloat("xcobrarclientes.monto");
            }
            suma7=Math.abs(suma7);
            devefectivo2.setText("" + suma7);
            
            sql = "SELECT salida.tipopago_idtipopago,salida.fecha,devoluciones.subtotal,devoluciones.idcompra ,devoluciones.fecha ,devoluciones.entradasalida FROM devoluciones INNER JOIN salida ON devoluciones.idcompra = salida.idsalida  where salida.fecha BETWEEN '" + fechai + "' AND '" + fechaf + "' and devoluciones.entradasalida='" + cliente + "'" + " and salida.tipopago_idtipopago=1 and salida.usuario_idusuario="+idusu;
            ResultSet rs8 = s.executeQuery(sql);
            float suma8 = 0;
            while (rs8.next()) {
                suma8 = suma8 + rs8.getFloat("devoluciones.subtotal");
            }
            
            
            sql = "SELECT salida.tipopago_idtipopago,devoluciones.subtotal,devoluciones.idcompra ,devoluciones.fecha ,devoluciones.entradasalida FROM devoluciones INNER JOIN salida ON devoluciones.idcompra = salida.idsalida  where devoluciones.fecha BETWEEN '" + fechai + "' AND '" + fechaf + "' and devoluciones.entradasalida='" + cliente + "'" + " and salida.tipopago_idtipopago=1 and salida.usuario_idusuario="+idusu;
            ResultSet rs2 = s.executeQuery(sql);
            float suma2 = 0;
            float resta = 0;
            //java.util.Date fecha1 = sdf.parse(fechai, new ParsePosition(0));
            while (rs2.next()) {
                suma2 = suma2 + rs2.getFloat("devoluciones.subtotal");
            }
            totalefectivo.setText("" + (suma + suma8 +suma9));
            devefectivo.setText("" + suma2);
            efectivocaja.setText("" + ((suma + suma6 + suma8 + suma9) - (suma2+suma7)));
            conexion.close();

        } catch (Exception ex) {

            Logger.getLogger(frmventas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed
    public void reporte(String id, String ids, String id3) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Fincial;
    private javax.swing.JLabel abonosefectivo;
    private javax.swing.JComboBox cusuario;
    private javax.swing.JLabel devefectivo;
    private javax.swing.JLabel devefectivo2;
    private javax.swing.JLabel efectivocaja;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private jcMousePanel.jcMousePanel jcMousePanel1;
    private javax.swing.JPanel pnlPaginador;
    private javax.swing.JLabel totalefectivo;
    private javax.swing.JLabel totalventa;
    // End of variables declaration//GEN-END:variables
}
