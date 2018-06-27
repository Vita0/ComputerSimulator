package computersimulator;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class Translator_JUnitTest {

    Translator i;
    RAM r, t;

    public Translator_JUnitTest() {
        r = new RAM(0);
        i = new Translator(r);
    }

    @Test
    public void test0() {
        r = new RAM(4);
        t = new RAM(4);
        i = new Translator(r);
       ArrayList<String> v = new ArrayList<>();
        v.add("WriteA a");
        v.add("WriteA b");

        i.translate(v);
        r = new RAM(i.getRAM());

        t.changeCell(0, new CellRAM(Command.WRITE_A_NUM, 2));
        t.changeCell(1, new CellRAM(Command.WRITE_A_NUM, 3));

        assertEquals(t.getCell(0).getCommand(), r.getCell(0).getCommand());
        assertEquals(t.getCell(0).getValue(), r.getCell(0).getValue());
        assertEquals(t.getCell(1).getCommand(), r.getCell(1).getCommand());
        assertEquals(t.getCell(1).getValue(), r.getCell(1).getValue());
        assertEquals(i.getOut(), "");
    }

    @Test
    public void test1() {
        r = new RAM(3);
        t = new RAM(3);
        i = new Translator(r);
        ArrayList<String> v = new ArrayList<>();
        v.add("WriteA a");
        v.add("ReadBVar a");

        i.translate(v);
        r = new RAM(i.getRAM());

        t.changeCell(0, new CellRAM(Command.WRITE_A_NUM, 2));
        t.changeCell(1, new CellRAM(Command.READ_B_NUM, 2));

        assertEquals(t.getCell(0).getCommand(), r.getCell(0).getCommand());
        assertEquals(t.getCell(0).getValue(), r.getCell(0).getValue());
        assertEquals(t.getCell(1).getCommand(), r.getCell(1).getCommand());
        assertEquals(t.getCell(1).getValue(), r.getCell(1).getValue());
        assertEquals(i.getOut(), "");
    }

    @Test
    public void test2() {
        r = new RAM(1);
        t = new RAM(1);
        i = new Translator(r);
        ArrayList<String> v = new ArrayList<>();
        v.add("WriteA xyz");
        v.add("ReadBVar xyz");

        i.translate(v);
        r = new RAM(i.getRAM());

        assertEquals(t.getCell(0).getCommand(), r.getCell(0).getCommand());
        assertEquals(t.getCell(0).getValue(), r.getCell(0).getValue());
        assertEquals(i.getOut(), "Недостаточно памяти в ОЗУ, увеличьте количество ячеек в ОЗУ");
    }
}