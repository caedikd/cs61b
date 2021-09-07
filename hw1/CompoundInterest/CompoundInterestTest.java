import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers.
        assertEquals(0, 0); */
        assertEquals(0, CompoundInterest.numYears(2021));
        assertEquals(1, CompoundInterest.numYears(2022));
        assertEquals(100, CompoundInterest.numYears(2121));

    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(1157.63, CompoundInterest.futureValue(1000.0, 5.0, 2024), 0.01);
        assertEquals(12.544, CompoundInterest.futureValue(10, 12.0, 2023), 0.01);

    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(11.8026496, CompoundInterest.futureValueReal(10, 12, 2023,3 ), tolerance);

    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(16550, CompoundInterest.totalSavings(5000, 2023, 10), tolerance);
        assertEquals(23.205, CompoundInterest.totalSavings(5, 2024, 10), tolerance);

    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(19.89, CompoundInterest.totalSavingsReal(5, 2024, 10, 5), tolerance);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
