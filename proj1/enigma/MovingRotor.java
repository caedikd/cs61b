package enigma;

import afu.org.checkerframework.checker.oigj.qual.O;

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
        _notches = notches;
    }

    @Override
    boolean atNotch() {
        return _notches.indexOf(permutation().alphabet().toChar(setting())) != -1;
    }

    @Override
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
        this.set(permutation().wrap(setting() + 1));
    }

//    @Override
//    void set(int posn) {
//        _setting += posn;
//    }
//
//    @Override
//    int setting(){
//        return _setting;
//    }
//
//    @Override
//    int convertForward(int p) {
//        int conversion = _perm.permute(_perm.wrap(p + _setting));
//        return permutation().wrap(conversion - _setting);
//    }
//
//    @Override
//    int convertBackward(int e) {
//        int conversion = _perm.invert((e + _setting) % size());
//        return permutation().wrap(conversion - _setting);
//    }


    private String _notches;

    private Permutation _perm;

    private int _setting;
}
