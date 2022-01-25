package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
/** \brief Clase que implementa la vista del menu principal de un usuario.
 *  @author Roberto Amat
 */
public class SignUp{
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
     * Campo de texto para introducir la contraseña
     */
    private JPasswordField passwordPasswordField;
    /**
     * Campo de texto para introducir el usuario
     */
    private JTextField usernameTextField;
    /**
     * Boton para ir a la vista del login
     */
    private JButton backButton;
    /**
     * Boton para rear la cuenta con los valores introducidos
     */
    private JButton SignUpButton;
    /**
     * Campo de texto para introducir la contraseña de nuevo y evitar errores
     */
    private JPasswordField repeatPasswordField;

    private JLabel username;
    private JLabel password;


    /**
     * Creadora de la clase
     */
    public SignUp(){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = frame.getX();
                int y = frame.getY();
                CtrlPres.changeLogInView(x,y);
                frame.dispose();
            }
        });
        SignUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    if (Objects.equals(passwordPasswordField.getText(), repeatPasswordField.getText())) {
                        CtrlPres.singUp(usernameTextField.getText(), passwordPasswordField.getText());
                        int x = frame.getX();
                        int y = frame.getY();
                        CtrlPres.changeLogInView(x, y);
                        frame.dispose();
                    }
                    else JOptionPane.showMessageDialog(null,"The password does not match");
                } catch (Exception e) {
                    enableButtons();
                    if(passwordPasswordField.getText().isEmpty() && repeatPasswordField.getText().isEmpty())
                        JOptionPane.showMessageDialog(null,"password field is empty");
                    else JOptionPane.showMessageDialog(null,"The username already exists or is composed by characters");
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
        backButton.setEnabled(false);
    }

    /**
     * Te permite habilitar todos los botones de la vista
     */
    public void enableButtons() {
        backButton.setEnabled(true);
    }
}
