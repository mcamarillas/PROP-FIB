package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
/** \brief Clase que implementa la vista del menu principal de un usuario.
 *  @author Roberto Amat
 */
public class StatsView {
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
     * Boton que te lleva a la vista de atributos de el item favorito del usuario
     */
    private JButton favouriteItem;
    /**
     * Campo de Texto usado para indicar la media de las valoraciones del usuario
     */
    private JLabel avgRating;
    /**
     * Campo de Texto usado para indicar el numero de items puntuados por el usuario
     */
    private JLabel numRated;
    /**
     * Boton para ir a la vista del menu principal
     */
    private JButton backButton;

    /**
     * Creadora de la clase
     */
    public StatsView(){
        Integer num = CtrlPres.numRated();
        String sNum = num.toString();
        numRated.setText(sNum);
        Integer avg = CtrlPres.avgRating();
        String sAvg = avg.toString();
        avgRating.setText(sAvg);
        Integer fav = CtrlPres.itemFavourite();
        String sFav = fav.toString();
        if(fav != 0) favouriteItem.setText(sFav);
        else favouriteItem.setText("You have not valorated item");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = frame.getX();
                int y = frame.getY();
                CtrlPres.changeProfileView(x,y);
                frame.dispose();
            }
        });
        favouriteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (favouriteItem.getText() != "You have not valorated item") {
                    String selectedValue = favouriteItem.getText();
                    String selectedItem = "";
                    for(char c : selectedValue.toCharArray()) {
                        if(c == ':') break;
                        else selectedItem += c;
                    }
                    CtrlPres.selectItem(selectedItem);
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowAtributesView(x,y, selectedItem);
                    frame.dispose();
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
        
    }

    /**
     * Te permite habilitar todos los botones de la vista
     */
    public void enableButtons() {
        
    }

}
