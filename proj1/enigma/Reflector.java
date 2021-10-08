package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a reflector in the enigma.
 *  @author Caedi Seim
 */
class Reflector extends FixedRotor {

    /** A non-moving rotor named NAME whose permutation at the 0 setting
     * is PERM. */
    Reflector(String name, Permutation perm) {
        super(name, perm);
        // FIXME
    }

    /*
    reflectors only have ONE setting
     */
    // FIXME?

    @Override
    boolean reflecting() {
        return true;
    }

//    int setting() {
//        return _setting = 0; // FIXME
//    }
//    /* reflectors can only ever be at the 0 setting
//    because they don't move
//     */


    @Override
    void set(int posn) {
        if (posn != 0) {
            throw error("reflector has only one position");
        }
    }

//    private int _setting;

}
