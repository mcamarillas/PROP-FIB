package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URL;
import javax.imageio.ImageIO;

/** \brief Clase que implementa la vista donde salen los atributos del item seleccionado.
 *  @author Roberto Amat
 */
public class showAtributes {

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
     * Boton para cambiar la valoracion del usuario sobre el item
     */
    private JButton valorate;
    /**
     * Label con la valoracion del usuario del item, si no tiene informa del hecho.
     */
    private JLabel valoration;
    /**
     * Label con el id del item seleccionado
     */
    private JLabel item;
    /**
     * ScrollPane que hace la tabla scrolleable.
     */
    private JScrollPane scrollPane;
    /**
     * Tabla que contiene los atributos y sus valores relacionados
     */
    private JTable tabla;
    /**
     * Label que sirve para mostrar la imagen del item, si tiene
     */
    private JLabel label;
    /**
     * Path que dirige a la imagen del item, si tiene.
     */
    private String path;

    /**
     * Classe privada que extiende de la defaulttable para redefinir esta y que no se puedan editar las celdas.
     */
    private class MiModelo extends DefaultTableModel
    {
        public MiModelo(Object[][] o, Object[] toArray) {
            super(o, toArray);
        }

        public boolean isCellEditable (int row, int column)
        {
            // Aquí devolvemos true o false según queramos que una celda
            // identificada por fila,columna (row,column), sea o no editable
            return false;
        }
    }

    /**
     * Funcion auxiliar que tranforma el String del parametro para que quepa en una ventana de largura maxDialogWidth
     * @param message mensaje que se quiere transformar
     *  @param maxDialogWidth maximo tamanyo de la ventana donde se quiere que se ajuste el String message
     */
    private static String getMessage(String message, int maxDialogWidth) {
        String string;
        JLabel label = new JLabel(message);
        if (label.getPreferredSize().width > maxDialogWidth) {
            string = "<html><body><p style='width:" + maxDialogWidth + "px;'>"+message+"</p></body></html>";
        } else {
            string = "<html><body><p>" + message+ "</p></body></html>";
        }
        return string;
    }

    /**
     * Creadora de la classe segun el item especificado en el parametro
     * @param id identificador del item seleccionado
     */

    public showAtributes(String id) throws IOException {

        item.setText("ID: " + id);

        scrollPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(134,114,62);
                this.trackColor  = new Color(187,165,107);
            }
        });

        ArrayList<String> atributos = CtrlPres.getAtributos();
        ArrayList<String> valores = CtrlPres.getValors();

        String valoracion = CtrlPres.getValoration(String.valueOf(id));
        valoration.setText("valoration: " + valoracion);

        scrollPane.getVerticalScrollBar().getComponent(0).setBackground(new Color(134,114,62));
        scrollPane.getVerticalScrollBar().getComponent(1).setBackground(new Color(134,114,62));

        MiModelo model = new MiModelo(null, atributos.toArray());
        model.addRow(valores.stream().toArray());
        tabla = new JTable(model);

        tabla.setRowHeight(20);

        TableColumnModel columnModel = tabla.getColumnModel();
        for (int i = 0; i < valores.size(); i++){
            if(atributos.get(i).equals("img_url")) path = valores.get(i);
            columnModel.getColumn(i).setPreferredWidth(100);
        }
        int width = 100 * valores.size();
        System.out.println(width);
        tabla.setMinimumSize(new Dimension(width, 40));
        tabla.setMaximumSize(new Dimension(width, 40));
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.setViewportView(tabla);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = frame.getX();
                int y = frame.getY();
                CtrlPres.inicializePresentation(x, y);
                frame.dispose();
            }
        });

        valorate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int input = JOptionPane.showConfirmDialog(null, "Vols sobreescriure la valoracio?", "Selecciona una opció...",
                        JOptionPane.OK_CANCEL_OPTION);
                if (input == 0) {
                    String new_valoration = JOptionPane.showInputDialog(null,
                            "Escriu la nova valoració", null);
                    CtrlPres.SetValoration(String.valueOf(id), new_valoration);
                    valoration.setText("valoration: " + new_valoration);
                    CtrlPres.addValorationItem(Float.valueOf(new_valoration));
                }
            }
        });

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tabla.rowAtPoint(e.getPoint());
                    int columna = tabla.columnAtPoint(e.getPoint());
                    if (fila == 0){

                        String text = valores.get(columna);
                        text = getMessage(text, 600);
                        JOptionPane.showMessageDialog(null, text);
                    }
                }
            }
        };



        tabla.addMouseListener(mouseListener);

        try {
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);
            label.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * Te permite mostrar el frame junto con todos los atributos, es de un tamaño ligeramente superior al resto
     * @param x posicion x donde se inicializa
     * @param y posicion y donde se inicializa
     */

    public void showWindow(int x, int y) {
        frame = new JFrame("Sistema Recomanador");
        frame.setContentPane(this.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(x,y,750,750);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
