/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computersimulator;

import GI.MainFrame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author BadBoys
 */
public class ComputerSimulator {
        public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (        ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(ComputerSimulator.class.getName()).log(Level.SEVERE, null, ex);
                }
                MainFrame mainFrame = new MainFrame();
            }
        });
    }
}
