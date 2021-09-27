package enigma;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.HashMap;

import static enigma.TestUtils.*;
public class AlphabetTest {

    @Test
    public void AlphabetTest(){
        String _chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Alphabet norm = new Alphabet(_chars);
        assertEquals(26, norm.size());
        assertEquals(true, norm.contains('D'));
        assertEquals('Y', norm.toChar(24));
        assertEquals(24, norm.toInt('Y'));
    }
}
