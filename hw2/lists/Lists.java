package lists;

/* NOTE: The file Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #1. */

/** List problem.
 *  @author
 */
public class Lists {

    /* B. */
    /** Return the list of lists formed by breaking up L into "natural runs":
     *  that is, maximal strictly ascending sublists, in the same order as
     *  the original.  For example, if L is (1, 3, 7, 5, 4, 6, 9, 10, 10, 11),
     *  then result is the four-item list
     *            ((1, 3, 7), (5), (4, 6, 9, 10), (10, 11)).
     *  Destructive: creates no new IntList items, and may modify the
     *  original list pointed to by L. */
    /*
    cut the list L given into ascending sublists so when the list L hits a number =
    or less than the previous, create a new list and add it into there
     */
    static IntListList naturalRuns(IntList L) {

        //IntList newList = new IntList();
        //the start of the list L is always going to be the head of the list
        //newList.head = L.head;

        //if the rest of the list exists
        if (L != null){
            //create the list inside the list
            IntListList newLList = new IntListList();

            IntList point = nondestHelp(L);
            newLList = naturalRuns(point.tail);

            //will break the list into chunks by adding a null to the end, point.tail becomes the new L

            point.tail = null;
            return new IntListList(L, newLList);
        }
        return null;
    }

    //change L without destroying it here
    static private IntList nondestHelp(IntList L){
        //check to see if the previous elements are less than the next
        while (L.tail != null && L.head < L.tail.head){
            L = L.tail;
        }
        return L;
    }
}
