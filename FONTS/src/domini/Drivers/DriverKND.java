package FONTS.src.domini.drivers;
import FONTS.src.domini.exceptions.EmptyFileException;
import FONTS.src.domini.exceptions.FileNotExistsException;
import FONTS.src.domini.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/** \brief Driver de la clase K_Nearest_Neigthbour.
 *  @author Jordi Olmo
 */
public class DriverKND {

    private static Conjunt_Items CI_Static;
    private static boolean prueba_static = false;


    public static Vector<String> Lector_Items(String csvFile) throws Exception {
        //post: return un vector de les files del csv

        Vector <String> items = new Vector<String>();
        BufferedReader br = null;
        String line = "";
        try{
            br = new BufferedReader(new FileReader(csvFile));
        }
        catch (Exception e){
            throw new FileNotExistsException(csvFile);
        }

        try {
            while ((line = br.readLine()) != null) {
                items.add(line);
            }
        }
        catch (Exception e){
            throw e;
        }
        if (items.size()==0) throw new EmptyFileException("Lector_Items");
        return items;
    }

    public static ArrayList<Item> loadItems(String File) throws Exception {

        ArrayList<Item> A_Items = new ArrayList<Item>();
        Map<String, TipusItem> itemTypeList = new HashMap<String, TipusItem>();
        Vector <String>  items = Lector_Items(File);

            for(int i = 1; i < items.size();++i)
                A_Items.addAll(createItem(items.get(0), items.get(i), itemTypeList ));
        return A_Items;
    }

    public static ArrayList<Item> createItem(String atributs, String valors, Map<String, TipusItem> itemTypeList ){

        //string to arraylist de valors
        Conjunt_Items itemList = new Conjunt_Items();

        ArrayList<String> datos_valors = new ArrayList<String>();
        String pal="";
        boolean frase=false;
        for (int iterat=0;iterat<valors.length();++iterat){
            if(valors.charAt(iterat)=='"'){
                frase=!frase;
            }
            if(valors.charAt(iterat)!=',' || frase){
                pal+=valors.charAt(iterat);
            }
            if(valors.charAt(iterat)==',' && !frase){
                if (pal.length()==0) pal="";
                datos_valors.add(pal);
                //System.out.println(pal);
                pal="";

            }
        }
        if (valors.charAt(valors.length()-1)==','){
            datos_valors.add("");
        }
        else datos_valors.add(pal);

        //cremos vector atributos

        String[] datos = atributs.split(",");
        //System.out.println(datos.length);
        ArrayList <Atribute> va = new ArrayList<Atribute>();
        ArrayList <String> vsa = new ArrayList<String>();//solo para definir el tipo de item
        int pos_id=0;
        for (int i = 0; i <datos.length; ++i) {
            if(datos[i].equals("id")) pos_id=i;
            else{
                Atribute aux = new Atribute(datos[i], "");
                va.add(aux);
                vsa.add(datos[i]);

            }
        }

        //miramos si no existe item

        int comp = Integer.valueOf(datos_valors.get(pos_id));
        if (itemList.existeix_item(comp)){
            System.out.println("ja existeix id");
            //acabar la funcion-----------------------------------------------------------------------------
        }
        //creamos tipus item si NO EXISTE
        boolean new_type_item=false;
        String ID_ti=vsa.toString();
        TipusItem ti;
        if (itemTypeList.containsKey(ID_ti)){//existe
            ti=itemTypeList.get(ID_ti);
            va=ti.getAtributes();
        }
        else{//no existe
            ti = new TipusItem(va);
            itemTypeList.put(ID_ti, ti);
            new_type_item=true;
        }

        //DEFINIR TIPO ATRIBUTO
        //string de valores to vector

        //if (valors[valors.length()].equals(","))
        //System.out.println(vsa.size());
        //System.out.println(datos_valors.size());
        ArrayList <String> vsv= new ArrayList<String>();
        for (int i = 0; i <vsa.size()+1; ++i) {
            //System.out.println(datos_valors.get(i));
            if(i!=pos_id){
                //System.out.println(vsa.get(i));

                vsv.add(datos_valors.get(i));
            }
        }
        for (int pos=0;pos<vsv.size();pos++) {
            String i = vsv.get(pos);
            Boolean ranged = true;
            Atribute a = va.get(pos);
            if (i.equals("False") || i.equals("True")){
                a.setTipus("Boolean");
                a.setRellevant(true);
            }
            else if(i.contains(";")){
                a.setTipus("Vector de String");
                a.setRellevant(true);
            }
            else if(i.length()==10 && i.charAt(0)<='9' && i.charAt(0)>='0' && i.charAt(1)<='9' && i.charAt(1)>='0' && i.charAt(2)<='9' && i.charAt(2)>='0'
                    && i.charAt(3)<='9' && i.charAt(3)>='0' && i.charAt(4)=='-' && i.charAt(5)<='9' && i.charAt(5)>='0' && i.charAt(6)<='9' && i.charAt(6)>='0'
                    && i.charAt(7)=='-' && i.charAt(8)>='0' && i.charAt(8)<='9' && i.charAt(9)<='9' && i.charAt(9)>='0' ){
                a.setTipus("Data");
                a.setRellevant(true);
            }
            else if(i.equals("")){
                if (a.getType().equals("")){
                    a.setTipus("Buit");
                    a.setRellevant(false);
                }
            }
            else {
                for (int p = 0; p < i.length() && ranged; ++p){
                    if (!((i.charAt(p)>='0' && i.charAt(p)<='9') || i.charAt(p)=='.')) ranged = false;
                }
                if(ranged){
                    if (new_type_item) {
                        double min=Double.valueOf(vsv.get(pos));
                        double max=Double.valueOf(vsv.get(pos));
                        a.setTipus("Rang");
                        a.setRellevant(true);
                        Ranged_Atribute ra = new Ranged_Atribute(a.getName(), a.getType(),min, max );
                        va.set(pos, ra);
                    }
                    else{
                        if (a.getType().equals("Rang")){
                            double aux = Double.valueOf(vsv.get(pos));
                            if (a.getUpper()<aux) a.setUpper(aux);
                            if (a.getLower()>aux) a.setLower(aux);
                        }
                        else{
                            double min=Double.valueOf(vsv.get(pos));
                            double max=Double.valueOf(vsv.get(pos));
                            a.setTipus("Rang");
                            a.setRellevant(true);
                            Ranged_Atribute ra = new Ranged_Atribute(a.getName(), a.getType(),min, max );
                            va.set(pos, ra);
                        }
                    }

                }
                else {//si la estaba creado y no tenia valor de string
                    a.setTipus("String");
                    a.setRellevant(true);
                }
            }
        }


        //creamos item
        int id = Integer.valueOf(datos_valors.get(pos_id));
        Item i =new Item(id, ti, vsv);
        if (!(itemList.existeix_item(id))) itemList.anyadir_item(i);
        return itemList.getItems();
    }

    static void printInfo() {

        System.out.println("\nDRIVER DE LA CLASE K_Neareast_Neightbour\n");
        System.out.println("Funciones de la clase disponibles para probar:\n");
        System.out.println(
                "    1: Crear K_Neareast_Neightbour Vacio\n    2: Algorithm()\n    3: Prueba del Algorithm() sencilla\n    4: PASAR A PRUEBA ESTATICA.\n" +
                "    5: FINALIZAR PRUEBA");
    }

    static void testFunction(int f) throws Exception {

        switch (f) {

            case 1:

            {
                if (prueba_static)
                    System.out.println("ESTAS EN PRUEBA ESTATICA, LA SIGUIENE FUNCION NO AFECTARA AL CONJUNT ESTATIC,\n" +
                            "SI QUERES REDEFINIR ESTE, USA LA OPCION PRUEBA ESTATICA");
                    System.out.println("================================================================================================");
                    System.out.println("Prueba la creadora de K_Neareast_Neightbour");

                    K_Neareast_Neightbour prueba = new K_Neareast_Neightbour();

                    System.out.println("El K_Neareast_Neightbour se ha creado correctamente, pero como no almacena nada no tiene nada que motrar: \n");
                    System.out.println("=================================================================================================");
                break;
            }

            case 2:

            {
                if(!prueba_static){

                    long mil = (long) 1000.0;
                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion Algorithm()");
                    System.out.println("Introduce los siguientes dos ficheros de Items de la siguiente forma: ./path/file.csv ./path/file.csv");

                    Scanner s = new Scanner(System.in);
                    String file1 = s.next();
                    String file2 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);
                    ArrayList<Item> A2 = loadItems(file2);

                    System.out.println("Seguidamente pon todas las valoraciones del segundo fichero, teniendo en cuenta que: ");
                    System.out.println("Que las valoraciones son del tipo Double (separa el decimal con ,) y");
                    System.out.println("que tienes que poner el mismo numero de valoraciones, que el numero de items del segundo fichero");

                    ArrayList<Double> Valoracions = new ArrayList<Double>();
                    for (int i = 0; i < A2.size(); ++i)
                        Valoracions.add(s.nextDouble());

                    System.out.println("Finalmente introduce el valor de k, teniendo en cuenta que k és un entero: ");

                    int k = s.nextInt();

                    long start_time = System.currentTimeMillis();

                    Conjunt_Items Ct = new Conjunt_Items(A1);
                    K_Neareast_Neightbour prueba = new K_Neareast_Neightbour();
                    ArrayList<Item> Resultado = prueba.Algorithm(k, Ct, A2, Valoracions).get(0);

                    long end_time = System.currentTimeMillis();
                    long ex_time = (end_time - start_time)/mil;

                    System.out.println("\n Se ejecuta en: " + ex_time + " segundos\n");

                    System.out.println("El K_Neareast_Neightbour se ha creado correctamente y estos son los k items recomendados: \n");
                    for (int i = 0; i < Resultado.size(); ++i)
                        System.out.print(" " + Resultado.get(i).getID());
                    System.out.print("\n");
                    System.out.println("=================================================================================================");
                }
                else {

                    long mil = (long) 1000.0;
                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion Algorithm() con el Connjunt_Items Static");
                    System.out.println("Introduce un fichero de Items de la siguiente forma: ./path/file.csv");

                    Scanner s = new Scanner(System.in);
                    String file1 = s.next();
                    String file2 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);
                    ArrayList<Item> A2 = loadItems(file2);

                    System.out.println("Seguidamente pont todas las valoraciones del fichero, teniendo en cuenta que: ");
                    System.out.println("Que las valoraciones son del tipo Double (separa el decimal con ,) y");
                    System.out.println("que tienes que poner el mismo numero de valoraciones, que el numero de items del segundo fichero");

                    ArrayList<Double> Valoracions = new ArrayList<Double>();
                    for (int i = 0; i < A2.size(); ++i)
                        Valoracions.add(s.nextDouble());

                    System.out.println("Finalmente introduce el valor de k, teniendo en cuenta que k és un entero: ");

                    int k = s.nextInt();

                    long start_time = System.currentTimeMillis();

                    Conjunt_Items Ct = CI_Static;
                    K_Neareast_Neightbour prueba = new K_Neareast_Neightbour();
                    ArrayList<Item> Resultado = prueba.Algorithm(k, Ct, A2, Valoracions).get(0);

                    long end_time = System.currentTimeMillis();
                    long ex_time = (end_time - start_time)/mil;

                    System.out.println("\n Se ejecuta en: " + ex_time + " segundos\n");

                    System.out.println("El K_Neareast_Neightbour se ha creado correctamente y estos son los k items recomendados: \n");
                    for (int i = 0; i < Resultado.size(); ++i)
                        System.out.print(" "  + Resultado.get(i).getID());
                    System.out.print("\n");
                    System.out.println("=================================================================================================");
                }
                break;
            }

            case 3:

            {
                System.out.println("================================================================================================");
                System.out.println("Prueba del algrotimo con un conjunto de Items sencillo predefinido");

                long mil = (long) 1000.0;

                Ranged_Atribute AT = new Ranged_Atribute("Peso", "Rang", 32.4, 75.8);
                Atribute AT2 = new Atribute("Color", "String");
                Atribute AT3 = new Atribute("Fecha", "Data");
                Atribute AT4 = new Atribute("Disponible", "Boolean");
                Atribute AT5 = new Atribute("KeyWords", "Vector de String");

                Ranged_Atribute AU = new Ranged_Atribute("Duracion", "Rang", 60.0, 240.0);
                Atribute AU2 = new Atribute("Idioma", "String");
                Atribute AU4 = new Atribute("Disponible", "Boolean");
                Atribute AU3 = new Atribute("Estreno", "Data");

                ArrayList<Atribute> Tipus1 = new ArrayList<Atribute>(Arrays.asList(AT, AT2, AT3, AT4, AT5));
                ArrayList<Atribute> Tipus2 = new ArrayList<Atribute>(Arrays.asList(AU, AU2, AU4, AU3));

                TipusItem T1 = new TipusItem(Tipus1);
                TipusItem T2 = new TipusItem(Tipus2);

                ArrayList<String> Valors2 = new ArrayList<String>(Arrays.asList("32.4", "Verde", "1992-04-12", "true", "hola;que;tal" ));
                ArrayList<String> Valors3 = new ArrayList<String>(Arrays.asList("75.8", "Verde", "1960-05-03","false", "Buenos;dias;señorita"));
                ArrayList<String> Valors4 = new ArrayList<String>(Arrays.asList("32.4", "Rojo", "2000-12-04", "false", "Habia;una;vez;un;varquito;chuiquito;dias"));
                ArrayList<String> Valors5 = new ArrayList<String>(Arrays.asList("46.8", "Azul", "1999-00-00", "", "hola;que;tal"));

                ArrayList<String> Valors6 = new ArrayList<String>(Arrays.asList("120", "Ingles", "true", "1992-04-12"));
                ArrayList<String> Valors7 = new ArrayList<String>(Arrays.asList("712", "Español", "false", "1960-05-03"));
                ArrayList<String> Valors8 = new ArrayList<String>(Arrays.asList("240", "Ingle", "", "2000-12-04"));
                ArrayList<String> Valors9 = new ArrayList<String>(Arrays.asList("60", "Espa", "true", "1999-00-00"));

                Item a2 = new Item(2, T1, Valors2);
                Item a3 = new Item(3, T1, Valors3);
                Item a4 = new Item(4, T1, Valors4);
                Item a5 = new Item(5, T1, Valors5);

                Item a6 = new Item(10, T2, Valors6);
                Item a7 = new Item(11, T2, Valors7);
                Item a8 = new Item(12, T2, Valors8);
                Item a9 = new Item(13, T2, Valors9);

                ArrayList<Item> Items = new ArrayList<Item>(Arrays.asList(a2, a3, a4, a5));

                System.out.println("Finalmente introduce el valor de k, teniendo en cuenta que k és un entero: ");

                Scanner s = new Scanner(System.in);
                int k = s.nextInt();

                long start_time = System.currentTimeMillis();

                Conjunt_Items Ct = new Conjunt_Items(Items);
                ArrayList<Item> ItemsUsats = new ArrayList<Item>(Arrays.asList(a9, a7, a8, a6));
                ArrayList<Double> Valoracions = new ArrayList<Double>(Arrays.asList(2.1, 4.1, 1.0, 3.2));

                for (int i = 0; i < ItemsUsats.size(); ++i) {

                    Ct.anyadir_item(ItemsUsats.get(i));
                }

                K_Neareast_Neightbour prueba = new K_Neareast_Neightbour ();
                ArrayList<Item> Resultado = prueba.Algorithm(k, Ct, ItemsUsats, Valoracions).get(0);

                long end_time = System.currentTimeMillis();
                long ex_time = (end_time - start_time);

                System.out.println("\n Se ejecuta en: " + ex_time + " milisegundos\n");

                System.out.println("El K_Neareast_Neightbour se ha creado correctamente y estos son los k items recomendados: \n");

                for (int i = 0; i < Resultado.size(); ++i)
                    System.out.print(Resultado.get(i).getID() + " ");

                System.out.print("\n");
                System.out.println("=================================================================================================");
                break;
            }

            case 4:

            {
                System.out.println("================================================================================================");
                System.out.println("A partir de ahora todas las pruebas se haran con el mismo Conjunt_Items, para comenzar inizializa el Conjunt");
                System.out.println("Introduce un fichero de Items de la siguiente forma: ./path/file.csv ");

                Scanner s = new Scanner(System.in);
                String file1 = s.next();
                ArrayList<Item> A1 = loadItems(file1);
                CI_Static = new Conjunt_Items(A1);

                System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");

                prueba_static = true;
                A1 = CI_Static.getItems();

                for (int i = 0; i < A1.size(); ++i)
                    System.out.print(' ' + A1.get(i).getID());
                System.out.print("\n");

                System.out.println("y esta la matriz de Distancias: \n");
                ArrayList<ArrayList<Double>> Aux = CI_Static.getDistances();
                for (int i = 0; i < Aux.size(); ++i) {

                    for(int j = 0; j < Aux.size(); ++j)
                        System.out.print(' ' + Aux.get(i).get(j));

                    System.out.print("\n");
                }
                System.out.println("=================================================================================================");
                break;
            }

            case 5:break;

            default : System.out.println("No has introducido un número entre 1 y 5");

        }
    }

    public static void main(String[] args) throws Exception {

        Scanner s = new Scanner(System.in);
        int f;
        do{
            printInfo();
            System.out.println("\nSelecciona funcion a probar: ");
            f = s.nextInt();
            testFunction(f);

        }while(f != 5);

    }
}


