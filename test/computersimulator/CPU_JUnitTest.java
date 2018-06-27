package computersimulator;

import computersimulator.CPU.CPU;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Филиппов Виталий 23501/4
 */
public class CPU_JUnitTest {
    
    CPU cpu;
    RAM ram;
    RAM testRAM;
    
    public CPU_JUnitTest() {
        ram = new RAM(22);
        testRAM = new RAM(22);
        cpu = new CPU(ram);
    }
    
    @Before
    public void setUp() {
        ram.changeCell( 0, new CellRAM(Command.READ_A, 150));
        ram.changeCell( 1, new CellRAM(Command.READ_B, 5));
        ram.changeCell( 2, new CellRAM(Command.SUM_A_B, 0));
        ram.changeCell( 3, new CellRAM(Command.WRITE_A_NUM, 21));//155
        ram.changeCell( 4, new CellRAM(Command.INV_B, 0));
        ram.changeCell( 5, new CellRAM(Command.SUM_A_B, 0));
        ram.changeCell( 6, new CellRAM(Command.DIV_A_B, 0));
        ram.changeCell( 7, new CellRAM(Command.WRITE_A_NUM, 20));//-30
        ram.changeCell( 8, new CellRAM(Command.READ_B, -10));
        ram.changeCell( 9, new CellRAM(Command.MUL_A_B, 0));
        ram.changeCell(10, new CellRAM(Command.WRITE_A_NUM, 19));//300
        ram.changeCell(11, new CellRAM(Command.READ_A_NUM, 21));//155
        ram.changeCell(12, new CellRAM(Command.READ_B_NUM, 20));//-30
        ram.changeCell(13, new CellRAM(Command.SUM_A_B, 0));//125
        ram.changeCell(14, new CellRAM(Command.JUMP_A_0_TO_N, 2));
        ram.changeCell(15, new CellRAM(Command.READ_A, -999));
        ram.changeCell(16, new CellRAM(Command.WRITE_A_NUM, 18));//125
        ram.changeCell(17, new CellRAM(Command.END, 0));
        
        testRAM.changeCell( 0, new CellRAM(Command.READ_A, 150));
        testRAM.changeCell( 1, new CellRAM(Command.READ_B, 5));
        testRAM.changeCell( 2, new CellRAM(Command.SUM_A_B, 0));
        testRAM.changeCell( 3, new CellRAM(Command.WRITE_A_NUM, 21));//155
        testRAM.changeCell( 4, new CellRAM(Command.INV_B, 0));
        testRAM.changeCell( 5, new CellRAM(Command.SUM_A_B, 0));
        testRAM.changeCell( 6, new CellRAM(Command.DIV_A_B, 0));
        testRAM.changeCell( 7, new CellRAM(Command.WRITE_A_NUM, 20));//-30
        testRAM.changeCell( 8, new CellRAM(Command.READ_B, -10));
        testRAM.changeCell( 9, new CellRAM(Command.MUL_A_B, 0));
        testRAM.changeCell(10, new CellRAM(Command.WRITE_A_NUM, 19));//300
        testRAM.changeCell(11, new CellRAM(Command.READ_A_NUM, 21));//155
        testRAM.changeCell(12, new CellRAM(Command.READ_B_NUM, 20));//-30
        testRAM.changeCell(13, new CellRAM(Command.SUM_A_B, 0));//125
        testRAM.changeCell(14, new CellRAM(Command.JUMP_A_0_TO_N, 2));//125>0 -> Jump
        testRAM.changeCell(15, new CellRAM(Command.READ_A, -999));
        testRAM.changeCell(16, new CellRAM(Command.WRITE_A_NUM, 18));//125
        testRAM.changeCell(17, new CellRAM(Command.END, 0));
        testRAM.changeCell(18, new CellRAM(Command.DATA, 125));
        testRAM.changeCell(19, new CellRAM(Command.DATA, 300));
        testRAM.changeCell(20, new CellRAM(Command.DATA, -30));
        testRAM.changeCell(21, new CellRAM(Command.DATA, 155));
    }
    
    @Test
    public void test0() {
        assertTrue(0==cpu.getRegA() && cpu.getRegB()==0 && 0==cpu.getCounter() && 0==cpu.getNextCounter());
        cpu.step();
        assertTrue(150==cpu.getRegA() && cpu.getRegB()==0 && 0==cpu.getCounter() && 1==cpu.getNextCounter());
        cpu.step();
        assertTrue(150==cpu.getRegA() && cpu.getRegB()==5 && 1==cpu.getCounter() && 2==cpu.getNextCounter());
        cpu.step();
        assertTrue(155==cpu.getRegA() && cpu.getRegB()==5 && 2==cpu.getCounter() && 3==cpu.getNextCounter());
        cpu.step();
        assertTrue(155==cpu.getRegA() && cpu.getRegB()==5 && 3==cpu.getCounter() && 4==cpu.getNextCounter());
        cpu.step();
        assertTrue(155==cpu.getRegA() && cpu.getRegB()==-5 && 4==cpu.getCounter() && 5==cpu.getNextCounter());
        cpu.step();
        assertTrue(150==cpu.getRegA() && cpu.getRegB()==-5 && 5==cpu.getCounter() && 6==cpu.getNextCounter());
        cpu.step();
        assertTrue(-30==cpu.getRegA() && cpu.getRegB()==-5 && 6==cpu.getCounter() && 7==cpu.getNextCounter());
        cpu.step();
        assertTrue(-30==cpu.getRegA() && cpu.getRegB()==-5 && 7==cpu.getCounter() && 8==cpu.getNextCounter());
        cpu.step();
        assertTrue(-30==cpu.getRegA() && cpu.getRegB()==-10 && 8==cpu.getCounter() && 9==cpu.getNextCounter());
        cpu.step();
        assertTrue(300==cpu.getRegA() && cpu.getRegB()==-10 && 9==cpu.getCounter() && 10==cpu.getNextCounter());
        cpu.step();
        assertTrue(300==cpu.getRegA() && cpu.getRegB()==-10 && 10==cpu.getCounter() && 11==cpu.getNextCounter());
        cpu.step();
        assertTrue(155==cpu.getRegA() && cpu.getRegB()==-10 && 11==cpu.getCounter() && 12==cpu.getNextCounter());
        cpu.step();
        assertTrue(155==cpu.getRegA() && cpu.getRegB()==-30 && 12==cpu.getCounter() && 13==cpu.getNextCounter());
        cpu.step();
        assertTrue(125==cpu.getRegA() && cpu.getRegB()==-30 && 13==cpu.getCounter() && 14==cpu.getNextCounter());
        cpu.step();
        assertTrue(125==cpu.getRegA() && cpu.getRegB()==-30 && 14==cpu.getCounter() && 16==cpu.getNextCounter());
        cpu.step();
        assertTrue(125==cpu.getRegA() && cpu.getRegB()==-30 && 16==cpu.getCounter() && 17==cpu.getNextCounter());
        cpu.step();
        assertTrue(125==cpu.getRegA() && cpu.getRegB()==-30 && 17==cpu.getCounter() && 17==cpu.getNextCounter());
    }
    
    @Test
    public void test1() {
        boolean end = false;
        while(!end){
            assertEquals(cpu.getStatusOfEnd(),"Программа выполняется...");
            assertFalse(cpu.getCounter()==15);
            end=!cpu.step();
        }
        for(int i=0; i<22; i++){
            assertTrue(ram.getCell(i).getCommand()==testRAM.getCell(i).getCommand());
            assertTrue(ram.getCell(i).getValue()==testRAM.getCell(i).getValue());
        }
        assertEquals(cpu.getStatusOfEnd(),"Программа завершена УСПЕШНО.");
    }
    
    @Test
    public void test2() {
        ram.changeCell(17, new CellRAM(Command.DATA, 0));
        testRAM.changeCell(17, new CellRAM(Command.DATA, 0));
        
        boolean end = false;
        while(!end){
            assertEquals(cpu.getStatusOfEnd(),"Программа выполняется...");
            assertFalse(cpu.getCounter()==15);
            end=!cpu.step();
        }
        for(int i=0; i<22; i++){
            assertTrue(ram.getCell(i).getCommand()==testRAM.getCell(i).getCommand());
            assertTrue(ram.getCell(i).getValue()==testRAM.getCell(i).getValue());
        }
        assertEquals(cpu.getStatusOfEnd(),"Программа завершена с ОШИБКОЙ: счётчик вышел за пределы.");
    }
    
    @Test
    public void test3() {
        ram.changeCell(16, new CellRAM(Command.WRITE_A_NUM, 22));
        testRAM.changeCell(16, new CellRAM(Command.WRITE_A_NUM, 22));
        
        boolean end = false;
        while(!end){
            assertEquals(cpu.getStatusOfEnd(),"Программа выполняется...");
            assertFalse(cpu.getCounter()==15);
            end=!cpu.step();
        }
        for(int i=0; i<17; i++){
            assertEquals(ram.getCell(i).getCommand(),testRAM.getCell(i).getCommand());
            assertEquals(ram.getCell(i).getValue(),testRAM.getCell(i).getValue());
        }
        assertEquals(cpu.getStatusOfEnd(),"Программа завершена с ОШИБКОЙ: попытка записать в ячейку №22");
    }
    
    @Test
    public void test4() {
        ram.changeCell(0, new CellRAM(Command.OUT_A, 10));
        testRAM.changeCell(16, new CellRAM(Command.OUT_A, 10));
        ram.changeCell(1, new CellRAM(Command.OUT_B, 22));
        testRAM.changeCell(16, new CellRAM(Command.OUT_B, 22));
        assertEquals(cpu.getOut(),"");
        cpu.step();
        assertEquals(cpu.getOut(),"RegA = 0");
        cpu.step();
        assertEquals(cpu.getOut(),"RegB = 0");
    }
    @Test
    public void test5() {
        ram.changeCell(0, new CellRAM(Command.READ_A, 10));
        testRAM.changeCell(16, new CellRAM(Command.READ_A, 10));
        ram.changeCell(1, new CellRAM(Command.DIV_A_B, 0));
        testRAM.changeCell(16, new CellRAM(Command.DIV_A_B, 0));
        assertEquals(cpu.getOut(),"");
        cpu.step();
        assertEquals(cpu.getOut(),"");
        cpu.step();
        assertEquals(cpu.getOut(),"Ошибка: деление на ноль. команда была пропущена");
        assertEquals(cpu.getRegA(),10);
    }
}