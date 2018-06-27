package GI;

import computersimulator.Translator;
import computersimulator.RAM;
import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author BadBoys
 */
public class TextEditorPanel extends JPanel {

    JTextArea textArea;
    JScrollPane jsp;
    JSpinner sizeRamSpinner;
    SpinnerModel smodel;
    JLabel name, checkCodeLabel;
    JButton buttonLoadRam, buttonOpenFile, buttonSaveFile;
    AbstractDocument doc;
    ImageIcon goodIcon, badIcon;
    boolean end;

    public TextEditorPanel() {
        super();
        String ini = "ReadA 5\n"
                + "ReadB 1\n"
                + "SumAB 0\n"
                + "outA 0\n"
                + "JumpA0toN 5\n"
                + "SumAB 0\n"
                + "WriteA y\n"
                + "ReadA 1\n"
                + "JumpA0toN 4\n"
                + "ReadB 6\n"
                + "SumAB 0\n"
                + "WriteA z\n"
                + "end 0";

        this.setBackground(new Color(166, 100, 255, 255));
        this.setBounds(780, 10, 200, 500);
        this.setLayout(null);
        textArea = new JTextArea();
        textArea.setToolTipText("Команда записываются 'команда'+'пробел'+'целое число'. \nРазделяются команды переносом строки");
        textArea.setText(ini);
        textArea.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,14));

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setLayout(null);
        jsp = new JScrollPane(textArea);
        jsp.setBounds(10, 90, 180, 400);
        this.add(jsp);
        name = new JLabel("Редактор команд", JLabel.CENTER);
        name.setBounds(40, 5, 120, 20);
        name.setForeground(Color.WHITE);
        this.add(name);

        buttonLoadRam = new JButton("в ОЗУ");
        buttonLoadRam.setBounds(10, 30, 70, 20);
        this.add(buttonLoadRam);

        buttonOpenFile = new JButton("Открыть");
        buttonOpenFile.setBounds(10, 60, 85, 20);
        this.add(buttonOpenFile);

        buttonSaveFile = new JButton("Сохранить");
        buttonSaveFile.setBounds(100, 60, 90, 20);
        buttonSaveFile.getVisibleRect();
        this.add(buttonSaveFile);

        sizeRamSpinner = new JSpinner();
        sizeRamSpinner.setToolTipText("Количество ячеек в ОЗУ");
        sizeRamSpinner.setBounds(90, 30, 40, 20);
        smodel = new SpinnerNumberModel(1, 1, 1000, 1);
        sizeRamSpinner.setModel(smodel);
        this.add(sizeRamSpinner);

        checkCodeLabel = new JLabel();
        checkCodeLabel.setLayout(null);
        checkCodeLabel.setBounds(140, 30, 20, 20);
        this.add(checkCodeLabel);
        goodIcon = new ImageIcon("good.png");

        badIcon = new ImageIcon("bad.png");

        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                int a = textArea.getLineCount();
                int b = (int) sizeRamSpinner.getValue();
                smodel = new SpinnerNumberModel((a > b) ? a : b, a, 1000, 1);
                sizeRamSpinner.setModel(smodel);
                a = isCodForComputerSistem();
                if (a == 0) {
                    if (end) 
                        buttonLoadRam.setEnabled(true);
                    checkCodeLabel.setIcon(goodIcon);
                    checkCodeLabel.setToolTipText("Ваш код, соответствует правилам Симулятора");
                    checkCodeLabel.repaint();
                } else {
                    buttonLoadRam.setEnabled(false);
                    checkCodeLabel.setIcon(badIcon);
                    checkCodeLabel.setToolTipText("Ошибка на строке № " + String.valueOf(a));
                    repaint();
                    SimpleAttributeSet atrset = new SimpleAttributeSet();
                    StyleConstants.setForeground(atrset, Color.RED);
                    //textArea.getDocument().insertString(textArea.getText().length(), TOOL_TIP_TEXT_KEY, null);
                }
            }
        });
    }

    public List<String> get() {
        List<String> v = new ArrayList<>();
        Scanner sc = new Scanner(textArea.getText()).useDelimiter("\n");
        while (sc.hasNextLine()) {
            v.add(sc.nextLine());
        }
        return v;
    }

    private int isCodForComputerSistem() {
        List<String> v;
        v = get();
        int j = 0;
        for (String i : v) {
            j++;
            if (i.indexOf(" ") < 1) {
                return j;
            }
            String s = i.substring(0, i.indexOf(" "));
            Translator intepr = new Translator(new RAM(0));
            if (!intepr.isCommand(s)) {
                return j;
            }
            i = i.substring(i.indexOf(" "));
            if (i.length() <= 1) {
                return j;
            }
            i = i.substring(1);
            char c;
            char h = i.charAt(0);
            if (('0' <= i.charAt(0) && i.charAt(0) <= '9')
                    || i.charAt(0) == '-' || i.charAt(0) == '+') {
                c = '0';
            } else if (('a' <= i.charAt(0) && i.charAt(0) <= 'z') || ('A' <= i.charAt(0) && i.charAt(0) <= 'Z')) {
                c = 'a';
            } else {
                return j;
            }
            if (c == 'a') {
                for (int k = 1; k < i.length(); k++) {
                    if (!(('0' <= i.charAt(k) && i.charAt(k) <= '9')
                            || ('a' <= i.charAt(k) && i.charAt(k) <= 'z')
                            || ('A' <= i.charAt(k) && i.charAt(k) <= 'Z'))) {
                        return j;
                    }
                }
            } else {
                if (i.length()==1 && i.charAt(0)=='-') return j;
                for (int k = 1; k < i.length(); k++) {
                    if (!('0' <= i.charAt(k) && i.charAt(k) <= '9')) {
                        return j;
                    }
                }
            }
        }
        return 0;
    }
}
