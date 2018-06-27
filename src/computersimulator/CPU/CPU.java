package computersimulator.CPU;

import computersimulator.*;

/**
* Процессор содержит, счётчик и значения регистров A и B.
* Привязан к задаваемой ОЗУ.
* Блок Управления (БУ) содержится в методе control.
* Операции БУ выполняет при помощи АЛУ.
* @author Филиппов Виталий 23501/4
*/
public class CPU {
    private int regA, regB;
    private int counter;              
    private int nextCell;
    private RAM ram;
    private String statusOfEnd;
    private String out;
    public CPU(RAM r){
        ram=r;
        regA=0;
        regB=0;
        counter=-1;
        nextCell=0;
        statusOfEnd="Программа выполняется...";
        out="";
    }
    public int getRegA(){
        return regA;
    }
    public int getRegB(){
        return regB;
    }
    public int getCounter(){
        if (counter==-1){
            return 0;
        } else {
            return counter;
        }
    }
    public int getNextCounter(){
        return nextCell;
    }
    public String getStatusOfEnd(){
        return statusOfEnd;
    }
    public String getOut(){
        return out;
    }
    public boolean step(){
        counter=nextCell;
        if (isOutOfSpaceRAM()){
            return false;
        }
        if (isEnd()){
            return false;
        }
        control();
        return true;
        
    }
    private boolean isEnd(){
        if (Command.END==ram.getCell(counter).getCommand()){
            statusOfEnd="Программа завершена УСПЕШНО.";
        }
        return (Command.END==ram.getCell(counter).getCommand());
    }
    private boolean isOutOfSpaceRAM(){
        if (counter>=ram.getSize() || counter<0){
            statusOfEnd="Программа завершена с ОШИБКОЙ: счётчик вышел за пределы.";
            return true;
        }
        Command cc = ram.getCell(counter).getCommand();
        if ( (cc==Command.READ_A_NUM || cc==Command.READ_B_NUM || cc==Command.WRITE_A_NUM)
                && (ram.getCell(counter).getValue()>=ram.getSize() || ram.getCell(counter).getValue()<0 )){
            statusOfEnd="Программа завершена с ОШИБКОЙ: попытка записать в ячейку №"+ram.getCell(counter).getValue();
            return true;
        }
        return false;
    }
    private void control(){
        out="";
        CellRAM c;
        c=ram.getCell(counter);
        switch (Command.getString(c.getCommand())) {
            case "ReadA":
                regA=c.getValue();
                break;
            case "ReadB":
                regB=c.getValue();
                break;
            case "WriteANum":
                ram.changeCell(c.getValue(), new CellRAM(Command.DATA,regA));
                break;
            case "SumAB":
                regA=ALU.sum(regA, regB);
                break;
            case "InvB":
                regB=ALU.inv(regB);
                break;
            case "MulAB":
                regA=ALU.mul(regA, regB);
                break;
            case "DivAB":
                if (regB!=0)
                    regA=ALU.div(regA, regB);
                else
                    out="Ошибка: деление на ноль. команда была пропущена";
                break;
            case "ReadANum":
                regA=ram.getCell(c.getValue()).getValue();
                break;
            case "ReadBNum":
                regB=ram.getCell(c.getValue()).getValue();
                break;
            case "outA":
                out="RegA = "+regA;
                break;
            case "outB":
                out="RegB = "+regB;
                break;
            case "JumpA0toN":
                if (ALU.isAPossitive(regA)){
                    nextCell+=c.getValue()-1;
                }
                break;
            case "end":
                nextCell--;
                break;
        }
        nextCell++;
    }
}