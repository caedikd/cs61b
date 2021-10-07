package enigma;

import java.util.HashMap;
import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Caedi Seim
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls, HashMap<String, Rotor> allRotors) {
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
        return _numRotors; // FIXME
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls; // FIXME
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        /*
        we need to put the corresponding rotor object in, meaning
        we need to have something that will transform a rotor name
        to the actual object it represents
        - position in the string should guarantee the type of rotor
        - ex. rotors[0] is the reflector and rotos[rotors.length - 1] is the fast moving
        Ways to do this?
        - check if the names of the string rotors are equal to the names
        of
        0-reflector
        1- moving
        2- moving
        3- fast moving?
         */

        /*
        first add reflector
        _allrotors is a bag of rotors that we dont use all of them
        so what does string[] rotors represent
         */

        //check if the starting rotor is a reflector
        //can there be more than 1 reflectors?
        if (_allRotors.get(rotors[0]).reflecting() == false) {
            throw new EnigmaException("Wrong Order");
        }

        for (int i = 0; i < rotors.length; i++) {
            if (i != 0 && _allRotors.get(rotors[i]).reflecting()) {
                throw new EnigmaException("Wrong Order");
            }
            _inserted[i] = _allRotors.get(rotors[i]);
        }

//        if (_inserted.length != _numRotors){
//            throw new EnigmaException("Wrong number of Rotors");
//        }

    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        if (setting.length() != numRotors() - 1) {
            throw new EnigmaException("Wrong set string");
        }

        //if the character is not in the alphabet, throw exception
        for (int j = 0; j < setting.length(); j++){
            if (_alphabet.contains(setting.charAt(j)) == false){
                throw new EnigmaException("Set characters not in Alphabet");
            }
        }

        for (int i = 0; i < numRotors(); i++) {
            /*
            get the rotor at the hashmap and set it to the letters
             */
            _inserted[i].set(setting.charAt(i));
        }

    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugBoard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
//    int convert(int c) {
//        int plugged = _plugBoard.permute(c);
//
//
//        for (int i = numRotors() - 1; i > 0; i--) {
//            if (_inserted[i].atNotch()) {
//                if (i != numRotors() - 1) {
//                    if (i - 1 != 0) {
//                        _inserted[i - 1].advance();
//                        _inserted[i].advance();
//                    }
//                    else {
//                        _inserted[i].advance();
//                    }
//                }
//                else {
//                    _inserted[i - 1].advance();
//                }
//            }
//        }
//        _inserted[numRotors()-1].advance();
//
//
//        /*
//        convert the input character
//        maybe call permutation on
//        first advances the machine (moving rotors as appropriate), then
//        passes the character C through the entire machine - which may be many rotors
//         */
//        // FIXME
//    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        return ""; // FIXME
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    private int _pawls;

    private int _numRotors;

    private HashMap<String, Rotor> _allRotors;

    //keep array
    public Rotor[]_inserted = new Rotor[_numRotors];

    private Permutation _plugBoard;

    // FIXME: ADDITIONAL FIELDS HERE, IF NEEDED.
}
