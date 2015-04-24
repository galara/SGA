package Modelos;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author El APRENDIZ www.elaprendiz.net63.net
 */
public class ModeloTabla extends DefaultTableModel {
    //protected ArrayList<M> registros;
    //protected String[] nombreColumnas;
   String[] nombreColumnas = new String[]{"Producto Seleccionado",
                               "Cantidad","Precio","Descuento %","Importe","Accion"," a "};
        
   @Override
   public boolean isCellEditable (int row, int column)
   {
       // Aquí devolvemos true o false según queramos que una celda
       // identificada por fila,columna (row,column), sea o no editable
       //JOptionPane.showMessageDialog(null, row+" "+column);
 return false;

   }
    
}
