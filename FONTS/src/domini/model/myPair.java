package FONTS.src.domini.model;

/** \brief Implementa un par de ID item y valoración.
 *  @author Roberto Amat
 */
public class myPair {
    /** ID del item.
     */
    private int itemID;
    /** Valoración del item.
     */
    private float valoration;

    /** Creadora de la clase.
     * @param itemID ID del item.
     * @param valoration Valoración del item.
     */
    public myPair(int itemID, float valoration) {
        this.itemID = itemID;
        this.valoration = valoration;
    }

    /** @return Valoración del Item.
     * @see Item
     */
    public float getValoration() {
        return valoration;
    }

    /** @return Id del Item.
     * @see Item
     */
    public int getItemID() {
        return itemID;
    }
}
