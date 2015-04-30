package Modelos;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

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
