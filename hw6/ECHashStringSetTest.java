import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

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
    }

    @Test
    public void testNothing() {
        // FIXME: Delete this function and add your own tests
    }
}
