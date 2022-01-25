package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorDomini;
import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/** \brief Clase que implementa la vista del menu principal de un usuario.
 *  @author Roberto Amat
 */
public class MainMenu {
    /**
     * Instancia del controlador de Presentacion
     */
    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();
    /**
     * frame principal de la vista
     */
    private static JFrame frame;
    /**
     * frame principal de la vista
     */
    private JPanel panel;
    /**
     * Boton para ir a los ajustes
     */
    private JButton settingsButton;
    /**
     * Boton para ir a la vista ShowRatedItems
     */
    private JButton showRatedItemsButton;
    /**
     * Boton para ir a la vista ShowAllItems
     */
    private JButton showAllItemsButton;
    /**
     * Boton para ir a la vista ShowRecomendedItems con las
     * recomendiaciones generadas por el Collaboratibe Filtering.
     */
    private JButton showRecomendedItemsByCFButton;
    /**
     * Label que contiene el titulo de la vista.
     */
    private JLabel Label;
    /**
     * Boton para ir a la vista ShowRecomendedItems con las
     * recomendiaciones generadas por el Hybrid.
     */
    private JButton ShowRecomendedItemsByHybridButton;
    /**
     * Boton para ir a la vista ShowRecomendedItems con las
     * recomendiaciones generadas por el Conted Based.
     */
    private JButton showRecomendedItemsByCBButton;

    /**
     * Creadora de la clase
     */
    public MainMenu() {


        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ControladorDomini dom = ControladorDomini.getInstance();
                try {
                    disableButtons();
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeProfileView(x,y);
                    frame.dispose();
                    settingsButton.setEnabled(true);
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
                enableButtons();
            }
        });
        showRatedItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowRatedItemsView(x,y);
                    frame.dispose();
                    showRatedItemsButton.setEnabled(true);
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
            }
        });
        showRecomendedItemsByCFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int input = JOptionPane.showConfirmDialog(null, "Do you want to load the recomendation?", "Choose",
                            JOptionPane.OK_CANCEL_OPTION);
                    String numRec = JOptionPane.showInputDialog(null, "Enter the number of items to be recomended", 50);
                    int n = Integer.parseInt(numRec);
                    boolean b = input != 0;
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowRecomendedItemsView(x,y,"CF", b, n);
                    frame.dispose();
                    showRatedItemsButton.setEnabled(true);
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null, "You have not save any recomendation");
                    e.printStackTrace();
                }
            }
        });
        showRecomendedItemsByCBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int input = JOptionPane.showConfirmDialog(null, "Do you want to load the recomendation?", "Choose",
                            JOptionPane.OK_CANCEL_OPTION);
                    String numRec = JOptionPane.showInputDialog(null, "Enter the number of items to be recomended", 50);
                    int n = Integer.parseInt(numRec);
                    boolean b = input != 0;
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowRecomendedItemsView(x,y,"CB", b, n);
                    frame.dispose();
                    showRatedItemsButton.setEnabled(true);
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null, "You have not save any recomendation");
                    e.printStackTrace();
                }
            }
        });

        ShowRecomendedItemsByHybridButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                try {
                    disableButtons();
                    int input = JOptionPane.showConfirmDialog(null, "Do you want to load the recomendation?", "Choose",
                            JOptionPane.OK_CANCEL_OPTION);
                    String numRec = JOptionPane.showInputDialog(null, "Enter the number of items to be recomended", 50);
                    int n = Integer.parseInt(numRec);
                    boolean b = input != 0;
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowRecomendedItemsView(x,y,"Hybrid", b, n);
                    frame.dispose();
                    showRatedItemsButton.setEnabled(true);
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null, "You have not save any recomendation");
                    e.printStackTrace();
                }
            }
        });
        showAllItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowAllItemsView(x,y);
                    frame.dispose();
                    showRatedItemsButton.setEnabled(true);
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
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
        showRecomendedItemsByCFButton.setEnabled(false);
        settingsButton.setEnabled(false);
        showAllItemsButton.setEnabled(false);
        showRatedItemsButton.setEnabled(false);
        showRecomendedItemsByCBButton.setEnabled(false);
        ShowRecomendedItemsByHybridButton.setEnabled(false);
    }
    /**
     * Te permite habilitar todos los botones de la vista
     */
    public void enableButtons() {
        settingsButton.setEnabled(true);
        showRatedItemsButton.setEnabled(true);
        showAllItemsButton.setEnabled(true);
        showRecomendedItemsByCFButton.setEnabled(true);
        showRecomendedItemsByCBButton.setEnabled(true);
        ShowRecomendedItemsByHybridButton.setEnabled(true);
    }
}
