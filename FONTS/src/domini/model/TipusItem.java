package FONTS.src.domini.model;

import java.util.*;

/** \brief Clase que representa un tipo de item, definido por su conjunto de atributos.
 *  @author Jordi Olmo
 */
public class TipusItem {

    //atributes
    /** Identificador del tipes d'item, correspont a un String amb el nom de tots els seus atributs seguits
     */
    private String ID;

    /** ArrayList que contiene todos atributos que pertenecen a este tipo.
     * @see Atribute
     */
    private ArrayList<Atribute> atributes;

    //contructores

    /** Constructora de la clase. Copia la ArrayList de atributes,
     para el id coje todos los nonmbres de atributs y los combina en un String.
     * @param atributes ArrayList de los atributos que definen este tipo.
     */
    public TipusItem( ArrayList<Atribute> atributes) {

        ArrayList <String> v = new ArrayList<String> ();
        for (int i = 0; i < atributes.size(); ++i) {

            v.add(i, atributes.get(i).getName());
        }

        this.ID = v.toString();
        this.atributes = atributes;
    }

    //getters

    /** Devueve el ID del tipus.
     */

    public String getID() {
        return ID;
    }

    /** Devueve el ArrayList de Atributes.
     * @see Atribute
     */

    public ArrayList<Atribute> getAtributes() {
        return atributes;
    }

    /** Devueve el numero de Atributes de este tipo.
     */

    public int num_atributs() {return atributes.size(); }

    /** Devueve el numero de Atributes rellevants de este tipo.
     * @see Atribute
     */

    public int num_atributs_rellevants() {

        int n = 0;
        for (int i = 0;i < atributes.size(); i++){
            if(atributes.get(i).isRellevant()) ++n;
        }
        return n;
    }

    /** Devueve el ArrayList de Atributes que son rellevants.
     * @see Atribute
     */

    public ArrayList<Atribute> Atributs_rellevants(){

        ArrayList <Atribute> Rellevants = new ArrayList<>();

        for (int i = 0;i < atributes.size(); i++){
            if(atributes.get(i).isRellevant())
                Rellevants.add(atributes.get(i));
        }
        return Rellevants;
    }
}