package pl.edu.ur.lib.library;

import pl.edu.ur.lib.dataModel.Book;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Window to delete book.
 *
 * @author Marcin
 */

public class WindowDeleteBook extends JDialog implements ActionListener {

    JComboBox comboName = new JComboBox();
    pl.edu.ur.lib.DBconnection.Conn conn = new pl.edu.ur.lib.DBconnection.Conn();
    JButton buttonDelete2 = new JButton("Delete");
    JButton buttonReturn = new JButton("Return");
    JFrame windowDelete = new JFrame("Delete");

    public WindowDeleteBook(JFrame main) {
        super(main, "Delete Book", true);

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

        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(420, 90);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        java.util.List<Book> booksItems = new LinkedList<Book>();
        booksItems = conn.loadBooks();
        for (int i = 0; i < booksItems.size(); i++) {
            comboName.addItem(booksItems.get(i).getTitle());
        }

        JPanel panelMain = new JPanel();
        this.add(panelMain);
        panelMain.add(comboName);
        panelMain.add(buttonDelete2);
        panelMain.add(buttonReturn);

        buttonReturn.addActionListener(this);
        buttonDelete2.addActionListener(this);
        comboName.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        
        if (ae.getActionCommand().equals("Delete")) {

            
            try {
                
                java.util.List<Book> booksItems2 = new LinkedList<Book>();
                booksItems2 = conn.loadBooks();
                int index = comboName.getSelectedIndex();
                conn.deleteBondFromBondsTitle(booksItems2.get(index).getID());
                conn.deleteBook(booksItems2.get(index).getID());
                
                JOptionPane.showMessageDialog(null, "Delete complete.");
                comboName.removeAllItems();

                java.util.List<Book> booksItems = new LinkedList<Book>();
                booksItems = conn.loadBooks();
                for (int i = 0; i < booksItems.size(); i++) {
                    comboName.addItem(booksItems.get(i).getTitle());
                }

                WindowMain.buttonRefresh.doClick();

                this.revalidate();
                this.repaint();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Delete problem!");
            }
        } else if (ae.getActionCommand().equals("Return")) {
            this.dispose();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JDialog d = new WindowDeleteChooser(windowDelete);
                }
            });
        }

    }
}
