package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a reflector in the enigma.
 *  @author
 */
class Reflector extends FixedRotor {

    /** A non-moving rotor named NAME whose permutation at the 0 setting
     * is PERM. */
    Reflector(String name, Permutation perm) {
        super(name, perm);
        _setting = 0;
        // FIXME
    }

    /*
    reflectors only have ONE setting
     */
    // FIXME?

    boolean reflecting(){
        return true;
    }

    int setting() {
        return _setting = 0; // FIXME
    }


    @Override
    void set(int posn) {
        if (posn != 0) {
            throw error("reflector has only one position");
        }
    }

    private int _setting;

}
