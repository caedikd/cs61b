package lists;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *
 *  @author Caedi Seim
 */

public class ListsTest {
    /** FIXME
     */
    private IntList l1;
    private IntList l2;
    private IntList l3;
    private IntList l4;
    private IntList l5;
    private IntListList L;

    @Test
    public void testNaturalRuns(){
        l1= IntList.list(1,3, 7, 5, 4, 6, 9, 10, 10, 11);
        l2 = IntList.list(1,3,7);
        l3 = IntList.list(5);
        l4 = IntList.list(4,6,9, 10);
        l5 = IntList.list(10, 11);
        L = IntListList.list(l2, l3,l4,l5);
        assertEquals(L, Lists.naturalRuns(l1));


    }
    // It might initially seem daunting to try to set up
    // IntListList expected.
    //
    // There is an easy way to get the IntListList that you want in just
    // few lines of code! Make note of the IntListList.list method that
    // takes as input a 2D array.

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
