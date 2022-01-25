package FONTS.src.domini.drivers;
import FONTS.src.domini.model.*;
import FONTS.src.persistencia.ControladorPersistenciaRatings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/** \brief Driver de la clase Kmeans.
 *  @author Roberto Amat
 */
public class DriverKmeans {

    static String path;



    /** Mapa que contiene como llave el ID de un usuario y com valor dicho usuario.
     *  Se utiliza para relizar las pruebas con los datos recibidos a través del fichero.
     */



    /** Función que lee el archivo que contiene los datos a probar.
     * @param csvFile Dirección del archivo que contiene los datos-
     * @return Vector que almacena los datos leídos.
     */
    public static ArrayList<Vector<String>> Lector_Ratings(String csvFile){
        ArrayList<Vector<String>> ratings=new ArrayList<Vector<String>>();
        BufferedReader br = null;
        String line = "";
        //Se define separador ","
        String cvsSplitBy = ",";
        int user=0;
        int item=1;
        int rating=2;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(cvsSplitBy);
                if (first){//colamos orden. User, item, valoracion
                    if (datos[0].equals("userId")) user=0;
                    if (datos[1].equals("userId")) user=1;
                    if (datos[2].equals("userId")) user=2;
                    if (datos[0].equals("itemId")) item=0;
                    if (datos[1].equals("itemId")) item=1;
                    if (datos[2].equals("itemId")) item=2;
                    if (datos[0].equals("rating")) rating=0;
                    if (datos[1].equals("rating")) rating=1;
                    if (datos[2].equals("rating")) rating=2;
                }
                if(!first) {
                    Vector<String> dentro = new Vector<String>();
                    dentro.add(datos[user]);
                    dentro.add(datos[item]);
                    dentro.add(datos[rating]);
                    ratings.add(dentro);
                }
                first = false;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return ratings;

    }

    /** Función que se encarga de leer los datos y almacenarlos en su lugar correspondiente.
     */
    public static Map<Integer, User> readData(String s){
        Map<Integer, User> usersList = new HashMap<>();
        ControladorPersistenciaRatings l = new ControladorPersistenciaRatings();
        ArrayList<Vector<String>> readed_ratings = Lector_Ratings(s);
        boolean fail = false;
        for (Vector<String> vs : readed_ratings) {
            if(fail) break;
            else {
                if (usersList.containsKey(Integer.valueOf(vs.get(0)))) {//existeix
                    User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                    if (usuari.searchUsedItem(Integer.parseInt(vs.get(1))) == null) {//no existe el item en sus valoraciones
                        usuari.addvaloratedItem(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));

                    }
                } else {//no existeix, es crea, afegim valoracio a la seva llista, afegim valoracio allista itemUsatList
                    try {
                        User usuari = new User(Integer.parseInt(vs.get(0)));
                        usuari.addvaloratedItem(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));
                        usersList.put(Integer.valueOf(vs.get(0)), usuari);
                    }catch (Exception e) {
                        System.out.println(e);
                        System.out.println("\nERROR!");
                        System.out.println("El fichero introducido no tiene el formato correcto.");
                        System.out.println("Por favor, seleccione la opcion 2 y escoja un fichero con el formato adecuado.\n");
                        fail = true;
                    }
                }
            }

        }
        return usersList;
    }

    static void printInfo() {
        System.out.println("\nDRIVER DE LA CLASE KMEANS\n");
        System.out.println("La clase kmeans implementa dicho algoritmo. Para ejecutar el algoritmo necesitamos un conjunto\n" +
                "de usuarios y sus valoraciones.\n" +
                "Por defecto vamos a probar el fichero que se encuentra en la ruta: Entradas_CSV/ratings.db.csv.\n" +
                "Los archivos que se deseen probar en el algoritmo han de contener los siguientes valores separados\n" +
                "por comas: [userId, itemId, rating].");
        System.out.println("\nOpciones del driver:\n");
        System.out.println("    1. Seleccionar fichero por defecto\n    2. Seleccionar otro fichero\n    3. Ejecutar" +
                " algoritmo\n    4. Encontrar K óptima\n    5. FINALIZAR PRUEBA\n ");
    }

    static void testFunction(int f){
        switch(f)
        {
            case 1 :
            {
                System.out.println("=====================================================================================");
                System.out.println("El fichero por defecto, en la ruta [Entradas_CSV/ratings.db.csv] es el seleccionado.");
                path = "EXE/Entradas_CSV/ratings.db.csv";
                System.out.println("=====================================================================================");
                break;
            }

            case 2 :
            {
                System.out.println("=====================================================================================");
                System.out.print("Introduce la ruta del nuevo fichero: ");
                Scanner s = new Scanner(System.in);
                boolean b = true;
                String npath;
                do{
                    npath = s.next();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(npath));
                        b = true;
                    } catch (Exception e) {
                        b = false;
                        System.out.println(e);
                        System.out.println("\nPor favor, introduce una ruta valida:");
                    }

                }while(!b);
                path = npath;
                System.out.println("=====================================================================================");
                break;
            }

            case 3:
            {
                System.out.println("================================================================================================");
                System.out.print("Selecciona el valor de K (numero de clusters del algoritmo): ");
                Scanner s = new Scanner(System.in);
                int k = s.nextInt();
                System.out.println("Ejecucion del algoritmo con el fichero y la k seleccionados: ");
                Kmeans kmeans = new Kmeans();
                kmeans.run(readData(path), k);
                kmeans.printAllClusters();
                break;
            }
            case 4: {
                Kmeans k = new Kmeans();
                for(int i = 1; i <= 10; ++i) {
                    k.run(readData(path),i);
                    System.out.println(i + " " + k.obtainWCSS());
                }
                break;
            }
            case 5: break;

            default : System.out.print("Porfavor introduce un valor correcto\n ");
        }
    }


    /** Main del driver.
     *  Prueba 4 casos:
     *      - Llamar al algoritmo con más clusters que usuarios.
     *      - Llamar al algoritmo pasandole 0 usuarios.
     *      - Llamar al algoritmo con k = 0 (0 clusters).
     *      - Llamar al algoritmo con un fichero de datos real.
     */
    public static void main(String[] args){
        printInfo();
        Scanner s = new Scanner(System.in);
        int f;
        do{
            System.out.println("\nSelecciona funcion a probar: ");
            f = s.nextInt();
            testFunction(f);

        }while(f != 5);
    }
}
