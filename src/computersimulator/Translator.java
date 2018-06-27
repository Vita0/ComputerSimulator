package computersimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.Iterator;
import java.util.List;
/**
* Интерпретирует команды в ячейки ОЗУ.
* Позволяет адресам переменных сопоставлять имя.
* Не изменяет такие же команды в ОЗУ.
* @author Филиппов Виталий 23501/4
*/
public class Translator {
    private List<List<CellRAM>> cells;
    private RAM ram;
    private List<Boolean> isCellsEmpty;//true if cell empty, false else
    private HashMap<String, Integer> nameVarToAdr;
    private int counter;
    private HashMap<Integer, Integer> numCellsWithVars;
    private String out;
    public Translator(RAM r){
        ram = new RAM(r);
        isCellsEmpty= new ArrayList();
        //БЫЛО ПРОСТО ram.getSize() :
        //isCellsEmpty.setSize(ram.getSize()/*+1*/);
        for(int i=0; i<isCellsEmpty.size(); i++){
            isCellsEmpty.set(i,true);
        }
        nameVarToAdr = new HashMap<>();
        cells = new ArrayList<>();
        counter=-1;
        numCellsWithVars = new HashMap<>();
        out="";
//            for(int j=0; j<v.size(); j++){
//                ram.changeCell(counter, v.elementAt(j));
//            }
    }
    public void clear(){
        cells.clear();
        isCellsEmpty.clear();
        ram = new RAM(0);
        nameVarToAdr.clear();
        counter=-1;
        numCellsWithVars.clear();
        out = "";
    }
    
    public String getOut(){
        return out;
    }
    public RAM getRAM(){
        return ram;
    }
    
    private int firstEmptyCell(){
        int i;
        for(i=0;i<isCellsEmpty.size();i++){
            if (isCellsEmpty.get(i)==true){
                return i;
            }
        }
        return i;
    }
    
    private void loadInRAM(){
        int count=-1;
        for(int i=0; i<cells.size(); i++){
            for(int j=0; j<cells.get(i).size(); j++){
                count++;
                ram.changeCell(count, cells.get(i).get(j));
            }
        }
    }
/** Возвращает адресс переменной в памяти по имени. Если такого имени нет возвращает -1*/
    int getAdressVar(String name){
        if (nameVarToAdr.containsKey(name)){
            return nameVarToAdr.get(name);
        }
        return -1;
    }
/** Возвращает имя переменной в памяти по @adress. Если такому адресу не сопоставляется имя возвращает пустую строку*/
    String getNameVar(int adress){
        for(String s : nameVarToAdr.keySet()){
            if (nameVarToAdr.get(s)==adress){
                return s;
            }
        }
        return "";
    }
/** Заполняет ОЗУ командами, заданными в {@link kod}. Интерпретирует команды, так чтобы их понимал Процессор*/
    public void translate(List<String> kod){
        String command;
        Scanner sc;
        for(String s:kod){
            sc = new Scanner(s);
            if (!sc.hasNext()){continue;}
            command=sc.next();
            List<CellRAM> v = new ArrayList<>();
            //составные команды ///////////////////////////////////////////////
            if (false/*command.contains("=")*/) {
                
            } else {
                counter++;
                ///////////////////////////////////////////////////////////////
                //Интерпретируемые команды
                switch (command) {
                    case "ReadAVar":
                        {
                            String nameVar=sc.next();
                            //временный 0
                            v.add(new CellRAM(Command.READ_A_NUM,0));
                            //запомним какой adr должен храниться вместо 0 в ячейке #counter
                            if (nameVarToAdr.containsKey(nameVar)){
                                numCellsWithVars.put(counter, nameVarToAdr.get(nameVar));
                            } else {
                                out+="Неизвестная переменная "+nameVar+'\n';
                            }
                            break;
                        }
                    case "ReadBVar":
                        {
                            String nameVar=sc.next();
                            //временный 0
                            v.add(new CellRAM(Command.READ_B_NUM,0));
                            //запомним какой adr должен храниться вместо 0 в ячейке #counter
                            if (nameVarToAdr.containsKey(nameVar)){
                                    numCellsWithVars.put(counter, nameVarToAdr.get(nameVar));
                                } else {
                                    out+="Неизвестная переменная "+nameVar+'\n';
                                }
                            break;
                        }
                    case "WriteA":
                        {
                            String nameVar=sc.next();
                            int adr;
                            if (nameVarToAdr.containsKey(nameVar)){
                                adr=nameVarToAdr.get(nameVar);
                            } else {
                                adr=firstEmptyCell();
        //                        if (adr>=ram.getSize() || adr<0){
        //                            out+="Выход за предел ОЗУ: WriteA - Нет свободных ячеек"+'\n';
        //                        } else {
                                    isCellsEmpty.add(adr, false);
                                    nameVarToAdr.put(nameVar, adr);
        //                        }
                            }
                            //временный 0
                            v.add(new CellRAM(Command.WRITE_A_NUM,0));
                            //запомним какой adr должен храниться вместо 0 в ячейке #counter
                            numCellsWithVars.put(counter, adr);
                            break;
                        }
                    default: {
                        v.add(new CellRAM(Command.getCommand(command),sc.nextInt()));
                        break;
                    }
                }
                cells.add(v);
            }
        }
        
        //сдвиг адресов для хранения переменных
        int a=0;
        Set<String> set;
        set=nameVarToAdr.keySet();
        Iterator it=set.iterator();
        for (String s:set){
            s=it.next().toString();
            //БЫЛО ТАК
            //a=nameVarToAdr.get(s)+counter+1;
            a=nameVarToAdr.get(s).intValue()+counter+1;
            nameVarToAdr.put(s, a);
        }
        if (a<=ram.getSize()-1)
        {
            //запрещаем записывать новые переменные в занятые ячейки
            for(int i=0; i<a; i++){
                isCellsEmpty.add(i, false);
            }

            //загрузка в ОЗУ преобразованных команд
            loadInRAM();

            //вставляем что должно стоять вместо 0
            Set<Integer> sett;
            sett=numCellsWithVars.keySet();
            for(int i : sett){
                CellRAM cc;
                cc = ram.getCell(i);
                cc.setValue( numCellsWithVars.get(i) +counter+1 );
                ram.changeCell(i,cc);
            }
        }
        else {
            out+="Недостаточно памяти в ОЗУ, увеличьте количество ячеек в ОЗУ";
        }
    }
    public boolean isCommand(String s){
        switch (s) {
            case "WriteA":
                return true;
            case "ReadAVar":
                return true;
            case "ReadBVar":
                return true;
            default: {
                if ( Command.isCommand(s) )
                return true;
            }
        }
        return false;
    }
}