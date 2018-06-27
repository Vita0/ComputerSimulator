package computersimulator;

import java.util.ArrayList;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.Scanner;

/**
 * Загружает строки из файла
 *
 * @author Филиппов Виталий 23501/4
 */
public class Loader {

    public List<String> getVectStr(String s) {
        List<String> v;
        Scanner sc;
        v = new ArrayList<>();
        try {
            sc = new Scanner(new BufferedReader(new FileReader(s)));
            while (sc.hasNextLine()) {
                v.add(sc.nextLine());
            }
        } catch (java.io.FileNotFoundException ex) {
            System.err.println(ex.toString());
        }


        return v;
    }

    public String getString(String s) {
        Scanner sc;
        String ss = "";
        try {
            sc = new Scanner(new BufferedReader(new FileReader(s)));
            while (sc.hasNextLine()) {
                ss = ss + sc.nextLine() + '\n';
            }
            ss = ss.substring(0, ss.length() - 1);
        } catch (java.io.FileNotFoundException ex) {
            System.err.println(ex.toString());
        }
        return ss;
    }
}
