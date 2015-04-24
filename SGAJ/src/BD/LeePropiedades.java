/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

/**
 *
 * @author Otto
 */
import java.util.ResourceBundle;

public class LeePropiedades {

    public static String archivoRecurso = "";
    private static ResourceBundle resource = null;

    public static String leeID(String id) {
        resource = ResourceBundle.getBundle(archivoRecurso);

        return resource.getString(id);
    }
}
