package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/**
 * The suite of all JUnit tests for the Permutation class. For the purposes of
 * this lab (in order to test) this is an abstract class, but in proj1, it will
 * be a concrete class. If you want to copy your tests for proj1, you can make
 * this class concrete by removing the 4 abstract keywords and implementing the
 * 3 abstract methods.
 *
 *  @author
 */
public abstract class PermutationTest {

    /**
     * For this lab, you must use this to get a new Permutation,
     * the equivalent to:
     * new Permutation(cycles, alphabet)
     * @return a Permutation with cycles as its cycles and alphabet as
     * its alphabet
     * @see Permutation for description of the Permutation conctructor
     */
    abstract Permutation getNewPermutation(String cycles, Alphabet alphabet);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet(chars)
     * @return an Alphabet with chars as its characters
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet(String chars);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet()
     * @return a default Alphabet with characters ABCD...Z
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet();

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /** Check that PERM has an ALPHABET whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha,
                           Permutation perm, Alphabet alpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.toInt(c), ei = alpha.toInt(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        Alphabet alpha = getNewAlphabet();
        Permutation perm = getNewPermutation("", alpha);
        checkPerm("identity", UPPER_STRING, UPPER_STRING, perm, alpha);
    }

    @Test
    public void testInvertChar() {
        Permutation a = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertEquals('D', a.invert('B'));
        assertEquals('A', a.invert('C'));

        Permutation q = getNewPermutation("(A1) (B2) (C3)", getNewAlphabet("ABC123"));
        assertEquals('A', q.invert('1'));
        assertEquals('2', q.invert('B'));

        Permutation p = getNewPermutation("(BACD) (12E) (F)", getNewAlphabet("ABCDEF1234"));
        assertEquals('C', p.invert('D'));
        assertEquals('D', p.invert('B'));
        assertEquals('F', p.invert('F'));
        assertEquals('E', p.invert('1'));
        assertEquals('1', p.invert('2'));
        assertEquals('4', p.invert('4'));
        assertEquals('3', p.invert('3'));
    }


    @Test
    public void testPermuteChar() {
        Permutation a = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertEquals('A', a.permute('B'));
        assertEquals('D', a.permute('C'));

        Permutation q = getNewPermutation("(A1) (B2) (C3)", getNewAlphabet("ABC123"));
        assertEquals('A', q.permute('1'));
        assertEquals('2', q.permute('B'));

        Permutation p = getNewPermutation("(BACD) (12E) (F)", getNewAlphabet("ABCDEF1234"));
        assertEquals('A', p.permute('B'));
        assertEquals('2', p.permute('1'));
        assertEquals('F', p.permute('F'));
        assertEquals('1', p.permute('E'));
        assertEquals('B', p.permute('D'));
        assertEquals('4', p.permute('4'));
        assertEquals('3', p.permute('3'));

        //        NAVALA.put("I", "(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)");
        //        NAVALA_MAP.put("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        Permutation b = getNewPermutation(NAVALA.get("I"), getNewAlphabet(NAVALA_MAP.get("I")));
        assertEquals('M' , b.permute('C'));
        assertEquals('S' , b.permute('S'));
        assertEquals('N' , b.permute('K'));




    }

    @Test
    public void testInvertInt() {
        Permutation a = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertEquals(3, a.invert(1));
        assertEquals(0, a.invert(2));

        Permutation q = getNewPermutation("(A1) (B2) (C3)", getNewAlphabet("ABC123"));
        assertEquals(0, q.invert(3));
        assertEquals(4, q.invert(1));

        Permutation p = getNewPermutation("(BACD) (12E) (F)", getNewAlphabet("ABCDEF1234"));
        assertEquals(2, p.invert(3));
        assertEquals(3, p.invert(1));
        assertEquals(5, p.invert(5));
        assertEquals(7, p.invert(4));
        assertEquals(4, p.invert(6));
        assertEquals(9, p.invert(9));
        assertEquals(8, p.invert(8));
    }

    @Test
    public void testPermuteInt() {
        Permutation a = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertEquals(0, a.permute(1));
        assertEquals(3, a.permute(2));

        Permutation q = getNewPermutation("(A1) (B2) (C3)", getNewAlphabet("ABC123"));
        assertEquals(0, q.permute(3));
        assertEquals(4, q.permute(1));

        Permutation p = getNewPermutation("(BACD) (12E) (F)", getNewAlphabet("ABCDEF1234"));
        assertEquals(0, p.permute(1));
        assertEquals(7, p.permute(6));
        assertEquals(5, p.permute(5));
        assertEquals(6, p.permute(4));
        assertEquals(1, p.permute(3));
        assertEquals(9, p.permute(9));
        assertEquals(8, p.invert(8));

        //        NAVALA.put("I", "(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)");
        //        NAVALA_MAP.put("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        Permutation b = getNewPermutation(NAVALA.get("I"), getNewAlphabet(NAVALA_MAP.get("I")));
        assertEquals(20 , b.permute(17));

    }


    @Test(expected = EnigmaException.class)
    public void testNotInAlphabet() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertEquals("not in alphabet", p.invert('F'));
    }

    @Test(expected = EnigmaException.class)
    public void testNoAlphabet() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet(""));
        p.invert('F');
    }

    @Test(expected = EnigmaException.class)
    public void testAlphabetNotInPerm() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("TRS"));
        p.invert('B');
    }

    @Test(expected = EnigmaException.class)
    public void testWrongForm() {
        Permutation p = getNewPermutation("(BACD) (AB) (B)", getNewAlphabet("ABCD"));
        p.invert('B');
    }

    @Test(expected = EnigmaException.class)
    public void testWrongForm3() {
        Permutation p = getNewPermutation("(BACD)(AB)(B)", getNewAlphabet("ABCD"));
        p.invert('B');
    }

    @Test(expected = EnigmaException.class)
    public void testWrongForm2() {
        Permutation p = getNewPermutation("ABCD", getNewAlphabet("ABCD"));
        p.invert('A');
    }

    @Test(expected = EnigmaException.class)
    public void testWrongIndex() {
        Permutation p = getNewPermutation("ABCD", getNewAlphabet("ABCD"));
        p.invert(8);
    }

    @Test(expected = EnigmaException.class)
    public void testCharDoesntExist() {
        Permutation p = getNewPermutation("ABCD", getNewAlphabet("ABCD"));
        p.permute('P');
    }

    @Test
    public void testalphabet() {
        Alphabet A = getNewAlphabet("ABCD");
        Permutation p = getNewPermutation("(ABCD) ", A);
        p.permute('A');
        assertEquals(true, A.contains('A'));
        assertEquals(4, A.size());

    }


    @Test(expected = EnigmaException.class)
    public void testMultipleinAlphabet() {
        Permutation p = getNewPermutation("(ABCB)", getNewAlphabet("ABCD"));
       p.permute('B');
    }

    @Test
    public void testNoSpaces() {
        Permutation p = getNewPermutation("(AB)(CD)", getNewAlphabet("ABCD"));
        assertEquals('A',p.permute('B'));
    }

    @Test(expected = EnigmaException.class)
    public void testSpaces() {
        Permutation p = getNewPermutation("(A B) (C D)", getNewAlphabet("ABCD"));
        p.permute('A');
    }

    @Test(expected = EnigmaException.class)
    public void testNoChar() {
        Permutation p = getNewPermutation("ABCD", getNewAlphabet("ABCD"));
        p.permute('E');
    }













    // FIXME: Add tests here that pass on a correct Permutation and fail on buggy Permutations.
}
