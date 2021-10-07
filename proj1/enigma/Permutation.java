package enigma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;

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
    /*
     The constructor should take whatever cycle specification/alphabet are passed
      in and do some preprocessing to determine what the permutations are. A good
      start would be to think about what instance variables you want to track--for
      example, how are you going to save which letter maps to which in a
      permutation/its inversion?
     */
    Permutation(String cycles, Alphabet alphabet) {
        //what instance variables I want to track for
        _alphabet = alphabet;
        _cycles = cycles.replaceAll("[)(]", "");
        _cycles = _cycles.replaceAll("^\\s+", "");
        _arrayCycles = _cycles.split(" ");


        //for (String c: arrayOfStrings) {
         //   addCycle(c);
        //}
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        /*
            you can feed a cycle into the method and it will
            transform it into whatever representation you choose that
            will allow you to actually translate one letter to another.
         */
        for(int i = 0; i < _arrayCycles.length + 1; i++){
            for(int j = 0; i < _arrayCycles[i].length(); j++){
            }
        }
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
        //return the index of the newly mapped char in the alphabet
        // For example,if the newly mapped char is 'C' and our Alphabet is the default
        // alphabet, we should return 2?
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
        // map the int p modulo alphabet size to the next character in the cycle
        //use wrap?
        if (_alphabet.contains(p) == false) {
            throw error("<not in alphabet>");
        }
        for (int i = 0; i < _arrayCycles.length; i++) {
            if (_arrayCycles[i].length() == 0){
                return p;
            }
            for (int j = 0; j < _arrayCycles[i].length(); j++) {
                if (p == _arrayCycles[i].charAt(j)) {
                    if (j == _arrayCycles[i].length()-1){
                        return _arrayCycles[i].charAt(0);
                    }
                    return _arrayCycles[i].charAt(j+1);
                }
            }
        }
        return p;
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        if (_alphabet.contains(c) == false) {
            throw error("<not in alphabet>");
        }
        int find = _alphabet.toInt(c);
        for (int i = 0; i < _arrayCycles.length; i++) {
            if (_arrayCycles[i].length() == 0){
                return c;
            }
            for (int j = 0; j < _arrayCycles[i].length(); j++) {
                if (c == _arrayCycles[i].charAt(j)) {
                    if (j == 0){
                        return _arrayCycles[i].charAt(_arrayCycles[i].length() - 1);
                    }
                    return _arrayCycles[i].charAt(j-1);
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

    private String _cycleList;

    private String _cycles;

    private String[] _arrayCycles;
    // FIXME: ADDITIONAL FIELDS HERE, AS NEEDED
}
