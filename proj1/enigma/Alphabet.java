package enigma;
import static enigma.EnigmaException.*;

/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Caedi Seim
 */
class Alphabet {

    public String _chars;
    /** A new alphabet containing CHARS. The K-th character has index
     *  K (numbering from 0). No character may be duplicated. */
    Alphabet(String chars) {
        for(int i = 0; i < chars.length(); i++){

        }
        _chars = chars;
    }

    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /** Returns the size of the alphabet. */
    int size() {
        if(_chars.length() == 0){
            throw error("<no alphabet>");
        }
        return _chars.length();// FIXME
    }

    /** Returns true if CH is in this alphabet. */
    boolean contains(char ch) {
        if (_chars.indexOf(ch) == -1){
            return false;
        }
        return true;
    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        //return (char) ('A' + index); // FIXME
        return _chars.charAt(index);
    }

    /** Returns the index of character CH which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        //return ch - 'A'; // FIXME
        return _chars.indexOf(ch);
    }

    boolean duplicate(String chars){
        return true;
    }

}
