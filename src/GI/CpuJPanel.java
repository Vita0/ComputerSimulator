/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GI;

import computersimulator.CPU.CPU;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author BadBoys
 */
public class CpuJPanel extends JPanel {
    CPU cpu;
    private JLabel name;
    private JLabel regAlabel,regBlabel;
    private JLabel commandLabel, nextCommandLabel;
    JTextField commandField, nextCommandField;
    JTextField regA,regB;
    CpuJPanel(){
        super();
        this.setLayout(null);
        this.setBackground(Color.YELLOW);
        this.setBounds(380,80,265,240);
        name = new JLabel("Процессор");
        name.setBounds(105, 5, 90, 20);
        name.setForeground(Color.BLUE);
        add(name);
        regAlabel = new JLabel("A = ");
        regAlabel.setBounds(10, 50, 30, 30);
        regAlabel.setForeground(Color.BLACK);
        add(regAlabel);
        regBlabel = new JLabel("B = ");
        regBlabel.setBounds(140, 50, 30, 30);
        regBlabel.setForeground(Color.BLACK);
        add(regBlabel);
        commandLabel = new JLabel("Текущая команда");
        commandLabel.setBounds(10, 80, 140, 30);
        commandLabel.setForeground(Color.BLACK);
        add(commandLabel);
        nextCommandLabel = new JLabel("Следующая команда");
        nextCommandLabel.setBounds(10, 110, 140, 30);
        nextCommandLabel.setForeground(Color.BLACK);
        add(nextCommandLabel);
        regA = new JTextField();
        regA.setBounds(45, 50, 80, 30);
        regA.setEditable(false);
        add(regA);
        regB = new JTextField();
        regB.setBounds(175, 50, 80, 30);
        regB.setEditable(false);
        add(regB);
        commandField = new JTextField();
        commandField.setBounds(155, 85, 100, 20);
        commandField.setEditable(false);
        add(commandField);
        nextCommandField = new JTextField();
        nextCommandField.setBounds(155, 115, 100, 20);
        nextCommandField.setEditable(false);
        add(nextCommandField);
    }
}
