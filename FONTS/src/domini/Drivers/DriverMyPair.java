package FONTS.src.domini.drivers;

import FONTS.src.domini.model.*;

import java.util.Scanner;

/** \brief Driver de la clase myPair.
 *  @author Roberto Amat
 */
public class DriverMyPair {

    static myPair mp;
    int x;


    static void testFunction(int f) {
        switch(f)
        {
            case 1 :
            {
                System.out.println("=============================================");
                System.out.println("Devuelve el ID de item almacenado en el par.");
                System.out.println(mp.getItemID());
                System.out.println("==============================================");
                break;
            }

            case 2 :
            {
                System.out.println("==============================================");
                System.out.println("Devuelve la valoracion almacenada en el par.");
                System.out.println(mp.getValoration());
                System.out.println("==============================================");
                break;
            }

            case 3:
            {
                System.out.println("=============================================================================");
                Scanner s = new Scanner(System.in);
                System.out.print("Introduce ID de item a almacenar en el par: ");
                int ID = s.nextInt();
                System.out.print("Introduce la valoracion a almacenar en el par (si es decimal usar la coma): ");
                float valoration = s.nextFloat();
                mp = new myPair(ID, valoration);
                System.out.println("=============================================================================");
                break;
            }

            case 4: break;

            default : System.out.print("Porfavor introduce un valor correcto\n ");
        }
    }

    static void printInfo() {
        System.out.println("\nDRIVER DE LA CLASE myPair\n");
        System.out.println("La clase myPair representa un par [itemID, valoracion] que representa que el\n"
            +"item con ID [itemID] tiene la valoracion [valoracion].");
        System.out.println("\nFunciones de la clase disponibles para probar:\n");
        System.out.println("    1. getItemID()\n    2. getValoration()\n    3. CAMBIAR CLUSTER ACTUAL.\n    4. FINALIZAR PRUEBA.\n");
    }

    /** Main del driver.
     *  Prueba 5 casos
     *      - Borrar un usuario que no existe en el cluster
     *      - Añadir usuarios leidos al cluster
     *      - Añadir usuario que ya existe
     *      - Establecer como centroide un usuario que no forma parte del cluster
     *      - Imprimir correctamente el cluster
     */
    public static void main(String[] args) {
        printInfo();
        Scanner s = new Scanner(System.in);
        testFunction(3);
        int f;
        do{
            System.out.println("\nSelecciona funcion a probar: ");
            f = s.nextInt();
            testFunction(f);

        }while(f != 4);
    }
}
