package FONTS.src.domini.drivers;
import FONTS.src.domini.model.Atribute;
import FONTS.src.domini.model.Item;
import FONTS.src.domini.model.TipusItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses; import org.junit.runners.Suite.SuiteClasses;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/** \brief Driver de la classe Item. Suite del resto de funciones
 *  @author Jordi Olmo
 */
public class Suite_Resto_Item {

    private  Atribute AT, AT2, AT3;
    private  ArrayList<Atribute> Tipus1;
    private  TipusItem STRING;
    private  ArrayList<String> Valors;

    @Before
    public  void antesdeclase(){

        AT = new Atribute("Color", "String", false);
        AT2 = new Atribute("Forma", "String", true);
        AT3 = new Atribute("Longitud", "String", false);
        Tipus1 = new ArrayList<Atribute>(Arrays.asList(AT, AT2, AT3));
        STRING = new TipusItem(Tipus1);
        Valors = new ArrayList<String>(Arrays.asList("Verde", "Redondo", "32.7"));
    }

    @Test
    public void getID(){

        Item I = new Item(332);
        assertEquals(I.getID(),332);
    }

    @Test
    public void getTipus(){

        Item I = new Item(332, STRING , Valors);
        assertEquals(I.getTipus(),STRING);
    }

    @Test
    public void getValors(){

        Item I = new Item(332, STRING , Valors);
        assertEquals(I.getValors(),Valors);
    }

    @Test
    public void getString(){

        Item I = new Item(332, STRING , Valors);
        String s = I.getString();
        //System.out.println(s);
        assertEquals(I.getValors(),Valors);
    }

    @Test
    public void setID(){

        Item I = new Item(332, STRING , Valors);
        I.setID(58);
        assertEquals(I.getID(),58);
    }


}
