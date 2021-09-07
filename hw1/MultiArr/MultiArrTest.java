import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {

    @Test
    public void testMaxValue() {
        assertEquals(2, MultiArr.maxValue(new int [][] {{1, 0, 1} , {2 , 2 , 0}}));
    }

    @Test
    public void testAllRowSums() {
        assertArrayEquals(new int[] {2, 4}, MultiArr.allRowSums(new int [][] {{1, 0, 1} , {2 , 2 , 0}}));
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
