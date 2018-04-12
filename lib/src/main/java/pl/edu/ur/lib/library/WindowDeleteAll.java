package pl.edu.ur.lib.library;

/*package liblary2;

import dataModel.Author;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Marcin
 *
public class WindowDeleteAll extends JDialog implements ActionListener {

    JComboBox comboName = new JComboBox();
    DBconnection.Conn conn = new DBconnection.Conn();
    JButton buttonDelete = new JButton("Delete");
    JFrame windowDelete = new JFrame("Delete");

    
    public WindowDeleteAll (JFrame main) {
        super(main, "Delete All", true);
        conn.openConnection();
//        windowDelete.setLayout(new FlowLayout(FlowLayout.LEFT));
//        windowDelete.setLocationRelativeTo(null);
//        windowDelete.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        windowDelete.setSize(new Dimension(300, 100));
//        windowDelete.setResizable(false);

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(300, 100));
        this.setResizable(false);

        java.util.List<Author> authorsItems = new LinkedList<Author>();
        authorsItems = conn.loadAuthor();
        for (int i = 0; i < authorsItems.size(); i++) {
            comboName.addItem(authorsItems.get(i).getLastName());
            String author = (String) comboName.getSelectedItem();
        }

        JPanel panelMain = new JPanel();
        //windowDelete.add(panelMain);
        this.add(panelMain);
        panelMain.add(comboName);
        panelMain.add(buttonDelete);

        buttonDelete.addActionListener(this);;
        comboName.addActionListener(this);

        //windowDelete.setVisible(true);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Delete")) {

            try {
                conn.deleteBondFromBonds((String) comboName.getSelectedItem());
                conn.deleteAuthorFromAuthors((String) comboName.getSelectedItem());
                

                JOptionPane.showMessageDialog(null, "Delete complete.");
                comboName.removeAllItems();

                java.util.List<Author> authorsItems = new LinkedList<Author>();
                authorsItems = conn.loadAuthor();
                for (int i = 0; i < authorsItems.size(); i++) {
                    comboName.addItem(authorsItems.get(i).getLastName());
                    String author = (String) comboName.getSelectedItem();
                }

//                windowDelete.revalidate();
//                windowDelete.repaint();
                this.revalidate();
                this.repaint();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Delete problem!");
            }
        }
    }

}
*/