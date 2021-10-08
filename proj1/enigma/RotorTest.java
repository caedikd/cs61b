package enigma;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RotorTest {

    @Test
    public void testRotorSetting() {
        Alphabet a = new Alphabet("ABCD");
        Permutation p = new Permutation("(BACD)", a);
        Rotor r = new Rotor("I", p);
        assertEquals(0, r.setting());
        r.set(1);
        assertEquals(1, r.setting());
    }

    @Test
    public void testRotorConvertForward() {
        Alphabet a = new Alphabet("ABCD");
        Permutation p = new Permutation("(BACD)", a);
        Rotor r = new Rotor("I", p);
        assertEquals(0, r.convertForward(1));

        Alphabet c = new Alphabet("ABCDEFG12345");
        Permutation s = new Permutation ("(ABC1) (D23) (EFG4) (5)", c);
        Rotor r2 = new Rotor("Rotor II", s);
        r2.set(2);
        assertEquals(2, r2.setting());
        assertEquals(10, r2.convertForward(5));
    }

    @Test
    public void testRotorConvertBackward() {
        Alphabet a = new Alphabet("ABCD");
        Permutation p = new Permutation("(BACD)", a);
        Rotor r = new Rotor("I", p);
        assertEquals(1, r.convertBackward(0));

        Alphabet c = new Alphabet("ABCDEFG12345");
        Permutation s = new Permutation ("(ABC1) (D23) (EFG4) (5)", c);
        Rotor r2 = new Rotor("Rotor II", s);
        r2.set(2);
        assertEquals(2, r2.setting());
        assertEquals(9, r2.convertBackward(9));
    }

}
