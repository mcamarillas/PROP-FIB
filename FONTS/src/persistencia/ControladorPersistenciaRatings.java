package FONTS.src.persistencia;

import FONTS.src.domini.exceptions.EmptyFileException;
import FONTS.src.domini.exceptions.FileExistsException;
import FONTS.src.domini.exceptions.FileNotExistsException;
import FONTS.src.domini.exceptions.NotRatingsFileException;
import FONTS.src.domini.model.User;
import FONTS.src.domini.model.valoratedItem;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

/** \brief Clase que implementa el controlador de persistencia de Ratings.
 * @author  Oriol Cuellar
 */
public class ControladorPersistenciaRatings {
    /** Constructora de la clase.
     */
    public ControladorPersistenciaRatings(){
    }

    /** Función que lee valoraciones de un fichero CSV.
     * @param csvFile Path al fichero CSV.
     * @return ArrayList de Vectores de Strings que contienen las valoraciones leidas.
     */
    public ArrayList<Vector<String>> Lector_Ratings(String csvFile ) throws Exception{
        ArrayList <Vector<String>> ratings=new ArrayList<Vector<String>>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int user=0;
        int item=1;
        int rating=2;
        try {
            br = new BufferedReader(new FileReader(csvFile));
        }

        catch (Exception e){
            throw new FileNotExistsException(csvFile);
        }
        try {
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(cvsSplitBy);
                if (datos.length!=3) throw  new NotRatingsFileException(csvFile);
                if (first){//colamos orden. User, item, valoracion
                    if (datos[0].equals("userId")) user=0;
                    if (datos[1].equals("userId")) user=1;
                    if (datos[2].equals("userId")) user=2;
                    if (datos[0].equals("itemId")) item=0;
                    if (datos[1].equals("itemId")) item=1;
                    if (datos[2].equals("itemId")) item=2;
                    if (datos[0].equals("rating")) rating=0;
                    if (datos[1].equals("rating")) rating=1;
                    if (datos[2].equals("rating")) rating=2;
                }
                if(!first) {

                    Vector<String> dentro = new Vector<String>();
                    dentro.add(datos[user]);
                    dentro.add(datos[item]);
                    dentro.add(datos[rating]);
                    ratings.add(dentro);
                }
                first = false;
            }
        }
        catch (Exception e){
            throw e;
        }
        br.close();
        if (ratings.size()==0) throw new EmptyFileException("Lector_Ratings");
        return ratings;

    }
    /** Función que escribe valoraciones de un fichero CSV.
     * @param csvFile Path al fichero CSV.
     * @param list_users es el map de usuarios
     */
    public void Escritor_Ratings(String csvFile, Map <Integer, User> list_users) throws Exception{
        File fichero = new File(csvFile);

            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile));
                String s ="userId,itemId,rating\n";
                bw.write(s);
                for (int i: list_users.keySet()){
                    ArrayList<valoratedItem> valoraciones_usuario = list_users.get(i).getValoratedItems();
                    for (valoratedItem j: valoraciones_usuario){
                        String linea="";
                        linea+= i+ ",";
                        linea+= j.getItem().getID()+ ",";
                        linea+= j.getValoracio()+"\n";
                        bw.write(linea);
                    }
                }

                bw.close();
            } catch (Exception e){
                throw e;
            }
    }
    /** Función que escribe valoraciones del unknown en un fichero CSV.
     * @param csvFile Path al fichero CSV.
     * @param UnKnown es la arraylist de valoraciones
     */
    public void Escritor_Unknown(String csvFile, ArrayList <Vector<String>> UnKnown) throws Exception{
        File fichero = new File(csvFile);
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile));
            String s ="userId,itemId,rating\n";
            bw.write(s);
            for(Vector <String> v: UnKnown){
                String linea="";
                boolean b=true;
                for(String a: v){
                    if (b) linea=linea+a;
                    else linea=linea+","+a;
                    b=false;
                }
                bw.write(linea+"\n");
            }
            bw.close();
        } catch (Exception e){
            throw e;

        }
    }
}
