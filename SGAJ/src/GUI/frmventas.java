/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmventa.java
 *
 * Created on 22-oct-2012, 19:01:56
 */
package GUI;

import BD.BdConexion;
import BD.sqlprod;
import static GUI.MenuPrincipal.panel_center;
import Modelos.AccesoUsuario;
import Modelos.MiModelo;
import Modelos.Utilidades;
import Modelos.codigoproductocombo;
import Modelos.formadepago;
import com.mysql.jdbc.Statement;
import excepciones.FormatoDecimal;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import reporte.GeneraReportes;
import reporte.imprimiendo;

/**
 *
 * @author Otto
 */
public class frmventas extends javax.swing.JInternalFrame {

    private AudioClip sonido;

    private String archivoRecurso = "controlador-bd";
    public static String idfactura = "", fechasalida = "";
    public static String fecha = "";
    java.sql.Connection conn;//getConnection intentara establecer una conexión.
    int idabono = 0;
    public static float montoabonado = 0, saldoventa = 0, saldototalc = 0;

    /**
     * Creates new form frmventa
     */
    public frmventas() {
        initComponents();
        llenarcombo();
        llenarcombocodigo();

        codigoproductos.addItemListener(
                (ItemEvent e) -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        //selecciongrupo();
                        seleccionnombreproducto();
                    }
                });

        addEscapeKey();
        for (int i = 0; i <= 7; i++) {
            if ((i == 0) || (i == 3) || (i == 06) || (i == 7)) {
                TableColumn desaparece = tablaventas.getColumnModel().getColumn(i);
                desaparece.setMaxWidth(0);
                desaparece.setMinWidth(0);
                desaparece.setPreferredWidth(0);
                tablaventas.doLayout();
            }
            if ((i == 2) || (i == 4) || (i == 5)) {
                TableColumn desaparece = tablaventas.getColumnModel().getColumn(i);
                desaparece.setMaxWidth(90);
                desaparece.setMinWidth(90);
                desaparece.setPreferredWidth(90);
                tablaventas.doLayout();
            }
        }
        tablaventas.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent arg0) {
                int key = arg0.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                    eliminaarticulo();
                }
            }
        });
        busquedaproducto.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    DefaultTableModel temp = (DefaultTableModel) busquedaproducto.getModel();
                    descricionP.setText("" + temp.getValueAt(busquedaproducto.getSelectedRow(), 5));
                }
            }
        });
        busquedacliente.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent arg0) {
                int key = arg0.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                    DefaultTableModel temp = (DefaultTableModel) busquedacliente.getModel();
                    //temp.removeRow(temp.removeRow(tablita.getSelectedRow()););
                    //decmodifica.setText(temp.getValueAt(tablamodifica.getSelectedRow(), 4).toString())
                    idcliente.setText(temp.getValueAt(busquedacliente.getSelectedRow(), 0).toString());
                    nombrecliente.setText(temp.getValueAt(busquedacliente.getSelectedRow(), 1).toString());
                    //System.out.println(temp.getValueAt(busquedacliente.getSelectedRow(), 1));
                    if (temp.getValueAt(busquedacliente.getSelectedRow(), 4) == null) {
                        nittxt.setText("C/F");
                    } else {
                        nittxt.setText(temp.getValueAt(busquedacliente.getSelectedRow(), 4).toString());
                    }
                    direccion.setText(temp.getValueAt(busquedacliente.getSelectedRow(), 2).toString());
                    tele.setText(temp.getValueAt(busquedacliente.getSelectedRow(), 3).toString());
                    cliente.setVisible(false);

                }
            }
        });

        busquedaproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent arg0) {
                int key = arg0.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_ENTER) {
                    int p = busquedaproducto.getSelectedRow();
                    float C = 0;
                    DefaultTableModel temps = (DefaultTableModel) busquedaproducto.getModel();

                    String Cant = JOptionPane.showInputDialog(null, "ingresa Cantidad Venta Producto");
                    if (Cant.isEmpty()) {

                    } else {
                        C = Float.parseFloat(Cant);
                        if (C <= Float.parseFloat(temps.getValueAt(p, 3).toString())) {
                            idproducto.setText("" + temps.getValueAt(p, 0));
                            cantidadP.setText(C + "");
                            existencia.setText("" + temps.getValueAt(p, 3));
                            guardarventa();
                            busqueda.requestFocus();
                            busqueda.selectAll();
                        }
                    }
                }
            }
        });

    }

    private void addEscapeKey() {
        KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (tablaventas.getRowCount() <= 0) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No Puedes cerrar Tienes Ventas Pendientes");
                }
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
    }

    public void llenarcombo() {
        DefaultComboBoxModel value2;
        value2 = new DefaultComboBoxModel();
        formapago.setModel(value2);
        try {
            conn = BdConexion.getConexion();
            //conn = BdConexion.getConexion();
            Statement s = (Statement) conn.createStatement();
            ResultSet rs = s.executeQuery(sqlprod.COMBOTP);

            while (rs.next()) {
                value2.addElement(new formadepago(rs.getString("descripcion"), rs.getInt("dias"), "" + rs.getInt("idtipopago")));
            }
            ////conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurio un Error al cargar los datos\n" + ex.toString());
        }
    }

    public void llenarcombocodigo() {
        DefaultComboBoxModel value;
        value = new DefaultComboBoxModel();
        codigoproductos.setModel(value);
        try {
            conn = BdConexion.getConexion();
            Statement s = (Statement) conn.createStatement();
            ResultSet rs = s.executeQuery("select idproducto,codigo from producto");
            value.addElement(new codigoproductocombo(" ", "0"));

            while (rs.next()) {
                value.addElement(new codigoproductocombo(rs.getString("codigo"), "" + rs.getInt("idproducto")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurio un Error al cargar los datos\n" + ex.toString());
        }
    }

    public void seleccionnombreproducto() {
        if (codigoproductos.getSelectedIndex() > 0) {
            try {
                conn = BdConexion.getConexion();
                Statement s = (Statement) conn.createStatement();
                ResultSet rs = s.executeQuery("SELECT producto.nombre,unidad.Nombre FROM unidad INNER JOIN producto ON unidad.idunidad = producto.idunidad where codigo='" + codigoproductos.getSelectedItem() + "'");

                while (rs.next()) {
                    nombreproducto.setText(rs.getString("producto.nombre"));
                    unidadproducto.setText(rs.getString("unidad.nombre"));
                }
                rs.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocurio un Error al cargar los datos\n" + ex.toString());
            }

        } else if (codigoproductos.getSelectedIndex() == 0) {
            nombreproducto.setText("");
            unidadproducto.setText("");
            //nombreproducto.setText("");
            existencia.setText("");
            precios.setText("");
            //unidadproducto.setText("");
        }
    }

    public static float montoabonado() {

        return montoabonado;
    }

    public static float saldoventa() {

        return saldoventa;
    }

    public static float saldototalc() {

        return saldototalc;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regeneratd by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cliente = new javax.swing.JDialog();
        jcMousePanel1 = new jcMousePanel.jcMousePanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        busquedacliente = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jcMousePanel14 = new jcMousePanel.jcMousePanel();
        jLabel3 = new javax.swing.JLabel();
        buscacliente = new elaprendiz.gui.textField.TextFieldRectIcon();
        jcMousePanel17 = new jcMousePanel.jcMousePanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        nitn = new javax.swing.JTextField();
        nombren = new javax.swing.JTextField();
        telefonon = new javax.swing.JTextField();
        direccionn = new javax.swing.JTextField();
        Mensaje = new elaprendiz.gui.label.LabelRect();
        jButton2 = new javax.swing.JButton();
        cancelar = new elaprendiz.gui.button.ButtonRect();
        diascredito = new javax.swing.JDialog();
        jcMousePanel16 = new jcMousePanel.jcMousePanel();
        jLabel14 = new javax.swing.JLabel();
        cantdias = new javax.swing.JTextField();
        buscar = new javax.swing.JDialog();
        jcMousePanel12 = new jcMousePanel.jcMousePanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        busquedaproducto = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        busqueda = new elaprendiz.gui.textField.TextFieldRectIcon();
        bntSalir1 = new elaprendiz.gui.button.ButtonRect();
        descricionP = new javax.swing.JLabel();
        pago = new javax.swing.JDialog();
        jcMousePanel13 = new jcMousePanel.jcMousePanel();
        cantpago = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        precio = new javax.swing.JDialog();
        jcMousePanel15 = new jcMousePanel.jcMousePanel();
        btnbusscar2 = new elaprendiz.gui.button.ButtonRect();
        btnbusscar3 = new elaprendiz.gui.button.ButtonRect();
        Actual = new elaprendiz.gui.label.LabelRect();
        labelRect2 = new elaprendiz.gui.label.LabelRect();
        labelRect3 = new elaprendiz.gui.label.LabelRect();
        Cprecio = new javax.swing.JTextField();
        ps = new elaprendiz.gui.label.LabelRect();
        Ccantidad = new elaprendiz.gui.label.LabelRect();
        costo = new elaprendiz.gui.label.LabelRect();
        iddcambio = new elaprendiz.gui.label.LabelRect();
        abono = new javax.swing.JDialog();
        jcMousePanel6 = new jcMousePanel.jcMousePanel();
        jLabel4 = new javax.swing.JLabel();
        idcompra = new javax.swing.JTextField();
        montoabono = new javax.swing.JFormattedTextField();
        dcFecha = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        pnlActionButtons1 = new javax.swing.JPanel();
        butnguardarabono = new elaprendiz.gui.button.ButtonRect();
        btncancelarabono = new elaprendiz.gui.button.ButtonRect();
        btncancelarabono1 = new elaprendiz.gui.button.ButtonRect();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        observacionabono = new javax.swing.JTextArea();
        formapagoabono = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        efectivo = new javax.swing.JFormattedTextField();
        jLabel29 = new javax.swing.JLabel();
        jcMousePanel4 = new jcMousePanel.jcMousePanel();
        jcMousePanel3 = new jcMousePanel.jcMousePanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        fechainicial = new org.freixas.jcalendar.JCalendarCombo();
        nombrecliente = new javax.swing.JLabel();
        nittxt = new javax.swing.JLabel();
        idproducto = new javax.swing.JTextField();
        idfac = new javax.swing.JTextField();
        idcliente = new javax.swing.JTextField();
        tele = new javax.swing.JTextField();
        direccion = new javax.swing.JTextField();
        precioscostos = new javax.swing.JTextField();
        jcMousePanel2 = new jcMousePanel.jcMousePanel();
        jLabel15 = new javax.swing.JLabel();
        cantidadP = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        nombreproducto = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        existencia = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        precios = new javax.swing.JTextField();
        bntSalir = new elaprendiz.gui.button.ButtonRect();
        jLabel21 = new javax.swing.JLabel();
        comboprecio = new javax.swing.JComboBox();
        codigoproductos = new javax.swing.JComboBox();
        unidadproducto = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        btncrearproducto1 = new elaprendiz.gui.button.ButtonRect();
        jLabel11 = new javax.swing.JLabel();
        formapago = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaventas = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        TotalPagar = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtarticulos = new javax.swing.JLabel();
        iniciar = new elaprendiz.gui.button.ButtonAction();
        jcMousePanel9 = new jcMousePanel.jcMousePanel();
        jLabel22 = new javax.swing.JLabel();
        Nodefac = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        clockDigital1 = new elaprendiz.gui.varios.ClockDigital();
        jButton4 = new javax.swing.JButton();
        jcMousePanel11 = new jcMousePanel.jcMousePanel();
        elart = new elaprendiz.gui.button.ButtonRect();
        btnmodificar = new elaprendiz.gui.button.ButtonRect();
        btnbusscar = new elaprendiz.gui.button.ButtonRect();
        btnbusscar1 = new elaprendiz.gui.button.ButtonRect();

        cliente.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        cliente.setTitle("ingrese cliente");
        cliente.setAlwaysOnTop(true);
        cliente.setBounds(new java.awt.Rectangle(0, 0, 600, 550));
        cliente.setMinimumSize(null);
        cliente.setModal(true);
        cliente.setUndecorated(true);
        buscacliente.requestFocus();

        jcMousePanel1.setColor1(new java.awt.Color(204, 204, 204));
        jcMousePanel1.setColor2(new java.awt.Color(153, 153, 153));
        jcMousePanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N
        jcMousePanel1.setName(""); // NOI18N
        jcMousePanel1.setOpaque(false);
        jcMousePanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcMousePanel1KeyPressed(evt);
            }
        });

        busquedacliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD", "NOMBRE", "DIRECCIÓN", "TEL", "NIT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(busquedacliente);

        jPanel9.setBackground(new java.awt.Color(61, 139, 189));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Presiona ENTER para seleccionar cliente");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(38, 38, 38))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel10))
        );

        jcMousePanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 0, 12), java.awt.Color.white)); // NOI18N
        jcMousePanel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Busqueda:");

        buscacliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/1390607350_Search.png"))); // NOI18N
        buscacliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscaclienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jcMousePanel14Layout = new javax.swing.GroupLayout(jcMousePanel14);
        jcMousePanel14.setLayout(jcMousePanel14Layout);
        jcMousePanel14Layout.setHorizontalGroup(
            jcMousePanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel14Layout.createSequentialGroup()
                .addContainerGap(114, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buscacliente, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jcMousePanel14Layout.setVerticalGroup(
            jcMousePanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel14Layout.createSequentialGroup()
                .addGroup(jcMousePanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscacliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jcMousePanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente Nuevo", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jcMousePanel17.setForeground(new java.awt.Color(102, 102, 255));
        jcMousePanel17.setColor1(new java.awt.Color(192, 219, 213));
        jcMousePanel17.setColor2(new java.awt.Color(192, 219, 213));
        jcMousePanel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcMousePanel17.setModo(2);
        jcMousePanel17.setName(""); // NOI18N
        jcMousePanel17.setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Nit");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 51, 51));
        jLabel26.setText("Nombres*");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 51, 51));
        jLabel27.setText("Direccion*");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setText("telefono");

        Mensaje.setBackground(new java.awt.Color(102, 102, 102));
        Mensaje.setText("Ingresa Cliente");

        jButton2.setBackground(new java.awt.Color(255, 204, 0));
        jButton2.setText("Crear");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cancelar.setBackground(new java.awt.Color(51, 153, 255));
        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jcMousePanel17Layout = new javax.swing.GroupLayout(jcMousePanel17);
        jcMousePanel17.setLayout(jcMousePanel17Layout);
        jcMousePanel17Layout.setHorizontalGroup(
            jcMousePanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel17Layout.createSequentialGroup()
                .addGroup(jcMousePanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Mensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jcMousePanel17Layout.createSequentialGroup()
                        .addGroup(jcMousePanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jcMousePanel17Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jcMousePanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel26)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel17Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel27)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jcMousePanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(direccionn, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .addComponent(telefonon, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nitn, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombren, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGap(18, 18, 18)
                .addGroup(jcMousePanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jcMousePanel17Layout.setVerticalGroup(
            jcMousePanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel17Layout.createSequentialGroup()
                .addGroup(jcMousePanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jcMousePanel17Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jcMousePanel17Layout.createSequentialGroup()
                        .addComponent(nitn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombren, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(telefonon, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jcMousePanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jcMousePanel17Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jcMousePanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(direccionn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jcMousePanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jcMousePanel17Layout.createSequentialGroup()
                        .addComponent(Mensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel17Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout jcMousePanel1Layout = new javax.swing.GroupLayout(jcMousePanel1);
        jcMousePanel1.setLayout(jcMousePanel1Layout);
        jcMousePanel1Layout.setHorizontalGroup(
            jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcMousePanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jcMousePanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jcMousePanel1Layout.setVerticalGroup(
            jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel1Layout.createSequentialGroup()
                .addComponent(jcMousePanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcMousePanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout clienteLayout = new javax.swing.GroupLayout(cliente.getContentPane());
        cliente.getContentPane().setLayout(clienteLayout);
        clienteLayout.setHorizontalGroup(
            clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        clienteLayout.setVerticalGroup(
            clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        diascredito.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        diascredito.setTitle("pago");
        diascredito.setBounds(new java.awt.Rectangle(200, 200, 250, 80));
        diascredito.setUndecorated(true);

        jcMousePanel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Ingrese Días de crédito y presione ENTER");

        cantdias.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cantdias.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cantdias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cantdiasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jcMousePanel16Layout = new javax.swing.GroupLayout(jcMousePanel16);
        jcMousePanel16.setLayout(jcMousePanel16Layout);
        jcMousePanel16Layout.setHorizontalGroup(
            jcMousePanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jcMousePanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cantdias, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                    .addGroup(jcMousePanel16Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jcMousePanel16Layout.setVerticalGroup(
            jcMousePanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel16Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cantdias, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout diascreditoLayout = new javax.swing.GroupLayout(diascredito.getContentPane());
        diascredito.getContentPane().setLayout(diascreditoLayout);
        diascreditoLayout.setHorizontalGroup(
            diascreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        diascreditoLayout.setVerticalGroup(
            diascreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        buscar.setBounds(new java.awt.Rectangle(100, 100, 710, 430));

        jcMousePanel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Enter Para Agregar Producto"));

        busquedaproducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "codigo", "producto", "cantidad", "Precio V", "Descripcion", "Unidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        busquedaproducto.setRowHeight(20);
        jScrollPane6.setViewportView(busquedaproducto);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Busqueda");

        busqueda.setToolTipText("");
        busqueda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/1390607350_Search.png"))); // NOI18N
        busqueda.setPreferredSize(new java.awt.Dimension(150, 24));
        busqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busquedaActionPerformed(evt);
            }
        });

        bntSalir1.setBackground(new java.awt.Color(102, 204, 0));
        bntSalir1.setText("Salir");
        bntSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSalir1ActionPerformed(evt);
            }
        });

        descricionP.setForeground(new java.awt.Color(255, 255, 255));
        descricionP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        descricionP.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        javax.swing.GroupLayout jcMousePanel12Layout = new javax.swing.GroupLayout(jcMousePanel12);
        jcMousePanel12.setLayout(jcMousePanel12Layout);
        jcMousePanel12Layout.setHorizontalGroup(
            jcMousePanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel12Layout.createSequentialGroup()
                .addGroup(jcMousePanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jcMousePanel12Layout.createSequentialGroup()
                        .addGroup(jcMousePanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jcMousePanel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jcMousePanel12Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jLabel19)
                                .addGap(32, 32, 32)
                                .addComponent(busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(descricionP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel12Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(bntSalir1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jcMousePanel12Layout.setVerticalGroup(
            jcMousePanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jcMousePanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descricionP, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntSalir1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout buscarLayout = new javax.swing.GroupLayout(buscar.getContentPane());
        buscar.getContentPane().setLayout(buscarLayout);
        buscarLayout.setHorizontalGroup(
            buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        buscarLayout.setVerticalGroup(
            buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pago.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        pago.setBounds(new java.awt.Rectangle(300, 300, 250, 80));
        pago.setUndecorated(true);

        jcMousePanel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N

        cantpago.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cantpago.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cantpago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cantpagoActionPerformed(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Ingrese el valor de pago y presione ENTER");

        javax.swing.GroupLayout jcMousePanel13Layout = new javax.swing.GroupLayout(jcMousePanel13);
        jcMousePanel13.setLayout(jcMousePanel13Layout);
        jcMousePanel13Layout.setHorizontalGroup(
            jcMousePanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jcMousePanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cantpago))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jcMousePanel13Layout.setVerticalGroup(
            jcMousePanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel13Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cantpago, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pagoLayout = new javax.swing.GroupLayout(pago.getContentPane());
        pago.getContentPane().setLayout(pagoLayout);
        pagoLayout.setHorizontalGroup(
            pagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pagoLayout.createSequentialGroup()
                .addComponent(jcMousePanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pagoLayout.setVerticalGroup(
            pagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        precio.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        precio.setBounds(new java.awt.Rectangle(0, 0, 300, 125));

        jcMousePanel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N

        btnbusscar2.setBackground(new java.awt.Color(51, 153, 255));
        btnbusscar2.setText("Cambiar");
        btnbusscar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbusscar2ActionPerformed(evt);
            }
        });

        btnbusscar3.setBackground(new java.awt.Color(51, 153, 255));
        btnbusscar3.setText("Cancelar");
        btnbusscar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbusscar3ActionPerformed(evt);
            }
        });

        Actual.setText(".");

        labelRect2.setText("Actual");

        labelRect3.setText("Cambio");

        Cprecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CprecioActionPerformed(evt);
            }
        });

        ps.setText(".");
        ps.setVisible(false);

        Ccantidad.setText(".");
        Ccantidad.setVisible(false);

        costo.setText(".");
        costo.setVisible(false);

        iddcambio.setText(".");
        iddcambio.setVisible(false);

        javax.swing.GroupLayout jcMousePanel15Layout = new javax.swing.GroupLayout(jcMousePanel15);
        jcMousePanel15.setLayout(jcMousePanel15Layout);
        jcMousePanel15Layout.setHorizontalGroup(
            jcMousePanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jcMousePanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel15Layout.createSequentialGroup()
                        .addGap(0, 15, Short.MAX_VALUE)
                        .addGroup(jcMousePanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel15Layout.createSequentialGroup()
                                .addComponent(btnbusscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel15Layout.createSequentialGroup()
                                .addComponent(iddcambio, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(costo, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)))
                        .addGroup(jcMousePanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel15Layout.createSequentialGroup()
                                .addComponent(btnbusscar3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel15Layout.createSequentialGroup()
                                .addComponent(Ccantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ps, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36))))
                    .addGroup(jcMousePanel15Layout.createSequentialGroup()
                        .addGroup(jcMousePanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jcMousePanel15Layout.createSequentialGroup()
                                .addComponent(labelRect3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Cprecio))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel15Layout.createSequentialGroup()
                                .addComponent(labelRect2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Actual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jcMousePanel15Layout.setVerticalGroup(
            jcMousePanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jcMousePanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelRect2, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(Actual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jcMousePanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRect3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jcMousePanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Ccantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(costo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iddcambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jcMousePanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnbusscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbusscar3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout precioLayout = new javax.swing.GroupLayout(precio.getContentPane());
        precio.getContentPane().setLayout(precioLayout);
        precioLayout.setHorizontalGroup(
            precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        precioLayout.setVerticalGroup(
            precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(precioLayout.createSequentialGroup()
                .addComponent(jcMousePanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        abono.setBounds(new java.awt.Rectangle(220, 200, 270, 200));
        abono.setFocusTraversalPolicyProvider(true);
        abono.setMinimumSize(null);
        abono.setUndecorated(true);

        jcMousePanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        jcMousePanel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N
        jcMousePanel6.setMaximumSize(null);
        jcMousePanel6.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Fecha:");
        jcMousePanel6.add(jLabel4);
        jLabel4.setBounds(40, 20, 80, 20);

        idcompra.setVisible(false);
        idcompra.setOpaque(false);
        jcMousePanel6.add(idcompra);
        idcompra.setBounds(30, 40, 10, 20);

        montoabono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        montoabono.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        montoabono.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        montoabono.setName("precioalmayor"); // NOI18N
        montoabono.setPreferredSize(new java.awt.Dimension(80, 23));
        montoabono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                montoabonoKeyReleased(evt);
            }
        });
        jcMousePanel6.add(montoabono);
        montoabono.setBounds(130, 50, 140, 23);

        dcFecha.setDate(Calendar.getInstance().getTime());
        dcFecha.setDateFormatString("dd/MM/yyyy");
        dcFecha.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        dcFecha.setMaxSelectableDate(new java.util.Date(3093496470100000L));
        dcFecha.setMinSelectableDate(new java.util.Date(-62135744300000L));
        dcFecha.setPreferredSize(new java.awt.Dimension(120, 22));
        jcMousePanel6.add(dcFecha);
        dcFecha.setBounds(130, 20, 140, 21);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Monto:");
        jcMousePanel6.add(jLabel5);
        jLabel5.setBounds(40, 50, 80, 20);

        pnlActionButtons1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 255), 1, true));
        pnlActionButtons1.setOpaque(false);
        pnlActionButtons1.setPreferredSize(new java.awt.Dimension(786, 52));
        pnlActionButtons1.setLayout(null);

        butnguardarabono.setBackground(new java.awt.Color(102, 204, 0));
        butnguardarabono.setText("Guardar");
        butnguardarabono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butnguardarabonoActionPerformed(evt);
            }
        });
        pnlActionButtons1.add(butnguardarabono);
        butnguardarabono.setBounds(120, 7, 81, 25);

        btncancelarabono.setBackground(new java.awt.Color(102, 204, 0));
        btncancelarabono.setText("Salir");
        btncancelarabono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarabonoActionPerformed(evt);
            }
        });
        pnlActionButtons1.add(btncancelarabono);
        btncancelarabono.setBounds(300, 7, 81, 25);

        btncancelarabono1.setBackground(new java.awt.Color(102, 204, 0));
        btncancelarabono1.setText("Cancelar");
        btncancelarabono1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarabono1ActionPerformed(evt);
            }
        });
        pnlActionButtons1.add(btncancelarabono1);
        btncancelarabono1.setBounds(210, 7, 81, 25);

        jcMousePanel6.add(pnlActionButtons1);
        pnlActionButtons1.setBounds(0, 150, 500, 40);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Observación:");
        jcMousePanel6.add(jLabel6);
        jLabel6.setBounds(10, 90, 110, 20);

        observacionabono.setColumns(20);
        observacionabono.setRows(5);
        observacionabono.setAutoscrolls(false);
        observacionabono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                observacionabonoKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(observacionabono);

        jcMousePanel6.add(jScrollPane4);
        jScrollPane4.setBounds(130, 90, 350, 40);

        formapagoabono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        formapagoabono.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Contado", "Cheque", "Deposito", "Otro" }));
        formapagoabono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formapagoabonoActionPerformed(evt);
            }
        });
        jcMousePanel6.add(formapagoabono);
        formapagoabono.setBounds(390, 50, 90, 23);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Efectivo");
        jcMousePanel6.add(jLabel8);
        jLabel8.setBounds(290, 20, 100, 20);

        efectivo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#####0.00",true))));
        efectivo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        efectivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        efectivo.setName("precioalmayor"); // NOI18N
        efectivo.setPreferredSize(new java.awt.Dimension(80, 23));
        efectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                efectivobutnguardarabonoActionPerformed(evt);
            }
        });
        efectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                efectivoKeyReleased(evt);
            }
        });
        jcMousePanel6.add(efectivo);
        efectivo.setBounds(390, 20, 90, 23);

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Tipo Pago");
        jcMousePanel6.add(jLabel29);
        jLabel29.setBounds(290, 50, 100, 20);

        javax.swing.GroupLayout abonoLayout = new javax.swing.GroupLayout(abono.getContentPane());
        abono.getContentPane().setLayout(abonoLayout);
        abonoLayout.setHorizontalGroup(
            abonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
        );
        abonoLayout.setVerticalGroup(
            abonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Ventas");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("frmventas"); // NOI18N

        jcMousePanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jcMousePanel4.setColor1(new java.awt.Color(153, 153, 153));
        jcMousePanel4.setColor2(new java.awt.Color(204, 204, 204));
        jcMousePanel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N
        jcMousePanel4.setOpaque(false);

        jcMousePanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), "Datos de Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14), java.awt.Color.white)); // NOI18N
        jcMousePanel3.setColor1(new java.awt.Color(153, 153, 153));
        jcMousePanel3.setModo(5);
        jcMousePanel3.setName(""); // NOI18N
        jcMousePanel3.setOpaque(false);

        jLabel23.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Fecha:");

        jLabel24.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Cliente:");

        jLabel25.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Nit:");

        nombrecliente.setBackground(new java.awt.Color(255, 255, 255));
        nombrecliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nombrecliente.setForeground(new java.awt.Color(0, 51, 153));
        nombrecliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        nombrecliente.setOpaque(true);

        nittxt.setBackground(new java.awt.Color(255, 255, 255));
        nittxt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nittxt.setForeground(new java.awt.Color(0, 51, 153));
        nittxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        nittxt.setOpaque(true);

        idproducto.setVisible(false);
        idproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idproductoActionPerformed(evt);
            }
        });

        idfac.setVisible(false);
        idfac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idfacActionPerformed(evt);
            }
        });

        idcliente.setVisible(false);

        tele.setVisible(false);

        direccion.setVisible(false);

        precioscostos.setText("costo");
        precioscostos.setVisible(false);

        javax.swing.GroupLayout jcMousePanel3Layout = new javax.swing.GroupLayout(jcMousePanel3);
        jcMousePanel3.setLayout(jcMousePanel3Layout);
        jcMousePanel3Layout.setHorizontalGroup(
            jcMousePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jcMousePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jcMousePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel3Layout.createSequentialGroup()
                        .addComponent(nittxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tele, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idfac, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jcMousePanel3Layout.createSequentialGroup()
                        .addComponent(fechainicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(precioscostos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jcMousePanel3Layout.setVerticalGroup(
            jcMousePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel3Layout.createSequentialGroup()
                .addGroup(jcMousePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jcMousePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fechainicial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(precioscostos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(idproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(idcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jcMousePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jcMousePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jcMousePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(nittxt, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jcMousePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idfac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        jcMousePanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2), "Busqueda de Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14), java.awt.Color.white)); // NOI18N
        jcMousePanel2.setColor1(new java.awt.Color(102, 102, 102));
        jcMousePanel2.setColor2(new java.awt.Color(204, 204, 204));
        jcMousePanel2.setModo(5);
        jcMousePanel2.setOpaque(false);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Cantidad");

        cantidadP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cantidadP.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidadP.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cantidadP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cantidadPActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Descripcion");

        nombreproducto.setEditable(false);
        nombreproducto.setEditable(false);
        nombreproducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nombreproducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        nombreproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreproductoActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Existencia");

        existencia.setEditable(false);
        existencia.setEditable(false);
        existencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        existencia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        existencia.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        existencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existenciaActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Precio");

        precios.setEditable(true);
        precios.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        precios.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        precios.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        precios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preciosActionPerformed(evt);
            }
        });

        bntSalir.setBackground(new java.awt.Color(51, 153, 255));
        bntSalir.setText("...");
        bntSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSalirActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel21.setText("Tipo de Precio");

        comboprecio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboprecio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Contado", "Credito", "Distribuidor", "Especial" }));
        comboprecio.setToolTipText("");

        codigoproductos.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        codigoproductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoproductosActionPerformed(evt);
            }
        });
        codigoproductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoproductosKeyPressed(evt);
            }
        });

        nombreproducto.setEditable(false);
        unidadproducto.setEditable(false);
        unidadproducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        unidadproducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        unidadproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unidadproductoActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Unidad de Medida");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Código");

        btncrearproducto1.setBackground(new java.awt.Color(102, 204, 0));
        btncrearproducto1.setText("Crear Producto");
        btncrearproducto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncrearproductoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel11.setText("Forma Pago:");

        formapago.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jcMousePanel2Layout = new javax.swing.GroupLayout(jcMousePanel2);
        jcMousePanel2.setLayout(jcMousePanel2Layout);
        jcMousePanel2Layout.setHorizontalGroup(
            jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel2Layout.createSequentialGroup()
                .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jcMousePanel2Layout.createSequentialGroup()
                        .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jcMousePanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(codigoproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(unidadproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cantidadP, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(10, 10, 10)
                        .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(existencia)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jcMousePanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(formapago, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(btncrearproducto1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(precios, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );
        jcMousePanel2Layout.setVerticalGroup(
            jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel2Layout.createSequentialGroup()
                .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(formapago)
                        .addComponent(comboprecio)
                        .addComponent(btncrearproducto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jcMousePanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(13, 13, 13)
                .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jcMousePanel2Layout.createSequentialGroup()
                        .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cantidadP, javax.swing.GroupLayout.DEFAULT_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(existencia, javax.swing.GroupLayout.DEFAULT_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(precios, javax.swing.GroupLayout.DEFAULT_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jcMousePanel2Layout.createSequentialGroup()
                        .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jcMousePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(unidadproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(codigoproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bntSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tablaventas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idproducto", "PRODUCTO", "CANTIDAD", "precio C", "PRECIO", "SUB-TOTAL", "idlote", "idd"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaventas.setGridColor(new java.awt.Color(51, 51, 255));
        tablaventas.setRowHeight(20);
        jScrollPane3.setViewportView(tablaventas);
        if (tablaventas.getColumnModel().getColumnCount() > 0) {
            tablaventas.getColumnModel().getColumn(0).setResizable(false);
            tablaventas.getColumnModel().getColumn(1).setResizable(false);
            tablaventas.getColumnModel().getColumn(2).setResizable(false);
            tablaventas.getColumnModel().getColumn(3).setResizable(false);
            tablaventas.getColumnModel().getColumn(4).setResizable(false);
            tablaventas.getColumnModel().getColumn(5).setResizable(false);
            tablaventas.getColumnModel().getColumn(6).setResizable(false);
            tablaventas.getColumnModel().getColumn(7).setResizable(false);
        }

        jPanel8.setBackground(new java.awt.Color(61, 139, 189));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jPanel8.setForeground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Presiona ENTER encima del producto para elminar de ventas");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Total");

        TotalPagar.setBackground(new java.awt.Color(204, 204, 204));
        TotalPagar.setFont(TotalPagar.getFont().deriveFont((TotalPagar.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD, 24));
        TotalPagar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TotalPagar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Articulos:");

        txtarticulos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtarticulos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtarticulos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));

        iniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/accept1.png"))); // NOI18N
        iniciar.setText("Cobrar");
        iniciar.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(txtarticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TotalPagar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(iniciar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtarticulos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(iniciar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jcMousePanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), "Datos de Salida", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14), java.awt.Color.white)); // NOI18N
        jcMousePanel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo/índice.jpg"))); // NOI18N
        jcMousePanel9.setModo(5);
        jcMousePanel9.setOpaque(false);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Registro de Ventas");

        Nodefac.setFont(Nodefac.getFont().deriveFont((Nodefac.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD, 35));
        Nodefac.setForeground(new java.awt.Color(255, 255, 255));
        Nodefac.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Nodefac.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("No. Salida:");

        clockDigital1.setForeground(new java.awt.Color(255, 255, 255));

        jButton4.setBackground(new java.awt.Color(51, 153, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        jButton4.setText("Salir");
        jButton4.setToolTipText("ESC");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jcMousePanel9Layout = new javax.swing.GroupLayout(jcMousePanel9);
        jcMousePanel9.setLayout(jcMousePanel9Layout);
        jcMousePanel9Layout.setHorizontalGroup(
            jcMousePanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jcMousePanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jcMousePanel9Layout.createSequentialGroup()
                        .addComponent(clockDigital1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Nodefac, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jcMousePanel9Layout.setVerticalGroup(
            jcMousePanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel9Layout.createSequentialGroup()
                .addGroup(jcMousePanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jcMousePanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clockDigital1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Nodefac, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jcMousePanel11.setBackground(new java.awt.Color(255, 255, 255));
        jcMousePanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jcMousePanel11.setModo(5);
        jcMousePanel11.setLayout(null);

        elart.setBackground(new java.awt.Color(51, 153, 255));
        elart.setMnemonic(KeyEvent.VK_E);
        elart.setText("Eliminar Articulo");
        elart.setToolTipText("ALT+E");
        elart.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        elart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elartActionPerformed(evt);
            }
        });
        jcMousePanel11.add(elart);
        elart.setBounds(410, 10, 115, 30);

        btnmodificar.setBackground(new java.awt.Color(51, 153, 255));
        btnmodificar.setMnemonic(KeyEvent.VK_M);
        btnmodificar.setText("Modificar Precio");
        btnmodificar.setToolTipText("ALT+M");
        btnmodificar.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });
        jcMousePanel11.add(btnmodificar);
        btnmodificar.setBounds(290, 10, 115, 30);

        btnbusscar.setBackground(new java.awt.Color(51, 153, 255));
        btnbusscar.setMnemonic(KeyEvent.VK_B);
        btnbusscar.setText("Buscar");
        btnbusscar.setToolTipText("ALT+B");
        btnbusscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbusscarActionPerformed(evt);
            }
        });
        jcMousePanel11.add(btnbusscar);
        btnbusscar.setBounds(170, 10, 115, 30);

        btnbusscar1.setBackground(new java.awt.Color(51, 153, 255));
        btnbusscar1.setMnemonic(KeyEvent.VK_X);
        btnbusscar1.setText("Cancelar");
        btnbusscar1.setToolTipText("ALT+X");
        btnbusscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbusscar1ActionPerformed(evt);
            }
        });
        jcMousePanel11.add(btnbusscar1);
        btnbusscar1.setBounds(530, 10, 115, 30);

        javax.swing.GroupLayout jcMousePanel4Layout = new javax.swing.GroupLayout(jcMousePanel4);
        jcMousePanel4.setLayout(jcMousePanel4Layout);
        jcMousePanel4Layout.setHorizontalGroup(
            jcMousePanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jcMousePanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jcMousePanel4Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jcMousePanel4Layout.createSequentialGroup()
                        .addComponent(jcMousePanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jcMousePanel4Layout.createSequentialGroup()
                        .addGroup(jcMousePanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jcMousePanel4Layout.createSequentialGroup()
                                .addComponent(jcMousePanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcMousePanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jcMousePanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jcMousePanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))))
        );
        jcMousePanel4Layout.setVerticalGroup(
            jcMousePanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jcMousePanel4Layout.createSequentialGroup()
                .addGroup(jcMousePanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcMousePanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcMousePanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcMousePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jcMousePanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcMousePanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcMousePanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void cuentaprecio() {
        DefaultTableModel tempe = (DefaultTableModel) tablaventas.getModel();
        float Actual = 0, Resultado = 0;
        float res = 0, act = 0;
        for (int i = 0; i < tempe.getRowCount(); i++) {
            Actual = Float.parseFloat(tempe.getValueAt(i, 5).toString());
            act = Float.parseFloat(tempe.getValueAt(i, 2).toString());
            Resultado = Resultado + Actual;
            res = res + act;
            //temp.getValueAt(i, 6);
        }
        Resultado = (float) (Math.round(Resultado * 100.0) / 100.0);
        TotalPagar.setText("" + Resultado);
        txtarticulos.setText("" + res);
        //busquedacompra.setText("");
        codigoproductos.setSelectedIndex(0);

    }

    public void buscarproductocodigo() {

        if (/*busquedacompra.getText().equals("")*/codigoproductos.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(this,
                    "Sin datos a buscar en ventas", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {

            try {
                String idP = null, prod = null;
                int idPrv = 0, idfact = 0, Nofac = 0;
                float cantP = 0, loteC = 0, C = 0, restalote = 0, restapro = 0, preV = 0, preC = 0;

                float suma = 0, est = 0;
                String precioventa = "";
                if (comboprecio.getSelectedIndex() == 0) {
                    precioventa = "producto.precioventa";
                }
                if (comboprecio.getSelectedIndex() == 1) {
                    precioventa = "producto.preciocredito";
                }
                if (comboprecio.getSelectedIndex() == 2) {
                    precioventa = "producto.preciodistribuidor";
                }
                if (comboprecio.getSelectedIndex() == 3) {
                    precioventa = "producto.precioespecial";
                }

                String sqlfac = "", cancel = "C", sql3 = "", estado = "T", sql = "select producto.preciocompra,producto.idProducto,producto.Cantidad,producto.nombre," + precioventa + " from lote INNER JOIN producto ON  lote.producto_idProducto=producto.idproducto where Codigo='" + codigoproductos.getSelectedItem() + "' and lote.estado='" + estado + "' and producto.estado='T' group by idproducto";

                //System.out.println(sql);
                String fecha = "";
                java.util.Date fechas = new Date();
                int mes = fechas.getMonth() + 1;
                GregorianCalendar calendarios
                        = new GregorianCalendar();
                Calendar c1 = Calendar.getInstance();

                fecha = "" + c1.get(calendarios.YEAR) + "-" + mes + "-" + fechas.getDate();// + " " + fechas.getHours() + ":" + fechas.getMinutes() + ":" + fechas.getSeconds();
                conn = BdConexion.getConexion();
                Statement s = (Statement) conn.createStatement();
                //s.executeUpdate("LOCK TABLES lote WRITE;");
                s.executeUpdate("LOCK TABLES producto WRITE, lote WRITE;");
                ResultSet rs = s.executeQuery(sql);

                //s.executeUpdate("LOCK TABLES lote WRITE;");
                if (rs.next() == true) {

                    prod = rs.getString("producto.nombre");
                    nombreproducto.setText(prod);
                    precioscostos.setText(rs.getString("producto.preciocompra"));
                    cantP = rs.getFloat("producto.cantidad");
                    existencia.setText(cantP + "");
                    idP = rs.getString("producto.idProducto");
                    idproducto.setText(idP);

                    preV = rs.getFloat(precioventa);
                    //unidadproducto.setText(rs.getString("unidad.nombre"));

                    precios.setText("" + preV);

                    if (cantP <= 0) {
                        idproducto.setText("");
                        nombreproducto.setText("");
                        existencia.setText("");
                        precios.setText("");
                        unidadproducto.setText("");

                        JOptionPane.showMessageDialog(null, "No Hay Productos");
                    }

                    cantidadP.requestFocus();
                    cantidadP.selectAll();
                }//fin del doooo
                else {
                    idproducto.setText("");
                    nombreproducto.setText("");
                    unidadproducto.setText("");
                    existencia.setText("");
                    precios.setText("");

                    JOptionPane.showMessageDialog(null, "No Hay Productos");

                }
                s.executeUpdate("UNLOCK TABLES;");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }

        }
    }

    public static String idfac() {
        return idfactura;
    }

    public static String fechafact() {
        return fechasalida;
    }
    private void cantpagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cantpagoActionPerformed
        // TODO add your handling code here:
        String fecha = "";
        montoabonado = 0;//mod
        java.util.Date fechas = new Date();
        int mes = fechas.getMonth() + 1;
        GregorianCalendar calendarios
                = new GregorianCalendar();
        Calendar c1 = Calendar.getInstance();

        fecha = "" + c1.get(calendarios.YEAR) + "-" + mes + "-" + fechas.getDate();// + " " + fechas.getHours() + ":" + fechas.getMinutes() + ":" + fechas.getSeconds();

        float resta = 0;
        String sqlfac = "";

        try {
            if (Float.parseFloat(cantpago.getText().toString()) >= Float.parseFloat(TotalPagar.getText().toString())) {
                conn = BdConexion.getConexion();
                // Se crea un Statement, para realizar la consulta
                Statement s = (Statement) conn.createStatement();

                formadepago formadepago = (formadepago) formapago.getSelectedItem();
                String idTP = formadepago.getID();
                char sta = 'T';
                String saldov = "", total = "";

                sta = 'F';
                saldov = "0";
                total = (TotalPagar.getText());
                System.out.print(total + "  total a pagar \n");
                sqlfac = "UPDATE salida SET estado='" + sta + "',total='" + total + "',saldo='" + saldov + "' WHERE idsalida =" + idfac.getText();
                s.executeUpdate(sqlfac);

                resta = Float.parseFloat(cantpago.getText()) - Float.parseFloat(TotalPagar.getText());
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension ventana = pago.getSize();
                pago.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                pago.setVisible(false);

                JOptionPane.showMessageDialog(null, "<HTML><font color= blue size=+2></font> \n" + "<HTML><font color=blue size=+2> su cambio: " + resta + "</font> ");

                int resp;
                resp = JOptionPane.showOptionDialog(this, "¿Desea Inprimier el comprobante?", "Pregunta", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, new Object[]{"No", "Si"}, "No");
                if (resp == 1) {
                    String nombrereporte = "reimpresionContado.jasper";
                    Map parametro = new HashMap();
                    parametro.put("idsalida", Integer.parseInt(idfac()));
                    GeneraReportes.AbrirReporte(nombrereporte, parametro);
                    limipiarventas();
                }
                if (resp == 0) {
                    limipiarventas();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cantidad Menor a consumido");
            }

        } catch (NumberFormatException | NullPointerException | SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            //Logger.getLogger(frmventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cantpagoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String sqlfac = "";
        if (formapago.getSelectedIndex() >= 0) {
            if (formapago.getSelectedItem().toString().equals("CONTADO")) {
                if (idfac.getText().equals("")) {
                    JOptionPane.showMessageDialog(this,
                            "No hay productos", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    Dimension ventana = pago.getSize();
                    pago.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    pago.setVisible(true);
                }

            }
            if (!formapago.getSelectedItem().toString().equals("CONTADO")) {
                if (idfac.getText().equals("")) {
                    JOptionPane.showMessageDialog(this,
                            "No hay productos", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    Dimension ventana = diascredito.getSize();
                    diascredito.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    formadepago formadepago = (formadepago) formapago.getSelectedItem();
                    int dias = formadepago.todia();
                    cantdias.setText("" + dias);
                    diascredito.setVisible(true);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Selecciona tipo de pago", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void cantdiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cantdiasActionPerformed
        // TODO add your handling code here:
        float resta = 0;
        montoabonado = 0;//mod
        String sqlfac = "";
        try {
            if (Float.parseFloat(cantdias.getText().toString()) >= 1) {
                int fes1 = 0;
                Calendar cal = new GregorianCalendar(año(), mes(), dia());
                fes1 = Integer.parseInt(cantdias.getText());
                cal.add(Calendar.DATE, fes1);
                int mes2 = cal.get(Calendar.MONTH) + 1;
                String fechacred = cal.get(Calendar.YEAR) + "-" + mes2 + "-" + cal.get(Calendar.DAY_OF_MONTH);// + " " + cal.getTime().getHours() + ":" + cal.getTime().getMinutes() + ":" + cal.getTime().getSeconds();
                fechasalida = fechacred;
                conn = BdConexion.getConexion();
                // Se crea un Statement, para realizar la consulta
                Statement s = (Statement) conn.createStatement();

                formadepago formadepago = (formadepago) formapago.getSelectedItem();
                String idTP = formadepago.getID();
                char sta = 'T';
                String saldov = "", total = "";

                sta = 'T';
                saldov = (TotalPagar.getText());
                total = (TotalPagar.getText());
                sqlfac = "UPDATE salida SET fechapago='" + fechacred + "',estado='" + sta + "',total='" + total + "',saldo='" + saldov + "' WHERE idsalida =" + idfac.getText();
                s.executeUpdate(sqlfac);

                diascredito.setVisible(false);

                int resp;
                resp = JOptionPane.showOptionDialog(this, "¿Desea realizar un Abono?", "Pregunta", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, new Object[]{"No", "Si"}, "No");
                if (resp == 1) {
                    dcFecha.setDate(Calendar.getInstance().getTime());
                    montoabono.setValue(null);
                    efectivo.setValue(null); //mod
                    observacionabono.setText("");
                    formapagoabono.setSelectedIndex(0);//mod
                    abono.setVisible(true);
                    montoabono.requestFocus();
                    abono.setSize(504, 190);
                    abono.setResizable(false);
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    Dimension ventana = abono.getSize();
                    abono.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    abono.toFront();//aparece al frente
                    //new imprimiendo().setVisible(true);
                }
                if (resp == 0) {
                    saldototal();
                    new imprimiendo().setVisible(true);
                    limipiarventas();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Error dias no puede ser menor a 1");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_cantdiasActionPerformed

    public boolean esentero(double x) {
        if (x % 1 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void guardarventa() {

        if ((formapago.getSelectedIndex() < 0)) {
            JOptionPane.showInternalMessageDialog(this, "Seleccione el tipo de pago ");
        } else {
            String fecha = "";
            fecha = getFecha();

            if ((idproducto.getText() != "") && (cantidadP.getText() != "")) {
                try {

                    String idP = null, prod = null/*preV = null, preC = null*/;
                    int idPrv = 0, /*cantP = 0, loteC = 0, C = 0, restalote = 0, restapro = 0,*/ idfact = 0, Nofac = 0/*, C2 = 0*/;
                    float suma = 0, est = 0, C = 0, cantP = 0, loteC = 0, preV = 0, C2 = 0, restalote = 0, restapro = 0, preC = 0;
                    String precioventa = "";
                    if (comboprecio.getSelectedIndex() == 0) {
                        precioventa = "producto.precioventa";
                    }
                    if (comboprecio.getSelectedIndex() == 1) {
                        precioventa = "producto.preciocredito";
                    }
                    if (comboprecio.getSelectedIndex() == 2) {
                        precioventa = "producto.preciodistribuidor";
                    }
                    if (comboprecio.getSelectedIndex() == 3) {
                        precioventa = "producto.precioespecial";
                    }
                    conn = BdConexion.getConexion();
                    // Se crea un Statement, para realizar la consulta
                    Statement s = (Statement) conn.createStatement();
                    C2 = Float.parseFloat(cantidadP.getText().toString());

                    String sqlfac = "", cancel = "C", sql3 = "", estado = "T", sql = null;
                    if (esentero(C2) == true) {
                        sql = "select lote.idlote,lote.precio,lote.stock,producto.idProducto,producto.Cantidad,producto.nombre," + precioventa + " from lote INNER JOIN producto ON  lote.producto_idProducto=producto.idproducto where producto.idproducto='" + idproducto.getText() + "'and lote.estado='" + estado + "' and lote.stock > 0.99 order by lote.idlote asc ";
                    }
                    if (esentero(C2) == false) {
                        sql = "select lote.idlote,lote.precio,lote.stock,producto.idProducto,producto.Cantidad,producto.nombre," + precioventa + " from lote INNER JOIN producto ON  lote.producto_idProducto=producto.idproducto where producto.idproducto='" + idproducto.getText() + "'and lote.estado='" + estado + "' order by lote.idlote asc";
                    }
                    if (C2 <= Float.parseFloat(existencia.getText())) {
                        if (tablaventas.getRowCount() == 0) {

                            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                            Dimension ventana = cliente.getSize();
                            cliente.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);

                            cliente.setSize(651, 568);
                            cliente.setVisible(true);

                        }
                    }
                    try {
                        conn.setAutoCommit(false);
                        //s.executeUpdate("LOCK TABLES productos WRITE;");
                        s.executeUpdate("LOCK TABLES producto WRITE,detallesalida WRITE, lote WRITE, salida WRITE;");
                        while (est == 0) {
                            ResultSet rs = s.executeQuery(sql);

                            if (rs.next() == true) {
                                if (C == 0) {
                                    //           String Cant = JOptionPane.showInputDialog(null, "ingresa Cantidad Venta Producto");
                                    C = Float.parseFloat(cantidadP.getText().toString());
                                    C = (float) (Math.round(C * 100.0) / 100.0);
                                }
                                if (C > 0) {

                                    prod = rs.getString("producto.nombre");
                                    //nombreproducto.setText(prod);
                                    cantP = rs.getFloat("producto.cantidad");

                                    idP = rs.getString("producto.idProducto");
                                    idPrv = rs.getInt("lote.idlote");

                                    preC = rs.getFloat("lote.precio");
                                    loteC = rs.getFloat("lote.stock");

                                    if (!precios.getText().isEmpty()) {
                                        preV = Float.parseFloat(precios.getText());//rs.getFloat("" + precioventa);
                                        //preV = rs.getFloat("" + precioventa);
                                        preV = (float) (Math.round(preV * 100.0) / 100.0);
                                    } else {
                                        preV = rs.getFloat("" + precioventa);
                                        preV = (float) (Math.round(preV * 100.0) / 100.0);
                                    }

                                    if (cantP >= C) {
                                        if (tablaventas.getRowCount() == 0) {

                                            if (idfac.getText().equals("")) {

                                                formadepago formadepago = (formadepago) formapago.getSelectedItem();
                                                String idTP = formadepago.getID();
                                                int dias = formadepago.todia();
//                                            char sta = 'T';
//                                            String saldov = "";
                                                String fechapago = "";
                                                if (formapago.getSelectedItem().toString().equals("CONTADO")) {
                                                    fechapago = fecha;
                                                }
                                                if (!formapago.getSelectedItem().toString().equals("CONTADO")) {
                                                    Calendar cal = new GregorianCalendar(año(), mes(), dia());

                                                    cal.add(Calendar.DATE, dias);
                                                    int mes2 = cal.get(Calendar.MONTH) + 1;
                                                    String fechacred = cal.get(Calendar.YEAR) + "-" + mes2 + "-" + cal.get(Calendar.DAY_OF_MONTH);// + " " + cal.getTime().getHours() + ":" + cal.getTime().getMinutes() + ":" + cal.getTime().getSeconds();
                                                    fechapago = fechacred;

                                                }
                                                fechainicial.setEnabled(false);

                                                sqlfac = "insert into salida" + "(fecha,fechapago,usuario_idusuario,tipopago_idtipopago,clientes_idClientes) values" + "('" + fecha + "','" + fechapago + "','" + AccesoUsuario.idusu() + "','" + idTP + "','" + idcliente.getText() + "')";
                                                s.executeUpdate(sqlfac, Statement.RETURN_GENERATED_KEYS);
                                                ResultSet rs4 = s.getGeneratedKeys();
                                                if (rs4 != null && rs4.next()) {
                                                    idfact = rs4.getInt(1);
                                                }
                                                idfactura = idfact + "";
                                                Nofac = idfact + 500;
                                                sqlfac = "UPDATE salida SET salida='" + Nofac + "' WHERE idsalida =" + idfact;
                                                s.executeUpdate(sqlfac);
                                                idfac.setText("" + idfact);
                                                Nodefac.setText("" + Nofac);
                                            }
                                        }

                                        if (loteC >= C) {
                                            int iddetalle = 0;
                                            suma = C * preV;
                                            suma = (float) (Math.round(suma * 100.0) / 100.0);
                                            DefaultTableModel tempe = (DefaultTableModel) tablaventas.getModel();

                                            restalote = loteC - C;
                                            if (restalote == 0) {
                                                sql3 = "UPDATE lote SET stock='" + restalote + "',estado='" + cancel + "' WHERE idlote =" + idPrv;
                                                s.executeUpdate(sql3);
                                                restapro = cantP - C;
                                                sql3 = "UPDATE producto SET cantidad='" + restapro + "' WHERE idproducto =" + idP;
                                                s.executeUpdate(sql3);
                                                sql3 = "insert into detallesalida" + "(Cantidad,Precio,lote_idlote,salida_idsalida)values" + "('" + C + "','" + preV + "','" + idPrv + "','" + idfac.getText() + "')";
                                                s.executeUpdate(sql3, Statement.RETURN_GENERATED_KEYS);
                                                ResultSet rs4 = s.getGeneratedKeys();
                                                if (rs4 != null && rs4.next()) {
                                                    iddetalle = rs4.getInt(1);
                                                }
                                                Object nuevo[] = {idP, prod, C, preC, preV, suma, idPrv, iddetalle};
                                                tempe.addRow(nuevo);
                                                cuentaprecio();
                                            } else {
                                                sql3 = "UPDATE lote SET stock='" + restalote + "' WHERE idlote =" + idPrv;
                                                s.executeUpdate(sql3);
                                                restapro = cantP - C;
                                                sql3 = "UPDATE producto SET cantidad='" + restapro + "' WHERE idproducto =" + idP;
                                                s.executeUpdate(sql3);
                                                sql3 = "insert into detallesalida" + "(cantidad,Precio,lote_idlote,salida_idsalida)values" + "('" + C + "','" + preV + "','" + idPrv + "','" + idfac.getText() + "')";
                                                s.executeUpdate(sql3, Statement.RETURN_GENERATED_KEYS);
                                                ResultSet rs4 = s.getGeneratedKeys();
                                                if (rs4 != null && rs4.next()) {
                                                    iddetalle = rs4.getInt(1);
                                                }
                                                Object nuevo[] = {idP, prod, C, preC, preV, suma, idPrv, iddetalle};
                                                tempe.addRow(nuevo);
                                                cuentaprecio();
                                            }
                                            est = 1;
                                        }//fin del if lote
                                        else {
                                            float numetero = (C);
                                            int cantidadd = (int) loteC;
                                            float diff = (float) (Math.round((loteC - cantidadd) * 100.0) / 100.0);
                                            if (esentero(numetero) == true) {
                                                loteC = cantidadd;

                                            } else {
                                                loteC = (float) (Math.round(loteC * 100.0) / 100.0);
                                                diff = 0;
                                            }
                                            int iddetalle = 0;
                                            suma = (loteC * preV);
                                            suma = (float) (Math.round(suma * 100.0) / 100.0);
                                            DefaultTableModel tempe = (DefaultTableModel) tablaventas.getModel();
                                            //idproveedorfac.setText(""+temps.getValueAt(p, 8));
                                            String cancellote = "T";
                                            if (diff > 0) {
                                                cancellote = "T";
                                            } else {
                                                cancellote = "C";
                                            }
                                            System.out.print("diff " + diff + " - cancelalote " + cancellote);
                                            est = 0;
                                            sql3 = "UPDATE lote SET stock='" + diff + "', estado='" + cancellote + "' WHERE idlote =" + idPrv;
                                            s.executeUpdate(sql3);
                                            restapro = (float) (Math.round((cantP - loteC) * 100.0) / 100.0);
                                            sql3 = "UPDATE producto SET cantidad='" + restapro + "' WHERE idproducto =" + idP;
                                            s.executeUpdate(sql3);
                                            sql3 = "insert into detallesalida" + "(cantidad,Precio,lote_idlote,salida_idsalida)values" + "('" + loteC + "','" + preV + "','" + idPrv + "','" + idfac.getText() + "')";
                                            s.executeUpdate(sql3, Statement.RETURN_GENERATED_KEYS);
                                            ResultSet rs4 = s.getGeneratedKeys();
                                            if (rs4 != null && rs4.next()) {
                                                iddetalle = rs4.getInt(1);
                                            }
                                            Object nuevo[] = {idP, prod, loteC, preC, preV, suma, idPrv, iddetalle};
                                            tempe.addRow(nuevo);
                                            cuentaprecio();

                                            C = C - loteC;

                                        }
                                    }//fin el if contador 
                                    else {
                                        est = 1;
                                        JOptionPane.showMessageDialog(this,
                                                "No tienes esa cantidad de productos", "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                        est = 1;

                                    }
                                } else {
                                    est = 1;
                                    JOptionPane.showMessageDialog(this,
                                            "Cantidad tiene que ser mayor a cero", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                est = 1;
                                JOptionPane.showMessageDialog(this,
                                        "Cantidad mayor a productos existentes", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }//fin del doooo
                        System.out.print("cierre de unlock");
                        s.executeUpdate("UNLOCK TABLES;");
                        conn.commit();
                        s.close();
                        if (!conn.getAutoCommit()) {
                            conn.setAutoCommit(true);
                        }
                        ////conn.close();
                        if (est == 1) {
                            idproducto.setText("");
                            nombreproducto.setText("");
                            existencia.setText("");
                            precios.setText("");
                            cantidadP.setText("");
                            //busquedacompra.setText("");
                            //busquedacompra.requestFocus();
                            unidadproducto.setText("");
                            codigoproductos.setSelectedIndex(0);
                            codigoproductos.requestFocus();

                            comboprecio.setEnabled(false);
                            formapago.setEnabled(false);
                            // llenarcombo();
                        }
//busquedacompra.selectAll();
                    } catch (Exception ex) {
                        System.out.print("cierre de unlock");
                        s.executeUpdate("UNLOCK TABLES;");
                        conn.rollback();
                        s.close();
                        if (!conn.getAutoCommit()) {
                            conn.setAutoCommit(true);
                        }
                        fechainicial.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Ocurrio un error en el Proceso " + ex);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocurrio un error en el Proceso " + ex);
                    //Logger.getLogger(frmventas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


    private void cantidadPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cantidadPActionPerformed
        // TODO add your handling code here:
        //modificado el 12-03-2015 GLARA..
        //guardarventa();
        precios.requestFocus();
    }//GEN-LAST:event_cantidadPActionPerformed

    private void nombreproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreproductoActionPerformed

    private void existenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_existenciaActionPerformed

    private void preciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preciosActionPerformed
        // TODO add your handling code here:
        //modificado el 12-03-2015 GLARA..
        guardarventa();
    }//GEN-LAST:event_preciosActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (tablaventas.getRowCount() <= 0) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "No Puedes cerrar Tienes Ventas Pendientes");
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    private void idfacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idfacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idfacActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String fecha = "", sql = "";
        java.util.Date fechas = new Date();
        int mes = fechas.getMonth() + 1;
        GregorianCalendar calendarios
                = new GregorianCalendar();
        Calendar c1 = Calendar.getInstance();

        fecha = "" + c1.get(calendarios.YEAR) + "-" + mes + "-" + fechas.getDate();// + " " + fechas.getHours() + ":" + fechas.getMinutes() + ":" + fechas.getSeconds();

        if (nombren.getText().equals("") || direccionn.getText().equals("")) {

            Mensaje.setText("*Faltan datos por ingresar ");

        } else {
            if ((telefonon.getText().equals("") || telefonon.getText().equals(" ") || telefonon.getText().equals("  ")) || (nitn.getText().equals("") || nitn.getText().equals(" ") || nitn.getText().equals("  "))) {
                String tel = telefonon.getText(), nits = nitn.getText();
                sql = "insert into clientes" + "(Nombre,Direccion,Fec_reg)values" + "('" + nombren.getText() + "','" + direccionn.getText() + "','" + fecha + "')";

                if ((telefonon.getText().equals("") || telefonon.getText().equals(" ")) && (!nitn.equals(""))) {
                    sql = "insert into clientes" + "(Nombre,nit,Direccion,Fec_reg)values" + "('" + nombren.getText() + "','" + nits + "','" + direccionn.getText() + "','" + fecha + "')";

                }
                if ((nitn.getText().equals("") || nitn.getText().equals(" ") || nitn.getText().equals("  ")) && (!telefonon.equals(""))) {

                    sql = "insert into clientes" + "(Nombre,telefono,Direccion,Fec_reg)values" + "('" + nombren.getText() + "','" + telefonon.getText() + "','" + direccionn.getText() + "','" + fecha + "')";
                }

            } else {
                sql = "insert into clientes" + "(Nombre,Nit,telefono,Direccion,Fec_reg)values" + "('" + nombren.getText() + "','" + nitn.getText() + "','" + telefonon.getText() + "','" + direccionn.getText() + "','" + fecha + "')";

            }
            try {
                conn = BdConexion.getConexion();
                // Se crea un Statement, para realizar la consulta
                Statement s = (Statement) conn.createStatement();
                s.executeUpdate(sql);
                //conexion.close();
                Mensaje.setText("Ingresa Cliente");
                telefonon.setText("");
                nombren.setText("");
                direccionn.setText("");
                nitn.setText("");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, "Error : " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                //Logger.getLogger(frmventas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void bntSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSalirActionPerformed
        // TODO add your handling code here:
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = buscar.getSize();
        buscar.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);

        buscar.setVisible(true);
    }//GEN-LAST:event_bntSalirActionPerformed

    private void busquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_busquedaActionPerformed
        // TODO add your handling code here:
        try {
            conn = BdConexion.getConexion();
            // Se crea un Statement, para realizar la consulta
            Statement s = (Statement) conn.createStatement();
            float suma = 0, cantPrestamo = 0, cantidad = 0;
            MiModelo modelo = new MiModelo();
            busquedaproducto.setModel(modelo);
            modelo.addColumn("Id");
            modelo.addColumn("codigo");
            modelo.addColumn("Producto");
            modelo.addColumn("Cantidad");
            modelo.addColumn("Precio V");
            modelo.addColumn("Descripción");
            modelo.addColumn("Unidad");
            String precioventa = "";
            if (comboprecio.getSelectedIndex() == 0) {
                precioventa = "producto.precioventa";
            }
            if (comboprecio.getSelectedIndex() == 1) {
                precioventa = "producto.preciocredito";
            }
            if (comboprecio.getSelectedIndex() == 2) {
                precioventa = "producto.preciodistribuidor";
            }
            if (comboprecio.getSelectedIndex() == 3) {
                precioventa = "producto.precioespecial";
            }

            String sql = "select " + precioventa + ",producto.cantidad,producto.idProducto,producto.Codigo,producto.nombre,producto.descripcion,unidad.nombre from producto INNER JOIN unidad on producto.idunidad=unidad.idunidad where producto.nombre like '%" + busqueda.getText() + "%' and producto.estado='T'";
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = new Object[7]; // Hay tres columnas en la tabla
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                fila[0] = rs.getInt("producto.idproducto");
                fila[1] = rs.getString("producto.Codigo");
                fila[2] = rs.getString("producto.nombre");
                cantidad = rs.getFloat("producto.cantidad");
                fila[3] = cantidad;
                fila[4] = rs.getString(precioventa);
                fila[5] = rs.getString("producto.descripcion");
                fila[6] = rs.getString("unidad.nombre");

                modelo.addRow(fila);
            }
            Utilidades.ajustarAnchoColumnas(busquedaproducto);
            TableColumn desaparece4 = busquedaproducto.getColumnModel().getColumn(5);
            desaparece4.setMaxWidth(0);
            desaparece4.setMinWidth(0);
            desaparece4.setPreferredWidth(0);
            busquedaproducto.doLayout();
            //Utilidades.ajustarAnchoColumnas(busquedaproducto);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_busquedaActionPerformed

    private void bntSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSalir1ActionPerformed
        // TODO add your handling code here:
        buscar.setVisible(false);
    }//GEN-LAST:event_bntSalir1ActionPerformed
    public void cancelararticulo() {
        try {

            conn = BdConexion.getConexion();
            // Se crea un Statement, para realizar la consulta
            Statement s = (Statement) conn.createStatement();
            int p = 0;
            int resp;

            resp = JOptionPane.showConfirmDialog(null, "¿Desea Cancelar la venta= " + fecha + "?", "Pregunta", 0);

            if (resp == 0) {
                if (tablaventas.getRowCount() > 0) {
                    for (int i = tablaventas.getRowCount(); i > 0; i--) {
                        DefaultTableModel teme = (DefaultTableModel) tablaventas.getModel();
                        p = i - 1;
                        if (p >= 0) {
                            ResultSet rs = s.executeQuery("SELECT idProducto, cantidad FROM Producto WHERE idproducto='" + teme.getValueAt(p, 0) + "'");
                            float cant = 0;
                            while (rs.next()) {
                                cant = rs.getFloat("cantidad");
                            }
                            rs = s.executeQuery("SELECT  stock FROM lote WHERE idlote='" + teme.getValueAt(p, 6) + "'");
                            float cant2 = 0;
                            while (rs.next()) {
                                cant2 = rs.getFloat("stock");
                            }

                            float cant1 = Float.parseFloat(teme.getValueAt(p, 2).toString());
                            float Suma = cant + cant1;
                            float suma2 = cant2 + cant1;
                            String estado1 = "T";
                            s.executeUpdate("UPDATE producto SET cantidad='" + Suma + "' WHERE idProducto =" + teme.getValueAt(p, 0));
                            s.executeUpdate("UPDATE lote SET stock='" + suma2 + "', estado='" + estado1 + "' WHERE idlote =" + teme.getValueAt(p, 6));
                            s.executeUpdate("delete from detallesalida WHERE iddetallesalida =" + teme.getValueAt(p, 7));
                            if ((p == 0) && (tablaventas.getRowCount() == 1)) {
                                // JOptionPane.showMessageDialog(null, idfac.getText());
                                s.executeUpdate("delete from salida WHERE idsalida =" + idfac.getText());
                                limpieza();
                            }
                            teme.removeRow(p);
                            cuentaprecio();

                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "Selecciona Articulo a eliminar", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No hay articulos para eliminar ", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    public void eliminaarticulo() {
        try {

            conn = BdConexion.getConexion();
            // Se crea un Statement, para realizar la consulta
            Statement s = (Statement) conn.createStatement();
            int p = -1;
            int resp;

            resp = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar el Articulo= " + fecha + "?", "Pregunta", 0);

            if (resp == 0) {
                if (tablaventas.getRowCount() > 0) {

                    DefaultTableModel teme = (DefaultTableModel) tablaventas.getModel();
                    p = tablaventas.getSelectedRow();
                    if (p >= 0) {
                        ResultSet rs = s.executeQuery("SELECT idProducto, cantidad FROM Producto WHERE idproducto='" + teme.getValueAt(p, 0) + "'");
                        float cant = 0;
                        while (rs.next()) {
                            cant = rs.getFloat("cantidad");
                        }
                        rs = s.executeQuery("SELECT  stock FROM lote WHERE idlote='" + teme.getValueAt(p, 6) + "'");
                        float cant2 = 0;
                        while (rs.next()) {
                            cant2 = rs.getFloat("stock");
                        }

                        float cant1 = Float.parseFloat(teme.getValueAt(p, 2).toString());
                        float Suma = cant + cant1;
                        float suma2 = cant2 + cant1;
                        String estado1 = "T";
                        s.executeUpdate("UPDATE producto SET cantidad='" + Suma + "' WHERE idProducto =" + teme.getValueAt(p, 0));
                        s.executeUpdate("UPDATE lote SET stock='" + suma2 + "', estado='" + estado1 + "' WHERE idlote =" + teme.getValueAt(p, 6));
                        s.executeUpdate("delete from detallesalida WHERE iddetallesalida =" + teme.getValueAt(p, 7));
                        if ((p == 0) && (tablaventas.getRowCount() == 1)) {
                            // JOptionPane.showMessageDialog(null, idfac.getText());
                            s.executeUpdate("delete from salida WHERE idsalida =" + idfac.getText());
                            limpieza();
                        }
                        teme.removeRow(p);
                        cuentaprecio();

                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Selecciona Articulo a eliminar", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No hay articulos para eliminar ", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

    }

    private String Validar(String x) {
        String y;
        if (x.equals("")) {
            y = "0";
            return y;
        } else {
            y = x;
            return y;
        }
    }

    public void saldototal() {
        try {
            conn = BdConexion.getConexion();
            //conn = BdConexion.getConexion();
            String sqls = "select sum(saldo) from salida where clientes_idclientes='" + idcliente.getText() + "' and salida.estado='T'";
            Statement ss = (Statement) conn.createStatement();
            float nsaldototall = 0;

            ResultSet rss = ss.executeQuery(sqls);
            if (rss.next() == true) {
                rss.beforeFirst();
                while (rss.next()) {
                    nsaldototall = rss.getFloat("sum(saldo)");

                }
                saldototalc = Float.parseFloat("" + nsaldototall);
            }
            ////conn.close();
        } catch (Exception e) {

        }

    }

    private void elartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elartActionPerformed
        // TODO add your handling code here:
        eliminaarticulo();
    }//GEN-LAST:event_elartActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        // TODO add your handling code here:
        DefaultTableModel temp = (DefaultTableModel) tablaventas.getModel();
        if (tablaventas.getRowCount() > 0) {

            DefaultTableModel teme = (DefaultTableModel) tablaventas.getModel();
            int p = tablaventas.getSelectedRow();
            if (p >= 0) {
                Actual.setText(temp.getValueAt(tablaventas.getSelectedRow(), 4).toString());
                Ccantidad.setText(temp.getValueAt(tablaventas.getSelectedRow(), 2).toString());
                Cprecio.setText(temp.getValueAt(tablaventas.getSelectedRow(), 4).toString());
                costo.setText(temp.getValueAt(tablaventas.getSelectedRow(), 3).toString());
                iddcambio.setText(temp.getValueAt(tablaventas.getSelectedRow(), 7).toString());
                ps.setText("" + tablaventas.getSelectedRow());
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension ventana = precio.getSize();
                precio.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                precio.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Selecciona Articulo a Modificar", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "No hay articulos para Modificar ", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        //modificaprecio();
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void btnbusscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbusscarActionPerformed
        // TODO add your handling code here:
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = buscar.getSize();
        buscar.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        buscar.setVisible(true);
    }//GEN-LAST:event_btnbusscarActionPerformed
    public void modificaprecio() {
        try {

            //DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            //LeePropiedades.archivoRecurso = archivoRecurso;
            //Connection conexion = (Connection) DriverManager.getConnection(LeePropiedades.leeID("url"), LeePropiedades.leeID("usuario"), LeePropiedades.leeID("password"));
            conn = BdConexion.getConexion();
// Se crea un Statement, para realizar la consulta
            Statement s = (Statement) conn.createStatement();
            int p = -1;
            int resp;

            resp = JOptionPane.showConfirmDialog(null, "¿Desea Modificar precio del Articulo= " + fecha + "?", "Pregunta", 0);

            if (resp == 0) {
                if (tablaventas.getRowCount() > 0) {

                    DefaultTableModel teme = (DefaultTableModel) tablaventas.getModel();
                    p = tablaventas.getSelectedRow();
                    if (p >= 0) {
                        ResultSet rs = s.executeQuery("SELECT idProducto, cantidad FROM Producto WHERE idproducto='" + teme.getValueAt(p, 0) + "'");
                        float cant = 0;
                        while (rs.next()) {
                            cant = rs.getFloat("cantidad");
                        }
                        rs = s.executeQuery("SELECT  stock FROM lote WHERE idlote='" + teme.getValueAt(p, 6) + "'");
                        float cant2 = 0;
                        while (rs.next()) {
                            cant2 = rs.getFloat("stock");
                        }

                        float cant1 = Float.parseFloat(teme.getValueAt(p, 2).toString());
                        float Suma = cant + cant1;
                        float suma2 = cant2 + cant1;
                        String estado1 = "T";
                        s.executeUpdate("UPDATE producto SET cantidad='" + Suma + "' WHERE idProducto =" + teme.getValueAt(p, 0));
                        s.executeUpdate("UPDATE lote SET stock='" + suma2 + "', estado='" + estado1 + "' WHERE idlote =" + teme.getValueAt(p, 6));
                        s.executeUpdate("delete from detallesalida WHERE iddetallesalida =" + teme.getValueAt(p, 7));
                        if ((p == 0) && (tablaventas.getRowCount() == 1)) {
                            // JOptionPane.showMessageDialog(null, idfac.getText());
                            s.executeUpdate("delete from salida WHERE idsalida =" + idfac.getText());
                            limpieza();
                        }
                        teme.removeRow(p);
                        cuentaprecio();

                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Selecciona Articulo a eliminar", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No hay articulos para eliminar ", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }
    private void buscaclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscaclienteActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelo = new DefaultTableModel();
        busquedacliente.setModel(modelo);
        modelo.addColumn("CODIGO");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("DIRECCION");
        modelo.addColumn("TEL");
        modelo.addColumn("NIT");

        String sql = "select idclientes,nombre,nit,direccion,telefono from clientes where nombre like '%" + buscacliente.getText() + "%' and estado='T'";
        try {
            conn = BdConexion.getConexion();
            // Se crea un Statement, para realizar la consulta
            Statement s = (Statement) conn.createStatement();

            ResultSet rs = s.executeQuery(sql);
            if (rs.next() == true) {
                rs.beforeFirst();
                while (rs.next()) {
                    //JOptionPane.showMessageDialog(null, ""+rs.getString("nombreusuario"));
                    Object[] fila = new Object[5]; // Hay tres columnas en la tabla
                    // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                    fila[0] = rs.getInt("idclientes");
                    fila[4] = rs.getString("nit");
                    fila[1] = rs.getString("nombre");
                    fila[2] = rs.getString("direccion");
                    fila[3] = rs.getString("telefono");
                    modelo.addRow(fila);
                }
                Utilidades.ajustarAnchoColumnas(busquedacliente);
                TableColumn desaparece = busquedacliente.getColumnModel().getColumn(0);
                desaparece.setMaxWidth(33);
                desaparece.setMinWidth(33);
                desaparece.setPreferredWidth(333);
                busquedacliente.doLayout();

            } else {

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

    }//GEN-LAST:event_buscaclienteActionPerformed

    private void btnbusscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbusscar1ActionPerformed
        // TODO add your handling code here:
        cancelararticulo();
    }//GEN-LAST:event_btnbusscar1ActionPerformed
    public void cambiar() {
        DefaultTableModel temps = (DefaultTableModel) tablaventas.getModel();
        int p = Integer.parseInt(ps.getText());

        if (Float.parseFloat(Cprecio.getText()) > Float.parseFloat(costo.getText())) {
            try {
                conn = BdConexion.getConexion();
                // Se crea un Statement, para realizar la consulta
                Statement s = (Statement) conn.createStatement();

                String sqlfac = "UPDATE detallesalida SET precio='" + Cprecio.getText() + "' WHERE iddetallesalida =" + iddcambio.getText();
                s.executeUpdate(sqlfac);

                //conexion.close();
                float suma = Float.parseFloat(Ccantidad.getText()) * Float.parseFloat(Cprecio.getText());
                suma = (float) (Math.round(suma * 100.0) / 100.0);
                temps.setValueAt(Cprecio.getText(), p, 4);
                temps.setValueAt(suma, p, 5);
                cuentaprecio();
                precio.setVisible(false);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(null, "Precio No puede ser Menor a Costo");
        }
    }
    private void btnbusscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbusscar2ActionPerformed
        // TODO add your handling code here:
        cambiar();

    }//GEN-LAST:event_btnbusscar2ActionPerformed

    private void btnbusscar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbusscar3ActionPerformed
        // TODO add your handling code here:
        precio.setVisible(false);
    }//GEN-LAST:event_btnbusscar3ActionPerformed

    private void idproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idproductoActionPerformed
    private int año() {
        int años = fechainicial.getCalendar().get(Calendar.YEAR);
        return años;
    }

    private int mes() {
        int mess = fechainicial.getCalendar().get(Calendar.MONTH);
        return mess;
    }

    private int dia() {
        int dias = fechainicial.getCalendar().get(Calendar.DAY_OF_MONTH);
        return dias;
    }

    private String getFecha() {

        try {
            String fecha;
            int años = fechainicial.getCalendar().get(Calendar.YEAR);
            int dias = fechainicial.getCalendar().get(Calendar.DAY_OF_MONTH);
            int mess = fechainicial.getCalendar().get(Calendar.MONTH) + 1;

            fecha = "" + años + "-" + mess + "-" + dias;// + " " + hours + ":" + minutes + ":" + seconds;
            return fecha;
        } catch (Exception e) {
            JOptionPane.showInternalMessageDialog(this, "Verifique la fecha");
            //System.out.print(e.getMessage());
        }
        return null;
    }
    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        // TODO add your handling code here:
        cliente.dispose();
    }//GEN-LAST:event_cancelarActionPerformed

    private void CprecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CprecioActionPerformed
        // TODO add your handling code here:
        cambiar();
    }//GEN-LAST:event_CprecioActionPerformed

    private void jcMousePanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcMousePanel1KeyPressed
        // TODO add your handling code here:
//        JOptionPane.showMessageDialog(null, "rrrr");
    }//GEN-LAST:event_jcMousePanel1KeyPressed

    private void montoabonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_montoabonoKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == 27) {
            abono.setVisible(false);
        }
    }//GEN-LAST:event_montoabonoKeyReleased

    private void butnguardarabonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butnguardarabonoActionPerformed
        // TODO add your handling code here:
        //int fil = tablacomprasporpagar.getSelectedRow();
        float dat = 0, dato3 = 0, vuelto = 0;
        int indicador = 0;
        montoabonado = 0;//mod
        dat = Float.parseFloat(Validar(TotalPagar.getText()));
        dat = (float) (Math.round((dat) * 100.0) / 100.0);

        if (getFecha() == null || formapagoabono.getSelectedIndex() <= 0 || montoabono.getValue() == null || montoabono.getValue().toString().equals("0") || efectivo.getValue() == null || efectivo.getValue().toString().equals("0")) {
            JOptionPane.showInternalMessageDialog(this, "Complete los campos obligatorios");
        } else {

            //if (!montoabono.getValue().equals(0)) {
            dato3 = Float.parseFloat(Validar(montoabono.getValue().toString()));
            dato3 = (float) (Math.round((dato3) * 100.0) / 100.0);
            //}
            //if (!efectivo.getValue().equals(0)) {
            vuelto = Float.parseFloat(Validar(efectivo.getValue().toString()));
            vuelto = (float) (Math.round((vuelto) * 100.0) / 100.0);
            //}

            if (dato3 > dat) {
                JOptionPane.showInternalMessageDialog(this, "El abono debe ser menor o igual al saldo ");
            } else if (vuelto < dato3) {
                JOptionPane.showInternalMessageDialog(this, "El efectivo debe ser mayor o igual al abono ");
            } else {
                int resp;
                resp = JOptionPane.showInternalConfirmDialog(this, "¿Desea Grabar el Registro?", "Pregunta", 0);
                if (resp == 0) {
                    try {
                        abono.setVisible(false);
                        conn = BdConexion.getConexion();
                        //conn = BdConexion.getConexion();
                        //PreparedStatement nos permite crear instrucciones SQL compiladas, que se ejecutan con más efi ciencia que los objetos Statement
                        //también pueden especifi car parámetros,lo cual las hace más fl exibles que las instrucciones Statement
                        int idabono = 0;
                        //int fila = tablacomprasporpagar.getSelectedRow();

                        float montoc, saldoc, nsaldo, nsaldototal, nsaldototal2;
                        montoc = Float.parseFloat(Validar(montoabono.getText()));
                        saldoc = Float.parseFloat(Validar(TotalPagar.getText()));
                        nsaldo = (float) (Math.round((saldoc - montoc) * 100.0) / 100.0);

                        String dato = idfactura;
                        float nsaldototall = 0;
                        String sqls = "select sum(saldo) from salida where clientes_idclientes='" + idcliente.getText() + "' and salida.estado='T'";
                        Statement ss = (Statement) conn.createStatement();

                        ResultSet rss = ss.executeQuery(sqls);
                        if (rss.next() == true) {
                            rss.beforeFirst();
                            while (rss.next()) {
                                nsaldototall = rss.getFloat("sum(saldo)");

                            }
                        }
                        montoabonado = Float.parseFloat(Validar(montoabono.getText()));
                        saldoventa = nsaldo;

                        String sql = "insert into xcobrarclientes (fecha,monto,salida_idsalida,usuario_idusuario,observacion,tipopago,nsaldoventa,nsaldototal) values (?,?,?,?,?,?,?,?)";
                        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                        ps.setString(1, getFecha());
                        ps.setString(2, Validar(montoabono.getText()));
                        ps.setString(3, dato);
                        //Login entrar = new Login();
                        ps.setString(4, "" + AccesoUsuario.idusu());
                        ps.setString(5, observacionabono.getText());
                        ps.setString(6, (formapagoabono.getSelectedItem().toString()));
                        ps.setFloat(7, nsaldo);

                        //idcliente.getText();
                        nsaldototal = (float) (Math.round((nsaldototall) * 100.0) / 100.0);
                        /**
                         * *****
                         */
                        nsaldototal2 = (float) (Math.round((nsaldototal - montoc) * 100.0) / 100.0);
                        saldototalc = nsaldototal2;
                        ps.setFloat(8, nsaldototal2);

                        int n = ps.executeUpdate();
                        if (n > 0) {
                            ResultSet rs = ps.getGeneratedKeys();
                            while (rs.next()) {
                                idabono = rs.getInt(1);
                            }

                        }

                        //tablacomprasporpagar.getValueAt(i, 4).toString()
                        int n2 = 0;
                        char status;

                        if (montoc > 0 & montoc <= saldoc) {
                            status = 'T';
                            if (montoc == saldoc) {
                                status = 'F';
                            }
                            String abono = "update salida set  saldo=?, estado=? where idsalida=?";
                            PreparedStatement ps2 = conn.prepareStatement(abono);
                            ps2.setFloat(1, nsaldo);
                            ps2.setString(2, "" + status);
                            ps2.setString(3, dato);

                            n2 = ps2.executeUpdate();

                        }

                        ////conn.close();
                        if (n > 0 & n2 > 0) {
                            dcFecha.setDate(Calendar.getInstance().getTime());
                            montoabono.setValue(null);
                            efectivo.setValue(null);//mod
                            formapagoabono.setSelectedIndex(0);//mod
                            observacionabono.setText("");
                            abono.setVisible(false);

                            JOptionPane.showInternalMessageDialog(this, "Datos guardados correctamente\n\n Vuelto = Q. " + ((float) (Math.round((vuelto - dato3) * 100.0) / 100.0)));
                            new imprimiendo().setVisible(true);
                            limipiarventas();
                            //abrircomprobante(idabono);
                        }

                    } catch (SQLException e) {
                        JOptionPane.showInternalMessageDialog(this, "Error  al guardar \n Verifique los datos e intente nuevamente ", "Error", JOptionPane.ERROR_MESSAGE);
                        //System.out.print(e.getMessage());
                    }

                }
            }
        }
    }//GEN-LAST:event_butnguardarabonoActionPerformed

    private void btncancelarabonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarabonoActionPerformed
        // TODO add your handling code here:
        dcFecha.setDate(Calendar.getInstance().getTime());//mod
        montoabono.setValue(null);//mod
        efectivo.setValue(null);//mod
        formapagoabono.setSelectedIndex(0);//mod
        observacionabono.setText("");//mod
        this.abono.dispose();
    }//GEN-LAST:event_btncancelarabonoActionPerformed

    private void btncancelarabono1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarabono1ActionPerformed
        // TODO add your handling code here:
        dcFecha.setDate(Calendar.getInstance().getTime());
        montoabono.setValue(null);
        efectivo.setValue(null);//mod
        formapagoabono.setSelectedIndex(0);//mod
        observacionabono.setText("");

    }//GEN-LAST:event_btncancelarabono1ActionPerformed

    private void observacionabonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_observacionabonoKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == 27) {
            abono.setVisible(false);
        }
    }//GEN-LAST:event_observacionabonoKeyReleased

    private void formapagoabonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formapagoabonoActionPerformed
        // TODO add your handling code here:
        //System.out.print(formapagoabono.getSelectedItem());
    }//GEN-LAST:event_formapagoabonoActionPerformed

    private void efectivobutnguardarabonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_efectivobutnguardarabonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_efectivobutnguardarabonoActionPerformed

    private void efectivoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_efectivoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_efectivoKeyReleased

    private void btncrearproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncrearproductoActionPerformed
        // TODO add your handling code here:
        Producto nuevasol = new Producto();
        if (panel_center.getComponentCount() > 0 & panel_center.getComponentCount() < 2) //solo uno en t
        {
            panel_center.add(nuevasol);
            nuevasol.show();// ver interno
            nuevasol.setClosable(true);// icono de cerrar
            nuevasol.toFront();//aparece al frente
        }
    }//GEN-LAST:event_btncrearproductoActionPerformed

    private void codigoproductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoproductosActionPerformed
        // TODO add your handling code here:
//        int key = evt.getKeyCode();
//                if (evt == java.awt.event.KeyEvent.VK_ENTER) {
//                    eliminaarticulo();
//                }
    }//GEN-LAST:event_codigoproductosActionPerformed

    private void unidadproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unidadproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unidadproductoActionPerformed

    private void codigoproductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoproductosKeyPressed
        // TODO add your handling code here:

        int key = evt.getKeyCode();
        if (key == java.awt.event.KeyEvent.VK_ENTER) {
            //eliminaarticulo();
            //cantidadP.requestFocus();
            buscarproductocodigo();
        }
    }//GEN-LAST:event_codigoproductosKeyPressed

    public void limpieza() {
        nombrecliente.setText("");
        nittxt.setText("");
        cantpago.setText("");
        idfac.setText("");
        idcliente.setText("");
        TotalPagar.setText("0.0");
        Nodefac.setText("");
        formapago.setEnabled(true);
        comboprecio.setEnabled(true);
        fechainicial.setEnabled(true);
        llenarcombo();
    }

    public void limipiarventas() {
        nombrecliente.setText("");
        nittxt.setText("");
        cantpago.setText("");
        txtarticulos.setText("");
        idfac.setText("");
        idcliente.setText("");
        TotalPagar.setText("0.0");
        Nodefac.setText("");
        comboprecio.setEnabled(true);
        formapago.setEnabled(true);
        fechainicial.setEnabled(true);
        llenarcombo();
        DefaultTableModel teme = (DefaultTableModel) tablaventas.getModel();
        for (int h = teme.getRowCount(); h > 0; h--) {
            teme.removeRow(h - 1);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private elaprendiz.gui.label.LabelRect Actual;
    private elaprendiz.gui.label.LabelRect Ccantidad;
    private javax.swing.JTextField Cprecio;
    private elaprendiz.gui.label.LabelRect Mensaje;
    private javax.swing.JLabel Nodefac;
    private javax.swing.JLabel TotalPagar;
    private javax.swing.JDialog abono;
    private elaprendiz.gui.button.ButtonRect bntSalir;
    private elaprendiz.gui.button.ButtonRect bntSalir1;
    private elaprendiz.gui.button.ButtonRect btnbusscar;
    private elaprendiz.gui.button.ButtonRect btnbusscar1;
    private elaprendiz.gui.button.ButtonRect btnbusscar2;
    private elaprendiz.gui.button.ButtonRect btnbusscar3;
    private elaprendiz.gui.button.ButtonRect btncancelarabono;
    private elaprendiz.gui.button.ButtonRect btncancelarabono1;
    private elaprendiz.gui.button.ButtonRect btncrearproducto1;
    private elaprendiz.gui.button.ButtonRect btnmodificar;
    private elaprendiz.gui.textField.TextFieldRectIcon buscacliente;
    private javax.swing.JDialog buscar;
    private elaprendiz.gui.textField.TextFieldRectIcon busqueda;
    private javax.swing.JTable busquedacliente;
    private javax.swing.JTable busquedaproducto;
    private elaprendiz.gui.button.ButtonRect butnguardarabono;
    private elaprendiz.gui.button.ButtonRect cancelar;
    private javax.swing.JTextField cantdias;
    private javax.swing.JTextField cantidadP;
    private javax.swing.JTextField cantpago;
    private javax.swing.JDialog cliente;
    private elaprendiz.gui.varios.ClockDigital clockDigital1;
    private javax.swing.JComboBox codigoproductos;
    private javax.swing.JComboBox comboprecio;
    private elaprendiz.gui.label.LabelRect costo;
    private com.toedter.calendar.JDateChooser dcFecha;
    private javax.swing.JLabel descricionP;
    private javax.swing.JDialog diascredito;
    private javax.swing.JTextField direccion;
    private javax.swing.JTextField direccionn;
    private javax.swing.JFormattedTextField efectivo;
    private elaprendiz.gui.button.ButtonRect elart;
    private javax.swing.JTextField existencia;
    private org.freixas.jcalendar.JCalendarCombo fechainicial;
    private javax.swing.JComboBox formapago;
    private javax.swing.JComboBox formapagoabono;
    private javax.swing.JTextField idcliente;
    private javax.swing.JTextField idcompra;
    private elaprendiz.gui.label.LabelRect iddcambio;
    private javax.swing.JTextField idfac;
    private javax.swing.JTextField idproducto;
    private elaprendiz.gui.button.ButtonAction iniciar;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private jcMousePanel.jcMousePanel jcMousePanel1;
    private jcMousePanel.jcMousePanel jcMousePanel11;
    private jcMousePanel.jcMousePanel jcMousePanel12;
    private jcMousePanel.jcMousePanel jcMousePanel13;
    private jcMousePanel.jcMousePanel jcMousePanel14;
    private jcMousePanel.jcMousePanel jcMousePanel15;
    private jcMousePanel.jcMousePanel jcMousePanel16;
    private jcMousePanel.jcMousePanel jcMousePanel17;
    private jcMousePanel.jcMousePanel jcMousePanel2;
    private jcMousePanel.jcMousePanel jcMousePanel3;
    private jcMousePanel.jcMousePanel jcMousePanel4;
    private jcMousePanel.jcMousePanel jcMousePanel6;
    private jcMousePanel.jcMousePanel jcMousePanel9;
    private elaprendiz.gui.label.LabelRect labelRect2;
    private elaprendiz.gui.label.LabelRect labelRect3;
    private javax.swing.JFormattedTextField montoabono;
    private javax.swing.JTextField nitn;
    private javax.swing.JLabel nittxt;
    private javax.swing.JLabel nombrecliente;
    private javax.swing.JTextField nombren;
    private javax.swing.JTextField nombreproducto;
    private javax.swing.JTextArea observacionabono;
    private javax.swing.JDialog pago;
    private javax.swing.JPanel pnlActionButtons1;
    private javax.swing.JDialog precio;
    private javax.swing.JTextField precios;
    private javax.swing.JTextField precioscostos;
    private elaprendiz.gui.label.LabelRect ps;
    private javax.swing.JTable tablaventas;
    private javax.swing.JTextField tele;
    private javax.swing.JTextField telefonon;
    private javax.swing.JLabel txtarticulos;
    private javax.swing.JTextField unidadproducto;
    // End of variables declaration//GEN-END:variables

    private AudioClip getAudioClip(URL urL) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
