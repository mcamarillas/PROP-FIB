package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;
/** \brief Clase que implementa la vista donde salen los items Valorados de un usuario.
 *  @author Roberto Amat
 */
public class ShowRatedItems {
    /**
     * Instancia del controlador de Presentacion
     */
    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();
    /**
     * frame principal de la vista
     */
    private static JFrame frame;
    /**
     * Boton para ir a la vista del menu principal
     */
    private JButton backButton;
    /**
     * panel principal de la vista
     */
    private JPanel panel;
    /**
     * Lista que contiene la información resumida (id + nombre) de los items recomendados.
     */
    private JList list1;
    /**
     * ScrollPane que hace la list1 scrolleable.
     */
    private JScrollPane scrollPane;

    /**
     * Creadora de la clase
     */
    public ShowRatedItems () {

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(134,114,62);
                this.trackColor  = new Color(187,165,107);
            }
        });
        scrollPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(134,114,62);
                this.trackColor  = new Color(187,165,107);
            }
        });
        scrollPane.getVerticalScrollBar().getComponent(0).setBackground(new Color(134,114,62));
        scrollPane.getVerticalScrollBar().getComponent(1).setBackground(new Color(134,114,62));

        DefaultListModel demoList = new DefaultListModel();
        ArrayList<Integer> items = CtrlPres.getRatedItems();
        for(int i = 0; i < items.size(); ++i) {
            String l = "";
            l += items.get(i) + ": " + (CtrlPres.getTitle(items.get(i)));
            demoList.addElement(l);
        }
        list1.setModel(demoList);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                int x = frame.getX();
                int y = frame.getY();
                CtrlPres.inicializePresentation(x,y);
                frame.dispose();


            }
        });

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedValue = String.valueOf(list1.getSelectedValue());
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
        };
        list1.addMouseListener(mouseListener);
    }

    /**
     * Te permite mostrar el frame junto con todos los atributos
     * @param x posicion x donde se inicializa
     * @param y posicion y donde se inicializa
     */
    public void showWindow(int x, int y) {
        frame = new JFrame("Sistema Recomanador");
        frame.setContentPane(this.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(x,y,600,600);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}
