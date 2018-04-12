package pl.edu.ur.lib.library;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class of window where we can see information about author.
 *
 * @author Marcin
 */
public class WindowInformator extends JDialog implements ActionListener {

    JButton buttonOk = new JButton("OK");

    public WindowInformator(JFrame main) {
        super(main, "Information", true);
        this.setLayout(new GridLayout(2,1));
        this.setSize(215, 100);
        this.setLocationRelativeTo(null);
        //this.setTitle("Edit Menu");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        
        JPanel panelInfo = new JPanel();
        JPanel panelButton = new JPanel();

        JLabel labelInfo = new JLabel("Writted by Marcin Chyła. IT 2nd year.");
        
        this.add(panelInfo);
        
        
        panelInfo.add(labelInfo);
        panelInfo.add(panelButton);
        panelButton.add(buttonOk);
        this.add(panelButton);
        buttonOk.addActionListener(this);
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("OK")){
            this.dispose();
        }
    }

}
