package computersimulator;

public class RAM{
    private CellRAM[] cells;
    private int size;
    
    public RAM(int size){
        this.size=size;
        cells=new CellRAM[size];
        for(int i=0;i<size;i++){
            cells[i]=new CellRAM(Command.END, 0);
        }
    }
    public RAM(RAM r){
        size=r.getSize();
        cells=new CellRAM[size];
        for(int i=0;i<size;i++){
            cells[i]=r.getCell(i);
        }
    }
    public CellRAM getCell(int adress){
        if (adress<0 || adress>=size){
            throw new OutOfRAMException();
        }
        return cells[adress];
    }
    public void changeCell(int adress, CellRAM cell){
        if (adress<0 || adress>=size){
            throw new OutOfRAMException();
        }
        cells[adress]=cell;
    }
    public int getSize(){
        return size;
    }
}

