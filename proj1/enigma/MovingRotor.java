package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Caedi Seim
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _perm = perm;
        _notches = notches;
        _setting = 0;
        // FIXME
    }

    @Override
    boolean atNotch() {
        //if the setting is equal to the int that the notch
        //is supposed to be
        for (int i = 0; i < alphabet().size(); i++) {
            if (super.setting() == alphabet().toInt(_notches.charAt(i))) {
                return true;
            }

        }
        return super.atNotch();
    }

    boolean rotates() {
        return true;
    }
    // FIXME?
    /*
    If the rotor to the right of the moving rotor is at the notch position,
    then, yes, the next turn, the moving rotor will turn.
    If the moving rotor is at notch position, it will move if
    double-stepping occurs, which happens if the rotor to the
    left is a moving rotor. If the rotor to the left is not a moving rotor,
    it has no pawl, so this moving rotor won’t move even if it is at notch
    position (like Rotor 2 in the small example in the spec).
     */

    @Override
    void advance() {
        //super.convertForward(1);
    }

    // FIXME: ADDITIONAL FIELDS HERE, AS NEEDED
    private String _notches;

    private Permutation _perm;

    private int _setting;
}
