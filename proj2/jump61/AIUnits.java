package jump61;

import org.junit.Test;

import static jump61.Side.RED;

/** Attempt at test.
 * @author Caedi
 * */
public class AIUnits {

    /** Bruh moment. */
    @Test
    public void testStaticEval() {
        Board B = new Board(4);
        B.addSpot(RED, 1, 1);
        B.addSpot(RED, 2, 1);
        B.addSpot(RED, 1, 1);
        System.out.println(B.toString());



    }
}
