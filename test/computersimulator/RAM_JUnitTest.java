package computersimulator;

import org.junit.Test;
import static org.junit.Assert.*;

public class RAM_JUnitTest {
    
    RAM ram;
    
    public RAM_JUnitTest() {
        ram=new RAM(10);
    }
    
    @Test
    public void test0() {
    CellRAM c = new CellRAM(Command.END,0);
    assertEquals(ram.getCell(0).getCommand(), c.getCommand());
    assertEquals(ram.getCell(0).getValue(), c.getValue());
    assertEquals(ram.getSize(),10);
    c = new CellRAM(Command.DIV_A_B, 8);
    ram.changeCell(0, c);
    assertEquals(ram.getCell(0).getCommand(), c.getCommand());
    assertEquals(ram.getCell(0).getValue(), c.getValue());
    RAM r = new RAM(ram);
    assertEquals(r.getCell(0).getCommand(), c.getCommand());
    assertEquals(r.getCell(0).getValue(), c.getValue());
    }
    
    @Test
    public void test1(){
        ram.getCell(0);
        ram.getCell(9);
    }
    
    @Test(expected = OutOfRAMException.class)
    public void test2(){
        ram.getCell(-1);
    }
    
    @Test(expected = OutOfRAMException.class)
    public void test3(){
        ram.getCell(10);
    }
    
}