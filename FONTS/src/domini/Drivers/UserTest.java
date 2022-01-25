package FONTS.src.domini.drivers;

import FONTS.src.domini.model.Item;
import FONTS.src.domini.model.TipusRol;
import FONTS.src.domini.model.User;
import FONTS.src.domini.model.valoratedItem;

import org.junit.Test;

import java.util.ArrayList;

import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;


public class UserTest extends User {

    @Test
    public void testGetUserID() {
        User u = new User(1234);
        assertEquals(u.getUserID(),1234);
    }

    @Test
    public void testGetPassword() {
        User u = new User(1234, "1234", TipusRol.Usuari);
        assertEquals(u.getPassword(),"1234");
    }

    @Test
    public void testGetRol() {
        User u = new User(1234, "1234", TipusRol.Usuari);
        assertEquals(u.getRol(), TipusRol.Usuari);
    }

    @Test
    public void testGetValoratedItems() {
        User u = new User(1234);
        ArrayList<valoratedItem> items = new ArrayList<valoratedItem>();
        Item item = new Item(1234);
        valoratedItem vItem = new valoratedItem(item,4);
        items.add(vItem);
        u.setValoratedItems(items);
        assertEquals(items,u.getValoratedItems());
    }

    @Test
    public void testGetNumCluster() {
        User u = new User(1);
        assertEquals(u.getNumCluster(),-1);
    }

    @Test
    public void testSetUserID() {
        User u = new User();
        u.setUserID(2);
        assertEquals(u.getUserID(),2);
    }

    @Test
    public void testSetPassword() {
        User u = new User();
        u.setPassword("1234");
        assertEquals(u.getPassword(),"1234");
    }

    @Test
    public void testSetRol() {
        User u = new User();
        u.setRol(TipusRol.Administrador);
        assertEquals(u.getRol(),TipusRol.Administrador);
    }

    @Test
    public void testSetValoratedItems() {
        User u = new User(1234);
        ArrayList<valoratedItem> items = new ArrayList<valoratedItem>();
        Item item = new Item(1234);
        valoratedItem vItem = new valoratedItem(item,4);
        items.add(vItem);
        u.setValoratedItems(items);
        assertEquals(items,u.getValoratedItems());
    }

    @Test
    public void testSetNumCluster() {
        User u = new User(1);
        u.setNumCluster(2);
        assertEquals(u.getNumCluster(),2);
    }

    @Test
    public void testAddvaloratedItem() {
        User u = new User(1);
        u.addvaloratedItem(1234,4);
        assertEquals(u.getValoratedItems().get(0).getItem().getID(),1234);
    }

    @Test
    public void testCalculateSimilarity() {
        int userID1 = 1234;
        //USER 1
        User user1 = new User(userID1);
        ArrayList<valoratedItem> items1 = new ArrayList<valoratedItem>();
        int ID = 1;
        float val = 4;
        Item item = new Item(ID);
        valoratedItem vItem = new valoratedItem(item,val);
        items1.add(vItem);
        ID = 2;
        val = 3;
        Item item1 = new Item(ID);
        valoratedItem vItem1 = new valoratedItem(item1,val);
        items1.add(vItem1);
        ID = 3;
        val = 1;
        Item item2 = new Item(ID);
        valoratedItem vItem2 = new valoratedItem(item2,val);
        items1.add(vItem2);
        ID = 4;
        val = 5;
        Item item3 = new Item(ID);
        valoratedItem vItem3 = new valoratedItem(item3,val);
        items1.add(vItem3);
        //USER 2
        User user2 = new User(userID1);
        ArrayList<valoratedItem> items2 = new ArrayList<valoratedItem>();
        ID = 2;
        val = 3;
        Item item4 = new Item(ID);
        valoratedItem vItem4 = new valoratedItem(item4,val);
        items2.add(vItem4);
        ID = 4;
        val = 1;
        Item item5 = new Item(ID);
        valoratedItem vItem5 = new valoratedItem(item5,val);
        items2.add(vItem5);
        ID = 6;
        val = 1;
        Item item6 = new Item(ID);
        valoratedItem vItem6 = new valoratedItem(item6,val);
        items2.add(vItem6);
        ID = 7;
        val = 5;
        Item item7 = new Item(ID);
        valoratedItem vItem7 = new valoratedItem(item7,val);
        items2.add(vItem7);
        user1.setValoratedItems(items1);
        user2.setValoratedItems(items2);
        assertEquals(user1.calculateSimilarity(user2),14/(sqrt(51)*6), 0.01);
    }
    @Test
    public void testSearchUsedItem() {
        User u = new User(1234);
        u.addvaloratedItem(1234,4);
        assertEquals(u.searchUsedItem(1234).getItem().getID(),1234);
    }
}
