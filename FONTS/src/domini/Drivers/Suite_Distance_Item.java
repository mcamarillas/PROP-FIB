package FONTS.src.domini.drivers;
import java.util.*;
import static org.junit.Assert.*;

import FONTS.src.domini.model.Atribute;
import FONTS.src.domini.model.Item;
import FONTS.src.domini.model.Ranged_Atribute;
import FONTS.src.domini.model.TipusItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value=Parameterized.class)

/** \brief Driver de la classe Item. Suite de Distance.
 *  @author Jordi Olmo
 */

public class Suite_Distance_Item {

    private Double Distancia;
    private Item a, b;

    public Suite_Distance_Item(Double Dis, Item a, Item b) {
        Distancia = Dis;
        this.a = a;
        this.b = b;
    }

    @Parameters
    public static Collection<Object[]> getTestParameters () {

        Ranged_Atribute AT = new Ranged_Atribute("Peso", "Rang", 0.0, 100.0);
        Atribute AT2 = new Atribute("Color", "String");
        Atribute AT3 = new Atribute("Fecha", "Data");
        Atribute AT4 = new Atribute("Disponible", "Boolean");
        Atribute AT5 = new Atribute("KeyWords", "Vector de String");

        ArrayList<Atribute> Tipus1 = new ArrayList<Atribute>(Arrays.asList(AT));
        ArrayList<Atribute> Tipus2 = new ArrayList<Atribute>(Arrays.asList(AT2));
        ArrayList<Atribute> Tipus3 = new ArrayList<Atribute>(Arrays.asList(AT3));
        ArrayList<Atribute> Tipus4 = new ArrayList<Atribute>(Arrays.asList(AT4));
        ArrayList<Atribute> Tipus5 = new ArrayList<Atribute>(Arrays.asList(AT5));

        TipusItem RANG = new TipusItem(Tipus1);
        TipusItem STRING = new TipusItem(Tipus2);
        TipusItem DATA = new TipusItem(Tipus3);
        TipusItem BOOLEAN = new TipusItem(Tipus4);
        TipusItem VECTOR_DE_STRING = new TipusItem(Tipus5);

        ArrayList<String> Valors2 = new ArrayList<String>(Arrays.asList("32.4"));
        ArrayList<String> Valors22 = new ArrayList<String>(Arrays.asList("32.4"));

        ArrayList<String> Valors23 = new ArrayList<String>(Arrays.asList("0.0"));
        ArrayList<String> Valors24 = new ArrayList<String>(Arrays.asList("50.0"));

        ArrayList<String> Valors3 = new ArrayList<String>(Arrays.asList("Verde"));
        ArrayList<String> Valors32 = new ArrayList<String>(Arrays.asList("Verde"));
        ArrayList<String> Valors33 = new ArrayList<String>(Arrays.asList("Rojo"));

        ArrayList<String> Valors4 = new ArrayList<String>(Arrays.asList("2000-12-04"));
        ArrayList<String> Valors42 = new ArrayList<String>(Arrays.asList("2000-12-04"));
        ArrayList<String> Valors43 = new ArrayList<String>(Arrays.asList("2050-12-04"));
        ArrayList<String> Valors44 = new ArrayList<String>(Arrays.asList("2025-12-04"));

        ArrayList<String> Valors5 = new ArrayList<String>(Arrays.asList(""));
        ArrayList<String> Valors52 = new ArrayList<String>(Arrays.asList(""));
        ArrayList<String> Valors53 = new ArrayList<String>(Arrays.asList("true"));

        ArrayList<String> Valors6 = new ArrayList<String>(Arrays.asList("true"));
        ArrayList<String> Valors62 = new ArrayList<String>(Arrays.asList("false"));
        ArrayList<String> Valors63 = new ArrayList<String>(Arrays.asList("true"));

        ArrayList<String> Valors7 = new ArrayList<String>(Arrays.asList("maria;de;la;hoz"));
        ArrayList<String> Valors72 = new ArrayList<String>(Arrays.asList("maria; España"));
        ArrayList<String> Valors73 = new ArrayList<String>(Arrays.asList("maria;de;la;hoz"));

        Item a2 = new Item(2, RANG, Valors2);
        Item a22 = new Item(22, RANG, Valors22);
        Item a23 = new Item(23, RANG, Valors23);
        Item a24 = new Item(24, RANG, Valors24);

        Item a3 = new Item(3, STRING, Valors3);
        Item a32 = new Item(32, STRING, Valors32);
        Item a33 = new Item(33, STRING, Valors33);

        Item a4 = new Item(4, DATA, Valors4);
        Item a42 = new Item(42, DATA, Valors42);
        Item a43 = new Item(43, DATA, Valors43);
        Item a44 = new Item(44, DATA, Valors44);

        Item a5 = new Item(5, BOOLEAN, Valors5);
        Item a52 = new Item(52, BOOLEAN, Valors52);
        Item a53 = new Item(53, BOOLEAN, Valors53);

        Item a6 = new Item(6, BOOLEAN, Valors6);
        Item a62 = new Item(62, BOOLEAN, Valors62);
        Item a63 = new Item(63, BOOLEAN, Valors63);

        Item a7 = new Item(7, VECTOR_DE_STRING, Valors7);
        Item a72 = new Item(72, VECTOR_DE_STRING, Valors72);
        Item a73 = new Item(73, VECTOR_DE_STRING, Valors73);

       return Arrays.asList( new Object[][] {
                { 1.0, a2, a22},
                { 0.5, a23, a24},
                { 0.0, a32, a33},
                { 1.0, a3, a32},
                { 1.0, a4, a42},
                { 0.0, a42, a43},
                { 0.5, a42, a44},
                { 0.0, a5, a52},
                { 0.0, a5, a53},
                { 0.0, a6, a62},
                { 1.0, a6, a63},
                { 0.25, a7, a72},
                { 1.0, a7, a73}
       });

    }
    @Test
    public void TestDistancies() {
        assertEquals( Distancia, a.Distance(b), 0 );
    }
}
