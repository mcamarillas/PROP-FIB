package FONTS.src.domini.drivers;
import FONTS.src.persistencia.ControladorPersistenciaItem;
import FONTS.src.persistencia.ControladorPersistenciaRatings;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

/** \brief Driver de la clase ControladorPersistencia.
 *  @author Oriol Cuellar
 */


//public class DriverLectorCSV2 {

    /** Main del driver.
     *  Prueba 2 casos:
     *      - Lee valoraciones de un path dado y los imprime por pantalla.
     *      - Lee items de un path dado y los imprime por pantalla.
     * @see ControladorPersistenciaRatings
     */
    /*
    public static void main(String[] args) {
        printInfo();

        Scanner s = new Scanner(System.in);
        String cual="-1";
        while(!cual.equals("1") && !cual.equals("0")) {
            System.out.println("=====================================================================================");
            System.out.println("\n- INTRODUCE \n  - 0 para leer valoraciones \n  - 1 para leer items \n");
            cual = s.next();
        }
        boolean leido=false;
        ControladorPersistenciaRatings reader = new ControladorPersistenciaRatings();
        ControladorPersistenciaItem readerItems = new ControladorPersistenciaItem();
        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();
        Vector<String> readed_Items = new Vector<String>();
        while (!leido){
            try {
                if (cual.equals("0")) {//leer valoraciones
                    System.out.println("=====================================================================================");
                    String path;
                    System.out.println("\n- INTRODUCE el path al CSV de valoraciones que desea leer: ");
                    path = s.next();
                    readed_ratings = reader.Lector_Ratings(path);
                    leido=true;
                }
                if (cual.equals("1")) {//leer items
                    System.out.println("=====================================================================================");
                    String path;
                    System.out.println("\n- INTRODUCE el path al CSV de items que desea leer: ");
                    path = s.next();
                    readed_Items = readerItems.Lector_Items(path);
                    leido=true;
                }
            }
            catch (Exception e){
                System.out.println("\n\n- ERROR");
                System.out.println(e);
                System.out.println("\n- Prueba con una entrada como \n   - ratings.db.csv \n   - nombre_carpeta/ratings.db.csv " +
                        "\n   - items.csv \n   - nombre_carpeta/items.csv");
            }
        }

        if (cual.equals("0")){
            System.out.println("UserId ItemId Rating");
            for (Vector<String> v: readed_ratings){
                String pal="";
                for(String st: v){
                    pal+=st+" ";
                }
                System.out.println(pal);
            }
        }
        else if (cual.equals("1")){
            for (String st: readed_Items){
                System.out.println(st);
            }
        }

    }
    /** Funcion que muestra informacion sobre el driver.
     * Se puede escoger entre los 2 casos.

    static void printInfo() {
        System.out.println("=====================================================================================");
        System.out.println("\nDRIVER DE LA CLASE ControladorPersistencia\n");
        System.out.println("    La clase ControladorPersistencia lee ficheros CSV de los paths introducidos por el usuario.");
        System.out.println("    Los muestra por pantalla");

    }
}
*/
