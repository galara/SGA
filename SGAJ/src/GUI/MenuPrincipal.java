/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

//import BD.LeePropiedades;
//import eliminar.ventacredito;
import BD.BdConexion;
import Modelos.AccesoUsuario;
import Modelos.AddForms;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import reporte.*;

/**
 *
 * @author Glara
 */
public class MenuPrincipal extends javax.swing.JFrame {

    //private String archivoRecurso = "controlador-bd";
    //Connection conn;//getConnection intentara establecer una conexión.
    java.sql.Connection conn;//getConnection intentara establecer una conexión.
    /**
     * Creates new form principal
     */
    public MenuPrincipal() {

        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        //panel_center.setBorder(new bodega.ImagenFondo());
        this.setIconImage(new ImageIcon(getClass().getResource("/recursos/mi logo.png")).getImage());
        activar();
    }

    private void cerrarVentana() {
        int count = panel_center.getComponentCount();
        if (count == 0) {
            int nu = JOptionPane.showConfirmDialog(this, "¿Desea Cerrar esta ventana?", "Cerrar ventana", JOptionPane.YES_NO_OPTION);

            if (nu == JOptionPane.YES_OPTION || nu == 0) {
                System.exit(0);
            } else {
            }
        } else if (count > 0) {
            JOptionPane.showMessageDialog(null, "Para cerrar el Systema primero debe cerrar los formularios abiertos " + "( " + count + " )");
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

        panel_south = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        usuarioactual = new javax.swing.JLabel();
        panel_center = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        m1 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        m2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        m3 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        m4 = new javax.swing.JMenuItem();
        m5 = new javax.swing.JMenuItem();
        m6 = new javax.swing.JMenuItem();
        m7 = new javax.swing.JMenuItem();
        m8 = new javax.swing.JMenuItem();
        m9 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        m10 = new javax.swing.JMenuItem();
        m11 = new javax.swing.JMenuItem();
        m12 = new javax.swing.JMenuItem();
        m13 = new javax.swing.JMenuItem();
        m14 = new javax.swing.JMenuItem();
        m15 = new javax.swing.JMenuItem();
        m16 = new javax.swing.JMenuItem();
        m17 = new javax.swing.JMenuItem();
        m18 = new javax.swing.JMenu();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        m19 = new javax.swing.JMenuItem();
        m20 = new javax.swing.JMenuItem();
        m21 = new javax.swing.JMenuItem();
        m22 = new javax.swing.JMenuItem();
        m23 = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        acercade = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sistema de Gestion de Almacenes");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panel_south.setBackground(new java.awt.Color(204, 219, 255));
        panel_south.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_south.setForeground(new java.awt.Color(255, 255, 255));
        panel_south.setPreferredSize(new java.awt.Dimension(649, 37));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Usuario :");

        //GUI.Login entrar = new GUI.Login();
        //      usuarioactual.setText(entrar.usuario());
        usuarioactual.setText(AccesoUsuario.getUsuario());
        usuarioactual.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        usuarioactual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panel_southLayout = new javax.swing.GroupLayout(panel_south);
        panel_south.setLayout(panel_southLayout);
        panel_southLayout.setHorizontalGroup(
            panel_southLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_southLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usuarioactual, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(346, Short.MAX_VALUE))
        );
        panel_southLayout.setVerticalGroup(
            panel_southLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(usuarioactual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_southLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(panel_south, java.awt.BorderLayout.SOUTH);

        panel_center.setBackground(new java.awt.Color(153, 180, 209));
        getContentPane().add(panel_center, java.awt.BorderLayout.CENTER);

        jMenuBar1.setBackground(new java.awt.Color(204, 219, 255));

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/home.png"))); // NOI18N
        jMenu1.setMnemonic('A');
        jMenu1.setText("Archivo");

        m1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/user.png"))); // NOI18N
        m1.setText("Crear Usuario");
        m1.setEnabled(false);
        m1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m1ActionPerformed(evt);
            }
        });
        jMenu1.add(m1);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/close.png"))); // NOI18N
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/transacciones.png"))); // NOI18N
        jMenu2.setMnemonic('O');
        jMenu2.setText("Movimientos");

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/salida.png"))); // NOI18N
        jMenu3.setText("Salidas");

        m2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        m2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/ordenS.png"))); // NOI18N
        m2.setText("ventas");
        m2.setEnabled(false);
        m2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m2ActionPerformed(evt);
            }
        });
        jMenu3.add(m2);

        jMenu2.add(jMenu3);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/producto.png"))); // NOI18N
        jMenu4.setText("Ingresos");

        m3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        m3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/ingresoSP.png"))); // NOI18N
        m3.setText("Compras");
        m3.setEnabled(false);
        m3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m3ActionPerformed(evt);
            }
        });
        jMenu4.add(m3);

        jMenu2.add(jMenu4);

        jMenuBar1.add(jMenu2);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/mantenimiento.png"))); // NOI18N
        jMenu6.setMnemonic('M');
        jMenu6.setText("Mantenimiento");

        m4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        m4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/categoria.png"))); // NOI18N
        m4.setText("Categoria de Productos");
        m4.setEnabled(false);
        m4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m4ActionPerformed(evt);
            }
        });
        jMenu6.add(m4);

        m5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        m5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/producto.png"))); // NOI18N
        m5.setText("Unidad de Medida de Productos");
        m5.setEnabled(false);
        m5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m5ActionPerformed(evt);
            }
        });
        jMenu6.add(m5);

        m6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        m6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/newproducto.png"))); // NOI18N
        m6.setText("Marca de Producto");
        m6.setEnabled(false);
        m6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m6ActionPerformed(evt);
            }
        });
        jMenu6.add(m6);

        m7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        m7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/proveedor.png"))); // NOI18N
        m7.setText("Proveedores");
        m7.setEnabled(false);
        m7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m7ActionPerformed(evt);
            }
        });
        jMenu6.add(m7);

        m8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        m8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/productos.png"))); // NOI18N
        m8.setText("Productos");
        m8.setEnabled(false);
        m8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m8ActionPerformed(evt);
            }
        });
        jMenu6.add(m8);

        m9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        m9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/addclient.png"))); // NOI18N
        m9.setText("Clientes");
        m9.setEnabled(false);
        m9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m9ActionPerformed(evt);
            }
        });
        jMenu6.add(m9);

        jMenuBar1.add(jMenu6);

        jMenu11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/report.png"))); // NOI18N
        jMenu11.setText("Reportes");

        m10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/reportsalida.png"))); // NOI18N
        m10.setText("Venta");
        m10.setEnabled(false);
        m10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m10ActionPerformed(evt);
            }
        });
        jMenu11.add(m10);

        m11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/reportsalida.png"))); // NOI18N
        m11.setText("Venta Contado");
        m11.setEnabled(false);
        m11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m11ActionPerformed(evt);
            }
        });
        jMenu11.add(m11);

        m12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/reportsalida.png"))); // NOI18N
        m12.setText("Venta Credito");
        m12.setEnabled(false);
        m12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m12ActionPerformed(evt);
            }
        });
        jMenu11.add(m12);

        m13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/inventario3.png"))); // NOI18N
        m13.setText("Venta-Ganancia");
        m13.setEnabled(false);
        m13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m13ActionPerformed(evt);
            }
        });
        jMenu11.add(m13);

        m14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/inventario1.png"))); // NOI18N
        m14.setText("Inventario por Lotes");
        m14.setEnabled(false);
        m14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m14ActionPerformed(evt);
            }
        });
        jMenu11.add(m14);

        m15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/inventario1.png"))); // NOI18N
        m15.setText("Inventario General");
        m15.setEnabled(false);
        m15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m15ActionPerformed(evt);
            }
        });
        jMenu11.add(m15);

        m16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/comprometido.png"))); // NOI18N
        m16.setText("Pedido-compra");
        m16.setEnabled(false);
        m16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m16ActionPerformed(evt);
            }
        });
        jMenu11.add(m16);

        m17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/reportecompras.png"))); // NOI18N
        m17.setText("Compras");
        m17.setEnabled(false);
        m17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m17ActionPerformed(evt);
            }
        });
        jMenu11.add(m17);

        m18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/print_64.png"))); // NOI18N
        m18.setText("Re-Impresiones");
        m18.setEnabled(false);

        jMenuItem21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/salida.png"))); // NOI18N
        jMenuItem21.setText("Salida de productos");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        m18.add(jMenuItem21);

        jMenuItem22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/ingresoCP.png"))); // NOI18N
        jMenuItem22.setText("Ingreso de productos");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        m18.add(jMenuItem22);

        jMenu11.add(m18);

        m19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/report.png"))); // NOI18N
        m19.setText("Corte");
        m19.setEnabled(false);
        m19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m19ActionPerformed(evt);
            }
        });
        jMenu11.add(m19);

        m20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/comprometido.png"))); // NOI18N
        m20.setText("Creditos Vencidos(Ventas)");
        m20.setEnabled(false);
        m20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m20ActionPerformed(evt);
            }
        });
        jMenu11.add(m20);

        m21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/comprometido.png"))); // NOI18N
        m21.setText("Creditos Vencidos(Compras)");
        m21.setEnabled(false);
        m21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m21ActionPerformed(evt);
            }
        });
        jMenu11.add(m21);

        m22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/report.png"))); // NOI18N
        m22.setText("Corte Usuario");
        m22.setEnabled(false);
        m22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m22ActionPerformed(evt);
            }
        });
        jMenu11.add(m22);

        m23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/inventario3.png"))); // NOI18N
        m23.setText("Dias de atraso");
        m23.setEnabled(false);
        m23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m23ActionPerformed(evt);
            }
        });
        jMenu11.add(m23);

        jMenuBar1.add(jMenu11);

        jMenu12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/ayuda.png"))); // NOI18N
        jMenu12.setText("Ayuda");

        acercade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/acercade2.png"))); // NOI18N
        acercade.setText("Acerca de");
        acercade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acercadeActionPerformed(evt);
            }
        });
        jMenu12.add(acercade);

        jMenuBar1.add(jMenu12);

        setJMenuBar(jMenuBar1);

        getAccessibleContext().setAccessibleName("Sistema Gestion de Almacenes");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void m1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m1ActionPerformed
        // TODO add your handling code here:
        Usuarios newfrm = new Usuarios();
        if (newfrm == null ) {
            newfrm = new Usuarios();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        cerrarVentana();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void m14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m14ActionPerformed
        // TODO add your handling code here:
        String nombrereporte = "Inventario2.jasper";
        GeneraReportes.AbrirReporte(nombrereporte, null);
        //new stock2().setVisible(true);
    }//GEN-LAST:event_m14ActionPerformed

    private void m17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m17ActionPerformed
        // TODO add your handling code here:
        ReporteDeCompras newfrm = new ReporteDeCompras();
        if (newfrm == null) {
            newfrm = new ReporteDeCompras();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m17ActionPerformed

    private void m3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m3ActionPerformed
        // TODO add your handling code here:
        compras newfrm = new compras();
        if (newfrm == null) {
            newfrm = new compras();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m3ActionPerformed
    
    public void activar() {
        try {
            conn = BdConexion.getConexion();
            // Se crea un Statement, para realizar la consulta
            com.mysql.jdbc.Statement s = (com.mysql.jdbc.Statement) conn.createStatement();
            String sql = "select estado,menu from perfilusu where idusuario='" + AccesoUsuario.idusu() + "' ";
            // Se realiza la consulta. Los resultados se guardan en el 
            // ResultSet rs
            ResultSet rs = s.executeQuery(sql);

            // Se recorre el ResultSet, mostrando por pantalla los resultados.
            while (rs.next()) {
                if (rs.getString("menu").equals("0") && rs.getString("estado").equals("T")) {
                    m1.setEnabled(true);
                }
                if (rs.getString("menu").equals("1") && rs.getString("estado").equals("T")) {
                    m2.setEnabled(true);
                }
                if (rs.getString("menu").equals("2") && rs.getString("estado").equals("T")) {
                    m3.setEnabled(true);
                }
                if (rs.getString("menu").equals("3") && rs.getString("estado").equals("T")) {
                    m4.setEnabled(true);
                }
                if (rs.getString("menu").equals("4") && rs.getString("estado").equals("T")) {
                    m5.setEnabled(true);
                }
                if (rs.getString("menu").equals("5") && rs.getString("estado").equals("T")) {
                    m6.setEnabled(true);
                }
                if (rs.getString("menu").equals("6") && rs.getString("estado").equals("T")) {
                    m7.setEnabled(true);
                }
                if (rs.getString("menu").equals("7") && rs.getString("estado").equals("T")) {
                    m8.setEnabled(true);
                }
                if (rs.getString("menu").equals("8") && rs.getString("estado").equals("T")) {
                    m9.setEnabled(true);
                }
                if (rs.getString("menu").equals("9") && rs.getString("estado").equals("T")) {
                    m10.setEnabled(true);
                }
                if (rs.getString("menu").equals("10") && rs.getString("estado").equals("T")) {
                    m11.setEnabled(true);
                }
                if (rs.getString("menu").equals("11") && rs.getString("estado").equals("T")) {
                    m12.setEnabled(true);
                }
                if (rs.getString("menu").equals("12") && rs.getString("estado").equals("T")) {
                    m13.setEnabled(true);
                }
                if (rs.getString("menu").equals("13") && rs.getString("estado").equals("T")) {
                    m14.setEnabled(true);
                }
                if (rs.getString("menu").equals("14") && rs.getString("estado").equals("T")) {
                    m15.setEnabled(true);
                }
                if (rs.getString("menu").equals("15") && rs.getString("estado").equals("T")) {
                    m16.setEnabled(true);
                }
                if (rs.getString("menu").equals("16") && rs.getString("estado").equals("T")) {
                    m17.setEnabled(true);
                }
                if (rs.getString("menu").equals("17") && rs.getString("estado").equals("T")) {
                    m18.setEnabled(true);
                }
                if (rs.getString("menu").equals("18") && rs.getString("estado").equals("T")) {
                    m19.setEnabled(true);
                }
                if (rs.getString("menu").equals("19") && rs.getString("estado").equals("T")) {
                    m20.setEnabled(true);
                }
                if (rs.getString("menu").equals("20") && rs.getString("estado").equals("T")) {
                    m21.setEnabled(true);
                }
                if (rs.getString("menu").equals("21") && rs.getString("estado").equals("T")) {
                    m22.setEnabled(true);
                }
                if (rs.getString("menu").equals("22") && rs.getString("estado").equals("T")) {
                    m23.setEnabled(true);
                }
            }
            // Se cierra la conexión con la base de datos.
            //conexion.close();
        } catch (NullPointerException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void acercadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acercadeActionPerformed
        // TODO add your handling code here:
        acercade newfrm = new acercade();
        if (newfrm == null) {
            newfrm = new acercade();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_acercadeActionPerformed

    private void m10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m10ActionPerformed
        // TODO add your handling code here:
        ReporteDeVentas newfrm = new ReporteDeVentas();
        if (newfrm == null) {
            newfrm = new ReporteDeVentas();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m10ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
        Rmovimientos newfrm = new Rmovimientos();
        if (newfrm == null) {
            newfrm = new Rmovimientos();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
        Rmovimientos2 newfrm = new Rmovimientos2();
        if (newfrm == null) {
            newfrm = new Rmovimientos2();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void m15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m15ActionPerformed
        // TODO add your handling code here:
        String nombrereporte = "Inventario3.jasper";
        GeneraReportes.AbrirReporte(nombrereporte, null);
        //new exist().setVisible(true);
    }//GEN-LAST:event_m15ActionPerformed

    private void m2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m2ActionPerformed
        // TODO add your handling code here:
        frmventas newfrm = new frmventas();
        if (newfrm == null) {
            newfrm = new frmventas();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m2ActionPerformed

    private void m5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m5ActionPerformed
        // TODO add your handling code here:
        unidadmedprod newfrm = new unidadmedprod();
        if (newfrm == null) {
            newfrm = new unidadmedprod();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m5ActionPerformed

    private void m6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m6ActionPerformed
        // TODO add your handling code here:
        marcaprod newfrm = new marcaprod();
        if (newfrm == null) {
            newfrm = new marcaprod();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m6ActionPerformed

    private void m9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m9ActionPerformed
        // TODO add your handling code here:
        Cliente newfrm = new Cliente();
        if (newfrm == null) {
            newfrm = new Cliente();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m9ActionPerformed

    private void m7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m7ActionPerformed
        // TODO add your handling code here:
        Proveedores newfrm = new Proveedores();
        if (newfrm == null) {
            newfrm = new Proveedores();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m7ActionPerformed

    private void m8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m8ActionPerformed
        // TODO add your handling code here:
        Producto newfrm = new Producto();
        if (newfrm == null) {
            newfrm = new Producto();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m8ActionPerformed

    private void m4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m4ActionPerformed
        // TODO add your handling code here:
        Categoriaproducto newfrm = new Categoriaproducto();
        if (newfrm == null) {
            newfrm = new Categoriaproducto();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m4ActionPerformed

    private void m13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m13ActionPerformed
        // TODO add your handling code here:
        ReporteGanancia newfrm = new ReporteGanancia();
        if (newfrm == null) {
            newfrm = new ReporteGanancia();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m13ActionPerformed

    private void m16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m16ActionPerformed
        // TODO add your handling code here:
        //new pedidos().setVisible(true);
        String nombrereporte = "Pedido.jasper";
        GeneraReportes.AbrirReporte(nombrereporte, null);
    }//GEN-LAST:event_m16ActionPerformed

    private void m12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m12ActionPerformed
        // TODO add your handling code here:
        ReporteDeVentasCredito newfrm = new ReporteDeVentasCredito();
        if (newfrm == null) {
            newfrm = new ReporteDeVentasCredito();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m12ActionPerformed

    private void m11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m11ActionPerformed
        // TODO add your handling code here:
        ReporteDeVentasContado newfrm = new ReporteDeVentasContado();
        if (newfrm == null) {
            newfrm = new ReporteDeVentasContado();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m11ActionPerformed

    private void m20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m20ActionPerformed
        // TODO add your handling code here:
        ReporteVentasOpciones newfrm = new ReporteVentasOpciones();
        if (newfrm == null) {
            newfrm = new ReporteVentasOpciones();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m20ActionPerformed

    private void m21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m21ActionPerformed
        // TODO add your handling code here:
        ReporteComprasOpciones newfrm = new ReporteComprasOpciones();
        if (newfrm == null) {
            newfrm = new ReporteComprasOpciones();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m21ActionPerformed

    private void m19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m19ActionPerformed
        // TODO add your handling code here:
        cortecaja1 newfrm = new cortecaja1();
        if (newfrm == null) {
            newfrm = new cortecaja1();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m19ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        cerrarVentana();
    }//GEN-LAST:event_formWindowClosing

    private void m22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m22ActionPerformed
        // TODO add your handling code here:
        cortecajausuario newfrm = new cortecajausuario();
        if (newfrm == null) {
            newfrm = new cortecajausuario();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m22ActionPerformed

    private void m23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m23ActionPerformed
        // TODO add your handling code here:
        ReporteDias_atraso newfrm = new ReporteDias_atraso();
        if (newfrm == null) {
            newfrm = new ReporteDias_atraso();
        }
        AddForms.adminInternalFrame(panel_center, newfrm);
    }//GEN-LAST:event_m23ActionPerformed

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
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new MenuPrincipal().setVisible(true);

            }
        }
        );
        // new MenuPrincipal().setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem acercade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem m1;
    private javax.swing.JMenuItem m10;
    private javax.swing.JMenuItem m11;
    private javax.swing.JMenuItem m12;
    private javax.swing.JMenuItem m13;
    private javax.swing.JMenuItem m14;
    private javax.swing.JMenuItem m15;
    private javax.swing.JMenuItem m16;
    private javax.swing.JMenuItem m17;
    private javax.swing.JMenu m18;
    private javax.swing.JMenuItem m19;
    private javax.swing.JMenuItem m2;
    private javax.swing.JMenuItem m20;
    private javax.swing.JMenuItem m21;
    private javax.swing.JMenuItem m22;
    private javax.swing.JMenuItem m23;
    private javax.swing.JMenuItem m3;
    private javax.swing.JMenuItem m4;
    private javax.swing.JMenuItem m5;
    private javax.swing.JMenuItem m6;
    private javax.swing.JMenuItem m7;
    private javax.swing.JMenuItem m8;
    private javax.swing.JMenuItem m9;
    public static javax.swing.JDesktopPane panel_center;
    public static javax.swing.JPanel panel_south;
    private javax.swing.JLabel usuarioactual;
    // End of variables declaration//GEN-END:variables

//    public void abrirInternalFrame(JInternalFrame frminternas) {
//        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
////obtenemos el tamaño de la ventana
//        Dimension ventana = frminternas.getSize();
////para centrar la ventana lo hacemos con el siguiente calculo
//
//        frminternas.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 5 /*10*/);
////y para finalizar la hacemos visible
////frmventas.setVisible(true);
//        panel_center.add(frminternas);
//        frminternas.show();// ver interno
//        frminternas.setClosable(true);// icono de cerrar
//        frminternas.toFront();//aparece al frente
//        frminternas.setMaximizable(true);//icono de maximizar
//
//    }
}
