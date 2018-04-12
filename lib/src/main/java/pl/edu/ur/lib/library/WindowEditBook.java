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
 * Class of window where we can edit only book informations.
 *
 * @author Marcin
 */
public class WindowEditBook extends JDialog implements ActionListener {

    JTextField fieldTitle;
    JTextField fieldPublishHouse;
    JTextField fieldDesc;
    JTextField fieldYear;
    JTextField fieldISBN;

    String oldTitle;
    String oldLastName;
    pl.edu.ur.lib.DBconnection.Conn conn = new pl.edu.ur.lib.DBconnection.Conn();

    JButton buttonOk = new JButton("OK");
    JButton buttonCancel = new JButton("Cancel");
    JButton buttonExit = new JButton("Exit");

    public WindowEditBook(JFrame main, Data ab) {
        super(main, "Edit Book", true);

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
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);

        Book b = ab.getBook();
        Author a = ab.getAuthor();
        oldTitle = b.getTitle();
        oldLastName = a.getLastName();

        fieldTitle = new JTextField(b.getTitle());
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

        fieldTitle.setPreferredSize(new Dimension(150, 20));
        fieldPublishHouse.setPreferredSize(new Dimension(150, 20));
        fieldYear.setPreferredSize(new Dimension(150, 20));
        fieldDesc.setPreferredSize(new Dimension(150, 20));

        JScrollPane scrollFieldDesc = new JScrollPane(fieldDesc);

        this.setLayout(new GridLayout(5, 1));

        JLabel labelTitle = new JLabel("Title: ");
        JLabel labelPublishHous = new JLabel("Publishing House: ");
        JLabel labelYear = new JLabel("Year: ");
        JLabel labelDesc = new JLabel("Descryption: ");

        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelPublishHouse = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelYear = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelDesc = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel panelButton = new JPanel();

        this.add(panelTitle);
        this.add(panelPublishHouse);
        this.add(panelYear);
        this.add(panelDesc);

        this.add(panelButton);

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

        buttonOk.addActionListener(this);
        buttonCancel.addActionListener(this);

        this.setVisible(true);

        oldTitle = fieldTitle.getText();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        String isbn;
        if (ae.getActionCommand().equals("OK")) {

            isbn = conn.checkBookISBN(oldTitle);
            conn.updateBook(isbn, fieldTitle.getText(), fieldPublishHouse.getText(),
                    Integer.valueOf(fieldYear.getText()), fieldDesc.getText(), oldLastName);
            WindowMain.buttonRefresh.doClick();
            this.dispose();
        } else if (ae.getActionCommand().equals("Cancel")) {
            this.dispose();
        }
    }

}
