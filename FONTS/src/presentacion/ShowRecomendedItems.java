package FONTS.src.presentacion;
import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/** \brief Clase que implementa la vista DONDE SALEN LOS ITEMS RECOMENDADOS.
 *  @author Marc Camarillas
 */
public class ShowRecomendedItems {
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
     * lista donde aparecen todos los items recomendados
     */
    private JList list1;
    /**
     * Panel que permite poder desplazarte por la lista list1
     */
    private JScrollPane scrollPane;
    /**
     * Boton que te permite ir a la vista del menu principal
     */
    private JButton backButton;
    /**
     * Boton que te permite evaluar la recomendación que se te ha hecho
     */
    private JButton EvaluateButton;
    /**
     * Boton que te permite guardar la recomendacion que se te ha hecho
     */
    private JButton saveRecomendationButton;

    /**
     * Creadora de la clase
     * @param s es el tipo de algoritmo de recomendacion ("Hybrid", "CF", "CB")
     * @param execute cierto si hay que volver a ejectuar el algoritmo de recomendacion
     * @param n numero de items que se quieren recomendar
     */
    public ShowRecomendedItems(String s, boolean execute, int n) {

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
        ArrayList<Integer> items;
        if(execute) {
            if (s == "CF") items = CtrlPres.getRecomendedItemsSlope();
            else if (s == "CB") items = CtrlPres.getRecomendedItemsCB();
            else items = CtrlPres.getRecomendedItemsHybrid();
        }
        else items = CtrlPres.loadRecomendation(s);
        if(n > items.size()) n = items.size();
        for(int i = 0; i < n; ++i) {
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
        EvaluateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CtrlPres.evaluateRecomendation(s);
            }
        });



        saveRecomendationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(execute) CtrlPres.saveRecomendation(s);
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
