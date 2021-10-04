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
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        //edge cases too
        assertEquals('B', p.invert('A'));
        assertEquals(1, p.permute(3));
        assertEquals(3, p.invert(1));
        assertEquals(2, p.permute(0));
        assertEquals('D', p.invert('B'));
        assertEquals('B', p.permute('D'));
        assertEquals('C', p.permute('A'));
        assertEquals(2, p.permute(0));
    }

    public void testPermuteChar() {
        Permutation p = getNewPermutation("(BACD) (F)", getNewAlphabet("ABCDEF"));
        assertEquals('B', p.invert('A'));
        assertEquals('D', p.invert('B'));
        assertEquals('C', p.permute('A'));
        assertEquals('E', p.permute('E'));
        assertEquals('F', p.permute('F'));
        assertEquals(2, p.permute(0));
        assertEquals(4, p.permute(4));

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

//    @Test
//    public void testalphabet() {
//        Alphabet A = getNewAlphabet("ABCD");
//        A.
//        Permutation p = getNewPermutation("(ABCD) ", A);
//        assertEquals('B', p.permute('A'));
//        assertEquals(A, p.);
//    }

    @Test
    public void testPermutations() {
        Permutation test = getNewPermutation(NAVALA.get("II"), getNewAlphabet(UPPER_STRING));
        assertEquals('X', test.permute('I'));
        assertEquals(23, test.permute(8));
        assertEquals('I', test.invert('X'));
        assertEquals('V', test.permute('X'));
        assertEquals(21, test.permute(23));
        assertEquals('F', test.permute('W'));
        assertEquals(5, test.permute(22));
        assertEquals('R', test.permute('G'));
        assertEquals(17, test.permute(6));
        assertEquals('A', test.permute('A'));
        assertEquals(0, test.permute(0));
        assertEquals('Q', test.permute('Q'));
        assertEquals(16, test.permute(16));
        assertEquals(16, test.invert(16));
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
