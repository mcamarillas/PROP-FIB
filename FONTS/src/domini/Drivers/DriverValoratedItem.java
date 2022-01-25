package FONTS.src.domini.drivers;

import FONTS.src.domini.model.Item;
import FONTS.src.domini.model.valoratedItem;

import java.util.Scanner;

/** \brief Driver de la clase valoratedIem.
 *  @author Marc Camrillas
 */
public class DriverValoratedItem {
    public static void main(String[] args) {
        int x;
        System.out.println("Introduce el ID del Item que quiere valorar: ");
        Scanner S = new Scanner(System.in);
        int itemID =  S.nextInt();
        System.out.println("Introduce la valoración que quiere ponerle: ");
        int valoration = S.nextInt();
        Item i = new Item(itemID);
        valoratedItem vItem = new valoratedItem(i,valoration);

        System.out.println("La valoración que has introducido es: " + valoration + "\nLa valoracion que hemos guardado es: " + vItem.getValoracio());
        System.out.println("========================================");
        System.out.println("El ID que has que has introducido es: " + itemID + "\nEl ID que hemos guardado es: " + vItem.getItem().getID());
        System.out.println("========================================");
        if( valoration == vItem.getValoracio() && itemID == vItem.getItem().getID()) System.out.println("FUNCIONA BIEN");
        else System.out.println("NO FUNCIONA BIEN");
    }
}
