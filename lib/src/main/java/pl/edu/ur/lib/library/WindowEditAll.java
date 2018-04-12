package pl.edu.ur.lib.library;

import pl.edu.ur.lib.dataModel.Author;
import pl.edu.ur.lib.dataModel.Book;
import pl.edu.ur.lib.dataModel.Data;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Class of window where we can edit all information.
 *
 * @author Marcin
 */
public class WindowEditAll extends JDialog implements ActionListener {

    JTextField fieldTitle;
    JTextField fieldName;
    JTextField fieldLastName;
    JTextField fieldPublishHouse;
    JTextField fieldDesc;
    JTextField fieldYear;
    JTextField fieldISBN;

    int idTemp;
    pl.edu.ur.lib.DBconnection.Conn conn = new pl.edu.ur.lib.DBconnection.Conn();

    JButton buttonOk = new JButton("OK");
    JButton buttonCancel = new JButton("Cancel");
    JButton buttonExit = new JButton("Exit");

    String oldLastName;
    String oldTitle;

    public WindowEditAll(JFrame main, Data ab) {
        super(main, "Edit all", true);

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

        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setTitle("Edit All");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);

        Author a = ab.getAuthor();
        Book b = ab.getBook();

        String oldLastName2 = (a.getLastName());

        fieldTitle = new JTextField(b.getTitle());
        fieldName = new JTextField(a.getName());
        fieldLastName = new JTextField(a.getLastName());
        oldLastName = oldLastName2;
        oldTitle = fieldTitle.getText();
        fieldPublishHouse = new JTextField(b.getPublishingHouse());
        fieldYear = new JTextField(Integer.toString(b.getYear()));
        fieldYear.setDocument(new JTextFieldLimit(5));
        fieldYear.setText(Integer.toString(b.getYear()));

        fieldYear.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))
                        && (caracter != '\b')) {
                    e.consume();
                }
            }
        });

        fieldDesc = new JTextField(b.getDescription());
        fieldISBN = new JTextField(b.getID());

        fieldName.setPreferredSize(new Dimension(150, 20));
        fieldLastName.setPreferredSize(new Dimension(150, 20));
        fieldTitle.setPreferredSize(new Dimension(150, 20));
        fieldPublishHouse.setPreferredSize(new Dimension(150, 20));
        fieldYear.setPreferredSize(new Dimension(150, 20));
        fieldDesc.setPreferredSize(new Dimension(150, 20));
        JScrollPane scrollFieldDesc = new JScrollPane(fieldDesc);

        this.setLayout(new GridLayout(7, 1));

        JLabel labelID = new JLabel("ID: ");
        JLabel labelName = new JLabel("Name: ");
        JLabel labelLastName = new JLabel("LastName: ");
        JLabel labelTitle = new JLabel("Title: ");
        JLabel labelPublishHous = new JLabel("Publishing House: ");
        JLabel labelYear = new JLabel("Year: ");
        JLabel labelDesc = new JLabel("Descryption: ");;

        JPanel panelName = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelLastName = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelPublishHouse = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelYear = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelDesc = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel panelButton = new JPanel();

        panelName.add(labelName);
        panelName.add(fieldName);
        panelLastName.add(labelLastName);
        panelLastName.add(fieldLastName);
        panelTitle.add(labelTitle);
        panelTitle.add(fieldTitle);
        panelPublishHouse.add(labelPublishHous);
        panelPublishHouse.add(fieldPublishHouse);
        panelYear.add(labelYear);
        panelYear.add(fieldYear);
        panelDesc.add(labelDesc);
        panelDesc.add(fieldDesc);

        panelButton.add(buttonOk);
        panelButton.add(buttonCancel);

        this.add(panelName);
        this.add(panelLastName);
        this.add(panelTitle);
        this.add(panelPublishHouse);
        this.add(panelYear);
        this.add(panelDesc);
        this.add(panelButton);

        buttonOk.addActionListener(this);
        buttonCancel.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int id = 0;
        int newAuthorId = 0;
        String isbn;
        if (ae.getActionCommand().equals("OK")) {
            id = conn.checkAuthorID(oldLastName);
            isbn = conn.checkBookISBN(oldTitle);
            newAuthorId = conn.checkNewAuthorID(fieldLastName.getText());

            conn.updateBook(isbn, fieldTitle.getText(), fieldPublishHouse.getText(), 
                 Integer.valueOf(fieldYear.getText()), fieldDesc.getText(), oldLastName);
            conn.updateAuthor(oldLastName, fieldName.getText(), fieldLastName.getText());
             WindowMain.buttonRefresh.doClick();
            this.dispose();
        } else if (ae.getActionCommand().equals("Cancel")) {

            this.dispose();
        }

    }

}
