package pl.edu.ur.lib.library;

import pl.edu.ur.lib.xml.ReadXMLFile;
import pl.edu.ur.lib.DBconnection.Conn;
import pl.edu.ur.lib.dataModel.Author;
import pl.edu.ur.lib.dataModel.Book;
import pl.edu.ur.lib.dataModel.Data;
import pl.edu.ur.lib.dataModel.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class with main window of program.
 *
 * @author Marcin
 */
public class WindowMain implements ActionListener {

    pl.edu.ur.lib.DBconnection.Conn conn = new pl.edu.ur.lib.DBconnection.Conn();
    Statement statement;
    JFrame windowMain;
    JTable table;
    JButton buttonAdd = new JButton("Add");
    JButton buttonEdit = new JButton("Edit");
    JButton buttonDelete = new JButton("Delete");
    JButton buttonSort = new JButton("Filtr");
    static JButton buttonRefresh;
    JComboBox comboName = new JComboBox();

    public WindowMain() {
        
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

        buttonRefresh = new JButton("Refresh");

        conn.openConnection();
        windowMain = new JFrame();
        windowMain.setIconImage(new ImageIcon(".\\program.png").getImage());
        windowMain.setTitle("Ultimate Library Program 2.2.6");
        windowMain.setLayout(new FlowLayout(FlowLayout.LEFT));
        windowMain.setSize(505, 320);
        windowMain.setLocationRelativeTo(null);
        windowMain.setDefaultCloseOperation(EXIT_ON_CLOSE);
        windowMain.setResizable(true);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenu menuHelp = new JMenu("Help");
        JMenu menuImport = new JMenu("Import");
        JMenuItem menuItemImportXML = new JMenuItem("From XML");
        JMenuItem menuItemFile = new JMenuItem("Exit");
        JMenuItem menuItemHelpPDF = new JMenuItem("How to use");
        JMenuItem menuItemHelpOnline = new JMenuItem("Help online");
        JMenuItem menuItemHelp = new JMenuItem("About");

        JPanel panelMain = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelOne = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelTwo = new JPanel(new BorderLayout());

        windowMain.setJMenuBar(menuBar);
        windowMain.add(panelOne, BorderLayout.NORTH);
        menuBar.add(menuFile);
        menuBar.add(menuImport);
        menuBar.add(menuHelp);

        menuFile.add(menuItemFile);
        menuHelp.add(menuItemHelpPDF);
        menuHelp.add(menuItemHelpOnline);
        menuHelp.add(menuItemHelp);

        menuImport.add(menuItemImportXML);

        table = new JTable(new TableModel(conn.loadAllData()));
        java.util.List<Author> authorsItems = new LinkedList<Author>();
        authorsItems = conn.loadAuthor();
        for (int i = 0; i < authorsItems.size(); i++) {
            comboName.addItem(authorsItems.get(i).getNameAndLastName());
            String author = (String) comboName.getSelectedItem();
        }

        windowMain.add(panelMain);

        //windowMain.add(table);
        windowMain.add(panelTwo);
        panelTwo.setLayout(null);
        panelTwo.add(table);
        JScrollPane scroll = new JScrollPane(table);
        windowMain.add(scroll);
        windowMain.setResizable(false);
        scroll.setPreferredSize(new Dimension(480, 200));
        table.setAutoscrolls(true);
        //table.setAutoCreateRowSorter(true);

        panelMain.add(buttonAdd);
        panelMain.add(buttonDelete);
        panelMain.add(buttonEdit);
        panelMain.add(buttonSort);
        panelMain.add(comboName);
        panelMain.add(buttonRefresh);

        buttonSort.addActionListener(this);
        comboName.addActionListener(this);
        buttonAdd.addActionListener(this);
        buttonEdit.addActionListener(this);
        buttonDelete.addActionListener(this);
        buttonRefresh.addActionListener(this);
        menuItemFile.addActionListener(this);
        menuItemHelp.addActionListener(this);
        menuItemImportXML.addActionListener(this);
        menuItemHelpPDF.addActionListener(this);
        menuItemHelpOnline.addActionListener(this);

        windowMain.setVisible(true);
    }

    public static void openWindow(Conn conn) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WindowMain();

            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("Filtr")) {

            StringTokenizer st = new StringTokenizer(((String) comboName.getSelectedItem()));
            String name = st.nextToken();
            String lastName = st.nextToken();

            int id = conn.checkAuthorID(lastName);

            table.setModel(new TableModel(conn.loadAuthorBooks(id)));
            table.repaint();
        } else if (ae.getActionCommand().equals("Add")) {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {

                    JDialog d = new WindowAdd(windowMain);
                    d.setVisible(true);
                }
            });
            java.util.List<Author> authorsItems = new LinkedList<Author>();
            authorsItems = conn.loadAuthor();
            comboName.removeAllItems();

            for (int i = 0; i < authorsItems.size(); i++) {
                comboName.addItem(authorsItems.get(i).getNameAndLastName());
                String author = (String) comboName.getSelectedItem();
            }

        } else if (ae.getActionCommand().equals("Refresh")) {

            windowRefresh();
        } else if (ae.getActionCommand().equals("Edit")) {
            try {

                int indexRow = table.convertRowIndexToModel(table.getSelectedRow());

                String name = "";
                String lastName = "";

                StringTokenizer st = new StringTokenizer((String) table.getModel().getValueAt(indexRow, 1));
                name = st.nextToken();
                lastName = st.nextToken();

                String title = (String) table.getModel().getValueAt(indexRow, 0);
                String publishHouse = (String) table.getModel().getValueAt(indexRow, 2);
                int year = (int) table.getModel().getValueAt(indexRow, 3);
                String desc = (String) table.getModel().getValueAt(indexRow, 4);
                String isbn = (String) table.getModel().getValueAt(indexRow, 5);
                final Author a = new Author(name, lastName);
                final Book b = new Book(isbn, title, publishHouse, year, desc);
                final Data ab = new Data(a, b);

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        JDialog d = new WindowEditChose(windowMain, ab);

                    }
                });

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Proszę zaznaczyć element do edycji.");
            }

            table.revalidate();
            table.repaint();

        } else if (ae.getActionCommand().equals("Delete")) {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JDialog d = new WindowDeleteChooser(windowMain);
                }
            });

        } else if (ae.getActionCommand().equals("About")) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JDialog d = new WindowInformator(windowMain);
                }
            });
        } else if (ae.getActionCommand().equals("Exit")) {
            System.exit(0);
        } else if (ae.getActionCommand().equals("From XML")) {
            String url = "";

            JFileChooser chooser = new JFileChooser("Select XML file.");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files", "xml");
            chooser.setFileFilter(filter);
            chooser.setCurrentDirectory(new File("."));
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int returnValue = chooser.showOpenDialog(windowMain);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                url = selectedFile.getPath();

                ReadXMLFile readXML = new ReadXMLFile();
                readXML.readXMLFile(url);
                int id = conn.returnIdCount();
                for (int i = 0; i < readXML.readXMLFile(url).size(); i++) {
                    Author a = readXML.readXMLFile(url).getData(i).getAuthor();
                    id += 1;
                    a.setID(id);
                    Book b = readXML.readXMLFile(url).getData(i).getBook();
                    Data d = new Data(a, b);

                    conn.addAuthor(a);
                    conn.addBook(b);
                    conn.addBound(d);
                    WindowMain.buttonRefresh.doClick();

                }

            } else if (returnValue == JFileChooser.CANCEL_OPTION) {
            }
        } else if (ae.getActionCommand().equals("How to use")) {
            Process p;
            try {
                p = Runtime.getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler HTU.PDF");
                p.waitFor();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "PDF problem!");//index.html
            }
        } else if (ae.getActionCommand().equals("Help online")) {
            String url = "http://www.library.doprzodu.com";
            URI uri;
            Desktop dt = Desktop.getDesktop();
            try {
                uri = new URI(url);
                dt.browse(uri.resolve(uri));
            } catch (URISyntaxException ex) {
                JOptionPane.showMessageDialog(null, "Internet problem!" + ex.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Internet problem!" + ex.getMessage());
            }

        }

        table.revalidate();
        table.repaint();

    }

    /**
     * Method to refresh main window of program.
     */
    public void windowRefresh() {

        comboName.removeAllItems();

        java.util.List<Author> authorsItems = new LinkedList<Author>();
        authorsItems = conn.loadAuthor();
        for (int i = 0; i < authorsItems.size(); i++) {
            comboName.addItem(authorsItems.get(i).getNameAndLastName());
            String author = (String) comboName.getSelectedItem();
        }
        table.setModel(new TableModel(conn.loadAllData()));
        table.revalidate();
        table.repaint();

    }

    /**
     * Method to refresh combobox with name and lastname of authors in main
     * window.
     */
    public void comboRefres() {
        comboName.removeAllItems();
        java.util.List<Author> authorsItems = new LinkedList<Author>();
        authorsItems = conn.loadAuthor();
        for (int i = 0; i < authorsItems.size(); i++) {
            comboName.addItem(authorsItems.get(i).getNameAndLastName());
            String author = (String) comboName.getSelectedItem();
        }
    }

}
