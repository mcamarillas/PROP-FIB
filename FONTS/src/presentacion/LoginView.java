package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** \brief Clase que implementa la vista Inicio de sesion.
 *  @author Marc Camarillas
 */
public class LoginView {
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
     * Entrada de texto que te permite escribir la contraseña
     */
    private JPasswordField passwordPasswordField;
    /**
     * Entrada de texto que te permite escribir el username
     */
    private JTextField usernameTextField;
    /**
     * Boton que te permite acceder al menu principal
     */
    private JButton signupButton;
    /**
     * Boton que te permite acceder a la vista de registro
     */
    private JButton signinButton;
    /**
     * Boton que te permite recuperar los datos que se habian cargado anteriormente en el sistema
     */
    private JButton loadSavedDataButton;
    /**
     * Etiqueta que pone username
     */
    private JLabel username;
    /**
     * Etiqueta que pone password
     */
    private JLabel password;

    /**
     * Creadora de la clase
     */
    public LoginView(){
        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    CtrlPres.login(usernameTextField.getText(), passwordPasswordField.getText());
                    int x = frame.getX();
                    int y = frame.getY();
                    if(CtrlPres.isAdmin()) CtrlPres.changeAdminMainView(x,y);
                    else CtrlPres.inicializePresentation(x,y);
                    frame.dispose();
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
                enableButtons();
            }
        });
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(CtrlPres.getUsersSize() == 0)
                        JOptionPane.showMessageDialog(null,"An Administrador should enter before doing this option");
                    else {
                        disableButtons();
                        int x = frame.getX();
                        int y = frame.getY();
                        CtrlPres.changeSignUpView(x, y);
                        frame.dispose();
                    }
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        loadSavedDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    CtrlPres.loadItems("./SaveData/items.csv");
                    CtrlPres.loadRates("./SaveData/ratings.csv");
                    CtrlPres.loadUnKnown("./SaveData/unknown.csv");
                } catch (Exception e) {
                    enableButtons();
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
        signupButton.setEnabled(false);
    }

    /**
     * Te permite habilitar todos los botones de la vista
     */
    public void enableButtons() {
        signupButton.setEnabled(true);
    }
}
