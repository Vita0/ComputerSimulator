package computersimulator.CPU;

import org.junit.Test;
import static org.junit.Assert.*;

public class ALU_JUnitTest {
    ALU alu;
    public ALU_JUnitTest() {
        alu = new ALU();
    }
    
    @Test
    public void test0() {
        assertEquals(alu.div(5, 2),2);
        assertEquals(alu.sum(5, 2),7);
        assertEquals(alu.mul(5, 2),10);
        assertEquals(alu.inv(5),-5);
        assertTrue(alu.isAPossitive(1)==true);
        assertTrue(alu.isAPossitive(0)==false);
        assertTrue(alu.isAPossitive(-1)==false);
    }
    
    @Test(expected = DivisionByZeroALUException.class)
    public void test1() {
        alu.div(10, 0);
    }
}