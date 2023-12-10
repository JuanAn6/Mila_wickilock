/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capavista2;


import capamodel.User;
import interficiepersistencia.IGestorPersistencia;
import capavista2.Rutes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author Juan Antonio
 */
public class Login extends JFrame {
    
    protected static IGestorPersistencia gBD = null;
    protected static User u = null;
    
    private JPanel principal = new JPanel();
    private JLabel titol = new JLabel();
    private JPanel grid = new JPanel();
    private JLabel userLabel = new JLabel("Email o User: ");
    private JLabel passLabel = new JLabel("Password: ");
    private JLabel errorLabel = new JLabel();
    
    private JButton button = new JButton("Iniciar Sessió");
    
    private JTextField user = new JTextField();
    private JPasswordField pass = new JPasswordField();
    
    public Login()
    {
        setSize(900,800);
        //setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("WikiLoc Juan Antonio");
        
        principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));
        
        titol.setFont(new java.awt.Font("Calibri", 1, 40));
        titol.setText("Iniciar sessió");
        titol.setAlignmentX(Component.CENTER_ALIGNMENT);
        principal.add(titol, BorderLayout.NORTH);
        
        
        grid.setLayout(new GridLayout(15,1));
        
        
        userLabel.setFont(new java.awt.Font("Calibri", 1, 20));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setVerticalAlignment(SwingConstants.CENTER);
        user.setPreferredSize(new Dimension(300 , 30));
        user.setAlignmentX(Component.CENTER_ALIGNMENT);
        user.setHorizontalAlignment(JTextField.CENTER);
       
        grid.add(userLabel);
        JPanel aux1 = new JPanel(new FlowLayout());
        aux1.add(user);
        grid.add(aux1);
        
        passLabel.setFont(new java.awt.Font("Calibri", 1, 20));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        pass.setPreferredSize(new Dimension(300 , 30));
        pass.setAlignmentX(Component.CENTER_ALIGNMENT);
        pass.setHorizontalAlignment(JTextField.CENTER);
        
        grid.add(passLabel);
        JPanel aux2 = new JPanel(new FlowLayout());
        aux2.add(pass);
        grid.add(aux2);
        
        
        errorLabel.setForeground(Color.red);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setVerticalAlignment(SwingConstants.CENTER);
        errorLabel.setVisible(false);
        
        
        JPanel aux3 = new JPanel(new FlowLayout());
        button.addActionListener(new ActionListener(
        ) {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(user.getText().length() > 0 && pass.getPassword().length > 0){
                    
                    u = new User();
                    u.setLogin(user.getText());
                    String passString = "";
                    
                    for(char a : pass.getPassword()){
                        passString = passString+=a;
                    }
                    u.setPwd(passString);
                    if(gBD.confirmarCredencialsUsuari(u)){
                        user.setVisible(false);
                        u = gBD.obtenirUser(u.getLogin());
                        
                        Rutes r = new Rutes(u);
                        r.setVisible(true);
                        dispose();
                        
                    }else{
                        errorLabel.setText("Credencials invalides");
                        errorLabel.setVisible(true);
                    }
                }else{
                    
                    errorLabel.setText("Introdueix dades");
                    errorLabel.setVisible(true);
                }
                
            }
        });
        
        
        aux3.add(button);
        grid.add(aux3);
        
        JPanel aux4 = new JPanel();
        aux4.add(errorLabel);
        grid.add(aux4);
        
        principal.add(grid, BorderLayout.CENTER);
        
        add(principal);
        
        this.addWindowListener(
                new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (gBD != null) {      // Per si no s'havia establert connexió
                    try {
                        gBD.tancarConnexio();
                    } catch (Exception ex) {
                        System.out.println("Error en tancar la connexió.\n\nMotiu:\n\n" + ex.getMessage());
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex1) {
                        }
                    }
                }
                System.exit(0);

            }
        });
    }
    
    public static void main(String args[]){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Error.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Error.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Error.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Error.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        if (args.length == 0) {
            System.out.println("Cal passar el nom de la classe que dona la persistència com a primer argument");
            System.exit(0);
        }
        
        try {
            String nomClassePersistencia = args[0];
            // Intent de crear objecte per gestionar la connexió amb la BD
            System.out.println("Intentant establir connexió...");
            gBD = (IGestorPersistencia) Class.forName(nomClassePersistencia).newInstance();
            System.out.println("Connexió establerta");
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+" "+ ex.getStackTrace());
        }
        
        Login l = new Login();
        l.setVisible(true);
    }
}
