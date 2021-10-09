package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Caedi Seim
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = cycles.replaceAll("[)(]", "");
        _cycles = _cycles.replaceAll("^\\s+", "");
        _arrayCycles = _cycles.split(" ");
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        char find = _alphabet.toChar(wrap(p));
        char to = permute(find);
        return _alphabet.toInt(to);
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char find = _alphabet.toChar(wrap(c));
        char to = invert(find);
        return _alphabet.toInt(to);

    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        if (!_alphabet.contains(p)) {
            throw error("<not in alphabet>");
        }
        for (int i = 0; i < _arrayCycles.length; i++) {
            if (_arrayCycles[i].length() == 0) {
                return p;
            }
            for (int j = 0; j < _arrayCycles[i].length(); j++) {
                if (p == _arrayCycles[i].charAt(j)) {
                    if (j == _arrayCycles[i].length() - 1) {
                        return _arrayCycles[i].charAt(0);
                    }
                    return _arrayCycles[i].charAt(j + 1);
                }
            }
        }
        return p;
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        if (!_alphabet.contains(c)) {
            throw error("<not in alphabet>");
        }
        int find = _alphabet.toInt(c);
        for (int i = 0; i < _arrayCycles.length; i++) {
            if (_arrayCycles[i].length() == 0) {
                return c;
            }
            for (int j = 0; j < _arrayCycles[i].length(); j++) {
                if (c == _arrayCycles[i].charAt(j)) {
                    if (j == 0) {
                        return _arrayCycles[i]
                                .charAt(_arrayCycles[i].length() - 1);
                    }
                    return _arrayCycles[i].charAt(j - 1);
                }
            }
        }
        return c;    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        int count = 0;
        for (int i = 0; i < _arrayCycles.length; i++) {
            count += _arrayCycles[i].length();
        }
        if (count == _alphabet.size()) {
            return true;
        }
        return false;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** _cycles in a string. */
    private String _cycles;

    /** cycles in an arraylist for accesibility. */
    private String[] _arrayCycles;
}
