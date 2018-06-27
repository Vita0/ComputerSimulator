package computersimulator;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum Command {
    END,
    READ_A,//из этой ячейки RAM в CPU, A-int
    READ_B,
    READ_A_NUM,//из адресса RAM в CPU
    READ_B_NUM,
    WRITE_A_NUM,//из CPU в RAM в ячейку по адрессу
    SUM_A_B,
    INV_B,
    MUL_A_B,
    DIV_A_B,
    JUMP_A_0_TO_N,//если А > 0, то прыгаем ЧЕРЕЗ N адрессов
    DATA,
    OUT_A,
    OUT_B;

    static final private Map<Command, String> commandToString = new EnumMap<>(Command.class);
    static final private Map<String, Command> stringToCommand = new HashMap<>();

    static {
        commandToString.put(Command.READ_A, "ReadA");
        commandToString.put(Command.READ_B, "ReadB");
        commandToString.put(Command.WRITE_A_NUM, "WriteANum");
        commandToString.put(Command.READ_A_NUM, "ReadANum");
        commandToString.put(Command.READ_B_NUM, "ReadBNum");
        commandToString.put(Command.SUM_A_B, "SumAB");
        commandToString.put(Command.INV_B, "InvB");
        commandToString.put(Command.MUL_A_B, "MulAB");
        commandToString.put(Command.DIV_A_B, "DivAB");
        commandToString.put(Command.JUMP_A_0_TO_N, "JumpA0toN");
        commandToString.put(Command.DATA, "data");
        commandToString.put(Command.OUT_A, "outA");
        commandToString.put(Command.OUT_B, "outB");
        commandToString.put(Command.END, "end");
        
        stringToCommand.put("ReadA",Command.READ_A);
        stringToCommand.put("ReadB", Command.READ_B);
        stringToCommand.put("WriteANum", Command.WRITE_A_NUM);
        stringToCommand.put("ReadANum", Command.READ_A_NUM);
        stringToCommand.put("ReadBNum", Command.READ_B_NUM);
        stringToCommand.put("SumAB", Command.SUM_A_B);
        stringToCommand.put("InvB", Command.INV_B);
        stringToCommand.put("MulAB", Command.MUL_A_B);
        stringToCommand.put("DivAB", Command.DIV_A_B);
        stringToCommand.put("JumpA0toN", Command.JUMP_A_0_TO_N);
        stringToCommand.put("data", Command.DATA);
        stringToCommand.put("outA", Command.OUT_A);
        stringToCommand.put("outB", Command.OUT_B);
        stringToCommand.put("end", Command.END);
    }

    static public String getString(Command c) {
        return commandToString.get(c);
    }
    
    static public Command getCommand(String s){
        return stringToCommand.get(s);
    }
    
    static public boolean isCommand(String s){
        return stringToCommand.containsKey(s);
    }

    static public int getNumer(Command c) {
        return c.ordinal();
    }
}
//A=5; B=1; A=A+B; if (A>0) {B=-B; A=A+B} else {A=A+B}
//# 0 ReadA 5
//# 1 ReadB 1
//# 2 SumAB 0
//# 3 WriteANum #128
//# 4 JumpA0toN 5
//# 5 SumAB
//# 6 WriteA #128
//# 7 ReadA 1
//# 8 JumpA0toN 4
//# 9 InvB 0
//#10 SumAB
//#11 WriteANum #128
//#12 end