package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/** \brief Clase que implementa el algoritmo Kmeans.
 *  @author Roberto Amat
 */
public class Kmeans {
    /** ArrayList que contiene todos los clusters.
     * @see Cluster
     */
    private ArrayList<Cluster> clusters;
    /** Mapa con el ID de un usuario como Key y dicho usuario como value.
     * Contiene todos los usuarios sobre los que se aplica el algoritmo.
     * @see User
     */
    private Map<Integer, User> users;
    /** Parámetro que determina el número de clusters que va a generar el algoritmo.
     */
    private int k;

    /** Función que crea aleatoriamente los k clusters iniciales con los que parte
     *  el algoritmo.
     */
    private void assignKCentroids(){
        Random myRandom = new Random();
        ArrayList<User> nuevoscentroids = new ArrayList<User>();
        int indiceNuevocentroid = 0;

        for(int i = 0; i < k; ++i) {
            User aux;
            Cluster c = new Cluster();
            ArrayList<Integer> keysAsArray = new ArrayList<Integer>(users.keySet());
            do{
                indiceNuevocentroid = myRandom.nextInt(users.size());
                aux = users.get(keysAsArray.get(myRandom.nextInt(keysAsArray.size())));
            }while (nuevoscentroids.contains(aux));
            c.addUser(aux);
            c.setCentroid(aux);
            clusters.add(c);
            nuevoscentroids.add(aux);
        }
    }


    /** Función que asigna un nuevo usuario al cluster con el centroide más
     * cercano a él. Devuelva true si ha cambiado el centroide y false en caso contrario.
     * @param user Usuario a asignar.
     */
    private void asignUserToCluster(User user) {
        float dMin = 0;
        int iMin = 0;
        for(int i = 0; i < k; ++i) {
            User centroidActual = clusters.get(i).getcentroid();
            float distanciaActual = user.calculateSimilarity(centroidActual);
            if(distanciaActual > dMin) {
                dMin = distanciaActual;
                iMin = i;
            }
        }
        user.setNumCluster(iMin);
        clusters.get(iMin).addUser(user);
    }

    /** Función que ejecuta el algoritmo.
     * @param k Numero de clusters que tendra el algoritmo.
     */
    public void run(Map <Integer, User> users, int k) {
        this.k = k;
        clusters = new ArrayList<Cluster>();
        this.users = users;

            assignKCentroids();
            // Assignas el resto de usuarios a los clusters correspondientes y recalculamos el centroid
            boolean equilibrated = false;
            int i = 0;
            float WCSS = 0;
            float WCSSold = Float.POSITIVE_INFINITY;
            float WCSSold2 = Float.POSITIVE_INFINITY;
            float threshold = (float)0.995;
            boolean first = true;
            while(!equilibrated && WCSSold2*threshold > WCSS && WCSSold*threshold > WCSS && i < 10) {
                WCSSold2 = WCSSold;
                WCSS = 0;
                WCSSold = 0;
                ArrayList<User> centroids = new ArrayList<>();
                for(int j = 0; j < k; ++j) {
                    clusters.get(j).getCluster().removeAll(clusters.get(j).getCluster());
                    clusters.get(j).clearSumDistances();
                    centroids.add(clusters.get(j).getcentroid());
                    if(first) {
                        WCSSold = Float.POSITIVE_INFINITY;
                        first = false;
                    } else WCSSold += clusters.get(j).getWCSS();
                }

                for (Map.Entry<Integer, User> entry : users.entrySet()) {
                    asignUserToCluster(entry.getValue());
                }
                equilibrated = true;
                for(int j = 0; j < k; ++j) {
                    clusters.get(j).recalculateCentroid();
                    if(clusters.get(j).getcentroid().getUserID() != centroids.get(j).getUserID()) equilibrated = false;
                    clusters.get(j).calculateWCSS();
                    WCSS += clusters.get(j).getWCSS();
                }

            }
    }

    public float obtainWCSS() {
        int sum = 0;
        for(int i = 0; i < k; ++i) {
            clusters.get(i).calculateWCSS();
            sum += clusters.get(i).getWCSS();
        }
        return (float) sqrt(sum);
    }

    /** Constructora de la clase.
     */
    public Kmeans() {

    }

    /** Función que devuelve los clusters.
     * @return Clusters generados por el algoritmo.
     */
    public ArrayList<Cluster> getClusters() {
        return clusters;
    }


    /** Función que imprime todos los clusters generados por el algoritmo.
     * @see Cluster
     */
    public void printAllClusters() {
        for (int i = 0; i < clusters.size(); ++i) {
           // System.out.println("Cluster "+i+", formado por " + clusters.get(i).getCluster().size() + " usuarios.\n");
            //clusters.get(i).printCluster();
            //System.out.println("\n============================================================================================================\n");
        }
    }
}
