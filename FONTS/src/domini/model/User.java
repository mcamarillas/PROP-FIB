package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

/** \brief Clase que implementa un usuario.
 *  @author  Marc Camarillas
 */
public class User {
    // Atributos
    /**
     * ID del usuario
     */
    private int userID;
    /**
     * Contraseña del usuario
     */
    private String password;
    /**
     * Rol del usuario
     * @see TipusRol
     */
    private TipusRol Rol;
    /**
     * Items que ha valorado el usuario
     * @see valoratedItem
     */
    private ArrayList<valoratedItem> valoratedItems;
    /**
     * Cluster al que pertenece el usuario
     */
    private int numCluster;

    // Constructoras

    /**
     * Crea un usuario vacio
     */
    public User() {
        this.userID = -2;
        this.valoratedItems = new ArrayList<valoratedItem>();
        this.numCluster = -1;
    }

    /**
     * Crea un usario con un userID, una password y un Rol
     * @param userID  UserID con el que lo queremos inicializar
     * @param password pasword con el que lo queremos inicializar
     * @param Rol rol con el que lo queremos inicializar
     */
    public User(int userID, String password, TipusRol Rol) {
        this.userID = userID;
        this.password = password;
        this.Rol = Rol;
        this.valoratedItems = new ArrayList<valoratedItem>();
        this.numCluster = -1;
    }

    /**
     * Crea un usaurio con un userID, con un password por defecto y rol de usuario
     * @param userID
     */
    public User(int userID) {
        this.userID = userID;
        this.password = String.valueOf(userID);
        this.Rol = TipusRol.Usuari;
        this.valoratedItems = new ArrayList<valoratedItem>();
        this.numCluster = -1;
    }

    // Métodos Privados

    /**
     * Calcula la intersección entre dos listas de items valorados según su ID.
     * @param l1 lista de Items valorados
     * @param l2 lista de Items valorados
     * @return ArrayList de ID de la Intersección.
     */
    private ArrayList<Integer> intersection(ArrayList<valoratedItem> l1, ArrayList<valoratedItem> l2) {
        ArrayList<Integer> l3 = new ArrayList<Integer>();
        for (valoratedItem valoratedItem1 : l1) {
            for (valoratedItem valoratedItem2 : l2) {
                if (valoratedItem1.getItem().getID() == valoratedItem2.getItem().getID()) {
                    l3.add(valoratedItem1.getItem().getID());
                }
            }
        }
        return l3;
    }

    // Métodos Públicos

    // Getters

    /**
     * @return Devuelve el ID del usuario
     */
    public int getUserID() {
        return userID;
    }
    /**
     * @return Devuelve el password del usuario
     */
    public String getPassword() {
        return password;
    }
    /**
     * @return Devuelve el rol del usuario
     */
    public TipusRol getRol() { return Rol; }
    /**
     * @return Devuelve los items valorados por usuario
     */
    public ArrayList<valoratedItem> getValoratedItems() { return valoratedItems; }
    /**
     * @return Devuelve clúster al que pertenece el usuario
     */
    public int getNumCluster() {
        return numCluster;
    }

    // Setters

    /**
     * Cambia el valor del userID por el que le pasamos.
     * @param userID nuevo userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /**
     * Cambia el valor del password por el que le pasamos.
     * @param password nueva password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Cambia el valor del rol por el que le pasamos.
     * @param Rol nuevo Rol
     */
    public void setRol(TipusRol Rol) { this.Rol = Rol; }
    /**
     * Cambia el valor de los valoratedItems por el que le pasamos.
     * @param valoratedItems nuevo valoratedItems
     */
    public void setValoratedItems(ArrayList<valoratedItem> valoratedItems) { this.valoratedItems = valoratedItems; }
    /**
     * Cambia el valor del numCluster por el que le pasamos.
     * @param numCluster nuevo numCluster
     */
    public void setNumCluster(int numCluster) {
        this.numCluster = numCluster;
    }

    // Otros

    /**
     * Añade un Item valorado a la lista de valoratedItems
     * @param itemID ID del item que queremos añadir
     * @param valoration valoración del item que queremos añadir
     */
    public void addvaloratedItem(int itemID, float valoration) {
        Item i = new Item(itemID);
        valoratedItem usedItem = new valoratedItem(i, valoration);
        //falta comprobar que no se repitan
        valoratedItems.add(usedItem);
    }

    /**
     * Calcula la similaridad que tiene con otro usuario mediante la formula de cosine-similarity
     * @param user2 usuario sobre el que queremos ver su similaridad
     * @return valor entre 0 y 1, donde 1 es muy similar y 0 no es nada similar.
     */
    public float calculateSimilarity(User user2) {
        ArrayList<valoratedItem> valoratedItems2 = user2.getValoratedItems();
        ArrayList<Integer> intersectionItems = intersection(valoratedItems,valoratedItems2);
        float sumProdValorations = 0;
        if(intersectionItems.size() == 0) sumProdValorations = 1;
        for(int i = 0; i < intersectionItems.size(); ++i) {
            int itemID = intersectionItems.get(i);
            float valoration1 = searchUsedItem(itemID).getValoracio();
            float valoration2 = user2.searchUsedItem(itemID).getValoracio();
            sumProdValorations += valoration1*valoration2;
        }
        float sumSquareValoration1 = 0;
        float sumSquareValoration2 = 0;
        for(int i = 0; i < valoratedItems.size(); ++i) {
            float valoration1 = valoratedItems.get(i).getValoracio();
            sumSquareValoration1 += valoration1*valoration1;
        }
        for(int i = 0; i < valoratedItems2.size(); ++i) {
            float valoration2 = valoratedItems2.get(i).getValoracio();
            sumSquareValoration2 += valoration2*valoration2;
        }

        return (sumProdValorations)/(float)(sqrt(sumSquareValoration1)*sqrt(sumSquareValoration2));
    }

    /**
     * Busca si existe el item con ID itemID
     * @param itemID Id del item que queremos encontrar
     * @return valoratedItem si existe, null si no existe
     */
    public valoratedItem searchUsedItem(int itemID) {
        for(int i = 0; i < valoratedItems.size(); ++i) {
            if (valoratedItems.get(i).getItem().getID() == itemID) return valoratedItems.get(i);
        }
        return null;
    }

    // Prints

    /**
     * Imprime los items que ha valorado el usuario
     */
    public void printUsedItems() {
        for(int i = 0; i < valoratedItems.size(); ++i) {
            System.out.println("Item " + valoratedItems.get(i).getItem().getID() + " valorated with " + valoratedItems.get(i).getValoracio());
        }
    }
}
