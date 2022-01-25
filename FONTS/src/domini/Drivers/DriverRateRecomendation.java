package FONTS.src.domini.drivers;
import FONTS.src.domini.model.*;
import FONTS.src.persistencia.ControladorPersistenciaRatings;


import java.util.*;

/** \brief Driver de la clase RateRecomendation.
 *  @author Oriol Cuellar
 */
public class DriverRateRecomendation {

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

    /** Main del Driver.
     *      - Lee valoraciones de un fichero CSV dado el path.
     *      - Crea en los maps correspondientes, los usuarios i los items que ha leido.
     *      - Llama al algoritmo K-means y Slope-One
     *      - Evalua el resultado con el algortimo
     * @see Kmeans
     * @see SlopeOne
     * @see  RateRecomendation
     */
    public static void main(String[] args) {
        printInfo();

        //se leen valoraciones
        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();
        readed_ratings=leer_datos(readed_ratings);
        rellenar_listas(readed_ratings);

        //kmeans
        boolean leerk=false;
        int k=1;
        while(!leerk){
            System.out.println("Introduce un valor para el numero de clusters");
            Scanner aux = new Scanner(System.in);
            k= aux.nextInt();
            if (k>0 && k<usersList.keySet().size()-1) leerk=true;
        }
        Kmeans kmeans = new Kmeans();
        kmeans.run(usersList,k);

        //slope one
        SlopeOne So = new SlopeOne();

        //get user
        Scanner s = new Scanner(System.in);
        boolean trobat=false;
        User u = new User();
        while (!trobat){
            mostrar_opciones();
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

        ArrayList<myPair> predictions = So.getPredictions(u,item_valorated_by,10);

        //Algortimo de valorar recomendaciones
        try {
            RateRecomendation recomendation = new RateRecomendation();
            float result = recomendation.execute(predictions, predictions);
            System.out.println("\n\n La valoracion de la recomendacion: " + result);
        }
        catch (Exception e){
            System.out.println(e);
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
    /** Función que imprime informacion sobre el driver.
     */
    static void printInfo() {
        System.out.println("=====================================================================================");
        System.out.println("\nDRIVER DE LA CLASE RATE RECOMENDATION\n");
        System.out.println("    La clase Rate Recomendation evalua la salida de los algoritmos K-Means y Slope-One.");
        System.out.println("    Esta salida la redirige a la funcion execute de la clase Rate Recomendation");

    }
    /** Función que muestra las opciones del usuario para introducir usuarios.
     */
    static void mostrar_opciones() {
        System.out.println("=====================================================================================");
        System.out.println("\nOPCIONES PARA ELEGIR EL USUARIO SOBRE EL QUE HACER LA PREDICCION\n");
        System.out.println("    - 1 ");
        System.out.println("         Usuario aleatorio");
        System.out.println("    - 2 ");
        System.out.println("         Usuario introducido por teclado");
        System.out.println("    - 3 ");
        System.out.println("         Mostrar todos los usuarios\n\n");

    }


}

