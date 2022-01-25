package FONTS.src.domini.drivers;

import FONTS.src.domini.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/** \brief Driver de la clase SlopeOne.
 *  @author Marc Camarillas
 */
public class DriverSlopeOne{

    /** Mapa que contiene como llave el ID de un usuario y com valor dicho usuario.
     *  Se utiliza para relizar las pruebas con los datos recibidos a través del fichero.
     */
    static Map<Integer,User> usersList = new HashMap<Integer,User> ();
    /** Mapa que contiene como llave el ID de un item y com valor la lista de usuarios que lo han valorado.
     *  Se utiliza para relizar las pruebas con los datos recibidos a través del fichero.
     */
    static Map<Integer,ArrayList<User>> itemValoratedBy = new HashMap<Integer,ArrayList<User>>();

    static SlopeOne So;

    /** Función que lee el archivo que contiene los datos a probar.
     * @param csvFile Dirección del archivo que contiene los datos-
     * @return Vector que almacena los datos leídos.
     */
    public static ArrayList<Vector<String>> Lector_SlopeOne_Test(String csvFile) {
        ArrayList<Vector<String>> ratings = new ArrayList<Vector<String>>();
        BufferedReader br = null;
        String line = "";
        //Se define separador ","
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(cvsSplitBy);
                //Imprime datos.
                for(int i = 0; i < datos.length; ++i) {
                    //System.out.print(datos[i] + ", ");
                }
                System.out.println();
                if(!first) {
                    Vector<String> dentro = new Vector<String>();
                    dentro.add(datos[0]);
                    dentro.add(datos[1]);
                    dentro.add(datos[2]);
                    dentro.add(datos[3]);
                    ratings.add(dentro);
                }
                first = false;
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No existe el archivo o el directorio, por favor introduzca de nuevo el fichero.");
            Scanner S = new Scanner(System.in);
            readData(S.next());
        }
        return ratings;
    }
    /** Función que se encarga de leer los datos y almacenarlos en su lugar correspondiente.
     */
    public static void readData(String csvFile) {
        ArrayList<Vector<String>> readed_ratings = Lector_SlopeOne_Test(csvFile);

        TipusRol t = TipusRol.Usuari;
        try {
            for (Vector<String> vs : readed_ratings) {
                if (usersList.containsKey(Integer.valueOf(vs.get(0)))) {//existeix
                    User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                    if (usuari.searchUsedItem(Integer.parseInt(vs.get(1))) == null) {//no existe el item en sus valoraciones
                        usuari.addvaloratedItem(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));

                    }
                } else {//no existeix, es crea, afegim valoracio a la seva llista, afegim valoracio allista itemUsatList
                    User usuari = new User(Integer.parseInt(vs.get(0)));
                    usuari.setNumCluster(Integer.parseInt(vs.get(3)));
                    usuari.addvaloratedItem(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));
                    usersList.put(Integer.valueOf(vs.get(0)), usuari);
                }
                //parte del item
                if (itemValoratedBy.containsKey(Integer.valueOf(vs.get(1)))) {//existeix item al map
                    User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                    itemValoratedBy.get(Integer.valueOf(vs.get(1))).add(usuari);
                } else {//NO existeix item al map
                    User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                    ArrayList<User> au = new ArrayList<User>();
                    au.add(usuari);
                    itemValoratedBy.put(Integer.valueOf(vs.get(1)), au);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("El fichero de datos es incorrecto, por favor introduzca de nuevo el fichero.");
            Scanner S = new Scanner(System.in);
            readData(S.next());
        }
    }

    static void testFunction(int f) {
        switch (f) {
            case 1: {
                itemValoratedBy = new HashMap<Integer,ArrayList<User>>();
                System.out.print("Introduzca el directorio del fichero de datos que quiere leer para crear itemValoratedBy: ");
                Scanner S = new Scanner(System.in);
                readData(S.next());
                System.out.println("=============================================================");
                System.out.println("Se ha cargado correctamente el conjunto de datos deseado");
                System.out.println("=============================================================");
                break;
            }
            case 2: {
                itemValoratedBy = new HashMap<Integer,ArrayList<User>>();
                readData("EXE/Entradas_CSV/SlopeOneTest.csv");
                System.out.println("=============================================================");
                System.out.println("Se ha cargado correctamente el conjunto de datos por defecto");
                System.out.println("=============================================================");
                break;
            }
            case 3: {
                System.out.println("=========================================");
                System.out.println("Ejecuta el algoritmo de SlopeOne.");
                System.out.println("=========================================");
                System.out.print("Introduzca el ID del usuario al que quiere predecir sus valoraciones: ");
                Scanner S = new Scanner(System.in);
                int userID = S.nextInt();
                User user = usersList.get(userID);
                System.out.print("Introduzca el valor máximo de las valoraciones: ");
                int maxValue = S.nextInt();
                So.getPredictions(user,itemValoratedBy,maxValue);
                System.out.println();
                System.out.println("===========================================");
                System.out.println("El aloritmo se ha ejecutado correctamente");
                System.out.println("===========================================");
                break;
            }
            case 4: {
                System.out.println("======================================");
                System.out.println("Imprime las predicciones del usuario");
                System.out.println("=====================================");
                So.printResults();
                break;
            }
        }
    }

    static void printInfo() {
        System.out.println("\nDRIVER DE LA CLASE SLOPE ONE\n");
        System.out.println("La clase SlopeOne ejecuta dicho algoritmo y te devuelve para cada item que no ha valorado el usuario unas predicciones");
        System.out.println("\nFunciones de la clase disponibles para probar:\n");
        System.out.println("    1. getPredictions(user,itemValoratedBy,maxValue)\n    2. printResults()\n    ");
        System.out.println();

        System.out.println("==================================================================================================");
        System.out.println("Para que un fichero sea válido debe tener 4 columnas [id_usuari,id_item,puntuacio,num_cluster]");
        System.out.println("==================================================================================================\n");
    }

    /** Main del driver.
        Se le pide al usuario que introduzca el fichero que quiere leer o que use el juego de pruebas por defecto.
        Una vez leido el fichero, se le pide al usuario que introduzca la funcionalidad que quiere probar.
     */
    public static void main(String[] args) {
        So = new SlopeOne();
        printInfo();
        Scanner S = new Scanner(System.in);
        System.out.println("Funcionalidades a probar: \n     1.Cargar Fichero de Datos \n     2.Usar juego de pruebas por defecto  \n     3.getPredictions \n     4.printResults \n     5.FINALIZAR PRUEBA");
        int f;
        do{
            System.out.println("Seleciona funcionalidad a probar: ");
            f = S.nextInt();
            testFunction(f);
            System.out.println();
            System.out.println("Funcionalidades a probar: \n     1.Cargar Fichero de Datos \n     2.Usar juego de pruebas por defecto  \n     3.getPredictions \n     4.printResults \n     5.FINALIZAR PRUEBA");
        }while(f != 5);
    }
}
