package FONTS.src.persistencia;

import FONTS.src.domini.exceptions.EmptyFileException;
import FONTS.src.domini.exceptions.FileExistsException;
import FONTS.src.domini.exceptions.FileNotExistsException;
import FONTS.src.domini.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


/** \brief Clase que implementa el controlador de persistencia de Items.
 * @author  Oriol Cuellar
 */
public class ControladorPersistenciaItem {

    /** Constructora de la clase.
     */
    public ControladorPersistenciaItem(){
    }
    /** Función que lee Items de un fichero CSV.
     * @param csvFile Path al fichero CSV.
     * @return Vector de Strings que contienen los items leidos.
     */
    public Vector <String> Lector_Items(String csvFile) throws Exception{
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
    /** Función que escribe Items de un fichero CSV.
     * @param csvFile Path al fichero CSV.
     * @param list_items es el conjunto de items que hay que escribir.
     */
    public void Escritor_Items(String csvFile, Conjunt_Items list_items) throws Exception {
        File fichero = new File(csvFile);

        try {
            int n = 0;
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile));
            for (Item i : list_items.getItems()) {//para cada tipo de item
                if (n == 0) bw.write(i.getAtr()+ "\n");
                n++;
                bw.write(i.getString() + "\n");
            }
            bw.close();

        } catch (Exception e) {
            throw e;
        }

    }
}
