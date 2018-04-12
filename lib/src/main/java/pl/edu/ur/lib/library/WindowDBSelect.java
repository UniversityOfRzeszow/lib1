package pl.edu.ur.lib.library;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Window to select database to work.
 *
 * @author Marcin
 */
public class WindowDBSelect extends JFrame implements ActionListener {

    JFrame windowsSelect = new JFrame("Select database");

    public WindowDBSelect() {

        windowsSelect.setIconImage(new ImageIcon(".\\program.png").getImage());
        windowsSelect.setSize(250, 150);
        windowsSelect.setLocationRelativeTo(null);
        windowsSelect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowsSelect.setResizable(false);
        JButton buttonSelect = new JButton("Select");
        JButton buttonHelp = new JButton("Help");
        JLabel labelSelect = new JLabel("Please chose a database file.");
        JPanel panelSelect = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelSelectButton = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel panel = new JPanel(new GridLayout(2, 0));
        windowsSelect.add(panel);
        panelSelect.add(labelSelect);
        panelSelectButton.add(buttonSelect);
        panelSelectButton.add(buttonHelp);

        panel.add(panelSelect);
        panel.add(panelSelectButton);

        buttonHelp.addActionListener(this);
        buttonSelect.addActionListener(this);
        windowsSelect.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // new WindowDBSelect();
                new WindowLogin();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("Select")) {

            windowsSelect.setVisible(false);
            JFileChooser chooser = new JFileChooser("Select database file");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Database files", "FDB");
            chooser.setFileFilter(filter);
            chooser.setCurrentDirectory(new File("."));
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            //chooser.showOpenDialog(null);

            int returnValue = chooser.showOpenDialog(windowsSelect);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                pl.edu.ur.lib.DBconnection.Conn.DB_SID = "lib";
                //selectedFile.getPath();
                //windowsSelect.dispose();
                WindowLogin.openWindow();
            } else if (returnValue == JFileChooser.CANCEL_OPTION) {
                windowsSelect.setVisible(true);
            }

        } else if (ae.getActionCommand().equals("Help")) {
            Process p;
            try {
                p = Runtime.getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler HTU.PDF");
                p.waitFor();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "PDF problem!");
            }

        }
    }
}
