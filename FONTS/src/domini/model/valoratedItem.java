package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.ArrayList;

/** \brief Clase que representa un item valorado
 *  @author Marc Camarillas
 */
public class valoratedItem {
    // Atributos
    /**
     * Puntuación que le han dado
     */
    private float valoration;
    /**
     * Item que ha sido valorado
     * @see Item
     */
    private Item item;


    // Constructora

    /**
     * Constructora de valoratedItem
     * @param item item que se ha valorado
     * @param valoration puntuación que se le da al item
     */
    public valoratedItem(Item item, float valoration) {
        this.item = item;
        this.valoration = valoration;
    }

    //Getters

    /**
     * @return Devuelve la valoración que se le ha dado al Item
     */
    public float getValoracio() {
        return valoration;
    }

    /**
     * @return Devuelve el item que se ha valorado
     */
    public Item getItem() {
        return item;
    }
}