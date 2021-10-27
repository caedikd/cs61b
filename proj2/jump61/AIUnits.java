package jump61;

import org.junit.Test;

import static jump61.Side.RED;
import static org.junit.Assert.assertEquals;

public class AIUnits {

    @Test
    public void testStaticEval() {
        Board B = new Board(4);
        B.addSpot(RED, 1, 1);
        B.addSpot(RED, 2, 1);
        B.addSpot(RED, 1, 1);
        System.out.println(B.toString());



    }
}
