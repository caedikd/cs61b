
package enigma;

import java.util.HashMap;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Caedi Seim
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            HashMap<String, Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _allRotors = allRotors;
    }

    /*
    All pawls are on moving rotors, and all pawls (and their corresponding
    moving rotors) are to the right of all rotors that can’t move (whether
     moving and pawl-less or otherwise)
     */
    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {

        if (!_allRotors.get(rotors[0]).reflecting()) {
            throw new EnigmaException("Wrong Order");
        }

        _inserted = new Rotor[_numRotors];

        for (int i = 0; i < rotors.length; i++) {
            if (i != 0 && _allRotors.get(rotors[i]).reflecting()) {
                throw new EnigmaException("Wrong Order");
            }
            _inserted[i] = _allRotors.get(rotors[i]);
        }

    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        if (setting.length() != numRotors() - 1) {
            throw new EnigmaException("Wrong set string");
        }
        for (int j = 0; j < setting.length(); j++) {
            if (!_alphabet.contains(setting.charAt(j))) {
                throw new EnigmaException("Set characters not in Alphabet");
            }
        }

        if (!_inserted[0].reflecting()) {
            throw new EnigmaException("Missing Reflector");
        }

        for (int i = 0; i < numRotors() - 1; i++) {
            _inserted[i + 1].set(setting.charAt(i));
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugBoard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        /*
        first look for places that need to be advanced, add them to a
        data structure, and then advance them by calling .advance
         */
        boolean[] advance = new boolean[numRotors()];
        advance[_inserted.length - 1] = true;
        for (int i = numRotors() - 2; i > 0; i--) {
            if (((_inserted[i + 1].atNotch()
                    || _inserted[i - 1].rotates() && _inserted[i].atNotch()))) {
                advance[i] = true;
            } else {
                advance[i] = false;
            }
        }

        for (int i = 1; i < _numRotors; i++) {
            if (advance[i]) {
                _inserted[i].advance();
            }
        }

        int result = c;
        if (_plugBoard != null) {
            result = _plugBoard.permute(c);
        }
        for (int i = _numRotors - 1; i >= 0; i--) {
            result = _inserted[i].convertForward(result);
        }
        for (int i = 1; i < _numRotors; i++) {
            result = _inserted[i].convertBackward(result);
        }
        if (_plugBoard != null) {
            result = _plugBoard.invert(result);
        }
        return result;
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        /*
        It should advance before every character,
        which should be handled by another method.
         */
        String converted = "";
        for (int i = 0; i < msg.length(); i++) {
            int val = _alphabet.toInt(msg.charAt(i));
            converted += _alphabet.toChar(convert(val));
        }
        return converted;
    }

    /** Accessor method.
     * @return rotor
     * */
    Rotor[] insert() {
        Rotor[] inserted = new Rotor[_inserted.length];
        int i = 0;
        for (Rotor r: _inserted) {
            inserted[i] = r;
            i++;
        }
        return inserted;
    }

    /** Insert the names of the rotors into string.
     * @return string
     * */
    String[] insert2() {
        String[] inserted = new String[_inserted.length];
        int i = 0;
        for (Rotor r: _inserted) {
            inserted[i] = r.name();
            i++;
        }
        return inserted;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Num Pawls. */
    private int _pawls;

    /** Num Rotors. */
    private int _numRotors;

    /** Hashmap of rotors. */
    private HashMap<String, Rotor> _allRotors;

    /** Rotors in a array. */
    private Rotor[] _inserted;

    /** The plugboard. */
    private Permutation _plugBoard;

}
