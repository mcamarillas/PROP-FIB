package FONTS.src.domini.drivers;
import FONTS.src.domini.model.Ranged_Atribute;


import java.util.Scanner;

/** \brief Driver de la clase Ranged_Atribute.
        *  @author Jordi Olmo
        */
public class DriverRanged_Atribute {

    static void testFunction(int f) {
        switch(f)
        {
            case 1 :
            {
                System.out.println("==================================================================================");
                System.out.println("Prueba de la creadora de Ranged_Atribute");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus Min Max");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String, Max y Min son Double (se separa el decimal con ,)");
                Scanner s = new Scanner(System.in);
                Ranged_Atribute a = new Ranged_Atribute(s.next(), s.next(), s.nextDouble(), s.nextDouble());
                System.out.println("El Ranged_Atribute se ha creado corrtrectamente y estos son los parametros en el mismo orden : \n"
                        + a.getName() + ' ' + a.getType() + ' ' + a.getLower() + ' ' + a.getUpper());
                System.out.println("==================================================================================");
                break;
            }

            case 2 :
            {
                System.out.println("==================================================================================");
                System.out.println("Prueba de la funcion getLower()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus Min Max");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String, Max y Min son Double (se separa el decimal con ,)");
                Scanner s = new Scanner(System.in);
                Ranged_Atribute a = new Ranged_Atribute(s.next(), s.next(), s.nextDouble(), s.nextDouble());
                System.out.println("El Ranged_Atribute se ha creado correctamente y el Min és: \n"
                        + a.getLower());
                System.out.println("==================================================================================");
                break;
            }

            case 3 :

            {
                System.out.println("==================================================================================");
                System.out.println("Prueba de la funcion getUpper()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus Min Max");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String, Max y Min son Double (se separa el decimal con ,)");
                Scanner s = new Scanner(System.in);
                Ranged_Atribute a = new Ranged_Atribute(s.next(), s.next(), s.nextDouble(), s.nextDouble());
                System.out.println("El Ranged_Atribute se ha creado correctamente y el Max és: \n"
                        + a.getUpper());
                System.out.println("==================================================================================");
                break;
            }

            case 4 :

            {
                System.out.println("==================================================================================");
                System.out.println("Prueba de la funcion setLower()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus Min Max");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String, Max y Min son Double (se separa el decimal con ,)");
                Scanner s = new Scanner(System.in);
                Ranged_Atribute a = new Ranged_Atribute(s.next(), s.next(), s.nextDouble(), s.nextDouble());
                System.out.println("El Ranged_Atribute se ha creado correctamente y el Min és: \n"
                        + a.getLower());
                System.out.println("Ahora introduce el nuevo Min");
                Double min = s.nextDouble();
                a.setLower(min);
                System.out.println("El Ranged_Atribute se ha actualizado correctamente y el nuevo Min és: \n"
                        + a.getLower());
                System.out.println("==================================================================================");
                break;
            }

            case 5:
            {
                System.out.println("==================================================================================");
                System.out.println("Prueba de la funcion setUpper()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus Min Max");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String, Max y Min son Double (se separa el decimal con ,)");
                Scanner s = new Scanner(System.in);
                Ranged_Atribute a = new Ranged_Atribute(s.next(), s.next(), s.nextDouble(), s.nextDouble());
                System.out.println("El Ranged_Atribute se ha creado correctamente y el Min és: \n"
                        + a.getUpper());
                System.out.println("Ahora introduce el nuevo Max");
                Double max = s.nextDouble();
                a.setUpper(max);
                System.out.println("El Ranged_Atribute se ha actualizado correctamente y el nuevo Min és: \n"
                        + a.getUpper());
                System.out.println("==================================================================================");
                break;
            }

            case 6: break;

            default : System.out.println("No has introducido un número entre 1 y 6");
        }
    }

    static void printInfo() {

        System.out.println("\nDRIVER DE LA CLASE Ranged_Atribute\n");
        System.out.println("\nFunciones de la clase disponibles para probar:\n");
        System.out.println("    1: Crear Ranged_Atribute\n    2: getLower()\n    3: getUpper()\n" +
                "    4: setLower()\n    5: setUpper()\n    6: FINALIZAR PRUEBA.\n");
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        int f;
        do{
            printInfo();
            System.out.println("\nSelecciona funcion a probar: ");
            f = s.nextInt();
            testFunction(f);

        }while(f != 6);
    }
}
