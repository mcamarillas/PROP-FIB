package FONTS.src.domini.model;

import java.util.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

/** \brief Clase que implementa el algoritmo SlopeOne.
 *  @author  Marc Camarillas
 */
public class SlopeOne {
    /** User sobre el que se quiere hacer la recomendación.
     * @see User
     */
    private User user;
    /** Valor máximo que se le puede asignar a una valoración.
     */
    private float maxValue;
    /** Mapa que contiene como llave el ID de un item y com valor la lista de usuarios que lo han valorado.
     * @see User
     */
    private Map<Integer,ArrayList<User>> itemValoratedBy;
    /** ArrayList que contiene las predicciones de todos los items que no ha valorado el usuario sobre el que se quiere hacer la valoracoión.
     * @see Cluster
     */
    private ArrayList<myPair> predictions;

    // Constructora

    /** Constructora de la clase.
     */
    public SlopeOne() {
    }

    // Métodos Privados

    /**
     * Calcula la intersección entre los usuarios que hay en la lista l1 y los usuarios que hay en la lista l2 y además pertenecen al mismo clúster.
     * @param l1 ArrayList de usuarios que ha valorado un item
     * @param l2 ArrayList de usuarios que ha valorado un item
     * @param numCluster Número de clúster al que pertenece usuario actual
     * @return ArrayList de Usuarios de la Intersección
     */
    private ArrayList<User> intersection(ArrayList<User> l1, ArrayList<User> l2, int numCluster) {
        ArrayList<User> l3 = new ArrayList<User>();
        for (User user1 : l1) {
            if (user1.getNumCluster() == numCluster) {
                for (User user2 : l2) {
                    if (user1.getUserID() == user2.getUserID() && user2.getNumCluster() == numCluster) {
                        l3.add(user1);
                    }
                }
            }
        }
        return l3;
    }

    /**
     * Calcula la desviación de dos items para cada usuario que ha valoradpo ambos.
     * @param IDitemI ID del Item I
     * @param IDitemJ ID del Item J
     * @param usersIJ Usuarios que han valorado tanto el Item I, como el Item J
     * @return Desviación entre los dos Items
     */
    private float calculateDesviation(int IDitemI, int IDitemJ, ArrayList<User> usersIJ) {
        float sumTotal = 0;
        for (User user : usersIJ) {
            float valorationUserI = user.searchUsedItem(IDitemI).getValoracio();
            float valorationUserJ = user.searchUsedItem(IDitemJ).getValoracio();

            sumTotal += (valorationUserJ - valorationUserI);
        }
        return sumTotal/usersIJ.size();
    }

    /**
     * Calcula la media de todas las desviaciones
     * @param user Usuario sobre el que se hace la predicción
     * @return devuelve la media de valoraciones del usuario user
     */
    private float calculateValorationMean(User user) {
        ArrayList<valoratedItem> usedItems = user.getValoratedItems();
        float sum = 0;
        for(valoratedItem u: usedItems) sum += u.getValoracio();
        return sum/usedItems.size();
    }

    /**
     * Suma media de las desviaciones de todos los items I que ha valorado el usuario, respecto del item J.
     * En caso de que nadie haya valorado ambos items la desviación será -infinito debido a que no podremos hacer la predicción.
     * @param user Usuario sobre el que se hace la predicción
     * @param IDitemJ ID del Item sobre el que se quiere hacer la predicción(no valorado por el usuario.
     * @return devuelve la media de las desviaciones.
     */
    private float calculateDesviationMean(User user, int IDitemJ) {
        ArrayList<valoratedItem> usedItems = user.getValoratedItems();
        float num = 0;
        int count = 0;
        for(valoratedItem itemI : usedItems) {
            int IDitemI =  itemI.getItem().getID();
            if(IDitemI != IDitemJ) {
                ArrayList<User> usersIJ = getIntersaction(IDitemI,IDitemJ);
                if(usersIJ.size() != 0) {
                    num += calculateDesviation(IDitemI, IDitemJ, usersIJ);
                    count++;
                }
            }
        }
        if(count == 0) return -maxValue;
        return num/count;
    }

    /**
     * Calcula los usuarios que han valorado tanto el Item I como el Item J y que pertenecen al mismo clúster.
     * @param IDitemI ID del Item I
     * @param IDitemJ ID del Item J
     * @return ArrayList los ususarios de la Intersección
     */
    private ArrayList<User> getIntersaction(int IDitemI, int IDitemJ) {
        ArrayList<User> usersI = itemValoratedBy.get(IDitemI);
        ArrayList<User> usersJ = itemValoratedBy.get(IDitemJ);

       return intersection(usersI, usersJ, user.getNumCluster());
    }

    /**
     * Calcula la predicción que hará el usuario user sobre todos los items que no ha valorado.
     * @param user usuario sobre el que se quiere hacer la predicción
     */
    private void slopeOneAlgorithm(User user) {
        float meanValoration = calculateValorationMean(user);
        this.predictions = new ArrayList<myPair>();
        //predecir todos los que no tiene valoracion
        for (Map.Entry<Integer, ArrayList<User>> item : itemValoratedBy.entrySet()) {
            //si el item no esta valorado por el usuario ejecutar predicción
            if (!item.getValue().contains(user)) {
                float valoration = meanValoration + calculateDesviationMean(user, item.getKey()) ;
                predictions.add(new myPair(item.getKey(), max(-maxValue, valoration)));
            }
        }
    }

    /**
     * Algoritmo de ordenación QuickSort
     * @param A ArrayList que queremos ordenar
     * @param izq posición donde queremos empezar a ordenar
     * @param der posición donde queremos dejar de ordenar
     */
    private static void quicksort(ArrayList<myPair> A, int izq, int der) {
        float pivote = A.get(izq).getValoration();
        int pivoteID = A.get(izq).getItemID();
        int i=izq;
        int j=der;
        float auxV;
        int auxID;
        while(i < j){
            while( A.get(i).getValoration() >= pivote && i < j) i++;
            while( A.get(j).getValoration() < pivote) j--;
            if (i < j) {
                auxV = A.get(i).getValoration();
                auxID = A.get(i).getItemID();
                myPair mp = new myPair(A.get(j).getItemID(),A.get(j).getValoration());
                A.set(i,mp);
                myPair mp1 = new myPair(auxID,auxV);
                A.set(j,mp1);
            }
        }
        myPair mp = new myPair(A.get(j).getItemID(),A.get(j).getValoration());
        A.set(izq,mp);
        myPair piv = new myPair(pivoteID,pivote);
        A.set(j,piv);

        if(izq < j-1)
            quicksort(A,izq,j-1);
        if(j+1 < der)
            quicksort(A,j+1,der);
    }

    // Metodos Públicos

    /**
     * Imprime las predicciones que se han hecho sobre el usuario actual.
     */
    public void printResults() {
        try {
            for (myPair prediction : predictions) {
                //System.out.println("Valoracion estimada para el item " + prediction.getItemID() +
                //        ": " + prediction.getValoration());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Ejecuta el algoritmo SlopeOne y ordena las predicciones en orden decreciente
     * @param user usuario sobre el que se quiere hacer la recomendación
     * @param itemValoratedBy Conjunto de items y los usuarios que lo han valorado
     * @param maxValue valor máximo que puede tener una recomendación
     * @return predicciones ordenadas
     */
    public ArrayList<myPair> getPredictions(User user, Map<Integer,ArrayList<User>> itemValoratedBy, float maxValue){
        this.user = user;
        this.maxValue = maxValue;
        this.itemValoratedBy = itemValoratedBy;
        this.predictions = new ArrayList<>();
        try {
            slopeOneAlgorithm(user);
            quicksort(predictions, 0, predictions.size() - 1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return predictions;
    }
}
