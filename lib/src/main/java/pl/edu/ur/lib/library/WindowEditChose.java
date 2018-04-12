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
 * Class of window where we can choose what information from database we need to
 * edit.
 *
 * @author Marcin
 */
public class WindowEditChose extends JDialog implements ActionListener {

    pl.edu.ur.lib.DBconnection.Conn conn = new pl.edu.ur.lib.DBconnection.Conn();
    JFrame windowMain;
    JButton buttonEditAll = new JButton("Edit all");
    JButton buttonEditAuthor = new JButton("Edit author");
    JButton buttonEditBook = new JButton("Edit book");
    JButton buttonExit = new JButton("Exit");
    Author author;
    Book book;
    Data data;

    public WindowEditChose(JFrame main, Data a) {
        super(main, "Edit chooser", true);
        conn.openConnection();
        this.setSize(350, 80);
        this.setLocationRelativeTo(null);
        this.setTitle("Edit Menu");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);

        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        author = a.getAuthor();
        book = a.getBook();
        data = a;

        JPanel panelButton = new JPanel();

        this.add(panelButton);

        panelButton.add(buttonEditAll);
        panelButton.add(buttonEditAuthor);
        panelButton.add(buttonEditBook);
        panelButton.add(buttonExit);

        buttonEditAll.addActionListener(this);;
        buttonEditAuthor.addActionListener(this);
        buttonEditBook.addActionListener(this);
        buttonExit.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Edit all")) {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JDialog d = new WindowEditAll(windowMain, data);

                    d.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                }
            });
            this.dispose();
        } else if (ae.getActionCommand().equals("Edit author")) {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JDialog d = new WindowEditAuthor(windowMain, author);

                }
            });

            this.dispose();
        } else if (ae.getActionCommand().equals("Edit book")) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JDialog d = new WindowEditBook(windowMain, data);

                }
            });
            this.dispose();
        } else if (ae.getActionCommand().equals("Exit")) {
            this.dispose();
        }
    }
}
