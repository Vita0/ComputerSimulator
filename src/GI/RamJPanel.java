package GI;

import computersimulator.Command;
import computersimulator.Translator;
import computersimulator.RAM;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author BadBoys
 */
public class RamJPanel extends JPanel{
    JLabel ramName;
    List<JLabel> ramLabel;
    JPanel panelRAM;
    JScrollPane jsp;
    RAM ram;
    Translator translator;
    public RamJPanel(){
        super();
        ram = new RAM(0);
        translator = new Translator(ram);
        
        this.setBackground(new Color(0,204,255,255));
        this.setBounds(10,10,215,500);
        ramLabel = new ArrayList();
        
        ramName = new JLabel("ОЗУ",JLabel.CENTER);
        ramName.setBounds(80, 5, 60, 20);
        ramName.setForeground(Color.WHITE);
        ramName.setFont(new Font(Font.DIALOG,Font.BOLD,14));
        this.add(ramName);
        
        panelRAM = new JPanel();
        panelRAM.setLayout(null);
        panelRAM.setBackground(Color.green);
        panelRAM.setPreferredSize(new Dimension(180, 455));
        
        jsp = new JScrollPane(panelRAM);
        jsp.setBackground(new Color(0,204,255,255));
        jsp.setPreferredSize(new Dimension(200, 460));
        jsp.getVerticalScrollBar().setUnitIncrement(20);
        this.add(jsp);
    }
    boolean loadRAM(List<String> s, int size){
        ram = new RAM(size);
        translator.clear();
        translator = new Translator(ram);
        translator.translate(s);
        ram = new RAM(translator.getRAM());
        if (!translator.getOut().equals("")){
            return false;
        }
        int i;
        if (4+25*size+22>panelRAM.getHeight()) {
            panelRAM.setPreferredSize(new Dimension(180, 4+25*size));
        } else
        {
            panelRAM.setPreferredSize(new Dimension(180, 455));
        }
        for (i=0;i<size;i++){
            ramLabel.add(new JLabel(i+" "+Command.getString(ram.getCell(i).getCommand())+" "+ram.getCell(i).getValue(),JLabel.CENTER));
            ramLabel.get(i).setBounds(5, 4+25*i, 170, 20);
            ramLabel.get(i).setBorder(BorderFactory.createLineBorder(Color.RED,2));
            ramLabel.get(i).setToolTipText(String.valueOf(Command.getNumer(ram.getCell(i).getCommand())));
            panelRAM.add(ramLabel.get(i));
        }
        //ВОЛШЕБСТВО!!!
        jsp.getViewport().updateUI();
        return true;
    }
    void clear(){
        for(JLabel jl:ramLabel){
            panelRAM.remove(jl);
        }
        ramLabel.clear();
    }
    void updateLabels(){
        for (int i=0;i<ram.getSize();i++){
            ramLabel.get(i).setText(Integer.toString(i)
                    +' '+Command.getString(ram.getCell(i).getCommand())
                    +' '+ram.getCell(i).getValue());
            ramLabel.get(i).setToolTipText(String.valueOf(Command.getNumer(ram.getCell(i).getCommand())));
        }
    }
}
