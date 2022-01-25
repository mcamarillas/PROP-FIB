package FONTS.src.domini.drivers;

import FONTS.src.domini.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/** \brief Driver de la ConjuntItems.
 *  @author Jordi Olmo
 */
public class DriverConjuntItems {

    private static Conjunt_Items CI_Static;
    private static boolean prueba_static = false;

    public static Vector<String> Lector_Items(String csvFile) {
        //post: return un vector de les files del csv
        Vector <String>  items = new Vector<String>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            boolean first = true;
            int num_atributes=0;
            while ((line = br.readLine()) != null) {
                items.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return items;
    }

    public static ArrayList<Item> loadItems(String File){

        ArrayList<Item> A_Items = new ArrayList<Item>();
        Vector <String>  items = Lector_Items(File);

        for(int i = 1; i < items.size();++i)
            A_Items.addAll(createItem(items.get(0), items.get(i)));
        return A_Items;
    }

    public static ArrayList<Item> createItem(String atributs, String valors){

        //string to arraylist de valors
        Conjunt_Items itemList = new Conjunt_Items();
        Map<String, TipusItem> itemTypeList = new HashMap<String, TipusItem>();

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

    static void testFunction(int f) {

        switch (f) {

            case 1:

            {
                if (prueba_static)
                    System.out.println("ESTAS EN PRUEBA ESTATICA, LA SIGUIENE FUNCION NO AFECTARA AL CONJUNT ESTATIC,\n" +
                            "SI QUERES REDEFINIR ESTE USA LA OPCION PRUEBA ESTATICA");
                System.out.println("================================================================================================");
                System.out.println("Prueba la creadora de ConjuntItems");
                System.out.println("Introduce los siguientes un fichero de Items de la siguiente forma: ./path/file.csv\n");

                Scanner s = new Scanner(System.in);
                String file1 = s.next();
                ArrayList<Item> A1 = loadItems(file1);

                Conjunt_Items Ct = new Conjunt_Items(A1);

                System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");
                A1 = Ct.getItems();
                for (int i = 0; i < A1.size(); ++i)
                    System.out.print(' ' + A1.get(i).getID());
                System.out.print("\n");

                System.out.println("y esta la matriz de Distancias: \n");
                ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                for (int i = 0; i < Aux.size(); ++i) {

                    for(int j = 0; j < Aux.size(); ++j)
                        System.out.print(' ' + Aux.get(i).get(j));

                    System.out.print("\n");
                }
                System.out.println("=================================================================================================");
                break;
            }

            case 2:

            {
                if (prueba_static)
                    System.out.println("ESTAS EN PRUEBA ESTATICA, LA SIGUIENE FUNCION NO AFECTARA AL CONJUNT ESTATIC,\n" +
                            "SI QUERES REDEFINIR ESTE USA LA OPCION PRUEBA ESTATICA");
                System.out.println("================================================================================================");
                System.out.println("Prueba la creadora de ConjuntItems vacia");
                System.out.println("Como es vacía no hace falta que introduzcas nada: \n");
                Conjunt_Items Ct = new Conjunt_Items();

                System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");
                ArrayList<Item> A1 = Ct.getItems();
                for (int i = 0; i < A1.size(); ++i)
                    System.out.print(' ' + A1.get(i).getID());
                System.out.print("\n");

                System.out.println("y esta la matriz de Distancias: \n");
                ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                for (int i = 0; i < Aux.size(); ++i) {

                    for(int j = 0; j < Aux.size(); ++j)
                        System.out.print(' ' + Aux.get(i).get(j));

                    System.out.print("\n");
                }
                System.out.println("=================================================================================================");
                break;
            }

            case 3:

            {
                if(!prueba_static) {

                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion existeix_item()");
                    System.out.println("Introduce los siguientes un fichero de Items de la siguiente forma: ./path/file.csv\n");

                    Scanner s = new Scanner(System.in);
                    String file1 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);

                    Conjunt_Items Ct = new Conjunt_Items(A1);

                    System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }
                    System.out.println("Ahora introduce los siguientes parametros del Item a comprovar: ID");
                    System.out.println("teniendo en cuenta que Id és un Integer\n");
                    Boolean exist = Ct.existeix_item(s.nextInt());
                    System.out.println("El resultado de la funcion és: \n");
                    System.out.println(exist);
                    System.out.println("=================================================================================================");
                }
                else {

                    System.out.println("Prueba de la funcion existeix_item() Estatica");

                    Conjunt_Items Ct = CI_Static;
                    Scanner s = new Scanner(System.in);

                    System.out.println("Ahora introduce los siguientes parametros del Item a comprovar: ID");
                    System.out.println("teniendo en cuenta que Id és un Integer\n");
                    Boolean exist = Ct.existeix_item(s.nextInt());
                    System.out.println("El resultado de la funcion és: \n");
                    System.out.println(exist);
                    System.out.println("=================================================================================================");
                }
                break;
            }

            case 4:

            {
                if(!prueba_static) {

                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion get_position()");
                    System.out.println("Introduce los siguientes un fichero de Items de la siguiente forma: ./path/file.csv\n");

                    Scanner s = new Scanner(System.in);
                    String file1 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);

                    Conjunt_Items Ct = new Conjunt_Items(A1);

                    System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }
                    System.out.println("Ahora introduce los siguientes parametros del Item a comprovar: ID");
                    System.out.println("teniendo en cuenta que Id és un Integer\n");
                    Item i = new Item(s.nextInt());
                    int pos = Ct.get_position(i);
                    System.out.println("La posicion del item és: \n");
                    System.out.println(pos);
                    System.out.println("=================================================================================================");
                }
                else {

                    System.out.println("Prueba de la funcion get_position() Estatica");

                    Conjunt_Items Ct = CI_Static;
                    Scanner s = new Scanner(System.in);

                    System.out.println("Ahora introduce los siguientes parametros del Item a comprovar: ID");
                    System.out.println("teniendo en cuenta que Id és un Integer\n");
                    Item i = new Item(s.nextInt());
                    int pos = Ct.get_position(i);
                    System.out.println("La posicion del item és: \n");
                    System.out.println(pos);
                    System.out.println("=================================================================================================");
                }
                break;
            }

            case 5:

            {
                if(!prueba_static) {

                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion n_Items()");
                    System.out.println("Introduce los siguientes un fichero de Items de la siguiente forma: ./path/file.csv\n");

                    Scanner s = new Scanner(System.in);
                    String file1 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);

                    Conjunt_Items Ct = new Conjunt_Items(A1);

                    System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }

                    int n = Ct.n_Items();
                    System.out.println("El número de Item és: \n");
                    System.out.println(n);
                    System.out.println("=================================================================================================");
                }
                else {
                    System.out.println("Prueba de la funcion n_Items() Estatica");

                    Conjunt_Items Ct = CI_Static;

                    int n = Ct.n_Items();
                    System.out.println("El número de Item és: \n");
                    System.out.println(n);
                    System.out.println("=================================================================================================");
                }
                break;
            }

            case 6:

            {
                if(!prueba_static) {

                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion getItems()");
                    System.out.println("Introduce los siguientes un fichero de Items de la siguiente forma: ./path/file.csv\n");

                    Scanner s = new Scanner(System.in);
                    String file1 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);

                    Conjunt_Items Ct = new Conjunt_Items(A1);

                    System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }

                    System.out.println("Estos son los ID de los items, resultado de usar getItems() en el conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");
                    System.out.println("=================================================================================================");
                }

                else {

                    System.out.println("Prueba de la funcion getItems() Estatica");

                    Conjunt_Items Ct = CI_Static;

                    System.out.println("Estos son los ID de los items, resultado de usar getItems() en el conjunto: \n");
                    ArrayList<Item> A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");
                    System.out.println("=================================================================================================");
                }
                break;
            }

            case 7:

            {
                if (!prueba_static) {

                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion getDistances()");
                    System.out.println("Introduce los siguientes un fichero de Items de la siguiente forma: ./path/file.csv\n");

                    Scanner s = new Scanner(System.in);
                    String file1 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);

                    Conjunt_Items Ct = new Conjunt_Items(A1);

                    System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }

                    System.out.println("Estos son las distancias de los items, resultado de usar getDistances() en el conjunto: \n");
                    Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }
                    System.out.println("=================================================================================================");
                }
                else {

                    System.out.println("Prueba de la funcion getDistances() Estatica");

                    Scanner s = new Scanner(System.in);
                    Conjunt_Items Ct = CI_Static;
                    System.out.println("Estos son las distancias de los items, resultado de usar getDistances() en el conjunto: \n");
                    ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }
                }
                break;
            }

            case 8:

            {
                if(!prueba_static) {

                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion gDistance()");
                    System.out.println("Introduce los siguientes un fichero de Items de la siguiente forma: ./path/file.csv\n");

                    Scanner s = new Scanner(System.in);
                    String file1 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);

                    Conjunt_Items Ct = new Conjunt_Items(A1);

                    System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }

                    System.out.println("Ahora introduce los siguientes parametros de los dos Items a comprovar: ID");
                    System.out.println("teniendo en cuenta que Id és un Integer\n");
                    Item a = new Item(s.nextInt());
                    Item b = new Item(s.nextInt());
                    Double d = Ct.Distance(a, b);
                    System.out.println("Esta és la distancias de los items, resultado de usar Distance() en el conjunto: \n");
                    System.out.print(d);
                    System.out.println("=================================================================================================");
                }
                else{

                    System.out.println("Prueba de la funcion getDistance() Estatica");

                    Scanner s = new Scanner(System.in);
                    Conjunt_Items Ct = CI_Static;
                    System.out.println("Ahora introduce los siguientes parametros de los dos Items a comprovar: ID");
                    System.out.println("teniendo en cuenta que Id és un Integer\n");
                    Item a = new Item(s.nextInt());
                    Item b = new Item(s.nextInt());
                    Double d = Ct.Distance(a, b);
                    System.out.println("Esta és la distancias de los items, resultado de usar Distance() en el conjunto: \n");
                    System.out.print(d);
                }
                break;
            }

            case 9:

            {
                if(!prueba_static) {

                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion setItems()");
                    System.out.println("Introduce los siguientes un fichero de Items de la siguiente forma: ./path/file.csv\n");

                    Scanner s = new Scanner(System.in);
                    String file1 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);

                    Conjunt_Items Ct = new Conjunt_Items(A1);

                    System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }

                    System.out.println("Ahora introduce otro fichero de la misma forma: ./path/file.csv\n");
                    file1 = s.next();
                    A1 = loadItems(file1);
                    Ct.setItems(A1);

                    System.out.println("El ConjuntItems se ha actualizado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }

                    System.out.println("=================================================================================================");
                }

                else {
                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion setItems() Estatica");

                    Scanner s = new Scanner(System.in);
                    Conjunt_Items Ct = CI_Static;

                    System.out.println("Introduce un fichero de la forma: ./path/file.csv\n");
                    String file1 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);
                    Ct.setItems(A1);

                    System.out.println("El ConjuntItems se ha actualizado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }
                }
                break;
            }

            case 10:

            {
                if (!prueba_static) {
                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion omplir_matriu()");
                    System.out.println("Introduce los siguientes un fichero de Items de la siguiente forma: ./path/file.csv\n");

                    Scanner s = new Scanner(System.in);
                    String file1 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);

                    Conjunt_Items Ct = new Conjunt_Items(A1);

                    System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }

                    System.out.println("Ahora introduce los siguientes parametros del Item a añadir: ID");
                    System.out.println("teniendo en cuenta que Id és un Integer y que no debe pertenecer al ConjuntItems\n");
                    Item a = new Item(s.nextInt());
                    Ct.anyadir_item(a);

                    System.out.println("El ConjuntItems se ha actualizado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }

                    System.out.println("El ConjuntItems se ha actualizado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }
                    System.out.println("=================================================================================================");
                }
                else {

                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion omplir_matricu() Estatica");

                    Scanner s = new Scanner(System.in);
                    Conjunt_Items Ct = CI_Static;

                    System.out.println("Ahora introduce los siguientes parametros del Item a añadir: ID");
                    System.out.println("teniendo en cuenta que Id és un Integer\n");
                    Item a = new Item(s.nextInt());
                    Ct.anyadir_item(a);

                    System.out.println("El ConjuntItems se ha actualizado correctamente y estos son los ID de los items del conjunto: \n");
                    ArrayList<Item> A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double> > Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }

                    System.out.println("El ConjuntItems se ha actualizado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }
                    System.out.println("=================================================================================================");
                }
                break;
            }

            case 11:

            {
                if (!prueba_static) {
                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion anyadir_item()");
                    System.out.println("Introduce los siguientes un fichero de Items de la siguiente forma: ./path/file.csv\n");

                    Scanner s = new Scanner(System.in);
                    String file1 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);

                    Conjunt_Items Ct = new Conjunt_Items(A1);

                    System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }

                    System.out.println("Ahora introduce los siguientes parametros del Item a añadir: ID");
                    System.out.println("teniendo en cuenta que Id és un Integer\n");
                    Item a = new Item(s.nextInt());
                    Ct.anyadir_item(a);

                    System.out.println("El ConjuntItems se ha actualizado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }
                    System.out.println("=================================================================================================");
                }
                else {

                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion añadir_item() Estatica");

                    Scanner s = new Scanner(System.in);
                    Conjunt_Items Ct = CI_Static;

                    System.out.println("Ahora introduce los siguientes parametros del Item a añadir: ID");
                    System.out.println("teniendo en cuenta que Id és un Integer\n");
                    Item a = new Item(s.nextInt());
                    Ct.eliminar_item(a);

                    System.out.println("El ConjuntItems se ha actualizado correctamente y estos son los ID de los items del conjunto: \n");
                    ArrayList<Item> A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double> > Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }
                    System.out.println("=================================================================================================");
                }
                break;
            }

            case 12: {

                if (!prueba_static) {

                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion eliminar_item()");
                    System.out.println("Introduce los siguientes un fichero de Items de la siguiente forma: ./path/file.csv\n");

                    Scanner s = new Scanner(System.in);
                    String file1 = s.next();
                    ArrayList<Item> A1 = loadItems(file1);

                    Conjunt_Items Ct = new Conjunt_Items(A1);

                    System.out.println("El ConjuntItems se ha creado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double>> Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }

                    System.out.println("Ahora introduce los siguientes parametros del Item a eliminar: ID");
                    System.out.println("teniendo en cuenta que Id és un Integer\n");
                    Item a = new Item(s.nextInt());
                    Ct.anyadir_item(a);

                    System.out.println("El ConjuntItems se ha actualizado correctamente y estos son los ID de los items del conjunto: \n");
                    A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }
                    System.out.println("=================================================================================================");
                }
                else {

                    System.out.println("================================================================================================");
                    System.out.println("Prueba de la funcion eliminar_item() Estatica");

                    Scanner s = new Scanner(System.in);
                    Conjunt_Items Ct = CI_Static;

                    System.out.println("Ahora introduce los siguientes parametros del Item a eliminar: ID");
                    System.out.println("teniendo en cuenta que Id és un Integer\n");
                    Item a = new Item(s.nextInt());
                    Ct.eliminar_item(a);

                    System.out.println("El ConjuntItems se ha actualizado correctamente y estos son los ID de los items del conjunto: \n");
                    ArrayList<Item> A1 = Ct.getItems();
                    for (int i = 0; i < A1.size(); ++i)
                        System.out.print(' ' + A1.get(i).getID());
                    System.out.print("\n");

                    System.out.println("y esta la matriz de Distancias: \n");
                    ArrayList<ArrayList<Double> > Aux = Ct.getDistances();
                    for (int i = 0; i < Aux.size(); ++i) {

                        for (int j = 0; j < Aux.size(); ++j)
                            System.out.print(' ' + Aux.get(i).get(j));

                        System.out.print("\n");
                    }
                    System.out.println("=================================================================================================");
                }
                break;
            }

            case 13:

            {
                System.out.println("================================================================================================");
                System.out.println("A partir dr ahora todas las pruebas se haran con el mis Conjunt_Items, para comenzar inizializa el Conjunt");
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

            case 14:break;

            default : System.out.println("\nNo has introducido un número entre 1 y 14");

        }
    }

    static void printInfo() {

        System.out.println("\nDRIVER DE LA CLASE ConjuntItems\n");
        System.out.println("Funciones de la clase disponibles para probar:\n");
        System.out.println(
                "    1: Crear ConjuntItems\n    2: Crear Conjunt_items vacio\n    3: existeix_item()\n" +
                "    4: get_position()\n    5: n_Items()\n    6: getItems()\n" +
                "    7: getDistances()\n    8: Distance()\n    9: setItems()\n" +
                "    10: omplir_matriu()\n    11: anyadir_item()\n    12: eliminar_item()\n" +
                "    13: PASAR A PRUEBA ESTATICA.\n    14: FINALIZAR PRUEBA");
    }


    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        int f;
        do{
            printInfo();
            System.out.println("\nSelecciona funcion a probar: ");
            f = s.nextInt();
            testFunction(f);

        }while(f != 14);
    }
}
