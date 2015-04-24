package Modelos;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import elaprendiz.gui.comboBox.ComboBoxRectIcon;
import java.awt.Color;
import static java.awt.Color.WHITE;
import java.awt.Component;
import java.util.Calendar;
import java.util.Date;
import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author GLARA
 */
public class Utilidades {

    /**
     * Este metodo ajusta el tamaño de las columnas en un JTable de acuerdo al
     * tamaño de sus registros, el registro más largo de una columna sera el que
     * defina el ancho de las columnas (maxwidth).
     *
     * @param table
     */
    public static void ajustarAnchoColumnas(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();

        for (int col = 0; col < table.getColumnCount(); col++) {
            int maxwidth = 0;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer rend = table.getCellRenderer(row, col);
                Object value = table.getValueAt(row, col);
                Component comp = rend.getTableCellRendererComponent(table, value, false, false, row, col);
                maxwidth = Math.max(comp.getPreferredSize().width, maxwidth);
            } // para fila
            TableColumn column = columnModel.getColumn(col);
            column.setPreferredWidth(maxwidth);
        } // para columnas 
        table.setAutoCreateRowSorter(true);//para ordenar el Jtable al dar clic encima del titulo de la columna
    }

}
