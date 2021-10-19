import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.HashSet;


/**
 * Test of a BST-based String Set.
 * @author
 */
public class ECHashStringSetTest  {
    // FIXME: Add your own tests for your ECHashStringSetTest

    @Test
    public void testResized() {
        ECHashStringSet newset = new ECHashStringSet(4);
        System.out.println(newset._buckets.length);
        newset.resized();
        System.out.println(newset._buckets.length);
    }

    @Test
    public void testPut() {
        int N = 100000;
        ECHashStringSet test = new ECHashStringSet(N);
        HashSet test1 = new HashSet();
        String s = "cat";
        for (int i = 0; i < N; i++) {
            s = StringUtils.nextString(s);
            test.put(s);
            test1.add(s);
        }
        assertEquals("size does not match", test1.size(), test._buckets.length);
    }


    @Test
    public void testNothing() {
        // FIXME: Delete this function and add your own tests
    }
}
