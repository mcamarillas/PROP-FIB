package FONTS.src.domini.model;

import FONTS.src.domini.controladors.ControladorDomini;
import FONTS.src.domini.controladors.ControladorPresentacion;
import FONTS.src.domini.drivers.DriverKND;
import FONTS.src.domini.exceptions.FileNotExistsException;
import FONTS.src.persistencia.ControladorPersistenciaRatings;

import javax.swing.*;
import java.util.*;

/** \brief Clase que implementa el Main.
 *  @author Oriol Cuellar
 */
public class Main {

    /** Mapa que contiene como llave el ID de un Item y com valor los usuarios que lo han valorado.
     *  Se utiliza como parametro para el algoritmo Slope-One
     *  @see Item
     *  @see User
     *  @see SlopeOne
     */
    static Map<Integer, ArrayList<User>> item_valorated_by= new HashMap<Integer, ArrayList<User>>();

    /** Mapa que contiene como llave el ID de un User y com valor el objeto User.
     *  Se utiliza como parametro para el algoritmo K-Means y Slope-One
     *  @see User
     *  @see Kmeans
     *  @see SlopeOne
     */
    static Map<Integer, User> usersList= new HashMap<Integer, User>();
    private static Object FileNotExistsException;

    /** Main de la clase Main.
     *   Ejecuta:
     *      - K-means
     *      - Slope-One
     *      - Evalua el resultado con el algortimo
     *      - K Nearest Neighbour
     * @see Kmeans
     * @see SlopeOne
     * @see RateRecomendation
     * @see K_Neareast_Neightbour
     */
    public static void main(String[] args) {
        ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();
        CtrlPres.changeLogInView(0,0);
        /*try {
            ControladorDomini c = ControladorDomini.getInstance();
            c.login("-1", "-1");
            c.loadItems("./EXE/Entradas_CSV/items.csv");
            c.loadRates("./EXE/Entradas_CSV/ratings.db.csv");
            c.crearCarpeta();
            c.saveItems();
            c.enseñaItemTypeAtr();

        } catch (Exception e){
            System.out.println(e);
        }*/


        /*printInfo();
        Scanner s = new Scanner(System.in);
        boolean escogido=false;
        String op = "";
        //escoger algoritmo
        while (!escogido) {
            mostrar_opciones();
            op=s.next();
            if (op.equals("1") || op.equals("2") || op.equals("3") || op.equals("4") || op.equals("5") || op.equals("6")){
                escogido=true;
            }
            else{
                System.out.print("Porfavor introduce un valor correcto\n ");
            }
        }
        try {

            //ejecutar algoritmo
            if (op.equals("1")) {
                execute_kmeans();
            }
            if (op.equals("2")) {
                execute_Slope_One();
            }
            if (op.equals("3")) {
                execute_RateRecomendation();
            }
            if (op.equals("4")) {
                execute_KNeares_Neighbour();
            }
            if (op.equals("5")) {
                programa_oriol();
            }
            if(op.equals("6")) {
                CtrlPres.changeLogInView(100, 100);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }*/
    }

    /** Función que imprime informacion de la clase main.
     *  el algoritmo.
     */
    static void printInfo() {
        System.out.println("\nClase MAIN\n");
        System.out.println("    Permite probar los algoritmos KMeans, Slope-One, K Nearest Neighbour y \n    dar una valoración a la recomendación de items dada por los algoritmos");
    }
    /** Función que imprime informacion de las opciones que se pueden ejecutar.
     */
    static void mostrar_opciones() {
        System.out.println("=====================================================================================");
        System.out.println("\nQUE DESEAS EJECUTAR\n");
        System.out.println("    - 1 ");
        System.out.println("         KMeans");
        System.out.println("    - 2 ");
        System.out.println("         Slope-One y KMeans");
        System.out.println("    - 3 ");
        System.out.println("         Valorar una recomendacion");
        System.out.println("    - 4 ");
        System.out.println("         K Nearest Neighbour\n\n");
        System.out.println("    - 5 ");
        System.out.println("         programa oriol\n\n");

    }
    /** Función que ejecuta el K Nearest Neighbour.
     */
    static void execute_KNeares_Neighbour(){
        //DriverKND.main(null);
    }
    /** Función que ejecuta el K Means.
     */
    static void execute_kmeans(){
        System.out.println("=====================================================================================");
        System.out.println("EJECUTANDO K MEANS");

        //leer datos
        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();
        readed_ratings=leer_datos(readed_ratings);
        rellenar_listas(readed_ratings);

        Scanner s = new Scanner(System.in);
        boolean escogido=false;
        int k=1;
        //escoger valor K
        while (!escogido) {
            System.out.print("Introduce el valor de la K (numero de clusters del algoritmo)");
            k=s.nextInt();
            if (k>0 && k<usersList.keySet().size()){
                escogido=true;
            }
            else{
                System.out.print("Porfavor introduce un valor correcto\n ");
            }
        }
        Kmeans kmeans = new Kmeans();
        kmeans.run(usersList,k);

        kmeans.printAllClusters();
    }
    /** Función que ejecuta el Slope OneK.
     */
    static ArrayList<myPair> execute_Slope_One(){
        System.out.println("=====================================================================================");
        System.out.println("PRIMERO EJECUTAR KMEANS");
        execute_kmeans();
        System.out.println("=====================================================================================");
        System.out.println("EJECUTANDO SLOPE-ONE");

        SlopeOne So = new SlopeOne();

        Scanner s = new Scanner(System.in);
        boolean trobat=false;
        User u = new User();
        while (!trobat){
            mostrar_opciones_slop();
            String op = s.next();
            switch (op) {
                case "1": {//usuario aleatorio
                    System.out.println("=====================================================================================");
                    Random r = new Random();
                    int valorDado = r.nextInt(usersList.size());
                    int id=(int ) usersList.keySet().toArray()[valorDado];
                    System.out.println("Usuario escogido: " + id);
                    u = usersList.get(id);
                    trobat = true;
                    break;
                }
                case "2": {//teclado
                    System.out.println("=====================================================================================");
                    System.out.println("Introduce el ID del usuario");
                    int tec = s.nextInt();
                    if (!usersList.containsKey(tec)) {
                        System.out.println("Usuario no existe");
                        break;
                    }
                    u = usersList.get(tec);
                    trobat = true;
                    break;
                }
                case "3": {//mostrar todos
                    System.out.println("=====================================================================================");
                    Set keys = usersList.keySet();
                    for (Iterator i = keys.iterator(); i.hasNext(); ) {
                        int key = (int) i.next();
                        System.out.println(key);
                    }
                    break;
                }

                default:
                    System.out.println("=====================================================================================");
                    System.out.print("Porfavor introduce un valor correcto\n ");
            }

        }
        ArrayList<myPair> predictions =So.getPredictions(u,item_valorated_by,10);
        So.printResults();
        return predictions;

    }
    /** Función que ejecuta Rate Recomendation.
     */
    static void execute_RateRecomendation() throws Exception{
        System.out.println("=====================================================================================");
        System.out.println("EJECUTANDO RATE RECOMENDATION");

        ArrayList<myPair> pred=execute_Slope_One();
        try {

            RateRecomendation rec = new RateRecomendation();
            float result = rec.execute(pred, pred);
            System.out.println("\n\n La valoracion de la recomendacion: " + result);
        }
        catch (Exception e){
            throw e;
        }

    }

    /** Función que rellena una lista de usuarios y una lista de items con los usuarios que lo han valorado.
     *  @param readed_ratings .
     */
    static void rellenar_listas(ArrayList<Vector<String>> readed_ratings){
        for (Vector<String> vs : readed_ratings) {
            //parte del Usuario
            if (usersList.containsKey(Integer.valueOf(vs.get(0)))) {//Usuario ya existe
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                if (usuari.searchUsedItem(Integer.parseInt(vs.get(1))) == null) {//No existe el item en sus valoraciones
                    usuari.addvaloratedItem(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));

                }
            } else {//Usuari no existe, se crea, añadimos valoracion a su lista, añadimos valoracion itemUsatList
                User usuari = new User(Integer.parseInt(vs.get(0)));
                usuari.addvaloratedItem(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));
                usersList.put(Integer.valueOf(vs.get(0)), usuari);
            }
            //parte del item
            if (item_valorated_by.containsKey(Integer.valueOf(vs.get(1)))){//Existe item al map
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                item_valorated_by.get(Integer.valueOf(vs.get(1))).add(usuari);
            }
            else{//NO existe item al map
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                ArrayList <User> au = new ArrayList<User>();
                au.add(usuari);
                item_valorated_by.put( Integer.valueOf(vs.get(1)), au);
            }
        }
    }

    /** Función que lee valoraciones.
     *  @param readed_ratings .
     *  @return ArrayList de vectores de Strings.Cada vector contiene 2 posiciones, UserId, ItemId, rating
     */
    static ArrayList<Vector<String>> leer_datos(ArrayList<Vector<String>> readed_ratings){
        boolean leido= false;
        while (!leido) {
            try {

                System.out.println("\n- INTRODUCE el path al CSV de valoraciones que desea leer: ");
                Scanner s = new Scanner(System.in);
                String path;
                path = s.next();
                ControladorPersistenciaRatings reader = new ControladorPersistenciaRatings();
                readed_ratings = reader.Lector_Ratings(path);
                leido=true;
            } catch (Exception e) {
                System.out.println("\n\n- ERROR");
                System.out.println(e);
                System.out.println("\n- Prueba con una entrada como \n   ratings.test.known.csv \n   o \n   nombre_carpeta/ratings.test.known.csv (si estubiera en carpeta)");
            }
        }
        return readed_ratings;
    }
    /** Función que muestra las opciones que se pueden hacer en el slope One para elegir usuario.
     */
    static void mostrar_opciones_slop() {
        System.out.println("=====================================================================================");
        System.out.println("\nOPCIONES PARA ELEGIR EL USUARIO SOBRE EL QUE HACER LA PREDICCION\n");
        System.out.println("    - 1 ");
        System.out.println("         Usuario aleatorio");
        System.out.println("    - 2 ");
        System.out.println("         Usuario introducido por teclado");
        System.out.println("    - 3 ");
        System.out.println("         Mostrar todos los usuarios\n\n");

    }

    static void programa_oriol(){
        ControladorDomini dom = ControladorDomini.getInstance();
        try {

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}