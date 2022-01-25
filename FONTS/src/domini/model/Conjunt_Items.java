package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.*;

/** \brief Estructura de datos para guardar Item de forma ordenada por ID y a la vez guarda una matriz con todas las distancias.
 *  @author Jordi Olmo
 */
public class Conjunt_Items {

    //Atributes
    /** ArrayList que contiene todos los Items.
     * @see Item
     */
    private ArrayList<Item> Items;

    /** Matriz de ArrayList de Double que contiene las distancias de un item a todos los otros items, teniendo en cuenta que las posicion coincide con
     la que ocupan en Conjunt_Items tanto en filas, como en columnas. i.e. Si el item ocupa la posicion i en Conjunt_Items la fila i de la matriz,
     corresponde a ese Item y sus distancias. También en cada fila la columna i representa la distancia del item de esa fila al Item de posicion.
     i. Por lo tanto la posicion[i][i] de la matriz tiene valor 0.0.
     */
    private ArrayList<ArrayList<Double> > Distances;

    /** ArrayList que contiene los valores maximos de los actuales ranged_atributes, en las posiciones que no corresponden a un ranged atribute guarda -1.0.
     * @see Item
     */

    private ArrayList<Double> maxims;

    /** ArrayList que contiene los valores minimos de los actuales ranged_atributes, en las posiciones que no corresponden a un ranged atribute guarda -1.0.
     * @see Item
     */

    private ArrayList<Double> minims;

    //Creadora

    /** Creadora de la classe. Crea un Conjunt_Item con la Array ordenada crecientemente por ID de Item. Añade sus distancias y rellena los otros atributos adecuadamente
     * @see Item
     * @param items ArrayList de Items con todos los Item del programa.
     */

    public Conjunt_Items(ArrayList<Item> items) {
        Items = items;
        Collections.sort(Items, new Comparator<Item> () {

            // Used for sorting in ascending order of Item.ID
            public int compare(Item a, Item b)
            {
                return a.getID() - b.getID();
            }
        });
        Distances = new ArrayList<ArrayList<Double>>();
        maxims = new ArrayList<Double>();
        minims = new ArrayList<Double>();

        Item aux = Items.get(0);
        for (int i = 0; i < aux.getValors().size(); ++i){

            if (aux.getTipus().getAtributes().get(i).getType() == "Rang") {

                maxims.set(i, aux.getTipus().getAtributes().get(i).getUpper());
                minims.set(i, aux.getTipus().getAtributes().get(i).getLower());
            }
            else {
                maxims.set(i, -1.0);
                minims.set(i, -1.0);
            }
        }

        initzialitzar_matriu();
    }

    /** Creadora de la classe con una ArraList vacía.
     * @see Item
     */

    public Conjunt_Items() {

        Items = new ArrayList<Item> ();
        Distances = new ArrayList<ArrayList<Double> >();
        maxims = new ArrayList<Double>();
        minims = new ArrayList<Double>();
    }

    //Getters

    /** Devuelve si el Item con este ID está en el Conjunt o no.
     * @see Item
     * @param id ID del Item a buscar.
     */

    public  boolean existeix_item(int id) {

        if (n_Items() == 0) return false;
        return binarySearch(Items, 0, Items.size()-1, id);
    }

    /** Devuelve el item con el id especificado. Si no existe devuelve null
     * @see Item
     * @param id identificador del Item que se quiere obtener.
     */

    public Item get_item(int id) {

        if(existeix_item(id)) {
            int pos = binarySearchPosition(Items, 0, Items.size() - 1, id);
            return Items.get(pos);
        }
        else return null;
    }

    /** Devuelve la posicion del Item especificado, en la ArrayList del conjunt.
     * @see Item
     * @param a Item del que se quiere saber la posicion.
     */

    public int get_position(Item a) {

        return binarySearchPosition(Items, 0, Items.size()-1, a.getID());
    }

    /** Devuelve el número de Item de la conjunt.
     */

    public int n_Items() { return Items.size();}

    /** Devuelve una ArrayList con los Item del Conjunt (Ordenada).
     * @see Item
     */

    public ArrayList<Item> getItems() {
        return Items;
    }

    /** Devuelve la matriz de distancias de todos los items del conjunt
     */

    public ArrayList<ArrayList<Double>> getDistances() { return Distances; }

    /** Devuelve el vector con las distancias del item indicado en el parametro.
     * * @see Item
     * * @param a Item del que se quieren saber sus distancias.
     */

    public ArrayList<Double> getDistancesofItem(Item a) {

        int pos = get_position(a);
        return Distances.get(pos);
    }

    /** Devuelve la distancia entre dos items. Al finalizar, si esta no estaba en la matriz se añade si es que estaban ya inizialidas en la matriz las posiciones correspondientes.
     * @param a  Primer Item.
     * @param b  Segundo Item.
     */

    public Double Distance (Item a , Item b) {

        int pos_a = get_position(a);
        int pos_b = get_position(b);
        if (a.getID() != b.getID()) {

            if (existeix_item(a.getID()) && existeix_item(b.getID())){

                double d = a.Distance(b);
                if (pos_a <= Distances.size() - 1 && pos_b <= Distances.size() - 1) {

                    Distances.get(pos_a).set(pos_b, d);
                    Distances.get(pos_b).set(pos_a, d);
                }
                return d;
            }
            else return -1.0;
        }

        else {

            if (pos_a <= Distances.size() - 1 && pos_b <= Distances.size() - 1)
                Distances.get(pos_a).set(pos_b, 0.0);
            return 0.0;
        }
    }

    //Setters

    /** Define una ArrayList como los Item del Conjunt y la ordena. También calcula la matriz de distancias y actualiza maximos y minimos.
     * @see Item
     */

    public boolean setItems(ArrayList<Item> items) {

        Items = items;
        Collections.sort(Items, new Comparator<Item> () {

            // Used for sorting in ascending order of Item.ID
            public int compare(Item a, Item b)
            {
                return a.getID() - b.getID();
            }
        });

        Boolean nuevo = false;
        if (n_Items() > 0) nuevo = true;

        if(nuevo) {

            Item aux = items.get(0);
            for (int i = 0; i < aux.getValors().size(); ++i){

                if (aux.getTipus().getAtributes().get(i).getType() == "Rang") {

                    maxims.add(i, aux.getTipus().getAtributes().get(i).getUpper());
                    minims.add(i, aux.getTipus().getAtributes().get(i).getLower());
                }
                else {
                    maxims.add(i, -1.0);
                    minims.add(i, -1.0);
                }
            }
        }

        else {

            Item aux = Items.get(0);
            ArrayList<String> valors = aux.getValors();
            Boolean re_needed = true;
            for (int i = 0; i < maxims.size(); ++i) {

                if (maxims.get(i) != -1.0) {

                    Double v_aux = Double.valueOf(valors.get(i));
                    if (v_aux > maxims.get(i) * 1.20) {

                        re_needed = true;
                        maxims.set(i, v_aux);
                    } else if (v_aux < minims.get(i) * 0.80) {

                        re_needed = true;
                        minims.set(i, v_aux);
                    }
                }
            }
            if(re_needed) re_calcul_atriu();
        }

        initzialitzar_matriu();
        return true;
    }

    //Operacions

    /** Añade el item del parametro al Conjunt. También añade las distancias correspondientes a la matriz de distancias Distance. Ademas actualiza maximos y minimos y
     recalcula distancias si estos han cambiado significativamente. Si no se ha podido añadir devuelve false.
     * @see Item
     * @param a Item a añadir en el conjunt.
     */

    public boolean anyadir_item(Item a)  {

        if (!existeix_item(a.getID())) {

            int i;
            if(n_Items() > 0) {
                Boolean re_needed = false;
                i = BinaryInsertionPos(Items, 0, Items.size()-1, a.getID());
                ArrayList<String> valors = a.getValors();
                for (int j = 0; j < maxims.size(); ++j) {

                    if (maxims.get(j) != -1.0) {

                        Double v_aux = Double.valueOf(valors.get(j));
                        if (v_aux > maxims.get(j) * 1.20) {

                            re_needed = true;
                            maxims.set(j, v_aux);
                        } else if (v_aux < minims.get(j) * 0.80) {

                            re_needed = true;
                            minims.set(j, v_aux);
                        }
                    }
                }
                if(re_needed) re_calcul_atriu();
            }
            else {

                i = -1;
                for (int j = 0; j < a.getValors().size(); ++j){

                    if (a.getTipus().getAtributes().get(j).getType() == "Rang") {

                        maxims.add(j, a.getTipus().getAtributes().get(j).getUpper());
                        minims.add(j, a.getTipus().getAtributes().get(j).getLower());
                    }
                    else {
                        maxims.add(j, -1.0);
                        minims.add(j, -1.0);
                    }
                }
            }



            Items.add(i+1, a);
            anyadir_elements( i+1, a);
            return true;
        }
        else return false;
    }

    /** Elimina el item del parametro del Conjunt, incluidas sus distancias. Los maximos y minimos se mantienen, ya que se sabe que pueden tener stos valores.
     * @see Item
     * @param a Item a eliminar en el conjunt.
     */

    public boolean eliminar_item(Item a)  {
        if (existeix_item(a.getID())) {
            int i = binarySearchPosition(Items, 0 ,n_Items()-1, a.getID());
            Items.remove(a);
            eliminar_elements(i);
            return true;
        }
        else return false;
    }

    //Operacions Auxiliars

    /** Vuelve a calcular la matriz de distancias enteras. Se debe llamar en caso de modificaion de los maximos o minimos.

     */

    private void re_calcul_atriu() {

        for (int i = 0; i < n_Items()-1; ++i)
            for (int j = n_Items()-1; j > i; --j)
                     Distance(Items.get(i), Items.get(j));

    }

    /** Inicializa y añade a la matriz Distance las respectivas distancias con todos los items.
     */

    private void initzialitzar_matriu() {

        for (int i = 0; i < n_Items(); ++i) {

            ArrayList<Double> Aux = new ArrayList<Double>();
            for (int j = 0; j < n_Items(); ++j){

                if (i == j) Aux.add(0.0);
                else {

                    Double d;
                    if (i <= j) d = Distance(Items.get(i), Items.get(j));
                    else d = Distances.get(j).get(i);
                    Aux.add(d);
                }
            }

            Distances.add(Aux);
        }
    }



    /** Añades el Item a la matriz de distancias Distance con sus respectivas distancias a los otros items.
     * @param i posision donde se insertó el Item
     *  @param a Item que se insertó.
     */

    private void anyadir_elements(int i, Item a) {

        ArrayList<Double> Aux = new ArrayList<Double>();
        if (n_Items() > 0) {

            for (int j = 0; j < n_Items(); ++j){

                Item b = Items.get(j);
                Double d = Distance(a,b);
                Aux.add(d);
            }

            Distances.add(i, Aux);

            for (int j = 0; j < n_Items(); ++j) {

                Double d = Distances.get(i).get(j);
                if (i != j) Distances.get(j).add(i, d);
            }
        }
        else {

            ArrayList<ArrayList<Double>> Dis_Aux = new ArrayList<ArrayList<Double>>();
            Aux.add(0.0);
            Dis_Aux.add(Aux);
            Distances = Dis_Aux;
        }
    }

    /** Elimnas las distancias de la matriz del item de la posicion.
     * @param i pos-1 donde se insertó el Item
     */

    private void eliminar_elements(int i) {

        if (n_Items() > 1) {

            for (int j = 0; j < n_Items(); ++j)
                Distances.get(j).remove(i);
        }
        else {

            ArrayList<ArrayList<Double>> Dis_Aux = new ArrayList<ArrayList<Double>>();
            Distances = Dis_Aux;
        }
    }

    /** Funcion derivada del algoritmo margeSort que sirve para buscar un Item en una ArrayList ordenada crecientemente
     por ID de Item.
     * @see Item
     * @param Items ArrayList de Item en la que buscar.
     * @param l Extremo izquierdo de la ArrayList en la que buscar.
     * @param r Extremo derecho de la ArrayList en la que buscar.
     * @param ID ID del Item a buscar en el conjunt.
     */

    private boolean binarySearch(ArrayList<Item> Items, int l, int r, int ID) {

        if (r >= l) {
            int mid = l + (r - l) / 2;
            //mirem se es en el mig
            if (Items.get(mid).getID() == ID)
                return true;
            //si no en es subvector esquerra
            if (Items.get(mid).getID() > ID)
                return binarySearch(Items, l, mid - 1, ID);
            //si no a la dreta
            return binarySearch(Items, mid + 1, r, ID);
        }
        else return false;
    }

    /** Funcion derivada del algoritmo margeSort que sirve para buscar la posicion de un Item en una ArrayList ordenada crecientemente
     por ID de Item.
     * @see Item
     * @param Items ArrayList de Item en la que buscar la posicion.
     * @param l Extremo izquierdo de la ArrayList en la que buscar.
     * @param r Extremo derecho de la ArrayList en la que buscar.
     * @param ID ID del Item a buscar la posicion en el conjunt.
     */

    private int binarySearchPosition(ArrayList<Item> Items, int l, int r, int ID) {

        if (r >= l) {
            int mid = l + (r - l) / 2;
            //mirem se es en el mig
            if (ID == Items.get(mid).getID())
                return mid;
            //si no en es subvector esquerra
            if (Items.get(mid).getID() > ID)
                return binarySearchPosition(Items, l, mid - 1, ID);
            //si no a la dreta
            return binarySearchPosition(Items, mid + 1, r, ID);
        }
        else return -1; //en teoria exception
    }

    /** Funcion derivada del algoritmo margeSort que sirve para buscar la posicion en la que se insertaria un item,
     devuelve la posicion-1.
     * @see Item
     * @param Items ArrayList de Item en la que buscar.
     * @param l Extremo izquierdo de la ArrayList en la que buscar.
     * @param r Extremo derecho de la ArrayList en la que buscar.
     * @param ID ID del Item a buscar donde insertar en el conjunt.
     */

    private int BinaryInsertionPos(ArrayList<Item> Items, int l, int r, int ID) {

        if (r > l) {
            int mid = l + (r - l) / 2;
            //mirem se es en el mig
            if (ID == Items.get(mid).getID())
                return mid;
            //si no en es subvector esquerra
            if (Items.get(mid).getID() > ID)
                return BinaryInsertionPos(Items, l, mid - 1, ID);
            //si no a la dreta
            return BinaryInsertionPos(Items, mid + 1, r, ID);
        }
        else  if (r >= 0 && Items.get(r).getID() > ID)
            return r-1; //en teoria exception
        else return r;
    }
}