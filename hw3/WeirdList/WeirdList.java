/** A WeirdList holds a sequence of integers.
 * @author Caedi
 */
public class WeirdList{
    /** The empty sequence of integers. */
    public int _head;
    public WeirdList _tail;
    public static final WeirdList EMPTY =
            new EmptyWeirdList(0, null);
    //pointing to something useful now

    /** A new WeirdList whose head is HEAD and tail is TAIL. */
    public WeirdList(int head, WeirdList tail) {
        _head = head;
        _tail = tail;
    }

    //through subtypes we can have the same object
    //act in different ways based on the context it is in.


    /** Returns the number of elements in the sequence that
     *  starts with THIS. */
    public int length() {
        return 1 + _tail.length();
    }

    /** Return a string containing my contents as a sequence of numerals
     *  each preceded by a blank.  Thus, if my list contains
     *  5, 4, and 2, this returns " 5 4 2". */
    @Override
    public String toString() {
        return " " + this._head + _tail.toString();
    }

    /** Part 3b: Apply FUNC.apply to every element of THIS WeirdList in
     *  sequence, and return a WeirdList of the resulting values. */
    public WeirdList map(IntUnaryFunction func) {

        return new WeirdList(func.apply(_head), _tail.map(func));  // TODO: REPLACE THIS LINE
    }

    /*
     * You should not add any methods to WeirdList, but you will need
     * to add private fields (e.g. head).

     * But that's not all!

     * You will need to create at least one additional class for WeirdList
     * to work. This is because you are forbidden to use any of the
     * following in ANY of the code for HW3:
     *       if, switch, while, for, do, try, or the ?: operator.

     * If you'd like an obtuse hint, scroll to the very bottom of this
     * file.

     * You can create this hypothetical class (or classes) in separate
     * files like you usually do, or if you're feeling bold you can
     * actually stick them INSIDE of this class. Yes, nested classes
     * are a thing in Java.

     * As an example:
     * class Garden {
     *     private static class Potato {
     *        int n;
     *        public Potato(int nval) {
     *           n = nval;
     *        }
     *     }
     * }
     * You are NOT required to do this, just an extra thing you can
     * do if you want to avoid making a separate .java file. */
    private static class EmptyWeirdList extends WeirdList {
        public EmptyWeirdList(int head, WeirdList tail) {
            super(0, null);
        }

        public int length() {
            return 0;
        }
        public WeirdList map (IntUnaryFunction func) {
            return this;
        }
        public String toString() {
            return "";
        }
    }
}



/*
 * Hint: The first non-trivial thing you'll probably do to WeirdList
 * is to fix the EMPTY static variable so that it points at something
 * useful. */
