package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author Caedi Seim
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void testInvertChar() {
        Permutation a = new Permutation("(BACD)", new Alphabet("ABCD"));
        assertEquals('D', a.invert('B'));
        assertEquals('A', a.invert('C'));

        Permutation q = new Permutation("(A1) (B2) (C3)", new Alphabet("ABC123"));
        assertEquals('A', q.invert('1'));
        assertEquals('2', q.invert('B'));

        Permutation p = new Permutation("(BACD) (12E) (F)", new Alphabet("ABCDEF1234"));
        assertEquals('C', p.invert('D'));
        assertEquals('D', p.invert('B'));
        assertEquals('F', p.invert('F'));
        assertEquals('E', p.invert('1'));
        assertEquals('1', p.invert('2'));
        assertEquals('4', p.invert('4'));
        assertEquals('3', p.invert('3'));

        //NAVALA.put("Gamma", "(AFNIRLBSQWVXGUZDKMTPCOYJHE)");
        //        NAVALA_MAP.put("Gamma", "FSOKANUERHMBTIYCWLQPZXVGJD");
        Permutation c = new Permutation(NAVALA.get("Gamma"), new Alphabet(NAVALA_MAP.get("Gamma")));
        assertEquals('B', c.invert('S'));
        assertEquals('Z', c.invert('D'));

        //        NAVALB.put("III", " (ZACGODIS) (BEKULYNXPHQVTJWRF) (M) ");
        //        NAVALB_MAP.put("III", "CEGIKBOQSWUYMXDHVFZJLTRPNA");
        Permutation d = new Permutation(NAVALB.get("III"), new Alphabet(NAVALB_MAP.get("III")));
        assertEquals('B', d.invert('E'));
        assertEquals('M', d.invert('M'));
        assertEquals('I', d.invert('S'));
        assertEquals('Z', d.invert('A'));
        assertEquals('L', d.invert('Y'));


    }


    @Test
    public void testPermuteChar() {
        Permutation a = new Permutation("(BACD)", new Alphabet("ABCD"));
        assertEquals('A', a.permute('B'));
        assertEquals('D', a.permute('C'));

        Permutation q = new Permutation("(A1) (B2) (C3)", new Alphabet("ABC123"));
        assertEquals('A', q.permute('1'));
        assertEquals('2', q.permute('B'));

        Permutation p = new Permutation("(BACD) (12E) (F)", new Alphabet("ABCDEF1234"));
        assertEquals('A', p.permute('B'));
        assertEquals('2', p.permute('1'));
        assertEquals('F', p.permute('F'));
        assertEquals('1', p.permute('E'));
        assertEquals('B', p.permute('D'));
        assertEquals('4', p.permute('4'));
        assertEquals('3', p.permute('3'));

        //        NAVALA.put("I", "(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)");
        //        NAVALA_MAP.put("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        Permutation b = new Permutation(NAVALA.get("I"), new Alphabet(NAVALA_MAP.get("I")));
        assertEquals('M' , b.permute('C'));
        assertEquals('S' , b.permute('S'));
        assertEquals('N' , b.permute('K'));

        //NAVALA.put("Gamma", "(AFNIRLBSQWVXGUZDKMTPCOYJHE)");
        //        NAVALA_MAP.put("Gamma", "FSOKANUERHMBTIYCWLQPZXVGJD");
        Permutation c = new Permutation(NAVALA.get("Gamma"), new Alphabet(NAVALA_MAP.get("Gamma")));
        assertEquals('Q', c.permute('S'));
        assertEquals('A', c.permute('E'));

                NAVALB.put("III", " (ZACGODIS) (BEKULYNXPHQVTJWRF) (M) ");
                NAVALB_MAP.put("III", "CEGIKBOQSWUYMXDHVFZJLTRPNA");
        Permutation d = new Permutation(NAVALB.get("III"), new Alphabet(NAVALB_MAP.get("III")));
        assertEquals('K', d.permute('E'));
        assertEquals('B', d.permute('F'));
        assertEquals('Z', d.permute('S'));
        assertEquals('C', d.permute('A'));
        assertEquals('N', d.permute('Y'));
        assertEquals('M', d.permute('M'));
    }

    @Test
    public void testInvertInt() {
        Permutation a = new Permutation("(BACD)", new Alphabet("ABCD"));
        assertEquals(3, a.invert(1));
        assertEquals(0, a.invert(2));

        Permutation q = new Permutation("(A1) (B2) (C3)", new Alphabet("ABC123"));
        assertEquals(0, q.invert(3));
        assertEquals(4, q.invert(1));

        Permutation p = new Permutation("(BACD) (12E) (F)", new Alphabet("ABCDEF1234"));
        assertEquals(2, p.invert(3));
        assertEquals(3, p.invert(1));
        assertEquals(5, p.invert(5));
        assertEquals(7, p.invert(4));
        assertEquals(4, p.invert(6));
        assertEquals(9, p.invert(9));
        assertEquals(8, p.invert(8));
        assertEquals(2, p.invert(13));


    }

    @Test
    public void testPermuteInt() {
        Permutation a = new Permutation("(BACD)", new Alphabet("ABCD"));
        assertEquals(0, a.permute(1));
        assertEquals(3, a.permute(2));

        Permutation q = new Permutation("(A1) (B2) (C3)", new Alphabet("ABC123"));
        assertEquals(0, q.permute(3));
        assertEquals(4, q.permute(1));

        Permutation p = new Permutation("(BACD) (12E) (F)", new Alphabet("ABCDEF1234"));
        assertEquals(0, p.permute(1));
        assertEquals(7, p.permute(6));
        assertEquals(5, p.permute(5));
        assertEquals(6, p.permute(4));
        assertEquals(1, p.permute(3));
        assertEquals(9, p.permute(9));
        assertEquals(8, p.permute(8));
        assertEquals(1, p.permute(13));

        //        NAVALA.put("I", "(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)");
        //        NAVALA_MAP.put("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        Permutation b = new Permutation(NAVALA.get("I"), new Alphabet(NAVALA_MAP.get("I")));
        assertEquals(20 , b.permute(17));

        //        NAVALB.put("III", " (ZACGODIS) (BEKULYNXPHQVTJWRF) (M) ");
        //        NAVALB_MAP.put("III", "CEGIKBOQSWUYMXDHVFZJLTRPNA");
        Permutation d = new Permutation(NAVALB.get("III"), new Alphabet(NAVALB_MAP.get("III")));
        assertEquals(4, d.permute(1));

    }


    @Test(expected = EnigmaException.class)
    public void testNotInAlphabet() {
        Permutation p = new Permutation("(BACD)", new Alphabet("ABCD"));
        assertEquals("not in alphabet", p.invert('F'));
    }

    @Test(expected = EnigmaException.class)
    public void testNoAlphabet() {
        Permutation p = new Permutation("(BACD)", new Alphabet(""));
        p.invert('F');
    }

    @Test(expected = EnigmaException.class)
    public void testAlphabetNotInPerm() {
        Permutation p = new Permutation("(BACD)", new Alphabet("TRS"));
        p.invert('B');
    }

//    @Test(expected = EnigmaException.class)
//    public void testWrongForm() {
//        Permutation p = new Permutation("(BACD) (AB) (B)", new Alphabet("ABCD"));
//        p.invert('B');
//    }
//
//    @Test(expected = EnigmaException.class)
//    public void testWrongForm3() {
//        Permutation p = new Permutation("(BACD)(AB)(B)", new Alphabet("ABCD"));
//        p.invert('B');
//    }
//
//    @Test(expected = EnigmaException.class)
//    public void testWrongForm2() {
//        Permutation p = new Permutation("ABCD", new Alphabet("ABCD"));
//        p.invert('A');
//    }
//
//    @Test(expected = EnigmaException.class)
//    public void testWrongIndex() {
//        Permutation p = new Permutation("ABCD", new Alphabet("ABCD"));
//        p.invert(8);
//    }

    @Test(expected = EnigmaException.class)
    public void testCharDoesntExist() {
        Permutation p = new Permutation("ABCD", new Alphabet("ABCD"));
        p.permute('P');
    }

    @Test
    public void testalphabet() {
        Alphabet A = new Alphabet("ABCD");
        Permutation p = new Permutation("(ABCD) ", A);
        p.permute('A');
        assertEquals(true, A.contains('A'));
        assertEquals(4, A.size());

    }


//    @Test(expected = EnigmaException.class)
//    public void testMultipleinAlphabet() {
//        Permutation p = new Permutation("(ABCB)", new Alphabet("ABCD"));
//        p.permute('B');
//    }


//    @Test(expected = EnigmaException.class)
//    public void testSpaces() {
//        Permutation p = new Permutation("(A B) (C D)", new Alphabet("ABCD"));
//        p.permute('A');
//    }

    @Test(expected = EnigmaException.class)
    public void testNoChar() {
        Permutation p =  new Permutation("ABCD", new Alphabet("ABCD"));
        p.permute('E');
    }


}
