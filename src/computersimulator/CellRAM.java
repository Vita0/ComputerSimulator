package computersimulator;

public class CellRAM {
    private Command command;
    private int value;
    public CellRAM(Command c, int v){
        command=c;
        value=v;
    }
    public Command getCommand(){
        return command;
    }
    public int getValue(){
        return value;
    }
//    public String getString(){
//        return command.getString(command);
//    }
    public void setValue(int value){
        this.value=value;
    }
}

