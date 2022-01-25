package FONTS.src.domini.model;
import java.util.ArrayList;
import java.util.Vector;

/** \brief Clase que representa un Item.
 *  @author Jordi Olmo
 */

public class Item {

    //Atributes
    /**
     * Identidicador del Item.
     */
    private int ID;
    /**
     * Tipus del Item
     *
     * @see TipusItem
     */
    private TipusItem tipus;

    /**
     * ArrayList de String que contiene los valores de los Atributo de su tipo, las posiciones de los valores debe
     * coincidir con las posiciones de los atributos de la ArrayList del tipo.
     *
     * @see TipusItem
     */
    private ArrayList<String> valors;

    /**
     * Valores iniciales.
     */
    private String ValI;
    /**
     * Atributos iniciales.
     */
    private String AtrI;

    //Constructors

    /**
     * Creadora de la classe. Crea un item con los parametros dados.
     *
     * @param ID     identificador del item, no puede coincidir el de ningun item.
     * @param tipus  Tipo del item.
     * @param valors ArrayList de String con el valor de los atributos del tipo del item, las posiciones de los valores debe
     *               coincidir con las posiciones de los atributos de la ArrayList del tipo.
     * @see TipusItem
     */

    /* El indice del valor debe coincidir con el indice del atributo para funcionar*/
    public Item(int ID, TipusItem tipus, ArrayList<String> valors) {
        //    if (!Conjunt_Items.existeix_item(ID)) {
        this.ID = ID;
        this.tipus = tipus;
        this.valors = valors;
        //   }
    }

    /**
     * Creadora de la classe. Crea un item con los parametros dados.
     *
     * @param ID identificador del item, no puede coincidir el de ningun item.
     *           coincidir con las posiciones de los atributos de la ArrayList del tipo.
     * @see TipusItem
     */

    public Item(int ID) {
        this.ID = ID;
    }

    //Getters

    /**
     * Devuelve el ID, del item.
     * @return
     */

    public int getID() {
        return ID;
    }

    /**
     * Devuelve el tipo del Item.
     */

    public TipusItem getTipus() {
        return tipus;
    }

    /**
     * Devuelve la ArrayList de valors del Item.
     */

    public ArrayList<String> getValors() {
        return valors;
    }

    /**
     * Devuelve los valores iniciales.
     */

    public String getString(){
        return ValI;
    }

    /**
     * Devuelve los atributos iniciales.
     */

    public String getAtr(){
        return AtrI;
    }

    /**
     * Establece los valores iniciales.
     */

    public void setString(String val){
        this.ValI=val;
    }

    /**
     * Establece los atributos iniciales.
     */

    public void setAtr(String val){
        this.AtrI=val;
    }

    //Setters

    /**
     * Establece el valor del id del Item.
     */

    public void setID(int ID) {
        this.ID = ID;
    }

    //Operacions

    /**
     * Devuelve la distancia del Item implicito y el del parametro. Los dos Item deben ser del mismo tipo
     *
     * @param b Item del que se quiere saber la distancia.
     */

    public Double Distance(Item b) {

        ArrayList<Atribute> V_A = new ArrayList<Atribute>();
        ArrayList<Atribute> V_B = new ArrayList<Atribute>();

        ArrayList<String> Valors_A = new ArrayList<String>();
        ArrayList<String> Valors_B = new ArrayList<String>();

        clonador_ArrayList(V_A, tipus.getAtributes());
        clonador_ArrayList(V_B, b.getTipus().getAtributes());

        if (V_A.size() > 0 && V_B.size() > 0 && tipus == b.getTipus() && !tipus.equals("[]")) {

            clonador_ArrayList(Valors_A, valors);
            clonador_ArrayList(Valors_B, b.getValors());

            int n_dimensions = V_A.size();
            if(ID == 1 && b.getID() == 21)
                n_dimensions = V_A.size();
            Double Distancia = comparador_tipus_iguals(V_A, V_B, Valors_A, Valors_B, n_dimensions);
            return Distancia;
        }
        return 0.0;
    }

    //Operacions Auxiliars

    /**
     * Compara todos los atrubutos de los ArrayList y evuelve un Double que serà mayor cuanto más se parezcan. Se
     * presupone que que todas las ArrayList son del mismo tamaño = n_d, ya que tan sólo funciona si los dos items
     * tienen los mismos atributos.
     *
     * @param V_A      ArrayList de Atribute del Item a.
     * @param V_B      ArrayList de Atribute del Item b, tiene que ser del mismo tamaño de V_A.
     * @param Valors_A ArrayList de String, con los valores que representan los Atributes del item a.
     * @param Valors_B ArrayList de String, con los valores que representan los Atributes del item b.
     * @param n_d      numero de dimensiones de las dos tipus, (numero de atributos, tienen que ser igual).
     */

    /* Es presoposa que que totels les ArrayList son de la mateixa mida = n_d, ja que tan sols funciona si els dos items tenen els mateixos atributs */
    private Double comparador_tipus_iguals(ArrayList<Atribute> V_A, ArrayList<Atribute> V_B, ArrayList<String> Valors_A, ArrayList<String> Valors_B, int n_d) {

        Double distance = 0.0;
        for (int i = 0; i < n_d; ++i) {

            if (!(Valors_A.get(i).equals("")) && !(Valors_B.get(i).equals("")) && V_A.get(i).isRellevant() && V_B.get(i).isRellevant()) {

                if (V_A.get(i) == V_B.get(i)) {

                    if (V_A.get(i).getType().equals("Boolean") || V_A.get(i).getType().equals("String")) {

                        if (Valors_A.get(i).equals(Valors_B.get(i)))
                            distance += 1.0;
                        else
                            distance += 0.0;
                    } else if (V_A.get(i).getType().equals("Vector de String")) {

                        Vector<String> S_A = Construc_Vector(Valors_A.get(i), ';');
                        Vector<String> S_B = Construc_Vector(Valors_B.get(i), ';');

                        int n_string = Math.max(S_A.size(), S_B.size());
                        Double coeficient = 1 / (double) n_string;
                        Double Sum = 0.0;

                        for (int j = 0; j < S_A.size(); j++) {
                            for (int k = 0; k < S_B.size(); k++) {

                                if (S_A.get(j).equals(S_B.get(k)))
                                    Sum += coeficient;

                            }
                        }
                        distance += Math.pow(Sum, 2);

                    }

                    //es considerarà que a partir de 50 anys de diferencia la distancia es 0
                    // es considera que any pelicula > 1900
                    else if (V_A.get(i).getType().equals("Data")) {

                        String s_a = Valors_A.get(i);
                        String s_b = Valors_B.get(i);

                        Vector<String> D_A = Construc_Vector(s_a, '-');
                        Vector<String> D_B = Construc_Vector(s_b, '-');

                        int dia_a, dia_b, mes_a, mes_b, any_a, any_b;

                        dia_a = Integer.valueOf(D_A.get(2));
                        mes_a = Integer.valueOf(D_A.get(1));
                        any_a = Integer.valueOf(D_A.get(0)) - 1900;

                        dia_b = Integer.valueOf(D_B.get(2));
                        mes_b = Integer.valueOf(D_B.get(1));
                        any_b = Integer.valueOf(D_B.get(0)) - 1900;

                        int total_a = dia_a + (mes_a / 2 * 31 + any_a * 4 * 30) + (mes_a / 2 * 30 + any_a * 7 * 31) + (any_a * 28);
                        int total_b = dia_b + (mes_b / 2 * 31 + any_b * 4 * 30) + (mes_b / 2 * 30 + any_b * 7 * 31) + (any_b * 28);
                        Double max = Double.valueOf((50 * 4 * 30) + (50 * 7 * 31) + (50 * 28));

                        Double coeficient = 1.0 - (((Math.max(total_a, total_b)) - Math.min(total_a, total_b)) / max);
                        distance += Math.pow(coeficient, 2);

                    } else if (V_A.get(i).getType().equals("Rang")) {

                        Double valor_a = Double.valueOf(Valors_A.get(i));
                        Double valor_b = Double.valueOf(Valors_B.get(i));

                        Double max = V_A.get(i).getUpper();
                        Double min = V_A.get(i).getLower();
                        Double dividend = max - min;
                        Double coeficient;

                        if (dividend != 0.0){

                            Double aux = (Math.max(valor_a, valor_b) - Math.min(valor_a, valor_b)) / (max - min);
                            coeficient = 1 - aux;
                        }

                        else
                            coeficient = 1.0;
                        distance += Math.pow(coeficient, 2);

                    }
                }

            }
        }
        return Math.sqrt(distance);
    }

    /**
     * Funcion auxiliar que devuelve el string con la fecha YYYY-MM-DD, en un vector de string (3), donde [0] == YYYY, [1] = MM, [2] = DD.
     * @param s String con la fecha YYYY-MM-DD.
     * @param c Char que se usa para separar en los String, en el caso de fecha `-`.
     */

    private Vector<String> Construc_Vector(String s, char c) {

        Vector<String> v = new Vector<String>(0);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {

                v.add(s.substring(0, i));
                s = s.substring(i + 1, s.length());
                i = 0;
            }
        }

        v.add(s);
        return v;
    }

    /**
     * Copia todos los valores de la segunda ArrayList y los añade en la primera. Las ArrayList tienen que ser de los mismos tipos.
     *
     * @param A ArrayList donde se copiara B, para que queden iguales debe ser vacía.
     * @param B ArrayList de origen que ser clonada en A.
     */

    private void clonador_ArrayList(ArrayList A, ArrayList B) {

        for (int i = 0; i < B.size(); ++i)
            A.add(i, B.get(i));
    }


}
