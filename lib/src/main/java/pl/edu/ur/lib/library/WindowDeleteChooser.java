package pl.edu.ur.lib.library;

import pl.edu.ur.lib.dataModel.Author;
import pl.edu.ur.lib.dataModel.Book;
import pl.edu.ur.lib.dataModel.Data;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Window to choose what we want to delete.
 * @author Marcin
 */
public class WindowDeleteChooser extends JDialog implements ActionListener {

    pl.edu.ur.lib.DBconnection.Conn conn = new pl.edu.ur.lib.DBconnection.Conn();
    JFrame windowMain;

    JButton buttonDeleteAuthor = new JButton("Delete author");
    JButton buttonDeleteBook = new JButton("Delete book");
    JButton buttonExit = new JButton("Exit");
    Author author;
    Book book;
    Data data;

    public WindowDeleteChooser(JFrame main) {
        super(main, "Delete choose", true);
        conn.openConnection();
        this.setSize(300, 80);
        this.setLocationRelativeTo(null);
        this.setTitle("Delete Menu");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);

        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel panelButton = new JPanel();

        this.add(panelButton);

        panelButton.add(buttonDeleteAuthor);
        panelButton.add(buttonDeleteBook);
        panelButton.add(buttonExit);

        buttonDeleteAuthor.addActionListener(this);
        buttonDeleteBook.addActionListener(this);
        buttonExit.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Delete author")) {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JDialog d = new WindowDeleteAuthor(windowMain);

                }
            });

            this.dispose();
        } else if (ae.getActionCommand().equals("Delete book")) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JDialog d = new WindowDeleteBook(windowMain);

                }
            });
            this.dispose();
        } else if (ae.getActionCommand().equals("Exit")) {
            this.dispose();
        }

    }
}
