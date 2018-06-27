package GI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author BadBoys
 */
public class Screen extends JPanel {
    JTextArea textArea;
    JScrollPane jsp;
    JLabel name;
    JButton buttonClear;
    Screen(){
        this.setBackground(new Color(255,153,0,255));
        this.setBounds(250,350,500,160);
        name = new JLabel("Вывод",JLabel.CENTER);
        name.setForeground(Color.BLACK);
        this.add(name);    
        buttonClear = new JButton("Отчистка");
        buttonClear.setPreferredSize(new Dimension(90,15));
        buttonClear.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
        this.add(buttonClear);
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEnabled(false);
        textArea.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,14));
        jsp = new JScrollPane(textArea);
        jsp.setPreferredSize(new Dimension(480, 125));
        this.add(jsp);
    }
    void addOut(String s){
        textArea.setText(textArea.getText()+'\n'+s);
        if (textArea.getText().charAt(0)=='\n'){
            textArea.setText(textArea.getText().substring(1));
        }
    }
}
