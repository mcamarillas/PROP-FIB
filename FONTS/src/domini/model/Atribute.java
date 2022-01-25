package FONTS.src.domini.model;

import java.util.Vector;

/** \brief Clase que representa un Atributo.
 *  @author Jordi Olmo
 */
public class Atribute {

    //atributes

    /** Nombre del Atributo.
     */
    private String Nom;

    /** Tipo del Atribute que deberia ser uno de los siguientes para usarse en el algoritmo
     {Boolean, String, Vector de String, Data, Rang}.
     */
    private String Tipus;

    /** Booleano que representa si el atributo es relevante o no, es decir si se debe considerar para comparar o no.
     */
    private boolean Rellevant;

    //Constructors

    /** Creadora de la classe, con el nomnbre y tipo de los parametros, rellevant por defecto es true.
     * @param name Nombre del atributo.
     * @param type Tipo del Atributo.
     */

    public Atribute(String name, String type) {

        Nom = name;
        Tipus = type;
        Rellevant = true;
    }

    /** Creadora de la classe, con el nomnbre, tipo de los parametros y rellevant.
     * @param name Nombre del atributo.
     * @param type Tipo del Atributo.
     * @param R Indica si el atributo es relevante
     */

    public Atribute(String name, String type, Boolean R) {

        Nom = name;
        Tipus = type;
        Rellevant = R;
    }

    //Getters

    /** Devuelve si el Atribute es rellevant.
     */

    public boolean isRellevant() {
        return Rellevant;
    }

    /** Devuelve el nombre del Atribute.
     */

    public String getName() {
        return Nom;
    }

    /** Devuelve el tipus del Atribute.
     */

    public String getType() {
        return Tipus;
    }

    /** Funcion para definir en Ranged_Atribute.
     */

    public Double getLower() { return -1.0; }

    /** Funcion para definir en Ranged_Atribute.
     */

    public Double getUpper() {
        return -1.0;
    }

    //Setters

    /** Define el type del Atribute.
     */

    public void setTipus(String tipus) {
        this.Tipus = tipus;
    }

    /** Define el name del Atribute.
     */

    public void setNom(String nom) {
        this.Nom = nom;
    }

    /** Define si el Atribute es Rellevant.
     */

    public void setRellevant(boolean rellevant) {
        this.Rellevant = rellevant;
    }

    /** Funcion para definir en Ranged_Atribute.
     */

    public void setLower(Double low) {    }

    /** Funcion para definir en Ranged_Atribute.
     */

    public void setUpper(Double up) {   }

}
