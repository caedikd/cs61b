package enigma;
import org.junit.Test;
import static org.junit.Assert.*;


import static enigma.TestUtils.*;
public class AlphabetTest {

    @Test
    public void alphabetATest() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Alphabet norm = new Alphabet(chars);
        assertEquals(26, norm.size());
        assertEquals(true, norm.contains('D'));
        assertEquals('Y', norm.toChar(24));
        assertEquals(24, norm.toInt('Y'));
    }
}
