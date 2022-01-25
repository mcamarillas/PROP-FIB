package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** \brief Clase que implementa la vista del menu principal del Administrador.
 *  @author Marc Camarillas
 */
public class AdminMainPage {
    /**
     * Instancia del controlador de Presentacion
     */
    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();
    /**
     * frame principal de la vista
     */
    private static JFrame frame;
    /**
     * panel principal de la vista
     */
    private JPanel panel;
    /**
     * Boton para ir a la vista del Perfil
     */
    private JButton settingsButton;
    /**
     * Boton para eliminar item
     */
    private JButton deleteItemButton;
    /**
     * Boton para eliminar usuario
     */
    private JButton deleteUserButton;
    /**
     * Boton para cargar Usuarios y ratings
     */
    private JButton uploadUsersRatingsButton;
    /**
     * Boton para cargar Items
     */
    private JButton uploadItemsButton;
    /**
     * Boton para cargar el fichero Unkown
     */
    private JButton uploadRateValorationButton;
    /**
     * Boton para eliminar todos los dtos cargados en el sistema
     */
    private JButton DeleteButton;
    /**
     * Boton para crear un item
     */
    private JButton createItemButton;
    /**
     * Boton para crear un usuario
     */
    private JButton createUserButton;
    /**
     * Te permite elegir el fichero que quieras de disco
     */
    private JFileChooser fileChooser = new JFileChooser();
    /**
     * Indica si hay items cargados en el sistema
     */
    private boolean items;
    /**
     * Indica si hay ratings cargados en el sistema
     */
    private boolean ratings;
    /**
     * Indica si hay el fichero de unkown cargado en el sistema
     */
    private boolean unkown;

    /**
     * Creadora de la clase
     */
    public AdminMainPage(){
        items = CtrlPres.itemsLoaded();
        ratings = CtrlPres.usersLoaded();
        unkown = CtrlPres.unknownLoaded();



        uploadItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    JOptionPane.showMessageDialog(null,"Load item.csv file");
                    fileChooser.showOpenDialog(fileChooser);
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    CtrlPres.loadItems(path);
                    items = CtrlPres.itemsLoaded();
                } catch (Exception e) {
                    enableButtons();
                    items = CtrlPres.itemsLoaded();
                    JOptionPane.showMessageDialog(null,"The chosen file does not have the correct format");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });
        uploadUsersRatingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    JOptionPane.showMessageDialog(null,"Load ratings.db.csv file");
                    fileChooser.showOpenDialog(fileChooser);
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    CtrlPres.loadRates(path);

                    JOptionPane.showMessageDialog(null,"Load ratings.test.known.csv file");
                    fileChooser.showOpenDialog(fileChooser);
                    String path2 = fileChooser.getSelectedFile().getAbsolutePath();
                    CtrlPres.loadRates(path2);

                    ratings = CtrlPres.usersLoaded();
                } catch (Exception e) {
                    enableButtons();
                    ratings = CtrlPres.usersLoaded();
                    JOptionPane.showMessageDialog(null,"The chosen file does not have the correct format");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        uploadRateValorationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    JOptionPane.showMessageDialog(null,"Load ratings.test.unknown.csv file");
                    fileChooser.showOpenDialog(fileChooser);
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    CtrlPres.loadUnKnown(path);
                    unkown = CtrlPres.unknownLoaded();
                } catch (Exception e) {
                    enableButtons();
                    unkown = CtrlPres.unknownLoaded();
                    JOptionPane.showMessageDialog(null,"The chosen file does not have the correct format");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = frame.getX();
                int y = frame.getY();
                if(items && ratings && unkown) {
                    CtrlPres.changeProfileView(x, y);
                    frame.dispose();
                }
                else if(items && ratings) JOptionPane.showMessageDialog(null,"You have to load the evaluate recomendation file before doing anything else");
                else if(items && unkown) JOptionPane.showMessageDialog(null,"You have to load ratings before doing anything else");
                else if(ratings && unkown) JOptionPane.showMessageDialog(null,"You have to load items before doing anything else");
                else if(items) JOptionPane.showMessageDialog(null,"You have to load ratings and evaluate recomendation file before doing anything else");
                else if(ratings) JOptionPane.showMessageDialog(null,"You have to load items and evaluate recomendation file before doing anything else");
                else if(unkown) JOptionPane.showMessageDialog(null,"You have to load items and ratings before doing anything else");

                else JOptionPane.showMessageDialog(null,"You have to load items, ratings and evaluate recomendation file before doing anything else");
            }
        });

        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int input = JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete all the loaded data?", "Choose",
                            JOptionPane.OK_CANCEL_OPTION);
                    if (input == 0) {
                        CtrlPres.deleteAllData();
                        items = CtrlPres.itemsLoaded();
                        ratings = CtrlPres.usersLoaded();
                        unkown = CtrlPres.unknownLoaded();
                    }
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null,"The chosen file does not have the correct format");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    String itemDeleted = JOptionPane.showInputDialog(null,
                            "Write the id of the item to delete", null);
                    CtrlPres.deleteItem(itemDeleted);
                    items = CtrlPres.itemsLoaded();
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null,"The chosen file does not have the correct format");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    String userCreate = JOptionPane.showInputDialog(null,
                            "Write the id of the user to create", null);
                    String password = JOptionPane.showInputDialog(null,
                            "Write the password", null);
                    CtrlPres.singUp(userCreate,password);
                    ratings = CtrlPres.usersLoaded();
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    String userDeleted = JOptionPane.showInputDialog(null,
                            "Write the id of the user to delete", null);
                    CtrlPres.deleteUser(userDeleted);
                    ratings = CtrlPres.itemsLoaded();
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null,e);
                    e.printStackTrace();
                }
                enableButtons();
            }
        });
        createItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    String str = CtrlPres.getAtributosString();
                    String valors = "";
                    String atribut = "";
                    String[] atributes=str.split(",");
                    boolean primer=true;
                    boolean fail = false;
                    for(String s : atributes) {
                        String val = JOptionPane.showInputDialog(null,
                                s, null);

                        if(primer){
                            if(val.startsWith("-")) {
                                JOptionPane.showMessageDialog(null,"The id can't be negative");
                                fail = true;
                                break;
                            }
                            valors =  valors + val;
                            primer=false;
                        }
                        else{
                            valors =  valors+","+ val;
                        }
                    }
                    if(!fail) CtrlPres.createItem(str,valors);
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null,e);
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

    }

    /**
     * Te permite mostrar el frame junto con todos los atributos
     * @param x posicion x donde se inicializa
     * @param y posicion y donde se inicializa
     */
    public void showWindow(int x, int y) {
        enableButtons();
        frame = new JFrame("Sistema Recomanador");
        frame.setContentPane(this.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(x,y,600,600);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Te permite deshabilitar todos los botones de la vista
     */
    public void disableButtons() {
        uploadItemsButton.setEnabled(false);
        settingsButton.setEnabled(false);
        uploadUsersRatingsButton.setEnabled(false);
        deleteItemButton.setEnabled(false);
        deleteUserButton.setEnabled(false);
        uploadRateValorationButton.setEnabled(false);
        DeleteButton.setEnabled(false);
        createItemButton.setEnabled(false);
        createUserButton.setEnabled(false);
    }
    /**
     * Te permite habilitar todos los botones de la vista
     */
    public void enableButtons() {
        uploadItemsButton.setEnabled(true);
        settingsButton.setEnabled(true);
        uploadUsersRatingsButton.setEnabled(true);
        deleteItemButton.setEnabled(true);
        deleteUserButton.setEnabled(true);
        uploadRateValorationButton.setEnabled(true);
        DeleteButton.setEnabled(true);
        createItemButton.setEnabled(true);
        createUserButton.setEnabled(true);

    }
}
