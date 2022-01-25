package FONTS.src.domini.drivers;
import FONTS.src.domini.model.Atribute;
import java.util.Scanner;

/** \brief Driver de la clase Atribute.
 *  @author Jordi Olmo
 */
public class DriverAtribute {

    static void testFunction(int f) {
        switch(f)
        {
            case 1 :

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba la creadora de Atribute");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String");
                Scanner s = new Scanner(System.in);
                Atribute a = new Atribute(s.next(), s.next());
                System.out.println("El Atribute se ha creado correctamente y estos son los parametros (incluyendo Rellevant)" +
                        " en el mismo orden : \n"
                        + a.getName() + ' ' + a.getType() + ' ' + a.isRellevant());
                System.out.println("=================================================================================================");
                break;
            }

            case 2 :

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba la creadora de Atribute especificando la relevancia");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus Rellevant");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String, Rellevant és Boolean");
                Scanner s = new Scanner(System.in);
                Atribute a = new Atribute(s.next(), s.next(), s.nextBoolean());
                System.out.println("El Atribute se ha creado correctamente y estos son los parametros (incluyendo Rellevant)" +
                        " en el mismo orden : \n"
                        + a.getName() + ' ' + a.getType() + ' ' + a.isRellevant());
                System.out.println("=================================================================================================");
                break;
            }

            case 3 :

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba de la funcion getLower()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String\n");
                Scanner s = new Scanner(System.in);
                Atribute a = new Atribute(s.next(), s.next());
                System.out.println("El Atribute se ha creado correctamente y este es el valor de Min" +
                        " (que és una funcion para que se sobreescriba en la clase hijo) \n"
                        + a.getLower());
                System.out.println("=================================================================================================");
                break;
            }

            case 4 :

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba de la funcion getUpper()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String\n");
                Scanner s = new Scanner(System.in);
                Atribute a = new Atribute(s.next(), s.next());
                System.out.println("El Atribute se ha creado correctamente y este es el valor de Man" +
                        " (que és una funcion para que se sobreescriba en la clase hijo) \n"
                        + a.getUpper());
                System.out.println("=================================================================================================");
                break;
            }

            case 5:

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba de la funcion getName()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String");
                Scanner s = new Scanner(System.in);
                Atribute a = new Atribute(s.next(), s.next());
                System.out.println("El Atribute se ha creado correctamente y este el Nombre: \n"
                        + a.getName());
                System.out.println("=================================================================================================");
                break;
            }

            case 6:

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba de la funcion getType()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String");
                Scanner s = new Scanner(System.in);
                Atribute a = new Atribute(s.next(), s.next());
                System.out.println("El Atribute se ha creado correctamente y este el Tipo: \n"
                        + a.getType());
                System.out.println("=================================================================================================");
                break;
            }

            case 7:

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba de la funcion isRellevant()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus Rellevant");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String, Rellevant és Boolean");
                Scanner s = new Scanner(System.in);
                Atribute a = new Atribute(s.next(), s.next(), s.nextBoolean());
                System.out.println("El Atribute se ha creado correctamente y este és el valor de Rellevant: \n"
                        + a.isRellevant());
                System.out.println("=================================================================================================");
                break;
            }

            case 8:

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba de la funcion setLower()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus Min");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String, Min és un Double (se separa el decimal con ,)");
                Scanner s = new Scanner(System.in);
                Atribute a = new Atribute(s.next(), s.next());
                System.out.println("El Atribute se ha creado correctamente y estos son los parametros (incluyendo Min)" +
                        " en el mismo orden : \n"
                        + a.getName() + ' ' + a.getType() + ' ' + a.getLower());
                System.out.println("Introduce el nuevo valor de Min");
                a.setLower(s.nextDouble());
                System.out.println("El Atribute se ha actualizado correctamente y este és el valor de Min: \n"
                        + a.getLower());
                System.out.println("=================================================================================================");
                break;
            }

            case 9:

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba de la funcion setUpper()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus Max");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String, Max és un Double (se separa el decimal con ,)");
                Scanner s = new Scanner(System.in);
                Atribute a = new Atribute(s.next(), s.next());
                System.out.println("El Atribute se ha creado correctamente y estos son los parametros (incluyendo Max)" +
                        " en el mismo orden : \n"
                        + a.getName() + ' ' + a.getType() + ' ' + a.getUpper());
                System.out.println("Introduce el nuevo valor de Max");
                a.setUpper(s.nextDouble());
                System.out.println("El Atribute se ha actualizado correctamente y este és el valor de Max: \n"
                        + a.getUpper());
                System.out.println("=================================================================================================");
                break;
            }

            case 10:

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba de la funcion setTipus()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String");
                Scanner s = new Scanner(System.in);
                Atribute a = new Atribute(s.next(), s.next());
                System.out.println("El Atribute se ha creado correctamente y estos son los parametros " +
                        " en el mismo orden : \n"
                        + a.getName() + ' ' + a.getType());
                System.out.println("Introduce el nuevo valor de Tipus");
                a.setTipus(s.next());
                System.out.println("El Atribute se ha actualizado correctamente y este és el valor de Tipus: \n"
                        + a.getType());
                System.out.println("=================================================================================================");
                break;
            }

            case 11:

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba de la funcion setNom()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String");
                Scanner s = new Scanner(System.in);
                Atribute a = new Atribute(s.next(), s.next());
                System.out.println("El Atribute se ha creado correctamente y estos son los parametros " +
                        " en el mismo orden : \n"
                        + a.getName() + ' ' + a.getType() );
                System.out.println("Introduce el nuevo Nombre");
                a.setNom(s.next());
                System.out.println("El Atribute se ha actualizado correctamente y este és el Nombre: \n"
                        + a.getName());
                System.out.println("=================================================================================================");
                break;
            }

            case 12:

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba de la funcion setRellevant()");
                System.out.println("Introduce los siguientes parametros de la siguiente forma: Nombre Tipus Rellevant");
                System.out.println("Teniendo en cuenta que: Nombre y tipus son String, Rellevant és un Boolean");
                Scanner s = new Scanner(System.in);
                Atribute a = new Atribute(s.next(), s.next(), s.nextBoolean());
                System.out.println("El Atribute se ha creado correctamente y estos son los parametros (incluyendo Rellevant)" +
                        " en el mismo orden : \n"
                        + a.getName() + ' ' + a.getType() + ' ' + a.isRellevant());
                System.out.println("Introduce el nuevo valor de Rellevant");
                a.setRellevant(s.nextBoolean());
                System.out.println("El Atribute se ha actualizado correctamente y este és el nuevo Rellevant: \n"
                        + a.isRellevant());
                System.out.println("=================================================================================================");
                break;
            }

            case 13:break;



            default : System.out.println("No has introducido un número entre 1 y 13");
        }
    }

    static void printInfo() {

        System.out.println("\nDRIVER DE LA CLASE Atribute\n");
        System.out.println("Funciones de la clase disponibles para probar:\n");
        System.out.println(
                "    1: Crear Atribute\n    2: Crear Atribute indicando relevancia\n    3: getLower()\n" +
                "    4: getUpper()\n    5: getName()\n    6: getType()\n" +
                "    7: isRellevant()\n    8: setLower()\n    9: setUpper()\n" +
                "    10: setTipus()\n    11: setNom()\n    12: setRellevant()\n"+
                "    13: FINALIZAR PRUEBA.");
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        int f;
        do{
            printInfo();
            System.out.println("\nSelecciona funcion a probar: ");
            f = s.nextInt();
            testFunction(f);

        }while(f != 13);
    }
}
