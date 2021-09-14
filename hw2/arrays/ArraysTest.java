package arrays;

import lists.IntList;
import lists.IntListList;
import lists.Lists;
import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author FIXME
 */

public class ArraysTest {
    /** FIXME
     */
    private int[] a;
    private int[] b;
    private int[] result;

    @Test
    public void testCatenate(){
        a = new int[]{1, 2, 3};
        b = new int[]{6, 7, 8, 9};
        result = new int[]{1, 2, 3, 6, 7, 8, 9};
        assertArrayEquals(result ,Arrays.catenate(a, b));
    }

    @Test
    public void testRemove(){
        a = new int[]{0, 1, 2, 3};
        int start = 1;
        int len = 2;
        result = new int[]{0,3};
        assertArrayEquals(result, Arrays.remove(a, start, len));
    }

    @Test
    public void testNaturalRuns() {
        int[] l = {0};
        int[][] b = {{0}};
        assertArrayEquals(b, Arrays.naturalRuns(l));


        int[][] L = new int[4][];
        int[] l1= {1,3, 7, 5, 4, 6, 9, 10, 10, 11};
        L[0] = new int[] {1,3,7};
        L[1] = new int[] {5};
        L[2] = new int[] {4,6,9, 10};
        L[3] = new int[] {10, 11};
        assertArrayEquals(L, Arrays.naturalRuns(l1));

    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
