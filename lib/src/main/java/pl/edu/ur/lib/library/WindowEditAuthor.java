package pl.edu.ur.lib.library;

import pl.edu.ur.lib.dataModel.Author;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Class of window wher we can edit only author informations.
 *
 * @author Marcin
 */
public class WindowEditAuthor extends JDialog implements ActionListener {

    JTextField fieldName;
    JTextField fieldLastName;

    int idTemp;
    pl.edu.ur.lib.DBconnection.Conn conn = new pl.edu.ur.lib.DBconnection.Conn();

    JButton buttonOk = new JButton("OK");
    JButton buttonCancel = new JButton("Cancel");
    JButton buttonExit = new JButton("Exit");

    String oldLastName;

    public WindowEditAuthor(JFrame main, Author a) {
        super(main, "Edit author", true);

        System.setProperty("file.encoding", "UTF-8");
        Field charset = null;
        try {
            charset = Charset.class.getDeclaredField("defaultCharset");
        } catch (NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(WindowMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        charset.setAccessible(true);
        try {
            charset.set(null, null);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(pl.edu.ur.lib.library.WindowMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        conn.openConnection();

        this.setSize(300, 150);
        this.setLocationRelativeTo(null);
        this.setTitle("Edit Author");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);

        String oldLastName2 = (a.getLastName());

        fieldName = new JTextField(a.getName());
        fieldLastName = new JTextField(a.getLastName());
        oldLastName = oldLastName2;

        fieldName.setPreferredSize(new Dimension(150, 20));
        fieldLastName.setPreferredSize(new Dimension(150, 20));

        this.setLayout(new GridLayout(3, 1));

        JLabel labelName = new JLabel("Name: ");
        JLabel labelLastName = new JLabel("LastName: ");

        JPanel panelName = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelLastName = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel panelButton = new JPanel();

        this.add(panelName);
        this.add(panelLastName);

        this.add(panelButton);

        panelName.add(labelName);
        panelName.add(fieldName);
        panelLastName.add(labelLastName);
        panelLastName.add(fieldLastName);

        panelButton.add(buttonOk);
        panelButton.add(buttonCancel);

        buttonOk.addActionListener(this);
        buttonCancel.addActionListener(this);

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int id = 0;
        int newAuthorId = 0;

        if (ae.getActionCommand().equals("OK")) {
            id = conn.checkAuthorID(oldLastName);

            newAuthorId = conn.checkNewAuthorID(fieldLastName.getText());

            conn.updateAuthor(oldLastName, fieldName.getText(), fieldLastName.getText());
            WindowMain.buttonRefresh.doClick();
            this.dispose();
        } else if (ae.getActionCommand().equals("Cancel")) {
            this.dispose();
        }

    }

}
