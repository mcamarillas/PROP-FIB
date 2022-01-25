package FONTS.src.domini.model;
import FONTS.src.domini.model.*;

/** \brief Clase que representa un Atributo con rango.
 * @see Atribute
 *  @author Jordi Olmo
 */
public class Ranged_Atribute  extends Atribute{

    //Atributes

    /** Double de los minimos y maximos del atributo, teniendo en cuenta los valores de todos los items que tienen
     este Atribute en su Tipus, por lo que representa el maximo y minimo de los valores que estan en el sistema, no
     los maximos y minimos que pueden alcancar, se usan para normalizar valores en el calculo de distancias.
     */
    private Double lower, upper;

    //Constructor

    /** Creadora de la classe. Crea un item con los parametros dados, llamando a la creadora del padre.
     * @param name Nombre del Atribute
     * @param type Tipo del Atribute.
     * @param min lower.
     * @param max Upper.
     */

    public Ranged_Atribute(String name, String type, Double min, Double max) {
        super(name, type);
        lower = min;
        upper = max;
    }

    //Getters

    /** Devuelve el lower, sobreeescribe la funcion de Atribute.
     */

    public Double getLower() {
        return lower;
    }

    /** Devuelve el Upper, sobreeescribe la funcion de Atribute.
     */

    public Double getUpper() {
        return upper;
    }

    //Setters

    /** Define el lower, sobreeescribe la funcion de Atribute.
     */

    public void setLower(Double lower) {
        this.lower = lower;
    }

    /** Define el Upper, sobreeescribe la funcion de Atribute.
     */

    public void setUpper(Double upper) {
        this.upper = upper;
    }
}
