package pl.edu.ur.lib.library;

import pl.edu.ur.lib.dataModel.Author;
import pl.edu.ur.lib.dataModel.Book;
import pl.edu.ur.lib.dataModel.Data;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 * Class of window where we can add new position to database.
 *
 * @author Marcin
 */
public class WindowAdd extends JDialog implements ActionListener {

    pl.edu.ur.lib.DBconnection.Conn conn = new pl.edu.ur.lib.DBconnection.Conn();
    JTextField fieldAuthorID = new JTextField("");
    JTextField fieldName = new JTextField("");
    JTextField fieldLastName;

    JTextField fieldTitle = new JTextField("");
    JTextField fieldPublishHouse = new JTextField("");
    JTextField fieldDesc = new JTextField("");
    JTextField fieldYear = new JTextField("");

    JFormattedTextField fieldISBN;

    pl.edu.ur.lib.DBconnection.Conn DB = new pl.edu.ur.lib.DBconnection.Conn();

    JButton buttonOk = new JButton("OK");
    JButton buttonCancel = new JButton("Clear");
    JButton buttonExit = new JButton("Exit");

    /**
     * Constructor of main window of program.
     *
     * @param main
     */
    WindowAdd(JFrame main) {
        super(main, "Add", true);
        this.setLayout(new GridLayout(8, 1));
        this.setSize(250, 350);
        this.setLocationRelativeTo(null);
        

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        conn.openConnection();
        fieldLastName = new JTextField("");
        fieldLastName.setToolTipText("Please don't use space.");
        fieldLastName.setTransferHandler(null);
        fieldName.setPreferredSize(new Dimension(70, 20));
        fieldLastName.setPreferredSize(new Dimension(70, 20));
        fieldTitle.setPreferredSize(new Dimension(70, 20));
        fieldPublishHouse.setPreferredSize(new Dimension(70, 20));
        fieldYear.setPreferredSize(new Dimension(70, 20));
        fieldYear.setDocument(new JTextFieldLimit(5));

        fieldYear.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))
                        && (caracter != '\b')) {
                    e.consume();
                }
            }
        });

        fieldLastName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char znak = e.getKeyChar();
                if (znak == 32) {
                    e.consume();
                }
            }
        });

        try {
            fieldISBN = new JFormattedTextField(new MaskFormatter("###-#-##-####-#"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "To enter ISBN you need to use pattern.");
        }

        fieldDesc.setPreferredSize(new Dimension(70, 20));
        fieldISBN.setPreferredSize(new Dimension(70, 20));

        JLabel labelID = new JLabel("ID: ");
        JLabel labelName = new JLabel("Name: ");
        JLabel labelLastName = new JLabel("LastName: ");
        JLabel labelTitle = new JLabel("Title: ");
        JLabel labelPublishHous = new JLabel("Publishing House: ");
        JLabel labelYear = new JLabel("Year: ");
        JLabel labelDesc = new JLabel("Descryption: ");
        JLabel labelISBN = new JLabel("ISBN: ");

        JPanel panelName = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelLastName = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelPublishHouse = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelYear = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelDesc = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelISBN = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));

        this.add(panelName);
        this.add(panelLastName);
        this.add(panelTitle);
        this.add(panelPublishHouse);
        this.add(panelYear);
        this.add(panelDesc);
        this.add(panelISBN);
        this.add(panelButton);

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
        panelISBN.add(labelISBN);
        panelISBN.add(fieldISBN);
        panelButton.add(buttonCancel);
        panelButton.add(buttonOk);
        panelButton.add(buttonExit);

        buttonOk.addActionListener(this);
        buttonCancel.addActionListener(this);
        buttonExit.addActionListener(this);

        //frame1.setVisible(true);
        conn.openConnection();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("OK")) {

            int idTemp = conn.checkAuthorID(fieldLastName.getText());
            String isbnTemp = conn.checkBookISBN(fieldISBN.getText());

            if (fieldName.getText().equals("") && fieldLastName.getText().equals("")
                    && fieldTitle.getText().equals("") && fieldPublishHouse.getText().equals("")
                    && fieldDesc.getText().equals("")
                    && fieldISBN.getText().equals("   - -  -    - ")) {
                JOptionPane.showMessageDialog(null, "Fill the field or click exit.");
            } else if (idTemp == 0 && !fieldISBN.getText().equals("   - -  -    - ")
                    && isbnTemp.equals("")) {

                int newID = conn.returnIdCount();

                Author newA = new Author(newID + 1, fieldName.getText(), fieldLastName.getText());
                Book newB = new Book(fieldISBN.getText(), fieldTitle.getText(), fieldPublishHouse.getText(),
                        checkYear(), fieldDesc.getText());
                Data newAuthor = new Data(newA, newB);

                conn.addAuthor(newA);
                conn.addBook(newB);
                conn.addBound(newAuthor);

                JOptionPane.showMessageDialog(null, "Added");
                WindowMain.buttonRefresh.doClick();
                this.dispose();

            } else if (idTemp != 0 && fieldISBN.getText().equals("   - -  -    - ")
                    && isbnTemp.equals("")) {

                int oldId = idTemp;

                Author a = new Author(oldId, fieldName.getText(), fieldLastName.getText());
                Book b = new Book(fieldISBN.getText(), fieldTitle.getText(),
                        fieldPublishHouse.getText(), checkYear(), fieldDesc.getText());
                Data author = new Data(a, b);

                conn.addBook(b);
                conn.addBound(author);

                JOptionPane.showMessageDialog(null, "Added");
                WindowMain.buttonRefresh.doClick();
                this.dispose();
            } else if (idTemp != 0 && (fieldISBN.getText().equals("   - -  -    - ") == false)) {

                int idTemp2 = conn.checkAuthorID(fieldLastName.getText());
                String isbnTemp2 = conn.checkBookISBN(fieldISBN.getText());

                int control = conn.checkBond(isbnTemp2, idTemp2);
                if (control != 0) {
                    JOptionPane.showMessageDialog(null, "This book of this author already exist in base.");
                } else {
                    Author newA = new Author(idTemp2, fieldName.getText(), fieldLastName.getText());
                    Book b = new Book(fieldISBN.getText(), fieldTitle.getText(),
                            fieldPublishHouse.getText(), checkYear(),
                            fieldDesc.getText());
                    Data author = new Data(newA, b);

                    conn.addBound(author);
                    WindowMain.buttonRefresh.doClick();
                    this.dispose();
                }

            } else if (idTemp == 0 && (fieldISBN.getText().equals("   - -  -    - ") == false)
                    && isbnTemp.equals("")) {
                int newID = conn.returnIdCount();

                Author newA = new Author(newID + 1, fieldName.getText(), fieldLastName.getText());
                Book b = new Book(fieldISBN.getText(), fieldTitle.getText(), fieldPublishHouse.getText(),
                        checkYear(), fieldDesc.getText());
                Data author = new Data(newA, b);

                conn.addAuthor(newA);
                conn.addBound(author);
                JOptionPane.showMessageDialog(null, "Added");
                WindowMain.buttonRefresh.doClick();
                this.dispose();

            } else if (!isbnTemp.equals("")) {
                JOptionPane.showMessageDialog(null, "Book with this ISBN already exist in base.");
            }

//            fieldName.setText("");
//            fieldLastName.setText("");
//            fieldPublishHouse.setText("");
//            fieldDesc.setText("");
//            fieldTitle.setText("");
//            fieldISBN.setText("");
//            fieldYear.setText("");
        } else if (ae.getActionCommand().equals("Clear")) {

            fieldName.setText("");
            fieldLastName.setText("");
            fieldPublishHouse.setText("");
            fieldDesc.setText("");
            fieldTitle.setText("");
            fieldISBN.setText("");
            fieldYear.setText("");

        } else if (ae.getActionCommand().equals("Exit")) {

            this.dispose();
        }
    }

    @Override
    public Component add(Component cmpnt) {
        return super.add(cmpnt);
    }

    public int checkYear() {
        int rok = 0;
        try {
            rok = Integer.parseInt(fieldYear.getText());
            return rok;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Wrong year!");
            return 0;
        }
    }

}
