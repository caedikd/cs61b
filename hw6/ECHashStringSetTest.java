import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;


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
        int N = 100;
        ECHashStringSet test = new ECHashStringSet(N);
        HashSet test1 = new HashSet();
        String s = "a";
        for (int i = 0; i < N; i++) {
            s = StringUtils.nextString(s);
            test.put(s);
            test1.add(s);
        }
        assertEquals("size doesn't match", test1.size(), test._buckets.length);

        ECHashStringSet test3 = new ECHashStringSet();
        test3.put("A");
        test3.put("B");
        test3.put("C");
        test3.put("D");
        test3.put("E");
        assertEquals("size of hashset", 5, 5);
        test3.put("F");
        test3.put("G");
        test3.put("H");
        test3.put("I");
        test3.put("J");

        test3.put("Fi");
        test3.put("Gi");
        test3.put("Hi");
        test3.put("Ii");
        test3.put("Ji");

        test3.put("Fh");
        test3.put("Gh");
        test3.put("Hh");
        test3.put("Ih");
        test3.put("Jh");

        test3.put("Fs");
        test3.put("Gs");
        test3.put("Hs");
        test3.put("Is");
        test3.put("Js");

        test3.put("Fo");

        System.out.println(test3.asList().toString());
        System.out.println(test3._buckets.length);
    }


    @Test
    public void testNothing() {
        // FIXME: Delete this function and add your own tests
    }
}
