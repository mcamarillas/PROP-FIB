package FONTS.src.domini.controladors;

import FONTS.src.presentacion.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/** \brief Clase que implementa el controlador de presentacion.
 *  @author  Marc Camarillas
 */
public class ControladorPresentacion {

    /**
     * Instancia singelton del controlador
     */
    private static ControladorPresentacion CtrlPres;
    /**
     * Instancia del controlador del dominio
     */
    private static ControladorDomini CtrlDom = ControladorDomini.getInstance();

    /**
     * Inicializa el controlador de dominio
     */
    private ControladorPresentacion() {
    }
    /**
     * Devuelve la instancia singelton del controlador
     */
    public static ControladorPresentacion getInstance() {
        if(CtrlPres == null) CtrlPres = new ControladorPresentacion();
        return CtrlPres;
    }
    /**
     * Función para canviar a la vista del menu prinicpal del usuario
     * @see MainMenu
     */
    public static void inicializePresentation(int x, int y) {
        MainMenu principalView = new MainMenu();
        principalView.showWindow(x,y);
    }

    /**
     * Función para canviar a la vista que muestra todos los items
     * @see ShowAllItems
     */
    public static void changeShowAllItemsView(int x, int y) {
        ShowAllItems showAllItems = new ShowAllItems();
        showAllItems.showWindow(x,y);
    }
    /**
     * Función para canviar a la vista que muestra los Items Recomendados
     * @see ShowRecomendedItems
     */
    public static void changeShowRecomendedItemsView(int x, int y,String s, boolean b, int n) {
        ShowRecomendedItems showRecomendedItems = new ShowRecomendedItems(s, b, n);
        showRecomendedItems.showWindow(x,y);
    }
    /**
     * Función para canviar a la vista que muestra los items valorados
     * @see ShowRatedItems
     */
    public static void changeShowRatedItemsView(int x, int y) {
        ShowRatedItems showRatedItems = new ShowRatedItems();
        showRatedItems.showWindow(x,y);
    }
    /**
     * Función para canviar a la vista que muestra los atributos de un item
     * @see showAtributes
     */
    public static void changeShowAtributesView(int x, int y, String id)  {
        try {
            showAtributes showAtributes = new showAtributes(id);
            showAtributes.showWindow(x,y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Función para canviar a la vista principal del administrador
     * @see AdminMainPage
     */
    public static void changeAdminMainView(int x, int y){
        AdminMainPage adminMainPage = new AdminMainPage();
        adminMainPage.showWindow(x,y);
    }
    /**
     * Función para canviar a la vista de registro
     * @see LoginView
     */
    public static void changeLogInView(int x, int y){
        CtrlDom.crearCarpeta();
        LoginView loginView = new LoginView();
        loginView.showWindow(x,y);
    }
    /**
     * Función para canviar a la vista del Perfil
     * @see ProfileView
     */
    public static void changeProfileView(int x, int y){
        ProfileView profileView = new ProfileView();
        profileView.showWindow(x,y);
    }
    /**
     * Función para canviar a la vista de las Estadicticas
     * @see StatsView
     */
    public static void changeStatsView(int x, int y){
        StatsView statsView = new StatsView();
        statsView.showWindow(x,y);
    }
    /**
     * Función para canviar a la vista de crar usuario
     * @see SignUp
     */
    public static void changeSignUpView(int x, int y){
        SignUp signUp = new SignUp();
        signUp.showWindow(x,y);
    }

    /**
     * Función obtener todos los items que hay cargados
     * @return Vector<Integer> son los id de todos los items
     */
    public static Vector<Integer> getAllItems(){
        Vector<Integer> s = new Vector<>();
        try {
            s = CtrlDom.AllItems();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e + "\nNo items Loaded", "Error ", JOptionPane.ERROR_MESSAGE);
            System.out.println(s.get(0));
        }
        return s;
    }
    /**
     * Función obtener todos los items valorados por el usuario actual
     * @return ArrayList<Integer> son los id de todos los items valorados por el usuario actual
     */
    public static ArrayList<Integer> getRatedItems(){
        ArrayList<Integer> s = new ArrayList<>();
        try {
            s = CtrlDom.getRatedItems();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No items Rated");
            System.out.println(s.get(0));
            JOptionPane.showMessageDialog(null,"No items Rated", "Error ", JOptionPane.ERROR_MESSAGE);
        }
        return s;
    }
    /**
     * Función obtener una recomendacion de items mediante el algoritmo de collaborative filtering
     * @return ArrayList<Integer> son los id de todos los items que se le van a recomendar al usuario ordenados de mejor a peor
     */
    public static ArrayList<Integer> getRecomendedItemsSlope(){
        ArrayList<Integer> s = new ArrayList<>();
        try {
            s = CtrlDom.doSlope(4,10);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No items Recomended", "Error ", JOptionPane.ERROR_MESSAGE);
            System.out.println(s.get(0));
        }
        return s;
    }
    /**
     * Función obtener una recomendacion de items mediante el algoritmo de content based
     * @return ArrayList<Integer> son los id de todos los items que se le van a recomendar al usuario ordenados de mejor a peor
     */
    public static ArrayList<Integer> getRecomendedItemsCB(){
        ArrayList<Integer> s = new ArrayList<>();
        try {
            s = CtrlDom.doKNN();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No items Recomended", "Error ", JOptionPane.ERROR_MESSAGE);
            System.out.println(s.get(0));
        }
        return s;
    }
    /**
     * Función obtener una recomendacion de items mediante el algoritmo hibrido
     * @return ArrayList<Integer> son los id de todos los items que se le van a recomendar al usuario ordenados de mejor a peor
     */
    public static ArrayList<Integer> getRecomendedItemsHybrid(){
        ArrayList<Integer> s = new ArrayList<>();
        try {
            s = CtrlDom.doRecomendation(4,10);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No items Recomended", "Error ", JOptionPane.ERROR_MESSAGE);
        }
        return s;
    }
    /**
     * Carga un conjunto de items al sistema
     * @param path es el documento de donde se quiere leer
     */
    public void loadItems(String path) {
       try {
           CtrlDom.loadItems(path);
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e + "\nError while reading the file");
       }
    }
    /**
     * Carga un conjunto de usuarios y las valoraciones que le han dado a los items
     * @param path es el documento de donde se quiere leer
     */
    public void loadRates(String path) {
        try {
            CtrlDom.loadRates(path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + "\nError while reading the file");
        }
    }
    /**
     * Carga un conjunto de usuarios con valoraciones ficticias que sirve para poder evaluar recomendaciones
     * @param path es el documento de donde se quiere leer
     */
    public void loadUnKnown(String path) {
        try {
            CtrlDom.loadUnKnown(path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + "\nError while reading the file");
        }
    }
    /**
     * Carga la recomendacion del usuario actual
     * @param s es el tipo de algoritmo de recomendacion ("Hybrid", "CF", "CB")
     */
    public ArrayList<Integer> loadRecomendation(String s) {
        try {
            return CtrlDom.loadRecomendation(s,"./SaveData/Recomendations/recomendation" + s + CtrlDom.getActualUserID() + ".csv");
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    /**
     * Evalua la recomendacion que se le ha dado al usuario actual
     * @param s es el tipo de algoritmo de recomendacion ("Hybrid", "CF", "CB")
     */
    public void evaluateRecomendation(String s) {
        try {
            float f = 0;
            if(s == "Hybrid") f = CtrlDom.evaluateRecomendationGeneral();
            if(s == "CB") f = CtrlDom.evaluateRecomendationKNN();
            else if(s == "CF") f = CtrlDom.evaluateRecomendationSlope();
            JOptionPane.showMessageDialog(null,f);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"It's impossible to evalute this recomendation","Error ", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }
    /**
     * Obtiene el titulo de un item
     * @param itemID id del item del que queremos obtener su titulo
     */
    public String getTitle(Integer itemID) {
        try {
            CtrlDom.selectItem(itemID.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
        ArrayList<String> val = getValors();
        ArrayList<String> atributes = getAtributos();
        for(int i = 0; i < atributes.size(); ++i) {
            if(atributes.get(i).equals("title")) return val.get(i);
        }
        return "Error";
    }

    /**
     * Se inicia la session un usuario
     * @param username id del usuario que quiere iniciar sesion
     * @param password contraseña del usuario que quiere iniciar sesion
     */
    public void login(String username, String password) {
        try {
            CtrlDom.login(username,password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Secrea la cuenta de un usuario
     * @param username id del usuario que quiere crearse la cuenta
     * @param password contraseña del usuario que quiere crearse la cuenta
     */
    public void singUp(String username, String password) {
        try {
            CtrlDom.register(username,password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error while creating the user");
            CtrlDom.getActualUser().getUserID();
        }
    }
    /**
     * Se modifica la contraseña del usuario que esta actualmente en el sistema
     * @param password contraseña nueva que ha introducido el usuario
     */
    public void editProfile(String password) {
        try {
            CtrlDom.editProfile(password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Indica si el usuario que ha iniciado sesion es admin o no
     * @return devuelve cierto si el usuario es admin
     */
    public boolean isAdmin() {
        return CtrlDom.getTypeActualUser() == "admin";
    }
    /**
     * Se cierra la sesion del usuario actual
     */
    public void logout() {
        try {
            CtrlDom.logout();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Funcion para saber el tamaño de usuarios que hay cargados en el sistema
     * @return numero de usuarios cargados en el sistema
     */
    public int getUsersSize() {
        int d = 0;
        try {
            d = CtrlDom.getUsersList().size();
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }
    /**
     * Funcion para saber el item favorito del usuario actual
     * @return devuelve el item favorito del usuario actual
     */
    public int itemFavourite() {
        int d = 0;
        try {
            d = CtrlDom.itemFavourite();
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }
    /**
     * Funcion para saber la media de valoraciones del usuario actual
     * @return devuelve el item favorito del usuario actual
     */
    public int avgRating() {
        int d = 0;
        try {
            d = CtrlDom.avgRating();
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }
    /**
     * Funcion para saber el numer de items valorados por usuario actual
     * @return devuelve numero de items valorados
     */
    public int numRated() {
        int d = 0;
        try {
            d = CtrlDom.numRated();
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }
    /**
     * Funcion para saber el ID del usuario actual
     * @return devuelve el ID del usuario actual
     */
    public Integer getActualUserId() {
        try {
            return CtrlDom.getActualUserID();
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
    /**
     * Funcion para saber la valoracion del item selecionado
     * @return devuelve la valoracion del item selecionado
     */
    public String getValoration(String id) {
        String s;
        try {

            s =  CtrlDom.showItemInfoValoration();
        } catch (Exception e) {
            s = "No té valoració";
        }
        return s;
    }
    /**
     * Funcion para saber si hay items cargados en el sistema
     * @return devuelve cierto si hay items cargados en el sistema
     */
    public boolean itemsLoaded() {
        return CtrlDom.itemsLoaded();
    }
    /**
     * Funcion para saber si hay usuarios cargados en el sistema
     * @return devuelve cierto si hay usuarios cargados en el sistema
     */
    public boolean usersLoaded() {
        return CtrlDom.usersLoaded();
    }
    /**
     * Funcion para saber si hay el fichero de unkown cargado en el sistema
     * @return devuelve cierto si hay el fichero de unkown cargado en el sistema
     */
    public boolean unknownLoaded() {
        return CtrlDom.unknownLoaded();
    }
    /**
     * Elimina todos los ficheros que se han cargado al sistema
     */
    public void deleteAllData() {
         CtrlDom.deleteAll();
    }
    /**
     * Elimina un item del sistema
     * @param id id del item que se quiere eliminar
     */
    public void deleteItem(String id) {
        try {
            CtrlDom.deleteItem(id);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "The item does not exist","Error ", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Guarda los items, usuarios/ratings y el unkown que esten cargados en este momento
     */
    public void saveAll() {
        try {
            CtrlDom.saveRatings();
            CtrlDom.saveItems();
            CtrlDom.saveUnkown();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e + "The item does not exist","Error ", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Guarda ultima recomendacion que se ha hecho
     * @param s es el tipo de algoritmo de recomendacion ("Hybrid", "CF", "CB")
     */
    public void saveRecomendation(String s) {
        try {
            CtrlDom.saveRecomendation(s);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e,"Error ", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Guarda los ratings/usuarios que hayan sido cargados en el sistema
     */
    public void saveRatings() {
        try {
            CtrlDom.saveRatings();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e + "The item does not exist","Error ", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Crea un item
     * @param atributes lista de atributos del item que se quiere crear
     * @param valors lista de valores del item que se quiere crear
     */
    public void createItem(String atributes, String valors) {
        try {
            if(itemsLoaded()) CtrlDom.createItem(atributes,valors);
            else JOptionPane.showMessageDialog(null, "There are no items loaded");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e,"Error ", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Elimina un usuario del sistema
     * @param id id del usuario que se quiere eliminar
     */
    public void deleteUser(String id) {
        try {
            CtrlDom.deleteUser(id);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "The user does not exist","Error ", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Devuelve la lista de atributos del item seleccionado
     */
    public ArrayList<String> getAtributos() {

        ArrayList<String> a = new ArrayList<String> ();
        try {

            a =  CtrlDom.showItemInfoAtributes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"the Item does not exists");
        }
        return a;
    }

    /**
     * Devuelve la lista de atributos del item seleccionado en forma de String
     */
    public String getAtributosString() {
        String a="";
        try {
            a =  CtrlDom.showItemInfoAtributesString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return a;
    }

    /**
     * Devuelve la lista de valores del item seleccionado
     */
    public ArrayList<String> getValors() {

        ArrayList<String> a = new ArrayList<String> ();
        try {
            a =  CtrlDom.showItemInfoValues();
        } catch (Exception e) {
            System.out.println(e);
        }
        return a;
    }
    /**
     * Fija un item como item seleccionado
     * @param id id del item que queremos seleccionar
     */
    public void selectItem(String id) {
        try {
            CtrlDom.selectItem(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Cambia la valoracion de un item
     * @param id id del item al que le queremos cambiar la valoracion
     * @param nv valoracion que queremos ponerle al item
     */
    public void SetValoration(String id, String nv) {

        float new_valoration = Float.valueOf(nv);
        try {
            CtrlDom.rateItem(id, new_valoration);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No s'ha pogut cambiar la valoració","Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addValorationItem(Float f) {
        CtrlDom.addValoratedItem(f);
    }

}
