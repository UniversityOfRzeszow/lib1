package pl.edu.ur.lib.library;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Class of window where we can login to selected database.
 *
 * @author Marcin
 */
public class WindowLogin extends JFrame implements ActionListener {

    JTextField fieldLogin = new JTextField("postgres");
    JPasswordField fieldPassword = new JPasswordField("postgres");
    JFrame windowLogin = new JFrame();
    String login = "";
    String pass = "";

    public WindowLogin() {

        windowLogin.setSize(new Dimension(250, 160));
        windowLogin.setLocationRelativeTo(null);
        windowLogin.setTitle("Login");
        windowLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowLogin.setIconImage(new ImageIcon(".\\program.png").getImage());

        JPanel panel = new JPanel(new GridLayout(3, 0, 2, 0));

        windowLogin.add(panel);
        JPanel panelWelcome = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelLogin = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelPassword = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel labelWelcome = new JLabel("Welcome in the virtual library.");
        JLabel labelWelcome2 = new JLabel("To enter just login.");
        JLabel labelLogin = new JLabel("Login: ");
        JLabel labelPass = new JLabel("Password:   ");
        JButton buttonLogin = new JButton("Login");
        JButton buttonClear = new JButton("Clear");

        buttonClear.addActionListener(this);
        buttonLogin.addActionListener(this);

        fieldPassword.setPreferredSize(new Dimension(70, 20));
        fieldLogin.setPreferredSize(new Dimension(70, 20));

        panel.add(panelWelcome);
        panel.add(panelLogin);
        panel.add(panelPassword);

        panelWelcome.setLayout(new GridLayout(2, 0));

        panelWelcome.add(labelWelcome);
        panelWelcome.add(labelWelcome2);

        panelLogin.add(labelLogin);
        panelPassword.add(fieldLogin);
        panelLogin.add(labelPass);
        panelPassword.add(fieldPassword);
        panelLogin.add(buttonLogin);

        panelPassword.add(buttonClear);

        windowLogin.setVisible(true);
    }

    public static void openWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WindowLogin();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("Login")) {

            try {
                pl.edu.ur.lib.DBconnection.Conn.login = fieldLogin.getText();
                pl.edu.ur.lib.DBconnection.Conn.pass = fieldPassword.getText();
                pl.edu.ur.lib.DBconnection.Conn conn = new pl.edu.ur.lib.DBconnection.Conn();
                conn.openConnection();
                if (conn.openConnection() == true) {

                    WindowMain.openWindow(conn);
                    windowLogin.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect login or password!");
                    fieldLogin.setText("");
                    fieldPassword.setText("");
                    pl.edu.ur.lib.DBconnection.Conn.login = "";
                    pl.edu.ur.lib.DBconnection.Conn.pass = "";
                }

            } catch (Exception e) {
                pl.edu.ur.lib.DBconnection.Conn.login = "";
                pl.edu.ur.lib.DBconnection.Conn.pass = "";
                windowLogin.dispose();
            }
        } else {
            fieldLogin.setText("");
            fieldPassword.setText("");
        }

    }
}
