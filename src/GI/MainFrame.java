package GI;

import computersimulator.CPU.CPU;
import computersimulator.Loader;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author BadBoys
 */
public class MainFrame extends JFrame {
    //текущий файл
    File currentFile;
    //GI
    JPanel mainPanel;
    //RAM
    RamJPanel ramPanel;
    //Text Editor
    TextEditorPanel textPanel;
    //CPU
    CpuJPanel cpuPanel;
    //Run
    JButton startButton, stepButton, runButton;
    //Out
    Screen scr;
    //process
    private boolean end;
    
    //Listeners
    ActionListener loadRamFromTextPanel, start, step, run;
    ActionListener openFile, saveFile;
    //Menu
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openMenu, saveMenu, quitMenu;
    private ActionListener quitListener;
    
    Timer timer;
    JSlider speedSlider;
    int period;
    
    //Храним информацию о последней загрузки ОЗУ
    List<String> code;
    int sizeOfCode;
    
    public MainFrame(){
        super("Симулятор компьютера v.0.9");
        currentFile = null;
        setMinimumSize(new Dimension(1000,580));
        this.setLayout(null);
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(204,255,204,255));
        mainPanel.setBounds(0, 0, 1000, 580);
        this.add(mainPanel);
        
        textPanel = new TextEditorPanel();
        
        textPanel.textArea.setCaretPosition(1);
        ramPanel = new RamJPanel();
        mainPanel.setBounds(0,0,getSize().width,getSize().height);
        mainPanel.setLayout(null);
        mainPanel.add(ramPanel);
        mainPanel.add(textPanel);
        
        cpuPanel = new CpuJPanel();
        mainPanel.add(cpuPanel);
        
        runButton = new JButton("Выполнить");
        runButton.setBounds(467, 30, 90, 30);
        runButton.setEnabled(false);
        mainPanel.add(runButton);
        
        stepButton = new JButton("Шаг");
        stepButton.setBounds(575, 30, 70, 30);
        stepButton.setEnabled(false);
        mainPanel.add(stepButton);
        
        startButton = new JButton("Старт");
        startButton.setBounds(380, 30, 70, 30);
        startButton.setEnabled(false);
        mainPanel.add(startButton);
        
        scr = new Screen();
        mainPanel.add(scr);
        
        initListeners();
        
        textPanel.buttonLoadRam.addActionListener(loadRamFromTextPanel);
        startButton.addActionListener(start);
        stepButton.addActionListener(step);
        runButton.addActionListener(run);
        
        textPanel.buttonOpenFile.addActionListener(openFile);
        textPanel.buttonSaveFile.addActionListener(saveFile);
        
        initMenuBar();
        
        this.setVisible(true);
        this.setResizable(false);
        this.setLocation(50, 50);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        end = true;
        this.textPanel.end=end;
        
        speedSlider = new JSlider();
        speedSlider.setBounds(467, 5, 90, 20);
        speedSlider.setMinimum(1);
        speedSlider.setMaximum(951);
        speedSlider.setToolTipText("Скорость выполнения программы");
        mainPanel.add(speedSlider);
        
        speedSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                period = 1001-speedSlider.getValue();
                timer = new Timer(period,step);
            }
        });
        speedSlider.setValue(501);
    }
    
    private void initMenuBar() {
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        openMenu = new JMenuItem("Открыть");
        openMenu.addActionListener(openFile);
        fileMenu.add(openMenu);
        saveMenu = new JMenuItem("Сохранить");
        saveMenu.addActionListener(saveFile);
        fileMenu.add(saveMenu);
        fileMenu.addSeparator();
        quitMenu = new JMenuItem("Выйти");
        quitMenu.addActionListener(quitListener);
        fileMenu.add(quitMenu);
    }
    
    private void initListeners() {
        loadRamFromTextPanel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ramPanel.clear();
                code = textPanel.get();
                sizeOfCode = (int) textPanel.sizeRamSpinner.getValue();
                if (!ramPanel.loadRAM(textPanel.get(),sizeOfCode)){
                    scr.addOut(ramPanel.translator.getOut());
                } else if (ramPanel.ram.getSize()>=1){
                    startButton.setEnabled(true);
                    cpuPanel.regA.setText("0");
                    cpuPanel.regB.setText("0");
                }
            }
        };
        quitListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
            
        };
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                onClose();
            }
        });
        start = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onStart();
            }
        };
        step = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onStep();
            }
        };
        run = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRun();
            }
        };
        openFile = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                onOpen();
            }
        };
        saveFile = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                onSave();
            }
        };
    }
    private void onStart(){
        if (end){
            scr.addOut("Старт");
            startButton.setText("Стоп");
            cpuPanel.cpu = new CPU(ramPanel.ram);
            //ramPanel.loadRAM(code, sizeOfCode);
            stepButton.setEnabled(true);
            runButton.setEnabled(true);
            cpuPanel.regA.setText("0");
            cpuPanel.regB.setText("0");
            textPanel.buttonLoadRam.setEnabled(false);
            end = false;
        } else {
            scr.addOut("Стоп");
            startButton.setText("Старт");
            //cpuPanel.cpu = new CPU(ramPanel.ram);
            ramPanel.loadRAM(code, sizeOfCode);
            stepButton.setEnabled(false);
            runButton.setEnabled(false);
            cpuPanel.regA.setText("0");
            cpuPanel.regB.setText("0");
            cpuPanel.commandField.setText("");
            cpuPanel.nextCommandField.setText("");
            textPanel.buttonLoadRam.setEnabled(true);
            end = true;
        }
        ramPanel.updateLabels();
        ramPanel.updateUI();
        this.textPanel.end=end;
    }
    
    private void onStep(){
        ramPanel.ramLabel.get(cpuPanel.cpu.getCounter()).setBorder(BorderFactory.createLineBorder(Color.RED,2));
        end=!cpuPanel.cpu.step();
        this.textPanel.end=end;
        if (end){
            stepButton.setEnabled(false);
            startButton.setText("Старт");
            textPanel.buttonLoadRam.setEnabled(true);
            scr.addOut(cpuPanel.cpu.getStatusOfEnd());
            if (timer.isRunning()){
                end=false;
                onRun();
            } else {
                end=false;
                onStart();
            }
        } else {
            if (cpuPanel.cpu.getCounter()>=0){
                ramPanel.ramLabel.get(cpuPanel.cpu.getCounter()).setBorder(BorderFactory.createLineBorder(Color.ORANGE,2));
                ramPanel.repaint();
                cpuPanel.regA.setText(Integer.toString(cpuPanel.cpu.getRegA()));
                cpuPanel.regB.setText(Integer.toString(cpuPanel.cpu.getRegB()));
                cpuPanel.commandField.setText(Integer.toString(cpuPanel.cpu.getCounter()));
                cpuPanel.nextCommandField.setText(Integer.toString(cpuPanel.cpu.getNextCounter()));
                if (!"".equals(cpuPanel.cpu.getOut())){
                    scr.addOut(cpuPanel.cpu.getOut());
                }
            }
        }
        ramPanel.updateLabels();
    }
    
    private void onRun(){
        if (!timer.isRunning()) {
            stepButton.setEnabled(false);
            startButton.setEnabled(false);
            speedSlider.setEnabled(false);
            timer.start();
            runButton.setText("Стоп");
            speedSlider.setEnabled(false);
        } else {
            timer.stop();
            speedSlider.setEnabled(true);
            stepButton.setEnabled(true);
            startButton.setEnabled(true);
            runButton.setText("Выполнить");
            onStart();
            end = true;
        }
    }
    private void onOpen(){
        JFileChooser fileChooser = new JFileChooser(currentFile);
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setMultiSelectionEnabled(true);

        int result = fileChooser.showOpenDialog(this);
        if (result==JFileChooser.APPROVE_OPTION) {

            currentFile = fileChooser.getSelectedFile();
            Loader loader = new Loader();
            textPanel.textArea.setText(loader.getString(currentFile.getAbsolutePath()));
            repaint();
        }
    }
    private void onSave(){
        JFileChooser fileChooser = new JFileChooser(currentFile);
        fileChooser.setCurrentDirectory(new File("."));
        int result = fileChooser.showSaveDialog(this);
        if (result==JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            currentFile = new File(currentFile.getPath());
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(currentFile)));
                out.print(this.textPanel.textArea.getText());
                out.flush();
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    private void onClose() {
        String[] vars = {"Да", "Нет"};
        int result = JOptionPane.showOptionDialog(this, "Действительно выйти?",
                "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, vars, "Да");
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}