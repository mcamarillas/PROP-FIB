package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** \brief Clase que implementa la vista del perfil de un usuario o administrador.
 *  @author Marc Camarillas
 */
public class ProfileView {
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
    private JLabel id;
    /**
     * Boton para cerrar session e ir a la vista de inicio de sesion
     */
    private JButton logoutButton;
    /**
     * Boton para ir a la vista del menu principal
     */
    private JButton backButton;
    /**
     * Boton para ir a la vista de las estadísticas
     */
    private JButton statsButton;
    /**
     * Boton para poder cambiar la contraseña
     */
    private JButton changePasswordButton;
    /**
     * Boton para eliminar el perfil e ir a la vista de inicio de sesion
     */
    private JButton deleteProfileButton;

    /**
     * Creadora de la clase
     */
    public ProfileView(){
        Integer userID = CtrlPres.getActualUserId();
        String s = userID.toString();
        id.setText(s);
        if(CtrlPres.isAdmin()){
            statsButton.setVisible(false);
            deleteProfileButton.setVisible(false);
        }
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    if(CtrlPres.isAdmin()) {
                        int input = JOptionPane.showConfirmDialog(null, "Do you want to save the changes?", "Choose",
                                JOptionPane.OK_CANCEL_OPTION);
                        if (input == 0) {
                            CtrlPres.saveAll();
                        }
                    } else {
                        CtrlPres.saveRatings();
                    }
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.logout();
                    CtrlPres.changeLogInView(x,y);
                    frame.dispose();
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    String password1 = JOptionPane.showInputDialog(null,
                            "Write the password", null);
                    String password2 = JOptionPane.showInputDialog(null,
                            "Repeat the password", null);
                    if(password1.equals(password2)) CtrlPres.editProfile(password1);
                    else JOptionPane.showMessageDialog(null, "The password does not match");
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null,"Error: Repeat the process");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        deleteProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    String userDeleted = JOptionPane.showInputDialog(null,
                            "Write CONFIRM to delete the profile\n Be sure all your data will be deleted", null);
                   System.out.println(userDeleted);
                    if(userDeleted.equals("CONFIRM")) {
                        CtrlPres.deleteUser(CtrlPres.getActualUserId().toString());
                        CtrlPres.logout();
                        int x = frame.getX();
                        int y = frame.getY();
                        CtrlPres.changeLogInView(x,y);
                        frame.dispose();
                    }
                    else JOptionPane.showMessageDialog(null,"The profile was not deleted");
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null, e);
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeStatsView(x,y);
                    frame.dispose();
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
                enableButtons();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
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
        logoutButton.setEnabled(false);
        backButton.setEnabled(false);
        statsButton.setEnabled(false);
    }
    /**
     * Te permite habilitar todos los botones de la vista
     */
    public void enableButtons() {
        logoutButton.setEnabled(true);
        backButton.setEnabled(true);
        statsButton.setEnabled(true);
    }
}
